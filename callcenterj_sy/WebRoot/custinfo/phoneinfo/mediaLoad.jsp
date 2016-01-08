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
<%--				$("#cust_name").formValidator({onshow:"请输入单位名称",onfocus:"单位名称不能为空",oncorrect:"单位名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"单位名称两边不能有空符号"},onerror:"单位名称不能为空"});
				$("#cust_addr").formValidator({onshow:"请输入单位地址",onfocus:"单位地址不能为空",oncorrect:"单位地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"单位地址两边不能有空符号"},onerror:"单位地址不能为空"});
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码不能为空"});--%>				
				$("#custLinkmanName").formValidator({onshow:"请输入联系人姓名",onfocus:"联系人姓名不能为空",oncorrect:"联系人姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人姓名两边不能有空符号"},onerror:"联系人姓名不能为空"});
<%--				$("#cust_duty").formValidator({onshow:"请输入联系人职务",onfocus:"联系人职务不能为空",oncorrect:"联系人职务合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人职务两边不能有空符号"},onerror:"联系人职务不能为空"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：02487654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"住宅电话格式不正确"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});
				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：02487654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"办公电话格式不正确"});
				$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
			})
			</logic:equal>
			<logic:equal name="opertype" value="update">
			$(document).ready(function(){
				$.formValidator.initConfig({formid:"phoneinfo",onerror:function(msg){alert(msg)}});	
<%--				$("#cust_name").formValidator({onshow:"请输入单位名称",onfocus:"单位名称不能为空",oncorrect:"单位名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"单位名称两边不能有空符号"},onerror:"单位名称不能为空"});
				$("#cust_addr").formValidator({onshow:"请输入单位地址",onfocus:"单位地址不能为空",oncorrect:"单位地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"单位地址两边不能有空符号"},onerror:"单位地址不能为空"});
				$("#cust_pcode").formValidator({onshow:"请输入邮政编码",onfocus:"邮政编码不能为空",oncorrect:"邮政编码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"邮政编码两边不能有空符号"},onerror:"邮政编码不能为空"});	--%>			
				$("#custLinkmanName").formValidator({onshow:"请输入联系人姓名",onfocus:"联系人姓名不能为空",oncorrect:"联系人姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人姓名两边不能有空符号"},onerror:"联系人姓名不能为空"});
<%--				$("#cust_duty").formValidator({onshow:"请输入联系人职务",onfocus:"联系人职务不能为空",oncorrect:"联系人职务合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人职务两边不能有空符号"},onerror:"联系人职务不能为空"});
				$("#cust_tel_home").formValidator({empty:true,onshow:"请输入住宅电话",onfocus:"正确格式：02487654321",oncorrect:"住宅电话合法",onempty:"没有填写住宅电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"住宅电话格式不正确"});
				$("#cust_tel_mob").formValidator({empty:true,onshow:"请输入手机号码，可以为空",onfocus:"如果输入，必须输入正确",oncorrect:"手机号码",onempty:"没有填写手机号码"}).inputValidator({min:11,max:11,onerror:"手机号码必须是11位的"}).regexValidator({regexp:"mobile",datatype:"enum",onerror:"手机号码格式不正确"});
				$("#cust_tel_work").formValidator({empty:true,onshow:"请输入办公电话",onfocus:"正确格式：02487654321",oncorrect:"办公电话合法",onempty:"没有填写办公电话"}).regexValidator({regexp:"^(0[0-9]{2,3})?[2-9][0-9]{6,7}$",onerror:"办公电话格式不正确"});
				$("#cust_email").formValidator({empty:true,onshow:"请输入邮箱",onfocus:"至少6个字,最多100个字",oncorrect:"输入正确",onempty:"没有填写邮箱"}).inputValidator({min:6,max:100,onerror:"输入的邮箱长度非法"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"邮箱格式不正确"});--%>
			
			})
			</logic:equal>
				
<%--		function checkForm(){--%>
<%--			var f = document.getElementById("cust_name").value;--%>
<%--			if(f.length == 0){--%>
<%--				alert("单位名称不能为空，请正确填写！");--%>
<%--				return false;--%>
<%--			}else{--%>
<%--				return true;--%>
<%--			}				--%>
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
<%--   	function add(){	--%>
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
	<%	String opertype = (String) request.getAttribute("opertype");	%>
	<body class="loadBody" onload="init();">
		<html:form action="/custinfo/phoneinfo.do" method="post" styleId="phoneinfo" onsubmit="return formAction();">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
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
					<td class="valueStyle" colspan="2">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle"
							onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						单位名称
					</td>
					<td  class="valueStyle">
						<html:text property="cust_name" size="15" styleClass="writeTypeStyle" styleId="cust_name"/>
						<div id="cust_nameTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						单位地址
					</td>
					<td class="valueStyle">
						<html:text property="cust_addr"  styleClass="writeTypeStyle" styleId="cust_addr"/>
						<div id="cust_addrTip" style="width: 10px;display:inline;"></div>
<%--						<html:select property="cust_addr" styleClass="writeTypeStyle">--%>
<%--						</html:select>--%>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						邮政编码
					</td>
					<td class="valueStyle">
						<html:text property="cust_pcode" size="6" styleClass="writeTypeStyle" styleId="cust_pcode"/>
						<div id="cust_pcodeTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						单位网页
					</td>
					<td class="valueStyle">
						<html:text property="enterprise_net" 
							styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						传&nbsp;&nbsp;&nbsp;&nbsp;真
					</td>
					<td class="valueStyle">
						<html:text property="cust_fax" size="20"
							styleClass="writeTypeStyle" />
					</td>
					<td class="labelStyle">
						联系人姓名
					</td>
					<td class="valueStyle">
						<html:text property="custLinkmanName" size="20" styleClass="writeTypeStyle" styleId="custLinkmanName"/>
						<div id="custLinkmanNameTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						联系人职务
					</td>
					<td class="valueStyle">
						<html:text property="cust_duty" size="20" styleClass="input" styleId="cust_duty"/>
						<div id="cust_dutyTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						移动电话
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" size="11" styleClass="writeTypeStyle" styleId="cust_tel_mob"/>
						<div id="cust_tel_mobTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						办公电话
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_work" size="13" styleClass="writeTypeStyle" styleId="cust_tel_work"/>
						<div id="cust_tel_workTip" style="width: 10px;display:inline;"></div>
					</td>
					<td class="labelStyle">
						电子信箱
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" styleClass="writeTypeStyle" styleId="cust_email"/>
						<div id="cust_emailTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						单位标识
					</td>
					<%
		    			String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("phoneinfo")).get("cust_id");
		    			//System.out.println("id == "+id);
		    			String p = "phoneinfo/show.jsp?t=oper_custinfo.cust_id&id="+id;
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
						<%--
							if (opertype != null && !"".equals(opertype.trim())){
								if ("insert".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" 添 加 " onClick="add()"
							class="buttonStyle">
						<%
								}else if ("update".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" 确 定 "
							onClick="update()" class="buttonStyle">
						<%
								}else if ("delete".equals(opertype)){
						%>
						<input type="button" name="Submit" value=" 删 除 " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						--%>
						<input type="submit" name="button" id="buttonSubmit" value="提交" class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" name="Submit2" value=" 重 置 "
							class="buttonStyle">
						&nbsp;&nbsp;&nbsp;&nbsp;	
						<input type="button" name="Submit3" value=" 关 闭 "
							onClick="javascript:window.close()" class="buttonStyle">
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
