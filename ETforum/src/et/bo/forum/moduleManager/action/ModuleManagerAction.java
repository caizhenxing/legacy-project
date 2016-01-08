package et.bo.forum.moduleManager.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.common.UserList;
import et.bo.forum.forumList.service.ForumListService;
import et.bo.forum.moduleManager.service.ModuleManagerService;
import et.bo.forum.userInfo.service.UserInfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class ModuleManagerAction extends BaseAction {
	    static Logger log = Logger.getLogger(ModuleManagerAction.class.getName());
	    
	    private ModuleManagerService moduleManagerService = null;
	    
	    private ClassTreeService cts = null; 

		/**
		 * @describe 模块列表
		 */
		public ActionForward toModuleList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
//			request.removeAttribute("operSign");
//			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
//	        String userId = ui.getUserName();
//			String pageState = null;
//			PageInfo pageInfo = null;
//			pageState = (String) request.getParameter("pagestate");
//			if (pageState == null) {
//				pageInfo = new PageInfo();
//			} else {
//				PageTurning pageTurning = (PageTurning) request.getSession()
//						.getAttribute("modulepageTurning");
//				pageInfo = pageTurning.getPage();
//				pageInfo.setState(pageState);
//				dto = (DynaActionFormDTO) pageInfo.getQl();
//			}
//			pageInfo.setPageSize(5);
			HashMap hashmap = new HashMap();
			try {
				hashmap = moduleManagerService.moduleList();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			int size = moduleManagerService.getSize();
//			pageInfo.setRowCount(size);
//			pageInfo.setQl(dto);
			request.setAttribute("moduleMap", hashmap);
			request.setAttribute("forumName", cts.getvaluebyNickName("forum_name"));
//			PageTurning pt = new PageTurning(pageInfo, "/ETforum/", map, request);
//			request.getSession().setAttribute("modulepageTurning", pt);
			return map.findForward("list");
	    }
		/**
		 * @describe 加载模块信息
		 */
		public ActionForward toModuleLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			String id = (String)request.getParameter("id");			
			String type = (String)request.getParameter("type");
			request.setAttribute("opertype",type);
			String createType = (String)request.getParameter("createType");
			request.setAttribute("createType",createType);
			List moduleLabel = moduleManagerService.getModuleValueBean();
			request.setAttribute("moduleLabel", moduleLabel);
			List forumSort = cts.getLabelVaList("forum_sort");
			request.setAttribute("forumSort", forumSort);
			if(type.equals("insert")){			
				return map.findForward("load");
			}
            if(type.equals("update")){
            	IBaseDTO dto = new DynaBeanDTO();
				try {
					dto = moduleManagerService.getModuleInfo(id);
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("load");
            }
            if(type.equals("delete")){
            	IBaseDTO dto = moduleManagerService.getModuleInfo(id);
            	request.setAttribute(map.getName(),dto);
            	return map.findForward("load");
            }
			return map.findForward("load");
			
		}
		/**
		 * @describe 加载父模块信息
		 */
		public ActionForward toParentModuleLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setCharacterEncoding("GBK");
			String id = (String)request.getParameter("id");
			System.out.println(id);
			String type = (String)request.getParameter("type");
			request.setAttribute("opertype",type);
//			String createType = (String)request.getParameter("createType");
//			request.setAttribute("createType",createType);
//			List moduleLabel = moduleManagerService.getModuleValueBean();
//			request.setAttribute("moduleLabel", moduleLabel);
//			List forumSort = cts.getLabelVaList("forum_sort");
//			request.setAttribute("forumSort", forumSort);
			if(type.equals("insert")){			
				return map.findForward("parentLoad");
			}
            if(type.equals("update")){
            	id = moduleManagerService.getIdByModuleName(id);
            	IBaseDTO dto = moduleManagerService.getModuleInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("load");
            }
            if(type.equals("delete")){        	
            	id = moduleManagerService.getIdByModuleName(id);         	
            	IBaseDTO dto = moduleManagerService.getModuleInfo(id);
            	request.setAttribute(map.getName(),dto);
            	return map.findForward("load");
            }
			return map.findForward("parentLoad");
			
		}
		/**
		 * @describe 加载用户信息
		 */
		public ActionForward toModuleOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String type = (String)request.getParameter("type");
			request.setAttribute("opertype",type);
			List moduleLabel = moduleManagerService.getModuleValueBean();
			request.setAttribute("moduleLabel", moduleLabel);
			if(type.equals("insert")){
				try {
					if(moduleManagerService.isModuleExist((String)dto.get("topicTitle"))){
						return map.findForward("load");
					}
					moduleManagerService.addModule(dto);
//					request.setAttribute("operSign", "操作成功");
					return map.findForward("loadSuccess");	
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");	
				}
			}
			if(type.equals("update")){
				try {
					if(moduleManagerService.updateIsModuleExist((String)dto.get("topicTitle"), (String)dto.getString("id"))){
						return map.findForward("load");
					}
					moduleManagerService.updateModule(dto);
//					request.setAttribute("operSign", "操作成功");
					return map.findForward("loadSuccess");	
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");	
				}
			}
			if(type.equals("delete")){
				try {
					moduleManagerService.deleteModule((String)dto.get("id"));
//					request.setAttribute("operSign", "操作成功");
					return map.findForward("loadSuccess");	
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");	
				}
			}					
			return map.findForward("load");		
		}

		public ModuleManagerService getModuleManagerService() {
			return moduleManagerService;
		}
		public void setModuleManagerService(ModuleManagerService moduleManagerService) {
			this.moduleManagerService = moduleManagerService;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		
}
