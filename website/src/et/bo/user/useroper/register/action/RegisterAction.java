/**
 * 	@(#)RegisterAction.java   Dec 1, 2006 2:57:37 PM
 *	 。 
 *	 
 */
package et.bo.user.useroper.register.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.user.useroper.register.service.RegisterService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhangfeng
 * @version Dec 1, 2006
 * @see
 */
public class RegisterAction extends BaseAction{
	
	private RegisterService register = null;
	
	public RegisterService getRegister() {
		return register;
	}

	public void setRegister(RegisterService register) {
		this.register = register;
	}

	/**
	 * @describe 跳转到用户声明信息页
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toDeclare(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("toDeclare");
	}
	
	/**
	 * @describe 跳转到注册用户信息
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toRegister(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("toRegister");
	}
	
	/**
	 * @describe 跳转到注册用户信息
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward register(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String rand=(String)request.getSession().getAttribute("rand");
	 	if(dto.get("val")==null||!((String)dto.get("val")).trim().equals(rand))
	 	{
	 		//request.setAttribute("error","sys.login.error.val");
	 		//request.removeAttribute(map.getName());
	 		return map.findForward("error");
	 	}
		register.registerUser(dto);
		return map.findForward("register");
	}
	
	/**
	 * @describe 跳转到修改用户信息页
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toUpdate(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("toUpdate");
	}
	
	/**
	 * @describe 修改论坛用户信息
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward updateForumUser(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		UserInfo ui = (UserInfo) request.getSession().getAttribute(
				SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		dto.set("id", userkey);
		register.updateUserInfo(dto);
		return map.findForward("register");
	}
	
	
	/**
	 * @describe 跳转到用户登录页
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toLogin(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("tologin");
	}
	
	/**
	 * @describe 验证是否登录成功
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward login(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String rand=(String)request.getSession().getAttribute("rand");
	 	if(dto.get("val")==null||!((String)dto.get("val")).trim().equals(rand))
	 	{
	 		request.setAttribute("error","sys.login.error.val");
	 		request.removeAttribute(map.getName());
	 		return map.findForward("tologin");
	 	}
	 	String name = (String)dto.get("name");
	 	String password = (String)dto.get("password");
	 	if (register.checkLogin(name, password)) {
	 		UserInfo ui = new UserInfo();
	 		String userid = register.getIdByName(name);
	 		ui.setUserName(userid);
	 		request.getSession().setAttribute(SysStaticParameter.USER_IN_SESSION,ui);
	 		request.getSession().setAttribute("userkey", userid);
			return new ActionForward("/forum/forumList.do?method=toForumList&moduleId=1");
		}else{
			request.setAttribute("error","sys.login.error.login");
 			request.removeAttribute(map.getName());
			return map.findForward("tologin");
		}
	}
	
	/**
	 * @describe 用户退出
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward logout(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return new ActionForward("/forum/forumList.do?method=toForumList&moduleId=1");
	}
	
}
