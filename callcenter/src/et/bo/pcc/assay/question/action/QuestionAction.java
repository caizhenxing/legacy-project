/**
 * 	@(#)QuestionAction.java   Oct 11, 2006 1:23:55 PM
 *	 。 
 *	 
 */
package et.bo.pcc.assay.question.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.assay.question.service.QuestionService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Oct 11, 2006
 * @see
 */
public class QuestionAction extends BaseAction{
	
	private QuestionService question = null;
	
	private ClassTreeService ctree = null;

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public QuestionService getQuestion() {
		return question;
	}

	public void setQuestion(QuestionService question) {
		this.question = question;
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
	public ActionForward toQuestionMain(ActionMapping map, ActionForm form,
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
	public ActionForward toQuestionQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        List l = ctree.getLabelVaList("question_type");
        request.setAttribute("ctreelist", l);
		return map.findForward("query");
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
	public ActionForward toQuestionLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
        List l = ctree.getLabelVaList("question_type");
        request.setAttribute("ctreelist", l);
		//详细
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = question.getQuestionInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		return map.findForward("load");
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
	public ActionForward toQuestionList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("questionpageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l = question.questionIndex(formdto, pageInfo);
        int size = question.getQuestionSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/callcenter/", map, request);
        request.getSession().setAttribute("questionpageTurning", pt);

        return map.findForward("list");
	}

}
