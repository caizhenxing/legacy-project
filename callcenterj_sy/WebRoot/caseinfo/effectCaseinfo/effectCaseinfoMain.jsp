<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>效果案例库信息</title>
</head>
<frameset name="dict" rows="15%,*" border="0" frameborder="0"  framespacing="0">
  <frame name="topp" scrolling="no" src="effectCaseinfo.do?method=toEffectCaseinfoQuery&state=<%=request.getParameter("state")%>" noresize>
  <frame name="bottomm" scrolling="no" src="../html/content.jsp" noresize>
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
</frameset>

</html>
