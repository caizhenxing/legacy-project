<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>list.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<frameset cols="20%,*" border="0" frameborder="0"  framespacing="0">
  <html:frame frameName="topp" page="/sys/tree.jsp" />
  <html:frame frameName="operationframe" page="/sys/content.jsp" />
  
  <noframes>
  <body>

  <p><bean:message bundle="sys" key="sys.frame.notsupport"/></p>

  </body>
  </noframes>
</html:html>
<%--    <a href="sys/staff/workUnitInfo.do?method=toWorkUnitInfoMain" target="_blank">员工操作</a>--%>
<%--    <br>--%>
<%--    <a href="sys/group/groupMain.jsp" target="_blank">部门操作</a>--%>
<%--    <br>--%>
<%--    <a href="sys/role/roleManagerMain.jsp" target="_blank">角色操作</a>--%>
<%--    <br>--%>
<%--    <a href="sys/user/main.jsp" target="_blank">用户管理</a>--%>
<%--</html>--%>
