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

	<title>快信信息列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>

</head>
<body class="listBody" onload="equalsCheckbox()">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" >
				标题
			</td>
			<td class="listTitleStyle">
				内容
			</td>
			<td class="listTitleStyle" width="200">
				创建日期
			</td>
			<td class="listTitleStyle" width="80">
				操作
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
		String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr style="line-height: 21px;">
				<td>
					<input name="educe" type="checkbox" id="educe"
						onclick="setCheckbox(this)"
						value="<bean:write name='c' property='caseId'/>">
				</td>
				<td>
					<bean:write name="c" property="caseTime" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseAttr4" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseContent" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="caseReply" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="state" filter="true" />
				</td>
				<td>
					<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=detail&id=<bean:write name='c' property='caseId'/>',800,475)"
						width="16" height="16" border="0" />
					<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id=<bean:write name='c' property='caseId'/>',800,475)"
						width="16" height="16" border="0" />
					<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='caseId'/>','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=delete&id=<bean:write name='c' property='caseId'/>',800,475)"
						width="16" height="16" border="0" />
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="4" class="pageTable">
				<jsp:include flush="true" page="../../output/inc.jsp?dbType=focus" />
			</td>
			<td colspan="2" class="pageTable">
				<page:page name="focusCaseinfopageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
				<input type="button" name="btnstatistic" value="统计" class="buttonStyle"
					onclick="popUp('statisticWindows','focusCaseinfo.do?method=toPopStatistic',500,40)" />
				<input type="button" name="btnadd" value="添加" class="buttonStyle"
					onclick="popUp('focusCaseinfoWindows','focusCaseinfo.do?method=toFocusCaseinfoLoad&type=insert',800,475)" />
			</td>
		</tr>
	</table>

</body>
</html:html>
