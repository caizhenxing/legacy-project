
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
    
    <title>top.jsp</title>
  <script type="text/javascript">
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
function sub1()
	{
		
		document.forms[0].submit();
    	
	}
//-->
</script>
    <META HTTP-EQUIV="refresh" CONTENT="30"/> 
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312"><style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
  
  <body>
  <html:form action="/oa/communicate/im.do?method=hasMsg">
  
  <logic:equal name="hasMsg" value="yes">
  	<a href="/ETOA/oa/communicate/im.do?method=receiveMsg" onclick="popUp('windows','',480,400)" target="windows">
 	<img name="images" src="<bean:write name='flash'/>" onclick="sub1()" border="0">
 	</a>
 	</logic:equal> 
 	
 	<logic:equal name="hasMsg" value="no">
  	<a href="/ETOA/oa/communicate/im/newIm.jsp" onclick="popUp('windows','',480,400)" target="windows">
 		<img name="images" src="<bean:write name='flash'/>" onclick="sub1()" border="0">
 	</a>
 	</logic:equal> 
 	
  	
 	
  </html:form>
  
  </body>
</html:html>
