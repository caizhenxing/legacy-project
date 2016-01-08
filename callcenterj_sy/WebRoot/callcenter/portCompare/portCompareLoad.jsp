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
    
    <title>�˿ڹ���</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />    
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
    <script language="javascript" src="../../js/clockCN.js"></script>
    <script language="javascript" src="../../js/clock.js"></script>
    
    <!-- jquery��֤ -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
    	//��ʼ��
		function init(){
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=insert";
				document.getElementById('spanHead').innerHTML="��Ӷ˿���Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸Ķ˿���Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ���˿���Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
			</logic:equal>
			<logic:equal name="opertype" value="detail">
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>
			
		}
		
		//ִ����֤
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"portCompare",onerror:function(msg){alert(msg)}});
			$("#seatNum").formValidator({onshow:"��������ϯ�ֻ���",onfocus:"��ϯ�ֻ��Ų���Ϊ��",oncorrect:"��ϯ�ֻ��źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�ֻ������߲����пշ���"},onerror:"��ϯ�ֻ��Ų���Ϊ��"});
			$("#ip").formValidator({onshow:"������ip",onfocus:"ip����Ϊ��",oncorrect:"ip�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ip���߲����пշ���"},onerror:"ip����Ϊ��"});
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"portCompare",onerror:function(msg){alert(msg)}});
			$("#seatNum").formValidator({onshow:"��������ϯ�ֻ���",onfocus:"��ϯ�ֻ��Ų���Ϊ��",oncorrect:"��ϯ�ֻ��źϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ϯ�ֻ������߲����пշ���"},onerror:"��ϯ�ֻ��Ų���Ϊ��"});
			$("#ip").formValidator({onshow:"������ip",onfocus:"ip����Ϊ��",oncorrect:"ip�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ip���߲����пշ���"},onerror:"ip����Ϊ��"});
		})
		</logic:equal>
		
<%--    	//���--%>
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.seatNum,"��ϯ��")) return false;--%>
<%--            if (!checkNotNull(addstaffer.ip,"IP")) return false;--%>
<%--            --%>
<%--              return true;--%>
<%--            }--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    	      document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=insert";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//�޸�--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		  document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=update";--%>
<%--    		  document.forms[0].submit();--%>
<%--    	    }--%>
<%--    	}--%>
<%--    	//ɾ��--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../portCompare.do?method=toPortCompareOper&type=delete";--%>
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
  
  <body onunload="toback()" class="loadBody" onload="init()">
    <logic:equal name="operSign" value="et.pcc.portCompare.samePortOrIp">
	<script>
		alert("��ϯ�ֻ��Ż�IP�Ѿ����ڣ�"); window.close();
	</script>
	</logic:equal>
	
	<logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("�����ɹ���"); toback(); window.close();
	</script>
	</logic:equal>
  
    <html:form action="/callcenter/portCompare" method="post" styleId="portCompare">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
<%--		    <logic:equal name="opertype" value="detail">--%>
		     ��ǰλ��&ndash;&gt;<span id="spanHead">�˿���ϸ��Ϣ</span>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     ��ǰλ��&ndash;&gt;�˿������Ϣ--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     ��ǰλ��&ndash;&gt;�˿��޸���Ϣ--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		     ��ǰλ��&ndash;&gt;�˿�ɾ����Ϣ--%>
<%--		    </logic:equal>--%>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
         
		  <tr>  
		    <td class="labelStyle">��ϯ�ֻ���</td>
		    <td class="valueStyle">
		    <html:text property="seatNum" styleClass="writeTextStyle" styleId="seatNum"/>
		    <div id="seatNumTip" style="width: 250px; display: inline;"></div>
		    </td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">IP</td>
		    <td class="valueStyle">
		    <html:text property="ip" styleClass="writeTextStyle" styleId="ip"/>
		    <div id="ipTip" style="width: 250px; display: inline;"></div></td>
		  </tr>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="beginTime" styleClass="input" readonly="true"/>--%>
<%--		    <input type="button" value="<bean:message bundle="pcc" key='sys.common.time'/>" onclick="OpenTime(document.all.beginTime);"/></td>--%>
<%--		  </tr>--%>
<%--		  <tr>  --%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft"><html:text property="endTime" styleClass="input" readonly="true"/>--%>
<%--		    <input type="button" value="<bean:message bundle="pccye" key='sys.common.time'/>" onclick="OpenTime(document.all.endTime);"/></td>--%>
<%--		  </tr>--%>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isPass"/></td>--%>
<%--		    <td class="tdbgcolorqueryleft">	    	--%>
<%--		    	<html:select property="isPass">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.pass"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.unpass"/></html:option>--%>
<%--	        	</html:select>--%>
<%--		    </td>--%>
<%--		  </tr>--%>
<%--		  <tr>--%>
<%--		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.callinFirewall.isAvailable"/></td>  --%>
<%--		    <td class="tdbgcolorqueryleft">--%>
<%--		        <html:select property="isAvailable">		--%>
<%--	        		<html:option value="" ></html:option>--%>
<%--	        		<html:option value="Y" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.able"/></html:option>--%>
<%--	        		<html:option value="N" ><bean:message bundle="pccye" key="et.pcc.callinFirewall.disable"/></html:option>--%>
<%--	        	</html:select></td>--%>
<%--		  </tr>--%>
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
