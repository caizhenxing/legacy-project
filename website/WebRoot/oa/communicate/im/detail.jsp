
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html  locale="true">
  <head>
    <html:base />
    
    <title>info.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
	
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript">
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
		
		
		
		popUp('windows','',480,400);
	}
	
	
	function updateIt()
	{
			window.close();
    		
	}
	
	
	
</script>
  </head>
  
  
  <body>
  <html:form action="/oa/communicate/im.do?method=send">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key="oa.communicate.im.title"/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.communicate.im.sender"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="sender" readonly="true" /></td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.communicate.im.sendTime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="sendTime" readonly="true"/></td>
  </tr>

  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.communicate.im.sendcontent"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="contents" readonly="true" rows="8" /></td>
  </tr> 
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <html:submit><bean:message key="oa.communicate.im.feedback"/></html:submit>
    <html:button property="aaaa" onclick="updateIt()" ><bean:message key="oa.communicate.im.close"/></html:button>
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
