<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>������Ϣ�б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>

</head>
<body class="listBody" >
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" style="width:80px;">
				��Ѷ����
			</td>
			<td class="listTitleStyle">
				��Ѷ����
			</td>
			<td class="listTitleStyle" width="200">
				����ʱ��
			</td>
			<td class="listTitleStyle" width="80">
				����
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
		String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr style="line-height: 21px;">
				<td>
					<bean:write name="c" property="msgTitle" />
				</td>
				<td>
					<bean:write name="c" property="msgContent" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="createDate" filter="true" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<img alt="��ϸ" src="../../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='id'/>','quickMessage.do?method=toQMLoad&type=detail&id=<bean:write name='c' property='id'/>',800,195)"
						width="16" height="16" border="0" />
					<img alt="�޸�" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('focusCas<bean:write name='c' property='id'/>einfoWindows','quickMessage.do?method=toQMLoad&type=update&id=<bean:write name='c' property='id'/>',800,195)"
						width="16" height="16" border="0" />
					<img alt="ɾ��" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('2<bean:write name='c' property='id'/>','quickMessage.do?method=toQMLoad&type=delete&id=<bean:write name='c' property='id'/>',800,195)"
						width="16" height="16" border="0" />
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td class="pageTable">
			</td>
			<td colspan="2" class="pageTable">
				<page:page name="userpageTurning" style="second" />
			</td>
			<td class="pageTable" style="margin-right:5px;padding-right:5px;">
				<div style="width:30px;display:inline;"></div><input type="button" name="btnadd" value="���" class="buttonStyle"
					onclick="popUp('focusCaseinfoWindows','quickMessage.do?method=toQMLoad&type=insert',800,195)" />
			</td>
		</tr>
	</table>

</body>
</html:html>
