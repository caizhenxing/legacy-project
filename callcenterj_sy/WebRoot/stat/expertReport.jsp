<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript">

    </script>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	%>
	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js"></script>
</head>

<body class="conditionBody">
	<table id="tbl1" width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr class="listTitleStyle">
			<td class="listTitleStyle" width="50%">
				ר��
			</td>
			<td class="listTitleStyle" width="50%">
			<logic:notEmpty name="querytype">
				<logic:equal name="querytype" value="ͳ�ƴ���">�������</logic:equal>
				<logic:equal name="querytype" value="ͳ��ʱ��">����ʱ��</logic:equal>
			</logic:notEmpty>
			
			<logic:empty name="querytype">ͳ�����</logic:empty>				
			</td>
		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr align="center" >
				<td>
					<bean:write name="c" property="X" filter="false" />
				</td>
				<td>
					<bean:write name="c" property="Sum1" filter="false" />
				</td>
			</tr>
		</logic:iterate>
		<logic:notEmpty name="list">
			<tr>
				<td class="evenStyle">
					�ܼ�
				</td>
				<td class="evenStyle">
					<bean:write name="c" property="num" filter="false" />
				</td>
			</tr>
		</logic:notEmpty>
		<tr>
			<td colspan="2" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
				<div style="text-align:right;">
					<input type="button" class="buttonStyle" value="�������" onclick="parseTbl('tbl1','ר�ҽ���ͳ��','<%=basePath%>')" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html:html>
