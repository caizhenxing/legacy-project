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
import et.bo.sys.group.service.GroupService;
import et.bo.sys.user.service.UserService;

public class GroupAction extends BaseAction {

	private KeyService ks = null;
	
	
	private ListValueService listValueService =null;
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
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("error");
			// TODO: handle exception
		}		
		return mapping.findForward("success");
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
			groupService.deleteGroup(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
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
//        System.out.println("l length "+l.size());
        int size = this.groupService.listGroupSize(dform,pageInfo);
//        System.out.println("size ========== "+size);
        pageInfo.setRowCount(size);
        pageInfo.setQl(dform);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",mapping,request);
        request.getSession().setAttribute("groupTurning",pt);		
//		System.out.println(temp.length);
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
	
}
