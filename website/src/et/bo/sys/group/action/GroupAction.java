/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.group.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.sys.group.service.GroupService;
import et.bo.sys.user.service.UserService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class GroupAction extends BaseAction {

	private KeyService ks = null;
	
	
	
	private GroupService groupService =null;
	private UserService userService =null;
	/**
	 * @return Returns the groupService.
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * @param groupService The groupService to set.
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	


	
	
	
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
//		List l =this.govYDService.getLabelValue("GovDriverInfo","driverName","driverId","请选择...");
//		request.setAttribute("l",l);

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
		request.setAttribute("type","i");
		return mapping.findForward("info");
    }
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		
		try {
			groupService.insertGroup(dto);
			request.setAttribute("idus_state", "sys.addsuccess");
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
			// TODO: handle exception
		}		
		return mapping.findForward("info");
    }
	 
	public ActionForward toLoad(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String type =request.getParameter("type");
		request.setAttribute("type",type);
		if("i".equals(type))
		{
			return mapping.findForward("info");
		}
		else if("u".equals(type) || "d".equals(type))
		{
			String did =request.getParameter("did");
			IBaseDTO dto =this.groupService.uniqueGroup(did);
			dform.set("name",dto.get("name"));
			dform.set("id",dto.get("id"));
			dform.set("freezeMark",dto.get("delMark"));
			dform.set("remark",dto.get("remark"));
			return mapping.findForward("info");
		}
		return null;
    }
	
	public ActionForward toDel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String did =request.getParameter("did");
		IBaseDTO dto =this.groupService.uniqueGroup(did);
		dform.set("name",dto.get("name"));
		dform.set("id",dto.get("id"));
		dform.set("freezeMark",dto.get("delMark"));
		dform.set("remark",dto.get("remark"));
		request.setAttribute("type","d");
		return mapping.findForward("info");
    }
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			String id =(String)dto.get("id");
			if(groupService.deleteGroup(id)==false){
//				request.setAttribute("idus_state", "sys.clew.error");
				return mapping.findForward("error");
			}
			request.setAttribute("idus_state", "sys.delsuccess");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("info");
    }
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dform =(DynaActionFormDTO)form;
		String did =request.getParameter("did");
		IBaseDTO dto =this.groupService.uniqueGroup(did);
		
		dform.set("name",dto.get("name"));
		dform.set("id",dto.get("id"));
		dform.set("freezeMark",dto.get("delMark"));
		dform.set("remark",dto.get("remark"));
		request.setAttribute("type","u");
		return mapping.findForward("info");
    }
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
		DynaActionFormDTO dto =(DynaActionFormDTO)form;
		try {
			groupService.updateGroup(dto);
			request.setAttribute("idus_state", "sys.updatesuccess");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("info");
    }
	
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
//		List l =this.govYDService.getLabelValue("GovDriverInfo","driverName","driverId","请选择...");
//		request.setAttribute("l",l);
		return mapping.findForward("tosearch");
    }
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("groupTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dform = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = this.groupService.listGroup(dform,pageInfo);
//        
        int size = this.groupService.listGroupSize(dform,pageInfo);

        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"",mapping,request);
        request.getSession().setAttribute("groupTurning",pt);		

		return mapping.findForward("searchresult");
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

	
	
}
