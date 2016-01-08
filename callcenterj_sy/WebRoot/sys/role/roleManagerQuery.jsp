<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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

	<title>角色查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../../js/tools.js"></script>
	<script language="javascript">
   
    	//查询
    	function query(){
    		document.forms[0].action = "../role/Role.do?method=toRoleList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    		window.close();
    	}
    	
    </script>


</head>

<body class="conditionBody">

	<html:form action="/sys/role/Role" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td colspan="4" class="navigateStyle">
					当前位置->角色管理
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle" width="128px">
					角色名称
				</td>
				<td class="valueStyle">
					<html:text property="name" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle" width="102px" style="text-aligh:right;">
					<input name="btnSearch" type="button" class="buttonStyle"
						value="查询" onclick="query()" />
					&nbsp;
					<input type="reset" value="刷新" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
					<input style="display:none;" name="btnAdd" type="button" class="buttonStyle" value="添加"
						onclick="popUp('windows','../role/Role.do?method=toRoleLoad&type=insert',400,180)" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
