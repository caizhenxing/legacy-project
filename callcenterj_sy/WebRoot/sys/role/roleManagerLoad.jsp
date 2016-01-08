<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="../../style.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>��ɫ����</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
    
    <!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
    //��ʼ��
		function init(){
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "../role/Role.do?method=operRole&type=insert";
				document.getElementById('tdHead').innerHTML="��ӽ�ɫ��Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../role/Role.do?method=operRole&type=update";
				document.getElementById('tdHead').innerHTML="�޸Ľ�ɫ��Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../role/Role.do?method=operRole&type=delete";
				document.getElementById('tdHead').innerHTML="ɾ����ɫ��Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
			</logic:equal>
			
		}
		//ִ����֤
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"roleId",onerror:function(msg){alert(msg)}});
			$("#name").formValidator({onshow:"�������ɫ����",onfocus:"��ɫ���Ʋ���Ϊ��",oncorrect:"��ɫ���ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ɫ�������߲����пշ���"},onerror:"��ɫ���Ʋ���Ϊ��"});
		})
    
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.name,"��ɫ����")) return false;--%>
<%--            if (!checkNotNull(addstaffer.delMark,"�Ƿ񶳽�")) return false;--%>
<%--              return true;--%>
<%--    }--%>
<%--    --%>
<%--    	//���--%>
<%--    	function add(){    		--%>
<%--    		var f =document.forms[0];    		--%>
<%--    	if(checkForm(f)){ --%>
<%--	    		document.forms[0].action = "../role/Role.do?method=operRole&type=insert";	    		 --%>
<%--	    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//�޸�--%>
<%--    	function update(){--%>
<%--    		document.forms[0].action = "../role/Role.do?method=operRole&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	//ɾ��--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../role/Role.do?method=operRole&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	--%>
<%--    	function openwin(param)--%>
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
		//����ҳ��
		function toback(){
			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody" onload="init();">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("�����ɹ�");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/sys/role/Role" method="post" styleId="roleId">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
   			 <td colspan="2" class="labelStyle" id="tdHead">��ɫ����</td>
  			</tr>
		 
          <tr>
           <td class="labelStyle">��ɫ����</td>
            <td class="valueStyle">
		    <html:text property="name" styleClass="writeTextStyle" styleId="name"></html:text>
		    <div id="nameTip" style="width: 150px;display:inline;"></div>
		    <html:hidden property="id"/>
	        </td>
         </tr>
  
         <tr>
           <td class="labelStyle">�Ƿ񶳽�</td>
            <td class="valueStyle">
		    <html:select property="delMark" styleClass="selectStyle">
			<html:option value="1">����</html:option>
			<html:option value="0">����</html:option>
		    </html:select>
	        </td>
         </tr>
         <tr>
            <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
            <td class="valueStyle">
	        <html:textarea cols="50" rows="4" styleClass="writeTextStyle" property="remark"></html:textarea>
	        </td>
        </tr> 
		<tr>
		     <td colspan="2"  class="buttonAreaStyle">
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     <input name="btnAdd" type="button"   value="���" onclick="add()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     <input name="btnAdd" type="button"   value="ȷ��" onclick="update()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		     <input name="btnAdd" type="button"   value="ɾ��" onclick="del()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
			<input type="submit" name="button" id="buttonSubmit" value="�ύ" class="buttonStyle" />
		    <input name="btnReset" type="button"   value="ȡ��" onclick="javascript:window.close();" class="buttonStyle"/></td>
		  
		</tr>
	</table>
    </html:form>
  </body>
</html:html>
