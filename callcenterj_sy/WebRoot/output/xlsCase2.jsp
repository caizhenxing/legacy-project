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
					<table cellspacing="0" cellpadding="0"
						style="border: solid  #000000 1px">
						<tr height="17">
							<td height="17" width="64" style="border: solid  #000000 1px">
								会诊主题
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseContent"/>
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								用户姓名
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custName"/>
							</td>
							<td style="border: solid  #000000 1px">
								用户电话
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custTel"/>
							</td>
							<td style="border: solid  #000000 1px">
								用户地址
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="custAddr"/>
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								受理专家
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseExpert"/>
							</td>
							<td style="border: solid  #000000 1px">
								受理工号
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseRid"/>
							</td>
							<td style="border: solid  #000000 1px">
								受理时间
							</td>
							<td width="105" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseTime"/>
							</td>
						</tr>
						<tr height="17">
							<td height="17" style="border: solid  #000000 1px">
								参诊单位及人员
							</td>
							<td colspan="5" width="338" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseJoins"/>
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								会诊过程
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseReply"/>
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								案例点评
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseReview"/>
							</td>
						</tr>
						<tr height="66">
							<td height="66" style="border: solid  #000000 1px">
								相关报道
							</td>
							<td colspan="5" width="443" style="border: solid  #000000 1px">
								<bean:write name="list" property="caseReport"/>
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
