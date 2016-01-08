<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//s;//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html:html locale="true">
<head>
<html:base />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<title>消息管理</title>
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>

<!-- jquery验证 -->
<script src="./../../js/jquery/jquery_last.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="./../../css/validator.css"></link>
<script src="./../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
<script src="./../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
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

		function CountNum()
		{
			var temp = document.forms[0].content.value;
			if(temp != null)
			    msgNum = temp.length;
			else
				msgNum = 0;
			
		    var wordnum = msgNum;
		   
		    document.all("CurWordNum").innerHTML ="<font color='red'  id='fontStyle'>"+msgNum + "</font>个" ;
		    
		}

//初始化
function init(){

	<c:choose>
		<c:when test="${opertype=='detail'}">
			document.getElementById('buttonSubmit').style.display="none";
		</c:when>
		<c:when test="${opertype=='insert'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=insert";
			document.getElementById('spanHead').innerHTML="添加信息";
			document.getElementById('buttonSubmit').value=" 添 加 ";
		</c:when>
		<c:when test="${opertype=='update'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=update";
			document.getElementById('spanHead').innerHTML="修改信息";
			document.getElementById('buttonSubmit').value=" 修 改 ";
		</c:when>
		<c:when test="${opertype=='delete'}">
			document.forms[0].action = "../columnInfo.do?method=toMessagesOper&type=delete";
			document.getElementById('spanHead').innerHTML="删除信息";
			document.getElementById('buttonSubmit').value=" 删 除 ";
		</c:when>
	</c:choose>	
}
//执行验证
<c:choose>				
	<c:when test="${opertype=='insert'}">	
	$(document).ready(function(){
		$.formValidator.initConfig({formid:"messageloadId",onerror:function(msg){alert(msg)}});	
		$("#columnInfoId").formValidator({onshow:"请选择短信栏目",onfocus:"短信栏目必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择短信栏目!"});
		$("#messageNameId").formValidator({onshow:"请选择短信标题",onfocus:"短信标题必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择短信标题!"});		
		$("#messageContentId").formValidator({onshow:"请输入短信内容",onfocus:"短信内容不能为空",oncorrect:"短信内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"短信内容两边不能有空符号"},onerror:"短信内容不能为空"});	
		})
	</c:when>

	<c:when test="${opertype=='update'}">
	$(document).ready(function(){
	  	$.formValidator.initConfig({formid:"messageloadId",onerror:function(msg){alert(msg)}});	
		$("#columnInfoId").formValidator({onshow:"请选择短信栏目",onfocus:"短信栏目必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择短信栏目!"});
		$("#messageNameId").formValidator({onshow:"请选择短信标题",onfocus:"短信标题必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择短信标题!"});		
		$("#messageContentId").formValidator({onshow:"请输入短信内容",onfocus:"短信内容不能为空",oncorrect:"短信内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"短信内容两边不能有空符号"},onerror:"短信内容不能为空"});	
		})
	</c:when>
</c:choose>	

		
<%--	function checkForm(messages){--%>
<%--            if (!checkNotNull(messages.receive_id,"接收人ID")) return false;--%>
<%--            if (!checkNotNull(messages.message_content,"信息内容")) return false;--%>
<%--            return true;--%>
<%--   	}--%>
<%--	var url = "messages.do?method=toMessagesOper&type=";--%>
<%--	//添加--%>
<%--   	function add(){  --%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "update";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//删除--%>
<%--   	function del(){--%>
<%--		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();   		--%>
<%--   		}--%>
<%--   	}--%>

</script>



</head>

<body class="loadBody" onload="init()">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
	<tr>
		<td class="navigateStyle">
    		当前位置&ndash;&gt;<span id="spanHead">查看短信</span>
		</td>
	</tr>
</table>
<html:form action="/sms/columnInfo.do" method="post" styleId="messageloadId" >

<table width="100%" border="0" align="center" class="contentTable">
  <tr><html:hidden property="id"/>
    <td class="labelStyle">短信栏目</td>
    <td class="valueStyle">
 	<html:select property="columnInfo" styleId="columnInfoId">
 	<option value="">请选择</option>
	<logic:iterate id="u" name="ulist" >
		<html:option value="${u.value}">${u.label}</html:option>						
	</logic:iterate>
	</html:select>
	<div id="columnInfoIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">短信标题</td>
    <td class="valueStyle">
    	<html:text property="messageName" size="30" styleClass="writeTextStyle" styleId="messageNameId"/>
    	<div id="messageNameIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">短信内容</td>
    <td class="valueStyle">
    	<html:textarea property="content" cols="50" rows="4" styleClass="writeTextStyle" styleId="messageContentId"
    	    onkeyup="CountNum();" onchange="CountNum();" onfocus="CountNum();"/>
    	<div id="messageContentIdTip" style="width: 0px;display:inline;"></div>
    </td>
  </tr>
  
  <tr>
	<td class="labelStyle">
		当前短信字数
	</td>
	<td class="valueStyle">
		<span class="cpx12red" id="CurWordNum"><font color="red"
			id="fontStyle">0个</font> </span>
	</td>
  </tr>
  
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

<%--  	 <logic:equal name="opertype" value="insert">--%>
<%--      <input type="button" name="Submit" value=" 发 送 " onClick="add()"  >--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" 重 置 "  >--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--      <input type="button" name="Submit" value=" 确 定 " onClick="update()"  >--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" 重 置 "  >--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" 删 除 " onClick="del()"  >--%>
<%--&nbsp;&nbsp;&nbsp;&nbsp;	 </logic:equal>--%>
<input type="submit" name="button" id="buttonSubmit" value=" 提 交 "  />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"  >
&nbsp;&nbsp;&nbsp;    </td>
  </tr>
</table>

</html:form>
</body>
</html:html>
