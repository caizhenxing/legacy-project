/*
 * Created on 2004-6-8
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ocelot.common.page;


import javax.servlet.http.HttpServletRequest;

import ocelot.common.util.Constants;

import org.apache.struts.action.ActionMapping;


//import com.hl.frame.formbean.StrutsConst;

/**
 * @author 赵一非
 * 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PageTurning {

	/**
	 * 
	 */
	//firstPage's url is firstPage such as <a href="/sth.do" >
	
	private String firstWords="首页"; 
	private String previousWords="上页";
	private String nextWords="下页";
	private String lastWords="末页";
	
	private String firstPage=""; 
	private String previousPage="";
	private String nextPage="";
	private String lastPage="";
	//</a>
	private String firstA=""; 
	private String previousA="";
	private String nextA="";
	private String lastA="";
	
	private String showSummary="";
	//input argument
	private ActionMapping actionMapping;
	private PageInfo page;
	
	private String basicUrl="";
	
	private static String showPage="showPage";
	private static String action="action";
	
	
	//private static String action="select";
	public PageTurning() {
	}
	
	public PageTurning(PageInfo pageInfor,String proj,ActionMapping actionMapping,HttpServletRequest request) {
		proj="/"+Constants.getProperty("project_name")+"/";
		this.page = pageInfor;
		this.actionMapping = actionMapping;
		this.basicUrl =proj;
		this.basicUrl+= actionMapping.getModuleConfig().getPrefix();
		
		this.basicUrl+= actionMapping.getPath()+".do";
		
		this.basicUrl = addUrlParameter(this.basicUrl,
				actionMapping.getParameter()
										,
										request.getParameter(actionMapping.getParameter()));
		
		
		if ((page.getShowPage()!=1)&&(page.getPageCount()!=1)){
			
			this.firstPage = doFirstPage();
			this.firstA ="</a>";
			
			this.previousPage = doPreviousPage();
			this.previousA = "</a>";
		}
		//
		if ((page.getShowPage()!=page.getPageCount())&&(page.getPageCount()>1)){
			
			this.nextPage = doNextPage();
			this.nextA = "</a>";
			this.lastPage = doLastPage();
			this.lastA = "</a>";
		}
		if(page.getPageCount()>0)
		showSummary = "第"+page.getShowPage()+"页/";
		showSummary = showSummary+"共"+page.getPageCount()+"页";
	}
	
	public static void main(String[] args) {
		PageTurning pf=new PageTurning();
		pf.basicUrl="/abc.do";
		pf.page=new PageInfo(2,3,4,5);

		System.out.println(pf.getThePage(12));
		System.out.println(pf.getThePageEnd());
		//System.out.println(pf.getNextPage());
		//System.out.println(pf.getLastPage());
	}
	
	private String addUrlParameter(String url,String name,String value){
		int i = url.indexOf("?");
		if(i==-1){
			return(url+"?"+name+"="+value);
		}else{
			return(url+"&"+name+"="+value);
		}
	}
	private String getUrl(String url){
		String sTmp=new String();
		sTmp += "<a href=\"";
		sTmp += url;
		sTmp += "\">";
		return sTmp;
	}
	private String getUrl(String url,String name){
		String sTmp=new String();
		sTmp += "<a name=\"" +name+"\" "+
				"href=\"";
		sTmp += url;
		sTmp += "\">";
		return sTmp;
	}
	/**
	 * @return
	 */
	private String doFirstPage() {
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
							StrutsConst.IDUS,
							StrutsConst.IDUS_S);*/
		url =addUrlParameter(url,"pagestate",Integer.toString(page.getFirstPage()));
		return getUrl(url);
	}

	/**
	 * @return
	 */
	private String doLastPage() {
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
url =addUrlParameter(url,"pagestate",Integer.toString(page.getLastPage()));
		return getUrl(url);
	}

	/**
	 * @return
	 */
	private String doNextPage() {
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
url =addUrlParameter(url,"pagestate",Integer.toString(page.getNextPage()));
		return getUrl(url);
	}

	/**
	 * @return
	 */
	private String doPreviousPage() {
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
url =addUrlParameter(url,"pagestate",Integer.toString(page.getPrePage()));
return getUrl(url);
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
	public String getThePage(int i)
	{
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
		url =addUrlParameter(url,"pagestate",Integer.toString(i));
		return getUrl(url);
	}
	public String getThePage(String name)
	{
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
		url =addUrlParameter(url,"pagestate","");
		
		return getUrl(url,name);
	}
	public String getThePage(String name,String value)
	{
		String url=this.basicUrl;
		/*url =addUrlParameter(url,
		StrutsConst.IDUS,
		StrutsConst.IDUS_S);*/
		url =addUrlParameter(url,name,value);
		
		return getUrl(url);
	}
	public String getThePageEnd()
	{
		return "</a>";
	}

	

	public ActionMapping getActionMapping() {
		return actionMapping;
	}

	public void setActionMapping(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}
}
