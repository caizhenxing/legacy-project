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
<frameset name="dict" rows="0%,100%" border="0" frameborder="0"  framespacing="0">
  <frame name="topp" src="/ETOA/oa/privy/addressListSort.do?method=toAddressListSortQuery&sortSet=common" noresize>
  <frame name="bottomm" src="/ETOA/oa/privy/addressListSort.do?method=toAddressListSortList&sortSet=common" noresize>
  <noframes>
  <body>　
  <p><bean:message key="agrofront.oa.assissant.hr.hrManagerMain"/></p>
<logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");window.close();</script>
</logic:notEmpty>
  </body>
  </noframes>
</frameset>

</html:html>
