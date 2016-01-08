<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title></title>
</head>
<frameset rows="100,*,25" frameborder="no" border="0" framespacing="0">
	<FRAME SRC="telConferenceTop.jsp" NAME="TelConference_top" scrolling="no">
		<FRAME SRC="telConferenceRight.html" NAME="TelConference_right" scrolling="No" noresize="true">
<%--		<FRAME SRC="../callcenter/conf.do?method=toConfList" NAME="TelConference_right">--%>	
	 <noframes>
  <body>　

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
   <html:frame frameName="bottomFrame" page="/sys/buttom.jsp"  scrolling="No" noresize="true"  title="bottomFrame" />
</frameset>
</html:html>