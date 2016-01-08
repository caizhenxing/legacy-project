<%@ page language="java" import="java.util.*" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="et.bo.sys.login.bean.UserBean" %>

<html>
  <head>
    <%
   		UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USER_IN_SESSION);
    	String workId = ub.getUserId();
    	if(ub == null)
    	{
    		workId = "";
    	}
    %>
    <title>×ùÏ¯Íâºô [¹¤ºÅ(<%=workId %>)]</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <frameset name="dict" rows="50,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="userInfo.do?method=toCustinfoQuery" noresize>
	<frame name="bottomm" scrolling="no" src="../html/content.jsp" title="bottomm" noresize>
  </frameset>
</html>
