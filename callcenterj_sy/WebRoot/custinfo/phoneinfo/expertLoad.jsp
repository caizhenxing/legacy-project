<%@ page contentType="text/html; charset=gbk" language="java" errorPage=""%>

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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>电话薄管理</title>
		<script type='text/javascript' src='../../js/msg.js'></script>
		<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
		
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
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=insert";
					document.getElementById('spanHead').innerHTML="添加用户信息";
					document.getElementById('buttonSubmit').value=" 添 加 ";
				</logic:equal>
				<logic:equal name="opertype" value="update">
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=update";
					document.getElementById('spanHead').innerHTML="修改用户信息";
					document.getElementById('buttonSubmit').value=" 修 改 ";
				</logic:equal>
				<logic:equal name="opertype" value="delete">
					document.forms[0].action = "phoneinfo.do?method=toPhoneOper&type=delete";
					document.getElementById('spanHead').innerHTML="删除用户信息";
					document.getElementById('buttonSubmit').value=" 删 除 ";
					v_flag="del"
				</logic:equal>		
			}
			//执行验证
				
			<logic:equal name="opertype" value="insert">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
<%--				$("#cust_age").formValidator({empty:true,onshow:"请输入年龄，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"年龄",onempty:"没有填写年龄"}).inputValidator({min:1,max:99,type:"value",onerrormin:"年龄必须大于等于1",onerrormax:"年龄必须小于等于99",onerror:"年龄必须在1-99之间"});				
				$("#expert_type").formValidator({onshow:"请选择专家类别",onfocus:"专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择专家类别"});
				$("#dict_cust_voc").formValidator({onshow:"请输入从事行业",onfocus:"从事行业不能为空",oncorrect:"从事行业合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"从事行业两边不能有空符号"},onerror:"从事行业不能为空"});--%>
<%--				$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"住宅电话不能为空",oncorrect:"住宅电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"住宅电话两边不能有空符号"},onerror:"住宅电话不能为空"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：02487654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"住宅电话格式不正确"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});--%>
<%--				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"办公电话不能为空",oncorrect:"办公电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"办公电话两边不能有空符号"},onerror:"办公电话不能为空"});	
				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：02487654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"办公电话格式不正确"});
				$("#cust_addr").formValidator({onshow:"请输入通信地址",onfocus:"通信地址不能为空",oncorrect:"通信地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"通信地址两边不能有空符号"},onerror:"通信地址不能为空"});
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码不能为空"});--%>
<%--				$("#cust_email").formValidator({onshow:"请输入email",onfocus:"email不能为空",oncorrect:"email合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"email两边不能有空符号"},onerror:"email不能为空"});	--%>
<%--				$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
				$("#cust_name").formValidator({onshow:"请输入姓名",onfocus:"姓名不能为空",oncorrect:"姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"姓名两边不能有空符号"},onerror:"姓名不能为空"});
<%--				$("#cust_age").formValidator({empty:true,onshow:"请输入年龄，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"年龄",onempty:"没有填写年龄"}).inputValidator({min:1,max:99,type:"value",onerrormin:"年龄必须大于等于1",onerrormax:"年龄必须小于等于99",onerror:"年龄必须在1-99之间"});	
				$("#expert_type").formValidator({onshow:"请选择专家类别",onfocus:"专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择专家类别"});
				$("#dict_cust_voc").formValidator({onshow:"请输入从事行业",onfocus:"从事行业不能为空",oncorrect:"从事行业合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"从事行业两边不能有空符号"},onerror:"从事行业不能为空"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：02487654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"住宅电话格式不正确"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});
				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：02487654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"办公电话格式不正确"});
				$("#cust_addr").formValidator({onshow:"请输入通信地址",onfocus:"通信地址不能为空",oncorrect:"通信地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"通信地址两边不能有空符号"},onerror:"通信地址不能为空"});
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码不能为空"});
				$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
			
			})
			</logic:equal>
		
		
<%--		function checkForm(){--%>
<%--			var cust_name=document.forms[0].cust_name.value;--%>
<%--			if(cust_name.length==0){--%>
<%--				alert("用户姓名不能为空");--%>
<%--				document.forms[0].cust_name.focus();--%>
<%--				return false;--%>
<%--			}--%>
<%--			var pattern = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;--%>
<%--			var home_tel=document.forms[0].cust_tel_home.value;--%>
<%--			var mob_tel=document.forms[0].cust_tel_mob.value;--%>
<%--			var work_tel=document.forms[0].cust_tel_work.value;--%>
<%--			var email=document.forms[0].cust_email.value;--%>
<%--		 	var pattern1 = /^([0-9]|(-[0-9]))[0-9]*((\.[0-9]+)|([0-9]*))$/;--%>
<%--			var cust_age=document.forms[0].cust_age.value;--%>
<%--			--%>
<%--		 	if (cust_age!=""&&!pattern1.test(cust_age)) {--%>
<%--		 		alert("年龄必须为合法数字");--%>
<%--		 		document.forms[0].cust_age.focus();--%>
<%--		 		document.forms[0].cust_age.select();--%>
<%--		 		return false;--%>
<%--		 	}		--%>
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
<%--		    var pattern2 = /^.+@.+\..+$/;--%>
<%--			if (email!=""&&!pattern2.test(email)) {--%>
<%--				alert("请正确填写email");--%>
<%--				document.forms[0].cust_email.focus();--%>
<%--				document.forms[0].cust_email.select();--%>
<%--				return false;--%>
<%--			}--%>
<%--            return true;--%>
<%--   		}--%>
<%--		var url = "phoneinfo.do?method=toPhoneOper&type=";--%>
		var selectUrl = "custinfo/phoneinfo.do?method=toPhoneLoad&custType=";
		function selectType()
		{
			var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			selectUrl = "phoneinfo.do?method=toPhoneLoad&type="+operType+"&custType=";
			document.forms[0].action = selectUrl + type;
			document.forms[0].submit();
		}
		
<%--	//添加--%>
<%--   	function add(){--%>
<%--    	if(checkForm()){--%>
<%--	   		document.forms[0].action = url + "insert";--%>
<%--			document.forms[0].submit();--%>
<%--		}--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--  		var f =document.forms[0];--%>
<%--    	if(checkForm(f)){--%>
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
	<%	String opertype = (String) request.getAttribute("opertype"); %>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		当前位置&ndash;&gt;添加用户信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
		    		当前位置&ndash;&gt;<span id="spanHead">查看用户信息</span>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		当前位置&ndash;&gt;修改用户信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		当前位置&ndash;&gt;删除用户信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>			
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td colspan="2" class="labelStyle">
						选择用户类型
					</td>
					<td colspan="2" class="valueStyle">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle" onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle"/>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" size="8" styleClass="writeTypeStyle" styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						年&nbsp;&nbsp;&nbsp;&nbsp;龄
					</td>
					<td class="valueStyle">
						<html:text property="cust_age"  styleClass="writeTypeStyle" size="3" styleId="cust_age"/>
<%--						<div id="cust_ageTip" style="width: 10px;display:inline;"></div>--%>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						专家类别
					</td>
					<td class="valueStyle">
						<html:select property="expert_type" styleClass="writeTypeStyle" styleId="expert_type">
							<html:option value="">请选择</html:option>
							<html:options collection="expertList" property="value"
								labelProperty="label" styleClass="writeTypeStyle"/>
						</html:select>
						<div id="expert_typeTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						
					</td>
					<td class="valueStyle">
						
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						文化程度
					</td>
					<td class="valueStyle">
						<html:text property="cust_degree" styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						从事行业
					</td>
					<td class="valueStyle">
						<html:text property="dict_cust_voc" styleClass="writeTypeStyle" styleId="dict_cust_voc"/>
						<div id="dict_cust_vocTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						所在单位
					</td>
					<td class="valueStyle">
						<html:text property="cust_unit"  styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						职务职称
					</td>
					<td class="valueStyle">
						<html:text property="cust_duty" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						住宅电话
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_home" styleClass="writeTypeStyle" size="13" styleId="cust_tel_home"/>
						<div id="cust_tel_homeTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						手&nbsp;&nbsp;&nbsp;&nbsp;机
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" styleClass="writeTypeStyle" size="11" styleId="cust_tel_mob"/>
						<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						办公电话
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_work" styleClass="writeTypeStyle" size="13" styleId="cust_tel_work"/>
						<div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						传&nbsp;&nbsp;&nbsp;&nbsp;真
					</td>
					<td class="valueStyle">
						<html:text property="cust_fax" styleClass="writeTypeStyle"  styleId=""/>
<%--						<div id="Tip" style="width: 10px;display:inline;"></div>--%>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						通信地址
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr"  styleClass="writeTypeStyle" styleId="cust_addr"/>
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						邮政编码
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						电子邮箱
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" styleClass="writeTypeStyle" styleId="cust_email"/>
						<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						个人网页
					</td>
					<td class="valueStyle">
						<html:text property="cust_homepage" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						照&nbsp;&nbsp;&nbsp;&nbsp;片
					</td>
					<%
		    			String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_id");
		    			//System.out.println("id == "+id);
		    			String p = "phoneinfo/show2.jsp?t=oper_custinfo.cust_id&id="+id;
		    			//System.out.println("p == "+p);
		    		 %>
		    		<logic:equal name="opertype" value="detail">
		    			 <td class="valueStyle" colspan="3">
		    			 	<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
		    			 </td>
		    		</logic:equal>
		    		<logic:equal name="opertype" value="delete">
		    			 <td class="valueStyle" colspan="3">
		    			 	<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
		    			 </td>
		    		</logic:equal>
					<logic:equal name="opertype" value="insert">
						<td class="valueStyle" colspan="3">
							<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<td class="valueStyle" colspan="3">
							<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
						</td>
					</logic:equal>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center">
						
<%--						<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="Submit" value=" 添 加 "  onclick="add()" class="buttonStyle">--%>
<%--						</logic:equal>--%>
<%--						--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="Submit" value=" 确 定 " onClick="update()" class="buttonStyle">--%>
<%--						</logic:equal>--%>
<%--						--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="Submit" value=" 删 除 " onClick="del()"class="buttonStyle">--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value=" 重 置 " class="buttonStyle">
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input type="button" name="Submit3" value=" 关 闭 " onClick="javascript:window.close()" class="buttonStyle">
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
