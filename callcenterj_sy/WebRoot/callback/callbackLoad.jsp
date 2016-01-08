<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<script language="javascript" src="../js/form.js" type="text/javascript"/>


<logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		if(opener.parent.topp){
			//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}else{
			window.opener.location.reload(); //ˢ�¸�ҳ
		}
		
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>����ط�</title>
<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
<script type="text/javascript">
	function checkForm(callback){
        	if (!checkNotNull(callback.callback_man,"�ط���")) return false;
        	if (!checkNotNull(callback.callback_phone,"�طõ绰")) return false;
        	if (!checkNotNull(addstaffer.question_content,"�ط�����")) return false;
        	if (!checkNotNull(callback.callback_content,"�ط�����")) return false;
           return true;
        }
	var url = "callback.do?method=toCallbackOper&type=";
	//���
   	function add(){  
   		var f =document.forms[0];
    	if(checkForm(f)){
   			document.forms[0].action = url + "insert";
			document.forms[0].submit();
		}
   	}
   	//�޸�
   	function update(){
   		var f =document.forms[0];
    	if(checkForm(f)){
	   		document.forms[0].action = url + "update";
			document.forms[0].submit();
		}
   	}
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody"><br>
<html:form action="/callback/callback.do" method="post">
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <html:hidden property="id"/>
  <logic:equal name="opertype" value="insert">
  <tr>
    <td class="labelStyle" width="80">����ID<br></td>
    <td colspan="2" class="valueStyle"><html:text property="question_id" size="25" readonly="true" styleClass="readTextStyle"/>(����id����д)<br></td>
  </tr>
  <tr>
    <td class="labelStyle" width="80">�ط���<br></td>
    <td colspan="2" class="valueStyle"><html:text property="callback_man" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
    <td class="labelStyle" width="80">�طõ绰<br></td>
    <td colspan="2" class="valueStyle"><html:text property="callback_phone" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
  </logic:equal>
    <td class="labelStyle" width="100">�Ƿ�طóɹ�<br></td>
    <td colspan="2" class="valueStyle">
	  <html:select property="is_callback_succ" styleClass="selectStyle">
        <html:option value="�ɹ�">�ɹ�</html:option>
        <html:option value="���ɹ�">���ɹ�</html:option>
      </html:select>
	<br></td>

  <tr><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
    <td class="labelStyle" width="80">�ط�ʱ��<br></td>
    <td colspan="2" class="valueStyle">
    <html:text property="callback_time" value="<%= date %>" onclick="openCal('callback','callback_time',false);" size="10" styleClass="writeTextStyle"/>
    <br></td>
  </tr>
  <logic:equal name="opertype" value="insert">
  <tr>
    <td class="labelStyle">�ط�����<br></td>
    <td colspan="2" class="valueStyle"><html:text property="question_content" size="60" styleClass="writeTextStyle"/><br></td>
  </tr>
  </logic:equal>
  <tr>
    <td class="labelStyle">�ط�����<br></td>
    <td class="valueStyle">&gt;<html:textarea property="callback_content" cols="50" rows="3" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
    <td class="labelStyle">�طñ�ע</td>
    <td class="valueStyle"><br><html:textarea property="remark" cols="50" rows="3" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="3" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" �� �� " onClick="add()">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "  >
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" �� �� " onClick="update()"  >
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" �� �� "  >
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" ɾ �� " onClick="del()"  >
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" �� �� " onClick="javascript:window.close()"  >
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
