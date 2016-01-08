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

import ocelot.common.classtree.ClassTreeService;
import ocelot.common.key.KeyService;
import ocelot.common.page.PageInfo;
import ocelot.common.page.PageTurning;
import ocelot.framework.base.action.BaseAction;
import ocelot.framework.base.dto.IBaseDTO;
import ocelot.framework.base.dto.impl.DynaActionFormDTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.common.ListValueService;
import et.bo.sys.user.service.UserService;

public class UserAction extends BaseAction {

	
	private UserService userService =null;
	private KeyService ks = null;

	private ListValueService listValueService=null;
	private ClassTreeService classTree =null;
	
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
		List dl =listValueService.getLabelValue("SysDepartment","name","id","-1");
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
//		System.out.println("workl size is : "+workl.size());
		request.setAttribute("workl",workl);
		request.setAttribute("type","i");
		return mapping.findForward("info");
    }
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
//			groupService.insertGroup(dto);
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
		List rl =listValueService.getLabelValue("SysRole","name","id","deleteMark","1");
		request.setAttribute("rl",rl);
		List gl =listValueService.getLabelValue("SysGroup","name","id","delMark","1");
		request.setAttribute("gl",gl);
		List dl =listValueService.getLabelValue("SysDepartment","name","id","-1");
		request.setAttribute("dl",dl);
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
		request.setAttribute("type","d");
//		return new ActionForward("/sys/user/manage/info.jsp");
		return mapping.findForward("info");
		
    }
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			String userId =(String)dto.get("userId");
			userService.deleteUser(userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
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
