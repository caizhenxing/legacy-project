/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.user.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.common.ListValueService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import et.bo.sys.user.service.UserService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class UserAction extends BaseAction {

	
	private UserService userService =null;
	private KeyService ks = null;

	private ListValueService listValueService=null;
	private ClassTreeService classTree =null;
	private ClassTreeService depTree=null;
	
	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		
		return mapping.findForward("main");
    }
	
	public ActionForward toAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		
//		List l =this.govYDService.getLabelValue("GovDriverInfo","driverName","driverId","请选择...");
//		request.setAttribute("type","i");
//		request.setAttribute("l",l);
//		
//		govService.addGov(dto);
		List dl =depTree.getLabelVaList("1");
		request.setAttribute("dl",dl);
		List rl =listValueService.getLabelValue("SysRole","name","id","deleteMark","1");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","delMark","1");
		request.setAttribute("gl",gl);
		List cl =classTree.getLabelVaList("areaType");
		request.setAttribute("cl",cl);
		List provincel =classTree.getLabelVaList("provinceType");
		request.setAttribute("provincel",provincel);
		List bloodl =classTree.getLabelVaList("bloodType");
		request.setAttribute("bloodl",bloodl);
		List workl =classTree.getLabelVaList("workType");
		//证件类型
		List cardKind = classTree.getLabelVaList("card_kind");
		request.setAttribute("cardKind", cardKind);
    	IBaseDTO dbd = new DynaBeanDTO();
    	dbd.set("sexId", "1");
    	request.setAttribute(mapping.getName(), dbd);
    	
		request.setAttribute("workl",workl);
		request.setAttribute("type","i");
		return mapping.findForward("info");
    }
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		if(userService.judgementSameUer(dto)){			
			return mapping.findForward("error");
		}
		try {
//			groupService.insertGroup(dto);
//			Password_encrypt pe = new Password_encrypt();
//			dto.set("password",pe.pw_encrypt(dto.get("password").toString()));
			userService.insertUser(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
			// TODO: handle exception
		}		
		return mapping.findForward("success");
    }
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		try
		{
		List rl =listValueService.getLabelValue("SysRole","name","id","deleteMark","1");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","delMark","1");
		request.setAttribute("gl",gl);
		List dl =depTree.getLabelVaList("1");
		request.setAttribute("dl",dl);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
//		List l =this.govYDService.getLabelValue("GovDriverInfo","driverName","driverId","请选择...");
//		request.setAttribute("l",l);
//		return new ActionForward("/sys/user/manage/toSearch.jsp");
		return mapping.findForward("tosearch");
    }
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("userTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l =this.userService.listUser(dform,pageInfo);
        int size =this.userService.listUserSize(dform);
        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",mapping,request);
        request.getSession().setAttribute("userTurning",pt);
//		return new ActionForward("/sys/user/manage/searchResult.jsp");
        return mapping.findForward("searchresult");
    }
	public void DTOtoDTO(IBaseDTO dto1, IBaseDTO dto2)
	{
		dto1.set("userId",dto2.get("userId"));
		dto1.set("sysRole",dto2.get("sysRole"));
		dto1.set("sysGroup",dto2.get("sysGroup"));
		dto1.set("password",dto2.get("password"));
		dto1.set("userName",dto2.get("userName"));
		dto1.set("departmentId",dto2.get("departmentId"));
		dto1.set("freezeMark",dto2.get("freezeMark"));
		dto1.set("deleteMark",dto2.get("deleteMark"));
		dto1.set("remark",dto2.get("remark"));
		dto1.set("realName",dto2.get("realName"));
		dto1.set("sexId",dto2.get("sexId"));
		dto1.set("identityKind",dto2.get("identityKind"));
		dto1.set("identityCard",dto2.get("identityCard"));
		dto1.set("birthday",dto2.get("birthday"));
		dto1.set("countryId",dto2.get("countryId"));
		dto1.set("provinceId",dto2.get("provinceId"));
		dto1.set("qq",dto2.get("qq"));
		dto1.set("bloodType",dto2.get("bloodType"));
		dto1.set("address",dto2.get("address"));
		dto1.set("postalcode",dto2.get("postalcode"));
		dto1.set("mobile",dto2.get("mobile"));
		dto1.set("finishSchool",dto2.get("finishSchool"));
		dto1.set("speciality",dto2.get("speciality"));
		dto1.set("workId",dto2.get("workId"));
		dto1.set("homepage",dto2.get("homepage"));
	}
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String did =request.getParameter("did");
		IBaseDTO dto =this.userService.uniqueUser(did);
		DTOtoDTO(dform,dto);	
		List dl =depTree.getLabelVaList("1");
		request.setAttribute("dl",dl);
		List rl =listValueService.getLabelValue("SysRole","name","id","请选择");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","请选择");
		request.setAttribute("gl",gl);
		List cl =classTree.getLabelVaList("areaType");
		request.setAttribute("cl",cl);
		List provincel =classTree.getLabelVaList("provinceType");
		request.setAttribute("provincel",provincel);
		List bloodl =classTree.getLabelVaList("bloodType");
		request.setAttribute("bloodl",bloodl);
		List workl =classTree.getLabelVaList("workType");
		request.setAttribute("workl",workl);
		List cardKind = classTree.getLabelVaList("card_kind");
		request.setAttribute("cardKind", cardKind);
		request.setAttribute("type","u");
//		return new ActionForward("/sys/user/manage/info.jsp");
		return mapping.findForward("info");
		
    }
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			userService.updateUser(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
    }
	
	public ActionForward toDel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String did =request.getParameter("did");
		IBaseDTO dto =this.userService.uniqueUser(did);
		DTOtoDTO(dform,dto);
		List dl =listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
		List rl =listValueService.getLabelValue("SysRole","name","id","请选择");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","请选择");
		request.setAttribute("gl",gl);
		List cl =classTree.getLabelVaList("areaType");
		request.setAttribute("cl",cl);
		List provincel =classTree.getLabelVaList("provinceType");
		request.setAttribute("provincel",provincel);
		List bloodl =classTree.getLabelVaList("bloodType");
		request.setAttribute("bloodl",bloodl);
		List workl =classTree.getLabelVaList("workType");
		request.setAttribute("workl",workl);
		List cardKind = classTree.getLabelVaList("card_kind");
		request.setAttribute("cardKind", cardKind);
		request.setAttribute("type","d");
//		return new ActionForward("/sys/user/manage/info.jsp");
		return mapping.findForward("info");
		
    }
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		List dl =listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
		List rl =listValueService.getLabelValue("SysRole","name","id","请选择");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","请选择");
		request.setAttribute("gl",gl);
		List cl =classTree.getLabelVaList("areaType");
		request.setAttribute("cl",cl);
		List provincel =classTree.getLabelVaList("provinceType");
		request.setAttribute("provincel",provincel);
		List bloodl =classTree.getLabelVaList("bloodType");
		request.setAttribute("bloodl",bloodl);
		List workl =classTree.getLabelVaList("workType");
		request.setAttribute("workl",workl);
		List cardKind = classTree.getLabelVaList("card_kind");
		request.setAttribute("cardKind", cardKind);
		try {
			String userId =(String)dto.get("userId");
			boolean flag = userService.deleteUser(userId);
			if(flag==false){
				request.setAttribute("idus_state", "sys.common.sysUserUnableDelete");
				return mapping.findForward("info");
			}
			request.setAttribute("idus_state", "sys.delsuccess");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("info");
    }
	
	/**
	 * @describe 密码修改页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toModifyPwd(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {     
		request.removeAttribute(map.getName());
		return map.findForward("toModifyPwd");
    }
	/**
	 * @describe 密码修改页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toManagerModifyPwd(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {     
//		request.removeAttribute(map.getName());
		String userId = request.getParameter("did");
		System.out.println(userId);
		request.setAttribute("userId", userId);
		return map.findForward("toManagerModifyPwd");
    }
	/**
	 * @describe 密码修改
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operModifyPwd(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        if(!formdto.get("repassword").toString().equals(formdto.get("repasswordAffirm").toString())){
        	request.setAttribute("operSign", "sys.clew.notSamePwd");
        	return map.findForward("toModifyPwd");
        }
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        String userkey = ui.getUserName();
        Password_encrypt pe = new Password_encrypt();
        if(!userService.check(userkey, formdto.get("password").toString())){
        	request.setAttribute("operSign", "sys.clew.modifyPwdError");
        	return map.findForward("modifyPwdSuccess");
        }
        	try {
        		formdto.set("userId", userkey);
        		formdto.set("repassword",pe.pw_encrypt(formdto.get("repassword").toString()));
        		userService.updatePwd(formdto);
				request.setAttribute("operSign", "sys.clew.success");			
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
			return map.findForward("modifyPwdSuccess");
    }
	/**
	 * @describe 密码修改
	 * @param
	 * @return
	 * 
	 */
	public ActionForward ManagerModifyPwd(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		System.out.println(formdto.get("repassword").toString()+"  "+formdto.get("repasswordAffirm").toString());
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		if(!formdto.get("repassword").toString().equals(formdto.get("repasswordAffirm").toString())){
        	request.setAttribute("operSign", "sys.clew.notSamePwd");
//        	System.out.println("输入的密码不一样!");
        	return map.findForward("toManagerModifyPwd");
        }
//        String userId = request.getAttribute("userId").toString();
//        System.out.println("userkey   :"+request.getParameter("userId"));
        
        formdto.set("userId", userId);
//        System.out.println("userId      "+userId);
//		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
//        String userkey = ui.getUserName();
        Password_encrypt pe = new Password_encrypt();
//        if(!userService.judgementSameUer(formdto)){
//        	request.setAttribute("operSign", "sys.clew.modifyPwdError");
//        	return map.findForward("managerModifyPwdSuccess");
//        }
        	try {
//        		formdto.set("userId", userkey);
        		formdto.set("repassword",pe.pw_encrypt(formdto.get("repassword").toString()));
        		userService.updatePwd(formdto);
//        		System.out.println("修改成功");
				request.setAttribute("operSign", "sys.clew.success");			
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
			return map.findForward("toManagerModifyPwd");
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
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
	 * @param userService The userService to set.
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return Returns the listValueService.
	 */
	public ListValueService getListValueService() {
		return listValueService;
	}

	/**
	 * @param listValueService The listValueService to set.
	 */
	public void setListValueService(ListValueService listValueService) {
		this.listValueService = listValueService;
	}

	/**
	 * @return Returns the classTree.
	 */
	public ClassTreeService getClassTree() {
		return classTree;
	}

	/**
	 * @param classTree The classTree to set.
	 */
	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}

	

}
