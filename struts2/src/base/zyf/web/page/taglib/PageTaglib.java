package base.zyf.web.page.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import base.zyf.web.page.PageInfo;



/**
 * @author 赵一非
 * @version 2007-1-15
 * @see
 */
public class PageTaglib extends TagSupport {

	String name = "pageInfo";

	String style = "first";
	//表格开始
	static String table = "<table border='0' width='100%' class='pagetable'>";
	static String hidden = "<input type='hidden' name='pageInfo.showPage' id='showPage'>";
	//表格结束
	static String table_ = "</table>";
	//行开始
	static String tr = "<tr>";
	//行结束
	static String tr_ = "</tr>";
	//列开始
	static String td_left = "<td align='left'>";
	static String td_center = "<td align='center'>";
	static String td_right = "<td align='right'>";
	//列结束
	static String td_ = "</td>";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut();

		try {
			PageTurning pt = getPt();

			if (style.equals("first")) {
				out.print(table);
				out.print(hidden);
				out.print(tr);
				out.print(td_center);
				out.print(pt.getFirstPage());
				out.print(pt.getFirstWords());
				out.print(pt.getFirstA());
				out.print(td_);
				out.print(td_center);
				out.print(pt.getPreviousPage());
				out.print(pt.getPreviousWords());
				out.print(pt.getPreviousA());
				out.print(td_);
				out.print(td_center);
				out.print(pt.getNextPage());
				out.print(pt.getNextWords());
				out.print(pt.getNextA());
				out.print(td_);
				out.print(td_center);
				out.print(pt.getLastPage());
				out.print(pt.getLastWords());
				out.print(pt.getLastA());
				out.print(td_);
				out.print(td_right);
				out.print(pt.getShowSummary());
				out.print(td_);
				out.print(tr_);
				out.print(table_);
			}
			if (style.equals("second")) {
				String script = "\r\n<script>\r\n"
						+ "function changepageto(pagechangea){\r\n"
						+ "if(isNaN(pagechangea.value))\r\n" + "{\r\n"
						+ "	pagechangea.value=\"\";\r\n"
						+ "	alert(\"输入合法数字\");\r\n" + "}\r\n"
						+ "document.getElementById('showPage').value = pagechangea.value;\r\n"
						+ "}\r\n" + "</script>\r\n";
				int last = pt.getPage().getLastPage();
				int current = pt.getPage().getShowPage();
				int begin = current;
				int end = current;
				while (end < begin + 4) {
					if (current > last) {
						end = last;
						break;
					}
					if (begin > 1)
						begin--;
					if (end < last)
						end++;
					if (begin == 1 && end == last)
						break;
				}
				out.print(table);
				out.print(hidden);
				out.print(tr);

				out.print(td_center);
				out.print(pt.getShowSummary());
				out.print("  ");
				out.print(pt.getFirstPage());
				out.print("<");

				out.print(pt.getFirstA());

				for (int i = begin; i <= end; i++) {

					out.print(pt.getThePage(i));
					if (i == current)
						out.print(" <font color='red'>");
					out.print(Integer.toString(i));
					out.print(".");
					if (i == current)
						out.print("</font> ");
					out.print(pt.getThePageEnd());

				}
				if (end != last)
					out.print("..");
				out.print(pt.getLastPage());

				out.print(">");
				out.print(pt.getLastA());
				out.print("   ");
				out.print("跳到");
				out.print("<input type='text' name='pagechangea' size='2' onchange='changepageto(pagechangea)' value='"
								+ Integer.toString(current) + "'/>");
				out.print(" ");
				out.print(pt.getThePage());
				out.print("GO");
				out.print(pt.getThePageEnd());
				out.print(td_);
				out.print(script);
				out.print(tr_);
				out.print(table_);
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return super.doEndTag();
	}

	public PageTurning getPt() throws JspException {
		PageTurning pt = null;
		PageInfo pi = null;
		pi = (PageInfo) pageContext.getAttribute(name,
				PageContext.PAGE_SCOPE);
		if (pi == null)
			pi = (PageInfo) pageContext.getAttribute(name,
					PageContext.REQUEST_SCOPE);
		if (pi == null)
			pi = (PageInfo) pageContext.getAttribute(name,
					PageContext.SESSION_SCOPE);
		if (pi == null)
			throw new JspException("Cannot find pageTurning attribute ");
		pt = new PageTurning(pi);
		return pt;
	}

}
