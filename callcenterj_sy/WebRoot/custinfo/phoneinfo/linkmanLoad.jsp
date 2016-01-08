<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
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
<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>联络员信息管理</title>
		<script type='text/javascript' src='../../js/msg.js'></script>
		<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
   		<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></SCRIPT>
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
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=insert";
					document.getElementById('spanHead').innerHTML="添加联络员信息";
					document.getElementById('buttonSubmit').value=" 添 加 ";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=update";
					document.getElementById('spanHead').innerHTML="修改联络员信息";
					document.getElementById('buttonSubmit').value=" 修 改 ";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "phoneinfo.do?method=toLinkManOper&type=delete";
					document.getElementById('spanHead').innerHTML="删除联络员信息";
					document.getElementById('buttonSubmit').value=" 删 除 ";
					v_flag="del"
				</logic:equal>		
			}
			//执行验证
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
				$("#cust_idcard").formValidator({empty:false,onshow:"请输入15或18位的身份证号",onfocus:"身份证号不能为空",oncorrect:"身份证号正确",onempty:"没有输入身份证号"}).regexValidator({regexp:"idcard",datatype:"enum",onerror:"输入的身份证号格式不正确"});
<%--				$("#cust_idcard").formValidator({empty:false,onshow:"请输入身份证号",onfocus:"身份证号不能为空",oncorrect:"身份证号合法",onempty:"没有填写身份证号"}).functionValidator({fun:isCardID});--%>
				$("#cust_addr").formValidator({onshow:"请输入地址",onfocus:"地址不能为空",oncorrect:"地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"地址两边不能有空符号"},onerror:"地址不能为空"});	
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:6,max:6,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码必须为六位"});
				$("#cust_tel_home").formValidator({empty:false,onshow:"请输入电话",onfocus:"如:02423448833，无杠线，区号完整，多个电话用逗号隔开",oncorrect:"电话合法",onempty:"没有填写电话"}).regexValidator({regexp:"^((0|1)[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"电话格式不正确"});
				$("#cust_way_bys").formValidator({onshow:"请选择用户类别(至少选一个)",onfocus:"用户类别必须选择",oncorrect:"用户类别选择正确",onempty:"没有选择用户类别"}).inputValidator({min:1,empty:"没有选择用户类别",onerror:"没有选择用户类别"});
				$("#dict_cust_scale").formValidator({onshow:"如:生猪年养殖300头，玉米种植30亩",onfocus:"从事项目及生产规模不能为空",oncorrect:"从事项目及生产规模合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"从事项目及生产规模两边不能有空符号"},onerror:"从事项目及生产规模不能为空"});	
				$("#cust_work_ways").formValidator({onshow:"请选择工作方式(至少选一个)",onfocus:"工作方式必须选择",oncorrect:"工作方式选择正确",onempty:"没有选择工作方式"}).inputValidator({min:1,empty:"没有选择工作方式",onerror:"没有选择工作方式"});
				$("#cust_develop_time").formValidator({onshow:"请输入发展时间",onfocus:"发展时间不能为空",oncorrect:"发展时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"发展时间两边不能有空符号"},onerror:"发展时间不能为空"});	
				$("#cust_job").formValidator({onshow:"请选定报价栏目",onfocus:"报价栏目不能为空",oncorrect:"报价栏目合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"报价两边不能有空符号"},onerror:"报价栏目不能为空"});
					
					
<%--				$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});--%>
<%--				$("#cust_age").formValidator({empty:true,onshow:"请输入年龄，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"年龄",onempty:"没有填写年龄"}).inputValidator({min:1,max:99,type:"value",onerrormin:"年龄必须大于等于1",onerrormax:"年龄必须小于等于99",onerror:"年龄必须在1-99之间"});				--%>
				
				
<%--				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：02487654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"办公电话格式不正确"});--%>
<%--				$("#cust_email").formValidator({empty:false,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
				
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});
				$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
				$("#cust_idcard").formValidator({empty:false,onshow:"请输入15或18位的身份证号",onfocus:"身份证号不能为空",oncorrect:"身份证号正确",onempty:"没有输入身份证号"}).regexValidator({regexp:"idcard",datatype:"enum",onerror:"输入的身份证号格式不正确"});
<%--				$("#cust_idcard").formValidator({empty:false,onshow:"请输入身份证号",onfocus:"身份证号不能为空",oncorrect:"身份证号合法",onempty:"没有填写身份证号"}).functionValidator({fun:isCardID});--%>
				$("#cust_addr").formValidator({onshow:"请输入地址",onfocus:"地址不能为空",oncorrect:"地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"地址两边不能有空符号"},onerror:"地址不能为空"});	
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:6,max:6,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码必须为六位"});
				$("#cust_tel_home").formValidator({empty:false,onshow:"请输入电话",onfocus:"如:02423448833，无杠线，区号完整，多个电话用逗号隔开",oncorrect:"电话合法",onempty:"没有填写电话"}).regexValidator({regexp:"^((0|1)[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"电话格式不正确"});
				$("#cust_way_bys").formValidator({onshow:"请选择用户类别(至少选一个)",onfocus:"用户类别必须选择",oncorrect:"用户类别选择正确",onempty:"没有选择用户类别"}).inputValidator({min:1,empty:"没有选择用户类别",onerror:"没有选择用户类别"});
				$("#dict_cust_scale").formValidator({onshow:"如:生猪年养殖300头，玉米种植30亩",onfocus:"从事项目及生产规模不能为空",oncorrect:"从事项目及生产规模合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"从事项目及生产规模两边不能有空符号"},onerror:"从事项目及生产规模不能为空"});	
				$("#cust_work_ways").formValidator({onshow:"请选择工作方式(至少选一个)",onfocus:"工作方式必须选择",oncorrect:"工作方式选择正确",onempty:"没有选择工作方式"}).inputValidator({min:1,empty:"没有选择工作方式",onerror:"没有选择工作方式"});
				$("#cust_develop_time").formValidator({onshow:"请输入发展时间",onfocus:"发展时间不能为空",oncorrect:"发展时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"发展时间两边不能有空符号"},onerror:"发展时间不能为空"});	
				$("#cust_job").formValidator({onshow:"请选定报价栏目",onfocus:"报价栏目不能为空",oncorrect:"报价栏目合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"报价两边不能有空符号"},onerror:"报价栏目不能为空"});
			})
			</logic:equal>
			
			
<%--		function checkForm(){ --%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			var pattern = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--			var pattern2 = /^.+@.+\..+$/;--%>
<%--			var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_age=document.forms[0].cust_age.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("用户姓名不能为空");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			--%>
<%--			--%>
<%--		 	if (cust_age!=""&&!pattern1.test(cust_age)) {--%>
<%--		 		alert("年龄必须为合法数字");--%>
<%--		 		document.forms[0].cust_age.focus();--%>
<%--		 		document.forms[0].cust_age.select();--%>
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
<%--		var url = "phoneinfo.do?method=toLinkManOper&type=";--%>
<%--		var selectUrl = "custinfo/phoneinfo.do?method=toLinkManOper&custType=";--%>
<%--		--%>
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
<%--   		--%>
<%--   		document.forms[0].action = url + "delete";--%>
<%--   		document.forms[0].submit();--%>
<%--   		--%>
<%--   		}--%>
<%--   	}--%>
	
</script>

		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

	</head>
	<% String opertype = (String) request.getAttribute("opertype"); %>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		当前位置&ndash;&gt;添加联络员信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		当前位置&ndash;&gt;<span id="spanHead">查看联络员信息</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		当前位置&ndash;&gt;修改联络员信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		当前位置&ndash;&gt;删除联络员信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td class="labelStyle">
						编&nbsp;&nbsp;&nbsp;&nbsp;号
					</td>
					<td class="valueStyle">
						<html:text property="cust_number" size="20" styleClass="writeTypeStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座&nbsp;席&nbsp;号
					</td>
					<td class="valueStyle">
						<html:text property="cust_rid" size="20" styleClass="writeTypeStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" size="20" styleClass="writeTypeStyle"styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						性&nbsp;&nbsp;&nbsp;&nbsp;别
					</td>
					<td class="valueStyle">
						<html:select property="dict_sex" styleClass="writeTypeStyle" style="width:100px" styleId="dict_sex">
							<html:options collection="sexList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
						<div id="dict_sexTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						身份证号
					</td>
					<td class="valueStyle">
						<html:text property="cust_idcard" size="20" styleClass="writeTypeStyle" styleId="cust_idcard" />
						<div id="cust_idcardTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						地&nbsp;&nbsp;&nbsp;&nbsp;址
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr" size="20" styleClass="writeTypeStyle" styleId="cust_addr"/>
						<input type="button" name="btnadd" class="buttonStyle" value="选择"
						onClick="window.open('../custinfo/phoneinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						邮政编码
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" size="20"
							styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						电&nbsp;&nbsp;&nbsp;&nbsp;话
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_home" size="20" styleClass="writeTypeStyle" styleId="cust_tel_home"/>
						<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						用户类别
					</td>
					<td class="valueStyle">
<%--						<html:select property="cust_way_by" styleClass="writeTypeStyle" style="width:100px" styleId="cust_way_by">--%>
<%--							<html:options collection="leibielist" property="value"--%>
<%--								labelProperty="label" styleClass="writeTypeStyle" />--%>
<%--						</html:select>--%>
						农业大户<html:checkbox property="cust_way_bys" value="农业大户" styleId="cust_way_bys" />
						农业协会<html:checkbox property="cust_way_bys" value="农业协会" styleId="cust_way_bys"/>
						农产品经纪人<html:checkbox property="cust_way_bys" value="农产品经纪人" styleId="cust_way_bys"/>
						农资经销商<html:checkbox property="cust_way_bys" value="农资经销商" styleId="cust_way_bys"/>
						其他<html:checkbox property="cust_way_bys" value="其他" styleId="cust_way_bys"/>
						<div id="cust_way_bysTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="150">
						从事项目及生产规模
					</td>
					<td class="valueStyle">
						<html:text property="dict_cust_scale" size="40" styleClass="writeTypeStyle" styleId="dict_cust_scale"/>
						<div id="dict_cust_scaleTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						工作方式
					</td>
					<td class="valueStyle">
<%--						<html:select property="cust_work_way" styleClass="writeTypeStyle" style="width:100px" styleId="cust_work_way">--%>
<%--							<html:option value="">请选择</html:option>--%>
<%--							<html:options collection="workmethodlist" property="value"--%>
<%--								labelProperty="label" styleClass="writeTypeStyle" />--%>
<%--						</html:select>--%>
						报价<html:checkbox property="cust_work_ways" value="报价" styleId="cust_work_ways"/>
						回访<html:checkbox property="cust_work_ways" value="回访" styleId="cust_work_ways"/>
						<div id="cust_work_waysTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						发展时间
					</td>
					<td class="valueStyle">
						<html:text property="cust_develop_time" styleClass="writeTextStyle" size="20" readonly="true" styleId="cust_develop_time"/>
						<img alt="选择日期" src="../html/img/cal.gif"
							onclick="openCal('phoneinfo','cust_develop_time',false);">
						<div id="cust_develop_timeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						用户备注
					</td>
					<td class="valueStyle">
						<html:textarea property="remark" rows="5" cols="40"
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<logic:equal name="opertype" value="insert">
				<tr>
					<td class="labelStyle">
						栏目列表
					</td>
					<td class="valueStyle">
						<select name="dictProductType1" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" multiple="multiple">
							<jsp:include flush="true" page="../select_product.jsp" />
						</select>&nbsp;
						<span id="dict_product_type2_span"> 
						<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" 
							multiple="multiple">
						</select>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;
						
						<span id="product_name_span">
						<select id="productName" name="productName" multiple="multiple"
							class="selectStyle" style="width:110px;height:110px"
							ondblclick="del(this)">
						</select>
						</span>&nbsp;&nbsp;
<%--						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>--%>
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="col()" class="labelStyle">选定栏目</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="col()" class="labelStyle">选定栏目</a>
						</logic:equal>
					</td>
				</tr>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<tr>
					<td class="labelStyle">
						栏目列表
					</td>
					<td class="valueStyle">
						<select name="dictProductType1" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" multiple="multiple">
							<jsp:include flush="true" page="../select_product.jsp" />
						</select>&nbsp;
						<span id="dict_product_type2_span"> 
						<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px;height:110px" 
							multiple="multiple">
						</select>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;
						
						<span id="product_name_span">
						<select id="productName" name="productName" multiple="multiple"
							class="selectStyle" style="width:110px;height:110px"
							ondblclick="del(this)">
							<%
							Object o = request.getAttribute("productlist");
							List list = new ArrayList();
							if(o != null)
								list = (List)o;
							for(int i=0,size=list.size(); i<size; i++){
								%><option value="<%=list.get(i)%>"><%=list.get(i)%></option><%
							}
							 %>
<%--							<html:options collection="productlist" styleClass="writeTypeStyle" />--%>
						</select>
						</span>&nbsp;&nbsp;
<%--						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>--%>
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="col()" class="labelStyle">选定栏目</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="col()" class="labelStyle">选定栏目</a>
						</logic:equal>
					</td>
				</tr>
				</logic:equal>
				
				<tr>
					<td class="labelStyle" width="150">
						报价栏目
					</td>
					<td class="valueStyle">
						<html:text property="cust_job" size="40" styleClass="writeTypeStyle" 
								styleId="cust_job" readonly="true"/>&nbsp;
						<logic:equal name="opertype" value="insert">
							<a href="#" onclick="blank()" class="labelStyle">清空栏目</a>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<a href="#" onclick="blank()" class="labelStyle">清空栏目</a>
						</logic:equal>
						<div id="cust_jobTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						淘汰与否
					</td>
					<td class="valueStyle">
						<html:select property="is_eliminate" styleClass="writeTypeStyle" style="width:100px">
							<html:option value="">请选择</html:option>
							<html:option value="yes">是</html:option>
							<html:option value="no">否</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						淘汰原因
					</td>
					<td class="valueStyle">
						<html:text property="eliminate_reason" size="20" styleClass="writeTypeStyle" styleId="cust_tel_work"/>
					</td>
				</tr>
				
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center" class="buttonAreaStyle">
						<%--
								if (opertype != null && !"".equals(opertype.trim())) {
								if ("insert".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" 添 加 " onClick="add()"
							class="buttonStyle">
						<%
						} else if ("update".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" 确 定 "
							onClick="update()" class="buttonStyle">
						<%
						} else if ("delete".equals(opertype)) {
						%>
						<input type="button" name="Submit" value=" 删 除 " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						--%>
						<input type="submit" name="button" id="buttonSubmit" value="提 交" class="buttonStyle"/>

<%--						<input type="reset" name="Submit2" value=" 重 置 "--%>
<%--							class="buttonStyle">--%>
<%--						&nbsp;&nbsp;&nbsp;&nbsp;--%>
						<input type="button" name="Submit3" value=" 关 闭 "
							onClick="javascript:window.close()" class="buttonStyle">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html:html>
<script>
	function select1(obj){
		var sid = obj.name;
		var svalue = obj.options[obj.selectedIndex].text;
		if(svalue == "")
			return;
		if(sid == "dictProductType1"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
		}
	}

	var XMLHttpReq = false;
 	//创建XMLHttpRequest对象       
    function createXMLHttpRequest() {
		if(window.XMLHttpRequest) { //Mozilla 浏览器
			XMLHttpReq = new XMLHttpRequest();
		}
		else if (window.ActiveXObject) { // IE浏览器
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
	}
	//发送请求函数
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}
	
	function processResponse1() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("dict_product_type2_span").innerHTML = "<select name='dictProductType2' class='selectStyle'  onChange='select1(this)' style='width:110px;height:110px' multiple='multiple'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("product_name_span").innerHTML = "<select id='productName' name='productName' class='selectStyle' style='width:110px;height:110px' multiple='multiple' ondblclick='del(this)'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

	var cust_way_by = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_way_by") %>";
	var cust_work_way = "<%= (String) ((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_work_way") %>";
	var checkboxs = document.all.item("cust_way_bys");
	var checkboxs2 = document.all.item("cust_work_ways");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(cust_way_by.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
	if (checkboxs2!=null) {
		for (i=0; i<checkboxs2.length; i++) {
			if(cust_work_way.indexOf(checkboxs2[i].value)!=-1){
				checkboxs2[i].checked = true;
			}
		}
	}
	
	function del(obj) {
	    var l = document.getElementById("productName");
	    l.removeChild(l.options[obj.selectedIndex]);
	}
	
	function col() {
		var l = document.getElementById("productName");
		var count = l.options.length;
		var cjob = document.forms[0].cust_job.value;
		if(cjob != "")
			cjob += ",";
		for(var i=0; i<count; i++){
			var flag = 0;
			var cArray = cjob.split(',');
			for(var j=0; j<cArray.length; j++){
				if(cArray[j] == l[i].value)
					flag = 1;
			}
			if(flag == 0){
				cjob += l[i].value;
				cjob += ",";
			}
		}
		if(cjob != "")
			document.forms[0].cust_job.value = cjob.substring(0, cjob.length-1);
		else
			document.forms[0].cust_job.value = cjob;
	}
	
	function blank() {
		document.forms[0].cust_job.value = "";
	}
</script>
