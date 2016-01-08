package base.zyf.web.page;


/**
 * 
 * @author zhao yifei
 * 
 */
public class PageInfo {
	private int pageSize = 10; // 每一页记录数

	private int showPage = 1; // 初始化显示页

	// 与记录有关的
	private int rowCount = 0; // 总记录数

	private int pageCount = 1; // 初始化总页数

	private Object ql = null;

	/**
	 * 排序属性名
	 */
	public final static String orderbyasc = "orderbyasc";

	public final static String orderbydesc = "orderbydesc";

	public final static String pagestate = "pagestate";

	public final static String first = "first";

	public final static String last = "last";

	public final static String next = "next";

	public final static String pre = "pre";

	public final static String now = "now";

	private String fieldAsc = null;

	private String fieldDesc = null;

	public String getFieldAsc() {
		return fieldAsc;
	}

	public void setFieldAsc(String fieldAsc) {
		this.fieldAsc = fieldAsc;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	

	public PageInfo() {
	}

	public PageInfo(String showPage) {

	}

	public PageInfo(int pageSize, int showPage, int rowCount, int pageCount) {
		this.pageSize = pageSize;
		this.showPage = showPage;
		this.rowCount = rowCount;
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {

		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		int page = rowCount / pageSize;
		if (rowCount % pageSize != 0)
			page++;
		this.rowCount = rowCount;
		this.pageCount = page;
	}

	public int getShowPage() {
		int page = this.getRowCount()/this.getPageSize();
		if(this.showPage > page + 1)
		{
			this.setShowPage(page + 1);
		}
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public void setState(String pagestate) {

		if (pagestate == null)
			return;
		if (pagestate.equals(next)) {
			if (this.getShowPage() < this.getPageCount())
				this.setShowPage(this.getShowPage() + 1);
		}
		if (pagestate.equals(pre)) {
			if (this.getShowPage() > 1)
				this.setShowPage(this.getShowPage() - 1);
		}
		if (pagestate.equals(first)) {
			this.setShowPage(1);
		}
		if (pagestate.equals(last)) {
			this.setShowPage(this.getPageCount());
		}
		if (pagestate.equals(now)) {

		}
		try {
			int i = Integer.parseInt(pagestate);
			if (i <= this.getLastPage() && i > 0)
				this.setShowPage(i);
			if (i > this.getLastPage() && i > 0)
				this.setShowPage(this.getLastPage());
			if (i == 0)
				return;
		} catch (java.lang.NumberFormatException ne) {

		}

	}

	public int getNextPage() {
		if (showPage < pageCount)
			return showPage + 1;
		else
			return pageCount;
	}

	public int getPrePage() {
		if (showPage > 1)
			return showPage - 1;
		else
			return 1;
	}

	public int getFirstPage() {
		return 1;
	}

	public int getLastPage() {
		return pageCount;
	}

	public static void main(String[] arg0) {
		PageInfo pi = new PageInfo();
		pi.pageCount = 11;
		pi.setState("12");
		System.out.println(pi.getShowPage());
	}

	public int getBegin() {
		int begin = 0;
		begin = (this.getShowPage() - 1) * this.pageSize;
		return begin;
	}

}
