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
					��������
				</td>
			</tr>
			<tr>
				<td class="tdbgpiclist">
					����ͻ�
				</td>
				<td class="tdbgpiclist">
					������ϯ
				</td>
				<td class="tdbgpiclist">
					����ʱ��
				</td>
				<td class="tdbgpiclist">
					��������
				</td>
				<td class="tdbgpiclist">
					�����
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
