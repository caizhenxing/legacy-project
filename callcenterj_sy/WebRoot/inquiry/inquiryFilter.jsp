<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="../style.jsp"%>
<html:html locale="true">
<head>
<link href="style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
</head>
<body class="loadBody" onload="loadRight()">
<table width="100%">
	<tr class="navigateStyle">
		<td>
			备选调查列表
		</td>
	</tr>
<logic:iterate id="inquiry" name="list" indexId="i">
	<%
			String style = i.intValue() % 2 == 0 ? "labelStyle" : "valueStyle";
			%>
	<tr class="<%=style %>" style="text-indent: 0px;">
		<td class="labelStyle">
			<a id="a<%=i %>" href='inquiry.do?method=toDisplayCard&id=<bean:write name="inquiry" property="id" />' target="rightInquiryFrame"><bean:write name="inquiry" property="topic"/></a>
		</td>
	</tr>
</logic:iterate>
</table>
</body>
<script>
	function loadRight(){
		var obj = document.getElementById("a0");//a0是自动生成的
		parent.rightInquiryFrame.document.location = obj.href;
	}
</script>
</html:html>