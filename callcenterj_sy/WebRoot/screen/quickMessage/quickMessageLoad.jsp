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
<title>12316��Ѷά��</title>
<script type='text/javascript' src='../js/common.js'></script>
<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
   
   <!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
<script type="text/javascript">
		//��ʼ��
		function init(){
			<logic:equal name="opertype" value="detail">
	    		document.getElementById('buttonSubmit').style.display="none";
	    	</logic:equal>
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=insert";
				document.getElementById('tdHead').innerHTML="��ӿ�Ѷ";
				document.getElementById('buttonSubmit').value="���";		    		
	    	</logic:equal>
			
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=update";
				document.getElementById('tdHead').innerHTML="�޸Ŀ�Ѷ";
				document.getElementById('buttonSubmit').value="�޸�";
	    		
	    	</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=delete";
				document.getElementById('tdHead').innerHTML="ɾ����Ѷ";
				document.getElementById('buttonSubmit').value="ɾ��";		    		
	    	</logic:equal>
			
		}
		//ִ����֤
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"quickMessage",onerror:function(msg){alert(msg)}});
			$("#msgTitle").formValidator({onshow:"���������",onfocus:"���ⲻ��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ⲻ��Ϊ��"});
			$("#msgContent").formValidator({onshow:"����������",onfocus:"���ݲ���Ϊ��",oncorrect:"���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ݲ���Ϊ��"});
		})
    	</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"quickMessage",onerror:function(msg){alert(msg)}});
			$("#msgTitle").formValidator({onshow:"���������",onfocus:"���ⲻ��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ⲻ��Ϊ��"});
			$("#msgContent").formValidator({onshow:"����������",onfocus:"���ݲ���Ϊ��",oncorrect:"���ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ݲ���Ϊ��"});
		})
    	</logic:equal>
<%--		<logic:equal name="opertype" value="delete">--%>
<%--		$(document).ready(function(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){   		--%>
<%--   		document.forms[0].submit();--%>
<%--   		}		--%>
<%--		})--%>
<%--		</logic:equal>--%>
		
<%--	function checkForm(custinfo){--%>
<%--            if (!checkNotNull(quickMessage.msgTitle,"����")) return false;--%>
<%--            if (!checkNotNull(quickMessage.msgContent,"����")) return false;--%>
<%--            --%>
<%--              return true;--%>
<%--    }--%>
<%--	var url = "quickMessage.do?method=toQMOper&type=";--%>
<%--	//���--%>
<%--   	function add(){--%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--   		var f =document.forms[0];--%>
<%--	   	document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
			return true;
   		else
   			return false;
   	}
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init()">
<logic:notEqual name="opertype" value="delete">
<html:form action="screen/quickMessage.do" method="post" styleId="quickMessage">
<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		��ӿ�Ѷ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		�鿴��Ѷ
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		�޸Ŀ�Ѷ--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		ɾ����Ѷ--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">����</td>
    <td  class="valueStyle">
      <html:text property="msgTitle" size="13" styleClass="input" styleId="msgTitle"/>
<%--      <font color="red">*</font>--%>
		<div id="msgTitleTip" style="width: 150px;display:inline;"></div>
      <html:hidden property="id"/>      
      </td>
    </tr>
    <tr>
        <td class="labelStyle">����</td>
    <td class="valueStyle">
    	<html:textarea property="msgContent" rows="5" cols="50" styleId="msgContent"></html:textarea>
<%--    	<font color="red">*</font>--%>
    	<div id="msgContentTip" style="width: 150px;display:inline;"></div>
    </td>
    </tr>
<%--  <tr>--%>
<%--    	<td class="labelStyle">��������</td>--%>
<%--    	<td class="valueStyle" colspan="5">--%>
<%--			<html:text property="createDate" size="50"--%>
<%--				styleClass="writeTextStyle" readonly="true" />--%>
<%--		</td>--%>
<%--  </tr>--%>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

<%--  	 <logic:equal name="opertype" value="insert">--%>
<%--      <input type="button" name="Submit" value=" �� �� " onClick="add()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--      <input type="button" name="Submit" value=" ȷ �� " onClick="update()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" �� �� "   class="buttonStyle"/>--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	 </logic:equal>--%>
	  <input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value="�ر�" onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</logic:notEqual>
<logic:equal name="opertype" value="delete">
<html:form action="screen/quickMessage.do" method="post" styleId="quickMessage" onsubmit="return del();">
<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
		    		�鿴��Ѷ
				</td>
			</tr>
		</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">����</td>
    <td  class="valueStyle">
      <html:text property="msgTitle" size="13" styleClass="input" styleId="msgTitle"/>
<%--      <font color="red">*</font>--%>
		<div id="msgTitleTip" style="width: 150px;display:inline;"></div>
      <html:hidden property="id"/>      
      </td>
    </tr>
    <tr>
        <td class="labelStyle">����</td>
    <td class="valueStyle">
    	<html:textarea property="msgContent" rows="5" cols="50" styleId="msgContent"></html:textarea>
<%--    	<font color="red">*</font>--%>
    	<div id="msgContentTip" style="width: 150px;display:inline;"></div>
    </td>
    </tr>
<%--  <tr>--%>
<%--    	<td class="labelStyle">��������</td>--%>
<%--    	<td class="valueStyle" colspan="5">--%>
<%--			<html:text property="createDate" size="50"--%>
<%--				styleClass="writeTextStyle" readonly="true" />--%>
<%--		</td>--%>
<%--  </tr>--%>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">
	  <input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value="�ر�" onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</logic:equal>		
</body>
</html>
