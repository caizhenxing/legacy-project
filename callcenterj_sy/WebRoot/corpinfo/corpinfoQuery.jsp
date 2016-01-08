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

	<title>��ҵ��Ϣ��</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
	</SCRIPT>
	<script language="javascript" src="../js/common.js"></script>

	<script language="javascript" src="../js/clock.js"></script>

	<script type="text/javascript">
 function add()
 	{
 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../operCorpinfo.do?method=toOperCorpinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 </script>

</head>

<body class="conditionBody">
	<html:form action="/operCorpinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;��ҵ��Ϣ��
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">

			<tr>
				<td class="labelStyle">
					��ʼʱ��
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('operCorpinfoBean','createTime',false);">
				</td>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<html:select property="corpRid" style="width:95px">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
					<%--<html:text property="corpRid" styleClass="writeTextStyle" size="10"/>--%>
				</td>
				<td class="labelStyle">
					��ѯ����
				</td>
				<td class="valueStyle">
					<html:text property="contents" styleClass="writeTextStyle" />
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
						<option>ԭʼ</option>
						<option selected="selected">����</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>ԭʼ</option>
						<option>����</option>
						<option selected="selected">����</option>
						<option>����</option>
						<option>����</option>
					<%
					}else if("pass".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>ԭʼ</option>
						<option>����</option>
						<option>����</option>
						<option selected="selected">����</option>
						<option>����</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">ȫ��</option>
						<option>ԭʼ</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
						<option selected="selected">����</option>
					<%
					}else{
					%>
					<option value="" selected="selected">ȫ��</option>
						<option>ԭʼ</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
						<option>����</option>
					<%
					}
					 %>
<%--						<option value="">ȫ��</option>--%>
<%--						<option>ԭʼ</option>--%>
<%--						<option>����</option>--%>
<%--						<option>����</option>--%>
<%--						<option>����</option>--%>
<%--						<option>����</option>--%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" />
					<img alt="ѡ��ʱ��" src="../html/img/cal.gif"
						onclick="openCal('operCorpinfoBean','endTime',false);">
				</td>
				
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:select property="dictServiceType" styleClass="selectStyle" style="width:95px">
						<html:option value="">
	    				��ѡ��
	    				</html:option>
						<html:options collection="ServiceList" property="value"
							labelProperty="label" />

					</html:select>
				</td>

				<td class="labelStyle">
					���ߴ�
				</td>
				<td class="valueStyle">
					<html:text property="reply" styleClass="writeTextStyle" />
				</td>
				
				<td class="labelStyle" colspan="2" align="center">
					<input type="button" name="btnSearch" value="��ѯ" class="buttonStyle"
						  onclick="query()" />
					<input type="reset" value="ˢ��"  class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>

			<tr height="1px">
				<td colspan="10" class="buttonAreaStyle">

				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>

