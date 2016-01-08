<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>网站查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
 <link href="../../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
 <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>
 
 <script type="text/javascript">
 document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
 	function addinter()
 	{
 		document.forms[0].action="../intersave.do?method=tointerSaveLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../intersave.do?method=tointerList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
 </script>
 
  </head>
  
  <body >
    <html:form action="/oa/commoninfo/intersave" method="post">
    
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    当前位置&ndash;&gt;网址信息
		    </td>
		  </tr>
		</table>
    
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td class="tdbgcolorloadright">
    				网&nbsp;址&nbsp;名
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="interUsername" size="60" styleClass="input"/>
    			</td>
    			<td class="tdbgcolorloadright">
    				类&nbsp;&nbsp;&nbsp;&nbsp;型
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:select property="dictInterType">
    				<html:option value="">
    				请选择
    				</html:option>
    				<html:options collection="interType"
  							property="value"
  							labelProperty="label"/>
    			
    				</html:select>
    			</td>
    			
    		</tr>
    		<tr>
    			
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="查询"  class="button" onclick="query()"/>
<%--    				<input type="button" name="btnadd" value="添加" onclick="popUp('windows','intersave.do?method=tointerSaveLoad&type=insert',650,200)"/>--%>
    				<input type="reset" value="刷新"  class="button"/>
    			</td>
    			
    		</tr>
    		
<%--    		<html:hidden property="interUsername"/>--%>
    	</table>
    </html:form>
  </body>
</html:html>
b