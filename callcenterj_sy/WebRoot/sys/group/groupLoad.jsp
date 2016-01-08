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
    
    <title>�����</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
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
				document.forms[0].action = "../group/Group.do?method=operGroup&type=insert";
				document.getElementById('spanHead').innerHTML="�������Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../group/Group.do?method=operGroup&type=update";
				document.getElementById('spanHead').innerHTML="�޸�����Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../group/Group.do?method=operGroup&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
			</logic:equal>		
		}
		//ִ����֤
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"Group",onerror:function(msg){alert(msg)}});
			$("#nameId").formValidator({onshow:"������������",onfocus:"�����Ʋ���Ϊ��",oncorrect:"�����ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ʋ���Ϊ��"});
<%--			$("#isSys").formValidator({onshow:"��ѡ���Ƿ񶳽�",onfocus:"�Ƿ񶳽����ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ���Ƿ񶳽�"});--%>
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"Group",onerror:function(msg){alert(msg)}});
			$("#nameId").formValidator({onshow:"������������",onfocus:"�����Ʋ���Ϊ��",oncorrect:"�����ƺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���������߲����пշ���"},onerror:"�����Ʋ���Ϊ��"});
<%--			$("#isSys").formValidator({onshow:"��ѡ���Ƿ񶳽�",onfocus:"�Ƿ񶳽����ѡ��",oncorrect:"OK!"}).inputValidator({min:1,onerror: "û��ѡ���Ƿ񶳽�"});--%>
		})
		</logic:equal>
    
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.name,"������")) return false;--%>
<%--            if (!checkNotNull(addstaffer.delMark,"�Ƿ񶳽�")) return false;--%>
<%--              return true;--%>
<%--    }--%>
<%--    --%>
<%--    	//���--%>
<%--    	function add(){    		--%>
<%--    		var f =document.forms[0];    		--%>
<%--    	if(checkForm(f)){ --%>
<%--	    		document.forms[0].action = "../group/Group.do?method=operGroup&type=insert";	    		 --%>
<%--	    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//�޸�--%>
<%--    	function update(){--%>
<%--    		document.forms[0].action = "../group/Group.do?method=operGroup&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	//ɾ��--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../group/Group.do?method=operGroup&type=delete";--%>
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
  
    <html:form action="/sys/group/Group" method="post" styleId="Group">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		   <td class="navigateStyle" colspan="2">
		    ��ǰλ��&ndash;&gt;<span id="spanHead">��ϸ</span>
		    <%-- String type = request.getParameter("type"); if(type==null){out.print("�����");} if("insert".equals(type.trim())){out.print("�������Ϣ");}if("update".equals(type.trim())){out.print("�޸�����Ϣ");}if("delete".equals(type.trim())){out.print("ɾ������Ϣ");}--%>
		    </td>	 
          <tr>
           <td class="labelStyle">��&nbsp;��&nbsp;��</td>
            <td class="valueStyle">
		    <html:text property="name" styleId="nameId"></html:text>
			<div id="nameIdTip" style="width: 250px;display:inline;"></div>
		    <html:hidden property="id"/>
	        </td>
         </tr>
  
         <tr>
           <td class="labelStyle">�Ƿ񶳽�</td>
            <td class="valueStyle">
		    <html:select property="isSys" styleClass="selectStyle" styleId="isSys">
			<html:option value="1">��&nbsp;&nbsp;&nbsp;&nbsp;��</html:option>
			<html:option value="0">��&nbsp;&nbsp;&nbsp;&nbsp;��</html:option>
		    </html:select>
<%--<div id="isSysTip" style="width: 250px;display:inline;"></div>--%>
	        </td>
         </tr>
         <tr>
            <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>
            <td class="valueStyle">
	        <html:textarea cols="40" rows="4" property="remark"></html:textarea>
	        </td>
        </tr> 
		<tr>
		     <td colspan="2"  class="navigateStyle" style="text-align:right;">
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
