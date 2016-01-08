<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>�������ι���</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../../js/common.js"></script>
<script language="javascript" src="../../js/clock.js"></script>
<script language="javascript" src="../../js/clockCN.js"></script>
<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript></SCRIPT>
<SCRIPT language="javascript" src="../../js/calendar3.js" type=text/javascript></SCRIPT>

	<!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
	    //��ʼ��
	    function init(){	
			<logic:equal name="opertype" value="detail">
			document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=insert";
				document.getElementById('spanHead').innerHTML="���������Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸�������Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ��������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
			</logic:equal>		
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"callinFirewall",onerror:function(msg){alert(msg)}});
			$("#callinNum").formValidator({onshow:"�������������",onfocus:"������벻��Ϊ��",oncorrect:"�������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����������߲����пշ���"},onerror:"������벻��Ϊ��"});
			$("#isPass").formValidator({onshow:"��ѡ���Ƿ�ͨ��",onfocus:"�Ƿ�ͨ������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ���Ƿ�ͨ��!"});
			$("#beginTime").formValidator({onshow:"�����뿪ʼʱ��",onfocus:"��ʼʱ�䲻��Ϊ��",oncorrect:"��ʼʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼʱ�����߲����пշ���"},onerror:"��ʼʱ�䲻��Ϊ��"});
			$("#endTime").formValidator({onshow:"���������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"callinFirewall",onerror:function(msg){alert(msg)}});
			$("#callinNum").formValidator({onshow:"�������������",onfocus:"������벻��Ϊ��",oncorrect:"�������Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����������߲����пշ���"},onerror:"������벻��Ϊ��"});
			$("#isPass").formValidator({onshow:"��ѡ���Ƿ�ͨ��",onfocus:"�Ƿ�ͨ������ѡ��",oncorrect:"��ȷ",defaultvalue:""}).inputValidator({min:1,onerror: "û��ѡ���Ƿ�ͨ��!"});
			$("#beginTime").formValidator({onshow:"�����뿪ʼʱ��",onfocus:"��ʼʱ�䲻��Ϊ��",oncorrect:"��ʼʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ʼʱ�����߲����пշ���"},onerror:"��ʼʱ�䲻��Ϊ��"});
			$("#endTime").formValidator({onshow:"���������ʱ��",onfocus:"����ʱ�䲻��Ϊ��",oncorrect:"����ʱ��Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"����ʱ�����߲����пշ���"},onerror:"����ʱ�䲻��Ϊ��"});
		})
		</logic:equal>
    
<%--    	//���--%>
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.callinNum,"�������")) return false;--%>
<%--            if (!checkNotNull(addstaffer.isPass,"�Ƿ�ͨ��")) return false;--%>
<%--            if (!checkNotNull(addstaffer.isAvailable,"<bean:message bundle='pccye' key='et.pcc.callinFirewall.isAvailable'/>")) return false;--%>
<%--              return true;--%>
<%--            }--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    	      document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=insert";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//�޸�--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		  document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=update";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//ɾ��--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../callinFirewall.do?method=toCallinFireWallOper&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
    	
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
    <logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ���"); window.close();
	</script>
	</logic:notEmpty>
  
    <html:form action="/callcenter/callinFirewall" method="post" styleId="callinFirewall">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
<%--		    <logic:equal name="opertype" value="detail">--%>
		     ��ǰλ��&ndash;&gt;<span id="spanHead">������ϸ��Ϣ</span>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     ��ǰλ��&ndash;&gt;���������Ϣ--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     ��ǰλ��&ndash;&gt;�޸�������Ϣ--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		      ��ǰλ��&ndash;&gt;ɾ��������Ϣ--%>
<%--		    </logic:equal>--%>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
          <tr>
		    <td class="labelStyle" width="30%">�������</td>
		    <td class="valueStyle" colspan="3">
		    	<html:text property="callinNum" styleClass="writeTextStyle" styleId="callinNum"/>
		    	<div id="callinNumTip" style="width: 10px;display:inline;"></div>
		    </td>
		  </tr>
		  <tr>
<%--		  	<logic:equal name="opertype" value="detail">--%>
<%--		     <td class="labelStyle">�Ƿ�ͨ��</td>--%>
<%--		     <td class="valueStyle" colspan="3">--%>
<%--		     	<html:text property="isPass" styleClass="writeTextStyle"/>--%>
<%--		     </td>	 --%>
<%--		    </logic:equal>--%>
		    <logic:notEqual name="opertype" value="detail">
		    <td class="labelStyle">�Ƿ�ͨ��</td>
		    <td class="valueStyle" colspan="3">	    	
		    	<html:select property="isPass" styleClass="selectStyle" styleId="isPass">		
	        		<html:option value="" >��ѡ��</html:option>
	        		<html:option value="0" >ͨ��</html:option>
	        		<html:option value="1" >δͨ��</html:option>
	        	</html:select>
	        	<div id="isPassTip" style="width: 10px;display:inline;"></div>
		    </td>
		    </logic:notEqual>
		  </tr>
		  <tr>
		    <td class="labelStyle">��ʼʱ��</td>
		    <td class="valueStyle">
		    <html:text property="beginTime" styleClass="writeTextStyle" readonly="true" styleId="beginTime"/>
		    <img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('callinFirewallBean','beginTime',false);">
			<div id="beginTimeTip" style="width: 10px;display:inline;"></div>
			</td>
		 </tr>
		  <tr>	
		    <td class="labelStyle">����ʱ��</td>
		    <td class="valueStyle">
		    <html:text property="endTime" styleClass="writeTextStyle" readonly="true" styleId="endTime"/>
		    <img alt="ѡ��ʱ��" src="../../html/img/cal.gif"
						onclick="openCal('callinFirewallBean','endTime',false);">
			<div id="endTimeTip" style="width: 10px;display:inline;"></div>
			</td>
		  </tr>
		  <tr>
		    <td class="labelStyle">��&nbsp;&nbsp;&nbsp;&nbsp;ע</td>  
		    <td class="valueStyle" colspan="3">
		        <html:textarea property="remark" rows="3" styleClass="writeTextStyle" cols="30"/>
		    </td>
		  </tr>
		  <tr>	
		  
		    <td colspan="4" align="center" width="100%" class="buttonAreaStyle">
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
		    <input name="addgov" type="button"   value="�ر�" onClick="javascript: window.close();" class="buttonStyle"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
