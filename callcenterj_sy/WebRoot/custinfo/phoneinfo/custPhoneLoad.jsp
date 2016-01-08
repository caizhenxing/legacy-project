<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>�绰������</title>
		<script type='text/javascript' src='../../js/msg.js'></script>
		<SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
   		</SCRIPT>
		<script type="text/javascript">
		function checkForm(){ 
			var cust_name=document.forms[0].cust_name.value;
			var pattern = /(^(\d{2,4}[-_����]?)?\d{3,8}([-_����]?\d{3,8})?([-_����]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;
			var home_tel=document.forms[0].cust_tel_home.value;
			var mob_tel=document.forms[0].cust_tel_mob.value;
			var work_tel=document.forms[0].cust_tel_work.value;
			var email=document.forms[0].cust_email.value;
			var pattern2 = /^.+@.+\..+$/;
			var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;
			var cust_pcode=document.forms[0].cust_pcode.value;
			if(cust_name.length==0){
				alert("�û���������Ϊ��");
				document.forms[0].cust_name.focus();
				return false;
			}
			
			
		 	if (cust_pcode!=""&&!pattern1.test(cust_pcode)) {
		 		alert("�����������Ϊ�Ϸ�����");
		 		document.forms[0].cust_pcode.focus();
		 		document.forms[0].cust_pcode.select();
		 		return false;
		 	}			
		 	if (home_tel!=""&&!pattern.test(home_tel)) {
		 		alert("����ȷ��дסլ�绰���룡");
		 		document.forms[0].cust_tel_home.focus();
		 		document.forms[0].cust_tel_home.select();
		 		return false;
		 	}
		 	
		 	if (mob_tel!=""&&!pattern.test(mob_tel)) {
		 		alert("����ȷ��д�ֻ��绰���룡");
		 		document.forms[0].cust_tel_mob.focus();
		 		document.forms[0].cust_tel_mob.select();
		 		return false;
		 	}
		 	
		 	if (work_tel!=""&&!pattern.test(work_tel)) {
		 		alert("����ȷ��д�����绰���룡");
		 		document.forms[0].cust_tel_work.focus();
		 		document.forms[0].cust_tel_work.select();
		 		return false;
		 	}
		    
			if (email!=""&&!pattern2.test(email)) {
				alert("����ȷ��дemail");
				document.forms[0].cust_email.focus();
				document.forms[0].cust_email.select();
				return false;
			}         
            return true;
   		}
		var url = "phoneinfo.do?method=toPhoneOper&type=";
		var selectUrl = "custinfo/phoneinfo.do?method=toPhoneLoad&custType=";
		function selectType()
		{
			var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			selectUrl = "phoneinfo.do?method=toPhoneLoad&type="+operType+"&custType=";
			document.forms[0].action = selectUrl + type;
			document.forms[0].submit();
		}
		//���
   	function add(){
    	if(checkForm()){
	   		document.forms[0].action = url + "insert";
			document.forms[0].submit();
		}
   	}
   	//�޸�
   	function update(){
    	if(checkForm()){
	   		document.forms[0].action = url + "update";
			document.forms[0].submit();
		}
   	}
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		}
   	}
	</script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	</head>
	<%
	String opertype = (String) request.getAttribute("opertype");
	%>
	<body class="loadBody">
		<html:form action="/custinfo/phoneinfo.do" method="post">
		<html:hidden property="cust_id" />
		<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td colspan="2" class="labelStyle">
						ѡ���û�����
					</td>
					<td class="valueStyle">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle"
							onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td width="139" class="valueStyle">
						<html:text property="cust_name" size="13"
							styleClass="writeTypeStyle" /><font color="red">*</font>
						<html:hidden property="cust_id" />
					</td>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:select property="dict_sex" styleClass="writeTypeStyle">
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ַ
					</td>
					<td colspan="3" class="valueStyle">
						<html:text property="cust_addr" size="13"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" size="13"
							styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						E_mail&nbsp;
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" size="13"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						լ&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_home" size="13"
							styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						�칫�绰
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_work" size="13"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" size="13"
							styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						�������
					</td>
					<td class="valueStyle">
						<html:text property="cust_fax" size="13"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�ͻ���ҵ
					</td>
					<td class="valueStyle">
						<html:text property="cust_voc" size="13"
							styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						��ҵ��ģ
					</td>
					<td class="valueStyle">
						<html:text property="cust_scale" size="13"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�ͻ�����
					</td>
					<td colspan="3" class="valueStyle">
						<html:select property="cust_type" styleClass="writeTypeStyle">
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ע
					</td>
					<td colspan="3" class="valueStyle">
						<html:textarea property="remark" cols="50" rows="3"
							styleClass="writeTypeStyle" />
						<br>
					</td>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center">
						<%
							if (opertype != null && !"".equals(opertype.trim()))
							{
								if ("insert".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" �� �� " onClick="add()"
							class="buttonStyle">
						<%
								}
								else if ("update".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" ȷ �� "
							onClick="update()" class="buttonStyle">
						<%
								}
								else if ("delete".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" ɾ �� " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						%>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value=" �� �� "
							class="buttonStyle">
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input type="button" name="Submit3" value=" �� �� "
							onClick="javascript:window.close()" class="buttonStyle">
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
