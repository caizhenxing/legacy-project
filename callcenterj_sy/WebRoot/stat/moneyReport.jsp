<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

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
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	%>
	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js"></script>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
</head>

<body class="listBody">
	<table id="tbl1" width="102%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" width="196px">
				外呼电话
			</td>
			<td class="listTitleStyle" >
				外呼次数
			</td>
			<td class="listTitleStyle" >
				外呼时长(秒)
			</td>
			<td class="listTitleStyle" width="100px">
				外呼计费（元）
			</td>
		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr>

				<td >
					<bean:write name="c" property="tel" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="num" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="sum_touchKeeptime" filter="false" />
				</td>
				<td >
					<bean:write name="c" property="money" filter="false" />
				</td>
			</tr>
		</logic:iterate>
		<logic:notEmpty name="list">
			<tr>
				<td class="evenStyle">
					总计
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num1" filter="false" />
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="numLen" filter="false" />
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num2" filter="false" />
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td colspan="4" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
				<div style="text-align:right;">
					<input type="button" class="buttonStyle" value="导出Excel" onclick="parseTbl('tbl1','外呼计费统计','<%=basePath%>')" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
