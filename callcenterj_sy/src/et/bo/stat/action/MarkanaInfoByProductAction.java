/* ��    ����et.bo.stat.action
 * �� �� ����MarkanaInfoByProductAction.java
 * ע��ʱ�䣺2008-8-28 14:21:59
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.stat.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.JFreeChart;

import et.bo.stat.service.MarkanaInfoByProductService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


/**
 * The Class MarkanaInfoByProductAction.
 * �г�������ȫ��Ʒ�ֺ͸�Ʒ�ֵĲ�Ʒ������
 * @author Wang Lichun
 */
public class MarkanaInfoByProductAction extends BaseAction {
	private MarkanaInfoByProductService service;
	private ClassTreeService cts = new ClassTreeServiceImpl();

	/**
	 * Gets the service.
	 * The  service type is MarkanaInfoByProductService.
	 * @return the service
	 */
	public MarkanaInfoByProductService getService() {
		return service;
	}

	/**
	 * Sets the service.
	 * The  service type is MarkanaInfoByProductService.
	 * @param service the new service
	 */
	public void setService(MarkanaInfoByProductService service) {
		this.service = service;
	}

	/**
	 * To main.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * ִ�и÷�������ʾ��ϯ�绰��ͳ�Ƶ������������.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponse
	 * 
	 * @return the action forward
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
//		List evaluatingList = cts.getLabelVaList("evaluating");   
//        request.setAttribute("evaluatingList", evaluatingList);
		TreeControlNodeService node = cts.getNodeByNickName("variety");
		Iterator it = node.getChildren().iterator();
		List<LabelValueBean> markanainfolist = new ArrayList<LabelValueBean>();
		while(it.hasNext()){
			TreeControlNodeService child = (TreeControlNodeService)it.next();				
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel(child.getLabel());
			lvb.setValue(child.getLabel());
			markanainfolist.add(lvb);
		}
		request.setAttribute("list", markanainfolist);
		return mapping.findForward("query");
	}

	/**
	 * ����ͳ��������ִ����Ӧ�ĵ�ͳ�Ʒ�������ͳ�ƽ�����Ե�displayҳ��.
	 * 
	 * @param mapping the ActionMapping
	 * @param form the ActionForm
	 * @param request the HttpServletRequest
	 * @param response the HttpServletResponsee
	 * 
	 * @return the action forward
	 */
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=service.query(dto);
			
			if(!"".equals(dto.get("name").toString()))
				request.setAttribute("name", "notnull");
			else
				request.setAttribute("name", "null");
			
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
