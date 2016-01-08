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
    
    <title>popselect.jsp</title>
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

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
<script language="javascript" src="../js/common.js"></script>

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
        function query(){
    		document.forms[0].action = "../group/Group.do?method=toGroupList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}


</script>

  </head>
  
  <body>
    <html:form action="/sys/group/Group.do" method="post">
    
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="nivagateTable">

    		<tr>
    			 <td  class="labelStyle" align="right">
    				<input type="button" name="select" value="²éÑ¯"   onclick="popUp('windowsRole','Group.do?method=toGroupQuery',550,130)">&nbsp;
    				<input type="button" name="btnSearch" value="Ë¢ÐÂ"   onclick="query()">&nbsp;
    				<input name="btnAdd" type="button"   value="Ìí¼Ó" onclick="popUp('windows','../group/Group.do?method=toGroupLoad&type=insert',550,250)"/>
    				
    			</td>
    		</tr>
    	
    	</table>
    
    </html:form>
  </body>
</html:html>
y