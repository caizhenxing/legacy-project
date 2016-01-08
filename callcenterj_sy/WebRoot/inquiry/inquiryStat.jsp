<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html>
	<head>
		<link href="style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	</head>

<body class="listBody">

		<table width="100%">

			<logic:iterate id="card" name="cards" indexId="i">
			<%
			String style = i.intValue() % 2 == 0 ? "labelStyle" : "valueStyle";
			%>
				<tr class="<%=style %>" style="text-indent: 0px;">
					<td width="8%" align="center">
						<b><bean:write name="card" property="questionTypeLabel" /></b>
					</td>
					<td width="20%">
						<bean:write name="card" property="question" />
						<input name="cardIds" id="cardIds" type="hidden" value='<bean:write name="card" property="id" />'>
						<input name="questionTypes" id="questionTypes" type="hidden" value='<bean:write name="card" property="questionType" />'>
						<input name="questionS" id="questionS" type="hidden" value='<bean:write name="card" property="question" />'>
					</td>

					<logic:equal name="card" property="questionType" value="001">
						<td>
							<table width="100%" height="100%" class="<%=style %>" style="text-indent: 0px;">
							<logic:iterate id="alternative" name="card" property="alternatives">
								<tr>
									<td width="30%"><bean:write name="alternative" property="answer"/></td>
									<td width="60%">
										<img src="style/<%=styleLocation%>/images/inquiryReport01.gif" 
										width=<bean:write name="alternative" property="imgWidth"/>% height="10">
									</td>
									<td width="10%"><bean:write name="alternative" property="count"/>´Î</td>
								</tr>
							</logic:iterate>
							</table>
						</td>
					</logic:equal>

					<logic:equal name="card" property="questionType" value="002">
						<td>
							<table width="100%" height="100%" class="<%=style %>" style="text-indent: 0px;">
							<logic:iterate id="alternative" name="card" property="alternatives">
								<tr>
									<td width="30%"><bean:write name="alternative" property="answer"/></td>
									<td width="60%">
										<img src="style/<%=styleLocation%>/images/inquiryReport01.gif" 
										width=<bean:write name="alternative" property="imgWidth"/>% height="10">
									</td>
									<td width="10%"><bean:write name="alternative" property="count"/>´Î</td>
								</tr>
							</logic:iterate>
							</table>
						</td>
					</logic:equal>

					<logic:equal name="card" property="questionType" value="003">
						<td>
							<table width="100%" height="100%" class="<%=style %>" style="text-indent: 0px;">
							<logic:iterate id="alternative" name="card" property="alternatives">
								<tr>
									<td><bean:write name="alternative"/></td>
								</tr>
							</logic:iterate>
							</table>
						</td>
					</logic:equal>

				</tr>
			</logic:iterate>

		</table>

</body>
</html>
