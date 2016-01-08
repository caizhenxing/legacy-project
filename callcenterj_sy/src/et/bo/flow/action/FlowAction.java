/* ��    ����et.bo.flow.action
 * �� �� ����FlowAction.java
 * ע��ʱ�䣺2008-7-18 10:34:08
 * ��Ȩ���У�������׿Խ�Ƽ����޹�˾��
 */

package et.bo.flow.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import et.bo.flow.service.FlowService;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>�ͻ�����action</p>.
 * 
 * @version 2008-03-28
 * @author nie
 */

public class FlowAction extends BaseAction {
	
	static Logger log = Logger.getLogger(FlowAction.class.getName());
	
	private FlowService flowService = null;
	//ע��service
	/**
	 * Gets the flow service.
	 * 
	 * @return the flow service
	 */
	public FlowService getFlowService() {
		return flowService;
	}
	
	/**
	 * Sets the flow service.
	 * 
	 * @param flowService the new flow service
	 */
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	
	/**
	 * ����URL����ִ�� toFlowMain ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toFlowMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("main");
		
	}
	
	/**
	 * ����URL����ִ�� toFlowQuery ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toFlowQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		List uList = flowService.getUserList();
		//System.out.println("************"+uList.size());
		request.setAttribute("uList", uList);
		Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
		String userId = (String)infoMap.get("userId");
		request.setAttribute("userid", userId);
		return map.findForward("query");
		
	}
	
	/**
	 * ����URL����ִ�� toFlowList ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toFlowList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("userpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(19);
       //ȡ��list������
        List list = flowService.flowQuery(dto,pageInfo);
        int size = flowService.getFlowSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);

		return map.findForward("list");
	}
	
	/**
	 * ����URL����ִ�� toFlowLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toFlowLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){
        	String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        	request.setAttribute("date", date);
        	
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = flowService.getFlowInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	List list = new ArrayList();
        	String type_id = dto.get("type_id").toString();
        	if( type_id.equals("oper_focusinfo")||type_id.equals("oper_markanainfo")){
        		list.add(new LabelValueBean("����", "firstDraft"));
        		list.add(new LabelValueBean("һ��", "state1"));
        		list.add(new LabelValueBean("����", "state2"));
        		list.add(new LabelValueBean("����", "state3"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}else{
        		list.add(new LabelValueBean("ԭʼ", "formerly"));
        		list.add(new LabelValueBean("����", "waitCensor"));
        		list.add(new LabelValueBean("����", "turnDown"));
        		list.add(new LabelValueBean("����", "censoring"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}
        	request.setAttribute("typelist", list);
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = flowService.getFlowInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	List list = new ArrayList();
        	String type_id = dto.get("type_id").toString();
        	if( type_id.equals("oper_focusinfo")||type_id.equals("oper_markanainfo")){
        		list.add(new LabelValueBean("����", "firstDraft"));
        		list.add(new LabelValueBean("һ��", "state1"));
        		list.add(new LabelValueBean("����", "state2"));
        		list.add(new LabelValueBean("����", "state3"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}else{
        		list.add(new LabelValueBean("ԭʼ", "formerly"));
        		list.add(new LabelValueBean("����", "waitCensor"));
        		list.add(new LabelValueBean("����", "turnDown"));
        		list.add(new LabelValueBean("����", "censoring"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}
        	request.setAttribute("typelist", list);
        	
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = flowService.getFlowInfo(id);
        	request.setAttribute(map.getName(), dto);
        	
        	List list = new ArrayList();
        	String type_id = dto.get("type_id").toString();
        	if( type_id.equals("oper_focusinfo")||type_id.equals("oper_markanainfo")){
        		list.add(new LabelValueBean("����", "firstDraft"));
        		list.add(new LabelValueBean("һ��", "state1"));
        		list.add(new LabelValueBean("����", "state2"));
        		list.add(new LabelValueBean("����", "state3"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}else{
        		list.add(new LabelValueBean("ԭʼ", "formerly"));
        		list.add(new LabelValueBean("����", "waitCensor"));
        		list.add(new LabelValueBean("����", "turnDown"));
        		list.add(new LabelValueBean("����", "censoring"));
        		list.add(new LabelValueBean("����", "putOut"));
        	}
        	request.setAttribute("typelist", list);
        	
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	
	/**
	 * ����URL����ִ�� toFlowOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toFlowOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		
		List list = new ArrayList();
	    request.setAttribute("typelist", list);
        
        if (type.equals("insert")) {
			try {

				flowService.addFlow(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=flowService.updateFlow(dto);
				if(b==true){
					request.setAttribute("operSign", "�޸ĳɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","�޸�ʧ��");
					return map.findForward("load");
				}
				
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
        
        if (type.equals("delete")){
			try {
				
				flowService.delFlow((String)dto.get("flow_id"));//���������ɾ���˰���
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");
				
				//��������Ǳ��ɾ��
				/*boolean b=flowService.isDelete((String)dto.get("cust_id"));
				if(b==true){
					request.setAttribute("operSign", "ɾ���ɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","ɾ��ʧ��");
					return map.findForward("load");
				}*/
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
       
        
		return map.findForward("load");
	}

}
