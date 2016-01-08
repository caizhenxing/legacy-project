/*
 * Created on 2004-6-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package base.zyf.web.page.taglib;

import javax.servlet.http.HttpServletRequest;

import base.zyf.common.util.Constants;
import base.zyf.web.page.PageInfo;


// import com.hl.frame.formbean.StrutsConst;

/**
 * @author 赵一非 zhangfeng 修改 添加总记录数属性
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PageTurning {
	static String label = "<label  onClick=\"document.getElementById('showPage').value='";
	static String labelTitle = "';CurrentPage.query();\" title='";
	static String labelEnd = "'>";

	/**
	 * 
	 */
	// firstPage's url is firstPage such as <a href="/sth.do" >
	private String firstWords = "首页";

	private String previousWords = "上页";

	private String nextWords = "下页";

	private String lastWords = "末页";

	private String firstPage = "";

	private String previousPage = "";

	private String nextPage = "";

	private String lastPage = "";

	// </a>
	private String firstA = "</label>";

	private String previousA = "</label>";

	private String nextA = "</label>";

	private String lastA = "</label>";

	private String showSummary = "";


	private PageInfo page;


	private static String showPage = "showPage";

	private static String action = "action";

	// private static String action="select";
	public PageTurning() {
	}

	public PageTurning(PageInfo pageInfor) {
		this.page = pageInfor;

		if ((page.getShowPage() != 1) && (page.getPageCount() != 1)) {

			this.firstPage = doFirstPage();
			this.firstA = "</label>";

			this.previousPage = doPreviousPage();
			this.previousA = "</label>";
		}
		//
		if ((page.getShowPage() != page.getPageCount())
				&& (page.getPageCount() > 1)) {

			this.nextPage = doNextPage();
			this.nextA = "</label>";
			this.lastPage = doLastPage();
			this.lastA = "</label>";
		}
		if (page.getPageCount() > 0){
			showSummary = page.getShowPage() + "/";
			showSummary = showSummary + page.getPageCount() + "页";
			showSummary = showSummary + " 总数" + page.getRowCount() + "条";
		}

	}

	public static void main(String[] args) {
		PageTurning pf = new PageTurning();
		pf.page = new PageInfo(2, 3, 4, 5);

		//System.out.println(pf.getThePage(12));
		System.out.println(pf.getFirstA());
		 System.out.println(pf.getFirstPage());
		 System.out.println(pf.getFirstWords());
	}

	private String addUrlParameter(String url, String name, String value) {
		int i = url.indexOf("?");
		if (i == -1) {
			return (url + "?" + name + "=" + value);
		} else {
			return (url + "&" + name + "=" + value);
		}
	}

	private String getUrl(String url) {
		String sTmp = new String();
		sTmp += "<a href=\"";
		sTmp += url;
		sTmp += "\">";
		return sTmp;
	}

	private String getUrl(String url, String name) {
		String sTmp = new String();
		sTmp += "<a name=\"" + name + "\" " + "href=\"";
		sTmp += url;
		sTmp += "\">";
		return sTmp;
	}

	/**
	 * @return
	 */
	private String doFirstPage() {
		StringBuilder url = new StringBuilder();
		url.append(label);
		url.append(page.getFirstPage());
		url.append(labelTitle);
		url.append(this.firstWords);
		url.append(labelEnd);
		
		return url.toString();
	}

	/**
	 * @return
	 */
	private String doLastPage() {
		StringBuilder url = new StringBuilder();
		url.append(label);
		url.append(page.getLastPage());
		url.append(labelTitle);
		url.append(this.lastWords);
		url.append(labelEnd);
		
		return url.toString();
	}

	/**
	 * @return
	 */
	private String doNextPage() {
		StringBuilder url = new StringBuilder();
		url.append(label);
		url.append(page.getNextPage());
		url.append(labelTitle);
		url.append(this.nextWords);
		url.append(labelEnd);
		
		return url.toString();
	}

	/**
	 * @return
	 */
	private String doPreviousPage() {
		StringBuilder url = new StringBuilder();
		url.append(label);
		url.append(page.getPrePage());
		url.append(labelTitle);
		url.append(this.previousWords);
		url.append(labelEnd);
		
		return url.toString();
	}

	/**
	 * @return
	 */
	public String getFirstA() {
		return firstA;
	}

	/**
	 * @return
	 */
	public String getLastA() {
		return lastA;
	}

	/**
	 * @return
	 */
	public String getNextA() {
		return nextA;
	}

	/**
	 * @return
	 */
	public String getPreviousA() {
		return previousA;
	}

	/**
	 * @return
	 */
	public String getLastPage() {
		return lastPage;
	}

	/**
	 * @return
	 */
	public String getNextPage() {
		return nextPage;
	}

	/**
	 * @return
	 */
	public String getPreviousPage() {
		return previousPage;
	}

	/**
	 * @return
	 */
	public String getFirstPage() {
		return firstPage;
	}

	/**
	 * @return
	 */
	public String getShowSummary() {
		return showSummary;
	}

	/**
	 * @return
	 */
	public String getFirstWords() {
		return firstWords;
	}

	/**
	 * @return
	 */
	public String getLastWords() {
		return lastWords;
	}

	/**
	 * @return
	 */
	public String getNextWords() {
		return nextWords;
	}

	/**
	 * @return
	 */
	public String getPreviousWords() {
		return previousWords;
	}

	public PageInfo getPage() {
		return page;
	}

	public String getThePage(int i) {
		StringBuilder url = new StringBuilder();
		url.append(label);
		url.append(i);
		url.append(labelTitle);
		url.append("翻页到："+i);
		url.append(labelEnd);
		
		return url.toString();
	}

	public String getThePage() {
		StringBuilder url = new StringBuilder();
		url.append("<label title='翻页' onClick = 'CurrentPage.query();'>");
		
		return url.toString();
	}

	

	public String getThePageEnd() {
		return "</label>";
	}



	public void setPage(PageInfo page) {
		this.page = page;
	}
}
