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
					<%= i+1 %>、<bean:write name="list" property="custAddr"/> <bean:write name="list" property="custName"/> 
					<bean:write name="list" property="custTel"/> (<bean:write name="list" property="caseTime"/>)来电咨询：
					<bean:write name="list" property="caseContent"/> (<bean:write name="list" property="caseExpert"/>)<br>
					【解答】 <bean:write name="list" property="caseReply"/><br>
					【案例点评】 <bean:write name="list" property="caseReview"/><br>
					【备注】 <bean:write name="list" property="remark"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;					
				</td>
			</tr>
			<tr>
				<td>&nbsp;
				</td>
			</tr>
			<tr>
				<td>&nbsp;
				</td>
			</tr>
		</logic:iterate>
		
	</table>

</body>
</html:html>
