/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package et.bo.expertgrouphotline.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.expertgrouphotline.service.ExpertGroupHLService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



/**
 * MyEclipse Struts Creation date: 03-13-2009
 * 
 * author: �� ��
 * 
 * @struts.action path="/screen/expertGroupHL" name="expertGroupHL"
 *                parameter="method" scope="request" validate="true"
 */
public class ExpertGroupHLAction extends BaseAction {
	private ExpertGroupHLService es = null;

	public ExpertGroupHLService getEs() {
		return es;
	}

	public void setEs(ExpertGroupHLService es) {
		this.es = es;
	}
	
	/**
	 * ����ר�����е�������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward groupHotLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		GeneralCaseinfoService gcs = (GeneralCaseinfoService)SpringRunningContainer.getInstance().getBean("GeneralCaseinfoService");
		JSONArray jsonArray = JSONArray.fromObject(es.screenList());
		outJsonString(response,jsonArray.toString());
		return null;
	}
	/**
	 * ����ר�����е�������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward groupHotLineByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
//		GeneralCaseinfoService gcs = (GeneralCaseinfoService)SpringRunningContainer.getInstance().getBean("GeneralCaseinfoService");
		String type = request.getParameter("type");
		if(type==null)
		{
			type = "";
		}
		else
		{
			try{
				type = new String(type.getBytes("iso8859-1"));
			}
			catch(Exception e){e.printStackTrace();}
		}

		JSONArray jsonArray = JSONArray.fromObject(es.getScreenExpertList(type));
		outJsonString(response,jsonArray.toString());
		return null;
	}
	public ActionForward toExpertHLMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// DynaActionFormDTO dto = (DynaActionFormDTO) form;// TODO
		// Auto-generated method stub
		return map.findForward("main");
	}

	/**
	 * ��ѯҳ�� Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toExpertHLQuery(ActionMapping map,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// DynaActionFormDTO dto = (DynaActionFormDTO) form;// TODO
		return map.findForward("query");
	}

	/**
	 * ������Ϣҳ�� Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toExpertHLLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String opertype = request.getParameter("opertype");
		String id = request.getParameter("id");
		
		if(opertype.equals("insert")){
			request.setAttribute("opertype", "insert");
		}
		
		// ɾ �� �� Ҫ�Ȱѵ�����Ϣ��������ʾ
		
		if(opertype.equals("delete")){
			request.setAttribute(map.getName(), es.getExpertHotLineById(id));
			request.setAttribute("opertype", "delete");
		}
		if(opertype.equals("update")){
			request.setAttribute(map.getName(), es.getExpertHotLineById(id));
			request.setAttribute("opertype", "update");
		
		}
		if(opertype.equals("detail")){
			request.setAttribute(map.getName(), es.getExpertHotLineById(id));
//			System.out.println(es.getExpertHotLineById(id).toString());
			request.setAttribute("opertype", "detail");	
		}
		return map.findForward("load");
	}

	/**
	 * listҳ�� Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toExpertGroupHLList(ActionMapping map,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
//		System.out.println("1111111111111111111111");
		List list = null;
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("ehlPageTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(20);
		try {
			list = es.getExperGroupHLinfoList(dto, pageInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		int size = es.getRecordSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(dto);
		request.setAttribute("list", list);
		PageTurning pt = new PageTurning(pageInfo, "", map, request);
		request.getSession().setAttribute("ehlPageTurning", pt);
		return map.findForward("list");
	}

	/**
	 * ִ���� ɾ �� ���� Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toExpertGroupHLOper(ActionMapping map,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		String opertype = request.getParameter("opertype");
		request.setAttribute("opertype", opertype);
		if (opertype.equals("insert")) {
			try{
				
				es.addExperGroupHLinfo(dto);
				request.setAttribute("operSign", "true");
			}catch(Exception ex){
				request.setAttribute("operSign", null);
				ex.printStackTrace();
			}
		}
		if (opertype.equals("update")) {
			if (es.updateExpertGroupHLinfo(dto)) {
				request.setAttribute("operSign", "true");
//				System.out.println("�޸ĳɹ�");
			}
		}
		if (opertype.equals("delete")) {
			try{
//				System.out.println("delete����������������");
				es.delExperGroupHLinfo(dto.getString("ehId").toString());
				request.setAttribute("operSign", "true");
			}catch(Exception ex){
				request.setAttribute("operSign", null);
				ex.printStackTrace();
			}
		}
		return map.findForward("load");
	}
}