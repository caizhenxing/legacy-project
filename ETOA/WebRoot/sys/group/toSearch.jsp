
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
<script language="javascript" src="../../js/tools.js"></script>
<script>
	function s()
	{
		document.forms[0].action+="?method=search";
    	document.forms[0].submit();
	}
	function a()
		{
			popUp('windows',"GroupOper.do?method=toLoad&type=i",600,200);
		}
</script>
  </head>
  
  <body>
  <br>
  <html:form action="/sys/group/GroupOper?method=search" target="mainFrame">
    <table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message bundle='sys' key='sys.group'/></td>
  </tr>


  <tr>
    <td  class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.group.info.groupname'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="name"></html:text></td>
   
  </tr>
  <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom">
    <html:submit><bean:message bundle='sys' key='sys.select'/></html:submit>
	<html:button property="ins" onclick="a()"><bean:message bundle='sys' key='sys.insert'/></html:button>
	</td>
  </tr>
  
</table>
</html:form>
  </body>
</html:html>
