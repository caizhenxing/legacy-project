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

</head>

<body>

	<table width="650" border="0" align="center" cellpadding="1"
		cellspacing="1">

		<logic:iterate id="list" name="list" indexId="i">
			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" style="border: solid  #000000 1px">
						<tr height="17">
							<td height="17" width="64" style="border: solid  #000000 1px">
								所属大类
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseAttr1"/>&nbsp;
							</td>
							<td width="64" style="border: solid  #000000 1px">
								受理专家
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseExpert"/>
							</td>
							<td width="64" style="border: solid  #000000 1px">
								用户姓名
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custName"/>
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								所属小类
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseAttr2"/>&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								受理工号
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseRid"/>
							</td>
							<td style="border: solid  #000000 1px">
								用户电话
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custTel"/>
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								所属种类
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseAttr3"/>&nbsp;
							</td>
							<td style="border: solid  #000000 1px">
								受理时间
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseTime"/>
							</td>
							<td style="border: solid  #000000 1px">
								用户地址
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custAddr"/>
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								受理问题
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseContent"/>&nbsp;
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								问题答案
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseReply"/>&nbsp;
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								备注
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="remark"/>&nbsp;
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
