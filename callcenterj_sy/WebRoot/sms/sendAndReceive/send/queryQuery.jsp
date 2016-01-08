<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>短信收发查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../../style/<%=styleLocation%>/style.css"
		rel="stylesheet" type="text/css" />
	<SCRIPT language=javascript src="../../../js/form.js"
		type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>

	<script type="text/javascript">
 	function query()
 	{
 		document.forms[0].action="../send.do?method=toSendList";
 		document.forms[0].target="bottomm";
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
	
	function alreadysendsearch() {
		document.forms[0].action = "../send.do?method=toSendList";
		document.forms[0].target = "bottomm";
		document.forms[0].submit();
	}
	
	function readysendsearch() {
		document.forms[0].action = "../sendNot.do?method=toSendNotList";
		document.forms[0].target = "bottomm";
		document.forms[0].submit();
	}
	
	function receivesearch() {
		document.forms[0].action = "/callcenterj_sy/sms/sendAndReceive/receive.do?method=toReceiveList";
		document.forms[0].target = "bottomm";
		document.forms[0].submit();
	}
 </script>

</head>

<body class="conditionBody">
	<html:form action="/sms/sendAndReceive/send" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;短信查询
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					短信查询
				</td>
				<td class="valueStyle">
					<a href="../send.do?method=toSendQuery&&type=already" target="topp">已发查询</a>
				</td>
				<td class="valueStyle">
					<a href="../send.do?method=toSendQuery&&type=notSend" target="topp">预发查询</a>
				</td>				
				<td class="valueStyle">
					<a href="../send.do?method=toSendQuery&&type=receive" target="topp">收到查询</a>
				</td>
			</tr>
			<tr height="1px">
				<td colspan="10" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
	