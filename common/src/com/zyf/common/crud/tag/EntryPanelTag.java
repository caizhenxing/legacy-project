package com.zyf.common.crud.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.ExpressionEvaluationUtils;

import com.zyf.security.SecurityContextInfo;
import com.zyf.security.model.CurrentUser;

public class EntryPanelTag extends TagSupport {
	/**
	 * ������ ��������serialVersionUID �������ͣ�long
	 */
	private static final long serialVersionUID = 1485175067093016503L;

	private String panelList = "";

	private String selectId = "";

	private String elementId = "";

	private List objectpanelList = new ArrayList(0);

	private String objectSelectId = "";

	private String objectSelectIdString = "";

	private String objectElementId = "";

	// ��ȡ��ǰ������
	private String qware = "";

	/**
	 * ��ʼ���������д���ֵ����ֵ
	 * 
	 * @throws JspException
	 */
	private void objectInit() throws JspException {
		objectSelectIdString = (String) ExpressionEvaluationUtils.evaluate(
				"panelList", panelList, pageContext);
		objectSelectId = ExpressionEvaluationUtils.evaluateString("selectId",
				selectId, pageContext);
		objectElementId = ExpressionEvaluationUtils.evaluateString("elementId",
				elementId, pageContext);
		// ����${initParam['publicResourceServer']}
		// qware =(String) pageContext.getRequest().getRealPath("/");
		qware = pageContext.getServletContext().getInitParameter(
				"publicResourceServer");
		// ����objectSelectIdString,ת����objectpanelList
		objectpanelList = stringToList(objectSelectIdString);
	}

	public int doStartTag() throws JspException {
		try {
			// ʵ������ز���
			StringBuffer html = new StringBuffer();
			objectInit();
			// ѭ������MAP�е�URL����ȡȨ��
			String urlarray = getUrlArray(objectpanelList, objectSelectId,
					objectElementId);
			html.append(urlarray);
			pageContext.getOut().print(html.toString());
		} catch (Exception je) {
			throw new JspTagException("SimpleTag: " + je.getMessage());
		}
		return 0;
	}

	private String getUrlArray(List objectpanelList, String selectId,
			String objectElementId) {
		StringBuffer html = new StringBuffer();
		html.append("<script type=\"text/javascript\">\r\n");
		html.append("var arr = new Array();\r\n");
		// ������ʱ����
		int arrIndex = 0;

		// �������������һ����������ֱ����ʾ�������
		// Ĭ����ʾҳΪ0
		if ("".equals(selectId) || null == selectId) {
			selectId = "0";
		}
		// Ĭ����ʾID
		String id = selectId;

		// ѭ������MAP�е�URL
		for (int i = 0; i < objectpanelList.size(); i++) {
			// �õ�Ƕ�׵�����
			ArrayList temp = (ArrayList) (objectpanelList.toArray()[i]);
			String name = (String) (temp.toArray()[0]);
			String url = (String) (temp.toArray()[1]);
			CurrentUser currentUser = SecurityContextInfo.getCurrentUser();
			HashMap urlPermissions = currentUser.getUrlPermissions();
			// javascript������ʾ
			// url�ַ�����������STEP��ǰ�Ĳ���
			int index1 = url.indexOf('&');
			String urltemp = new String(url);
			if (-1 != index1) {
				// �������������������
				urltemp = urltemp.substring(0, index1);
			}
			if (urlPermissions.containsKey(urltemp)) {
				// ��Ȩ��, ƴװ�����ַ���
				// html.append("arr[0] = [\" �б� \", \"\", \"<c:url value =
				// '/hr/empTraRecAction.do?step=list&paginater.page=0'/>\"];");
				html.append("arr[");
				html.append(arrIndex);
				html.append("] = [\"");
				html.append(name);
				html.append("\", \"\", \"");
				html.append(qware + url);
				html.append("\"];\r\n");
				// �޸�selectId��ֵ
				// ���������Ƿ������򲻴���
				if (selectId.indexOf("(") == -1) {
					if ((Integer.toString(i)).equals(selectId)) {
						id = Integer.toString(arrIndex);
					}
				}
				arrIndex++;
			}
		}
		// ƴװ�����ַ���
		// html.append("var panel = new Panel.panelObject(\"panel\", arr, 0,
		// \"<c:out value = \"${initParam['publicResourceServer']}/image/main/\"
		// />\");");
		html.append("var panel = new Panel.panelObject(\"panel\", arr, ");
		html.append(id);
		html.append(", \"");
		html.append(qware);
		html.append("/image/main/\");\r\n");
		// ƴװ�����ַ���
		// html.append("document.getElementById(\"divId_panel\").innerHTML =
		// panel.display();");
		if ("".equals(objectElementId) || null == objectElementId) {
			objectElementId = "divId_panel";
		}
		html.append("document.getElementById(\"");
		html.append(objectElementId);
		html.append("\").innerHTML = panel.display();\r\n");
		html.append("Global.displayOperaButton = function(){ };\r\n");
		html.append("</script>");
		return html.toString();
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getPanelList() {
		return panelList;
	}

	public void setPanelList(String panelList) {
		this.panelList = panelList;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	private ArrayList stringToList(String s) {
		ArrayList panelTemp = null;
		if (null != s) {
			// ���ڲ����ַ�����ʱ��
			ArrayList alTemp = new ArrayList();
			String[] s1 = s.split(",");
			for (int i = 0; i < s1.length; i++) {
				ArrayList alTemp2 = new ArrayList();
				String[] s2 = s1[i].split("#");
				alTemp2.add(" " + s2[0].trim() + " ");
				alTemp2.add(s2[1].trim());
				alTemp.add(alTemp2);
			}
			panelTemp = alTemp;
		}
		if (null == panelTemp) {
			panelTemp = new ArrayList();
		}
		return panelTemp;
	}
}
