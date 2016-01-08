<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="../style.jsp"%>
<html:html locale="true">
<head>
<html:base />

<title><logic:equal name="opertype" value="update">
    修改调查卡问题
    </logic:equal> <logic:equal name="opertype" value="delete">删除调查卡问题</logic:equal></title>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../js/form.js" type=text/javascript>
</SCRIPT>
<script>
function checkForm(inquiryCardForm){
            if (!checkNotNull(inquiryCardForm.question,"调查问题")) return false;  
            if (!checkNotNull(inquiryCardForm.alternatives,"调查答案")) return false;            
            return true;
}
function doUpdate(){
	var f =document.forms[0];
    if(checkForm(f)){
		document.forms[0].action="../inquiryCard.do?method=toSave&type=update";
		document.forms[0].submit();	
	}
}
function doDelete(){
	var f =document.forms[0];
   	if(checkForm(f)){
		document.forms[0].action="../inquiryCard.do?method=toSave&type=delete";
		document.forms[0].submit();	
	}
}
</script>
</head>
<logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); 
	opener.location.reload(true);
	window.close();
	
	</script>
</logic:notEmpty>
<body class="loadBody">
<html:form action="/inquiryCard" method="post">
	<html:hidden property="id" />
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		<tr>
			<td class="navigateStyle"><logic:equal name="opertype"
				value="update">
		    		修改问卷选项卡
		    	</logic:equal> <logic:equal name="opertype" value="delete">
		    		删除问卷选项卡
		    	</logic:equal>
		    </td>
		</tr>
	</table>

	<table id="questions" width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
					<tr>
						<td class="listTitleStyle" width="70">
							调查类型
						</td>
						<td class="listTitleStyle">
							调查问题
						</td>
						<td class="listTitleStyle">
							调查答案备选项（不同备选项用;号隔开，双击自动添写）
						</td>
					</tr>
					<tr class="evenStyle">
						<td>
							<html:select property="questionType" styleClass="input">
								<html:options collection="questionTypes" property="value" labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text property="question" styleClass="input" size="27"/>
						</td>
						<td>
							<html:text property="alternatives" styleClass="input" size="45" ondblclick="autoFill(this)"/>
						</td>
					</tr>
		<tr class="buttonAreaStyle">
			<td colspan="6">
			<logic:equal name="opertype" value="update">
				<input type="button" name="btnUpdate"   value="确定" onclick="doUpdate()" >
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				<input type="button" name="btnDelete"   value="删除" onclick="doDelete()" >
			</logic:equal>
				<input type="button" value="关闭"   onClick="javascript:window.close();" >
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>
<script>
	function autoFill(obj){
		var index = obj.parentNode.parentNode.rowIndex;
		window.open('inquiry/autoFill.jsp?'+index,'','width=400,height=200,status=no,resizable=yes,scrollbars=yes,top=300,left=300');
	}
</script>