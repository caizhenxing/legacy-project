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
		out.println("<option value=\"no\" selected>����������</option>");
		out.println("<option value=\"yes\">���ύ�����</option>");
	}else{
		out.println("<option value=\"no\">����������</option>");
		out.println("<option value=\"yes\" selected>���ύ�����</option>");
		b = true;
	}
	out.println("</select>");
	
	out.println("<select name=\"type\" id=\"type\" class=\"selectStyle\" onchange=\"changeType(this)\">");

	if(b || isAdmin(userId)){	//����ǹ���Ա���߲鿴���ύ�����ʱ������ʾȫ����
	//if(false){
		out.println("<option value=''>ȫ��</option>");
		out.println("<option>��ͨ������</option>");
		out.println("<option>���㰸����</option>");
		out.println("<option>���ﰸ����</option>");
		out.println("<option>Ч��������</option>");
		out.println("<option>ũ��Ʒ�����</option>");
		out.println("<option>ũ��Ʒ�۸��</option>");
		out.println("<option>����׷�ٿ�һ��</option>");
		out.println("<option>����׷�ٿ�����</option>");
		out.println("<option>����׷�ٿ�����</option>");
		out.println("<option>�г�������һ��</option>");
		out.println("<option>�г����������</option>");
		out.println("<option>�г�����������</option>");
		out.println("<option>��ҵ��Ϣ��</option>");
		out.println("<option>��ͨҽ�Ʒ�����Ϣ��</option>");
		out.println("<option>ԤԼҽ�Ʒ�����Ϣ��</option>");
		out.println("<option>�����ʾ���ƿ�</option>");
		out.println("<option>������Ϣ������</option>");
	
	}else{
		
		if(auditing != null && !auditing.equals("")){
			out.println("<option value='"+ auditing +"'>ȫ��</option>");
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
		if(value.indexOf("��") != -1){
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
