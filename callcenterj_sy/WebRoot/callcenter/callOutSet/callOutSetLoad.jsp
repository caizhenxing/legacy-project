<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("�Ѿ����ƹ���ҵ�񣬲����ظ����ƣ�");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("�Ѿ��˶�����ҵ�񣬲����ظ��˶���");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		<%
		request.removeAttribute("operSign");
		%>
	</script>
	</logic:equal>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<script language="javascript" src="../js/public.js"></script>	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>����</title>
<script type='text/javascript' src='../js/msg.js'></script>
<!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>   

<script type="text/javascript">
		function init(){
			document.forms[0].action = "callOutSet.do?method=toOrderMenuOper";
		}
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"callOutSet",onerror:function(msg){alert(msg)}});
			$("#telNum").formValidator({onshow:"��������к���",onfocus:"���к��벻��Ϊ��",oncorrect:"���к���Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"���к������߲����пշ���"},onerror:"���к��벻��Ϊ��"});
			
		})
	// ��ѯ���Ƽ�¼
<%--	function query(){--%>
<%--		var tel = document.forms[0].telNum.value;--%>
<%--		var cTel = checkTel(tel);--%>
<%--		if(cTel==false) {--%>
<%--   			alert("�û��������");--%>
<%--   			return false;--%>
<%--   		}--%>
<%--   		document.forms[0].action = "orderMenu.do?method=toOrderMenuList";--%>
<%--   		document.forms[0].target = "_blank";--%>
<%--		document.forms[0].submit();--%>
<%--	}--%>
	//���
   	function add(){
   		var tel = document.forms[0].telNum.value;
<%--   		var otype = document.forms[0].orderType.value;--%>
   		var begindate = document.forms[0].beginDate.value;
   		var begintime = document.forms[0].beginTime.value;
<%--   	    var cTel = checkTel(tel);--%>
		if(tel == null || tel == "") {
   			alert("���к��벻��Ϊ�գ�");
   			return false;
   		}
<%--   		if(otype == "") {--%>
<%--   			alert("ҵ�����ͱ���ѡ��");--%>
<%--   			return false;--%>
<%--   		}--%>
<%--   		if(otype == "dingzhi" && begintime == "") {--%>
<%--   			alert("����ҵ�����ѡ�����ʱ��");--%>
<%--   			return false;--%>
<%--   		}--%>
   		document.forms[0].action = "callOutSet.do?method=toOrderMenuOper";
		document.forms[0].submit();
   	}
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--   		document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   		--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();--%>
<%--   		--%>
<%--   		}--%>
<%--   	}--%>
   	
   	function change(){
   		if(document.forms[0].orderType.value == "dianbo") {
   			document.all.Submit.value = "�� ��";
   			document.getElementById("div1").style.display = 'block';
   		}
   		if(document.forms[0].orderType.value == "dingzhi") {
   			
   			document.getElementById("div1").style.display = 'none';
<%--   			document.getElementById("div2").style.display = 'block';--%>
   			document.all.Submit.value = "�� ��";
   		}
   		if(document.forms[0].orderType.value == "") {
   			document.all.Submit.value = "�� ��";
   		}
   	}
   	
   	function dep()
	{
		var arrparm = new Array();
		arrparm[0] = document.forms[0].userName;
		arrparm[1] = document.forms[0].telNum;
		select(arrparm);
	}
	 function select(obj)
   	 {
		
		var page = "<%=request.getContextPath()%>/callcenter/callOutSet.do?method=select&value=";
		var winFeatures = "dialogWidth:480px; dialogHeight:500px; center:1; status:0";

		window.showModalDialog(page,obj,winFeatures);
	 }
	
</script>

   

</head>

<body class="loadBody" onload="init();">
<html:form action="/callcenter/callOutSet" method="post" styleId="callOutSet" onsubmit="return init();">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
    ��ǰλ��&ndash;&gt;IVRȺ������
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
    <td class="labelStyle">���к���</td>
    <td class="valueStyle" colspan="3">
      <html:text property="telNum" styleClass="input" styleId="telNum"/>      
      <html:hidden property="userName"/>
      <img  src="../style/<%=styleLocation%>/images/detail.gif" alt="��ӱ����û�" onclick="dep()" width="16" height="16" border="0"/>
      <div id="telNumTip" style="width: 150px;display:inline;"></div>
    </td>
<%--    <td class="labelStyle">ҵ������</td>--%>
<%--    <td class="valueStyle">--%>
<%--      <html:select property="orderType" styleClass="selectStyle" onchange="change()">		--%>
<%--	     <html:option value="" >��ѡ��</html:option>--%>
<%--	     <html:option value="dianbo" >�㲥</html:option>--%>
<%--	     <html:option value="dingzhi" >����</html:option>--%>
<%--	     <html:option value="tuiding" >�˶�</html:option>--%>
<%--	  </html:select>     --%>
<%--    </td>--%>
   </tr>
   <tr>
     <td class="labelStyle">
		����ʱ��
     </td>
     <td class="valueStyle">	

     <html:text property="beginDate" styleClass="input"/>&nbsp;
     <img alt="ѡ��ʱ��" src="../html/img/cal.gif"
			onclick="openCal('callOutSetBean','beginDate',false);">
	 <html:text property="beginTime" maxlength="10" size="11"  styleClass="input" readonly="true"/>
       		&nbsp;&nbsp;<input type="button"   value="ѡ��ʱ��" onclick="OpenTime(document.all.beginTime);" class="buttonStyle" />

<%--     <div id='div2' style='display:none'>--%>
<%--	 <html:text property="beginTime" maxlength="10" size="10"  styleClass="input" readonly="true"/>--%>
<%--       		<input type="button"   value="ѡ��ʱ��" onclick="OpenTime(document.all.beginTime);"/>--%>
<%--     </div>--%>
     </td>
     <td class="labelStyle">
     	��������
     </td>
     <td class="valueStyle">
      <html:select property="ivrInfo">
<%--      	<html:option value="tianqiyubao" >����Ԥ��</html:option>--%>
        <html:options collection="ivrlist" property="value" labelProperty="label"/>
      </html:select>
     </td>
   </tr>
  
  <tr class="buttonAreaStyle">
    <td colspan="4" align="right" class="buttonAreaStyle">
<%--      <input type="button" name="Submit4" value="�� ѯ" onClick="query()"  >--%>
<%--      <input type="button" name="Submit" id="Submit" value="�� ��" onClick="add()" class="buttonStyle" />--%>
      <input type="submit" name="button" id="buttonSubmit" value="�� ��" class="buttonStyle" />
      <input type="reset" name="Submit2" value="�� ��" class="buttonStyle" />
      <input type="button" name="Submit3" value="�� ��" onClick="javascript:window.close()" class="buttonStyle" style="display:none" />

      </td>
    </tr>
</table>
</html:form>
</body>
</html>
