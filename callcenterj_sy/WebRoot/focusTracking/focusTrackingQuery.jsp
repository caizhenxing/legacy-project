<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>焦点追踪大屏幕</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script type="text/javascript">
	function query(){
 		document.forms[0].action="../focusTracking.do?method=toFocusTrackingList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/focusTracking" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;焦点追踪大屏幕
				</td>
			</tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					期&nbsp;&nbsp;&nbsp;&nbsp;第
				</td>
				<td class="valueStyle">
					<html:text property="ftPeriod" styleClass="writeTextStyle" size="10"/>
				</td>						
					
				<td class="labelStyle">
					标&nbsp;&nbsp;&nbsp;&nbsp;题
				</td>
				<td class="valueStyle">
					<html:text property="ftTitle" styleClass="writeTextStyle" size="10"/>
				</td>
				
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle">
					<html:text property="ftSummary" styleClass="writeTextStyle" size="10"/>
				</td>
				
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" class="buttonStyle" value="查询" onclick="query()" />				
					<input type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;"  >
				</td>
			</tr>
			
		</table>
	</html:form>
</body>
</html:html>