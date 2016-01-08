<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="../../style.jsp"%>
<html>
	<head>
		<html:base />
		<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" src="../../js/calendar3.js"></script>
<script>
	function doquery(){
		document.forms[0].action="../../detailForm.do?method=toList";
		document.forms[0].target="bottomm";
		document.forms[0].submit();
	}

</script>
	</head>
	<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
		<html:form action="/detailForm" method="post">
		<html:hidden property="topicId"/>
			<table width="100%" align="center"  cellpadding="0" cellspacing="1" class="conditionTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;热线调查维护
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				
				<tr class="conditionoddstyle">
					<td class="queryLabelStyle">
						开始时间 
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle" size="10"/>
						<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('detailForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						结束时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle" size="10"/>
						<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('detailForm','endTime',false);">
					</td>					
					<td class="queryLabelStyle">
						受理工号
					</td>
					<td class="valueStyle">
						<html:select property="rid">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
					</td>
					<td class="queryLabelStyle">
						用户姓名
					</td>
					<td class="valueStyle">
						<html:text property="actor" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle">
						问题类型
					</td>
					<td class="valueStyle">
						<select name="questionType" id="questionType">
								<option value=''>请选择</option>
								<option value='001'>单选题</option>
								<option value='002'>多选题</option>
								<option value='003'>问答题</option>
							</select>
					</td>
					<td class="queryLabelStyle">
						调查问题
					</td>
					<td class="valueStyle">
						<html:text property="question" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle">
						调查答案
					</td>
					<td class="valueStyle">
						<html:text property="answer" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle" align="center">
						<input type="button" name="btnSearch" value="查询" class="buttonStyle"  onclick="doquery()" />
						<input  type="reset" value="刷新" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;">
					</td>
				</tr>
				<tr height="1px">
					<td colspan="15" class="buttonAreaStyle">
						
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>