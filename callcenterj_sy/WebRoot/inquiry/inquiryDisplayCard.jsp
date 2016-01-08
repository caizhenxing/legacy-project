<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="../style.jsp"%>
<html:html locale="true">
<head>
	<title></title>

<link href="style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<script>
function doSubmit(){
	document.forms[0].submit();
}
</script>
</head>
<body  class="loadBody" onload='document.getElementById("actor").value = parent.parent.document.getElementById("cust_name").value'>
	<html:form action="/inquiryResult.do?method=toSave" method="post">
		
		<input name="inquiryId" id="inquiryId" type="hidden" value='<bean:write name="inquiryId" />'>
		
		<table width="100%" height="100%">

			<logic:iterate id="card" name="cards" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "labelStyle" : "valueStyle";
			%>
				<tr class="<%=style %>" style="text-indent: 0px;">
					<td width="20%">
						<bean:write name="card" property="question" />
						<input name="cardIds" id="cardIds" type="hidden" value='<bean:write name="card" property="id" />'>
						<input name="questionTypes" id="questionTypes" type="hidden" value='<bean:write name="card" property="questionType" />'>
						<input name="questionS" id="questionS" type="hidden" value='<bean:write name="card" property="question" />'>
					</td>
				
					<logic:equal name="card" property="questionType" value="001">
						<td>
							<logic:iterate id="alternative" name="card" property="alternatives">
								<input name='<bean:write name="card" property="id" />' type="radio" value='<bean:write name="alternative" />'>
								<bean:write name="alternative" />
							</logic:iterate>
						</td>
					</logic:equal>
					
					<logic:equal name="card" property="questionType" value="002">
						<td>
							<logic:iterate id="alternative" name="card" property="alternatives">
								<input name='<bean:write name="card" property="id" />' type="checkbox" value='<bean:write name="alternative"/>'>
								<bean:write name="alternative" />
							</logic:iterate>
						</td>
					</logic:equal>
					
					<logic:equal name="card" property="questionType" value="003">
						<td>
							<textarea name='<bean:write name="card" property="id"/>' cols="40"></textarea>
						</td>
					</logic:equal>
					
				</tr>
			</logic:iterate><%--
			<tr>
				<td>
					<input type="submit" value="Ìá½»" >
					<input type="button" value="¹Ø±Õ"
						onclick="javascript:window.opener=null;window.close();">
				</td>
			</tr>
		--%></table>
	</html:form>
</body>
</html:html>
