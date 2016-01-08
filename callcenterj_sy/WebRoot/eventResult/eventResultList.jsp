<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../js/common.js"></script>
</head>

<body class="listBody">

	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle">
				事件标题
			</td>
			<td class="listTitleStyle">
				反馈内容
			</td>
			<td class="listTitleStyle">
				负责人
			</td>
			<td class="listTitleStyle">
				受理座席
			</td>
			<td class="listTitleStyle">
				联络员
			</td>
			<td class="listTitleStyle">
				反馈时间
			</td>
			<td class="listTitleStyle" width="77">
				操作
			</td>
		</tr>
		<logic:iterate id="pagelist" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr style="line-height: 21px;">
				<td>
					<bean:write name="pagelist" property="topic" />
				</td>
				<td>
					<bean:write name="pagelist" property="feedback" />
				</td>
				<td>
					<bean:write name="pagelist" property="principal" />
				</td>
				<td>
					<bean:write name="pagelist" property="linkman_id" />
				</td>
				<td>
					<bean:write name="pagelist" property="linkman" />
				</td>
				<td>
					<bean:write name="pagelist" property="feedback_date" />
				</td>
				<td>

					<img alt="详细" src="../style/<%=styleLocation%>/images/detail.gif"  width="16" height="16" border="0" 
						onclick="popUp('','eventResult.do?method=toEventResultLoad&type=detail&id=<bean:write name="pagelist" property="id"/>',500,175)"/>
					<img alt="修改" src="../style/<%=styleLocation%>/images/update.gif"  width="16" height="16" border="0" 
						onclick="popUp('','eventResult.do?method=toEventResultLoad&type=update&id=<bean:write name='pagelist' property='id'/>',550,175)"/>
					<img alt="删除" src="../style/<%=styleLocation%>/images/del.gif"  width="16" height="16" border="0" 
						onclick="popUp('','eventResult.do?method=toEventResultLoad&type=delete&id=<bean:write name='pagelist' property='id'/>',500,175)"/>
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="7" class="pageTable">
				<page:page name="eventResultTurning" style="second" />
			</td>
		</tr>
	</table>

</body>
</html:html>

