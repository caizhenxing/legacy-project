<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		window.close();
	</script>
</logic:notEmpty>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>����Ϣ����</title>

		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
			
		<script type="text/javascript" src="../js/et/tools.js"></script>
			
		<script type="text/javascript">
			//�ı�����λ�ã�����ظ���ʾ�ظ�����
			function showCallback(){
				getObjById('btnCancelId').style.display = "inline";
				getObjById('backDiv').style.display = "inline";
				getObjById('sendDiv').style.display = "none";
				getObjById('btnSubId').style.display = "inline";
				getObjById('btnCallbackId').style.display = "none";
				getObjById('btnCancelId').style.display = "inline";
				getObjById('message_content_callback').value = "";
			}
			//��ʾȡ��
			function showCancel(){
				getObjById('backDiv').style.display = "none";
				getObjById('sendDiv').style.display = "inline";
				getObjById('btnSubId').style.display = "none";
				getObjById('btnCallbackId').style.display = "inline";
				getObjById('btnCancelId').style.display = "none";
			}
			//���ȷ������
			function subToSend(){
				document.forms[0].action = "messages.do?method=toMessagesOper&type=callback";
    			document.forms[0].submit();
			}
		</script>

	</head>

	<body class="loadBody">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;
					<span id="spanHead">�鿴��Ϣ</span>
				</td>
			</tr>
		</table>
		<html:form action="/messages/messages" method="post"
			styleId="messages">
			<div id="sendDiv" style="display:inline;">
			<table width="100%" border="0" align="center" class="contentTable">
				<tr>
					<td class="labelStyle">
						��Ϣ����
					</td>
					<td class="valueStyle">
						<bean:write name="messages" property="send_name"/>
						���͸���һ����Ϣ����Ϣ��������<br/>
						<html:textarea property="message_content" cols="50" rows="4" styleClass="writeTextStyle" styleId="message_content"/>
					</td>
				</tr>
			</table>
			</div>
			
			<div id="backDiv" style="display:none;">
			<table width="100%" border="0" align="center" class="contentTable">
				<tr>
					<td class="labelStyle">
						�ظ���Ϣ����
					</td>
					<td class="valueStyle">
						���ظ���<bean:write name="messages" property="send_name"/>����Ϣ����
						<br/>
						<html:textarea property="message_content_back" cols="50" rows="4" styleClass="writeTextStyle" styleId="message_content_callback"/>
						<html:hidden property="send_id"/>
					</td>
				</tr>
			</table>
			</div>
			
			<table width="100%" border="0" align="center" class="contentTable">
				<tr class="buttonAreaStyle">
					<td colspan="2" align="center">
						<input id="btnSubId" type="button" name="btnCallback" value="ȷ������"
							onClick="subToSend()" style="display:none;">
						<input id="btnCallbackId" type="button" name="btnCallback" value=" �� �� "
							onClick="showCallback()" style="display:inline;">
						<input id="btnCancelId" type="button" name="btnCancel" value=" ȡ �� "
							onClick="showCancel()" style="display:none;">
						&nbsp;
						<input type="button" name="btnClose" value=" �� �� "
							onClick="javascript:window.close()">
					</td>
				</tr>
			</table>

		</html:form>
	</body>
</html>
