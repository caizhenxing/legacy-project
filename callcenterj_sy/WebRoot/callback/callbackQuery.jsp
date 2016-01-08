<%@ page language="java" import="java.util.*" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<html:html lang="true">
  <head>
    <html:base />
    
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../images/css/styleA.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "callback.do?method=toCallbackList";
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
body,td,th {
	font-size: 13px;
}
-->
</style>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/callback/callback.do" method="post">
      	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle"> 

		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  

  <tr>
    <td class="tdbgcolorqueryright">回访内容</td>
    <td class="tdbgcolorqueryleft"><html:text property="callback_content" styleClass="input"/></td>
    <td class="tdbgcolorqueryright">回访备注</td>
    <td class="tdbgcolorqueryleft"><html:text property="remark" styleClass="input"/></td>
  </tr>
		  <tr>
		    <td align="right" class="tdbgcolorquerybuttom" colspan="4">
		    <input name="btnSearch" type="button" class="bottom" value="查询" onclick="query()"/>
		    <%--<input name="btnAdd" type="button" class="bottom" value="添加" onclick="popUp('windows','callback.do?method=toCallbackLoad&type=insert',680,301)"/>--%>
		    <html:reset value="刷新" onClick="parent.bottomm.document.location=parent.bottomm.document.location;"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>

