<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html:html locale="true">
<head>
	<html:base />

	<title>普通医疗操作</title>

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
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
	<script language="javascript" src="../../js/clock.js"></script>
	<SCRIPT language="javascript" src="../../js/form.js"
		type=text/javascript>
		
</SCRIPT>
		<script language="javascript" src="./../../js/ajax.js"></script>
		<script language="javascript" src="./../../js/all.js"></script>
		<script language="javascript" src="./../../js/agentState.js"></script>
		
	<!-- dwr调用 -->
	<script type='text/javascript'src='/callcenterj_sy/dwr/interface/expertService.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	<script type="text/javascript">
		 // 根据专家类别,获得专家
    	function getExpert_dwr(){
    	//alert("1");
    		//var obj_Pro = document.getElementById("sfd");
    		var obj_expertName = document.getElementById("expertName");
    		//var pro_Index = obj_Pro.selectedIndex;
    		//var pro_Value = obj_Pro.options[pro_Index].value;
    		var pro_Value = document.getElementById('billNum').value;
    		if(pro_Value != "" && pro_Value != null){
    		//alert("2:"+pro_Value);
    			expertService.getExpert(pro_Value,expertReturn);
    		}else{
    			DWRUtil.removeAllOptions(obj_expertName);    			
				DWRUtil.addOptions(obj_expertName,{'':'选择专家'});
    		}
    	}
    // 回调函数
    	function expertReturn(data){
    		var obj_expertName = document.getElementById("expertName");    		
    		DWRUtil.removeAllOptions(obj_expertName);
			DWRUtil.addOptions(obj_expertName,{'':'选择专家'});
			DWRUtil.addOptions(obj_expertName,data);
    	}
	</script>
	<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
	<!-- jquery验证 end -->
		
	<script type="text/javascript">
			
		function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.medicRid,"受理工号")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
        	
			if(!checkTelNumber(addstaffer.custTel)) return false;
           return true;
        }
				function addinfo()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../medicinfo.do?method=toMedicinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../medicinfo.do?method=toMedicinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../medicinfo.do?method=toMedicinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
			//opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
		function selecttype1(){
			//专家类别id
			var billnum = document.getElementById('bill_num').value;
			//getClassExpertsInfo('expert_name','',billnum);
			getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
			//动态生成的select id 为 expert_name
		
		}
		
		//初始化
		function init(){
			<c:choose>
				<c:when test="${opertype=='insert'}">
					document.getElementById('spanHead').innerHTML="添加信息";
					document.forms[0].action = "../medicinfo.do?method=toMedicinfo&type=insert";
					document.getElementById('buttonSubmit').value="添加";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../medicinfo.do?method=toMedicinfo&type=update";
					document.getElementById('buttonSubmit').value="修改";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../medicinfo.do?method=toMedicinfo&type=delete";
					document.getElementById('buttonSubmit').value="删除";
				</c:when>
				<c:when test="${opertype=='detail'}">
					document.getElementById('spanHead').innerHTML="查看信息";
<%--					document.forms[0].action = "../operCorpinfo.do?method=toOperCorpinfo&type=delete";--%>
					document.getElementById('buttonSubmit').style.display="none"
				</c:when>
			</c:choose>
		}
		
		//执行验证		
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"medicinfoId",onerror:function(msg){alert(msg)}});
			$("#medicRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_sort").formValidator({onshow:"请选择受理专家类别",onfocus:"受理专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家类别"});
<%--			$("#expert_name").formValidator({onshow:"请选择受理专家",onfocus:"受理专家必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家"});--%>
			$("#custNameId").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#dictSex").formValidator({empty:false,onshow:"请选择用户性别",onfocus:"用户性别必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择用户性别"});
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#custAddr").formValidator({onshow:"请输入用户地址",onfocus:"用户地址不能为空",oncorrect:"用户地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户地址两边不能有空符号"},onerror:"用户地址不能为空"});
<%--			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});--%>
			$("#contents").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#reply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"medicinfoId",onerror:function(msg){alert(msg)}});
			$("#medicRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_sort").formValidator({onshow:"请选择受理专家类别",onfocus:"受理专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家类别"});
<%--			$("#expert_name").formValidator({onshow:"请选择受理专家",onfocus:"受理专家必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家"});--%>
			$("#custNameId").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#dictSex").formValidator({empty:false,onshow:"请选择用户性别",onfocus:"用户性别必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择用户性别"});
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#custAddr").formValidator({onshow:"请输入用户地址",onfocus:"用户地址不能为空",oncorrect:"用户地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户地址两边不能有空符号"},onerror:"用户地址不能为空"});
<%--			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});--%>
			$("#contents").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#reply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			})
			</c:when>
		</c:choose>
				
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/medical/medicinfo.do" method="post" styleId="medicinfoId">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					<span id="spanHead">查看信息</span>
<%--					<logic:equal name="opertype" value="insert">--%>
<%--		    		添加信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="detail">--%>
<%--		    		查看信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--		    		修改信息--%>
<%--		    	</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--		    		删除信息--%>
<%--		    	</logic:equal>--%>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>

				<td class="labelStyle">
					受&nbsp;理&nbsp;工&nbsp;号
				</td>
				<td class="valueStyle">
					<html:text property="medicRid" styleClass="writeTextStyle" size="15" readonly="true" styleId="medicRid"/>
					<div id="medicRidTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					受理专家
				</td>
				<td class="valueStyle" colspan="3">
				<html:select styleId="expert_sort" property="billNum" styleClass="writeTextStyle" onchange="getExpert_dwr()" style="text-indent: 5px;">
						<html:option value="">选择专家类别</html:option>
						<html:options collection="expertList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName" styleClass="writeTextStyle" style="text-indent: 5px;">
						<html:option value="">选择专家</html:option>
						<html:options collection="expertNameList" property="value" labelProperty="label" styleClass="writeTypeStyle"/>
					</html:select>
					<div id="expert_sortTip" style="width:0px;display:inline;"></div>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					用&nbsp;户&nbsp;姓&nbsp;名
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" size="10" styleId="custNameId" />
					<div id="custNameIdTip" style="width: 0px;display:inline;"></div>
				</td>

				<td class="labelStyle">
					用户性别
				</td>
				<td class="valueStyle">
					<html:select property="dictSex" styleClass="selectStyle" styleId="dictSex">
						<html:option value="">请选择</html:option>
						<html:option value="SYS_TREE_0000000663">├男</html:option>
						<html:option value="SYS_TREE_0000000664">├女</html:option>
					</html:select>
					<div id="dictSexTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					用户电话
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" size="18" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>

			</tr>
			<tr>
				<td class="labelStyle">
					用&nbsp;户&nbsp;地&nbsp;址
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="35" styleClass="writeTextStyle" styleId="custAddr"/>
					<input name="add" type="button" id="add" value="选择"
					onClick="window.open('../sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
					class="buttonStyle" >
					<div id="custAddrTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=medicinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=medicinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=medicinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=medicinfoBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../../flow/incState.jsp?form=medicinfoBean"/>--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					参加&nbsp;新农合
				</td>
				<td class="valueStyle" colspan="3">
					<html:select property="isParter" styleClass="selectStyle">
						<html:option value="">请选择</html:option>
						<html:option value="yes">是</html:option>
						<html:option value="noyes">否</html:option>
					</html:select>
				</td>
				<td class="labelStyle">
					受理日期
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" size="18"/>
				</td>
			</tr>

			<tr>

				<td class="labelStyle">
					咨&nbsp;询&nbsp;内&nbsp;容
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="contents" styleClass="writeTextStyle"
						cols="70" rows="5"  styleId="contents"/>
					<div id="contentsTip" style="width:0px;display:inline;"></div>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					热&nbsp;线&nbsp;答&nbsp;复
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="reply" styleClass="writeTextStyle"
						cols="70" rows="5" styleId="reply"/>
						<div id="replyTip" style="width:0px;display:inline;"></div>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					跟&nbsp;踪&nbsp;服&nbsp;务
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="traceService" styleClass="writeTextStyle"
						cols="70" rows="5" />
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="remark" styleClass="writeTextStyle"
						cols="70" rows="5" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="添加"--%>
<%--							onclick="addinfo()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="确定" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="删除" onclick="del()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
					
					<input type="submit" name="addbtn" value="添加" id="buttonSubmit"
							class="buttonStyle" style="display:inline" />
					
					<input type="button" name="" value="关闭"  
						onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
			<html:hidden property="id" />
		</table>
	</html:form>
</body>
</html:html>
<script>

	function getAccid(v){
		sendRequest("../../focusPursue/getAccid.jsp", "state="+v);
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
	function sendRequest(url,value) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("accids").innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>

