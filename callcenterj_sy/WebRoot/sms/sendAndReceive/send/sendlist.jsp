<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>市场供求列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
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
 
 </script>
  </head>
  
  <body class="listBody">
	<html:form action="/sms/sendAndReceive/send" method="post">

	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">接方号码</td>
		    <td class="listTitleStyle">发送时间</td>
<%--		    <td class="listTitleStyle">预定发送时间</td>--%>
		    <td class="listTitleStyle">运营商</td>
		    <td class="listTitleStyle">短信内容</td>
		    <td class="listTitleStyle" width="15%">操作</td>
		    
		  </tr>
		  	  <logic:iterate id="c" name="list" indexId="i">
		 	<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  	  
		  <tr>
		  	<td ><bean:write name="c" property="receiveNum" filter="true"/></td>
		    <td ><bean:write name="c" property="operTime" filter="true"/></td>
<%--		    <td ><bean:write name="c" property="schedularTime" filter="true"/></td>--%>
		    <td ><bean:write name="c" property="management" filter="true"/></td>
		    <td ><bean:write name="c" property="content" filter="true"/></td>
		    
            <td width="15%" >
            <img alt="详细" src="../../../style/<%=styleLocation %>/images/detail.gif"
            onclick="popUp('1<bean:write name='c' property='id'/>','send.do?method=toSendLoad&type=detail&id=<bean:write name='c' property='id'/>',500,210)" width="16" height="16" border="0"/>
		    <img alt="删除" src="../../../style/<%=styleLocation %>/images/del.gif"
		    onclick="popUp('2<bean:write name='c' property='id'/>','send.do?method=toSendLoad&type=delete&delType=delete&id=<bean:write name='c' property='id'/>',500,210)" width="16" height="16"  border="0"/>
		    <img alt="永久删除" src="../../../style/<%=styleLocation %>/images/del.gif"
		    onclick="popUp('3<bean:write name='c' property='id'/>','send.do?method=toSendLoad&type=delete&delType=delForever&id=<bean:write name='c' property='id'/>',500,210)" width="16" height="16"  border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5" class="pageTable">
				<page:page name="sendpageTurning" style="second"/>
		    </td>
		  </tr>
	</table>
		  
	</html:form>
  </body>
</html:html>