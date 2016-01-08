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
	function tijiao(){
		var stime = document.forms[0].sendTime.value;
		if(stime == "") {
   			alert("��ѡ��ʱ�䣡");
   			return false;
   		}
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoSet";
   		document.forms[0].target = "contents";
		document.forms[0].submit();
		window.close();
	}
	//���
   	function add(){
   		var tel = document.forms[0].telNum.value;
   		var otype = document.forms[0].orderType.value;
   		var begindate = document.forms[0].beginDate.value;
   		var begintime = document.forms[0].beginTime.value;
   		if(tel == "") {
   			alert("ҵ����벻����Ϊ�գ�");
   			return false;
   		}
   		if(otype == "") {
   			alert("ҵ�����ͱ���ѡ��");
   			return false;
   		}
   		if(otype == "dingzhi" && begintime == "") {
   			alert("����ҵ�����ѡ�����ʱ��");
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
<html:form action="/sms/columnInfo" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
     ��ǰλ��&ndash;&gt;�趨��Ŀ��Ϣ����ʱ��
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
     <td class="labelStyle">
     	��Ŀ��Ϣ
     </td>
     <td class="valueStyle">
       <html:text property="columnInfo" styleClass="writeTextStyle"/>
     </td>
    
    <td class="labelStyle">
     	�趨ʱ��
     </td>
    <td class="valueStyle">
     <html:text property="sendDate" styleClass="writeTextStyle"/>
     <html:hidden property="nickname" styleClass="writeTextStyle"/>
     <img alt="ѡ��ʱ��" src="../html/img/cal.gif"
			onclick="openCal('columnInfoBean','sendDate',false);">
	 <html:text property="sendTime" maxlength="10" size="10"  styleClass="input" readonly="true"/>
       		<input type="button"   value="ѡ��ʱ��" onclick="OpenTime(document.all.sendTime);"/>
     </td>
   </tr>
  
  <tr class="buttonAreaStyle">
    <td colspan="4" align="right">
      <input type="button" name="Submit4" value="�� ��" onClick="tijiao()"  >
    </td>
  </tr>
</table>
</html:form>
</body>
</html>
