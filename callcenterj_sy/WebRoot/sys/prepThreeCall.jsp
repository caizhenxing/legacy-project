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

	<title>��������</title>

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
	//��������
		function goCallThree(){

				var tel = document.getElementById('handInputOutLine').value;
		
	
				if(tel != 0)
				{
					expertAnswerLogService.insertExpertAnswerLog(tel,"<%=agentNum%>");
					//expertService.insertThreeCall("<%=agentNum%>"); //���������
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
					alert('����д�������룡');
				}
				document.getElementById('btnCallOut').disabled=false;
				document.getElementById('btnCallOut').style.display="inline";
				document.getElementById('btnExpertHold').disabled=false;
			
		}
		//����
		function listen(){
			alert("jianting()");
		}
		//ǿ��
		function insert(){
			alert("qiangcha()");
		}
		//ǿ��
		function remove(){
			alert("qiangchai()");
		}
		//����ȫ��
		function threeHold(){
			//parent.opener.document.getElementById("btnThreeAllOnHook").click();
			//doRequestUsingGET('threehold','');threeonhook
			agentHold();
			expertHold();
			document.getElementById('btnThreeHold').disabled=true;
			window.close();
		}
		//�Ҷ�ר��
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
		//�Ҷ�ר�� �ָ�����
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
				alert('ר�ҹҶ�ʧ��');
			}
			//opener.acceptTelState();//�Ҷ�ר�ҡ��ָ������绰״̬
		}
		function holdExpertState()
		{
			opener.acceptTelState();
		}
		//�Ҷ���ϯ
		function agentHold(){
			//opener.document.getElementById("btnThreeAgentOnHook").click();
			opener.document.getElementById("btnOnHook").click();
			document.getElementById('btnAgentHold').disabled=true;
		}
		function tlaSwap()
		{
			opener.document.getElementById("btnTlaSwap").click();
		}
		//tlaActivate() ������ʼ
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
					��ǰλ��->��������
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px;">
					&nbsp;��������
				</td>
				<td class="labelStyle">
					&nbsp;<input type="text" id="handInputOutLine" class="writeTextStyle" />&nbsp;<input type="button" id="queryOutLine" class="buttonStyle" value="��ѯ" onclick="goQueryOutLine()" />
<%--					<input type="button"  value="�ָ�" style="display:inline;" onclick="opener.atUnHold()"/>--%>

				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle" style="margin-right:10px;">
					<input id="btnCallOut" type="button" value="��&nbsp;&nbsp;��" onclick="goCallThree()" style="display:inline;" />
					<input id="btnBegin" type="button" value="��&nbsp;&nbsp;ʼ"  onclick="tlaActivate()" style="display:none;"/>
					<input id="btnSwap" type="button" value="��&nbsp;&nbsp;��"  onclick="tlaSwap()" />
				</td>
				<td class="labelStyle">
					<input type="button" id="btnAgentHold" value="��ϯ��" style="display:inline;" onclick="agentHold()"/>
				</td>
				<td class="labelStyle">
					<!--<input type="button" id="btnExpertHold" value="ר�ҹ�" style="display:inline;" onclick="expertHold()"/>-->
					<input type="button" id="btnExpertHold" value="ר�ҹ�" style="display:inline;" onclick="expertHoldUserGoon()"/>
				</td>
				<td class="labelStyle">
					<input id="btnThreeHold" type="button" value="ȫ&nbsp;&nbsp;��" onclick="threeHold()" style="display:inline;" />

				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px; height:20px;" colspan="4">
				</td>
			</tr>
		</table>
</body>
</html:html>