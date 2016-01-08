<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js"></script>
</head>

<body class="listBody">
	<table id="tbl1" width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" width="25%">
<%--				��ϯԱ--%>
				������
			</td>
			<td class="listTitleStyle" width="25%">
				����
				<logic:equal name="condition" value="count">����(��)</logic:equal>
				<logic:notEqual name="condition" value="count">ʱ��(��)</logic:notEqual>
			</td>
			<td class="listTitleStyle" width="25%">
				����
				<logic:equal name="condition" value="count">����(��)</logic:equal>
				<logic:notEqual name="condition" value="count">ʱ��(��)</logic:notEqual>
			</td>
			<td class="listTitleStyle" width="25%">
			<logic:notEmpty name="querytype">
				<logic:equal name="querytype" value="count">�ܻ�����</logic:equal>
				<logic:equal name="querytype" value="periodtime">�ܷ���ʱ��</logic:equal>
			</logic:notEmpty>
			
			<logic:empty name="querytype">�ܻ�����</logic:empty>
			</td>
		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr>
				<td >
					<bean:write name="c" property="name" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="type1" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="type2" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="type3" filter="false" />
				</td>
			</tr>
		</logic:iterate>
		<logic:notEmpty name="list">
			<tr>
				<td class="evenStyle">
					�ܼ�
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num1" filter="false" />
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num2" filter="false" />
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num3" filter="false" />
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td colspan="4" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
				<div style="text-align:right;">
					<input type="button" class="buttonStyle" value="����Excel" onclick="parseTbl('tbl1','��ϯԱ����İ�������','<%=basePath%>')" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
