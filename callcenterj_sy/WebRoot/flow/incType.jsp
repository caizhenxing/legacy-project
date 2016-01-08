<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.*"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="excellence.framework.base.container.SpringRunningContainer"%>
<%
	Map infoMap = (Map) request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
	String userId = (String) infoMap.get("userId");
	
	String sub = request.getParameter("sub");
	boolean b = false;
	
	out.println("<select name=\"sub\" id=\"sub\" class=\"selectStyle\" onchange=\"changeSub(this)\">");
	if(sub == null || sub.equals("") || sub.equals("no")){
		out.println("<option value=\"no\" selected>我受理的审核</option>");
		out.println("<option value=\"yes\">我提交的审核</option>");
	}else{
		out.println("<option value=\"no\">我受理的审核</option>");
		out.println("<option value=\"yes\" selected>我提交的审核</option>");
		b = true;
	}
	out.println("</select>");
	
	out.println("<select name=\"type\" id=\"type\" class=\"selectStyle\" onchange=\"changeType(this)\">");

	if(b || isAdmin(userId)){	//如果是管理员或者查看我提交的审核时，才显示全部的
	//if(false){
		out.println("<option value=''>全部</option>");
		out.println("<option>普通案例库</option>");
		out.println("<option>焦点案例库</option>");
		out.println("<option>会诊案例库</option>");
		out.println("<option>效果案例库</option>");
		out.println("<option>农产品供求库</option>");
		out.println("<option>农产品价格库</option>");
		out.println("<option>焦点追踪库一审</option>");
		out.println("<option>焦点追踪库三审</option>");
		out.println("<option>焦点追踪库三审</option>");
		out.println("<option>市场分析库一审</option>");
		out.println("<option>市场分析库二审</option>");
		out.println("<option>市场分析库三审</option>");
		out.println("<option>企业信息库</option>");
		out.println("<option>普通医疗服务信息库</option>");
		out.println("<option>预约医疗服务信息库</option>");
		out.println("<option>调查问卷设计库</option>");
		out.println("<option>调查信息分析库</option>");
	
	}else{
		
		if(auditing != null && !auditing.equals("")){
			out.println("<option value='"+ auditing +"'>全部</option>");
			String[] auditings = auditing.split(",");
			for(int i = 0; i < auditings.length; i++){
				out.println("<option>"+ auditings[i] +"</option>");
			}
		}
	}
	
	out.println("</select>");
%>
<script>
	function changeSub(obj){
		var value = obj.options[obj.selectedIndex].value;
		var link = document.location.href;
		document.location = link.substring(0,link.indexOf("toFlowQuery")+11) + "&sub=" + value;
	}
	
	function changeType(obj){
		var value = obj.options[obj.selectedIndex].text;
		if(value.indexOf("审") != -1){
			var valueType = value.substring(0, 5);
			var stateType = value.substring(5, 7);
			obj.options[obj.selectedIndex].value = valueType;
			
			var stateObj = document.getElementById("state");
			for(var i = 0; i < stateObj.length; i++){
				if(stateObj.options[i].text == stateType){
					stateObj.options[i].selected = true;  
					break;
				}
			}
		}
	}
	
</script>
<%!	
	String auditing = "";
	public boolean isAdmin(String userId){
	
		boolean b = false;
		BasicDataSource bds = (BasicDataSource) SpringRunningContainer.getInstance().getBean("datasource");
			
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select * from sys_user where user_id = '"+ userId +"'");
			if(rs.next()){
				auditing = rs.getString("auditing");	
				String group_id = rs.getString("group_id");
				if( group_id != null && group_id.equals("administrator") ){
					b = true;
				}
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
		
		return true;
	}

%>
