/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.sys.voiceLeave.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.voiceLeave.service.VoiceLeaveService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>客户管理action</p>
 * 
 * @version 2008-03-28
 * @author 王文权
 */

public class VoiceLeaveAction extends BaseAction {
	
	static Logger log = Logger.getLogger(VoiceLeaveAction.class.getName());
	
	private VoiceLeaveService voiceLeaveService = null;
	
	
	public VoiceLeaveService getVoiceLeaveService() {
		return voiceLeaveService;
	}
	public void setVoiceLeaveService(VoiceLeaveService voiceLeaveService) {
		this.voiceLeaveService = voiceLeaveService;
	}
	/**
	 * 根据URL参数执行 toCustinfoMain 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toVoiceLeaveMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("main");
		
	}
	public ActionForward toPrepDispose(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		dto.set("id", id);
		request.getSession().setAttribute(map.getName(), dto);
		return map.findForward("dispose");
		
	}
	public ActionForward execDoWith(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		voiceLeaveService.execDoWith(dto);
		request.setAttribute("disposeSuccess", "true");
		return map.findForward("disposeSuccess");
		
	}
	public ActionForward toVoiceInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		if(id!=null&&!"".equals(id))
		{
//			//System.out.println("&&&&&&&&&&&&&&&&&&&");
			request.setAttribute(map.getName(), voiceLeaveService.getVoiceLeaveInfo(id));
		}
//		//System.out.println("%**%*%*%*%*%*%*%*%*%*%*");
		return map.findForward("toInfo");
	}
	/**
	 * 根据URL参数执行 toCustinfoQuery 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toVoiceLeaveQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		//request.setAttribute("typeList", typeList);
		request.setAttribute("voiceNodeList", voiceLeaveService.getVoiceNodeList());
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
	public ActionForward toVoiceLeaveList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		//语音节点传过来的是id 取其nickName
		String ivrtypeId = dto.getString("ivrtypeId");
		if(ivrtypeId!=null&&"".equals(ivrtypeId.trim())==false)
		{
			//将id变nickName
			dto.set("ivrtypeId", voiceLeaveService.getNickNameById(ivrtypeId));
		}
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("voicepageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(15);
       //取得list及条数
        List list = voiceLeaveService.voiceLeaveQuery(dto,pageInfo);
        int size = voiceLeaveService.getVoiceLeaveSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("voicepageTurning",pt);

		return map.findForward("list");
	}

}
