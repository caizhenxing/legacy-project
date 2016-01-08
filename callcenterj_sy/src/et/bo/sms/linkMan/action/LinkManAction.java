package et.bo.sms.linkMan.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import et.bo.sms.linkMan.service.LinkManService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class LinkManAction extends BaseAction{
	
	private LinkManService lgs = null;
	
	private ClassTreeService depTree=null;
	
	private ClassTreeService cts = null;
	
	
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	/**
	 * @describe 跳转到main页面
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
	public ActionForward toLinkManMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}
	
	/**
	 * @describe 跳转到query页面
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
	public ActionForward toLinkManQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List li = depTree.getLabelVaList("1");
		request.setAttribute("branchList", li);
		List l = cts.getLabelVaList("sex_type");
	    request.setAttribute("sexList", l);
	    List gl = lgs.getLinkGroupList();
	    request.setAttribute("groupList", gl);
		return map.findForward("query");
	}
	
	/**
	 * @describe 跳转到list页面
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
	public ActionForward toLinkManList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
        pageInfo.setPageSize(10);
        List list = lgs.linkManQuery(dto, pageInfo);
        int size = lgs.getLinkManSize();
        if(size == 0)
        	request.setAttribute("noResult", "true");
        else
        	request.setAttribute("noResult", "false");
//        System.out.println("noResult is "+request.getAttribute("noResult"));
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list", list);
        PageTurning pt = new PageTurning(pageInfo,"/HandMessage/",map,request);
        request.getSession().setAttribute("userpageTurning",pt);       
		return map.findForward("list");
    }
	
	 public ActionForward operLinkMan(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
	        DynaActionFormDTO dto = (DynaActionFormDTO)form;
	        String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	      
//	        if (type.equals("detail")) {
////	        	smsAlreadyService.updateHandsetNote(dto);
//	            request.setAttribute("idus_state","sys.updatesuccess");
//	            return map.findForward("load");
//	        }
//	        if (type.equals("update")) {
//	        	request.setAttribute("idus_state","sys.updatesuccess");
//	            return map.findForward("load");
//	        }
	        if (type.equals("insert")) {
	        	List li = depTree.getLabelVaList("1");
	    		request.setAttribute("branchList", li);
	    		List l = cts.getLabelVaList("sex_type");
	    	    request.setAttribute("sexList", l);
	    	    List gl = lgs.getLinkGroupList();
	    	    request.setAttribute("groupList", gl);
	        	
	        	return map.findForward("add");
	        }
//			if (type.equals("delete")) {
//				String[] str = dto.getStrings("chk");
//				ActionMessages errors = new ActionMessages();
//				if (str.length == 0) {
//					errors.add("nullselect", new ActionMessage(
//							"agrofront.email.emailLoad.nullselect"));
//					saveErrors(request, errors);
//					request.setAttribute("errMsg","请选择要删除的记录!");
//					return map.findForward("error");
//				}
//				operationService.delSmsAlready(str);
//				request.setAttribute("idus_state", "et.oa.communicate.handsetnote.handsetnotelist.delsuccess");
//				ActionForward actionforward = new ActionForward(
//						"/sms/operation.do?method=toOperationList");
//				return actionforward;
//			}
//			if (type.equals("deleteForever")) {
//				String[] str = dto.getStrings("chk");
//				ActionMessages errors = new ActionMessages();
//				if (str.length == 0) {
//					errors.add("nullselect", new ActionMessage(
//							"agrofront.email.emailLoad.nullselect"));
//					saveErrors(request, errors);
//					request.setAttribute("errMsg","请选择要删除的记录!");
//					return map.findForward("error");
//				}
//				operationService.delSmsAlreadyForever(str);
//				request.setAttribute("idus_state", "et.oa.communicate.handsetnote.handsetnotelist.delsuccess");
//				ActionForward actionforward = new ActionForward(
//						"/sms/operation.do?method=toOperationList");
//				return actionforward;
//			}
	        return map.findForward("load");
	    }
	 
	 /**
		 * @describe 跳转到load页面
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
	 public ActionForward toLinkManLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = lgs.getLinkManInfo(id);
	        	
	        	List li = depTree.getLabelVaList("1");
	    		request.setAttribute("branchList", li);
	    		List l = cts.getLabelVaList("sex_type");
	    	    request.setAttribute("sexList", l);
	    	    List gl = lgs.getLinkGroupList();
	    	    request.setAttribute("groupList", gl);
	        	
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("load");
	        } else if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = lgs.getLinkManInfo(id);
	        	
	        	List li = depTree.getLabelVaList("1");
	    		request.setAttribute("branchList", li);
	    		List l = cts.getLabelVaList("sex_type");
	    	    request.setAttribute("sexList", l);
	    	    List gl = lgs.getLinkGroupList();
	    	    request.setAttribute("groupList", gl);
	        	
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("load");
	        } else if(type.equals("delete")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = lgs.getLinkManInfo(id);
	        	
	        	List li = depTree.getLabelVaList("1");
	    		request.setAttribute("branchList", li);
	    		List l = cts.getLabelVaList("sex_type");
	    	    request.setAttribute("sexList", l);
	    	    List gl = lgs.getLinkGroupList();
	    	    request.setAttribute("groupList", gl);
	        	
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("load");
	        }
	       
			return map.findForward("load");
	    }
	 
	public ActionForward AddLinkMan(ActionMapping map, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		try {
			boolean flag = lgs.addLinkMan(dto);
			if(flag)
				request.setAttribute("operSign", "sys.common.operSuccess");
			
			List li = depTree.getLabelVaList("1");
			request.setAttribute("branchList", li);
			List l = cts.getLabelVaList("sex_type");
		    request.setAttribute("sexList", l);
		    List gl = lgs.getLinkGroupList();
		    request.setAttribute("groupList", gl);
			
			return map.findForward("load");
		} catch(Exception e) {
			e.printStackTrace();
			return map.findForward("error");
		}
	}
	
	public ActionForward UpdateLinkMan(ActionMapping map, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		try {
			String id = dto.get("id").toString();
			System.out.println("id is"+id);
			boolean flag = lgs.updateLinkMan(dto, id);
			if(flag)
				request.setAttribute("operSign", "sys.common.operSuccess");
			
			List li = depTree.getLabelVaList("1");
			request.setAttribute("branchList", li);
			List l = cts.getLabelVaList("sex_type");
		    request.setAttribute("sexList", l);
		    List gl = lgs.getLinkGroupList();
		    request.setAttribute("groupList", gl);
			
			return map.findForward("load");
		} catch(Exception e) {
			e.printStackTrace();
			return map.findForward("error");
		}
	}
	
	public ActionForward delLinkMan(ActionMapping map, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		try {
			String id = dto.get("id").toString();
			System.out.println("id is"+id);
			boolean flag = lgs.delLinkMan(id);
			if(flag)
				request.setAttribute("operSign", "sys.common.operSuccess");
			
			List li = depTree.getLabelVaList("1");
			request.setAttribute("branchList", li);
			List l = cts.getLabelVaList("sex_type");
		    request.setAttribute("sexList", l);
		    List gl = lgs.getLinkGroupList();
		    request.setAttribute("groupList", gl);
			
			return map.findForward("load");
		} catch(Exception e) {
			e.printStackTrace();
			return map.findForward("error");
		}
		
	}

	public LinkManService getLgs() {
		return lgs;
	}

	public void setLgs(LinkManService lgs) {
		this.lgs = lgs;
	}
	
	
}