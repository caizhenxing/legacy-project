
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="sys.module.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript">

</script>

  </head>
  
  
  <body bgcolor="#eeeeee">
  <html:form action="/sys/dep.do?method=delete"  method="post" target="contents">
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key="sys.dep.title"/></td>
  </tr>
  <html:hidden property="id"/>
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <html:submit onclick="window.close()"><bean:message key="sys.delete"/></html:submit>
    
</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
