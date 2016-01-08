<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<html:html locale="true">
<head>
	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<style>
table {
	font-size: 13px;
}
</style>
</head>

<body>

	<table width="650" border="0" align="center" cellpadding="0"
		cellspacing="0" width="140" style="border: solid  #000000 1px">
		<tr height="17" align="center">
			<td height="17" width="140" style="border: solid  #000000 1px">
				品种类别
			</td>
			<td width="140" style="border: solid  #000000 1px">
				产品名称
			</td>
			<td width="160" style="border: solid  #000000 1px">
				价格
			</td>
			<td width="140" style="border: solid  #000000 1px">
				备注
			</td>
			<td width="120" style="border: solid  #000000 1px">
				发布日期
			</td>
		</tr>
		<logic:iterate id="list" name="list" indexId="i">
			<tr>
				<td height="17" width="140" style="border: solid  #000000 1px">
					<bean:write name="list" property="dictProductType2" />&nbsp;
				</td>
				<td width="160" style="border: solid  #000000 1px">
					<bean:write name="list" property="dictProductType1" />&nbsp;
				</td>
				<td width="140" style="border: solid  #000000 1px">
					<bean:write name="list" property="productPrice" />&nbsp;
				</td>
				<td width="140" style="border: solid  #000000 1px">
					<bean:write name="list" property="remark" />&nbsp;
				</td>
				<td width="120" style="border: solid  #000000 1px">
					<bean:write name="list" property="operTime" />&nbsp;
				</td>
			</tr>
		</logic:iterate>

	</table>

</body>
</html:html>
