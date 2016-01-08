<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js"></script>
		    <script language="javascript" src="../../js/et/style.js"></script>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript">

    </script>
</head>

<body class="conditionBody">
	<table id="tbl1" width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr class="listTitleStyle">
			<td class="listTitleStyle" >
				栏目名称
			</td>
			<td class="listTitleStyle" >
				留言量
			</td>
			<td class="listTitleStyle" >
				点播量
			</td>
			<td class="listTitleStyle" >
				订制量
			</td>
			<td class="listTitleStyle" >
				退订量
			</td>
		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr align="center" >
				<td width="120px" align="left" style="padding-left:10px;">
					<bean:write name="c" property="menu" filter="false" />
				</td>
				<td>
					<bean:write name="c" property="lvNum" filter="false" />
				</td>
				<td>
					<bean:write name="c" property="dbNum" filter="false" />
				</td>
				<td>
					<bean:write name="c" property="dzNum" filter="false" />
				</td>
				<td>
					<bean:write name="c" property="tdNum" filter="false" />
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="5" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
				<div style="text-align:right;">
					<input type="button" class="buttonStyle" value="导出Excel" onclick="parseTbl('tbl1','语音留言统计','<%=basePath%>')" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
