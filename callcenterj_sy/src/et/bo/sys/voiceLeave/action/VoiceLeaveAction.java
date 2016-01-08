/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * <p>�ͻ�����action</p>
 * 
 * @version 2008-03-28
 * @author ����Ȩ
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
	 * ����URL����ִ�� toCustinfoMain ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
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
	 * ����URL����ִ�� toCustinfoQuery ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toVoiceLeaveQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		//request.setAttribute("typeList", typeList);
		request.setAttribute("voiceNodeList", voiceLeaveService.getVoiceNodeList());
		return map.findForward("query");
		
	}
	/**
	 * ����URL����ִ�� toCustinfoList ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toVoiceLeaveList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		//�����ڵ㴫��������id ȡ��nickName
		String ivrtypeId = dto.getString("ivrtypeId");
		if(ivrtypeId!=null&&"".equals(ivrtypeId.trim())==false)
		{
			//��id��nickName
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
       //ȡ��list������
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
