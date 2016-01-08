<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		//s;//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html:html locale="true">
<head>
<html:base />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<title>��Ϣ����</title>
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>

<!-- jquery��֤ -->
<script src="./../../js/jquery/jquery_last.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="./../../css/validator.css"></link>
<script src="./../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
<script src="./../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
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

		function CountNum()
		{
			var temp = document.forms[0].content.value;
			if(temp != null)
			    msgNum = temp.length;
			else
				msgNum = 0;
			
		    var wordnum = msgNum;
		   
		    document.all("CurWordNum").innerHTML ="<font color='red'  id='fontStyle'>"+msgNum + "</font>��" ;
		    
		}

//��ʼ��
function init(){

	<c:choose>
		<c:when test="${opertype=='detail'}">
			document.getElementById('buttonSubmit').style.display="none";
		</c:when>
		<c:when test="${opertype=='insert'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=insert";
			document.getElementById('spanHead').innerHTML="�����Ϣ";
			document.getElementById('buttonSubmit').value=" �� �� ";
		</c:when>
		<c:when test="${opertype=='update'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=update";
			document.getElementById('spanHead').innerHTML="�޸���Ϣ";
			document.getElementById('buttonSubmit').value=" �� �� ";
		</c:when>
		<c:when test="${opertype=='delete'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=delete";
			document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
			document.getElementById('buttonSubmit').value=" ɾ �� ";
		</c:when>
	</c:choose>	
}
//ִ����֤
<c:choose>				
	<c:when test="${opertype=='insert'}">	
	$(document).ready(function(){
		$.formValidator.initConfig({formid:"messageloadId",onerror:function(msg){alert(msg)}});	
		$("#columnInfoId").formValidator({onshow:"��ѡ�������Ŀ",onfocus:"������Ŀ����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ�������Ŀ!"});
		$("#messageNameId").formValidator({onshow:"��ѡ����ű���",onfocus:"���ű������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ����ű���!"});		
		$("#messageContentId").formValidator({onshow:"�������������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	
		})
	</c:when>

	<c:when test="${opertype=='update'}">
	$(document).ready(function(){
	  	$.formValidator.initConfig({formid:"messageloadId",onerror:function(msg){alert(msg)}});	
		$("#columnInfoId").formValidator({onshow:"��ѡ�������Ŀ",onfocus:"������Ŀ����ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ�������Ŀ!"});
		$("#messageNameId").formValidator({onshow:"��ѡ����ű���",onfocus:"���ű������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ����ű���!"});		
		$("#messageContentId").formValidator({onshow:"�������������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	
		})
	</c:when>
</c:choose>	

		
<%--	function checkForm(messages){--%>
<%--            if (!checkNotNull(messages.receive_id,"������ID")) return false;--%>
<%--            if (!checkNotNull(messages.message_content,"��Ϣ����")) return false;--%>
<%--            return true;--%>
<%--   	}--%>
<%--	var url = "messages.do?method=toMessagesOper&type=";--%>
<%--	//���--%>
<%--   	function add(){  --%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "update";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();   		--%>
<%--   		}--%>
<%--   	}--%>

</script>



</head>

<body class="loadBody" onload="init()">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
	<tr>
		<td class="navigateStyle">
    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴����</span>
		</td>
	</tr>
</table>
<html:form action="/sms/columnInfo.do" method="post" styleId="messageloadId" >

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="id"/>
    <td class="labelStyle">������Ŀ</td>
    <td class="valueStyle">
 	<html:select property="columnInfo" styleId="columnInfoId">
 	<option value="">��ѡ��</option>
	<logic:iterate id="u" name="ulist" >
		<html:option value="${u.value}">${u.label}</html:option>						
	</logic:iterate>
	</html:select>
	<div id="columnInfoIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">���ű���</td>
    <td class="valueStyle">
    	<html:text property="messageName" size="30" styleClass="writeTextStyle" styleId="messageNameId"/>
    	<div id="messageNameIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">��������</td>
    <td class="valueStyle">
    	<html:textarea property="content" cols="50" rows="4" styleClass="writeTextStyle" styleId="messageContentId"
    	    onkeyup="CountNum();" onchange="CountNum();" onfocus="CountNum();"/>
    	<div id="messageContentIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  
  <tr>
	<td class="labelStyle">
		��ǰ��������
	</td>
	<td class="valueStyle">
		<span class="cpx12red" id="CurWordNum"><font color="red"
			id="fontStyle">0��</font> </span>
	</td>
  </tr>
  
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

<%--  	 <logic:equal name="opertype" value="insert">--%>
<%--      <input type="button" name="Submit" value=" �� �� " onClick="add()"  >--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" �� �� "  >--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--      <input type="button" name="Submit" value=" ȷ �� " onClick="update()"  >--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" �� �� "  >--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"  >--%>
<%--&nbsp;&nbsp;&nbsp;&nbsp;	 </logic:equal>--%>
<input type="submit" name="button" id="buttonSubmit" value=" �� �� "  />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"  >
&nbsp;&nbsp;&nbsp;    </td>
  </tr>
</table>

</html:form>
</body>
</html:html>
