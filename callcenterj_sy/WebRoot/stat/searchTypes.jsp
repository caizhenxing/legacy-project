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
	

	String ssvalue = gbk(request.getParameter("ssvalue"));//选取的值

	String sql = "select distinct dict_product_type2 from oper_priceinfo where dict_product_type1 = '"+ssvalue+"'";
	System.out.println(sql);	
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
