<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>����ר������Ϣ����</title>

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
	function initPhoto()
	{
		document.getElementById('ehExpertPic').value=window.frames["iframeUpload"].document.getElementById('filePathName').value;
	}
	function openCalendar(formName,targetName){
		var cal=new calendar3(document.forms[formName].elements[targetName],window.event);
		cal.year_scroll = true;
		cal.time_comp = false;
		cal.popup();
	}
		function checkForm(userAccuseBean){
        	if (!checkNotNull(expertGroupHLBean.ehCallName,"ר�ҳƺ�")) return false; 
        	if (!checkNotNull(expertGroupHLBean.ehExpertZone,"��������")) return false;
        	if (!checkNotNull(expertGroupHLBean.ehExpertSummary,"ר�Ҽ��")) return false;
        	if (!checkNotNull(expertGroupHLBean.ehType,"ר������")) return false;

           return true;
        }
     function add()
	{
	initPhoto();
	   var f =document.forms[0];
      if(checkForm(f)&&isNumRangOk()){
     document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=insert";
	 document.forms[0].submit();
	 }
	}
	function update()
	{
	initPhoto();
	  var f =document.forms[0];
     if(checkForm(f)&&isNumRangOk()){
	 document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=update";
	 document.forms[0].submit();
	 }
	}
	function del()
	{
		if(confirm("Ҫ����ɾ�������뵥��--ȷ��, ȡ�������뵥��--ȡ��")){
		  document.forms[0].action="../../screen/expertGroupHL.do?method=toExpertGroupHLOper&opertype=delete";
		  document.forms[0].submit();
		}
	}
	function isNumRangOk()
	{
		var i = document.getElementById("ehAgreeLevel").value;
		if(i>0&&i<=100)
		{
			return true;
		}
		alert("֧���ʱ�����0��100������");
		return false;
	}
	
<%--	function checknull()--%>
<%--	{--%>
<%--		var val = document.getElementById("ehType").value;--%>
<%--		if(val=="")--%>
<%--		{--%>
<%--			alert("��ѡ�����ͣ�");--%>
<%--			return��--%>
<%--		}--%>
<%--	}--%>
	
</script>

</head>
<body onunload="toback()" class="loadBody">

	<logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�"); window.close();
    </script>
	</logic:notEmpty>
	<html:form action="/screen/expertGroupHL.do" method="post">
		<html:hidden property="ehId"/>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;����ר������Ϣ����
				<br></td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					ר�ҳƺ�
				<br>
				</td>
				<logic:equal value="insert" name="opertype">
				<td class="valueStyle">
					<html:text property="ehCallName" styleClass="writeTextStyle"
						size="30" />&nbsp;<font color="red">*</font>
				<br>
				</td>
				</logic:equal>
				<logic:notEqual value="insert" name="opertype">
				<td class="valueStyle">
					<html:text property="ehCallName" styleClass="writeTextStyle"
						size="30" readonly="true"/>&nbsp;<font color="red">*</font>
				<br>
				</td>
				</logic:notEqual>
			</tr>
			<!-- ######begin -->
			
			<tr>
					<td class="labelStyle">����ʦ��Ƭ</td>
					<td class="valueStyle" >
						<logic:equal name="opertype" value="insert">
						    <input type="hidden" name="ehExpertPic" id="ehExpertPic" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle"/>--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../../fileUpload/fileUpload.jsp" allowTransparency="true" ></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="hidden" name="ehExpertPic" id="ehExpertPic" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle" value="<%=request.getAttribute("photoPath").toString() %>">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle" />--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
						<html:text property="ehExpertPic" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
						<html:text property="ehExpertPic" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<img src="../../../<bean:write name="expertGroupHLBean" property="ehExpertPic" />" width="50" height="50" />
				    </td>
				</tr>
			<!-- ######end -->
			<tr>
				<td class="labelStyle">
					��������
				<br></td>
				<td class="valueStyle">
					<html:text property="ehExpertZone" styleClass="writeTextStyle" size="30" />&nbsp;<font color="red">*</font>
				<br></td>
			</tr>
			<tr>
					<td class="labelStyle">
						ר��֧����
					<br></td>
					<td class="valueStyle">
						<html:text property="ehAgreeLevel" styleClass="writeTextStyle"
							size="10" />&nbsp;%&nbsp;<font color="red">*</font>
					<br></td>
				</tr>
<%--			<logic:notEqual value="insert" name="opertype">--%>
<%--				<tr>--%>
<%--					<td class="labelStyle">--%>
<%--						����޸�ʱ��--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:text property="sssUpdateTime" styleClass="writeTextStyle"--%>
<%--							size="30"  readonly="true"/>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--			</logic:notEqual>--%>
				<tr>
					<td class="labelStyle">
						ר������
					</td>
					<td class="valueStyle">
						<html:select property="ehType" styleClass="writeTextStyle">
						    <html:option value="">��ѡ������</html:option>
							<html:option value="ũ��">ũ��</html:option>
							<html:option value="����">����</html:option>
						</html:select>&nbsp;<font color="red">*</font>

					</td>
				</tr>
			<tr>
				<td class="labelStyle">
					ר�Ҽ��
				<br></td>
				<td class="valueStyle"><html:textarea property="ehExpertSummary" styleClass="writeTextStyle"
						rows="10" cols="80" />&nbsp;<font color="red">*</font>
				<br></td>
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
