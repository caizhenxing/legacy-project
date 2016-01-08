/**
 * 	@(#)BookAction.java   Aug 31, 2006 7:33:47 PM
 *	 。 
 *	 
 */
package et.bo.oa.commoninfo.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.commoninfo.book.service.BookService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Aug 31, 2006
 * @see
 */
public class BookAction extends BaseAction {

	private BookService bookService = null;
	
	private ClassTreeService ctree = null;

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
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
	public ActionForward toBookMain(ActionMapping map, ActionForm form,
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
	public ActionForward toBookQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
	public ActionForward toBookLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
        List l = ctree.getLabelVaList("book_type");
        request.setAttribute("ctreelist", l);
        //增
		if (type.equals("insert")) {
	        formdto.set("buyTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
	        formdto.set("noteTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			return map.findForward("load");
		}
		//改
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//删
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//丢失
		if (type.equals("lose")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//详细
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		//借
		if (type.equals("borrow")) {
			
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			dto.set("borrowTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			request.setAttribute("employeename", bookService.getEmployeeList());
			request.setAttribute(map.getName(), dto);
			return map.findForward("borrow");
		}
		//还
		if (type.equals("return")) {
			
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			dto.set("returnTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			request.setAttribute("employeename", "");
			request.setAttribute(map.getName(), dto);
			return map.findForward("borrow");
		}
		//续借
		if (type.equals("reborrow")) {
			
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute("employeename", bookService.getBorrowInfo(id));
			dto.set("borrowTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			request.setAttribute(map.getName(), dto);
			return map.findForward("borrow");
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
	public ActionForward toBookList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("bookpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = bookService.bookIndex(formdto, pageInfo);
        int size = bookService.getBookSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("bookpageTurning",pt);
        return map.findForward("list");
	}
	
	/**
	 * @describe 招行添删改操作
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
    public ActionForward operBook(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        List l = ctree.getLabelVaList("book_type");
        request.setAttribute("ctreelist", l);
        if (type.equals("insert")) {
        	bookService.addBookInfo(formdto);
            request.setAttribute("idus_state","sys.savesuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	bookService.updateBookInfo(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
        	bookService.delBookInfo(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            return map.findForward("load");
        }
        if (type.equals("lose")) {
        	bookService.loseBookInfo(formdto);
            request.setAttribute("idus_state","sys.opersuccess");
            return map.findForward("load");
        }
        if (type.equals("borrow")) {
        	bookService.addBorrowInfo(formdto);
            request.setAttribute("idus_state","sys.opersuccess");
            return map.findForward("load");
        }
        if (type.equals("reborrow")) {
        	bookService.addReBorrowInfo(formdto);
        	request.setAttribute("idus_state","sys.opersuccess");
        	return map.findForward("load");
		}
        if (type.equals("return")) {
        	bookService.addReturnInfo(formdto);
            request.setAttribute("idus_state","sys.opersuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
