<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../images/css/styleA.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
</head>

<body>
	<html:form action="/sys/user/UserOper.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr class="tdbgpiclist">

				<td width="10%">
					用&nbsp;户&nbsp;名
				</td>
				<td width="10%">
					是否冻结
				</td>
				<td width="10%">
					组
				</td>
				<td width="10%">
					角&nbsp;&nbsp;&nbsp;&nbsp;色
				</td>
				<td>
					备&nbsp;&nbsp;&nbsp;&nbsp;注
				</td>
				<td width="10%">
					操&nbsp;&nbsp;&nbsp;&nbsp;作
				</td>
			</tr>
			<logic:iterate id="c" name="list" indexId="i">
				<%
							String style = i.intValue() % 2 == 0 ? "tdbgcolorlist1"
							: "tdbgcolorlist2";
				%>
				<tr>
					
					<td >
						<bean:write name="c" property="userName" filter="true" />
					</td>

					<td >
						<bean:write name="c" property="isFreeze" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="groupName" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="roleName" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="remark" filter="true" />
					</td>

					<td >
						<img alt="详细" src="../../images/sysoper/particular.gif"
							onclick="popUp('windowsAddUser','UserOper.do?method=toUserLoginload&type=detail&id=<bean:write name='c' property='id'/>',750,300)"
							width="16" height="16" border="0" />
						<img alt="修改" src="../../images/sysoper/update.gif"
							onclick="popUp('windowsAddUser','UserOper.do?method=toUserLoginload&type=update&id=<bean:write name='c' property='id'/>',750,300)"
							width="16" height="16" border="0" />
						<img alt="删除" src="../../images/sysoper/del.gif"
							onclick="popUp('windowsAddUser','UserOper.do?method=toUserLoginload&type=delete&id=<bean:write name='c' property='id'/>',750,300)"
							width="16" height="16" border="0" />
						<img alt="授权" src="../../images/sysoper/del.gif"
							onclick="popUp('windowsAddUser','right.do?method=loadUser&user=<bean:write name='c' property='id'/>',750,300)"
							width="16" height="16" border="0" />
					</td>


				</tr>
			</logic:iterate>
			<tr>
				<td colspan="6">
					<page:page name="userLoginTurning" style="second" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
