<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>��ͨ��������Ϣ��ѯ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
	</SCRIPT>


	<script type="text/javascript">
 function query()
 	{
 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}

 	
 	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus();
	}	
 </script>

</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/caseinfo/generalCaseinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��->��ͨ������
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
					<html:text property="beginTime" styleClass="writeTextStyle" size="15"/>
					<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('generalCaseinfoBean','beginTime',false);">
				</td>				
				
				<td class="labelStyle">
					����ר��
				</td>
				<td class="valueStyle">
					<%--<html:text property="caseExpert" styleClass="writeTextStyle" size="10"/>
				--%>
					<html:select property="caseExpert" style="width:155px">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="export">
							<html:option value="${u.custName}">${u.custName}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle">
					������
				</td>
				<td class="valueStyle">
					<%--<html:text property="caseRid" styleClass="writeTextStyle" size="10"/>--%>
					<html:select property="caseRid">
						<option value="">��ѡ��</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="labelStyle">
				��������
				</td>
				<td class="labelStyle" align="center">
					<html:text property="caseReview" styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					����ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle" size="15"/>
					<img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('generalCaseinfoBean','endTime',false);">
				</td>
				<td class="labelStyle">
					��ѯ����
				</td>
				<td class="valueStyle">
					<html:text property="caseContent" styleClass="writeTextStyle" style="width:155px"/>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:65px">
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
						
					</select>
				</td>
					<td class="labelStyle" align="center">
		
				</td>
				<td class="labelStyle" align="center">	
							<input type="button" name="btnSearch" value="��ѯ"
						class="buttonStyle" onclick="query()" />				
					<input type="reset" value="ˢ��" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
				</td>
			</tr>
			<tr height="1px">
				<td colspan="13" class="buttonAreaStyle">
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
