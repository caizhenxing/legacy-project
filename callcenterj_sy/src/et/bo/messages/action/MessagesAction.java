/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.messages.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.messages.service.MessagesService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>��Ϣ����action</p>
 * 
 * @version 2008-03-29
 * @author nie
 */

public class MessagesAction extends BaseAction {
	
	static Logger log = Logger.getLogger(MessagesAction.class.getName());
	
	private MessagesService messagesService = null;
	//ע��service
	public MessagesService getMessagesService() {
		return messagesService;
	}
	public void setMessagesService(MessagesService messagesService) {
		this.messagesService = messagesService;
	}

	/**
	 * ����URL����ִ�� toMessagesMain ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toMessagesMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("main");
		
	}
	/**
	 * ����URL����ִ�� toMessagesQuery ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toMessagesQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		List<LabelValueBean> uList = messagesService.getUserList();
		request.setAttribute("uList", uList);
		return map.findForward("query");
		
	}
	/**
	 * ����URL����ִ�� toMessagesOpen ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toMessagesOpen(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		return map.findForward("open");
		
	}
	/**
	 * ����URL����ִ�� toMessagesList ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toMessagesList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
//		Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
//		String userid = (String)infoMap.get("userId");
		UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		dto.set("send_id", ub.getUserId());
		//�ж���ϯ���ĸ����
		String usergroup = ub.getUserGroup();

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
        pageInfo.setPageSize(20);
        
       //ȡ��list������
        List list = new ArrayList();
        int size = 0;
        if(usergroup.equals("operator")||usergroup.equals("opseating")){
        	list = messagesService.messagesQuery(dto,pageInfo);
            size = messagesService.getMessagesSize();
        }else{
        	list = messagesService.messagesAdminQuery(dto,pageInfo);
            size = messagesService.getMessagesAdminSize();
        }
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);

        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);

		return map.findForward("list");
	}
	/**
	 * ����URL����ִ�� toMessagesLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toMessagesLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
    	List<LabelValueBean> uList = messagesService.getUserList();
		request.setAttribute("uList", uList);
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){        	
    		request.setAttribute("opertype", type);
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = messagesService.getMessagesInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	return map.findForward("load");
        }
        //������ʾ��Ϣ
        if(type.equals("show")){
        	String id = request.getParameter("id");
        	IBaseDTO dto = messagesService.getMessagesInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	return map.findForward("show");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = messagesService.getMessagesInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = messagesService.getMessagesInfo(id);
        	request.setAttribute(map.getName(), dto);

        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	/**
	 * ����URL����ִ�� toMessagesOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toMessagesOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		List<LabelValueBean> uList = messagesService.getUserList();
		request.setAttribute("uList", uList);
		
		String receive_id ="";
		List userList2 = (List)request.getSession().getAttribute("userList2");
		if(userList2 != null) {
			for(int i=0; i<userList2.size(); i++){
				LabelValueBean bean = (LabelValueBean)userList2.get(i);
				receive_id += bean.getValue();
				if(i != userList2.size()-1)
					receive_id += ",";
			}
			//System.out.println("receive_id is "+receive_id);
		}
		
        if (type.equals("insert")) {
			try {
				Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userid = (String)infoMap.get("userId");
				String username = getUserName(userid);
				dto.set("send_id", userid);
				dto.set("send_name", username);
				dto.set("receive_id", receive_id);

				messagesService.addMessages(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b = messagesService.updateMessages(dto);
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
				
				messagesService.delMessages((String)dto.get("message_id"));//���������ɾ���˰���
				request.setAttribute("operSign", "ɾ���ɹ�");
				return map.findForward("load");
				
				//��������Ǳ��ɾ��
				/*boolean b=messagesService.isDelete((String)dto.get("id"));
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
        
        if (type.equals("callback")) {
			try {
				Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				String userid = (String)infoMap.get("userId");
				String username = getUserName(userid);
				String recId = dto.get("send_id").toString();
				dto.set("send_id", userid);
				dto.set("send_name", username);
				
				dto.set("receive_id", recId);
				
				messagesService.backMessages(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("show");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}
        
        //ȫѡɾ��
        if(type.equals("delall")){
        	String[] str = request.getParameterValues("educe");
        	messagesService.delAllMessages(str);
        	request.setAttribute("operSign", "ɾ���ɹ�");
        	return new ActionForward("/messages/messages.do?method=toMessagesList");
        }
        
        
		return map.findForward("load");
	}
	/**
	 * �����û�IDȡ�����û����û�������û�����
	 * @param userid �û�id
	 * @return username �û���
	 */
	public String getUserName(String userid){
		String username = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ApplicationContext ac = (ApplicationContext) getServlet()
		.getServletContext().getAttribute(
				ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from sys_user where user_id = '" + userid + "'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				username = rs.getString("user_name");
			}
				
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		return username;
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
		List list = messagesService.getUserList();
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
	
	/**
	 * ���¼�������Ϊ�����ϵ��ʱ�õ��ķ���
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
}
