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
		alert("操作成功");
		if(opener.parent.topp){
			//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}else{
			window.opener.location.reload(); //刷新父页
		}
		
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>问题回访</title>
<script language="javascript" type="text/JavaScript" src="../js/calendar3.js"></script>
<script type="text/javascript">
	function checkForm(callback){
        	if (!checkNotNull(callback.callback_man,"回访人")) return false;
        	if (!checkNotNull(callback.callback_phone,"回访电话")) return false;
        	if (!checkNotNull(addstaffer.question_content,"回访问题")) return false;
        	if (!checkNotNull(callback.callback_content,"回访内容")) return false;
           return true;
        }
	var url = "callback.do?method=toCallbackOper&type=";
	//添加
   	function add(){  
   		var f =document.forms[0];
    	if(checkForm(f)){
   			document.forms[0].action = url + "insert";
			document.forms[0].submit();
		}
   	}
   	//修改
   	function update(){
   		var f =document.forms[0];
    	if(checkForm(f)){
	   		document.forms[0].action = url + "update";
			document.forms[0].submit();
		}
   	}
   	//删除
   	function del(){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){
   		
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
    <td class="labelStyle" width="80">问题ID<br></td>
    <td colspan="2" class="valueStyle"><html:text property="question_id" size="25" readonly="true" styleClass="readTextStyle"/>(问题id不可写)<br></td>
  </tr>
  <tr>
    <td class="labelStyle" width="80">回访人<br></td>
    <td colspan="2" class="valueStyle"><html:text property="callback_man" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
    <td class="labelStyle" width="80">回访电话<br></td>
    <td colspan="2" class="valueStyle"><html:text property="callback_phone" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
  </logic:equal>
    <td class="labelStyle" width="100">是否回访成功<br></td>
    <td colspan="2" class="valueStyle">
	  <html:select property="is_callback_succ" styleClass="selectStyle">
        <html:option value="成功">成功</html:option>
        <html:option value="不成功">不成功</html:option>
      </html:select>
	<br></td>

  <tr><% String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()); %>
    <td class="labelStyle" width="80">回访时间<br></td>
    <td colspan="2" class="valueStyle">
    <html:text property="callback_time" value="<%= date %>" onclick="openCal('callback','callback_time',false);" size="10" styleClass="writeTextStyle"/>
    <br></td>
  </tr>
  <logic:equal name="opertype" value="insert">
  <tr>
    <td class="labelStyle">回访问题<br></td>
    <td colspan="2" class="valueStyle"><html:text property="question_content" size="60" styleClass="writeTextStyle"/><br></td>
  </tr>
  </logic:equal>
  <tr>
    <td class="labelStyle">回访内容<br></td>
    <td class="valueStyle">&gt;<html:textarea property="callback_content" cols="50" rows="3" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr>
    <td class="labelStyle">回访备注</td>
    <td class="valueStyle"><br><html:textarea property="remark" cols="50" rows="3" styleClass="writeTextStyle"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="3" align="center">

  	 <logic:equal name="opertype" value="insert">
      <input type="button" name="Submit" value=" 添 加 " onClick="add()">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "  >
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="update">
      <input type="button" name="Submit" value=" 修 改 " onClick="update()"  >
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "  >
	 </logic:equal>
	 
	 <logic:equal name="opertype" value="delete">
      <input type="button" name="Submit" value=" 删 除 " onClick="del()"  >
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"  >
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
