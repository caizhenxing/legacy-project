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
						��ǰλ��&ndash;&gt;���ߵ���ά��
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				
				<tr class="conditionoddstyle">
					<td class="queryLabelStyle">
						��ʼʱ�� 
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle" size="10"/>
						<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('detailForm','beginTime',false);">
					</td>
					<td class="queryLabelStyle">
						����ʱ��
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle" size="10"/>
						<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('detailForm','endTime',false);">
					</td>					
					<td class="queryLabelStyle">
						������
					</td>
					<td class="valueStyle">
						<html:select property="rid">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
					</td>
					<td class="queryLabelStyle">
						�û�����
					</td>
					<td class="valueStyle">
						<html:text property="actor" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle">
						��������
					</td>
					<td class="valueStyle">
						<select name="questionType" id="questionType">
								<option value=''>��ѡ��</option>
								<option value='001'>��ѡ��</option>
								<option value='002'>��ѡ��</option>
								<option value='003'>�ʴ���</option>
							</select>
					</td>
					<td class="queryLabelStyle">
						��������
					</td>
					<td class="valueStyle">
						<html:text property="question" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle">
						�����
					</td>
					<td class="valueStyle">
						<html:text property="answer" styleClass="writeTextStyle" size="10"/>
					</td>
					<td class="queryLabelStyle" align="center">
						<input type="button" name="btnSearch" value="��ѯ" class="buttonStyle"  onclick="doquery()" />
						<input  type="reset" value="ˢ��" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;">
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