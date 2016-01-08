<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>
<%!
public String gbk(String s){
	try{
		s = new String(s.getBytes("ISO-8859-1"),"utf-8");
		return s;
	}catch(Exception e){
		return "";							//如果s为null则返回空串
	}
}
%>
<%
	ApplicationContext ac = (ApplicationContext) application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
	BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String sid = gbk(request.getParameter("sid"));//选取的框
	String svalue = gbk(request.getParameter("svalue"));//选取的值
	
	String sql = "select distinct name1 from oper_product order by 1";

	if(sid.equals("dictProductType1")){
		sql = "select distinct name2 from oper_product where name1 = '"+ svalue +"' order by 1";
	}else if(sid.equals("dictProductType2")){
		sql = "select distinct name3 from oper_product where name2 = '"+ svalue +"' order by 1";
	}
		
	String ssid = gbk(request.getParameter("ssid"));//选取的框
	String ssvalue = gbk(request.getParameter("ssvalue"));//选取的值

	if(ssid.equals("dict_product_type1")){
		sql = "select distinct name2 from oper_product where name1 = '"+ ssvalue +"' order by 1";
	}else if(ssid.equals("dict_product_type2")){
		sql = "select distinct name3 from oper_product where name2 = '"+ ssvalue +"' order by 1";
	}
		
	try {
		conn = bds.getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);
		String str = "";
		while(rs.next()){
			String s = rs.getString(1);
			str += "<option value='"+s+"'>"+ s +"</option>";
		}
		out.print(str);
	
	} catch (Exception e) {
		System.err.println(e);
	} finally {
		try {
			if (rs != null)		rs.close();
			if (stmt != null)	stmt.close();
			if (conn != null)	conn.close();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

%>
