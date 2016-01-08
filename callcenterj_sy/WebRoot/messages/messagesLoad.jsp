<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//s;//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>消息管理</title>
<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>

<!-- jquery验证 -->
<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
<script type="text/javascript">

var v_flag="";
function formAction(){
	if(v_flag=="del"){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'"))
			return true;
		else
			return false;
	}
}

//初始化
function init(){	
	<logic:equal name="opertype" value="detail">
		document.getElementById('buttonSubmit').style.display="none";
	</logic:equal>		
	<logic:equal name="opertype" value="insert">
		document.forms[0].action = "messages.do?method=toMessagesOper&type=insert";
		document.getElementById('spanHead').innerHTML="发送消息";
		document.getElementById('buttonSubmit').value=" 发 送 ";
	</logic:equal>
	<logic:equal name="opertype" value="update">
		document.forms[0].action = "messages.do?method=toMessagesOper&type=update";
		document.getElementById('spanHead').innerHTML="修改消息";
		document.getElementById('buttonSubmit').value=" 修 改 ";
	</logic:equal>
	<logic:equal name="opertype" value="delete">
		document.forms[0].action = "messages.do?method=toMessagesOper&type=delete";
		document.getElementById('spanHead').innerHTML="删除消息";
		document.getElementById('buttonSubmit').value=" 删 除 ";
		v_flag="del"
	</logic:equal>		
}
//执行验证
	
<logic:equal name="opertype" value="insert">
$(document).ready(function(){
	$.formValidator.initConfig({formid:"messages",onerror:function(msg){alert(msg)}});	
	$("#receive_id").formValidator({onshow:"请选择接收人",onfocus:"接收人必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择接收人!"});	
	$("#message_content").formValidator({onshow:"请输入消息内容",onfocus:"消息内容不能为空",oncorrect:"消息内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"消息内容两边不能有空符号"},onerror:"消息内容不能为空"});	

})
</logic:equal>
<logic:equal name="opertype" value="update">
  	$(document).ready(function(){
	$.formValidator.initConfig({formid:"messages",onerror:function(msg){alert(msg)}});	
	$("#receive_id").formValidator({onshow:"请选择接收人",onfocus:"接收人必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择接收人!"});	
	$("#message_content").formValidator({onshow:"请输入消息内容",onfocus:"消息内容不能为空",oncorrect:"消息内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"消息内容两边不能有空符号"},onerror:"消息内容不能为空"});	
})
</logic:equal>

		function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].receive_name;
			arrparm[1] = document.forms[0].receive_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/messages/messages.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }

</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init();">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--				<logic:equal name="opertype" value="insert">--%>
<%--		    		当前位置&ndash;&gt;发送消息--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="detail">--%>
		    		当前位置&ndash;&gt;<span id="spanHead">查看消息</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		当前位置&ndash;&gt;修改消息--%>
<%--		    	</logic:equal>--%>
<%--				<logic:equal name="opertype" value="delete">--%>
<%--		    		当前位置&ndash;&gt;删除信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
<html:form action="/messages/messages" method="post" styleId="messages" onsubmit="return formAction();">

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="message_id"/>
    <td class="labelStyle">接收人姓名</td>
    <td class="valueStyle">
    	<logic:equal name="opertype" value="insert">
    	<html:text property="receive_name" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	<html:hidden property="receive_id" />
    	<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="选择接收人" onclick="dep()" width="16" height="16" border="0"/>
    	</logic:equal>
    	
    	<logic:equal name="opertype" value="update">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	<logic:equal name="opertype" value="detail">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	<logic:equal name="opertype" value="delete">
    	  <html:text property="receive_id" styleId="receive_id" styleClass="writeTextStyle" size="40" />
    	</logic:equal>
    	
<%-- 	<html:select property="receive_id" styleId="receive_id">--%>
<%-- 	<option value="">选择接收人</option>--%>
<%--	<logic:iterate id="u" name="uList" >--%>
<%--		<html:option value="${u.value}">${u.label}</html:option>						--%>
<%--	</logic:iterate>--%>
<%--	</html:select>--%>
	
	<div id="receive_idTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">消息内容</td>
    <td class="valueStyle">
    	<html:textarea property="message_content" cols="50" rows="4" styleClass="writeTextStyle" styleId="message_content"/>
    	<div id="message_contentTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">


<input type="submit" name="button" id="buttonSubmit" value="提交"  />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"  >
		&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

</html:form>
</body>
</html:html>
