<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<logic:notEmpty name="operSign">
	<script>
		alert("操作成功");
		opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		window.close();
	</script>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>客户操作</title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<%--		引入表单验证的JS文件	--%>
		<SCRIPT language=javascript src="../js/form.js"></SCRIPT>
		<%--		引入日历组件的JS文件	--%>
		<SCRIPT language=javascript src="../js/calendar3.js"
			type=text/javascript></SCRIPT>
		<script type="text/javascript">
			var url = "fixedContact.do?method=toFixedContactOper&type=";
			function checkForm(myForm)
    	{
    		//if (!checkNotNull(myForm.cust_rid,"座席号"))return false;
    		//if (!checkNotNull(myForm.cust_name,"姓名"))return false;
    		//if (!checkNotNull(myForm.dict_sex,"性别"))return false;
    		if (!checkNotNull(myForm.cust_develop_time,"发展时间"))return false;
    		//if (!checkNotNull(myForm.cust_identity_card,"身份证号"))return false;
    		//else if (checkNumberRange(myForm.cust_identity_card,"身份证号"))return false;//身份证号必须全部为数字
    		//else if (checkNumberRange(myForm.cust_identity_card,"身份证号",18))return false;//身份证号必须为18位数字
    			
    		//if (!checkNotNull(myForm.cust_addr,"地址"))return false;
    		
    		//验证邮编
    		//if (!checkNotNull(myForm.cust_pcode,"邮编"))return false;
    		//else if (checkInteger(myForm.cust_pcode,"邮编"))return false;//邮编必须全部为数字
    		
    		//验证E-mail
    		//if (!checkNotNull(myForm.cust_email,"E-mail"))return false;
    		//else if(checkEmail(myForm.cust_email,"E-mail"))return false;
    		
    		//if (!checkNotNull(myForm.cust_tel_home,"宅电"))return false;
    		//if (!checkNotNull(myForm.cust_tel_work,"办公电话"))return false;
    		//if (!checkNotNull(myForm.cust_voc,"客户行业"))return false;
    		//验证备注
    		//if (!checkNotNull(myForm.remark,"备注"))return false;
    		//else if(checkLength(myForm.remark,"备注",400))return false; 
    		return true;
    	}
			//添加
   		function add()
   		{  
   			var f =document.forms[0];
   			if(checkForm(f))
   			{
   				f.action = url + "insert";
					f.submit();
				}	
   		}
	   	//修改
	   	function update()
	   	{
	   		var f =document.forms[0];
   			if(checkForm(f))
   			{
   				f.action = url + "update";
					f.submit();
				}	
	   	}
	   	//删除
	   	function del()
	   	{
				if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'"))
				{
			   	document.forms[0].action = url + "delete";
			   	document.forms[0].submit();
	   		}
	  	}
	  </script>
	</head>
	<body class="loadBody">
		<logic:equal name="opertype" value="insert">
			<%
			session.setAttribute("viewPicFlag", "insert");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="update">
			<%
			session.setAttribute("viewPicFlag", "update");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="detail">
			<%
			session.setAttribute("viewPicFlag", "detail");
			%>
		</logic:equal>
		<logic:equal name="opertype" value="delete">
			<%
			session.setAttribute("viewPicFlag", "delete");
			%>
		</logic:equal>
		<html:form action="/schema/fixedContact.do" method="post">
			<html:hidden property="cust_id" />
			<html:hidden property="cust_pic_path" />
			<html:hidden property="cust_pic_name" />
			<html:hidden property="cust_type" value="SYS_TREE_0000000683" />
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle" colspan="3">
						当前位置&ndash;&gt;金农固定联络员操作&ndash;&gt;添加固定联络员&nbsp;&nbsp;
						<font color="red">※注意：带*号标志为必填项</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座&nbsp;席&nbsp;号
					</td>
					<td class="valueStyle">
						<html:text property="cust_rid" size="13"
							styleClass="writeTextStyle" disabled="true"/>
					</td>
					<%-- 上传和显示个人上传的照片--%>
					<logic:equal name="opertype" value="insert">
						<td rowspan="6" class="valueStyle" width="40%" height="40%"
							align="right">
							<iframe src="fixedContact/fixedContactUpload.jsp" height="280"
								width="380" scrolling="no" frameborder="0"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<td rowspan="6" class="valueStyle" width="40%" height="40%"
							align="right">
							<iframe src="fixedContact/fixedContactUpload.jsp" height="240"
								width="380" scrolling="no" frameborder="0"></iframe>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="detail">
						<td class="valueStyle" height="80" align="center" rowspan="3">
							<logic:equal name="viewPic" value="no">
								<b>您未上传图片</b>
							</logic:equal>
							<logic:equal name="viewPic" value="yes">
								<a
									href="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>"
									target="_blank"><img
										src="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>
							"
										width="120" height="80" border="0" align="middle" /> </a>
							</logic:equal>
						</td>
					</logic:equal>
					<logic:equal name="opertype" value="delete">
						<td class="valueStyle" height="80" align="center" rowspan="3">
							<logic:equal name="viewPic" value="no">
								您未上传图片
							</logic:equal>
							<logic:equal name="viewPic" value="yes">
								<a
									href="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>"
									target="_blank"><img
										src="fixedContact/user_images/<bean:write name='fixedContact' property='cust_pic_name'/>
							"
										width="120" height="80" border="0" align="middle" /> </a>
							</logic:equal>
						</td>
					</logic:equal>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名
					</td>
					<td width="139" class="valueStyle">
						<html:text property="cust_name" size="8"
							styleClass="writeTextStyle" />
						<font color="red">*真实姓名</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						性&nbsp;&nbsp;&nbsp;&nbsp;别
					</td>
					<td class="valueStyle">
						<html:select property="dict_sex" styleClass="writeTextStyle">
							<html:options collection="sexList" property="value"
								labelProperty="label" />
						</html:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						发展时间
					</td>
					<td class="valueStyle" colspan="3">
						<html:text property="cust_develop_time"
							styleClass="writeTextStyle" />
						<logic:equal name="opertype" value="insert">
							<img alt="选择日期" src="../html/img/cal.gif"
								onclick="openCal('fixedContact','cust_develop_time',false);">
							<font color="red">*单击旁边的组件选择日期</font>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<img alt="选择日期" src="../html/img/cal.gif"
								onclick="openCal('fixedContact','cust_develop_time',false);">
							<font color="red">*单击旁边的组件选择日期</font>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="90">
						身份证号
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_identity_card" size="25"
							styleClass="writeTextStyle" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						地&nbsp;&nbsp;&nbsp;&nbsp;址
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_addr" size="50"
							styleClass="writeTextStyle" />
						<logic:equal name="opertype" value="insert">
							<input type="button" value="选择地址" class="buttonStyle"
								onclick="window.open('fixedContact/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
							<font color="red">*</font>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="button" value="选择地址" class="buttonStyle"
								onclick="window.open('fixedContact/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
							<font color="red">*</font>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						邮&nbsp;&nbsp;&nbsp;&nbsp;编
					</td>
					<td class="valueStyle" colspan="2">
						<html:text property="cust_pcode" size="20"
							styleClass="writeTextStyle" />
						<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*必须全部为数字</font>
					</td>

				</tr>
				<tr>
					<td class="labelStyle">
						&nbsp;E-mail&nbsp;
					</td>
					<td class="valueStyle">
						<html:text property="cust_email" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*如：aa@163.com</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						宅&nbsp;&nbsp;&nbsp;&nbsp;电
					</td>
					<td class="valueStyle" width="180">
						<html:text property="cust_tel_home" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*如：02423448833(无杠线)，要求有区号，无空格，无杠线</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						办公电话
					</td>
					<td class="valueStyle" width="180">
						<html:text property="cust_tel_work" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*如：02423448833(无杠线)，要求有区号，无空格，无杠线</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						手&nbsp;&nbsp;&nbsp;&nbsp;机
					</td>
					<td class="valueStyle">
						<html:text property="cust_tel_mob" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="labelStyle" width="180">
						客户行业
					</td>
					<td class="valueStyle">
						<html:text property="cust_voc" size="20"
							styleClass="writeTextStyle" />
					</td>
					<td class="valueStyle">
						<font color="red">*如养猪(年养殖300头)，玉米种植30亩，化肥经销商等</font>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						备&nbsp;&nbsp;&nbsp;&nbsp;注
					</td>
					<td colspan="2" class="valueStyle">
						<html:textarea property="remark" cols="50" rows="2"
							styleClass="writeTextStyle" />
						<font color="red">*限400字以内</font>
					</td>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="3" align="center">
						<logic:equal name="opertype" value="insert">
							<input type="button" name="Submit" value=" 添 加 " onClick="add()"
								 class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "
								class="buttonStyle"/>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="button" name="Submit" value=" 修 改 "
								onClick="update()"  class="buttonStyle">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="reset" name="Submit2" value=" 重 置 "
								 class="buttonStyle"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
							<input type="button" name="Submit" value=" 删 除 " onClick="del()"
								 class="buttonStyle"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
	 </logic:equal>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="Submit3" value=" 关 闭 "
							onClick="window.close()"  class="buttonStyle"/>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
