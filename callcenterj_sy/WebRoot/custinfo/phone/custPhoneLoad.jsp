<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
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
<title>�ͻ�����</title>
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
<script type='text/javascript' src='../../js/msg.js'></script>
<script type="text/javascript">
	function checkForm(custinfo){
            if (!checkNotNull(custinfo.cust_name,"����")) return false;
            if (!checkNotNull(custinfo.cust_addr,"��ַ")) return false;
            if (!checkNotNull(custinfo.cust_voc,"�ͻ���ҵ")) return false;
            
              return true;
    }
	var url = "custinfo.do?method=toPhoneOper&type=";
	//���
   	function add(){
   		var f =document.forms[0];
    	if(checkForm(f)){
	   		document.forms[0].action = url + "insert";
			document.forms[0].submit();
		}
   	}
   	//�޸�
   	function update(){
   		var f =document.forms[0];
    	if(checkForm(f)){
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

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody">
<html:form action="custinfo/custinfo.do" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td width="139" class="valueStyle">
      <html:text property="cust_name" size="13" styleClass="input"/>
      <html:hidden property="cust_id"/></td>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="valueStyle">
      <html:select property="dict_sex">
        <html:options collection="sexList" property="value" labelProperty="label"/>
      </html:select>      </td>
    </tr>
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ַ</td>
    <td colspan="3" class="valueStyle"><html:text property="cust_addr" size="46" styleClass="input"/></td>
    </tr>
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="valueStyle">
      <html:text property="cust_pcode" size="5" styleClass="input"/>
    </td>
    <td class="labelStyle">&nbsp;E_mail&nbsp;</td>
    <td class="valueStyle">   
      <html:text property="cust_email" size="13" styleClass="input"/>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">լ&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="valueStyle"><html:text property="cust_tel_home" size="13" styleClass="input"/></td>
    <td class="labelStyle">�칫�绰</td>
    <td class="valueStyle">
      <html:text property="cust_tel_work" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
    <td class="valueStyle"><html:text property="cust_tel_mob" size="13" styleClass="input"/></td>
    <td class="labelStyle">�������</td>
    <td class="valueStyle">
      <html:text property="cust_fax" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">�ͻ���ҵ</td>
    <td class="valueStyle"><html:text property="cust_voc" size="13" styleClass="input"/></td>
    <td class="labelStyle">��ҵ��ģ</td>
    <td class="valueStyle">
      <html:text property="cust_scale" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">�ͻ�����</td>
    <td colspan="3" class="valueStyle">
      <html:select property="cust_type">
        <html:options collection="typeList" property="value" labelProperty="label"/>
      </html:select>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" �� �� " onClick="add()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" �� �� " onClick="update()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"   class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
