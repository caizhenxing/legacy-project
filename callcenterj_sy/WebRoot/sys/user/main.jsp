<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

 <%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title></title>
</head>
<frameset name="dict" rows="10%,*" border="0" frameborder="0"  framespacing="0">
<%--  <html:frame  page="/sys/user/UserOper.do?method=popUser"  frameName="topFrame" scrolling="No"  noresize="true"/>--%>
  <html:frame  page="/sys/user/UserOper.do?method=toSearch"  frameName="topFrame" scrolling="No"  noresize="true"/>
  <html:frame page="/sys/user/UserOper.do?method=userLoginList" frameName="mainFrame" scrolling="No"  noresize="true"/>
<%--  <html:frame page="/sys/content.jsp" frameName="mainFrame" scrolling="No"  noresize="true"/>--%>
  <noframes>
  <body>

  <p><bean:message bundle="sys" key="sys.frame.notsupport"/></p>

  </body>
  </noframes>
</frameset>
</html:html>

