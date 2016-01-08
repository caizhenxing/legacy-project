<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>������Ϣ����</title>

	<link href="./../../style/<%=styleLocation%>/style.css"
		rel="stylesheet" type="text/css" />
	<SCRIPT language=javascript src="../../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
<%--	<script language="javascript" src="../../js/common.js"></script>--%>
<%--	<script language="javascript" src="../../js/clock.js"></script>--%>
	<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>
	<link REL=stylesheet href="../../markanainfo/css/divtext.css"
		type="text/css" />
<%--	<script language="JavaScript" src="../../markanainfo/js/divtext.js"></script>--%>

	<script language="JavaScript" type="text/javascript">
<%--	function add(){--%>
<%--		if(checkForm()){--%>
<%--		  document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=insert";--%>
<%--		  document.forms[0].submit();--%>
<%--		}--%>
<%--		--%>
<%--	}--%>
<%--	function update(){--%>
<%--	  if(checkForm()){--%>
<%--		document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=update";--%>
<%--		document.forms[0].submit();--%>
<%--	  }--%>
<%--	}--%>
<%--	function del(){--%>
<%--		document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=delete";--%>
<%--		document.forms[0].submit();--%>
<%--	}--%>
	function toback()
	{
		//opener.parent.topp.document.all.btnSearch.click();
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
	}

	function openCalendar(formName,targetName){
		var cal=new calendar3(document.forms[formName].elements[targetName],window.event);
		cal.year_scroll = true;
		cal.time_comp = false;
		cal.popup();
	}
		function checkForm(userAccuseBean){
        	if (!checkNotNull(sssFormBean.sssTitle,"��������")) return false; 
        	if (!checkNotNull(sssFormBean.sssSummary,"��������")) return false;
        	if (!checkNotNull(sssFormBean.sssWritter,"׫����")) return false;

           return true;
        }
     function add()
	{
	   var f =document.forms[0];
      if(checkForm(f)){
     document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=insert";
	 document.forms[0].submit();
	 }
	}
	function update()
	{
	  var f =document.forms[0];
     if(checkForm(f)){
	 document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=update";
	 document.forms[0].submit();
	 }
	}
	function del()
	{
		if(confirm("Ҫ����ɾ������ �뵥��--ȷ��, ȡ�������뵥��--ȡ��")){
		  document.forms[0].action="../../screen/scrspesur.do?method=toSSSOper&opertype=delete";
		  document.forms[0].submit();
		}
	}
</script>

</head>
<body onunload="toback()" class="loadBody">

	<logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�"); window.close();
    </script>
	</logic:notEmpty>
	<html:form action="/screen/scrspesur.do" method="post">
		<html:hidden property="sssId" />
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;������Ϣ����
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:text property="sssTitle" styleClass="writeTextStyle"
						size="30" />
				</td>
			</tr>
			<logic:notEqual value="insert" name="opertype">
				<tr>
					<td class="labelStyle">
						�����ύʱ��
					</td>
					<td class="valueStyle">
						<html:text property="sssCreateTime" styleClass="writeTextStyle"
							size="30" readonly="true"/>
					</td>
				</tr>
			</logic:notEqual>
			<tr>
				<td class="labelStyle">
					׫����
				</td>
				<td class="valueStyle">
					<html:text property="sssWritter" styleClass="writeTextStyle"
						size="30" />
				</td>
			</tr>
			<logic:notEqual value="insert" name="opertype">
				<tr>
					<td class="labelStyle">
						����޸�ʱ��
					</td>
					<td class="valueStyle">
						<html:text property="sssUpdateTime" styleClass="writeTextStyle"
							size="30"  readonly="true"/>
					</td>
				</tr>
			</logic:notEqual>
			<tr>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<html:text property="sssEmaple" styleClass="writeTextStyle"
						size="30" />
				</td>
			</tr>
			<tr>
					<td class="labelStyle">
						����ؼ���
					</td>
					<td class="valueStyle">
						<html:text property="sssKeywords" styleClass="writeTextStyle"
							size="30" />
					</td>
				</tr>
			<tr>
				<td class="labelStyle">
					ί�в���
				</td>
				<td class="valueStyle" >
					<html:text property="sssDelegeteDep" styleClass="writeTextStyle"
						size="50" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��Ҫ����
				</td>
				<td class="valueStyle">
					<html:textarea property="sssSummary" styleClass="writeTextStyle"
						rows="10" cols="60" />
				</td>
			</tr>
			<tr>
				<td class="navigateStyle" colspan="4" style="text-align:right;">
					<logic:equal value="delete" name="opertype">
						<input type="button" value="ɾ��" onclick="del()"
							class="buttonStyle" />
					</logic:equal>
					<logic:equal value="insert" name="opertype">
						<input type="button" value="���" onclick="add()"
							class="buttonStyle" />
					</logic:equal>
					<logic:equal value="update" name="opertype">
						<input type="button" value="�޸�" onclick="update()"
							class="buttonStyle" />
					</logic:equal>
					<input type="button" value="�ر�" onclick="window.close()"
						class="buttonStyle" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
