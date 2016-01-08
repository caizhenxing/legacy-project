
<%@ page 
language="java"
import="java.util.*"
contentType="text/html; charset=GBK"
pageEncoding="GBK"
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/newtree.tld" prefix="newtree" %>


<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title><bean:message key="sys.module.title"/></title>
</head>
<frameset cols="33%,*" border="0" frameborder="0"  framespacing="0">
  <html:frame frameName="topp" page="/sys/module/tree.jsp" />
  <html:frame frameName="operationframe" page="/sys/welcome.html" />
  
  <noframes>
  <body>

  <p><bean:message key="sys.frame.notsupport"/></p>

  </body>
  </noframes>
</frameset>
</html>