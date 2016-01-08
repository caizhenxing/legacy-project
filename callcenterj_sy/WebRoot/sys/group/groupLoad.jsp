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
    
    <title>组管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
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
				document.forms[0].action = "../group/Group.do?method=operGroup&type=insert";
				document.getElementById('spanHead').innerHTML="添加组信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "../group/Group.do?method=operGroup&type=update";
				document.getElementById('spanHead').innerHTML="修改组信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "../group/Group.do?method=operGroup&type=delete";
				document.getElementById('spanHead').innerHTML="删除组信息";
				document.getElementById('buttonSubmit').value="删除";
			</logic:equal>		
		}
		//执行验证
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"Group",onerror:function(msg){alert(msg)}});
			$("#nameId").formValidator({onshow:"请输入组名称",onfocus:"组名称不能为空",oncorrect:"组名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"组名称两边不能有空符号"},onerror:"组名称不能为空"});
<%--			$("#isSys").formValidator({onshow:"请选择是否冻结",onfocus:"是否冻结必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择是否冻结"});--%>
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"Group",onerror:function(msg){alert(msg)}});
			$("#nameId").formValidator({onshow:"请输入组名称",onfocus:"组名称不能为空",oncorrect:"组名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"组名称两边不能有空符号"},onerror:"组名称不能为空"});
<%--			$("#isSys").formValidator({onshow:"请选择是否冻结",onfocus:"是否冻结必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择是否冻结"});--%>
		})
		</logic:equal>
    
<%--    	function checkForm(addstaffer){--%>
<%--            if (!checkNotNull(addstaffer.name,"组名称")) return false;--%>
<%--            if (!checkNotNull(addstaffer.delMark,"是否冻结")) return false;--%>
<%--              return true;--%>
<%--    }--%>
<%--    --%>
<%--    	//添加--%>
<%--    	function add(){    		--%>
<%--    		var f =document.forms[0];    		--%>
<%--    	if(checkForm(f)){ --%>
<%--	    		document.forms[0].action = "../group/Group.do?method=operGroup&type=insert";	    		 --%>
<%--	    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//修改--%>
<%--    	function update(){--%>
<%--    		document.forms[0].action = "../group/Group.do?method=operGroup&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--    	//删除--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "../group/Group.do?method=operGroup&type=delete";--%>
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
  
    <html:form action="/sys/group/Group" method="post" styleId="Group">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		   <td class="navigateStyle" colspan="2">
		    当前位置&ndash;&gt;<span id="spanHead">详细</span>
		    <%-- String type = request.getParameter("type"); if(type==null){out.print("组管理");} if("insert".equals(type.trim())){out.print("添加组信息");}if("update".equals(type.trim())){out.print("修改组信息");}if("delete".equals(type.trim())){out.print("删除组信息");}--%>
		    </td>	 
          <tr>
           <td class="labelStyle">组&nbsp;名&nbsp;称</td>
            <td class="valueStyle">
		    <html:text property="name" styleId="nameId"></html:text>
			<div id="nameIdTip" style="width: 250px;display:inline;"></div>
		    <html:hidden property="id"/>
	        </td>
         </tr>
  
         <tr>
           <td class="labelStyle">是否冻结</td>
            <td class="valueStyle">
		    <html:select property="isSys" styleClass="selectStyle" styleId="isSys">
			<html:option value="1">正&nbsp;&nbsp;&nbsp;&nbsp;常</html:option>
			<html:option value="0">冻&nbsp;&nbsp;&nbsp;&nbsp;结</html:option>
		    </html:select>
<%--<div id="isSysTip" style="width: 250px;display:inline;"></div>--%>
	        </td>
         </tr>
         <tr>
            <td class="labelStyle">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
            <td class="valueStyle">
	        <html:textarea cols="40" rows="4" property="remark"></html:textarea>
	        </td>
        </tr> 
		<tr>
		     <td colspan="2"  class="navigateStyle" style="text-align:right;">
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
