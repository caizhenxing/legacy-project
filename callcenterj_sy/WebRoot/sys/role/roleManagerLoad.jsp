<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="../../style.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>角色操作</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
    
    <!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
    <script language="javascript">
    //初始化
		function init(){
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "../role/Role.do?method=operRole&type=insert";
				document.getElementById('tdHead').innerHTML="添加角色信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../role/Role.do?method=operRole&type=update";
				document.getElementById('tdHead').innerHTML="修改角色信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../role/Role.do?method=operRole&type=delete";
				document.getElementById('tdHead').innerHTML="删除角色信息";
				document.getElementById('buttonSubmit').value="删除";
			</logic:equal>
			
		}
		//执行验证
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"roleId",onerror:function(msg){alert(msg)}});
			$("#name").formValidator({onshow:"请输入角色名称",onfocus:"角色名称不能为空",oncorrect:"角色名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"角色名称两边不能有空符号"},onerror:"角色名称不能为空"});
		})
    
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.name,"角色名称")) return false;--%>
<%--            if (!checkNotNull(addstaffer.delMark,"是否冻结")) return false;--%>
<%--              return true;--%>
<%--    }--%>
<%--    --%>
<%--    	//添加--%>
<%--    	function add(){    		--%>
<%--    		var f =document.forms[0];    		--%>
<%--    	if(checkForm(f)){ --%>
<%--	    		document.forms[0].action = "../role/Role.do?method=operRole&type=insert";	    		 --%>
<%--	    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//修改--%>
<%--    	function update(){--%>
<%--    		document.forms[0].action = "../role/Role.do?method=operRole&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	//删除--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../role/Role.do?method=operRole&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	--%>
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
		//返回页面
		function toback(){
			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody" onload="init();">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("操作成功");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/sys/role/Role" method="post" styleId="roleId">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
   			 <td colspan="2" class="labelStyle" id="tdHead">角色管理</td>
  			</tr>
		 
          <tr>
           <td class="labelStyle">角色名称</td>
            <td class="valueStyle">
		    <html:text property="name" styleClass="writeTextStyle" styleId="name"></html:text>
		    <div id="nameTip" style="width: 150px;display:inline;"></div>
		    <html:hidden property="id"/>
	        </td>
         </tr>
  
         <tr>
           <td class="labelStyle">是否冻结</td>
            <td class="valueStyle">
		    <html:select property="delMark" styleClass="selectStyle">
			<html:option value="1">正常</html:option>
			<html:option value="0">冻结</html:option>
		    </html:select>
	        </td>
         </tr>
         <tr>
            <td class="labelStyle">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
            <td class="valueStyle">
	        <html:textarea cols="50" rows="4" styleClass="writeTextStyle" property="remark"></html:textarea>
	        </td>
        </tr> 
		<tr>
		     <td colspan="2"  class="buttonAreaStyle">
<%--		    <logic:equal name="opertype" value="insert">--%>
<%--		     <input name="btnAdd" type="button"   value="添加" onclick="add()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="update">--%>
<%--		     <input name="btnAdd" type="button"   value="确定" onclick="update()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
<%--		    <logic:equal name="opertype" value="delete">--%>
<%--		     <input name="btnAdd" type="button"   value="删除" onclick="del()" class="buttonStyle"/>&nbsp;&nbsp;--%>
<%--		    </logic:equal>--%>
			<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
		    <input name="btnReset" type="button"   value="取消" onclick="javascript:window.close();" class="buttonStyle"/></td>
		  
		</tr>
	</table>
    </html:form>
  </body>
</html:html>
