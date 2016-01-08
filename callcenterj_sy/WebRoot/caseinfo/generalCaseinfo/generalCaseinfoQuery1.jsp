<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>普通案例库信息查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
	</SCRIPT>


	<script type="text/javascript">
 function query()
 	{
 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
	
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/caseinfo/generalCaseinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置->金典案例
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('generalCaseinfoBean','beginTime',false);">
				</td>				
				
				<td class="labelStyle">
					受理专家
				</td>
				<td class="valueStyle">
					<%--<html:text property="caseExpert" styleClass="writeTextStyle" size="10"/>
				--%>
					<html:select property="caseExpert" style="width:155px">
						<option value="">请选择</option>
						<logic:iterate id="u" name="export">
							<html:option value="${u.custName}">${u.custName}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<%--<html:text property="caseRid" styleClass="writeTextStyle" size="10"/>--%>
					<html:select property="caseRid">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" value="查询"
						class="buttonStyle" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('generalCaseinfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:text property="caseContent" styleClass="writeTextStyle" style="width:155px"/>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:65px">
						<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					</select>
				</td>
				<td class="labelStyle" align="center">					
					<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="13" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
