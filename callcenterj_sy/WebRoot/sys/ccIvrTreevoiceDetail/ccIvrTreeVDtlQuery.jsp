<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
    
    <title>语音信息维护</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
 <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
 <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../../js/common.js"></script>

<script language="javascript" src="../../js/clock.js"></script>
 
 <script type="text/javascript">
 document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
 	function add()
 	{
 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfoList";
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	var sTreeId = 0;
	function setTree(obj)
	{
		sTreeId = obj.value;
	}
	function fileAdd()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=insert'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,650,300);
	}
	function fileAddText()
	{
		  var fUrl = 'ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoLoad&type=inserttext'
		  if(sTreeId!=0)
		  {
		  	fUrl = fUrl + '&treeId='+sTreeId;
		  }
			popUp('ccIvrTreeinfoWindows',fUrl,850,500);
	}
 </script>
 
  </head>
  
  <body class="conditionBody">
    <html:form action="/sys/ccIvrTreevoiceDetail" method="post">
    
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
		  <tr>
		   <td class="navigateStyle"> 
		    当前位置&ndash;&gt;语音信息维护
		    </td>
		  </tr>
		</table>
    
    	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
	    			
	    			
	    			<tr>
	    				<td class="labelStyle" align="left" width="123px">
	    					语音栏目
	    				</td>
	    				<td colspan="3" class="valueStyle">
	    				<html:select property="treeId" styleClass="selectStyle" onclick="setTree(this)">
		    				<html:option value="">请选择</html:option>
				    		<html:options collection="IVRLVList"
				  							property="value"
				  							labelProperty="label"/>
				    	</html:select>	
	    				</td>
	    				<td class="labelStyle">
	    					语音路径
	    				</td>
	    				<td colspan="3" class="valueStyle">
	    					<html:text property="voicePath" styleClass="writeTextStyle"/>
	    				</td>
    			
    			<td class="labelStyle" style="text-align:right;" width="95px">
    				<input type="button" name="btnSearch" value="查询"  class="buttonStyle" onclick="query()" />
    				<input type="button" style="display:none;" name="btnadd" value="文件添加" class="buttonStyle" onclick="fileAdd()" />
    				<input type="button" style="display:none;" name="btnaddtext" value="文本添加" class="buttonStyle" onclick="fileAddText()"/>
    				<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;"/>
    			</td>
    			
    		</tr>
    	</table>
    </html:form>
  </body>
</html:html>
