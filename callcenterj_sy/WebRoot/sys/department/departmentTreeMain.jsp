<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>departmentTreeMain.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<frameset cols="33%,*" border="0" frameborder="0"  framespacing="0">
  <html:frame frameName="topp" page="/sys/department/deptTree.do?method=loadDeptTree" />
  <html:frame frameName="operationframeTree" page="/sys/content.jsp" />
  
  <noframes>
  <body>

  <p><bean:message bundle="sys" key="sys.frame.notsupport"/></p>

  </body>
  </noframes>
</html:html>
