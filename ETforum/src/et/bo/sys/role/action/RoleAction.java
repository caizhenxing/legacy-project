/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.role.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.sys.role.service.RoleService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



/**
 * @author yepl
 *
 */
public class RoleAction extends BaseAction {
	
	private RoleService roleService =null;
	
	private KeyService ks = null;
	
	/**
     * <p>跳转到角色管理主柜架页面</p>
     *
     * @param info:跳转到角色管理主柜架页面
     * 
     * @return:
     * 
     * @throws
     */
	public ActionForward toRoleMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		return map.findForward("main");
    }
	 /**
     * <p>跳转到角色管理主查询</p>
     *
     * @param info:跳转到角色管理主查询
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward toRoleQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("query");
    }
    /**
     * <p>角色管理信息load</p>
     *
     * @param info:角色管理信息load
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toRoleLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {

            return map.findForward("load");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=roleService.getRoleInfo(id);

            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=roleService.getRoleInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    /**
     * <p>角色管理信息显示</p>
     *
     * @param info:角色管理信息显示
     * 
     * @return:
     * 
     * @throws
     */
    public ActionForward toRoleList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("roleTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = roleService.findRoleInfo(formdto,pageInfo);
        int size = roleService.getRoleSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETforum/",map,request);
        request.getSession().setAttribute("roleTurning",pt);       
        return map.findForward("list");
    }
    
    /**
     * <p>添加角色基本信息</p>
     *
     * @param info:
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operRole(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
//        	
            try {
				boolean flag = roleService.addRoleInfo(formdto);
				if(flag==false){
					request.setAttribute("idus_state","agrofront.sys.role.roleManagerLoad.sameRole");
					return map.findForward("load");
				}
				request.setAttribute("idus_state","sys.addsuccess");
	            return map.findForward("load");
				
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
            
            
        }
        if (type.equals("update")) {
            try {
				roleService.updateRoleInfo(formdto);
				request.setAttribute("idus_state","sys.updatesuccess");
				return map.findForward("load");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");				
			}
            
        }
        if (type.equals("delete")) {
            try {
				boolean flag=roleService.deleteRoleInfo(formdto);
				if(flag==false){
					request.setAttribute("idus_state","sys.common.sysRoleUnableDelete");
					return map.findForward("load");
				}
				request.setAttribute("idus_state","sys.delsuccess");
				return map.findForward("load");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
        }
        return map.findForward("load");
    }
	
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
}
