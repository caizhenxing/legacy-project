<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>



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
    
    <link href="../../css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../role/Role.do?method=toRoleList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
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
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/oa/wfm.do?method=wfdList" method="post">
      		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td align="center" class="tdbgpic1">工作流查询</td>
		  </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5">
		  <tr>
            <td width="28%" class="tdbgcolor1"><div align="right">工作流名称</div></td>
            <td width="18%" class="tdbgcolor1"><html:text property="name"></html:text></td>
            <td width="48%" class="tdbgcolor1">&nbsp;</td>
            <td width="6%" class="tdbgcolor1">&nbsp;</td>
          </tr>  
		  <tr>
		    <td align="right" class="tdbgcolor1"></td>
		    <td class="tdbgcolor1"></td>
		    <td class="tdbgcolor1">
		    <input name="btnSearch" type="button" class="bottom" value="查询" onclick="query()"/>
		    <input name="btnAdd" type="button" class="bottom" value="添加" onclick="popUp('windows','oa/wfm.do?method=addWFDef',480,400)"/>
		    </td>
		    <td class="tdbgcolor1">
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
