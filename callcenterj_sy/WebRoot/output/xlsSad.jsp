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

	<table width="650" border="0" align="center" cellpadding="1"
		cellspacing="1" class="tablebgcolor" class="listTable">

		<logic:iterate id="list" name="list" indexId="i">
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0"
						style="border: solid  #000000 1px">
						<tr height="17">
							<td height="17" width="64" style="border: solid  #000000 1px">
								联 系 人
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="custName" />&nbsp;
							</td>
							<td width="64" style="border: solid  #000000 1px">
								供求类型
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="dictSadType" />&nbsp;
							</td>
							<td width="64" style="border: solid  #000000 1px">
								有效期至
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="deployEnd" />&nbsp;
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								联系电话
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="custTel" />&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								产品名称
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="productName" />&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								发布日期
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="deployBegin" />&nbsp;
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								联系地址
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="custAddr" />&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								产品规格
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="productScale" />&nbsp;
							</td>
							<td rowspan="2" style="border: solid  #000000 1px">
								备注
							</td>
							<td rowspan="2" width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="remark" />&nbsp;
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								邮编
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="post" />&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								产品数量
							</td>
							<td width="175" style="border: solid  #000000 1px">
								<bean:write name="list" property="productCount" />&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</logic:iterate>

	</table>

</body>
</html:html>
