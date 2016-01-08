<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.Map" %>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></script>
	<script language="javascript">
    	//��ѯ
    	function query(){
    		document.forms[0].action = "../../screen/marAnalysis.do?method=toMarAnalysisScreenList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}

   	</script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/screen/marAnalysis" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;��ũ�г���������Ļ����
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					����ʦ
				</td>
				<td class="valueStyle">
					<input type="text" name="analysisPerson" class="writeTextStyle" size="16">
				</td>			
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<input type="text" name="subTitle" class="writeTextStyle" size="16">
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<input type="text" name="analysisContent" class="writeTextStyle">
				</td>
				<td class="labelStyle">
					���
				</td>
				<td class="valueStyle">
					<html:select property="analysisType" style="width:90">
						<option value="">��ѡ��</option>
						<option value="ũ���">ũ���</option>
						<option value="������">������</option>
					</html:select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" value="��ѯ"
						class="buttonStyle" onclick="query()" />
					<input type="reset" value="ˢ��" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>

		</table>
	</html:form>
</body>
</html:html>
