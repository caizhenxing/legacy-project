/**
 * 	@(#)BookBorrowAction.java   Sep 1, 2006 11:12:37 AM
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

import et.bo.oa.commoninfo.book.service.BookBorrowService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * ͼ�������Ϣ
 * @author zhang
 * @version Sep 1, 2006
 * @see
 */
public class BookBorrowAction extends BaseAction{
	
	private BookBorrowService bookBorrowService = null;
	
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
	public ActionForward toBookBorrowMain(ActionMapping map, ActionForm form,
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
	public ActionForward toBookBorrowQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("employeename", bookBorrowService.getEmployeeList());
		return map.findForward("query");
	}
	
	/**
	 * @describe ��ת���б�ҳ��
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
	public ActionForward toBookBorrowList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("bookhistorypageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = bookBorrowService.bookHistoryIndex(formdto, pageInfo);
        int size = bookBorrowService.getBookHistorysSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("bookhistorypageTurning",pt);
        return map.findForward("list");
	}
	
	/**
	 * @describe �г������û�
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
	public ActionForward toBookBeyondList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("bookhistorypageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = bookBorrowService.bookBeyondIndex(formdto, pageInfo);
        int size = bookBorrowService.getBookBeyondSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("bookhistorypageTurning",pt);
        return map.findForward("list");
	}

	public BookBorrowService getBookBorrowService() {
		return bookBorrowService;
	}

	public void setBookBorrowService(BookBorrowService bookBorrowService) {
		this.bookBorrowService = bookBorrowService;
	}
}
