/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.question.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callback.service.CallbackService;
import et.bo.output.service.OutputService;
import et.bo.question.service.QuestionService;
import et.po.OperQuestion;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p>问题管理action</p>
 * 
 * @version 2008-03-29
 * @author nie
 */

public class QuestionAction extends BaseAction {
	
	static Logger log = Logger.getLogger(QuestionAction.class.getName());
	
	private OutputService outputService = null;
	private QuestionService questionService = null;
	private CallbackService callbackService = null;
	private ClassTreeService cts = null;
	
	public OutputService getOutputService() {
		return outputService;
	}
	public void setOutputService(OutputService outputService) {
		this.outputService = outputService;
	}
	//注入service
	public QuestionService getQuestionService() {
		return questionService;
	}
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	public CallbackService getCallbackService() {
		return callbackService;
	}
	public void setCallbackService(CallbackService callbackService) {
		this.callbackService = callbackService;
	}
	
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	/**
	 * 根据URL参数执行 toQuestionMain 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回main框架页面
	 */
	public ActionForward toQuestionMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		return map.findForward("main");
		
	}
	/**
	 * 根据URL参数执行 toQuestionQuery 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回查询页面
	 */
	public ActionForward toQuestionQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		
		List dict_is_answer_succeed =  cts.getLabelVaList("dict_is_answer_succeed");
		request.setAttribute("dict_is_answer_succeed", dict_is_answer_succeed);
		
		List answer_man =  cts.getLabelVaList("answer_man");
		request.setAttribute("answer_man", answer_man);

		return map.findForward("query");
		
	}
	/**
	 * 根据URL参数执行 toQuestionList 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQuestionList(ActionMapping map, ActionForm form,
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
        pageInfo.setPageSize(14);
       //取得list及条数
        List list = questionService.questionQuery(dto,pageInfo);
        int size = questionService.getQuestionSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("userpageTurning",pt);

		return map.findForward("list");
	}
	/**
	 * 根据URL参数执行 toQuestionLoad 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQuestionLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        
        List dict_is_answer_succeed =  cts.getLabelVaList("dict_is_answer_succeed");
		request.setAttribute("dict_is_answer_succeed", dict_is_answer_succeed);
		List expertTypeList = cts.getLabelVaList("zhuanjialeibie");
		List answer_man =  cts.getLabelVaList("answer_man");
		request.setAttribute("answer_man", answer_man);
		request.setAttribute("expertTypeList", expertTypeList);
        //根本type参数判断执行所需要的操作
        if(type.equals("insert")){
        	return map.findForward("load");
        }
        
        if(type.equals("detail")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = questionService.getQuestionInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	List list = questionService.getCallbackList();
        	request.setAttribute("list", list);
        	
        	String is_callback_succ = (String)dto.get("is_callback_succ");
        	if(is_callback_succ!=null && is_callback_succ.equals("不成功")){
        		request.setAttribute("isAdd", "yes");
        	}
        	String dict_is_callback = (String)dto.get("dict_is_callback");
        	if(dict_is_callback!=null && dict_is_callback.equals("是")){
        		request.setAttribute("isCallback", "yes");
        	}
        	return map.findForward("load");
        }
        
        if(type.equals("update")){
        	
        	request.setAttribute("opertype", "update");
        	String id = request.getParameter("id");
        	IBaseDTO dto = questionService.getQuestionInfo(id);
        	request.setAttribute(map.getName(),dto);
        	
        	List list = questionService.getCallbackList();
        	request.setAttribute("list", list);

        	String is_callback_succ = (String)dto.get("is_callback_succ");
        	
        	if(is_callback_succ!=null && is_callback_succ.equals("不成功")){
        		request.setAttribute("isAdd", "yes");
        	}
        	
        	String dict_is_callback = (String)dto.get("dict_is_callback");
        	if(dict_is_callback!=null && dict_is_callback.equals("是")){
        		request.setAttribute("isCallback", "yes");
        	}
        	return map.findForward("load");
        }
        if(type.equals("delete")){
        	
        	String id = request.getParameter("id");
        	IBaseDTO dto = questionService.getQuestionInfo(id);
        	request.setAttribute(map.getName(), dto);

        	List list = questionService.getCallbackList();
        	request.setAttribute("list", list);
        	
        	String is_callback_succ = (String)dto.get("is_callback_succ");
        	if(is_callback_succ!=null && is_callback_succ.equals("不成功")){
        		request.setAttribute("isAdd", "yes");
        	}
        	
        	String dict_is_callback = (String)dto.get("dict_is_callback");
        	if(dict_is_callback!=null && dict_is_callback.equals("是")){
        		request.setAttribute("isCallback", "yes");
        	}
        	return map.findForward("load");
        }
        
		return map.findForward("load");
	}
	/**
	 * 根据URL参数执行 toQuestionOper 方法，返回要forward页面。
	 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQuestionOper(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		List list = new ArrayList();
    	request.setAttribute("list", list);
		List dict_is_answer_succeed =  cts.getLabelVaList("dict_is_answer_succeed");
		request.setAttribute("dict_is_answer_succeed", dict_is_answer_succeed);
		
		List answer_man =  cts.getLabelVaList("answer_man");
		request.setAttribute("answer_man", answer_man);
		
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		List expertTypeList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertTypeList", expertTypeList);
        if (type.equals("insert")) {
			try {
				
				questionService.addQuestion(dto);

				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("load");
				
			} catch (RuntimeException e) {
				return map.findForward("error");
			}
		}     
        
        if (type.equals("update")){
			try {
				
				boolean b = questionService.updateQuestion(dto);
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
				
				//questionService.delQuestion((String)dto.get("cust_id"));//这句可是真的删除了啊！
				
				//下面这块是标记删除
				boolean b=questionService.isDelete((String)dto.get("id"));
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

	/**
	 * 根据URL参数执行 toOutputFile 方法，返回要forward页面。
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 输出文件，关闭当前页面
	 */
	public ActionForward toOutputFile(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setHeader("Pragrma", "no-cache");
	    response.setHeader("Cache-Control", "no-store");
	    response.setDateHeader("Expires", 0);
	    response.setCharacterEncoding("gbk");
	    response.setContentType("text/html; charset=gbk");
	    PrintWriter out = response.getWriter();
	
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String path = request.getRealPath("/");
		String name = new java.text.SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS").format(new java.util.Date());
		String fileName = path + "output/down/" + name + ".xls";
		System.out.println("fileName: "+fileName);
		List list = questionService.questionSpecialQuery("024007009");
		outputService.outputExcelFile(list, fileName, "question");
		
		return null;
	}
}
