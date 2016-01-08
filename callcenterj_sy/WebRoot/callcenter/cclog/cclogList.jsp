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
    
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
    <script type="text/javascript">
      	
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
    <style type="text/css">
<!--
.STYLE1 {font-size: 9px}
-->
    </style>
  </head>
  
  <body class="listBody">
    <html:form action="/callcenter/cclog.do?method=toCclogList&pagestate=" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr>
<%--		  	<td class="listTitleStyle">是否回答成功</td>--%>
		    <td class="listTitleStyle">来电号码</td>
<%--		    <td class="listTitleStyle">应答座席员</td>--%>
		    <td class="listTitleStyle">来电开始时间</td>
		    <td class="listTitleStyle">来电结束时间</td>
<%--		    <td class="tdbgpiclist">收听录音</td>--%>
		    <td class="listTitleStyle" width="15%">操作</td>
		  </tr>
		  
		  <logic:iterate id="c" name="list" indexId="i">
		 	<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  
		  <tr>
		    <td ><bean:write name="c" property="telNum" filter="true"/></td>
		    <td ><bean:write name="c" property="ringBegintime" format="yyyy-MM-dd HH:mm:ss" filter="true"/></td>
		    <td ><bean:write name="c" property="processEndtime" format="yyyy-MM-dd HH:mm:ss" filter="true"/></td>
		    
		    <td width="15%" >
            <img alt="详细" alt="详细" src="../../style/<%=styleLocation %>/images/detail.gif"
            	onclick="popUp('21<bean:write name='c' property='id'/>','cclog.do?method=toCclogLoad&type=detail&id=<bean:write name='c' property='id'/>&talkid=<bean:write name='c' property='talkid'/>',650,650)" width="16" height="16" border="0"/>
            <a href="<bean:write name='c' property='filename' filter='true'/>">
		     	<img border="0" alt="收听录音" src="../../images/voice.gif"></a>
		    </td>
		    
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="cclogPageTurning" style="second"/>		    
			</td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>