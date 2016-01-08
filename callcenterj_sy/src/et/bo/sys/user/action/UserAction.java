/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     沈阳卓越科技有限公司</p>
 */
package et.bo.sys.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import et.bo.sys.user.service.UserService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class UserAction extends BaseAction {

	private UserService userService = null;

	private KeyService ks = null;
	
	private ClassTreeService cts = null;


	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("main");
	}

	
	public void DTOtoDTO(IBaseDTO dto1, IBaseDTO dto2) {
		dto1.set("userId", dto2.get("userId"));
		dto1.set("sysRole", dto2.get("sysRole"));
		dto1.set("sysGroup", dto2.get("sysGroup"));
		dto1.set("password", dto2.get("password"));
		dto1.set("userName", dto2.get("userName"));
		dto1.set("departmentId", dto2.get("departmentId"));
		dto1.set("freezeMark", dto2.get("freezeMark"));
		dto1.set("deleteMark", dto2.get("deleteMark"));
		dto1.set("remark", dto2.get("remark"));
		dto1.set("realName", dto2.get("realName"));
		dto1.set("sexId", dto2.get("sexId"));
		dto1.set("identityKind", dto2.get("identityKind"));
		dto1.set("identityCard", dto2.get("identityCard"));
		dto1.set("birthday", dto2.get("birthday"));
		dto1.set("countryId", dto2.get("countryId"));
		dto1.set("provinceId", dto2.get("provinceId"));
		dto1.set("qq", dto2.get("qq"));
		dto1.set("bloodType", dto2.get("bloodType"));
		dto1.set("address", dto2.get("address"));
		dto1.set("postalcode", dto2.get("postalcode"));
		dto1.set("mobile", dto2.get("mobile"));
		dto1.set("finishSchool", dto2.get("finishSchool"));
		dto1.set("speciality", dto2.get("speciality"));
		dto1.set("workId", dto2.get("workId"));
		dto1.set("homepage", dto2.get("homepage"));
	}

	
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
        List GroupList  = userService.getGroupList();
        request.setAttribute("GroupList", GroupList);
        
        List RoleList  = userService.getRoleList();
        request.setAttribute("RoleList", RoleList);
        
        List user=userService.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
        request.setAttribute("user", user);
        
		return mapping.findForward("tosearch");
	}
	
	
	/**
	 * @describe 跳转到OA用户load页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toUserLoginload(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionFormDTO dbd = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		
        request.setAttribute("opertype",type);

        List GroupList  = userService.getGroupList();
        request.setAttribute("GroupList", GroupList);
        
        List RoleList  = userService.getRoleList();
        request.setAttribute("RoleList", RoleList);
        
        // modify by chengang 20080426 添加技能组list

        cts = (ClassTreeService)SpringRunningContainer.getInstance().getBean("ClassTreeService");

        List skillList = cts.getLabelVaList("skillType", false);
        if(skillList != null)
        	request.setAttribute("skillList", skillList);
        
        List departmentList = cts.getLabelVaList("departmentRoot", true);
        if(departmentList != null)
        	request.setAttribute("depList", departmentList);
        // *****************************************
		
        if(type.equals("insert")){
//        	UserBean u=(UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
        	IBaseDTO dto = (IBaseDTO) form;
//        	dto.set("userId",u.getUserId());
        	request.setAttribute(map.getName(), dto);
        	return map.findForward("toUserLoginload");
        }
        if(type.equals("update")){
        	String id = request.getParameter("id");
        	IBaseDTO dto = userService.getUserInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	return map.findForward("toUserLoginload");
        }
        if(type.equals("detail")){
        	String id = request.getParameter("id");
        	IBaseDTO dto = userService.getUserInfo(id);
        	request.setAttribute(map.getName(), dto);

        	return map.findForward("toUserLoginload");
        }
        if(type.equals("delete")){
        	String id = request.getParameter("id");
        	IBaseDTO dto = userService.getUserInfo(id);
        	request.setAttribute(map.getName(), dto);

        	return map.findForward("toUserLoginload");
        }
		
		
		
		return map.findForward("toUserLoginload");
	}
	
	public ActionForward userLoginList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dform = (DynaActionFormDTO) form;

		String pageState = null;
		PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
		if (pageState == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("userLoginTurning");
			pageInfo = pageTurning.getPage();
			pageInfo.setState(pageState);
			dform = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(16);
		List l = this.userService.userList(dform, pageInfo);
		int size = this.userService.listUserSize(dform);
		pageInfo.setRowCount(size);
		pageInfo.setQl(dform);
		request.setAttribute("list", l);
		PageTurning pt = new PageTurning(pageInfo, "/callcenterj_sy/", mapping, request);
		request.getSession().setAttribute("userLoginTurning", pt);
		// return new ActionForward("/sys/user/manage/searchResult.jsp");
		return mapping.findForward("userLoginList");
	}
	
	/**
	 * @describe 对OA用户操作
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operUserLogin(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		
        request.setAttribute("opertype",type);
        
        List GroupList  = userService.getGroupList();
        request.setAttribute("GroupList", GroupList);
        
        List RoleList  = userService.getRoleList();
        request.setAttribute("RoleList", RoleList);
        
        Password_encrypt pe = new Password_encrypt();
        dto.set("password", pe.pw_encrypt(dto.get("password")
				.toString()));
        
//      modify by chengang 20080426 添加技能组list
//        System.out.println("cts is "+cts);
        cts = (ClassTreeService)SpringRunningContainer.getInstance().getBean("ClassTreeService");
//        System.out.println("cts is "+cts);
        List skillList = cts.getLabelVaList("skillType", false);
        if(skillList != null)
        	request.setAttribute("skillList", skillList);
        
        List departmentList = cts.getLabelVaList("departmentRoot", true);
        if(departmentList != null)
        	request.setAttribute("depList", departmentList);
        // *****************************************
        
		if (type.equals("insert")) {
//			try {
//////////	/审核权限处理开始
			String auditing = "";
			String[] auditings = dto.getStrings("auditings");
			for(int i = 0; i < auditings.length; i++){
				auditing += auditings[i]+",";
			}
			if(auditings.length > 0){//去掉最后一个逗号
				auditing = auditing.substring(0, auditing.length()-1);
			}
			dto.set("auditing", auditing);
//////////	/审核权限处理结束
				userService.addUser(dto);
				request.setAttribute("operSign", "sys.common.operSuccess");
//			} catch (RuntimeException e) {
//				// TODO Auto-generated catch block
//				return map.findForward("error");
//			}
		}        
		if (type.equals("update")){
			try {
				///////////审核权限处理开始
				String auditing = "";
				String[] auditings = dto.getStrings("auditings");
				for(int i = 0; i < auditings.length; i++){
					auditing += auditings[i]+",";
				}
				if(auditings.length > 0){//去掉最后一个逗号
					auditing = auditing.substring(0, auditing.length()-1);
				}
				dto.set("auditing", auditing);
				///////////审核权限处理结束
				
				boolean b=userService.userUpdate(dto);
				if(b==true){
					request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
					return map.findForward("toUserLoginload");
				}
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toUserLoginload");
			} catch (RuntimeException e) {
//				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
		if (type.equals("delete")){
			try {
				userService.delUser((String)dto.get("id"));
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("toUserLoginload");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return map.findForward("error");
			}
		}			

		return map.findForward("toUserLoginload");
	}
	
	
	/**
	 * @describe 对OA用户操作
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operUserAll(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;

		String str = request.getParameter("str").toString();
		

			
		
		
			try {
				
				boolean bool = userService.addUserAll(dto,str);
				
				
				if(bool==true)
				{
					request.setAttribute("userReg", "yes");
					return map.findForward("selectDep");
				}
				else if(bool==false)
				{
					request.setAttribute("operSign", "sys.common.operSuccess");
				}
				
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				return map.findForward("error");
			}
			
			
			return map.findForward("selectDep");
	}
	
	
	
	
	/**
	 * @describe 管理员密码修改页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toManagerModifyPwd(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// request.removeAttribute(map.getName());
		String userId = request.getParameter("did");
		//System.out.println(userId);
		request.setAttribute("userId", userId);
		return map.findForward("toManagerModifyPwd");
	}

	
	
	
	public ActionForward popUser(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		return map.findForward("popUser");
	}
	
	

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 需要写出方法的具体实现

	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks
	 *            The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * @return Returns the userService.
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            The userService to set.
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public ClassTreeService getCts() {
		return cts;
	}


	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	


}