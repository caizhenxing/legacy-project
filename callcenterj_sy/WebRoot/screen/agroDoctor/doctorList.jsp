<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
</head>

<body class="listBody">

	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle">
				医疗保健常识
			</td>
			<td class="listTitleStyle">
				普通疾病识别防治方法与措施
			</td>
			<td class="listTitleStyle" width="120">
				操作
			</td>
		</tr>
		<logic:iterate id="pagelist" name="list" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>
			<tr style="line-height: 21px;"> 
				<td>
					<div style="width:300px;height:20px;overflow:hidden;"><bean:write name="pagelist" property="docType" /></div>
				</td>
				<td>
					<div style="width:300px;height:20px;overflow:hidden;"><bean:write name="pagelist" property="docContent" /></div>
				</td>
				<td>
					<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"  width="16" height="16" border="0" 
						onclick="popUp('1<bean:write name="pagelist" property="id"/>','screenDoctor.do?method=toMarAnalysisLoad&type=detail&id=<bean:write name="pagelist" property="id"/>',700,500)"/>
					<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"  width="16" height="16" border="0" 
						onclick="popUp('2<bean:write name="pagelist" property="id"/>','screenDoctor.do?method=toMarAnalysisLoad&type=update&id=<bean:write name='pagelist' property='id'/>',700,500)"/>
					<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"  width="16" height="16" border="0" 
						onclick="popUp('3<bean:write name="pagelist" property="id"/>','screenDoctor.do?method=toMarAnalysisLoad&type=delete&id=<bean:write name='pagelist' property='id'/>',700,500)"/>
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td colspan="5" class="pageTable">
				<page:page name="phonepageTurning" style="second" />
			</td>
		</tr>
	</table>

</body>
</html:html>

