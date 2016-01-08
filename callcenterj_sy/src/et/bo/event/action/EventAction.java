

package et.bo.event.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.event.service.EventService;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



public class EventAction extends BaseAction {
	
	static Logger log = Logger.getLogger(EventAction.class.getName());
	
	private EventService eventService = null;
	//ע��service
	/**
	 * Gets the event service.
	 * 
	 * @return the event service
	 */
	public EventService getEventService() {
		return eventService;
	}
	
	/**
	 * Sets the event service.
	 * 
	 * @param eventService the new event service
	 */
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	
	/**
	 * ����URL����ִ�� toEventMain ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toEventMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		return map.findForward("main");
		
	}
	
	/**
	 * ����URL����ִ�� toEventQuery ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toEventQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		List user = eventService.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);
		
		return map.findForward("query");
		
	}
	
	/**
	 * ����URL����ִ�� toEventList ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("eventTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(19);
       //ȡ��list������
        List list = eventService.eventQuery(dto,pageInfo);
        int size = eventService.getEventSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("eventTurning",pt);

		return map.findForward("list");
	}
	
	/**
	 * ����URL����ִ�� toEventLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){
        	Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
    		String userId = (String)infoMap.get("userId");
        	DynaActionFormDTO dto = (DynaActionFormDTO)form;
        	dto.set("principal", userId);
        	dto.set("eventdate", TimeUtil.getNowTime("yyyy-MM-dd"));
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventService.getEventInfo(id);
        	request.setAttribute(map.getName(),dto);
        	request.setAttribute("list", dto.get("list"));

        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventService.getEventInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	request.setAttribute("list", dto.get("list"));

        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventService.getEventInfo(id);
        	request.setAttribute(map.getName(), dto);
        	request.setAttribute("list", dto.get("list"));
        	
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	
	/**
	 * ����URL����ִ�� toEventOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		
		List list = new ArrayList();
	    request.setAttribute("typelist", list);
        
        if (type.equals("insert")) {
			try {
				
				Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
	    		String userId = (String)infoMap.get("userId");
	    		dto.set("adduser", userId);
				
				eventService.addEvent(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=eventService.updateEvent(dto);
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
				
				eventService.delEvent((String)dto.get("id"));//���������ɾ���˰���
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");

			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
       
        
		return map.findForward("load");
	}

}
