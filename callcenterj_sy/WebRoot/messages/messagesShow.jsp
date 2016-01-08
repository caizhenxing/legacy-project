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
		alert("操作成功");
		window.close();
	</script>
</logic:notEmpty>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>短消息内容</title>

		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
			
		<script type="text/javascript" src="../js/et/tools.js"></script>
			
		<script type="text/javascript">
			//改变区域位置，点击回复显示回复内容
			function showCallback(){
				getObjById('btnCancelId').style.display = "inline";
				getObjById('backDiv').style.display = "inline";
				getObjById('sendDiv').style.display = "none";
				getObjById('btnSubId').style.display = "inline";
				getObjById('btnCallbackId').style.display = "none";
				getObjById('btnCancelId').style.display = "inline";
				getObjById('message_content_callback').value = "";
			}
			//显示取消
			function showCancel(){
				getObjById('backDiv').style.display = "none";
				getObjById('sendDiv').style.display = "inline";
				getObjById('btnSubId').style.display = "none";
				getObjById('btnCallbackId').style.display = "inline";
				getObjById('btnCancelId').style.display = "none";
			}
			//点击确定发送
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
					当前位置&ndash;&gt;
					<span id="spanHead">查看消息</span>
				</td>
			</tr>
		</table>
		<html:form action="/messages/messages" method="post"
			styleId="messages">
			<div id="sendDiv" style="display:inline;">
			<table width="100%" border="0" align="center" class="contentTable">
				<tr>
					<td class="labelStyle">
						消息内容
					</td>
					<td class="valueStyle">
						<bean:write name="messages" property="send_name"/>
						发送给您一条消息，消息的内容是<br/>
						<html:textarea property="message_content" cols="50" rows="4" styleClass="writeTextStyle" styleId="message_content"/>
					</td>
				</tr>
			</table>
			</div>
			
			<div id="backDiv" style="display:none;">
			<table width="100%" border="0" align="center" class="contentTable">
				<tr>
					<td class="labelStyle">
						回复消息内容
					</td>
					<td class="valueStyle">
						您回复给<bean:write name="messages" property="send_name"/>的消息内容
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
						<input id="btnSubId" type="button" name="btnCallback" value="确定发送"
							onClick="subToSend()" style="display:none;">
						<input id="btnCallbackId" type="button" name="btnCallback" value=" 回 复 "
							onClick="showCallback()" style="display:inline;">
						<input id="btnCancelId" type="button" name="btnCancel" value=" 取 消 "
							onClick="showCancel()" style="display:none;">
						&nbsp;
						<input type="button" name="btnClose" value=" 关 闭 "
							onClick="javascript:window.close()">
					</td>
				</tr>
			</table>

		</html:form>
	</body>
</html>
