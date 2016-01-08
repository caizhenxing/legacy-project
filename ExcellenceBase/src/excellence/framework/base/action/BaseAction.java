package excellence.framework.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import excellence.common.classtree.ClassTreeService;
import excellence.common.tree.base.service.TreeControlNodeService;

/**
 * @author zhaoyifei
 * @author wang wenquan
 * @version 2007-1-15
 * @see ����action���
 */
public class BaseAction extends DispatchAction {

	/*
	 * ��¼��־
	 */
	private static Log log = LogFactory.getLog(BaseAction.class);

	/*
	 * ����ǵ�ǰ�����ڵ��id
	 */
	protected String nickName = null;

	/*
	 * ��־�����������Ϣ key value
	 */
	protected Map logInfoMap = null;

	/*
	 * ����ǲ����� �����Ҫ����ע��
	 */
	private ClassTreeService classTreeService = null;

	/*
	 * �Դ�action��־������������� ���ģ���Ƿ���Ҫд��־ true �� false �� Ĭ��ֵtrue
	 */
	// private String writeLog = "true";
	//	
	// public String getWriteLog() {
	// return writeLog;
	// }
	// public void setWriteLog(String writeLog) {
	// this.writeLog = writeLog;
	// }
	/**
	 * ����struts��config.xml��parameterֵ������Ӧ�ķ���
	 * 
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest request,
	 *            HttpServletResponse response
	 * @return ActionForward ����ת��ҳ��
	 * @throws
	 */
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session == null)
			return mapping.findForward("sessionTimeOut");
		this.before(mapping, form, request, response);
		ActionForward a = super.execute(mapping, form, request, response);
		this.after(mapping, form, request, response);
		return a;
	}

	/*
	 * ԭ���ϣ����ص�һЩ��Ϣд��request����session�С�
	 */
	protected void before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// this.initNickName();

	}

	/*
	 * ԭ���ϣ����ص�һЩ��Ϣд��request����session�С�
	 */
	protected void after(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String logNickName = (String) request.getAttribute("logNickName");
		this.initNickName(logNickName);
		if (nickName != null) {
			try {
				TreeControlNodeService node = classTreeService
						.getNodeByNickName(nickName);

				// Map otherInfoMap = (Map) request.getSession().getAttribute(
				// SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				// if (otherInfoMap == null)
				// throw new NullPointerException(
				// "session��ʱ��¼��־������ϢΪ�ղ��ܼ�¼��־����");
				// this.log2Db(node, otherInfoMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ݽڵ������ݿ��м�¼��־
	 * 
	 * @param TreeControlNodeService
	 *            node ��¼�Ľڵ�
	 * @param IBaseDTO
	 *            dto ��־�������������
	 */
	protected void log2Db(TreeControlNodeService node, Map logInfoMap) {
		// classTreeService.log2db(node, logInfoMap);
	}

	/**
	 * ��ʼ����־��������Ϣ
	 * 
	 */
	// protected abstract void initLogInfoMap();
	/**
	 * ��ʼ����־��������Ϣ
	 * 
	 */
	// protected abstract void initNickName();
	protected void initNickName(String logNickName) {
		if (logNickName != null)
			this.nickName = logNickName;
	}

	/**
	 * spring ע��
	 * 
	 * @return
	 */
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}

	/**
	 * spring ע��
	 * 
	 * @return
	 */
	public void setClassTreeService(ClassTreeService classTreeService) {
		// System.out.println("ע��classTreeService"+classTreeService);
		this.classTreeService = classTreeService;
	}
	
	/**
	 * zhang feng add
	 * JSON�������
	 * 
	 * @param response
	 * @param json
	 */
	protected void outJsonString(HttpServletResponse response, String json) {
		response.setContentType("text/javascript;charset=UTF-8");
		try {
			outString(response,json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ���xml��ʽ���ַ���
	 * @param response
	 * @param xmlStr
	 */
	protected void outXMLString(HttpServletResponse response,String xmlStr) {
		response.setContentType("application/xml;charset=UTF-8");
		try {
			outString(response,xmlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//���json��ʽ����
	private void outString(HttpServletResponse response,String str) {
		try {
			PrintWriter out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
