<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>来电屏蔽管理</title>
</head>
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");</script>
	</logic:notEmpty>
<frameset name="dict" rows="11%,*" border="0" frameborder="0"  framespacing="0">
  <frame name="topp" scrolling="no" src="callinFirewall.do?method=toCallinFireWallQuery" noresize>
   <frame name="bottomm" scrolling="no" src="callinFirewall.do?method=toCallinFireWallList" noresize>
<%--  <frame name="bottomm" src="../html/content.jsp" noresize>--%>
  <noframes>
  <body>　

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
</frameset>

</html:html>
