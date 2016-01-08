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
		<SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
   		 </SCRIPT>
		<script type="text/javascript">
		function checkForm(phoneinfo){
            if (!checkNotNull(phoneinfo.cust_name,"座席员姓名")) return false;
            if (!checkNotNull(phoneinfo.cust_duty,"座席员职位")) return false;
            if (!checkNotNull(phoneinfo.role_id,"座席员角色")) return false;
            if (!checkNotNull(phoneinfo.cust_nature,"座席性质")) return false;
            if (!checkNotNull(phoneinfo.group_id,"座席分组")) return false;
             	
	

              return true;
    	}
		var selectUrl = "custinfo/phoneinfo.do?method=toPhoneLoad&custType=";
		function selectType()
		{
			var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			
			//请求的参数说明：type:操作类型；custType:客户类型
			selectUrl = "phoneinfo.do?method=toPhoneLoad&type="+operType+"&custType=";
			document.forms[0].action = selectUrl + type;
			document.forms[0].submit();
		}
		//添加
   	function add()
   	{	
   		var f =document.forms[0];
    	
   		var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			//请求的参数说明：type:操作类型；custType:客户类型
   		selectUrl = "phoneinfo.do?method=toPhoneOper&type="+operType+"&custType=";
   		if(checkForm(f)){
	   		document.forms[0].action = selectUrl + type;
	   		//alert(selectUrl + type);
				document.forms[0].submit();
		}
   	}
   	//修改
   	function update()
   	{
   		var operType = document.forms[0].opertype.value;
			var type = document.forms[0].dict_cust_type.value;
			//请求的参数说明：type:操作类型；custType:客户类型
   		selectUrl = "phoneinfo.do?method=toPhoneOper&type="+operType+"&custType=";
   		var f =document.forms[0];
   		if(checkForm(f)){
	   		document.forms[0].action = selectUrl + type;
	   		//alert(selectUrl + type);
				document.forms[0].submit();
		}
   	}
   	//删除
   	function del()
   	{
			if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'"))
			{
   			var operType = document.forms[0].opertype.value;
				var type = document.forms[0].dict_cust_type.value;
				//请求的参数说明：type:操作类型；custType:客户类型
	   		selectUrl = "phoneinfo.do?method=toPhoneOper&type="+operType+"&custType=";
	   		document.forms[0].action = selectUrl + type;
	   		//alert(selectUrl + type);
				document.forms[0].submit();
   		}
   	}
	</script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
	</head>
	<%
	String opertype = (String) request.getAttribute("opertype");
	%>
	<body class="loadBody">
		<html:form action="/custinfo/phoneinfo.do" method="post">
			<html:hidden property="cust_id" />
			<html:hidden property="opertype" value="<%=opertype%>" />
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="contentTable">
				<tr>
					<td colspan="3" class="labelStyle">
						选择用户类型
					</td>
					<td class="valueStyle">
						<html:select property="dict_cust_type" styleClass="writeTypeStyle"
							onchange="selectType()">
							<html:options collection="telNoteTypeList" property="value"
								labelProperty="label" styleClass="writeTypeStyle" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席工号
					</td>
					<td class="valueStyle">
						<html:text property="cust_rid" size="13"
							styleClass="writeTextStyle" disabled="true" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席员姓名
					</td>
					<td class="valueStyle">
						<html:text property="cust_name" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席员职位
					</td>
					<td class="valueStyle">
						<html:text property="cust_duty" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席员角色
					</td>
					<td class="valueStyle">
						<html:text property="role_id" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席性质
					</td>
					<td class="valueStyle">
						<html:text property="cust_nature" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<tr>
					<td class="labelStyle">
						座席分组
					</td>
					<td colspan="3" class="valueStyle">
						<html:text property="group_id" styleClass="writeTypeStyle" />
					</td>
				</tr>
				<!-- 显示照片处 -->
				<tr>
					<td>
						显示照片处
					</td>
				</tr>
				<tr class="buttonAreaStyle">
					<td colspan="4" align="center">
						<%
							if (opertype != null && !"".equals(opertype.trim()))
							{
								if ("insert".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" 添 加 " onClick="add()"
							class="buttonStyle">
						<%
								}
								else if ("update".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" 修 改 "
							onClick="update()" class="buttonStyle">
						<%
								}
								else if ("delete".equals(opertype))
								{
						%>
						<input type="button" name="Submit" value=" 删 除 " onClick="del()"
							class="buttonStyle">
						<%
							}
							}
						%>
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
