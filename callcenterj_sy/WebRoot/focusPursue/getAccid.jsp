<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.apache.commons.dbcp.BasicDataSource" %>
<%

String state = request.getParameter("state");
String group = "";
if(state.equals("state1")){
	group = "三级管理员";
}else if(state.equals("state2")){
	group = "二级管理员";
}else if(state.equals("state3")){
	group = "一级管理员";
}else{
	group = "全部";
}
if(!state.equals("waitCensor")){//如果不是待审
	out.print("");
	return;
}

ApplicationContext ac = (ApplicationContext)application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
		
		String str = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT sys_user.user_id FROM dbo.sys_user INNER JOIN sys_group ON sys_user.group_id = sys_group.Id WHERE sys_group.name = '" + group + "'";
			if(group.equals("全部")){
				sql = "SELECT sys_user.user_id FROM dbo.sys_user INNER JOIN sys_group ON sys_user.group_id = sys_group.Id WHERE sys_group.name = '一级管理员' or sys_group.name = '二级管理员' or sys_group.name = '三级管理员' or sys_group.name = '系统管理员'";
			}
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				str += "<option value='"+ rs.getString("user_id") +"'>"+ rs.getString("user_id") +"</option>";
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

if(!str.equals("")){
	str = "<select name='accid' class='selectStyle' onchange='if(document.forms[0].accept_id)document.forms[0].accept_id.value=this.options[this.selectedIndex].value'><option value='admin'>选择受理人ID</option>"+ str +"</select>";
}

out.print(str);

%>