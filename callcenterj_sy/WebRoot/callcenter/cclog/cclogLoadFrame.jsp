<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title></title>
</head>
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");</script>
	</logic:notEmpty>
	<%
		String logId = (String)request.getAttribute("cclogid");
		String talkid = (String)request.getAttribute("talkid");
    %>
<frameset name="dict" rows="35%,*" border="0" frameborder="0"  framespacing="0">
  <frame name="topp" scrolling="yes" src="../callcenter/cclog.do?method=toCclogLoad&type=detail&id=<%=logId%>&talkid=<%=talkid%>&count=1" noresize>
  <frame name="down" scrolling="auto" src="../html/content.jsp" noresize>
  <noframes>
  <body>��

  <p>����ҳʹ���˿�ܣ��������������֧�ֿ�ܡ�</p>

  </body>
  </noframes>
</frameset>

</html:html>
