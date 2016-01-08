<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>�г�������</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>

	<script language="javascript" src="../js/clockCN.js"></script>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
<link REL=stylesheet href="../markanainfo/css/divtext.css" type="text/css"/>
<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>


	<script type="text/javascript">
function query()
 	{
 		document.forms[0].action="../markanainfo.do?method=toMarkanainfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 	
 
 </script>

</head>

<body class="conditionBody">
	<html:form action="/markanainfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;�г�������
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
					<html:text property="beginTime" styleClass="writeTextStyle" size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('markanainfoBean','beginTime',false);">
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
					<html:text property="chargeEditor" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:select property="dictCommentType" styleClass="selectStyle" style="width: 75px;">
						<html:option value="">ȫ��</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
						<html:option value="����">����</html:option>
					</html:select>
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
					<html:text property="endTime" styleClass="writeTextStyle" size="10" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('markanainfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ
				</td>
				<td class="valueStyle">
					<html:text property="summary" styleClass="writeTextStyle" size="10"/>
				</td>
				<td class="labelStyle" >
					Ʒ&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
<%--						<html:text property="dictProductType" styleClass="writeTextStyle" size="10"/>--%>
						<html:select property="dictProductType" styleClass="writeTextStyle">
							<option value="">��ѡ��</option>
							<html:options collection="list" property="value"
								labelProperty="label" />
						</html:select>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle">
					<%
					String str_state = request.getParameter("state");
					if("firstDraft".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option selected="selected">����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>						
						<option>����</option>
					<%
					}else if("firstCensor".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option selected="selected">һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>						
						<option>����</option>
					<%
					}else if("firstCensorBack".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option selected="selected">һ�󲵻�</option>
						<option>����</option>
						<option>���󲵻�</option>						
						<option>����</option>
					<%
					}else if("secondCensor".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option selected="selected">����</option>
						<option>���󲵻�</option>						
						<option>����</option>
					<%
					}else if("secondCensorBack".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>����</option>
						<option>һ��</option>
						<option>һ�󲵻�</option>
						<option>����</option>
						<option selected="selected">���󲵻�</option>						
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
<%--						<option value="">ȫ��</option>--%>
<%--						<option>����</option>--%>
<%--						<option>һ��</option>--%>
<%--						<option>һ�󲵻�</option>--%>
<%--						<option>����</option>--%>
<%--						<option>���󲵻�</option>--%>
<%--						<option>����</option>--%>
<%--						<option>���󲵻�</option>--%>
<%--						<option>����</option>--%>
					</select>
				</td>
				<td class="labelStyle" align="center">
					<input type="reset" class="buttonStyle" value="ˢ��"  >
				</td>
			</tr>
			<tr>
				<td class="labelStyle" height="1px" colspan="9"></td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>