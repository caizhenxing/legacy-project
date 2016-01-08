/*
 * @(#)CallbackAction.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * <p>回访管理action</p>
 * 
 * @version 2008-04-01
 * @author nie
 */

public class CallbackAction extends BaseAction {
	
	static Logger log = Logger.getLogger(CallbackAction.class.getName());
	
	private CallbackService callbackService = null;
	private QuestionService questionService = null;
	//注入service
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
	 * 根据URL参数执行 toCallbackMain 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toCallbackMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		return map.findForward("main");
	}
	/**
	 * 根据URL参数执行 toCallbackQuery 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toCallbackQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("query");
		
	}
	/**
	 * 根据URL参数执行 toCallbackList 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
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
       //取得list及条数
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
	 * 根据URL参数执行 toCallbackLoad 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCallbackLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        //根本type参数判断执行所需要的操作
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
	 * 根据URL参数执行 toCallbackOper 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toCallbackOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
        
        if (type.equals("insert")) {
			try {
				
				//如果qid存在。则证明是某条问题的回访，修改该条问题的回访信息
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
				
				//callbackService.delCallback((String)dto.get("cust_id"));//这句可是真的删除了啊！
				
				//下面这块是标记删除
				boolean b=callbackService.isDelete((String)dto.get("id"));
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

}
