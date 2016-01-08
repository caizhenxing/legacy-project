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

	String svalue = gbk(request.getParameter("svalue"));//选取的值
	String sel = "";
	if(!svalue.equals("")){
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select distinct type2 from oper_type where type1 = '"+svalue+"' order by type2");
			while(rs.next()){
				String s = rs.getString(1);
				sel += "<option value='"+s+"'>"+ s +"</option>";
			}
			out.print(sel);
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
	}
%>