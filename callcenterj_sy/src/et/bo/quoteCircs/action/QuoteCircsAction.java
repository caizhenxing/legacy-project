/* 包    名：et.bo.quoteCircs.action
 * 文 件 名：QuoteCircsAction.java
 * 注释时间：2008-7-9 13:44:37
 * 版权所有：沈阳市卓越科技有限公司。
 */

package et.bo.quoteCircs.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.quoteCircs.service.QuoteCircsService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * The Class QuoteCircsAction.
 * 
 * @author NieYuan
 */
public class QuoteCircsAction extends BaseAction {
	
	static Logger log = Logger.getLogger(QuoteCircsAction.class.getName());
	
	private QuoteCircsService quoteCircsService = null;

	public QuoteCircsService getQuoteCircsService() {
		return quoteCircsService;
	}

	public void setQuoteCircsService(QuoteCircsService quoteCircsService) {
		this.quoteCircsService = quoteCircsService;
	}

	/**
	 * To main.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		return map.findForward("main");
		
	}


	/**
	 * To query.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		List user = quoteCircsService.userQuery("select cust_name from oper_custinfo where dict_cust_type = 'SYS_TREE_0000002108'");
		request.setAttribute("user", user);
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int nowMonth = calendar.MONTH;
		if(nowMonth == 1 || nowMonth == 2 || nowMonth == 3){
			dto.set("beginTime", sdf.format(calendar.getTime())+"-1-1");
			dto.set("endTime", sdf.format(calendar.getTime())+"-3-31");
		} else if(nowMonth == 4 || nowMonth == 5 || nowMonth == 6){
			dto.set("beginTime", sdf.format(calendar.getTime())+"-4-1");
			dto.set("endTime", sdf.format(calendar.getTime())+"-6-30");
		} else if(nowMonth == 7 || nowMonth == 8 || nowMonth == 9){
			dto.set("beginTime", sdf.format(calendar.getTime())+"-7-1");
			dto.set("endTime", sdf.format(calendar.getTime())+"-9-30");
		} else if(nowMonth == 10 || nowMonth == 11 || nowMonth == 12){
			dto.set("beginTime", sdf.format(calendar.getTime())+"-10-1");
			dto.set("endTime", sdf.format(calendar.getTime())+"-12-31");
		}
		
		request.setAttribute(map.getName(), dto);
		return map.findForward("query");
		
	}

	/**
	 * To list.
	 * 
	 * @param map the map
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the action forward
	 */
	public ActionForward toList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){

		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("CircsQueryTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(19);
/////////////////////////////////////////////////////////下面开始时间计算
        try{
        	java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    	Calendar calendar = Calendar.getInstance();
	    	
	        String beginTime = (String)dto.get("beginTime");
	        String endTime = (String)dto.get("endTime");
	        
	        if(beginTime == null || beginTime.equals("")){
	        	beginTime = getBeginTime();
	        }
	        if(endTime == null || endTime.equals("")){
	        	endTime = df.format(new Date());
	        }
	        
	        dto.set("beginTime", beginTime);
	        dto.set("endTime", endTime);
	        
	        Date beginDate = df.parse(beginTime);
	        Date endDate = df.parse(endTime);
	        
	        calendar.setTime(beginDate);
	        int beginTimeWeek = calendar.get(calendar.DAY_OF_WEEK);
	        calendar.add(Calendar.DATE, - beginTimeWeek + 2);
	        beginDate = calendar.getTime();//System.out.println(beginDate.toLocaleString());
	        
	        calendar.setTime(endDate);
	        int endTimeWeek = calendar.get(calendar.DAY_OF_WEEK);
	        
	        List titleList = new ArrayList();
	        calendar.add(Calendar.DATE, - endTimeWeek + 2);	//此时已经是当前周一的天数
	        titleList.add(df.format(calendar.getTime()));	//本神先把这周一存进去

	        while(endDate.after(beginDate)){	//System.out.println(beginDate.toLocaleString());System.out.println(endDate.toLocaleString());					//当前日期大于beginDate
	        	calendar.add(Calendar.DATE, -7);			//给我向前推上一个周一
	        	endDate = calendar.getTime();
	        	titleList.add(df.format(endDate));
	        }
	        
	        request.setAttribute("titleList", titleList);
	        
        }catch(Exception e){
    		System.err.println(e);
    	}
        ////////////////////////////////////////////////////////////////
        List list = quoteCircsService.quoteCircsQuery(dto,pageInfo);
        int size = quoteCircsService.getQuoteCircsSize();
       
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"",map,request);
        request.getSession().setAttribute("CircsQueryTurning",pt);
        
		return map.findForward("list");
	}
	
	public String getBeginTime(){
		String beginTime = "";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ApplicationContext ac = (ApplicationContext) getServlet().getServletContext().getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select min(addtime) as addtime from oper_priceinfo where cust_id <> ''";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				beginTime = rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return beginTime;
	}
	

}
