<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>
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
<title>��ͨ�û�����</title>
<script type='text/javascript' src='../js/common.js'></script>
<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
<!-- jquery��֤ -->
<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
	    
<script type="text/javascript">
	var v_flag="";
	function formAction(){
		if(v_flag=="del"){
			if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
				return true;
			else
				return false;
		}
	}
	
	//��ʼ��
	function init(){	
		<logic:equal name="opertype" value="detail">
			document.getElementById('buttonSubmit').style.display="none";
		</logic:equal>		
		<logic:equal name="opertype" value="insert">
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=insert";
			document.getElementById('spanHead').innerHTML="�����ͨ�û���Ϣ";
			document.getElementById('buttonSubmit').value=" �� �� ";
		</logic:equal>
		<logic:equal name="opertype" value="update">
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=update";
			document.getElementById('spanHead').innerHTML="�޸���ͨ�û���Ϣ";
			document.getElementById('buttonSubmit').value=" �� �� ";
		</logic:equal>
		<logic:equal name="opertype" value="delete">
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=delete";
			document.getElementById('spanHead').innerHTML="ɾ����ͨ�û���Ϣ";
			document.getElementById('buttonSubmit').value=" ɾ �� ";
			v_flag="del"
		</logic:equal>		
	}
	//ִ����֤
		
	<logic:equal name="opertype" value="insert">
	$(document).ready(function(){  
		$.formValidator.initConfig({formid:"custinfo",onerror:function(msg){alert(msg)}});	
		$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
		$("#cust_addr").formValidator({onshow:"�������ַ",onfocus:"��ַ����Ϊ��",oncorrect:"��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ַ���߲����пշ���"},onerror:"��ַ����Ϊ��"});
<%--		$("#cust_pcode").formValidator({empty:true,onshow:"�������ʱ࣬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ʱ�",onempty:"û����д�ʱ�"}).inputValidator({min:100000,max:999999,type:"value",onerror:"�ʱ��ʽ����ȷ"});				--%>
<%--		$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��024-87654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"סլ�绰��ʽ����ȷ"});--%>
<%--		$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});--%>
<%--		$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"��ȷ��ʽ��024-87654321",oncorrect:"�칫�绰�Ϸ�",onempty:"û����д�칫�绰"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"�칫�绰��ʽ����ȷ"});--%>
<%--		$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
	})
	</logic:equal>
	<logic:equal name="opertype" value="update">
	$(document).ready(function(){
		$.formValidator.initConfig({formid:"custinfo",onerror:function(msg){alert(msg)}});	
		$("#cust_name").formValidator({onshow:"����������",onfocus:"��������Ϊ��",oncorrect:"�����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"��������Ϊ��"});
		$("#cust_addr").formValidator({onshow:"�������ַ",onfocus:"��ַ����Ϊ��",oncorrect:"��ַ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ַ���߲����пշ���"},onerror:"��ַ����Ϊ��"});
<%--		$("#cust_pcode").formValidator({empty:true,onshow:"�������ʱ࣬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ʱ�",onempty:"û����д�ʱ�"}).inputValidator({min:100000,max:999999,type:"value",onerror:"�ʱ��ʽ����ȷ"});				--%>
<%--		$("#cust_tel_home").formValidator({empty:true,onshow:"������סլ�绰",onfocus:"��ȷ��ʽ��024-87654321",oncorrect:"סլ�绰�Ϸ�",onempty:"û����дסլ�绰"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"סլ�绰��ʽ����ȷ"});--%>
<%--		$("#cust_tel_mob").formValidator({empty:true,onshow:"�������ֻ����룬����Ϊ��",onfocus:"������룬����������ȷ",oncorrect:"�ֻ�����",onempty:"û����д�ֻ�����"}).inputValidator({min:11,max:11,onerror:"�ֻ����������11λ��"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"�ֻ������ʽ����ȷ"});--%>
<%--		$("#cust_tel_work").formValidator({empty:true,onshow:"������칫�绰",onfocus:"��ȷ��ʽ��024-87654321",oncorrect:"�칫�绰�Ϸ�",onempty:"û����д�칫�绰"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"�칫�绰��ʽ����ȷ"});--%>
<%--		$("#cust_email").formValidator({empty:true,onshow:"����������",onfocus:"����6����,���100����",oncorrect:"������ȷ",onempty:"û����д����"}).inputValidator({min:6,max:100,onerror:"��������䳤�ȷǷ�"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"�����ʽ����ȷ"});--%>
	
	})
	</logic:equal>
	


<%--	function checkForm(){ --%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			var pattern = /(^(\d{2,4}[-_����]?)?\d{3,8}([-_����]?\d{3,8})?([-_����]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--			var pattern2 = /^.+@.+\..+$/;--%>
<%--			var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_pcode=document.forms[0].cust_pcode.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("�û���������Ϊ��");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--			if(document.forms[0].cust_addr.value.length==0){--%>
<%--				alert("�û���ַ����Ϊ��");--%>
<%--				document.forms[0].cust_addr.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--		 	if (cust_pcode!=""&&!pattern1.test(cust_pcode)) {--%>
<%--		 		alert("�����������Ϊ�Ϸ�����");--%>
<%--		 		document.forms[0].cust_pcode.focus();--%>
<%--		 		document.forms[0].cust_pcode.select();--%>
<%--		 		return false;--%>
<%--		 	}			--%>
<%--		 	if (home_tel!=""&&!pattern.test(home_tel)) {--%>
<%--		 		alert("����ȷ��дסլ�绰���룡");--%>
<%--		 		document.forms[0].cust_tel_home.focus();--%>
<%--		 		document.forms[0].cust_tel_home.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (mob_tel!=""&&!pattern.test(mob_tel)) {--%>
<%--		 		alert("����ȷ��д�ֻ��绰���룡");--%>
<%--		 		document.forms[0].cust_tel_mob.focus();--%>
<%--		 		document.forms[0].cust_tel_mob.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (work_tel!=""&&!pattern.test(work_tel)) {--%>
<%--		 		alert("����ȷ��д�����绰���룡");--%>
<%--		 		document.forms[0].cust_tel_work.focus();--%>
<%--		 		document.forms[0].cust_tel_work.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		    --%>
<%--			if (email!=""&&!pattern2.test(email)) {--%>
<%--				alert("����ȷ��дemail");--%>
<%--				document.forms[0].cust_email.focus();--%>
<%--				document.forms[0].cust_email.select();--%>
<%--				return false;--%>
<%--			}         --%>
<%--            return true;--%>
<%--   		}--%>
<%--	var url = "custinfo.do?method=toCustinfoOper&type=";--%>
<%--	//���--%>
<%--   	function add(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "update";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init();">
<html:form action="custinfo/custinfo.do" method="post" styleId="custinfo" onsubmit="return formAction();">

 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="navigateStyle">
<%--		    	<logic:equal name="opertype" value="insert">--%>
<%--		    		��ǰλ��&ndash;&gt;�����ͨ�û���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="detail">--%>
		    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴��ͨ�û���Ϣ</span>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="update">--%>
<%--		    		��ǰλ��&ndash;&gt;�޸���ͨ�û���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--		    	 <logic:equal name="opertype" value="delete">--%>
<%--		    		��ǰλ��&ndash;&gt;ɾ����ͨ�û���Ϣ--%>
<%--		    	</logic:equal>--%>
		    </td>
		  </tr>
		</table>

<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">����</td>
    <td class="valueStyle">
      <html:text property="cust_name" size="8" styleClass="input" styleId="cust_name"/>
		<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
      <html:hidden property="cust_id"/></td>
    <td class="labelStyle">�Ա�</td>
    <td class="valueStyle">
      <html:select property="dict_sex">
        <html:options collection="sexList" property="value" labelProperty="label"/>
      </html:select>      </td>
    </tr>
  <tr>
    <logic:equal name="opertype" value="insert">
    	<td class="labelStyle">��ַ</td>
    	<td class="valueStyle" colspan="5">
			<html:text property="cust_addr" size="25" styleClass="writeTextStyle" styleId="cust_addr"/>
			<input type="button" value="ѡ���ַ" onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
			<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
		</td>
    </logic:equal>
    
    <logic:equal name="opertype" value="update">
    	<td class="labelStyle">��ַ</td>
    	<td class="valueStyle" colspan="5">
			<html:text property="cust_addr" size="25" styleClass="writeTextStyle" styleId="cust_addr"/>
			<input type="button" value="ѡ���ַ" onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
			<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
		</td>
    </logic:equal>
    
    <logic:equal name="opertype" value="detail">
    	<td class="labelStyle">��ַ</td>
    	<td colspan="3" class="valueStyle"><html:text property="cust_addr" size="46" styleClass="input"/></td>
    </logic:equal>
  </tr>
  <tr>
    <td class="labelStyle">�ʱ�</td>
    <td class="valueStyle">
      	<html:text property="cust_pcode" size="5" styleClass="input" styleId="cust_pcode"/>
		<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
    </td>
    <td class="labelStyle">&nbsp;E_mail&nbsp;</td>
    <td class="valueStyle">   
      	<html:text property="cust_email" size="13" styleClass="input" styleId="cust_email"/>
		<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">լ��</td>
    <td class="valueStyle">
	    <html:text property="cust_tel_home" size="13" styleClass="input"  styleId="cust_tel_home"/>
		<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
	</td>
    <td class="labelStyle">�칫�绰</td>
    <td class="valueStyle">
      <html:text property="cust_tel_work" size="13" styleClass="input" styleId="cust_tel_work"/>
	  <div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">�ֻ�</td>
    <td class="valueStyle">
	    <html:text property="cust_tel_mob" size="11" styleClass="input" styleId="cust_tel_mob"/>
		<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
	</td>
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
		<html:select property="cust_type" styleClass="Next_pulls" style="width: 131px;">
			<html:option value="SYS_TREE_0000002109">��ͨũ��</html:option>
			<html:option value="SYS_TREE_0000002103">ר��</html:option>
			<html:option value="SYS_TREE_0000002104">��ҵ</html:option>
			<html:option value="SYS_TREE_0000002105">ý��</html:option>
			<html:option value="SYS_TREE_0000002106">����</html:option>
			<html:option value="SYS_TREE_0000002108">����Ա</html:option>
		</html:select>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">��ע</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">
<input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle"/>
  	 <logic:equal name="opertype" value="insert">
<%--      <input type="button" name="Submit" value=" �� �� " onClick="add()"   class="buttonStyle"/>--%>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>
	 </logic:equal>
<%--	 --%>
	 <logic:equal name="opertype" value="update">
<%--      <input type="button" name="Submit" value=" ȷ �� " onClick="update()"   class="buttonStyle"/>--%>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>
	 </logic:equal>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	 </logic:equal>--%>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
