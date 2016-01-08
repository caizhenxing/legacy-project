<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="java.util.StringTokenizer" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="et.bo.sys.login.bean.UserBean" %>
<%@ page import="java.util.*" %>
<%@ include file="./../style.jsp"%>
<% 
	UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	String agentNum = ub.getUserId();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>三方呼叫</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<!-- import dwr -->
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/expertAnswerLogService.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/expertService.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
  	
	
	

	<link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="./../js/common.js"></script>
	<SCRIPT language=javascript src="./../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="./../js/clock.js"></script>
<script language="javascript" src="./../js/ajax.js"></script>
<script language="javascript" src="./../js/all.js"></script>
<script language="javascript" src="./../js/agentState.js"></script>
	<SCRIPT language="javascript" src="./../js/form.js"
		type=text/javascript>
</SCRIPT>
<script type="text/javascript">
	function goQueryOutLine()
	{
		window.open('../callcenter/userInfo.do?method=toCustinfoMain','','width=800,height=420,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
	}
	//呼叫三方
		function goCallThree(){

				var tel = document.getElementById('handInputOutLine').value;
		
	
				if(tel != 0)
				{
					expertAnswerLogService.insertExpertAnswerLog(tel,"<%=agentNum%>");
					//expertService.insertThreeCall("<%=agentNum%>"); //这个不用了
					//opener.document.getElementById('txtOutLine').value=tel;	
					opener.document.getElementById('txtOutLine').value=tel;
					opener.document.getElementById("btnCallThree").click();
					document.getElementById('btnBegin').style.display='inline';
					document.getElementById('btnCallOut').style.display='none';
					
					//state
					document.getElementById('btnCallOut').disabled=true;
					document.getElementById('btnCallOut').style.display="inline";
					document.getElementById('btnBegin').disabled=false;
					document.getElementById('btnBegin').style.display="inline";
					document.getElementById('btnSwap').disabled=false;
					document.getElementById('btnAgentHold').disabled=true;
					document.getElementById('btnExpertHold').disabled=true;
					document.getElementById('btnThreeHold').disabled=true;
				}
				else
				{
					alert('请填写三方号码！');
				}
				document.getElementById('btnCallOut').disabled=false;
				document.getElementById('btnCallOut').style.display="inline";
				document.getElementById('btnExpertHold').disabled=false;
			
		}
		//监听
		function listen(){
			alert("jianting()");
		}
		//强插
		function insert(){
			alert("qiangcha()");
		}
		//强拆
		function remove(){
			alert("qiangchai()");
		}
		//三方全挂
		function threeHold(){
			//parent.opener.document.getElementById("btnThreeAllOnHook").click();
			//doRequestUsingGET('threehold','');threeonhook
			agentHold();
			expertHold();
			document.getElementById('btnThreeHold').disabled=true;
			window.close();
		}
		//挂断专家
		function expertHold()
		{
			//parent.opener.document.getElementById("btnThreeExpertOnHook").click();
			var lineNum = parent.opener.document.getElementById("txtThreeLinenum").value;
			if(lineNum>=0)
			{
				getHoldThreeExpert(lineNum,'-1');
			}
			document.getElementById('btnExpertHold').disabled=true;
			
		}
		//挂断专家 恢复外线
		function expertHoldUserGoon()
		{
			var i = opener.atUnHold();
			if(i==0)
			{
				expertHold();
				document.getElementById('btnCallOut').disabled=false;
				document.getElementById('btnBegin').disabled=true;
				document.getElementById('btnBegin').style.display="none";
				document.getElementById('btnCallOut').style.display="inline";
				opener.isTreeHold=1;
				window.setTimeout("holdExpertState()",2000);
			}
			else
			{
				opener.isTreeHold=1;
				window.setTimeout("holdExpertState()",2000);
				alert('专家挂断失败');
			}
			//opener.acceptTelState();//挂断专家　恢复接听电话状态
		}
		function holdExpertState()
		{
			opener.acceptTelState();
		}
		//挂断座席
		function agentHold(){
			//opener.document.getElementById("btnThreeAgentOnHook").click();
			opener.document.getElementById("btnOnHook").click();
			document.getElementById('btnAgentHold').disabled=true;
		}
		function tlaSwap()
		{
			opener.document.getElementById("btnTlaSwap").click();
		}
		//tlaActivate() 三方开始
		function tlaActivate()
		{
			opener.document.getElementById("btnTlaActivate").click();
			//state
			document.getElementById('btnCallOut').disabled=true;
			document.getElementById('btnCallOut').style.display="none";
			document.getElementById('btnBegin').disabled=true;
			document.getElementById('btnBegin').style.display="inline";
			document.getElementById('btnSwap').disabled=true;
			document.getElementById('btnAgentHold').disabled=false;
			document.getElementById('btnExpertHold').disabled=false;
			document.getElementById('btnThreeHold').disabled=false;
		}
		function bodyOnLoad()
		{
			//btnThreeHold btnExpertHold btnAgentHold btnSwap btnBegin btnCallOut
			document.getElementById('btnCallOut').disabled=false;
			document.getElementById('btnBegin').disabled=true;
			document.getElementById('btnBegin').style.display="none";
			document.getElementById('btnSwap').disabled=true;
			document.getElementById('btnAgentHold').disabled=true;
			document.getElementById('btnExpertHold').disabled=true;
			document.getElementById('btnThreeHold').disabled=true;
		}
</script>
<style type="text/css">
 td{
 	padding-top:3px;
 	padding-bottom:3px;
 }
</style>
</head>

<body class="loadBody" onload="bodyOnLoad()">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" colspan="2">
					当前位置->三方呼叫
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px;">
					&nbsp;三方号码
				</td>
				<td class="labelStyle">
					&nbsp;<input type="text" id="handInputOutLine" class="writeTextStyle" />&nbsp;<input type="button" id="queryOutLine" class="buttonStyle" value="查询" onclick="goQueryOutLine()" />
<%--					<input type="button"  value="恢复" style="display:inline;" onclick="opener.atUnHold()"/>--%>

				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle" style="margin-right:10px;">
					<input id="btnCallOut" type="button" value="呼&nbsp;&nbsp;叫" onclick="goCallThree()" style="display:inline;" />
					<input id="btnBegin" type="button" value="开&nbsp;&nbsp;始"  onclick="tlaActivate()" style="display:none;"/>
					<input id="btnSwap" type="button" value="切&nbsp;&nbsp;换"  onclick="tlaSwap()" />
				</td>
				<td class="labelStyle">
					<input type="button" id="btnAgentHold" value="座席挂" style="display:inline;" onclick="agentHold()"/>
				</td>
				<td class="labelStyle">
					<!--<input type="button" id="btnExpertHold" value="专家挂" style="display:inline;" onclick="expertHold()"/>-->
					<input type="button" id="btnExpertHold" value="专家挂" style="display:inline;" onclick="expertHoldUserGoon()"/>
				</td>
				<td class="labelStyle">
					<input id="btnThreeHold" type="button" value="全&nbsp;&nbsp;挂" onclick="threeHold()" style="display:inline;" />

				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px; height:20px;" colspan="4">
				</td>
			</tr>
		</table>
</body>
</html:html>