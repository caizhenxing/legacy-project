
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
    
    <title><bean:message key="sys.tree.title.insert"/></title>
    
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
		alert("");
		popUp('windows','/callcenter/sys/tree.do?method=load&type=insert',480,400);
	}
	
	
	function deleteIt()
	{
		
		document.forms[0].action = '/callcenter/sys/tree.do?method=delete';
		document.forms[0].submit();
    	
	}
	
	
</script>
  </head>
  
  <body bgcolor="#eeeeee">
  <html:form action="/sys/tree.do?method=insert"  target="contents">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key="sys.tree.title.insert"/></td>
  </tr>
  
  
  <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.name"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="name" />
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.domain"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="domian" />
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.uniqueName"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="procAlias" />
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.parentId"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="parentId" />
		
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.display"/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="tagShow">
        			<html:option value="1">-<bean:message key="sys.tree.display.yes"/>-</html:option>
        			<html:option value="0"><bean:message key="sys.tree.display.no"/></html:option>
        			
        			</html:select>
		
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.system"/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="tagShow">
        			<html:option value="1">-<bean:message key="sys.tree.system.yes"/>-</html:option>
        			<html:option value="0"><bean:message key="sys.tree.system.no"/></html:option>
        			
        			</html:select>
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message key="sys.tree.remark"/></td>
    <td  class="tdbgcolorloadleft">
	<html:textarea property="remark"/>
	</td>
  </tr> 
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <html:submit onclick="window.close()"><bean:message key="sys.insert"/></html:submit>
     </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
