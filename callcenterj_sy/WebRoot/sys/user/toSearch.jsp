
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>系统用户管理应用</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/tools.js"></script>
	<script>
	document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
	function a()
		{
			popUp('windows',"UserOper.do?method=toAdd&type=i",800,800);
		}
		
		function sel()
		{

			document.forms[0].action="UserOper.do?method=userLoginList";
			document.forms[0].submit()
			window.close();
		}
		
	function dep()
		{
			select(document.forms[0].userName);
		}
		function select(obj)
   	 {
		
		var page = "../../sys/dep.do?method=select&value="+obj.value
		var winFeatures = "dialogWidth:600px; dialogHeight:600px;center:1; status:0";

		window.showModalDialog(page,obj,winFeatures);
	}
</script>
</head>

<body class="conditionBody">
	<html:form action="/sys/user/UserOper?method=search" target="mainFrame">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td colspan="9" class="navigateStyle">
					<bean:message bundle="sys" key="sys.current.page" />
					<bean:message bundle='sys' key='sys.user' />
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle"  style="text-align:center;">
					用户姓名
				</td>
				<td class="valueStyle">
					<html:text property="userName" size="10"></html:text>
<%--					<span style="display:none;"><img--%>
<%--							src="../../images/sysoper/particular.gif" onclick="dep()"--%>
<%--							width="16" height="16" border="0" /> </span>--%>
				</td>
				<td class="queryLabelStyle"  >
					座席工号
				</td>
				<td class="valueStyle">
					<html:select property="id">
						<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="queryLabelStyle">
					所在组
				</td>
				<td class="valueStyle">
					<html:select property="sysGroup" styleClass="selectStyle">
						<html:option value="">
							<bean:message bundle='sys' key='sys.pselect' />
						</html:option>
						<html:options collection="GroupList" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td class="queryLabelStyle">
					坐席角色
				</td>
				<td class="valueStyle">
					<html:select property="sysRole" styleClass="selectStyle">
						<html:option value="">
							<bean:message bundle='sys' key='sys.pselect' />
						</html:option>
						<html:options collection="RoleList" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td class="queryLabelStyle" align="center">
					<input type="button" name="btnsel"
						value="<bean:message bundle='sys' key='sys.select'/>"
						onclick="sel()" class="buttonStyle" />
					<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.mainFrame.document.location=parent.mainFrame.document.location;" />
					<input type="button" style="display:none" name="select" value="添加" class="buttonStyle"
						onclick="popUp('windowsAddUser','../../sys/user/UserOper.do?method=toUserLoginload&type=insert',750,310)" />
				</td>
				<tr height="1px">
				<td colspan="11" class="buttonAreaStyle">

				</td>
			</tr>				
			</tr>
		</table>
	</html:form>
</body>
</html:html>
