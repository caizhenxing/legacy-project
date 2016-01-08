/**
 * 	@(#)BookAction.java   Aug 31, 2006 7:33:47 PM
 *	 �� 
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
	 * @describe ��ת��mainҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toBookMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}

	/**
	 * @describe ��ת��queryҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toBookQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("query");
	}

	/**
	 * @describe ��ת��loadҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
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
        //��
		if (type.equals("insert")) {
	        formdto.set("buyTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
	        formdto.set("noteTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			return map.findForward("load");
		}
		//��
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//ɾ
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//��ʧ
		if (type.equals("lose")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//��ϸ
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		//��
		if (type.equals("borrow")) {
			
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			dto.set("borrowTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			request.setAttribute("employeename", bookService.getEmployeeList());
			request.setAttribute(map.getName(), dto);
			return map.findForward("borrow");
		}
		//��
		if (type.equals("return")) {
			
			String id = request.getParameter("id");
			IBaseDTO dto = bookService.getBookInfo(id);
			dto.set("returnTime", TimeUtil.getTheTimeStr(TimeUtil.getNowTime(),"yyyy-MM-dd"));
			request.setAttribute("employeename", "");
			request.setAttribute(map.getName(), dto);
			return map.findForward("borrow");
		}
		//����
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
	 * @describe ��ת��listҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
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
	 * @describe ������ɾ�Ĳ���
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
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
