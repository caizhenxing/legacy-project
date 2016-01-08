<%@ page language="java" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>×ùÏ¯¶Ë¹¦ÄÜÒ³</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  	<frameset rows="24,*" frameborder="no" border="0" framespacing="0" margwidth="0">
		  	<frame name="top" src="./sys/agentNavControl/agentNav.jsp" scrolling="No"  noresize="true" title="mid"/>
			<frame name="bottom" src="./html/content.jsp" />
	</frameset>
	<noframes>
	  <body>
	  </body>
    </noframes>
</html>
