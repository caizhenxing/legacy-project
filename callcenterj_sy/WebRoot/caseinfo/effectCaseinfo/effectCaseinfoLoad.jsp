<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>效果案例库信息操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<style type="text/css">
<!--
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript" src="../../js/clock.js"></script>
	<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>
	<script language="javascript" src="./../../js/ajax.js"></script>
<script language="javascript" src="./../../js/all.js"></script>
<script language="javascript" src="./../../js/agentState.js"></script>
		
	<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
		
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
        	if (!checkNotNull(addstaffer.caseRid,"受理工号")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
        	if (!checkTelNumber(addstaffer.custTel)) return false;

           return true;
        }
				function add()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../effectCaseinfo.do?method=toEffectCaseinfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		//初始化
		function init(){
			<c:choose>
				<c:when test="${opertype=='detail'}">
					document.getElementById('buttonSubmit').style.display="none";
				</c:when>
				<c:when test="${opertype=='insert'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=insert";
					document.getElementById('spanHead').innerHTML="添加信息";
					document.getElementById('buttonSubmit').value="添加";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=update";
					document.getElementById('spanHead').innerHTML="修改信息";
					document.getElementById('buttonSubmit').value="修改";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.forms[0].action = "../effectCaseinfo.do?method=toEffectCaseinfo&type=delete";
					document.getElementById('spanHead').innerHTML="删除信息";
					document.getElementById('buttonSubmit').value="删除";
				</c:when>
			</c:choose>
		}
		//执行验证
		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"effectCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#caseAttr5").formValidator({empty:false,onshow:"请选择案例属性",onfocus:"案例属性必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例属性"});			
			$("#caseContent").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#caseReply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			$("#caseReport").formValidator({onshow:"请输入相关报道",onfocus:"相关报道不能为空",oncorrect:"相关报道合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"相关报道两边不能有空符号"},onerror:"相关报道不能为空"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"effectCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#caseAttr5").formValidator({empty:false,onshow:"请选择案例属性",onfocus:"案例属性必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例属性"});			
			$("#caseContent").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#caseReply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			$("#caseReport").formValidator({onshow:"请输入相关报道",onfocus:"相关报道不能为空",oncorrect:"相关报道合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"相关报道两边不能有空符号"},onerror:"相关报道不能为空"});
			})
			</c:when>
		</c:choose>		
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
					function selecttype1(){
					document.getElementById('expert_name').innerHTML="";			
					document.getElementById('expert_name').add(new Option("选择专家","0")); 
		//专家类别id
		var billnum = document.getElementById('bill_num').value;
		//getClassExpertsInfo('expert_name','',billnum);
		getBClassExpertsInfo('expert_name','',billnum,'<%=basePath%>')
		//动态生成的select id 为 expert_name
		
	}
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/caseinfo/effectCaseinfo.do" method="post" styleId="effectCaseId">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
			<td class="navigateStyle">
				当前位置&ndash;&gt;
				<span id="spanHead">详细</span>
				</td>
<%--				<td class="navigateStyle">--%>
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
<%--				</td>--%>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle" width="268">
					<html:text property="caseRid" styleClass="writeTextStyle" readonly="true" styleId="dealCaseId"/>
					<div id="dealCaseIdTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" width="80">
					受理专家
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="caseExpert" styleClass="writeTextStyle" />--%>
					<html:select styleId="bill_num" property="expertType" styleClass="selectStyle" onchange="selecttype1()">
						<html:option value="0">专家类别</html:option>
						<html:options collection="expertList" property="value" labelProperty="label"/>
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName))
									{
										out.print("<option value="+rExpertName+">"+rExpertName+"</option>");
									}
									else
									{
										out.print("<option value=\"0\">选择专家</option>");
									}
								%>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					用户姓名
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="userName"/>
				<div id="userNameTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					用户电话
				</td>
				<td class="valueStyle">
					<html:text property="custTel" style="width: 120" styleClass="writeTextStyle" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean&opertype=delete"/>
				</logic:equal>					
<%--					<jsp:include flush="true" page="../../flow/incState.jsp?form=effectCaseinfoBean"/>--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					用户地址
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="70"
						styleClass="writeTextStyle" />
						<input type="button" name="btnadd" class="buttonStyle" value="选择"
						onClick="window.open('effectCaseinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td class="labelStyle">
					受理时间
				</td>
				<td class="valueStyle">
					<html:text property="caseTime" styleClass="writeTextStyle" size="18" styleId="caseTime"/>
					<html:hidden property="dictCaseType" value="effectCase" />
					<div id="caseTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					案例属性
				</td>
				<td class="valueStyle" colspan="5">
					<html:select property="caseAttr5" styleClass="selectStyle" style="width:130px"  styleId="caseAttr5">
						<html:option value="">请选择</html:option>
						<html:option value="用户建议">用户建议</html:option>
						<html:option value="用户评价">用户评价</html:option>
						<html:option value="服务成效">服务成效</html:option>
					</html:select>
					<div id="caseAttr5Tip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseContent" styleClass="writeTextStyle"
						cols="75" rows="4" styleId="caseContent"/>
						<div id="caseContentTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="180" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
<%--					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					热线答复
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReply" styleClass="writeTextStyle"
						cols="75" rows="4" styleId="caseReply"/>
					<div id="caseReplyTip" style="width:0px;display:inline;"></div>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					相关报道
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReport" styleClass="writeTextStyle"
						cols="75" rows="4"  styleId="caseReport"/>
						<div id="caseReportTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="valueStyle" rowspan="2" colspan="2" style="width:212px"
					style="text-indent: 0px;">
					<%
						String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("effectCaseinfoBean")).get("caseId");
						String p = "../../upload/show2.jsp?t=oper_caseinfo.case_id&id=" + id + "&opertype="+request.getAttribute("opertype");
					%>
					<iframe marginwidth="0" framespacing="0" marginheight="0"
						frameborder="0" scrolling="auto" style="width:100%" src="<%=p%>"></iframe>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					备&nbsp;&nbsp;&nbsp;&nbsp;注
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="remark" styleClass="writeTextStyle"
						cols="75" rows="4" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
				<input type="submit" name="addbtn" value="添加" id="buttonSubmit" class="buttonStyle" />
					<input type="button" name="reset" value="关闭"
						onClick="javascript:window.close();" class="buttonStyle" />
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd" class="buttonStyle" value="添加"--%>
<%--							onclick="add()" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate" class="buttonStyle"--%>
<%--							value="确定" onclick="update()" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete" class="buttonStyle"--%>
<%--							value="删除" onclick="del()" />--%>
<%--					</logic:equal>--%>
<%----%>
<%--					<input type="button" name="" value="关闭" class="buttonStyle"--%>
<%--						onClick="javascript:window.close();" />--%>

				</td>
			</tr>
			<html:hidden property="caseId" />
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
