<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>

<%
	String qid = request.getParameter("qid");
	String table = request.getParameter("table"); 
	String p = "closepage.jsp";
	if(table.equals("sad")){
		table = "oper_sadinfo";
		p = "../sad.do?method=toSadLoad&type=detail&id=";
	}else if(table.equals("price")){
		table = "oper_priceinfo";
		p = "../operpriceinfo.do?method=toOperPriceinfoLoad&type=detail&id=";
	
	}
	String sql = "select * from "+ table +" where question_id = '"+ qid +"'";
	String id = "";
	
	ApplicationContext ac = (ApplicationContext) application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
	BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	try {
		conn = bds.getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(sql);
		if(rs.next()){
			id = rs.getString(1);
			p += id;
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
	response.sendRedirect(p);
%>
