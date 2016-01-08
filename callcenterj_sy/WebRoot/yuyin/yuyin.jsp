<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree" %>
<%@ include file="../style.jsp"%>
<%
response.setHeader("Expires","0");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>yuyin.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
		
		<script type="text/javascript">
			function check(str)
			{		
				var v = str;
				
				if(v=='xuanzhe')
				{
					document.getElementById("reportType").style.display="block";
					document.getElementById("affixType").style.display="none";
					
				}
				else if(v=='zhantie')
				{
					document.getElementById("affixType").style.display="block";
					document.getElementById("reportType").style.display="none";
				}
				
			}
			function getValue()
			{
				var radios = document.getElementsByName("play");
				for(var i=0; i<radios.length; i++)
				{
					if(radios[i].checked)
					{
						if("xuanzhe"==radios[i].value)
						{
							return "0,"+parent.ivrOperationframeTree.document.getElementById('selectTreeId').value;//document.getElementById('tXuanzhe').value;
						}
						else if("zhantie"==radios[i].value)
						{
							return "1,"+document.getElementById('tZhantei').value;
						}
					}
				}
			}
			function invokeParent()
			{
				var temp = getValue();

				parent.window.opener.jsAuto2Applet(temp);
			}
		</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #f5f5f5;
}
.text-selected{
	background: #ccffff;
}
-->
</style>
<script language="javascript">
	function nockClick(id)
	{
		//alert('当前节点id'+id);
	}
</script>
  </head>
  
  <body onload="check('xuanzhe')">
    <form action="yuyin" method="post">
    	<table width="60%" border="0" height="40%" align="center" cellpadding="1" cellspacing="1" class="contentTable">
    		<tr>
    			<td colspan="2" class="labelStyle">
    				播放模式
    			</td>
    			<td class="labelStyle">
    				
    			</td>
    			<td  class="labelStyle">
    				
    			</td>
    		</tr>
    		<tr>
    			<td align="right" class="labelStyle">
    				
    			</td>
    			<td>
    				
    			</td>
    			<td >
    				<input type="radio" name="play" value="xuanzhe" onclick="check(this.value)" checked="checked"/>选择播放
    			</td>
    			
    		</tr>
    		<tr>
    			<td align="right" class="labelStyle">
    				
    			</td>
    			<td >
    				
    			</td>
    			<td>
    				<input type="radio" name="play" value="zhantie" onclick="check(this.value)"/>粘贴播放
    				
    			</td>
    		</tr>
    		<tr id="reportType">
    			<td class="labelStyle">
    				
    			</td>
    			<td align="right" class="labelStyle">
    				
    			</td>
    			<td class="labelStyle" >
		  	<newtree:tree tree="ivrYuyinTreeSession" 
  			action="/callcenterj_sy/yuyin.do?method=toyuyinTreeLoad&tree=$-{name}"
  			style="text" styleSelected="text-selected"
  			styleUnselected="text-unselected"
  			images="images"
  			/>	
    			</td>
    		</tr>
    		<tr id="affixType" style="display:none">
    			<td class="labelStyle">
    				
    			<br></td>
    			<td align="right" class="labelStyle">
    				
    			<br></td>
    			<td class="labelStyle">&nbsp;
    				<textarea name="affixTxt" cols="50" rows="30" id="tZhantei">jingjingjingjingjingjing</textarea>
    				<input type="text" style="display:none;" name="tXuanzhe" id="tXuanzhe" value="<%=(String)request.getAttribute("selectTreeId") %>" />
    			<br></td>
    		</tr>
    		<tr>
    		
    			<td align="center" colspan="4">
    				<input type="button" value="确定" name="" onclick="invokeParent()"/>
    				<input type="button" value="取消" name="" onclick="javascript:window.close()"/>
    			</td>
    			
    		</tr>
    	</table>
    </form>
  </body>
</html:html>
