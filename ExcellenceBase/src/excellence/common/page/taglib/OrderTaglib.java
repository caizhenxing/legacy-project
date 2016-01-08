/**
 * 	@(#)OrderTaglib.java   2006-12-8 下午04:42:14
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package excellence.common.page.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;

/**
 * @author zhaoyifei
 * @version 2006-12-8
 * @see
 */
public class OrderTaglib extends TagSupport {
	private String name = null;

	private String path = null;

	private String page = null;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut();
		PageInfo pi = null;
		PageTurning pt = getPt();
		pi = pt.getPage();
		try {

			if (name.equals(pi.getFieldAsc())) {
				out.print(pt.getThePage(pi.orderbydesc, name));
				out.print("<img src=\"");
				// out.print(getPat() + "/" + "desc.gif");
				out.print(path + "/" + "desc.gif");
				out.print("\" border=\"0\"/>");
				out.print(pt.getThePageEnd());
			} else {
				out.print(pt.getThePage(pi.orderbyasc, name));
				out.print("<img src=\"");
				// out.print(getPat() + "/" + "asc.gif");
				out.print(path + "/" + "asc.gif");
				out.print("\" border=\"0\"/>");
				out.print(pt.getThePageEnd());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new JspException();
		}
		return super.doEndTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public PageTurning getPt() throws JspException {
		PageTurning pt = null;
		pt = (PageTurning) pageContext.getAttribute(page,
				PageContext.PAGE_SCOPE);
		if (pt == null)
			pt = (PageTurning) pageContext.getAttribute(page,
					PageContext.REQUEST_SCOPE);
		if (pt == null)
			pt = (PageTurning) pageContext.getAttribute(page,
					PageContext.SESSION_SCOPE);
		if (pt == null)
			throw new JspException("Cannot find pageTurning attribute ");
		return pt;
	}

	public String getPat() throws JspException {
		String pt = null;
		pt = (String) pageContext.getAttribute(path, PageContext.PAGE_SCOPE);
		if (pt == null)
			pt = (String) pageContext.getAttribute(path,
					PageContext.REQUEST_SCOPE);
		if (pt == null)
			pt = (String) pageContext.getAttribute(path,
					PageContext.SESSION_SCOPE);
		if (pt == null)
			pt = path;
		return pt;
	}
}
