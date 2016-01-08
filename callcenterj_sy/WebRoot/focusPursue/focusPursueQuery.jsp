<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

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

	<title>焦点追踪库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>

	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>

	<script type="text/javascript">
function query()
 	{
 		document.forms[0].action="../focusPursue.do?method=toFocusPursueList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/focusPursue" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;焦点追踪库
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('focusPursueBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					主&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle">
					<html:text property="chiefTitle" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					责任编辑
				</td>
				<td class="valueStyle">
					<html:text property="chargeEditor" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					版&nbsp;&nbsp;&nbsp;&nbsp;别
				</td>
				<td class="valueStyle">
					<select name="dictFocusType" class="selectStyle" style="width: 75px;">
						<option value="">全部</option>
						<option>实事版</option>
						<option>市场版</option>
					</select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" class="buttonStyle" value="查询" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('focusPursueBean','endTime',false);">
				</td>

				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle">
					<html:text property="summary" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					正&nbsp;&nbsp;&nbsp;&nbsp;文
				</td>
				<td class="valueStyle">
					<html:text property="fucosContent" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle">
					<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option selected="selected" value="一审">一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>
						<option>发布</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option selected="selected" value="一审驳回">一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>
						<option>发布</option>
					<%
					}else if("waitagain".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option selected="selected" value="二审">二审</option>
						<option>二审驳回</option>
						<option>发布</option>
					<%
					}else if("backagain".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option selected="selected" value="二审驳回">二审驳回</option>
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>
						<option selected="selected">发布</option>
					<%
					}else{
					%>
					<option value="" selected="selected">全部</option>
						<option>初稿</option>
						<option>一审</option>
						<option>一审驳回</option>
						<option>二审</option>
						<option>二审驳回</option>
						<option>发布</option>
					<%
					}
					 %>
						
						
					</select>
				</td>
				<td class="labelStyle">
					<input type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;"  >
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>