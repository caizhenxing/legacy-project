/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     沈阳卓越科技有限公司</p>
 */
package et.bo.sys.group.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.sys.group.service.GroupService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



/**
 * @author jingyuzhuo
 *
 */
public class GroupAction extends BaseAction {
	
	private GroupService groupService =null;
	
	private KeyService ks = null;
	
	/**
     * <p>跳转到组管理主柜架页面</p>
     *
     * @param info:跳转到组管理主柜架页面
     * 
     * @return:
     * 
     * @throws
     */
	public ActionForward toGroupMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		return map.findForward("main");
    }
	 /**
     * <p>跳转到组管理主查询</p>
     *
     * @param info:跳转到pop
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward popGroup(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("pop");
    }
	 /**
     * <p>跳转到组管理主查询</p>
     *
     * @param info:跳转到组管理主查询
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward toGroupQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("query");
    }
    /**
     * <p>组管理信息load</p>
     *
     * @param info:组管理信息load
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toGroupLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {

            return map.findForward("load");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=groupService.getGroupInfo(id);

            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=groupService.getGroupInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    /**
     * <p>组管理信息显示</p>
     *
     * @param info:组管理信息显示
     * 
     * @return:
     * 
     * @throws
     */
    public ActionForward toGroupList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("groupTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(16);
        List l = groupService.findGroupInfo(formdto,pageInfo);
        int size = groupService.getGroupSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("groupTurning",pt);       
        return map.findForward("list");
    }
    
    /**
     * <p>添加组基本信息</p>
     *
     * @param info:组管理操作
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operGroup(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {       	
            boolean flag = groupService.addGroupInfo(formdto);
            if(flag==false){
            	request.setAttribute("idus_state","sys.addfail.nameexists");
            	return map.findForward("load");
            }
            request.setAttribute("idus_state","sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	groupService.updateGroupInfo(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            if(groupService.deleteGroupInfo(formdto)==false){
            	request.setAttribute("idus_state","sys.clew.error");
            	return map.findForward("load");
            }
            request.setAttribute("idus_state","sys.delsuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }
	
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
}
