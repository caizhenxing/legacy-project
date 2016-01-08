<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>12316快讯维护</title>
<script type='text/javascript' src='../js/common.js'></script>
<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
   
   <!-- jquery验证 -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   
<script type="text/javascript">
		//初始化
		function init(){
			<logic:equal name="opertype" value="detail">
	    		document.getElementById('buttonSubmit').style.display="none";
	    	</logic:equal>
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=insert";
				document.getElementById('tdHead').innerHTML="添加快讯";
				document.getElementById('buttonSubmit').value="添加";		    		
	    	</logic:equal>
			
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=update";
				document.getElementById('tdHead').innerHTML="修改快讯";
				document.getElementById('buttonSubmit').value="修改";
	    		
	    	</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "quickMessage.do?method=toQMOper&type=delete";
				document.getElementById('tdHead').innerHTML="删除快讯";
				document.getElementById('buttonSubmit').value="删除";		    		
	    	</logic:equal>
			
		}
		//执行验证
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"quickMessage",onerror:function(msg){alert(msg)}});
			$("#msgTitle").formValidator({onshow:"请输入标题",onfocus:"标题不能为空",oncorrect:"标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"标题两边不能有空符号"},onerror:"标题不能为空"});
			$("#msgContent").formValidator({onshow:"请输入内容",onfocus:"内容不能为空",oncorrect:"内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"内容两边不能有空符号"},onerror:"内容不能为空"});
		})
    	</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"quickMessage",onerror:function(msg){alert(msg)}});
			$("#msgTitle").formValidator({onshow:"请输入标题",onfocus:"标题不能为空",oncorrect:"标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"标题两边不能有空符号"},onerror:"标题不能为空"});
			$("#msgContent").formValidator({onshow:"请输入内容",onfocus:"内容不能为空",oncorrect:"内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"内容两边不能有空符号"},onerror:"内容不能为空"});
		})
    	</logic:equal>
<%--		<logic:equal name="opertype" value="delete">--%>
<%--		$(document).ready(function(){--%>
<%--		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){   		--%>
<%--   		document.forms[0].submit();--%>
<%--   		}		--%>
<%--		})--%>
<%--		</logic:equal>--%>
		
<%--	function checkForm(custinfo){--%>
<%--            if (!checkNotNull(quickMessage.msgTitle,"标题")) return false;--%>
<%--            if (!checkNotNull(quickMessage.msgContent,"内容")) return false;--%>
<%--            --%>
<%--              return true;--%>
<%--    }--%>
<%--	var url = "quickMessage.do?method=toQMOper&type=";--%>
<%--	//添加--%>
<%--   	function add(){--%>
<%--   		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--   		var f =document.forms[0];--%>
<%--	   	document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
   	//删除
   	function del(){
		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'"))
			return true;
   		else
   			return false;
   	}
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init()">
<logic:notEqual name="opertype" value="delete">
<html:form action="screen/quickMessage.do" method="post" styleId="quickMessage">
<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		添加快讯--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		查看快讯
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		修改快讯--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		删除快讯--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">标题</td>
    <td  class="valueStyle">
      <html:text property="msgTitle" size="13" styleClass="input" styleId="msgTitle"/>
<%--      <font color="red">*</font>--%>
		<div id="msgTitleTip" style="width: 150px;display:inline;"></div>
      <html:hidden property="id"/>      
      </td>
    </tr>
    <tr>
        <td class="labelStyle">内容</td>
    <td class="valueStyle">
    	<html:textarea property="msgContent" rows="5" cols="50" styleId="msgContent"></html:textarea>
<%--    	<font color="red">*</font>--%>
    	<div id="msgContentTip" style="width: 150px;display:inline;"></div>
    </td>
    </tr>
<%--  <tr>--%>
<%--    	<td class="labelStyle">创建日期</td>--%>
<%--    	<td class="valueStyle" colspan="5">--%>
<%--			<html:text property="createDate" size="50"--%>
<%--				styleClass="writeTextStyle" readonly="true" />--%>
<%--		</td>--%>
<%--  </tr>--%>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">

<%--  	 <logic:equal name="opertype" value="insert">--%>
<%--      <input type="button" name="Submit" value=" 添 加 " onClick="add()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--      <input type="button" name="Submit" value=" 确 定 " onClick="update()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>--%>
<%--	 </logic:equal>--%>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" 删 除 " onClick="del()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	 </logic:equal>--%>
	  <input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value="关闭" onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</logic:notEqual>
<logic:equal name="opertype" value="delete">
<html:form action="screen/quickMessage.do" method="post" styleId="quickMessage" onsubmit="return del();">
<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="tdHead">
		    		查看快讯
				</td>
			</tr>
		</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">标题</td>
    <td  class="valueStyle">
      <html:text property="msgTitle" size="13" styleClass="input" styleId="msgTitle"/>
<%--      <font color="red">*</font>--%>
		<div id="msgTitleTip" style="width: 150px;display:inline;"></div>
      <html:hidden property="id"/>      
      </td>
    </tr>
    <tr>
        <td class="labelStyle">内容</td>
    <td class="valueStyle">
    	<html:textarea property="msgContent" rows="5" cols="50" styleId="msgContent"></html:textarea>
<%--    	<font color="red">*</font>--%>
    	<div id="msgContentTip" style="width: 150px;display:inline;"></div>
    </td>
    </tr>
<%--  <tr>--%>
<%--    	<td class="labelStyle">创建日期</td>--%>
<%--    	<td class="valueStyle" colspan="5">--%>
<%--			<html:text property="createDate" size="50"--%>
<%--				styleClass="writeTextStyle" readonly="true" />--%>
<%--		</td>--%>
<%--  </tr>--%>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">
	  <input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value="关闭" onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</logic:equal>		
</body>
</html>
