<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>ְ����ز���</title>
</head>
<% 
	String type = request.getAttribute("type").toString();
	String id = request.getAttribute("id").toString();
%>


<frameset name="dict" rows="55%,*" border="0" frameborder="0"  framespacing="0">
  
  <frame name="topp" src="staffBasic.do?method=toStaffBasicLoad&type=<%=type%>&id=<%=id%>" noresize>
  <frame name="bottomm" src="../../html/content.html" noresize>
  <noframes>
  <noframes>
  <body>

  <p>����ҳʹ���˿�ܣ��������������֧�ֿ�ܡ�</p>

  </body>
  </noframes>
</frameset>

</html>
