<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title></title>
		<link href="images/css/styleA.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/common.js"></script>
	</head>

	<body>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td>
					调查主题
				</td>
			</tr>
			<tr>
				<td class="tdbgpiclist">
					参与客户
				</td>
				<td class="tdbgpiclist">
					受理座席
				</td>
				<td class="tdbgpiclist">
					参与时间
				</td>
				<td class="tdbgpiclist">
					调查问题
				</td>
				<td class="tdbgpiclist">
					调查答案
				</td>

			</tr>
			<logic:iterate id="r" name="results" indexId="i">
				<%
							String style = i.intValue() % 2 == 0 ? "tdbgcolorlist1"
							: "tdbgcolorlist2";
				%>

				<tr>
					<td >
						<bean:write name="r" property="actor" filter="true" />
					</td>
					<td >
						<bean:write name="r" property="rname" filter="true" />
					</td>
					<td >
						<bean:write name="r" property="time" filter="true" />
					</td>
					<td >
						<bean:write name="r" property="question" filter="true" />
					</td>
					<td >
						<bean:write name="r" property="answer" filter="true" />
					</td>
				</tr>
			</logic:iterate>
		</table>
	</body>
</html>
