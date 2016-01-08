<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>座席管理</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<link href="../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%--<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
<script language="javascript" src="../../js/form.js"></script>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
    
<script type="text/javascript">
		//初始化
		function init(){	
			<logic:equal name="opertype" value="detail">
			document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=insert";
				document.getElementById('spanHead').innerHTML="添加座席信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=update";
				document.getElementById('spanHead').innerHTML="修改座席信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "UserOper.do?method=operUserLogin&type=delete";
				document.getElementById('spanHead').innerHTML="删除座席信息";
				document.getElementById('buttonSubmit').value="删除";
			</logic:equal>		
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"UserOper",onerror:function(msg){alert(msg)}});
			$("#userId").formValidator({onshow:"请输入座席工号",onfocus:"座席工号不能为空",oncorrect:"座席工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"座席工号两边不能有空符号"},onerror:"座席工号不能为空"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});	
			$("#sysRole").formValidator({onshow:"请选择座席角色",onfocus:"座席角色必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择座席角色!"});
			$("#sysGroup").formValidator({onshow:"请选择所在组",onfocus:"所在组必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择所在组!"});			
			$("#password").formValidator({onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"密码不能为空"});
			$("#repassword").formValidator({onshow:"请输入验证密码",onfocus:"两次密码必须一致",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"验证密码两边不能有空符号"},onerror:"验证密码不能为空"}).compareValidator({desid:"password",operateor:"=",onerror:"2次密码不一致"});
			$("#isFreeze").formValidator({onshow:"请选择是否冻结",onfocus:"是否冻结必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择是否冻结!"});
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"UserOper",onerror:function(msg){alert(msg)}});
			$("#userId").formValidator({onshow:"请输入座席工号",onfocus:"座席工号不能为空",oncorrect:"座席工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"座席工号两边不能有空符号"},onerror:"座席工号不能为空"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});	
			$("#sysRole").formValidator({onshow:"请选择座席角色",onfocus:"座席角色必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择座席角色!"});
			$("#sysGroup").formValidator({onshow:"请选择所在组",onfocus:"所在组必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择所在组!"});			
			$("#password").formValidator({onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"密码不能为空"});
			$("#repassword").formValidator({onshow:"请输入验证密码",onfocus:"两次密码必须一致",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"验证密码两边不能有空符号"},onerror:"验证密码不能为空"}).compareValidator({desid:"password",operateor:"=",onerror:"2次密码不一致"});
			$("#isFreeze").formValidator({onshow:"请选择是否冻结",onfocus:"是否冻结必须选择",oncorrect:"正确",defaultvalue:""}).inputValidator({min:1,onerror: "没有选择是否冻结!"});
		})
		</logic:equal>


<%--  function checkForm(addstaffer){  	--%>
<%--        	--%>
<%--        	if (!checkNotNull(addstaffer.userId,"ID")) return false;--%>
<%--        	if (!checkNotNull(addstaffer.userName,"用户名")) return false;--%>
<%--        	 if (addstaffer.password.value !=addstaffer.repassword.value)--%>
<%--            {--%>
<%--            	alert("密码与验证码不一致");--%>
<%--            	return false;--%>
<%--            }--%>
<%--			return true;--%>
<%--        }--%>
<%--	function openwin(param)--%>
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
<%--	    	//添加--%>
<%--    	function add(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=insert";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	--%>
<%--    	//修改--%>
<%--    	function update(){--%>
<%--    	    var f =document.forms[0];--%>
<%--    	    if(checkForm(f)){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=update";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
<%--    	//删除--%>
<%--    	function del(){--%>
<%--    		document.forms[0].action = "UserOper.do?method=operUserLogin&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
<%--	--%>
		
	function toback(){
			opener.parent.topp.document.all.btnsel.click();
		}
</SCRIPT>
  </head>
  
  <body onunload="toback()" class="loadBody" onload="init();"><br>
  <logic:notEmpty name="operSign">
	  <script>
	  	alert("操作成功！"); 
	  	opener.parent.topFrame.document.all.btnsel.click();
	  	window.close();
	  </script>
	</logic:notEmpty>
  <html:form action="/sys/user/UserOper" styleId="UserOper">
<html:hidden property="id"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
	
	<tr>
		<td class="navigateStyle">
		当前位置&ndash;&gt;<span id="spanHead">详细</span>
		<%-- String type = request.getParameter("type"); if(type==null){out.print("座席管理");} if("insert".equals(type.trim())){out.print("添加座席信息");}if("update".equals(type.trim())){out.print("修改座席信息");}if("delete".equals(type.trim())){out.print("删除座席信息");} if("detail".equals(type.trim())){out.print("座席详细信息");}--%>
		</td>
	</tr>

</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
  	<td class="labelStyle">
  		座席工号	
  	</td>
  	<td class="valueStyle">
	<logic:equal name="opertype" value="insert">
   		<html:text property="userId" styleClass="writeTextStyle" styleId="userId"></html:text>
   		<div id="userIdTip" style="width: 10px;display:inline;"></div>
   	</logic:equal>  	
   	<logic:notEqual name="opertype" value="insert">
   		<html:text property="userId" styleClass="writeTextStyle" readonly="true"></html:text>
   	</logic:notEqual>
<%--  	<html:text property="userId" styleClass="writeTextStyle"></html:text>--%>
  	</td>
  	<td width="400" height="100%" rowspan="8">
  		<table width="100%" height="100%" style="border: solid  #000000 1px">
  			<tr height="20">
  				<td class="labelStyle" colspan="2">
  					审核权限管理
  				</td>
  			</tr>
  			<tr height="20">
  				<td width="48" class="labelStyle" style="text-indent: 0;">
  					金典案例
  				</td>
  				<td class="valueStyle">
  					普通案例库<html:checkbox property="auditings" value="普通案例库"/>
  					焦点案例库<html:checkbox property="auditings" value="焦点案例库"/>
  					会诊案例库<html:checkbox property="auditings" value="会诊案例库"/>
  					效果案例库<html:checkbox property="auditings" value="效果案例库"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					供求价格
  				</td>
  				<td class="valueStyle">
  					农产品供求库<html:checkbox property="auditings" value="农产品供求库"/>
  					农产品价格库<html:checkbox property="auditings" value="农产品价格库"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					金农产品
  				</td>
  				<td class="valueStyle">
  					焦点追踪库 
  					一审<html:checkbox property="auditings" value="焦点追踪库一审"/>
  					二审<html:checkbox property="auditings" value="焦点追踪库二审"/>
  					三审<html:checkbox property="auditings" value="焦点追踪库三审"/>
  					<br>
  					市场分析库 
  					一审<html:checkbox property="auditings" value="市场分析库一审"/>
  					二审<html:checkbox property="auditings" value="市场分析库二审"/>
  					三审<html:checkbox property="auditings" value="市场分析库三审"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					企业服务
  				</td>
  				<td class="valueStyle">
  					企业信息库<html:checkbox property="auditings" value="企业信息库"/>
  				</td>
  			</tr>
  			<tr height="20">
  				<td class="labelStyle" style="text-indent: 0;">
  					 医疗服务
  				</td>
  				<td class="valueStyle">
  					普通医疗服务信息库<html:checkbox property="auditings" value="普通医疗服务信息库"/>
  					预约医疗服务信息库<html:checkbox property="auditings" value="预约医疗服务信息库"/>
  				</td>
  			</tr>
  			<tr>
  				<td class="labelStyle" style="text-indent: 0;">
  					专题调查
  				</td>
  				<td class="valueStyle">
  					调查问卷设计库<html:checkbox property="auditings" value="调查问卷设计库"/>
  					调查信息分析库<html:checkbox property="auditings" value="调查信息分析库"/>
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		用户姓名	
  	</td>
  	<td class="valueStyle">
  		<html:text property="userName" styleClass="writeTextStyle" styleId="userName"></html:text>
  		<div id="userNameTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		座席角色
  	</td>
  	<td class="valueStyle">
  		<html:select property="sysRole" styleClass="selectStyle" style="width:130px" styleId="sysRole">
		<html:option value="">请选择</html:option>
		<html:options collection="RoleList" property="value" labelProperty="label"/>
		</html:select>
		<div id="sysRoleTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
      <tr>
  	<td class="labelStyle">
  		所&nbsp;在&nbsp;组	
  	</td>
  	<td class="valueStyle">
  		
  		<html:select property="sysGroup" styleClass="selectStyle" style="width:130px" styleId="sysGroup">
		<html:option value="">请选择</html:option>
		<html:options collection="GroupList" property="value" labelProperty="label"/>
		</html:select>
		<div id="sysGroupTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>

  
    <tr>
  	<td class="labelStyle">
  		<font color="red">登录密码</font> 	
  	</td>
  	<td class="valueStyle">
  		<html:password property="password" styleClass="writeTextStyle" styleId="password"></html:password>
  		<div id="passwordTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
   <tr>
  	<td class="labelStyle">
  		验证密码	
  	</td>
  	<td class="valueStyle">
  	<html:password property="repassword" styleClass="writeTextStyle" styleId="repassword"></html:password>
  	<div id="repasswordTip" style="width: 10px;display:inline;"></div>
  	</td>
  </tr>
    <tr>
  	<td class="labelStyle">
  		是否冻结
  	</td>
  	<td class="valueStyle">
		<html:select property="isFreeze" styleClass="selectStyle" style="width:130px" styleId="isFreeze">
			<html:option value="">请选择</html:option>
			<html:option value="0">是</html:option>
			<html:option value="1">否</html:option>
		</html:select>
		<div id="isFreezeTip" style="width: 10px;display:inline;"></div>	
  	</td>
  </tr>   
  <tr style="display:none;">
  	<td class="labelStyle">
  		部&nbsp;&nbsp;&nbsp;&nbsp;门	
  	</td>
  	<td class="valueStyle">
  		<html:select property="departmentId" styleClass="selectStyle">
  		<html:option value="">请选择</html:option>
		<html:options collection="depList" property="value" labelProperty="label"/>
		</html:select>
  		
  	</td>
  </tr>
  <tr>
  <td colspan="2" height="40px;">
  </td>
  </tr>
  
  
  <tr>
  
  		<td bgcolor="#ffffff" colspan="4" align="center" class="navigateStyle" style="text-align:right;">
<%--				<logic:equal name="opertype" value="insert">--%>
<%--					<input type="button" name="addbtn"  value="添加" onclick="add()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
<%--				--%>
<%--				<logic:equal name="opertype" value="update">--%>
<%--					<input type="button" name="updatebtn" value="确定" onclick="update()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
<%--				<logic:equal name="opertype" value="delete">--%>
<%--					<input type="button" name="delbtn" value="删除" onclick="del()"  class="buttonStyle"/>--%>
<%--				</logic:equal>--%>
					<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle" />
					<input type="button" name="" value="关闭" onClick="javascript:window.close();" class="buttonStyle"/>
				</td>
  
  </tr>
  
</table>
</html:form>
  </body>
</html:html>
<script>
	var auditing = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("userform")).get("auditing") %>";
	var checkboxs = document.all.item("auditings");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(auditing.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
	
</script>