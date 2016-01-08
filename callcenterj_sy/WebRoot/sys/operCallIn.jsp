<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="java.util.StringTokenizer" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="./../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>转内线</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  	 <script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentListService.js'></script>
 	 <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	 <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>


	<link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="./../js/common.js"></script>


	<SCRIPT language="javascript" src="./../js/form.js"
		type=text/javascript>
</SCRIPT>
<script type="text/javascript">
	function goChangeInLine()
	{
		
		var inline = document.getElementById('onLineAgent').value;
		if(inline!=-1)
		{
			opener.transferInline(inline);
			//opener.document.getElementById('txtInline').value=inline;
			//opener.document.getElementById("btnExecChangeInLine").click();
			opener.clickLoginState();
			//window.close();
		}
		else
		{
			alert('请选择内线呼叫!');
		}
	}
	//加载内线用户的列表
	function loadInLine(){
		agentListService.getAgentList(addInUserList);
	}
	//在选择的时候加载内线用户的列表
	function addInUserList(data){
		var agent_num = document.getElementById('onLineAgent');
    	DWRUtil.removeAllOptions(agent_num);
		DWRUtil.addOptions(agent_num,{'-1':'请选择座席工号'});
		DWRUtil.addOptions(agent_num,data);
	}
	//取消转移
	function goCancelChange()
	{
		var i = opener.Stoptransfer();
		if(i!=0)
		{
			alert('取消转移失败');
		}
	}
</script>
</head>

<body class="loadBody" onload="loadInLine()">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" colspan="2">
					当前位置->呼叫内线
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px;">
					&nbsp;选择座席
				</td>
				<td class="labelStyle">
					&nbsp;<select id="onLineAgent" >
					</select>
				</td>
			</tr>
			<tr>
				<td class="valueStyle">
		
				</td>
				<td class="labelStyle" align="left">
					&nbsp;<input type="button" value="转移" onclick="goChangeInLine()"/>&nbsp;<input type="button" value="取消" onclick="goCancelChange()" />&nbsp;<input type="button" value="关闭" onclick="window.close()" />
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px; height:20px;" colspan="4">
				</td>
			</tr>
		</table>

</body>
</html:html>

