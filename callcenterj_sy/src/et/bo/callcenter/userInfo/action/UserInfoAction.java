/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.callcenter.userInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.userInfo.service.UserInfoService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>客户管理action</p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class UserInfoAction extends BaseAction {
	
	static Logger log = Logger.getLogger(UserInfoAction.class.getName());
	
	private UserInfoService uis = null;
	private ClassTreeService cts = null;
	//注入service
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toCustinfoMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		//System.out.println(".........."+dto.get("outputID"));
		
		return map.findForward("main");
		
	}
	/**
	 * 根据URL参数执行 toCustinfoQuery 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toCustinfoQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
		
		return map.findForward("query");
		
	}
	/**
	 * 根据URL参数执行 toCustinfoList 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String telType = dto.get("tel_type").toString();
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
       //取得list及条数
        List list = uis.custinfoQuery(dto,pageInfo);
        int size = uis.getCustinfoSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);
        
        if("mobile".equals(telType))
        	request.getSession().setAttribute("telType", "mobile");
        else if("workphone".equals(telType))
        	request.getSession().setAttribute("telType", "workphone");
        else if("homephone".equals(telType))
        	request.getSession().setAttribute("telType", "homephone");

		return map.findForward("list");
	}
	
	/**
	 * 根据URL参数执行 toAllList 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toAllList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

        List list = uis.custinfoAllQuery();
        request.setAttribute("list",list);

		return map.findForward("alllist");
	}
	
	/**
	 * 根据URL参数执行 toCustinfoLoad 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        
        List sexList =  cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
        
        //根本type参数判断执行所需要的操作
        if(type.equals("insert")){
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = uis.getCustinfoInfo(id);
        	request.setAttribute(map.getName(), dto);
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	/**
	 * 根据URL参数执行 toCustinfoOper 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCustinfoOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		
		List sexList =  cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);
		
		List typeList =  cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);
        
        if (type.equals("insert")) {
			try {

				uis.addCustinfo(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=uis.updateCustinfo(dto);
				if(b==true){
					request.setAttribute("operSign", "修改成功");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","修改失败");
					return map.findForward("load");
				}
				
			} catch (RuntimeException e) {
				log.error("PortCompareAction : update ERROR");
				e.printStackTrace();
				return map.findForward("error");
			}
		}
        
        if (type.equals("delete")){
			try {
				
				//custinfoService.delCustinfo((String)dto.get("cust_id"));//这句可是真的删除了啊！
				
				//下面这块是标记删除
				boolean b=uis.isDelete((String)dto.get("cust_id"));
				if(b==true){
					request.setAttribute("operSign", "删除成功");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","删除失败");
					return map.findForward("load");
				}
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
        
        
		return map.findForward("load");
	}
	public UserInfoService getUis() {
		return uis;
	}
	public void setUis(UserInfoService uis) {
		this.uis = uis;
	}

}
