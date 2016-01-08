<%@ page contentType="text/html; charset=gbk"%>

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
		<title>金农市场分析大屏幕维护管理</title>
		<script language="javascript" type="text/JavaScript" src="../../js/calendar3.js"></script>
		<script language="javascript" src="../../js/common.js"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
				
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
			document.getElementById('analysiserPhoto').value=window.frames["iframeUpload"].document.getElementById('filePathName').value;

		}
		
		//初始化
		function init(){	
			<logic:equal name="opertype" value="detail">
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=insert";
				document.getElementById('spanHead').innerHTML="添加金农市场分析信息";
				document.getElementById('buttonSubmit').value="添加";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=update";
				document.getElementById('spanHead').innerHTML="修改金农市场分析信息";
				document.getElementById('buttonSubmit').value="修改";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "marAnalysis.do?method=toMarAnalysisOper2&type=delete";
				document.getElementById('spanHead').innerHTML="删除金农市场分析信息";
				document.getElementById('buttonSubmit').value="删除";
				v_flag="del"
			</logic:equal>
		}
		//执行验证
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#analysisPerson").formValidator({onshow:"请输入分析师",onfocus:"分析师不能为空",oncorrect:"分析师合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析师两边不能有空符号"},onerror:"分析师不能为空"});	
			$("#subTitle").formValidator({onshow:"请输入分析主题",onfocus:"分析主题不能为空",oncorrect:"分析主题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析主题两边不能有空符号"},onerror:"分析主题不能为空"});	
			$("#analysisContent").formValidator({onshow:"请输入分析正文",onfocus:"分析正文不能为空",oncorrect:"分析正文合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析正文两边不能有空符号"},onerror:"分析正文不能为空"});	
			$("#analysisType").formValidator({onshow:"请选择类别",onfocus:"类别不能为空",oncorrect:"类别合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"类别两边不能有空符号"},onerror:"类别不能为空"});	
		
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#analysisPerson").formValidator({onshow:"请输入分析师",onfocus:"分析师不能为空",oncorrect:"分析师合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析师两边不能有空符号"},onerror:"分析师不能为空"});	
			$("#subTitle").formValidator({onshow:"请输入分析主题",onfocus:"分析主题不能为空",oncorrect:"分析主题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析主题两边不能有空符号"},onerror:"分析主题不能为空"});	
			$("#analysisContent").formValidator({onshow:"请输入分析正文",onfocus:"分析正文不能为空",oncorrect:"分析正文合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"分析正文两边不能有空符号"},onerror:"分析正文不能为空"});	
			$("#analysisType").formValidator({onshow:"请选择类别",onfocus:"类别不能为空",oncorrect:"类别合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"类别两边不能有空符号"},onerror:"类别不能为空"});	
		
		})
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
		<html:form action="/screen/marAnalysis" method="post" styleId="marAnalysisBean" onsubmit="return formAction();">
			<html:hidden property="id" />
			<html:hidden property="analysiserPhoto2" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				当前位置&ndash;&gt;添加任务信息--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		当前位置&ndash;&gt;<span id="spanHead">查看金农市场分析信息</span>
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
					<td class="labelStyle">分析师</td>
					<td class="valueStyle" colspan="3">
						<html:text property="analysisPerson" styleClass="writeTextStyle" styleId="analysisPerson"/>
						<div id="analysisPersonTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">分析师简介</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="analysisPersonInfo" styleClass="writeTextStyle" rows="3" cols="40" styleId="analysisPersonInfo"/>
						<div id="analysisPersonInfoTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">分析师照片</td>
					<td class="valueStyle" colspan="3">
						<logic:equal name="opertype" value="insert">
						    <input type="hidden" name="analysiserPhoto" id="analysiserPhoto" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle"/>--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="update">
							<input type="hidden" name="analysiserPhoto" id="analysiserPhoto" class="buttonStyle">
<%--						<input type="file" name="analysiserPhoto" class="buttonStyle" value="<%=request.getAttribute("photoPath").toString() %>">--%>
<%--						<html:file property="analysiserPhoto" styleClass="buttonStyle" />--%>
<iframe frameborder="0" name="iframeUpload" width="100%" height="75" scrolling="No" src="../fileUpload/fileUpload.jsp" allowTransparency="true"></iframe>
						</logic:equal>
						<logic:equal name="opertype" value="detail">
						<html:text property="analysiserPhoto" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>
						<logic:equal name="opertype" value="delete">
						<html:text property="analysiserPhoto" styleClass="writeTextStyle" readonly="true"/>
						</logic:equal>&nbsp; 
						<img src="../../<bean:write name="marAnalysisBean" property="analysiserPhoto" />" width="50" height="50" />
				    </td>
				</tr>
				<tr>
					<td class="labelStyle">分析主题</td>
					<td class="valueStyle" colspan="3">
						<html:text property="subTitle" styleClass="writeTextStyle" styleId="subTitle"/>
						<div id="subTitleTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">分析正文</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="analysisContent" styleClass="writeTextStyle" rows="5" cols="40" styleId="analysisContent"/>
						<div id="analysisContentTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">类&nbsp;&nbsp;&nbsp;&nbsp;别</td>
					<td class="valueStyle" colspan="3">
						<html:select property="analysisType" style="width:130" styleId="analysisType">
							<html:option value="">请选择</html:option>
							<html:option value="农民版">农民版</html:option>
							<html:option value="政府版">政府版</html:option>
						</html:select>
						<div id="analysisTypeTip" style="width: 10px;display:inline;"></div>
					</td>	
				</tr>
				
				<tr>
					<td class="labelStyle">备注</td>
					<td class="valueStyle" colspan="3">
						<html:textarea property="remark" styleClass="writeTextStyle" rows="3" cols="40" styleId="remark"/>
						<div id="remarkTip" style="width: 10px;display:inline;"></div>
					</td>
				</tr>
				<tr>
					<td class="labelStyle">添加时间</td>
					<td class="valueStyle" colspan="3">
						<html:text property="recordTime" styleClass="writeTextStyle" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="right" class="buttonAreaStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--							<input type="button" name="btnAdd" class="buttonStyle" value="添加" onclick="add()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--							<input type="button" name="btnUpdate" class="buttonStyle" value="确定" onclick="update()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--							<input type="button" name="btnDelete" class="buttonStyle" value="删除" onclick="del()" />--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="提交"  class="buttonStyle"/>
						<input type="button" name="" value="关闭" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>					
		</html:form>
	</body>
</html:html>