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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>普通案例库信息操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../../js/common.js"></script>
	<script language="javascript" src="../../js/clock.js"></script>
	<script language="javascript" src="./../../js/ajax.js"></script>
	<script language="javascript" src="./../../js/all.js"></script>
	<script language="javascript" src="./../../js/agentState.js"></script>
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	
	<!-- dwr -->	
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/casetype.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
    
	<!-- jquery验证 -->
	<script src="../../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../../css/validator.css"></link>
	<script src="../../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
	
<style type="text/css">
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
</style>

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
<%--				function add()--%>
<%--				{--%>
<%--				    var f =document.forms[0];--%>
<%--    	    		if(checkForm(f)){--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=insert";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
<%--				function update()--%>
<%--				{--%>
<%--					var f =document.forms[0];--%>
<%--    	    		if(checkForm(f)){--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=update";--%>
<%--			 	--%>
<%--			 		document.forms[0].submit();--%>
<%--			 		}--%>
<%--				}--%>
<%--				function del()--%>
<%--				{--%>
<%--			 		document.forms[0].action="../generalCaseinfo.do?method=toGeneralCaseinfo&type=delete";--%>
<%--			 		--%>
<%--			 		document.forms[0].submit();--%>
<%--				}--%>
				
				//初始化
		function init(){
			<c:choose>
				<c:when test="${opertype=='detail'}">
					document.getElementById('buttonSubmit').style.display="none";
				</c:when>
				<c:when test="${opertype=='insert'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=insert";
					document.getElementById('spanHead').innerHTML="添加信息";
					document.getElementById('buttonSubmit').value="添加";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=update";
					document.getElementById('spanHead').innerHTML="修改信息";
					document.getElementById('buttonSubmit').value="修改";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.forms[0].action = "../generalCaseinfo.do?method=toGeneralCaseinfo&type=delete";
					document.getElementById('spanHead').innerHTML="删除信息";
					document.getElementById('buttonSubmit').value="删除";
				</c:when>
			</c:choose>
		}
		//执行验证

		<c:choose>				
			<c:when test="${opertype=='insert'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"generalCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_name").formValidator({onshow:"请选择受理专家",onfocus:"受理专家必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#caseAttr1").formValidator({empty:false,onshow:"请选择案例大类",onfocus:"案例大类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例大类"});			
			$("#caseAttr2").formValidator({empty:false,onshow:"请选择案例小类",onfocus:"案例小类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例小类"});
			$("#caseAttr3").formValidator({empty:false,onshow:"请选择案例种类",onfocus:"案例种类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例种类"});
			$("#caseContent").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#caseReply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"generalCaseId",onerror:function(msg){alert(msg)}});
			$("#dealCaseId").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_name").formValidator({onshow:"请选择受理专家",onfocus:"受理专家必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家"});
			$("#userName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#caseAttr1").formValidator({empty:false,onshow:"请选择案例大类",onfocus:"案例大类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例大类"});			
			$("#caseAttr2").formValidator({empty:false,onshow:"请选择案例小类",onfocus:"案例小类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例小类"});
			$("#caseAttr3").formValidator({empty:false,onshow:"请选择案例种类",onfocus:"案例种类必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择案例种类"});
			$("#caseContent").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#caseReply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			})
			</c:when>
		</c:choose>
		
		function toback()
		{

			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
				var link = opener.parent.bottomm.document.location.href;
				if(link.indexOf("pagestate") == -1){
					link += "&pagestate=1";
				}
				opener.parent.bottomm.document.location = link;
			}
		
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

	<!-- dwr -->
    <script type="text/javascript">
	//联动 大类--->小类
    function bigChange(obj){   
    	var pid=obj.value;
    	document.getElementById("caseAttr2").length=1;  	    	
    	if(pid!="")
    	casetype.getSmallTypeByBigType_app(pid,getSmallList);
    }
    
    function getSmallList(obj){
    	var v_obj = document.getElementById("caseAttr2");
    	v_obj.length=1;
    	DWRUtil.addOptions(v_obj,obj);
    }
	</script>
	
</head>

<body onunload="toback()" class="loadBody" onload="init()">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/caseinfo/generalCaseinfo.do" method="post" styleId="generalCaseId">

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
				<td class="valueStyle">
					<html:text property="caseRid" styleClass="writeTextStyle" readonly="true" styleId="dealCaseId"/>
					<div id="dealCaseIdTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					受理专家
				</td>
				<td class="valueStyle" colspan="3">
					<html:select styleId="bill_num" property="expertType" styleClass="selectStyle" onchange="selecttype1()">
						<html:option value="0">专家类别</html:option>
						<html:options collection="expertList" property="value" labelProperty="label"/>
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expert_name" property="caseExpert" styleClass="selectStyle">
					<html:option value="0">选择专家</html:option>
								<%
									String rExpertName = (String)request.getAttribute("rExpertName");
									if(rExpertName!=null&&!"".equals(rExpertName))
									{
										out.print("<option value='"+rExpertName+"' selected='selected'>"+rExpertName+"</option>");
									}
									/*else
									{
										out.print("<option value=\"0\">选择专家</option>");
									}*/
								%>
					</html:select>
					<div id="expert_nameTip" style="width:0px;display:inline;"></div>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					用户姓名
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="userName"/>
					<div id="userNameTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					用户电话
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">				
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean&opertype=delete"/>
				</logic:equal>
<%--				<jsp:include flush="true" page="../../flow/incState.jsp?form=generalCaseinfoBean"/>--%>
					<br>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					用户地址
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="71"
						styleClass="writeTextStyle" />
					<input type="button" name="btnadd" class="buttonStyle" value="选择"
						onClick="window.open('generalCaseinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td class="labelStyle" width="70">
					受理时间
				</td>
				<td class="valueStyle" width="130">
					<html:text property="caseTime" styleClass="writeTextStyle" size="18" readonly="true" styleId="caseTime"/>
					<html:hidden property="dictCaseType" value="putong" />
					<div id="caseTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>

				<td class="labelStyle">
					案例大类
				</td>
				<td class="valueStyle">
<%--					<html:select property="caseAttr1" styleClass="selectStyle" onchange="selecttype(this)" styleId="caseAttr1">--%>
<%--						<html:option value="">请选择</html:option>--%>
<%--						<html:option value="粮油作物">粮油作物</html:option>--%>
<%--						<html:option value="经济作物">经济作物</html:option>--%>
<%--						<html:option value="蔬菜">蔬菜</html:option>--%>
<%--						<html:option value="药材">药材</html:option>--%>
<%--						<html:option value="花卉">花卉</html:option>--%>
<%--						<html:option value="草坪及地被">草坪及地被</html:option>--%>
<%--						<html:option value="家畜">家畜</html:option>--%>
<%--						<html:option value="家禽">家禽</html:option>--%>
<%--						<html:option value="牧草">牧草</html:option>--%>
<%--						<html:option value="鱼类">鱼类</html:option>--%>
<%--						<html:option value="虾/蟹/鳖/龟/蛙/藻/螺贝及软体类">虾/蟹/鳖/龟/蛙/藻/螺贝及软体类</html:option>--%>
<%--						<html:option value="特种养殖">特种养殖</html:option>--%>
<%--						<html:option value="基础设施及生产资料">基础设施及生产资料</html:option>--%>
<%--						<html:option value="政策法规及管理">政策法规及管理</html:option>--%>
<%--						<html:option value="其他">其他</html:option>--%>
<%--					</html:select>--%>
					<html:select property="caseAttr1" onchange="bigChange(this)" styleId="caseAttr1" styleClass="selectStyle">
				    	<html:option value="">--请选择--</html:option>
				    	<html:optionsCollection name="bigtypelist"/>
			    	</html:select>
					<div id="caseAttr1Tip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					案例小类
				</td>
				<td class="valueStyle" >
<%--					<span id="caseAttr2_td">--%>
<%--					<html:text property="caseAttr2" styleClass="writeTextStyle" styleId="caseAttr2"/>					--%>
<%--					</span>--%>
					<html:select property="caseAttr2" styleClass="writeTextStyle" styleId="caseAttr2">
			    		<html:option value="">--请选择--</html:option>
			    		<html:optionsCollection name="smallTypeList"/>
			    	</html:select>
					<div id="caseAttr2Tip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					案例种类
				</td>
				<td class="valueStyle">
					<html:select property="caseAttr3" styleClass="selectStyle" style="width:120px" styleId="caseAttr3">
						<html:option value="">请选择</html:option>
						<html:option value="品种介绍">品种介绍</html:option>
						<html:option value="栽培管理">栽培管理</html:option>
						<html:option value="养殖管理">养殖管理</html:option>
						<html:option value="疫病防治">疫病防治</html:option>
						<html:option value="虫草防除">虫草防除</html:option>
						<html:option value="收获贮运">收获贮运</html:option>
						<html:option value="产品加工">产品加工</html:option>
						<html:option value="市场行情">市场行情</html:option>
						<html:option value="饲料配制">饲料配制</html:option>
						<html:option value="农资使用">农资使用</html:option>
						<html:option value="设施修建">设施修建</html:option>
						<html:option value="政策管理">政策管理</html:option>
						<html:option value="其他综合">其他综合</html:option>
					</html:select>
					<div id="caseAttr3Tip" style="width:0px;display:inline;"></div>
				</td>

			</tr>

			<tr>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseContent" styleClass="writeTextStyle"
						rows="4" cols="77" styleId="caseContent"/>
						<div id="caseContentTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="180" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					热线答复
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReply" styleClass="writeTextStyle"
						rows="4" cols="77"  styleId="caseReply"/>
						<div id="caseReplyTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					案例点评
				</td>
				<td class="valueStyle" colspan="3">
					<html:textarea property="caseReview" styleClass="writeTextStyle" rows="4" cols="77" />
				</td>
				<%
					String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("generalCaseinfoBean")).get("caseId");							
					String p = "../../upload/show2.jsp?t=oper_caseinfo.case_id&id=" + id + "&opertype="+request.getAttribute("opertype");
				%>
				<td class="valueStyle" rowspan="2" colspan="2" style="text-indent: 0px;">					
					<iframe frameborder="0" width="100%" scrolling="auto" src="<%=p %>"></iframe>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					备&nbsp;&nbsp;&nbsp;注
				</td>
				<td class="valueStyle" colspan="3">
				<html:textarea property="remark" styleClass="writeTextStyle" cols="77" rows="4" />
				</td>
			</tr>
			<tr>
				<td colspan="6" align="right" class="buttonAreaStyle">
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
<%--						<input type="button" name="btnDelete" class="" value="删除"--%>
<%--							onclick="del()" />--%>
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
	
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "请选择"){
			sendRequest("../../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
		}
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
	function sendRequest(url,value,process) {
		createXMLHttpRequest();
		XMLHttpReq.open("post", url, true);
		XMLHttpReq.onreadystatechange = process;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(value);  // 发送请求
	}
	
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle' id='caseAttr2'>"+res+"</select>";//<div id='caseAttr2Tip' style='width:0px;display:inline;'></div>
<%--                document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle' id='caseAttr2'><option value=\"\">请选择</option>"+res+"</select>";--%>
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>
