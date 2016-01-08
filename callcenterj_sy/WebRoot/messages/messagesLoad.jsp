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
		//s;//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>��Ϣ����</title>
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
		document.forms[0].action = "messages.do?method=toMessagesOper&type=insert";
		document.getElementById('spanHead').innerHTML="������Ϣ";
		document.getElementById('buttonSubmit').value=" �� �� ";
	</logic:equal>
	<logic:equal name="opertype" value="update">
		document.forms[0].action = "messages.do?method=toMessagesOper&type=update";
		document.getElementById('spanHead').innerHTML="�޸���Ϣ";
		document.getElementById('buttonSubmit').value=" �� �� ";
	</logic:equal>
	<logic:equal name="opertype" value="delete">
		document.forms[0].action = "messages.do?method=toMessagesOper&type=delete";
		document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
		document.getElementById('buttonSubmit').value=" ɾ �� ";
		v_flag="del"
	</logic:equal>		
}
//ִ����֤
	
<logic:equal name="opertype" value="insert">
$(document).ready(function(){
	$.formValidator.initConfig({formid:"messages",onerror:function(msg){alert(msg)}});	
	$("#receive_id").formValidator({onshow:"��ѡ�������",onfocus:"�����˱���ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ�������!"});	
	$("#message_content").formValidator({onshow:"��������Ϣ����",onfocus:"��Ϣ���ݲ���Ϊ��",oncorrect:"��Ϣ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ϣ�������߲����пշ���"},onerror:"��Ϣ���ݲ���Ϊ��"});	

})
</logic:equal>
<logic:equal name="opertype" value="update">
  	$(document).ready(function(){
	$.formValidator.initConfig({formid:"messages",onerror:function(msg){alert(msg)}});	
	$("#receive_id").formValidator({onshow:"��ѡ�������",onfocus:"�����˱���ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ�������!"});	
	$("#message_content").formValidator({onshow:"��������Ϣ����",onfocus:"��Ϣ���ݲ���Ϊ��",oncorrect:"��Ϣ���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��Ϣ�������߲����пշ���"},onerror:"��Ϣ���ݲ���Ϊ��"});	
})
</logic:equal>

		function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].receive_name;
			arrparm[1] = document.forms[0].receive_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/messages/messages.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }

</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init();">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--				<logic:equal name="opertype" value="insert">--%>
<%--		    		��ǰλ��&ndash;&gt;������Ϣ--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="detail">--%>
		    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴��Ϣ</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		��ǰλ��&ndash;&gt;�޸���Ϣ--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="delete">--%>
<%--		    		��ǰλ��&ndash;&gt;ɾ����Ϣ--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
<html:form action="/messages/messages" method="post" styleId="messages" onsubmit="return formAction();">

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="message_id"/>
    <td class="labelStyle">����������</td>
    <td class="valueStyle">
    	<logic:equal name="opertype" value="insert">
    	<html:text property="receive_name" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	<html:hidden property="receive_id" />
    	<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="ѡ�������" onclick="dep()" width="16" height="16" border="0"/>
    	</logic:equal>
    	
    	<logic:equal name="opertype" value="update">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	<logic:equal name="opertype" value="detail">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	<logic:equal name="opertype" value="delete">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	
<%-- 	<html:select property="receive_id" styleId="receive_id">--%>
<%-- 	<option value="">ѡ�������</option>--%>
<%--	<logic:iterate id="u" name="uList" >--%>
<%--		<html:option value="${u.value}">${u.label}</html:option>						--%>
<%--	</logic:iterate>--%>
<%--	</html:select>--%>
	
	<div id="receive_idTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">��Ϣ����</td>
    <td class="valueStyle">
    	<html:textarea property="message_content" cols="50" rows="4" styleClass="writeTextStyle" styleId="message_content"/>
    	<div id="message_contentTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">


<input type="submit" name="button" id="buttonSubmit" value="�ύ"  />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"  >
		&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

</html:form>
</body>
</html:html>
