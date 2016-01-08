

package et.bo.eventResult.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.eventResult.service.EventResultService;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



public class EventResultAction extends BaseAction {
	
	static Logger log = Logger.getLogger(EventResultAction.class.getName());
	
	private EventResultService eventResultService = null;
	//ע��service
	/**
	 * Gets the eventResult service.
	 * 
	 * @return the eventResult service
	 */
	public EventResultService getEventResultService() {
		return eventResultService;
	}
	
	/**
	 * Sets the eventResult service.
	 * 
	 * @param eventResultService the new eventResult service
	 */
	public void setEventResultService(EventResultService eventResultService) {
		this.eventResultService = eventResultService;
	}
	
	/**
	 * ����URL����ִ�� toEventResultMain ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toEventResultMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		return map.findForward("main");
		
	}
	
	/**
	 * ����URL����ִ�� toEventResultQuery ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toEventResultQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		return map.findForward("query");
		
	}
	
	/**
	 * ����URL����ִ�� toEventResultList ����������Ҫforwardҳ�档.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventResultList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("eventResultTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(18);
       //ȡ��list������
        List list = eventResultService.eventResultQuery(dto,pageInfo);
        int size = eventResultService.getEventResultSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("eventResultTurning",pt);

		return map.findForward("list");
	}
	
	/**
	 * ����URL����ִ�� toEventResultLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventResultLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		List user = eventResultService.userQuery("select user_id from sys_user where (group_id = 'SYS_GROUP_0000000001' or group_id = 'SYS_GROUP_0000000141')");
		request.setAttribute("user", user);

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){

        	DynaActionFormDTO dto = (DynaActionFormDTO)form;
        	dto.set("feedback_date", TimeUtil.getNowTime("yyyy-MM-dd"));
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventResultService.getEventResultInfo(id);
        	request.setAttribute(map.getName(),dto);

        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventResultService.getEventResultInfo(id);
        	request.setAttribute(map.getName(),dto);

        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = eventResultService.getEventResultInfo(id);
        	request.setAttribute(map.getName(), dto);

        	
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	
	/**
	 * ����URL����ִ�� toEventResultOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toEventResultOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		
		List list = new ArrayList();
	    request.setAttribute("typelist", list);
	    request.setAttribute("user", new ArrayList());
        if (type.equals("insert")) {
			try {
				
				Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
	    		String userId = (String)infoMap.get("userId");
	    		dto.set("adduser", userId);
				
				eventResultService.addEventResult(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=eventResultService.updateEventResult(dto);
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
				
				eventResultService.delEventResult((String)dto.get("id"));//���������ɾ���˰���
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");

			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
       
        
		return map.findForward("load");
	}

	/**
	 * ���¼�������Ϊ�������Աʱ�õ��ķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String receivers = request.getParameter("value");
		String select = request.getParameter("select");

		List l = (List) request.getSession().getAttribute("userList2");
		List<LabelValueBean> ul = (List) request.getSession().getAttribute(
				"userList");
		System.out.println("userList size is "+ul.size());
		for (int i = 0, size = ul.size(); i < size; i++) {
			LabelValueBean lvb = (LabelValueBean)ul.get(i);
			if (lvb.getValue().equals(select)) {
				l.add(lvb);
				ul.remove(lvb);
				break;
			}
		}
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("select");

	}

	public ActionForward addall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String receivers = request.getParameter("value");
		List l = (List) request.getSession().getAttribute("userList2");
		List ul = (List) request.getSession().getAttribute("userList");
		l.addAll(ul);
		ul.clear();
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("select");
	}

	public ActionForward suball(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String receivers = request.getParameter("value");
		List l = (List) request.getSession().getAttribute("userList2");
		List ul = (List) request.getSession().getAttribute("userList");
		ul.addAll(l);
		l.clear();
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("select");
	}

	/**
	 * Sub.
	 * 
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 * 
	 * @throws Exception the exception
	 */
	public ActionForward sub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String select = request.getParameter("select");

		List<LabelValueBean> l = (List<LabelValueBean>) request.getSession().getAttribute(
				"userList2");
		List ul = (List) request.getSession().getAttribute("userList");
		for (int i = 0, size = l.size(); i < size; i++) {
			LabelValueBean lvb = l.get(i);
			if (lvb.getValue().equals(select)) {
				ul.add(lvb);
				l.remove(lvb);
				break;
			}
		}
		request.getSession().setAttribute("userList", ul);
		request.getSession().setAttribute("userList2", l);
		return mapping.findForward("select");

	}
	
	/**
	 * ��ת��ѡ�����Ϣ������ҳ��
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSelectOperator(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		List list = eventResultService.getUserList();
		List<LabelValueBean> userList2 = new ArrayList<LabelValueBean>();
		request.getSession().setAttribute("userList", list);
		request.getSession().setAttribute("userList2", userList2);
		return map.findForward("select");
	}
	
	public ActionForward select(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
//		List list = messagesService.getUserList();
//		request.setAttribute("userList", list);
		return map.findForward("selectFrame");
	}
	
}
