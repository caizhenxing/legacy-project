<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title></title>
</head>
<frameset name="dict" rows="15%,*" border="0" frameborder="0"  framespacing="0">
  <frame name="topp"scrolling="no" src="operCorpinfo.do?method=toOperCorpinfoQuery&state=<%=request.getParameter("state")%>" noresize>
  <frame name="bottomm" scrolling="no" src="operCorpinfo.do?method=toOperCorpinfoList&state=<%=request.getParameter("state")%>" noresize>
  <noframes>
  <body>

  <p>����ҳʹ���˿�ܣ��������������֧�ֿ�ܡ�</p>

  </body>
  </noframes>
</frameset>

</html>
