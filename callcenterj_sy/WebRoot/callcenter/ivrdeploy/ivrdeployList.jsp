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
    
    <title>CcUserList.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
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
   <html:form action="/callcenter/ivrdeploy" method="post">	
  		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
<%--		  	<td class="tdbgpiclist">工单编号</td>--%>
			<td class="listTitleStyle">编号</td>
		    <td class="listTitleStyle">配置信息</td>
		    <td class="listTitleStyle">声音文件路径</td>
		    <td class="listTitleStyle">按键设置</td>
		    <td class="listTitleStyle">操作</td>

		    
		   
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  
		    <%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  
		  <tr>
<%--		    <td ><bean:write name="c" property="WNid" filter="true"/></td>--%>
		    <td ><bean:write name="c" property="id" filter="true"/></td>
		    <td ><bean:write name="c" property="content" filter="true"/></td>
		    <td ><bean:write name="c" property="voicefilepath" filter="true"/></td>
		    <td ><bean:write name="c" property="key" filter="true"/></td>
		    
            <td width="10%">
            
            <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif" 
            onclick="popUp('CcUserwindows','../callcenter/ivrdeploy.do?method=toIvrdeployUpdate&id=<bean:write name='c' property='id'/>',800,420)" width="16" height="16" target="windows" border="0"/>
		    
		    </td>
		  </tr>
		</logic:iterate>
<%--		  <tr>--%>
<%--		    <td colspan="5">--%>
<%--				<page:page name="CcUserpageTurning" style="second"/>--%>
<%--		    </td>--%>
<%--		  </tr>--%>
		</table>
    
    </html:form>
  </body>
</html:html>
e