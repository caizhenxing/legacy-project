<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@ include file="../../style.jsp"%>

<logic:equal value="insertsuccess" name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		window.parent.bottomm.document.location=window.parent.bottomm.document.location;
	</script>
</logic:equal>
<logic:equal value="updatesuccess" name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:equal>
<logic:equal value="deletesuccess" name="operSign">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:equal>
<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>金农热线内容维护管理</title>
		<script language="javascript" type="text/JavaScript" src="../../js/calendar3.js"></script>
		<script language="javascript" src="../../js/common.js"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
				
		<link REL=stylesheet href="../../markanainfo/css/divtext.css"
			type="text/css" />
		<script language="JavaScript" src="../../markanainfo/js/divtext.js"></script>
				
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
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=insert";
				document.getElementById('spanHead').innerHTML="添加家庭医生信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=update";
				document.getElementById('spanHead').innerHTML="修改家庭医生信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=delete";
				document.getElementById('spanHead').innerHTML="删除家庭医生信息";
				document.getElementById('buttonSubmit').value="删除";
				v_flag="del"
			</logic:equal>
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		//$(document).ready(function(){
			//$.formValidator.initConfig({formid:"doctorFormBean",onerror:function(msg){alert(msg)}});	
			//$("#healthcare").formValidator({onshow:"请输入医疗保健常识",onfocus:"医疗保健常识不能为空",oncorrect:"医疗保健常识合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"医疗保健常识两边不能有空符号"},onerror:"医疗保健常识不能为空"});
			//$("#healthcare").formValidator({onshow:"请输入普通疾病识别防治方法与措施",onfocus:"医疗普通疾病识别防治方法与措施不能为空",oncorrect:"普通疾病识别防治方法与措施合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"普通疾病识别防治方法与措施两边不能有空符号"},onerror:"普通疾病识别防治方法与措施不能为空"});		
		
		//})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	//$.formValidator.initConfig({formid:"doctorFormBean",onerror:function(msg){alert(msg)}});	
			//$("#healthcare").formValidator({onshow:"请输入医疗保健常识",onfocus:"医疗保健常识不能为空",oncorrect:"医疗保健常识合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"医疗保健常识两边不能有空符号"},onerror:"医疗保健常识不能为空"});
			//$("#healthcare").formValidator({onshow:"请输入普通疾病识别防治方法与措施",onfocus:"医疗普通疾病识别防治方法与措施不能为空",oncorrect:"普通疾病识别防治方法与措施合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"普通疾病识别防治方法与措施两边不能有空符号"},onerror:"普通疾病识别防治方法与措施不能为空"});		
		
		//})
		</logic:equal>
	
<%--	var url = "event.do?method=toEventOper&type=";--%>
<%--	//添加--%>
<%--   	function add(){  --%>
<%--   		document.forms[0].action = url + "insert";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//修改--%>
<%--   	function update(){--%>
<%--   		document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//删除--%>
<%--   	function del(){--%>
<%--		if(confirm("要继续删除操作请点击'确定',终止操作请点击'取消'")){--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>	
</script>
	</head>
<%
 Object o = request.getAttribute("photoPath");
 if(o != null){
 	System.out.println(o.toString());
 }
 %>
	<body class="loadBody" onload="init();">
		<html:form action="/screen/screenDoctor" method="post" styleId="doctorFormBean" onsubmit="return formAction();">
			<html:hidden property="id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				当前位置&ndash;&gt;添加任务信息--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		当前位置&ndash;&gt;<span id="spanHead">家庭医生</span>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		当前位置&ndash;&gt;修改任务信息--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		当前位置&ndash;&gt;删除任务信息--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				
				<tr>
				<td class="labelStyle">
					医疗保健常识
				</td>
				<td class="valueStyle" style="font-color:#000000">
					<html:select property="docType">
					<option value="healthcare">医疗保健常识</option>
					<option value="prevention">普通疾病识别防治方法与措施</option>
					</html:select>
					<span id="typeTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			<tr height="200">
				<td class="labelStyle">
					内容
				</td>
				<td class="valueStyle" style="font-color:#000000">
					<FCK:editor instanceName="docContent" height="200">
						<jsp:attribute name="value">
							<bean:write name="doctorFormBean" property="docContent"
								filter="false" />
						</jsp:attribute>
					</FCK:editor>
					<span id="docContentTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
				
				<tr>
					<td colspan="2" align="right" class="buttonAreaStyle">
						<input type="submit" name="button" id="buttonSubmit" value="提交"  class="buttonStyle"/>

					</td>
				</tr>
			</table>					
		</html:form>
	</body>
</html:html>