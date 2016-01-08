
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
    
    <title>日志查询</title>
    
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
<link href="../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%--<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
<script language="JavaScript" src="../../js/calendar.js"></script>

<script>
document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
	function select(obj)
    {
		var page = "../../sys/dep.do?method=select&value="+obj.value
		var winFeatures = "dialogHeight:500px; dialogLeft:500px;";
		
		
		window.showModalDialog(page,obj,winFeatures);
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		
		
		function query()
		{
			document.forms[0].action = "../log.do?method=search";
    		document.forms[0].target = "mainFrame";
    		document.forms[0].submit();
    		window.close();
		}	
</script>
  </head>
  
  <body>
  <br>
  <html:form action="/sys/log" target="mainFrame">
    
<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message bundle='sys' key='sys.log'/></td>
  </tr>
  <tr>
    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.log.toSearch.username'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="sysUser"></html:text>
<%--    <img  src="../../images/sysoper/particular.gif" onclick="select(sysUser)" width="16" height="16" border="0"/>    --%>
    
    </td>
    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.oper'/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="actorType">
    	<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
    	<html:options collection="typeList"
  							property="value"
  							labelProperty="label"/>
    </html:select>
    </td>
  </tr>
  
<%--  <tr>--%>
<%--    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.log.toSearch.startdate'/></td>--%>
<%--    <td class="tdbgcolorqueryleft">--%>
<%--    <html:text property="fdt" readonly="true" onfocus="calendar()"></html:text>--%>
<%--    </td>--%>
<%--    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.log.toSearch.enddate'/></td>--%>
<%--    <td class="tdbgcolorqueryleft">--%>
<%--    <html:text property="ldt" readonly="true" onfocus="calendar()"></html:text>--%>
<%--    </td>--%>
<%--  </tr>--%>
  <tr>
    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.log.toSearch.module'/></td>
    <td class="tdbgcolorqueryleft">
    <html:text property="sysModule"></html:text>
    </td>
    <td class="tdbgcolorqueryright"><bean:message bundle='sys' key='sys.property.ip'/></td>
    <td class="tdbgcolorqueryleft">
	<html:text property="ip"></html:text>
    </td>
  </tr>
   <tr>
    <td colspan="4" align="right" class="tdbgcolorquerybuttom">
<%--    <html:submit><bean:message bundle='sys' key='sys.select'/></html:submit>--%>
    <input type="button" value="查询" class="button" name="sel" onclick="query()">
    </td>
  </tr>
 
</table>
</html:form>
  </body>
</html:html>
