<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>焦点追踪大屏幕</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<style type="text/css">
<!--
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	
	<link REL=stylesheet href="../markanainfo/css/divtext.css"
		type="text/css" />
	<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>
		<!-- jquery验证 -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- end jquery验证 -->
	<script type="text/javascript">
	
		//初始化
		function init(){
			<logic:equal name="opertype" value="insert">
    				document.getElementById('spanHead').innerHTML="添加信息";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=insert";
					document.getElementById('btnOper').value="添加";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=update";
					document.getElementById('btnOper').value="修改";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=delete";
					document.getElementById('btnOper').value="删除";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		//执行验证
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#ftPeriod").formValidator({onshow:"请输入期第",onfocus:"期第不能为空",oncorrect:"期第合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"期第两边不能有空符号"},onerror:"期第不能为空"});
			$("#ftTitle").formValidator({onshow:"请输入标题",onfocus:"标题不能为空",oncorrect:"标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"标题两边不能有空符号"},onerror:"标题不能为空"});
			$("#ftSummary").formValidator({onshow:"请输入摘要信息",onfocus:"摘要信息不能为空",oncorrect:"摘要信息合法"}).inputValidator({min:1,empty:{rightempty:false,emptyerror:"摘要信息后边不能有空符号"},onerror:"摘要信息不能为空"});		
			
		})
   		</logic:equal>
   		<logic:equal name="opertype" value="update">
   		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#ftPeriod").formValidator({onshow:"请输入期第",onfocus:"期第不能为空",oncorrect:"期第合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"期第两边不能有空符号"},onerror:"期第不能为空"});
			$("#ftTitle").formValidator({onshow:"请输入标题",onfocus:"标题不能为空",oncorrect:"标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"标题两边不能有空符号"},onerror:"标题不能为空"});
			$("#ftSummary").formValidator({onshow:"请输入摘要信息",onfocus:"摘要信息不能为空",oncorrect:"摘要信息合法"}).inputValidator({min:1,empty:{rightempty:false,emptyerror:"摘要信息后边不能有空符号"},onerror:"摘要信息不能为空"});

		})
   		</logic:equal>
						
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">
	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();	
	</script>
	</logic:notEmpty>

	<html:form action="/focusTracking.do" method="post" styleId="formValidate">
	<html:hidden property="ftId" />
	
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				当前位置&ndash;&gt;
				<span id="spanHead">详细</span>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		
			<tr>
				<td class="labelStyle">
					期&nbsp;&nbsp;&nbsp;&nbsp;第
				</td>
				<td class="valueStyle">
					<html:text property="ftPeriod" styleClass="writeTextStyle" styleId="ftPeriod"/>
					<span id="ftPeriodTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>		
			<tr>
				<td class="labelStyle">
					标&nbsp;&nbsp;&nbsp;&nbsp;题
				</td>
				<td class="valueStyle" >
					<html:text property="ftTitle" styleClass="writeTextStyle" size="40" styleId="ftTitle" />
					<span id="ftTitleTip" style="width: 0px;display:inline;"></span>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle" >
					<html:textarea property="ftSummary" styleClass="writeTextStyle"  styleId="ftSummary" rows="7" cols="70"></html:textarea>
<%--					<html:text property="ftSummary" styleClass="writeTextStyle"  styleId="ftSummary" />--%>
					<span id="ftSummaryTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			
			<logic:equal name="opertype" value="detail">
	    	<tr>
				<td class="labelStyle">
					最近一次操作人
				</td>
				<td class="valueStyle">
					<html:text property="ftCreateUser" styleClass="writeTextStyle" />
					
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					最近一次操作时间
				</td>
				<td class="valueStyle">
					<html:text property="ftCreateTime" styleClass="writeTextStyle" />
				</td>
			</tr>		
	    	</logic:equal>			
						
			<tr>
				<td colspan="2" align="center" class="buttonAreaStyle">
				 	<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="详细"  class="buttonStyle"/>
				</logic:present>
					<input type="button" name="" value="关闭"  
						onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
		</table>	
	</html:form>
</body>
</html:html>
