
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>toSearch.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script>
	function s()
	{
		document.forms[0].action="/ETforum/sys/group/GroupOper.do?method=search";
    	document.forms[0].submit();
	}
	function a()
		{
			window.open("/ETforum/sys/group/GroupOper.do?method=toLoad&type=i","a");
		}
</script>
  </head>
  
  <body bgcolor="#eeeeee">
  <br>
  <html:form action="/sys/group/GroupOper" target="mainFrame" onsubmit="s()">
    <table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message key="sys.current.page"/><bean:message key='hl.bo.sys.group'/></td>
  </tr>


  <tr>
    <td  class="tdbgcolorqueryright"><bean:message key='hl.bo.sys.group.info.groupname'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="name"></html:text></td>
   
  </tr>
  <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom">
    <input name="Submit" type="submit" class="bottom" value="<bean:message key='agrofront.common.search'/>"/>
	<input name="addgov" type="button" class="buttom" value="<bean:message key='agrofront.common.insert'/>" onClick="a()"/>
	</td>
  </tr>
  
</table>
</html:form>
  </body>
</html:html>
