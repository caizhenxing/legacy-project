<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>����׷�ٿ�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>

	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>

	<script type="text/javascript">
function query()
 	{
 		document.forms[0].action="../focusPursue.do?method=toFocusPursueList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/focusPursue" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;����׷�ٿ�
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="labelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('focusPursueBean','beginTime',false);">
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="chiefTitle" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					���α༭
				</td>
				<td class="valueStyle">
					<html:text property="chargeEditor" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<select name="dictFocusType" class="selectStyle" style="width: 75px;">
						<option value="">ȫ��</option>
						<option>ʵ�°�</option>
						<option>�г���</option>
					</select>
				</td>
				<td class="labelStyle" align="center">
					<input type="button" name="btnSearch" class="buttonStyle" value="��ѯ" onclick="query()" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('focusPursueBean','endTime',false);">
				</td>

				<td class="labelStyle">
					ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ
				</td>
				<td class="valueStyle">
					<html:text property="summary" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="fucosContent" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle">
					<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option selected="selected" value="һ��">һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>
						<option>����</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option selected="selected" value="һ�󲵻�">һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>
						<option>����</option>
					<%
					}else if("waitagain".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option selected="selected" value="����">����</option>
						<option>���󲵻�</option>
						<option>����</option>
					<%
					}else if("backagain".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option selected="selected" value="���󲵻�">���󲵻�</option>
						<option>����</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>
						<option selected="selected">����</option>
					<%
					}else{
					%>
					<option value="" selected="selected">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>
						<option>����</option>
					<%
					}
					 %>
						
						
					</select>
				</td>
				<td class="labelStyle">
					<input type="reset" value="ˢ��" class="buttonStyle" onClick="parent.bottomm.document.location=parent.bottomm.document.location;"  >
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>