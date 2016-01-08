<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
      	//
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>

  </head>
  
  <body bgcolor="#eeeeee" class="listBody">
    <html:form action="/callcenter/appraise" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle" width="101px">来电号码</td>
		    <td class="listTitleStyle">评价类型</td>
			<td class="listTitleStyle">评价对象</td>
		    <td class="listTitleStyle">评价内容</td>
		    <td class="listTitleStyle">评价时间</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr>
		    <td ><bean:write name="c" property="telNum" filter="true"/></td>
		    <td >
			    <logic:equal name="c" property="appType" value="expert">专家评价</logic:equal>
	            <logic:equal name="c" property="appType" value="agent">座席评价</logic:equal>
            </td>
			<td ><bean:write name="c" property="appraiseObject" filter="true"/></td>
		   	<td >
		   		<logic:equal name="c" property="appResult" value="1">满意</logic:equal>
		   		<logic:equal name="c" property="appResult" value="2">基本满意</logic:equal>
		   		<logic:equal name="c" property="appResult" value="3">不满意</logic:equal>
		   		<logic:equal name="c" property="appResult" value="4">其它</logic:equal>
		   		<logic:equal name="c" property="appResult" value="5">其它</logic:equal>
		   	</td>
		   	<td ><bean:write name="c" property="endTime" filter="true"/></td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="6" class="pageTable">
				<page:page name="firewallpageTurning" style="second"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>