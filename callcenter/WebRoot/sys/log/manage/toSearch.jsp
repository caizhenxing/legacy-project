
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

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
<%--<script language="JavaScript" src="../../../js/calendar.js"></script>--%>
<script>
	function s()
	{
		document.forms[0].action="/callcenter/sys/log/LogOper.do?method=search";
    	document.forms[0].submit();
	}
	function a()
		{
			window.open("/callcenter/sys/user/UserOper.do?method=toAdd&type=i","a");
		}
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}	
</script>
  </head>
  
  <body bgcolor="#eeeeee">
  <br>
  <html:form action="/sys/log/LogOper" target="mainFrame">
    
<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message key="sys.current.page"/><bean:message key='hl.bo.sys.log'/></td>
  </tr>
  <tr>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.sys.log.toSearch.username'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="sysUser"></html:text></td>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.asset.oper'/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="actorType">
    	<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    	<html:optionsCollection name="logtype" label="label" value="value"/>
    </html:select>
    </td>
  </tr>
  
  <tr>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.sys.log.toSearch.startdate'/></td>
    <td class="tdbgcolorqueryleft">
    <html:text property="fdt" readonly="true"></html:text>
    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(fdt)"/>
    </td>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.sys.log.toSearch.enddate'/></td>
    <td class="tdbgcolorqueryleft">
    <html:text property="ldt" readonly="true"></html:text>
    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(ldt)"/>
    </td>
  </tr>
   <tr>
    <td colspan="4" align="right" class="tdbgcolorquerybuttom">
    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.chanel'/>" />&nbsp;&nbsp;
    <input name="Submit" type="button" class="bottom" value="<bean:message key='agrofront.common.search'/>" onclick="s()"/>
    </td>
  </tr>
 
</table>
</html:form>
  </body>
</html:html>
