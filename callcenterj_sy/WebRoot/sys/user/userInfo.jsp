<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>��ϯ����</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<link href="../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%--<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
<script language="javascript" src="../../js/form.js"></script>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
<script type="text/javascript">
		//��ʼ��
		function init(){	
			<logic:equal name="opertype" value="detail">
			document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=insert";
				document.getElementById('spanHead').innerHTML="�����ϯ��Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=update";
				document.getElementById('spanHead').innerHTML="�޸���ϯ��Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ����ϯ��Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
			</logic:equal>		
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"UserOper",onerror:function(msg){alert(msg)}});
			$("#userId").formValidator({onshow:"��������ϯ����",onfocus:"��ϯ���Ų���Ϊ��",oncorrect:"��ϯ���źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�������߲����пշ���"},onerror:"��ϯ���Ų���Ϊ��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});	
			$("#sysRole").formValidator({onshow:"��ѡ����ϯ��ɫ",onfocus:"��ϯ��ɫ����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ����ϯ��ɫ!"});
			$("#sysGroup").formValidator({onshow:"��ѡ��������",onfocus:"���������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ��������!"});			
			$("#password").formValidator({onshow:"����������",onfocus:"���벻��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���벻��Ϊ��"});
			$("#repassword").formValidator({onshow:"��������֤����",onfocus:"�����������һ��",oncorrect:"����һ��"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��֤�������߲����пշ���"},onerror:"��֤���벻��Ϊ��"}).compareValidator({desid:"password",operateor:"=",onerror:"2�����벻һ��"});
			$("#isFreeze").formValidator({onshow:"��ѡ���Ƿ񶳽�",onfocus:"�Ƿ񶳽����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ���Ƿ񶳽�!"});
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"UserOper",onerror:function(msg){alert(msg)}});
			$("#userId").formValidator({onshow:"��������ϯ����",onfocus:"��ϯ���Ų���Ϊ��",oncorrect:"��ϯ���źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�������߲����пշ���"},onerror:"��ϯ���Ų���Ϊ��"});
			$("#userName").formValidator({onshow:"�������û�����",onfocus:"�û���������Ϊ��",oncorrect:"�û������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�û��������߲����пշ���"},onerror:"�û���������Ϊ��"});	
			$("#sysRole").formValidator({onshow:"��ѡ����ϯ��ɫ",onfocus:"��ϯ��ɫ����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ����ϯ��ɫ!"});
			$("#sysGroup").formValidator({onshow:"��ѡ��������",onfocus:"���������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ��������!"});			
			$("#password").formValidator({onshow:"����������",onfocus:"���벻��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���벻��Ϊ��"});
			$("#repassword").formValidator({onshow:"��������֤����",onfocus:"�����������һ��",oncorrect:"����һ��"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��֤�������߲����пշ���"},onerror:"��֤���벻��Ϊ��"}).compareValidator({desid:"password",operateor:"=",onerror:"2�����벻һ��"});
			$("#isFreeze").formValidator({onshow:"��ѡ���Ƿ񶳽�",onfocus:"�Ƿ񶳽����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ���Ƿ񶳽�!"});
		})
		</logic:equal>


<%--  function checkForm(addstaffer){  	--%>
<%--        	--%>
<%--        	if (!checkNotNull(addstaffer.userId,"ID")) return false;--%>
<%--        	if (!checkNotNull(addstaffer.userName,"�û���")) return false;--%>
<%--        	 if (addstaffer.password.value !=addstaffer.repassword.value)--%>
<%--            {--%>
<%--            	alert("��������֤�벻һ��");--%>
<%--            	return false;--%>
<%--            }--%>
<%--			return true;--%>
<%--        }--%>
<%--	function openwin(param)--%>
<%--		{--%>
<%--		   var aResult = showCalDialog(param);--%>
<%--		   if (aResult != null)--%>
<%--		   {--%>
<%--		     param.value  = aResult;--%>
<%--		   }--%>
<%--		}--%>
<%--		--%>
<%--		function showCalDialog(param)--%>
<%--		{--%>
<%--		   var url="<%=request.getContextPath()%>/html/calendar.html";--%>
<%--		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");--%>
<%--		   if (aRetVal != null)--%>
<%--		   {--%>
<%--		      return aRetVal;--%>
<%--		   }--%>
<%--		   return null;--%>
<%--		}--%>
<%--	    	//���--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=insert";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	--%>
<%--    	//�޸�--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//ɾ��--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--	--%>
		
	function toback(){
			opener.parent.topp.document.all.btnsel.click();
		}
</SCRIPT>
  </head>
  
  <body onunload="toback()" class="loadBody" onload="init();"><br>
  <logic:notEmpty name="operSign">
	  <script>
	  	alert("�����ɹ���"); 
	  	opener.parent.topFrame.document.all.btnsel.click();
	  	window.close();
	  </script>
	</logic:notEmpty>
  <html:form action="/sys/user/UserOper" styleId="UserOper">
<html:hidden property="id"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
	
	<tr>
		<td class="navigateStyle">
		��ǰλ��&ndash;&gt;<span id="spanHead">��ϸ</span>
		<%-- String type = request.getParameter("type"); if(type==null){out.print("��ϯ����");} if("insert".equals(type.trim())){out.print("�����ϯ��Ϣ");}if("update".equals(type.trim())){out.print("�޸���ϯ��Ϣ");}if("delete".equals(type.trim())){out.print("ɾ����ϯ��Ϣ");} if("detail".equals(type.trim())){out.print("��ϯ��ϸ��Ϣ");}--%>
		</td>
	</tr>

</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
  	<td class="labelStyle">
  		��ϯ����	
  	</td>
  	<td class="valueStyle">
	<logic:equal name="opertype" value="insert">
   		<html:text property="userId" styleClass="writeTextStyle" styleId="userId"></html:text>
   		<div id="userIdTip" style="width: 10px;display:inline;"></div>
   	</logic:equal>  	
   	<logic:notEqual name="opertype" value="insert">
   		<html:text property="userId" styleClass="writeTextStyle" readonly="true"></html:text>
   	</logic:notEqual>
<%--  	<html:text property="userId" styleClass="writeTextStyle"></html:text>--%>
  	</td>
  	<td width="400" height="100%" rowspan="8">
  		<table width="100%" height="100%" style="border: solid  #000000 1px">
  			<tr height="20">
  				<td class="labelStyle" colspan="2">
  					���Ȩ�޹���
  				</td>
  			</tr>
  			<tr height="20">
  				<td width="48" class="labelStyle" style="text-indent: 0;">
  					��䰸��
  				</td>
  				<td class="valueStyle">
  					��ͨ������<html:checkbox property="auditings" value="��ͨ������"/>
  					���㰸����<html:checkbox property="auditings" value="���㰸����"/>
  					���ﰸ����<html:checkbox property="auditings" value="���ﰸ����"/>
  					Ч��������<html:checkbox property="auditings" value="Ч��������"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					����۸�
  				</td>
  				<td class="valueStyle">
  					ũ��Ʒ�����<html:checkbox property="auditings" value="ũ��Ʒ�����"/>
  					ũ��Ʒ�۸��<html:checkbox property="auditings" value="ũ��Ʒ�۸��"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					��ũ��Ʒ
  				</td>
  				<td class="valueStyle">
  					����׷�ٿ� 
  					һ��<html:checkbox property="auditings" value="����׷�ٿ�һ��"/>
  					����<html:checkbox property="auditings" value="����׷�ٿ����"/>
  					����<html:checkbox property="auditings" value="����׷�ٿ�����"/>
  					<br>
  					�г������� 
  					һ��<html:checkbox property="auditings" value="�г�������һ��"/>
  					����<html:checkbox property="auditings" value="�г����������"/>
  					����<html:checkbox property="auditings" value="�г�����������"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					��ҵ����
  				</td>
  				<td class="valueStyle">
  					��ҵ��Ϣ��<html:checkbox property="auditings" value="��ҵ��Ϣ��"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					 ҽ�Ʒ���
  				</td>
  				<td class="valueStyle">
  					��ͨҽ�Ʒ�����Ϣ��<html:checkbox property="auditings" value="��ͨҽ�Ʒ�����Ϣ��"/>
  					ԤԼҽ�Ʒ�����Ϣ��<html:checkbox property="auditings" value="ԤԼҽ�Ʒ�����Ϣ��"/>
  				</td>
  			</tr>
  			<tr>
  				<td class="labelStyle" style="text-indent: 0;">
  					ר�����
  				</td>
  				<td class="valueStyle">
  					�����ʾ���ƿ�<html:checkbox property="auditings" value="�����ʾ���ƿ�"/>
  					������Ϣ������<html:checkbox property="auditings" value="������Ϣ������"/>
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		�û�����	
  	</td>
  	<td class="valueStyle">
  		<html:text property="userName" styleClass="writeTextStyle" styleId="userName"></html:text>
  		<div id="userNameTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		��ϯ��ɫ
  	</td>
  	<td class="valueStyle">
  		<html:select property="sysRole" styleClass="selectStyle" style="width:130px" styleId="sysRole">
		<html:option value="">��ѡ��</html:option>
		<html:options collection="RoleList" property="value" labelProperty="label"/>
		</html:select>
		<div id="sysRoleTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
      <tr>
  	<td class="labelStyle">
  		��&nbsp;��&nbsp;��	
  	</td>
  	<td class="valueStyle">
  		
  		<html:select property="sysGroup" styleClass="selectStyle" style="width:130px" styleId="sysGroup">
		<html:option value="">��ѡ��</html:option>
		<html:options collection="GroupList" property="value" labelProperty="label"/>
		</html:select>
		<div id="sysGroupTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>

  
    <tr>
  	<td class="labelStyle">
  		<font color="red">��¼����</font> 	
  	</td>
  	<td class="valueStyle">
  		<html:password property="password" styleClass="writeTextStyle" styleId="password"></html:password>
  		<div id="passwordTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
   <tr>
  	<td class="labelStyle">
  		��֤����	
  	</td>
  	<td class="valueStyle">
  	<html:password property="repassword" styleClass="writeTextStyle" styleId="repassword"></html:password>
  	<div id="repasswordTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		�Ƿ񶳽�
  	</td>
  	<td class="valueStyle">
		<html:select property="isFreeze" styleClass="selectStyle" style="width:130px" styleId="isFreeze">
			<html:option value="">��ѡ��</html:option>
			<html:option value="0">��</html:option>
			<html:option value="1">��</html:option>
		</html:select>
		<div id="isFreezeTip" style="width: 10px;display:inline;"></div>	
  	</td>
  </tr>   
  <tr style="display:none;">
  	<td class="labelStyle">
  		��&nbsp;&nbsp;&nbsp;&nbsp;��	
  	</td>
  	<td class="valueStyle">
  		<html:select property="departmentId" styleClass="selectStyle">
  		<html:option value="">��ѡ��</html:option>
		<html:options collection="depList" property="value" labelProperty="label"/>
		</html:select>
  		
  	</td>
  </tr>
  <tr>
  <td colspan="2" height="40px;">
  </td>
  </tr>
  
  
  <tr>
  
  		<td bgcolor="#ffffff" colspan="4" align="center" class="navigateStyle" style="text-align:right;">
<%--				<logic:equal name="opertype" value="insert">--%>
<%--					<input type="button" name="addbtn"  value="���" onclick="add()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
<%--				--%>
<%--				<logic:equal name="opertype" value="update">--%>
<%--					<input type="button" name="updatebtn" value="ȷ��" onclick="update()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
<%--				<logic:equal name="opertype" value="delete">--%>
<%--					<input type="button" name="delbtn" value="ɾ��" onclick="del()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
					<input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle" />
					<input type="button" name="" value="�ر�" onClick="javascript:window.close();" class="buttonStyle"/>
				</td>
  
  </tr>
  
</table>
</html:form>
  </body>
</html:html>
<script>
	var auditing = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("userform")).get("auditing") %>";
	var checkboxs = document.all.item("auditings");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(auditing.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
	
</script>