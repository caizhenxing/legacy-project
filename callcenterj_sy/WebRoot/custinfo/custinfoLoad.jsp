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
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>普通用户管理</title>
<script type='text/javascript' src='../js/common.js'></script>
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
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=insert";
			document.getElementById('spanHead').innerHTML="添加普通用户信息";
			document.getElementById('buttonSubmit').value=" 添 加 ";
		</logic:equal>
		<logic:equal name="opertype" value="update">
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=update";
			document.getElementById('spanHead').innerHTML="修改普通用户信息";
			document.getElementById('buttonSubmit').value=" 修 改 ";
		</logic:equal>
		<logic:equal name="opertype" value="delete">
			document.forms[0].action = "custinfo.do?method=toCustinfoOper&type=delete";
			document.getElementById('spanHead').innerHTML="删除普通用户信息";
			document.getElementById('buttonSubmit').value=" 删 除 ";
			v_flag="del"
		</logic:equal>		
	}
	//执行验证
		
	<logic:equal name="opertype" value="insert">
	$(document).ready(function(){  
		$.formValidator.initConfig({formid:"custinfo",onerror:function(msg){alert(msg)}});	
		$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
		$("#cust_addr").formValidator({onshow:"请输入地址",onfocus:"地址不能为空",oncorrect:"地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"地址两边不能有空符号"},onerror:"地址不能为空"});
<%--		$("#cust_pcode").formValidator({empty:true,onshow:"请输入邮编，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"邮编",onempty:"没有填写邮编"}).inputValidator({min:100000,max:999999,type:"value",onerror:"邮编格式不正确"});				--%>
<%--		$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：024-87654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"住宅电话格式不正确"});--%>
<%--		$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});--%>
<%--		$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：024-87654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"办公电话格式不正确"});--%>
<%--		$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
	})
	</logic:equal>
	<logic:equal name="opertype" value="update">
	$(document).ready(function(){
		$.formValidator.initConfig({formid:"custinfo",onerror:function(msg){alert(msg)}});	
		$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
		$("#cust_addr").formValidator({onshow:"请输入地址",onfocus:"地址不能为空",oncorrect:"地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"地址两边不能有空符号"},onerror:"地址不能为空"});
<%--		$("#cust_pcode").formValidator({empty:true,onshow:"请输入邮编，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"邮编",onempty:"没有填写邮编"}).inputValidator({min:100000,max:999999,type:"value",onerror:"邮编格式不正确"});				--%>
<%--		$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：024-87654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"住宅电话格式不正确"});--%>
<%--		$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});--%>
<%--		$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：024-87654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^[[0-9]{3}-|\[0-9]{4}-]?([0-9]{8}|[0-9]{7})?$",onerror:"办公电话格式不正确"});--%>
<%--		$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
	
	})
	</logic:equal>
	


<%--	function checkForm(){ --%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			var pattern = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--			var pattern2 = /^.+@.+\..+$/;--%>
<%--			var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_pcode=document.forms[0].cust_pcode.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("用户姓名不能为空");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--			if(document.forms[0].cust_addr.value.length==0){--%>
<%--				alert("用户地址不能为空");--%>
<%--				document.forms[0].cust_addr.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--		 	if (cust_pcode!=""&&!pattern1.test(cust_pcode)) {--%>
<%--		 		alert("邮政编码必须为合法数字");--%>
<%--		 		document.forms[0].cust_pcode.focus();--%>
<%--		 		document.forms[0].cust_pcode.select();--%>
<%--		 		return false;--%>
<%--		 	}			--%>
<%--		 	if (home_tel!=""&&!pattern.test(home_tel)) {--%>
<%--		 		alert("请正确填写住宅电话号码！");--%>
<%--		 		document.forms[0].cust_tel_home.focus();--%>
<%--		 		document.forms[0].cust_tel_home.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (mob_tel!=""&&!pattern.test(mob_tel)) {--%>
<%--		 		alert("请正确填写手机电话号码！");--%>
<%--		 		document.forms[0].cust_tel_mob.focus();--%>
<%--		 		document.forms[0].cust_tel_mob.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		 	--%>
<%--		 	if (work_tel!=""&&!pattern.test(work_tel)) {--%>
<%--		 		alert("请正确填写工作电话号码！");--%>
<%--		 		document.forms[0].cust_tel_work.focus();--%>
<%--		 		document.forms[0].cust_tel_work.select();--%>
<%--		 		return false;--%>
<%--		 	}--%>
<%--		    --%>
<%--			if (email!=""&&!pattern2.test(email)) {--%>
<%--				alert("请正确填写email");--%>
<%--				document.forms[0].cust_email.focus();--%>
<%--				document.forms[0].cust_email.select();--%>
<%--				return false;--%>
<%--			}         --%>
<%--            return true;--%>
<%--   		}--%>
<%--	var url = "custinfo.do?method=toCustinfoOper&type=";--%>
<%--	//添加--%>
<%--   	function add(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "update";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//删除--%>
<%--   	function del(){--%>
<%--		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>
	
</script>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

</head>

<body class="loadBody" onload="init();">
<html:form action="custinfo/custinfo.do" method="post" styleId="custinfo" onsubmit="return formAction();">

 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="navigateStyle">
<%--		    	<logic:equal name="opertype" value="insert">--%>
<%--		    		当前位置&ndash;&gt;添加普通用户信息--%>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="detail">--%>
		    		当前位置&ndash;&gt;<span id="spanHead">查看普通用户信息</span>
<%--		    	</logic:equal>--%>
<%--		    	<logic:equal name="opertype" value="update">--%>
<%--		    		当前位置&ndash;&gt;修改普通用户信息--%>
<%--		    	</logic:equal>--%>
<%--		    	 <logic:equal name="opertype" value="delete">--%>
<%--		    		当前位置&ndash;&gt;删除普通用户信息--%>
<%--		    	</logic:equal>--%>
		    </td>
		  </tr>
		</table>

<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle" width="90">姓名</td>
    <td class="valueStyle">
      <html:text property="cust_name" size="8" styleClass="input" styleId="cust_name"/>
		<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
      <html:hidden property="cust_id"/></td>
    <td class="labelStyle">性别</td>
    <td class="valueStyle">
      <html:select property="dict_sex">
        <html:options collection="sexList" property="value" labelProperty="label"/>
      </html:select>      </td>
    </tr>
  <tr>
    <logic:equal name="opertype" value="insert">
    	<td class="labelStyle">地址</td>
    	<td class="valueStyle" colspan="5">
			<html:text property="cust_addr" size="25" styleClass="writeTextStyle" styleId="cust_addr"/>
			<input type="button" value="选择地址" onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
			<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
		</td>
    </logic:equal>
    
    <logic:equal name="opertype" value="update">
    	<td class="labelStyle">地址</td>
    	<td class="valueStyle" colspan="5">
			<html:text property="cust_addr" size="25" styleClass="writeTextStyle" styleId="cust_addr"/>
			<input type="button" value="选择地址" onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
			<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
		</td>
    </logic:equal>
    
    <logic:equal name="opertype" value="detail">
    	<td class="labelStyle">地址</td>
    	<td colspan="3" class="valueStyle"><html:text property="cust_addr" size="46" styleClass="input"/></td>
    </logic:equal>
  </tr>
  <tr>
    <td class="labelStyle">邮编</td>
    <td class="valueStyle">
      	<html:text property="cust_pcode" size="5" styleClass="input" styleId="cust_pcode"/>
		<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
    </td>
    <td class="labelStyle">&nbsp;E_mail&nbsp;</td>
    <td class="valueStyle">   
      	<html:text property="cust_email" size="13" styleClass="input" styleId="cust_email"/>
		<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">宅电</td>
    <td class="valueStyle">
	    <html:text property="cust_tel_home" size="13" styleClass="input"  styleId="cust_tel_home"/>
		<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
	</td>
    <td class="labelStyle">办公电话</td>
    <td class="valueStyle">
      <html:text property="cust_tel_work" size="13" styleClass="input" styleId="cust_tel_work"/>
	  <div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">手机</td>
    <td class="valueStyle">
	    <html:text property="cust_tel_mob" size="11" styleClass="input" styleId="cust_tel_mob"/>
		<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
	</td>
    <td class="labelStyle">传真号码</td>
    <td class="valueStyle">
      <html:text property="cust_fax" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">客户行业</td>
    <td class="valueStyle"><html:text property="cust_voc" size="13" styleClass="input"/></td>
    <td class="labelStyle">行业规模</td>
    <td class="valueStyle">
      <html:text property="cust_scale" size="13" styleClass="input"/>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">客户类型</td>
    <td colspan="3" class="valueStyle">
		<html:select property="cust_type" styleClass="Next_pulls" style="width: 131px;">
			<html:option value="SYS_TREE_0000002109">普通农户</html:option>
			<html:option value="SYS_TREE_0000002103">专家</html:option>
			<html:option value="SYS_TREE_0000002104">企业</html:option>
			<html:option value="SYS_TREE_0000002105">媒体</html:option>
			<html:option value="SYS_TREE_0000002106">政府</html:option>
			<html:option value="SYS_TREE_0000002108">联络员</html:option>
		</html:select>
    </td>
    </tr>
  <tr>
    <td class="labelStyle">备注</td>
    <td colspan="3" class="valueStyle"><html:textarea property="remark" cols="50" rows="3"/><br></td>
  </tr>
  <tr class="buttonAreaStyle">
    <td colspan="4" align="center">
<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle"/>
  	 <logic:equal name="opertype" value="insert">
<%--      <input type="button" name="Submit" value=" 添 加 " onClick="add()"   class="buttonStyle"/>--%>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>
	 </logic:equal>
<%--	 --%>
	 <logic:equal name="opertype" value="update">
<%--      <input type="button" name="Submit" value=" 确 定 " onClick="update()"   class="buttonStyle"/>--%>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "   class="buttonStyle"/>
	 </logic:equal>
<%--	 --%>
<%--	 <logic:equal name="opertype" value="delete">--%>
<%--      <input type="button" name="Submit" value=" 删 除 " onClick="del()"   class="buttonStyle"/>--%>
<%--      &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	 </logic:equal>--%>
	  
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()"   class="buttonStyle"/>
		&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
</table>
</html:form>
</body>
</html>
