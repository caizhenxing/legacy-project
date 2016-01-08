<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>调查信息分析库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		 	table{
	border: 1px solid #000000;
	 	}
	 	
		td{
			border:1px solid #000000;
		}
	</style>
	</style>
</head>

<body>
	<logic:iterate id="inquiry" name="list">

		<table width="100%" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					标&nbsp;&nbsp;&nbsp;&nbsp;题
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="reportTopic" />
				</td>
				<td class="labelStyle">
					副&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="inquiry" property="reportTopic2" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					调查主题
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="topic" />
				</td>
				<td class="labelStyle">
					组&nbsp;织&nbsp;者
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="inquiry" property="organizers" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					发起机构
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="organiztion" />
				</td>
				<td class="labelStyle">
					参与人员
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="inquiry" property="actors" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					撰&nbsp;稿&nbsp;人
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="reportCopywriter" />
				</td>
				<td class="labelStyle">
					关&nbsp;键&nbsp;字
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="inquiry" property="reportKeyword" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					调查时间
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="beginTime" />
					至
					<bean:write name="inquiry" property="endTime" />
				</td>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="inquiry" property="reportAbstract" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					调查样本
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="reportSwatch" />
				</td>
				<td class="labelStyle">
					调查有效率
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="reportEfficiency" />
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<bean:write name="inquiry" property="reportState" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					报告正文
				</td>
				<td class="valueStyle" colspan="5">
					<bean:write name="inquiry" property="reportContent" />
				</td>
			</tr>
			<br />
			<tr>
				<td class="labelStyle">
					报告评论
				</td>
				<td class="valueStyle" colspan="5">
					<bean:write name="inquiry" property="reportReview" />
				</td>
			</tr>
			<br />
			<tr>
				<td class="labelStyle">
					备 注
				</td>
				<td class="valueStyle" colspan="5">
					<bean:write name="inquiry" property="reportRemark" />
				</td>
			</tr>
		</table>
		<br />
		<br />
	</logic:iterate>
</body>
</html:html>
