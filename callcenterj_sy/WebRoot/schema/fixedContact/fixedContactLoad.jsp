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
		opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>�ͻ�����</title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<%--		�������֤��JS�ļ�	--%>
		<SCRIPT language=javascript src="../js/form.js"></SCRIPT>
		<%--		�������������JS�ļ�	--%>
		<SCRIPT language=javascript src="../js/calendar3.js"
			type=text/javascript></SCRIPT>
		<script type="text/javascript">
			var url = "fixedContact.do?method=toFixedContactOper&type=";
			function checkForm(myForm)
    	{
    		//if (!checkNotNull(myForm.cust_rid,"��ϯ��"))return false;
    		//if (!checkNotNull(myForm.cust_name,"����"))return false;
    		//if (!checkNotNull(myForm.dict_sex,"�Ա�"))return false;
    		if (!checkNotNull(myForm.cust_develop_time,"��չʱ��"))return false;
    		//if (!checkNotNull(myForm.cust_identity_card,"���֤��"))return false;
    		//else if (checkNumberRange(myForm.cust_identity_card,"���֤��"))return false;//���֤�ű���ȫ��Ϊ����
    		//else if (checkNumberRange(myForm.cust_identity_card,"���֤��",18))return false;//���֤�ű���Ϊ18λ����
    			
    		//if (!checkNotNull(myForm.cust_addr,"��ַ"))return false;
    		
    		//��֤�ʱ�
    		//if (!checkNotNull(myForm.cust_pcode,"�ʱ�"))return false;
    		//else if (checkInteger(myForm.cust_pcode,"�ʱ�"))return false;//�ʱ����ȫ��Ϊ����
    		
    		//��֤E-mail
    		//if (!checkNotNull(myForm.cust_email,"E-mail"))return false;
    		//else if(checkEmail(myForm.cust_email,"E-mail"))return false;
    		
    		//if (!checkNotNull(myForm.cust_tel_home,"լ��"))return false;
    		//if (!checkNotNull(myForm.cust_tel_work,"�칫�绰"))return false;
    		//if (!checkNotNull(myForm.cust_voc,"�ͻ���ҵ"))return false;
    		//��֤��ע
    		//if (!checkNotNull(myForm.remark,"��ע"))return false;
    		//else if(checkLength(myForm.remark,"��ע",400))return false; 
    		return true;
    	}
			//���
   		function add()
   		{  
   			var f =document.forms[0];
   			if(checkForm(f))
   			{
   				f.action = url + "insert";
					f.submit();
				}	
   		}
	   	//�޸�
	   	function update()
	   	{
	   		var f =document.forms[0];
   			if(checkForm(f))
   			{
   				f.action = url + "update";
					f.submit();
				}	
	   	}
	   	//ɾ��
	   	function del()
	   	{
				if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
				{
			   	document.forms[0].action = url + "delete";
			   	document.forms[0].submit();
	   		}
	  	}
	  </script>
	</head>
	<body class="loadBody">
		<logic:equal name="opertype" value="insert">
			<%
			session.setAttribute("viewPicFlag", "insert");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="update">
			<%
			session.setAttribute("viewPicFlag", "update");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="detail">
			<%
			session.setAttribute("viewPicFlag", "detail");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="delete">
			<%
			session.setAttribute("viewPicFlag", "delete");
			%>
		</logic:equal>
		<html:form action="/schema/fixedContact.do" method="post">
			<html:hidden property="cust_id" />
			<html:hidden property="cust_pic_path" />
			<html:hidden property="cust_pic_name" />
			<html:hidden property="cust_type" value="SYS_TREE_0000000683" />
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle" colspan="3">
						��ǰλ��&ndash;&gt;��ũ�̶�����Ա����&ndash;&gt;��ӹ̶�����Ա&nbsp;&nbsp;
						<font color="red">��ע�⣺��*�ű�־Ϊ������</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;ϯ&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_rid" size="13"
							styleClass="writeTextStyle" disabled="true"/>
					</td>
					<%-- �ϴ�����ʾ�����ϴ�����Ƭ--%>
					<logic:equal name="opertype" value="insert">
						<td rowspan="6" class="valueStyle" width="40%" height="40%"
							align="right">
							<iframe src="fixedContact/fixedContactUpload.jsp" height="280"
								width="380" scrolling="no" frameborder="0"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<td rowspan="6" class="valueStyle" width="40%" height="40%"
							align="right">
							<iframe src="fixedContact/fixedContactUpload.jsp" height="240"
								width="380" scrolling="no" frameborder="0"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="detail">
						<td class="valueStyle" height="80" align="center" rowspan="3">
							<logic:equal name="viewPic" value="no">
								<b>��δ�ϴ�ͼƬ</b>
							</logic:equal>
							<logic:equal name="viewPic" value="yes">
								<a
									href="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>"
									target="_blank"><img
										src="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>
							"
										width="120" height="80" border="0" align="middle" /> </a>
							</logic:equal>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="delete">
						<td class="valueStyle" height="80" align="center" rowspan="3">
							<logic:equal name="viewPic" value="no">
								��δ�ϴ�ͼƬ
							</logic:equal>
							<logic:equal name="viewPic" value="yes">
								<a
									href="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>"
									target="_blank"><img
										src="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>
							"
										width="120" height="80" border="0" align="middle" /> </a>
							</logic:equal>
						</td>
					</logic:equal>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td width="139" class="valueStyle">
						<html:text property="cust_name" size="8"
							styleClass="writeTextStyle" />
						<font color="red">*��ʵ����</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:select property="dict_sex" styleClass="writeTextStyle">
							<html:options collection="sexList" property="value"
								labelProperty="label" />
						</html:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						��չʱ��
					</td>
					<td class="valueStyle" colspan="3">
						<html:text property="cust_develop_time"
							styleClass="writeTextStyle" />
						<logic:equal name="opertype" value="insert">
							<img alt="ѡ������" src="../html/img/cal.gif"
								onclick="openCal('fixedContact','cust_develop_time',false);">
							<font color="red">*�����Աߵ����ѡ������</font>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<img alt="ѡ������" src="../html/img/cal.gif"
								onclick="openCal('fixedContact','cust_develop_time',false);">
							<font color="red">*�����Աߵ����ѡ������</font>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						���֤��
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_identity_card" size="25"
							styleClass="writeTextStyle" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ַ
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_addr" size="50"
							styleClass="writeTextStyle" />
						<logic:equal name="opertype" value="insert">
							<input type="button" value="ѡ���ַ" class="buttonStyle"
								onclick="window.open('fixedContact/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
							<font color="red">*</font>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="button" value="ѡ���ַ" class="buttonStyle"
								onclick="window.open('fixedContact/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
							<font color="red">*</font>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_pcode" size="20"
							styleClass="writeTextStyle" />
						<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*����ȫ��Ϊ����</font>
					</td>

				</tr>
				<tr>
					<td class="labelStyle">
						&nbsp;E-mail&nbsp;
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*�磺aa@163.com</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						լ&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle" width="180">
						<html:text property="cust_tel_home" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*�磺02423448833(�޸���)��Ҫ�������ţ��޿ո��޸���</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						�칫�绰
					</td>
					<td class="valueStyle" width="180">
						<html:text property="cust_tel_work" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*�磺02423448833(�޸���)��Ҫ�������ţ��޿ո��޸���</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="180">
						�ͻ���ҵ
					</td>
					<td class="valueStyle">
						<html:text property="cust_voc" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*������(����ֳ300ͷ)��������ֲ30Ķ�����ʾ����̵�</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						��&nbsp;&nbsp;&nbsp;&nbsp;ע
					</td>
					<td colspan="2" class="valueStyle">
						<html:textarea property="remark" cols="50" rows="2"
							styleClass="writeTextStyle" />
						<font color="red">*��400������</font>
					</td>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="3" align="center">
						<logic:equal name="opertype" value="insert">
							<input type="button" name="Submit" value=" �� �� " onClick="add()"
								 class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "
								class="buttonStyle"/>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="button" name="Submit" value=" �� �� "
								onClick="update()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "
								 class="buttonStyle"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
							<input type="button" name="Submit" value=" ɾ �� " onClick="del()"
								 class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value=" �� �� "
							onClick="window.close()"  class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
