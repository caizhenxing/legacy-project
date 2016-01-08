
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
    
    <title><bean:message key="oa.privy.plan.title"/></title>
    
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
  <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>
  
  <script type="text/javascript" language="JavaScript">
  <!--
	function popUp( win_name,loc, w, h, menubar,center ) {
	var NS = (document.layers) ? 1 : 0;
	var editorWin;
	if( w == null ) { w = 500; }
	if( h == null ) { h = 350; }
	if( menubar == null || menubar == false ) {
		menubar = "";
	} else {
		menubar = "menubar,";
	}
	if( center == 0 || center == false ) {
		center = "";
	} else {
		center = true;
	}
	if( NS ) { w += 50; }
	if(center==true){
		var sw=screen.width;
		var sh=screen.height;
		if (w>sw){w=sw;}
		if(h>sh){h=sh;}
		var curleft=(sw -w)/2;
		var curtop=(sh-h-100)/2;
		if (curtop<0)
		{ 
		curtop=(sh-h)/2;
		}

		editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
	}
	else{
		editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
	}

	editorWin.focus(); //causing intermittent errors
}
function insertIt(){
		popUp('windows','/ETOA/oa/privy/plan.do?method=info&type=insert',480,400);
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
  // -->
</script>
  </head>
  
  <body>
  <br>
  <html:form action="/oa/privy/plan.do?method=list" target="bottomm">
    <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message key="oa.privy.plan.type"/></td>
  </tr>
<tr>
    <td  class="tdbgcolorqueryright"><bean:message key="oa.privy.plan.user"/></td>
    <td class="tdbgcolorqueryleft"><html:text property="employeeId"></html:text></td>
    <td  class="tdbgcolorqueryright"><bean:message key="oa.privy.plan.type"/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="planType">
    	<html:option value=""><bean:message bundle="sys" key="sys.pselect"/></html:option>
    	 <html:optionsCollection name="tl" label="label" value="value"/>
    </html:select>
    </td>
  </tr>
  
  <tr>
    <td  class="tdbgcolorqueryright"><bean:message key="oa.privy.plan.begintime"/></td>
    <td class="tdbgcolorqueryleft"><html:text property="beginDate" readonly="true" onfocus="calendar()"></html:text></td>
    <td  class="tdbgcolorqueryright"><bean:message key="oa.privy.plan.endtime"/></td>
    <td class="tdbgcolorqueryleft"><html:text property="endDate" readonly="true" onfocus="calendar()"></html:text></td>
  </tr>
  
  <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom"><html:submit><bean:message bundle="sys" key="sys.select"/></html:submit><html:button    property="button" onclick="insertIt()"><bean:message bundle='sys' key='sys.insert'/></html:button></td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
