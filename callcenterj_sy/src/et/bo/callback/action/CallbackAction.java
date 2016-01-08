/*
 * @(#)CallbackAction.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.callback.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callback.service.CallbackService;
import et.bo.question.service.QuestionService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>�طù���action</p>
 * 
 * @version 2008-04-01
 * @author nie
 */

public class CallbackAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CallbackAction.class.getName());
	
	private CallbackService callbackService = null;
	private QuestionService questionService = null;
	//ע��service
	public CallbackService getCallbackService() {
		return callbackService;
	}
	public void setCallbackService(CallbackService callbackService) {
		this.callbackService = callbackService;
	}
	
	public QuestionService getQuestionService() {
		return questionService;
	}
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	/**
	 * ����URL����ִ�� toCallbackMain ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ����main���ҳ��
	 */
	public ActionForward toCallbackMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		return map.findForward("main");
	}
	/**
	 * ����URL����ִ�� toCallbackQuery ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward ���ز�ѯҳ��
	 */
	public ActionForward toCallbackQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("query");
		
	}
	/**
	 * ����URL����ִ�� toCallbackList ����������Ҫforwardҳ�档
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCallbackList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

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
        pageInfo.setPageSize(15);
       //ȡ��list������
        List list = callbackService.callbackQuery(dto,pageInfo);
        int size = callbackService.getCallbackSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);

		return map.findForward("list");
	}
	/**
	 * ����URL����ִ�� toCallbackLoad ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCallbackLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //����type�����ж�ִ������Ҫ�Ĳ���
        if(type.equals("insert")){
        	String qid = request.getParameter("qid");
        	if(qid != null && !qid.equals("")){
        		IBaseDTO dto = questionService.getQuestionInfo(qid);
        		dto.set("question_id", dto.get("id"));
        		dto.set("callback_man", dto.get("callback_man"));
        		dto.set("callback_phone", dto.get("callback_phone"));
        		dto.set("is_callback_succ", dto.get("is_callback_succ"));
            	request.setAttribute(map.getName(),dto);
        	}
        	
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = callbackService.getCallbackInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = callbackService.getCallbackInfo(id);
        	request.setAttribute(map.getName(),dto);
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = callbackService.getCallbackInfo(id);
        	request.setAttribute(map.getName(), dto);
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	/**
	 * ����URL����ִ�� toCallbackOper ����������Ҫforwardҳ�档
	 * ���Ҹ���URL������type��ֵ���ж���ִ�еĲ���:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward �����б�ҳ��
	 */
	public ActionForward toCallbackOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
        
        if (type.equals("insert")) {
			try {
				
				//���qid���ڡ���֤����ĳ������Ļطã��޸ĸ�������Ļط���Ϣ
				String qid = dto.get("question_id").toString();
				if(qid != null && !qid.equals("")){
					questionService.updateCallback(dto);
				}
				
				callbackService.addCallback(dto);
				
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b=callbackService.updateCallback(dto);
				if(b==true){
					request.setAttribute("operSign", "�޸ĳɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","�޸�ʧ��");
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
				
				//callbackService.delCallback((String)dto.get("cust_id"));//���������ɾ���˰���
				
				//��������Ǳ��ɾ��
				boolean b=callbackService.isDelete((String)dto.get("id"));
				if(b==true){
					request.setAttribute("operSign", "ɾ���ɹ�");
					return map.findForward("load");
				}else{
					request.setAttribute("operSign","ɾ��ʧ��");
					return map.findForward("load");
				}
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}		
        
        
		return map.findForward("load");
	}

}
