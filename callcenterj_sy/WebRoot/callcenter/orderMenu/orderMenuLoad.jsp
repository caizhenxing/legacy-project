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
		opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
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
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>����</title>
<script type='text/javascript' src='../js/msg.js'></script>
<script type="text/javascript">
	// ��ѯ���Ƽ�¼
	function query(){
		var tel = document.forms[0].telNum.value;
		if(tel == "") {
   			alert("��ѯʱ���������û����룡");
   			return false;
   		}
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuList";
   		document.forms[0].target="bottomm";
<%--   		document.forms[0].target = "_blank";--%>
		document.forms[0].submit();
	}
	//���
   	function add(){
   		var tel = document.forms[0].telNum.value;
   		var otype = document.forms[0].orderType.value;
   		var begindate = document.forms[0].beginDate.value;
   		var begintime = document.forms[0].beginTime.value;
   		var ivrInfo = document.forms[0].ivrInfo.value;
   		if(tel == "") {
   			alert("�û����벻����Ϊ�գ�");
   			return false;
   		}
   		if(otype == "") {
   			alert("�������ͱ���ѡ��");
   			return false;
   		}
   		if(otype == "dingzhi" && begintime == "") {
   			alert("����ҵ�����ѡ�����ʱ��");
   			return false;
   		}
   		if(otype == "dianbo" && (begindate == ""||begintime == "")) {
   			alert("�㲥ҵ�����ѡ������ʱ��");
   			return false;
   		}
   		
   		if(ivrInfo == "") {
   			alert("������Ŀ������Ϊ�գ�");
   			return false;
   		}
   		document.forms[0].action = "orderMenu.do?method=toOrderMenuOper";
		document.forms[0].submit();
   	}
   	//�޸�
   	function update(){
   		document.forms[0].action = url + "update";
		document.forms[0].submit();
   	}
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
   	
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
	
</script>
</head>

<body class="loadBody">
<%--<html:form action="/callcenter/orderMenu" method="post">--%>
<%--<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">--%>
<%--  <tr>--%>
<%--    <td class="navigateStyle">--%>
<%--    ��ǰλ��&ndash;&gt;������Ϣ����--%>
<%--    </td>--%>
<%--  </tr>--%>
<%--</table>--%>
<%--<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">--%>
<%--  <tr>--%>
<%--    <td class="labelStyle">�û�����</td>--%>
<%--    <td class="valueStyle" colspan="2">--%>
<%--      <html:text property="telNum" styleClass="input" styleId="telNum"/>&nbsp;<font color="red">*</font>--%>
<%--      <html:hidden property="id"/></td>--%>
<%--    <td class="labelStyle">��������</td>--%>
<%--    <td class="valueStyle">--%>
<%--      <html:select property="orderType" styleClass="selectStyle" onchange="change()" style="width:155px" styleId="orderType">		--%>
<%--	     <html:option value="" >��ѡ��</html:option>--%>
<%--	     <html:option value="dianbo" >�㲥</html:option>--%>
<%--	     <html:option value="dingzhi" >����</html:option>--%>
<%--	  </html:select>&nbsp;<font color="red">*</font>--%>
<%--    </td>--%>
<%--   </tr>--%>
<%--   <tr>--%>
<%--     <td class="labelStyle">--%>
<%--		����ʱ��--%>
<%--     </td>--%>
<%--     <td class="valueStyle" width="180">	--%>
<%--     <div id='div1'>--%>
<%--     <html:text property="beginDate" styleClass="writeTextStyle"/>--%>
<%--     <img alt="ѡ��ʱ��" src="../html/img/cal.gif"--%>
<%--			onclick="openCal('orderMenuBean','beginDate',false);">&nbsp;<font color="red">*</font></div>--%>
<%--	</td>--%>
<%--	 <td class="valueStyle">--%>
<%--	 <html:text property="beginTime" maxlength="10" size="10"  styleClass="input" readonly="true" styleId="beginTime"/>&nbsp;<font color="red">*</font>--%>
<%--       		<input type="button"   value="ѡ��ʱ��" onclick="OpenTime(document.all.beginTime);"/>--%>
<%--       		</td>--%>
<%--    --%>
<%--     <td class="labelStyle">--%>
<%--     	������Ŀ--%>
<%--     </td>--%>
<%--     <td class="valueStyle">--%>
<%--      <html:select property="ivrInfo" styleClass="input" styleId="ivrInfo">--%>
<%--      	<html:option value="">��ѡ����Ŀ����</html:option>--%>
<%--        <html:options collection="ivrlist" property="value" labelProperty="label"/>--%>
<%--      </html:select>&nbsp;<font color="red">*</font>--%>
<%--     </td>--%>
<%--   </tr>--%>
<%--  --%>
<%--  <tr class="buttonAreaStyle">--%>
<%--    <td colspan="5" align="right">--%>
<%--      <input type="button" name="Submit4" value="�� ѯ" onClick="query()"  class="buttonStyle"/>--%>
<%--      <input type="button" name="Submit" id="Submit" value="�� ��" onClick="add()"  class="buttonStyle"/>--%>
<%--      <input type="reset" name="Submit2" value="�� ��"  class="buttonStyle"/>--%>
<%--      <input type="button" name="Submit3" value="�� ��" onClick="javascript:window.close()"  class="buttonStyle" style="display:none"/>--%>
<%--      </td>--%>
<%--    </tr>--%>
<%--</table>--%>
<%--</html:form>--%>
</body>
</html>
