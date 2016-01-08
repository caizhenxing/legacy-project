<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "eventResult.do?method=toEventResultList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}

   	</script>
	<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/eventResult/eventResult" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;����Ա�¼�����
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<input type="text" name="beginTime" class="writeTextStyle">
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('eventResult','beginTime',false);">
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<input type="text" name="topic" class="writeTextStyle">
				</td>
				<td class="labelStyle">
					��ϯԱ
				</td>
				<td class="valueStyle">
					<input type="text" name="linkman_id" class="writeTextStyle">
				</td>
				
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" value="��ѯ"
						class="buttonStyle" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��ֹʱ��
				</td>
				<td class="valueStyle">
					<input type="text" name="endTime" class="writeTextStyle">
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('eventResult','endTime',false);">
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<input type="text" name="feedback" class="writeTextStyle">
				</td>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<input type="text" name="principal" class="writeTextStyle">
				</td>
				<td class="labelStyle" align="center">
					<input type="reset" value="ˢ��" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px" class="buttonAreaStyle">
				<td colspan="15">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
