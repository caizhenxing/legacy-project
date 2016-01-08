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

	<title>未发送信息查询</title>

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
 		document.forms[0].action="../sendNot.do?method=toSendNotList";
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
 </script>

</head>

<body class="conditionBody">
	<html:form action="/sms/sendAndReceive/sendNot" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;预发送信息
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择日期" src="../../../html/img/cal.gif"
						onclick="openCal('sendNotBean','beginTime',false);">
				</td>
				<td class="queryLabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择日期" src="../../../html/img/cal.gif"
						onclick="openCal('sendNotBean','endTime',false);">
				</td>
				<td class="queryLabelStyle">
					接方号码
				</td>
				<td class="valueStyle">
					<html:text property="receiveNum" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="queryLabelStyle">
					运&nbsp;营&nbsp;商
				</td>
				<td class="valueStyle">
					<html:select property="management" styleClass="selectStyle">
		    				<html:option value="">请选择</html:option>
		    				<html:option value="中国移动">中国移动</html:option>
		    				<html:option value="中国联通">中国联通</html:option>
				    	</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input type="button" name="btnSearch" value="查询" class="buttonStyle"
						  onclick="query()" size="10"/>
					<input type="reset" value="刷新" class="buttonStyle"  onClick="parent.bottomm.document.location=parent.bottomm.document.location;" size="10"/>
				</td>
			</tr>

			<tr height="1px">
				<td colspan="9" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>