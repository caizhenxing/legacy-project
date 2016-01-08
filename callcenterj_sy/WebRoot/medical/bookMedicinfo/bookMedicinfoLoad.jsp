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

	<title>预约医疗操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../../js/common.js"></script>
	<SCRIPT language=javascript src="../../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
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
        	if (!checkNotNull(addstaffer.bookRid,"受理工号")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
        	
			if (!checkTelNumber(addstaffer.custTel)) return false;
           return true;
        }
				function addinfo()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function update()
				{
					 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../bookMedicinfo.do?method=toBookMedicinfo&type=delete";
			 		
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
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=insert";
					document.getElementById('buttonSubmit').value="添加";
				</c:when>
				<c:when test="${opertype=='update'}">
					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=update";
					document.getElementById('buttonSubmit').value="修改";
				</c:when>
				<c:when test="${opertype=='delete'}">
					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../bookMedicinfo.do?method=toBookMedicinfo&type=delete";
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
			$.formValidator.initConfig({formid:"bookMedicinfoId",onerror:function(msg){alert(msg)}});
			$("#bookRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_sort").formValidator({onshow:"请选择受理专家类别",onfocus:"受理专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家类别"});
			$("#custNameId").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});				
			$("#dictSex").formValidator({empty:false,onshow:"请选择用户性别",onfocus:"用户性别必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择用户性别"});
			$("#custTel").formValidator({onshow:"请输入用户电话",onfocus:"用户电话不能为空",oncorrect:"用户电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户电话两边不能有空符号"},onerror:"用户电话不能为空"});
			$("#custAddr").formValidator({onshow:"请输入用户地址",onfocus:"用户地址不能为空",oncorrect:"用户地址合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户地址两边不能有空符号"},onerror:"用户地址不能为空"});
<%--			$("#caseTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});--%>
			$("#isParter").formValidator({empty:false,onshow:"请选择参加新农合",onfocus:"参加新农合必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择参加新农合"});
			$("#contents").formValidator({onshow:"请输入咨询内容",onfocus:"咨询内容不能为空",oncorrect:"咨询内容合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"咨询内容两边不能有空符号"},onerror:"咨询内容不能为空"});
			$("#reply").formValidator({onshow:"请输入热线答复",onfocus:"热线答复不能为空",oncorrect:"热线答复合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"热线答复两边不能有空符号"},onerror:"热线答复不能为空"});
			})
			</c:when>
			<c:when test="${opertype=='update'}">
			$(document).ready(function(){
			$.formValidator.initConfig({formid:"bookMedicinfoId",onerror:function(msg){alert(msg)}});
			$("#bookRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#expert_sort").formValidator({onshow:"请选择受理专家类别",onfocus:"受理专家类别必须选择",oncorrect:"OK!"}).inputValidator({min:1,onerror: "没有选择受理专家类别"});
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

	<html:form action="/medical/bookMedicinfo.do" method="post" styleId="bookMedicinfoId">

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

				<td class="labelStyle" width="76">
					受&nbsp;理&nbsp;&nbsp;工&nbsp;号
				</td>
				<td class="valueStyle">
					<html:text property="bookRid" styleClass="writeTextStyle" readonly="true" size="15" styleId="bookRid"/>
					<div id="bookRidTip" style="width:0px;display:inline;"></div>
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
					用&nbsp;户&nbsp;&nbsp;姓&nbsp;名
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" size="8" styleId="custNameId"/>
<%--					<font color="#ff0000">*</font>--%>
					<div id="custNameIdTip" style="width: 0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					用户性别
				</td>
				<td class="valueStyle">
					<html:select property="dictSex" styleClass="selectStyle"  styleId="dictSex">
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
					<html:text property="custTel" styleClass="writeTextStyle" style="width:120px" styleId="custTel" />
					<div id="custTelTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					用&nbsp;户&nbsp;&nbsp;地&nbsp;址
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="custAddr" size="43" styleClass="writeTextStyle"  styleId="custAddr"/>
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
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../../flow/incState.jsp?form=BookMedicinfoBean"/>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					参加&nbsp;&nbsp;新农合
				</td>
				<td class="valueStyle" colspan="3">
					<html:select property="isParter" styleClass="selectStyle" style="width:135px" styleId="isParter">
						<html:option value="">请选择</html:option>
						<html:option value="yes">是</html:option>
						<html:option value="noyes">否</html:option>
					</html:select>
					<div id="isParterTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					受理日期
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" style="width:120px"/>
				</td>
			</tr>

			<tr>

				<td class="labelStyle">
					咨&nbsp;询&nbsp;&nbsp;内&nbsp;容
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="contents" styleClass="writeTextStyle"
						cols="76" rows="3"  styleId="contents"/>
					<div id="contentsTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					热&nbsp;线&nbsp;&nbsp;答&nbsp;复
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="reply" styleClass="writeTextStyle"
						cols="76" rows="3" styleId="reply"/>
						<div id="replyTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					就&nbsp;诊&nbsp;&nbsp;类&nbsp;型
				</td>
				<td class="valueStyle">

					<html:select property="dictServiceType" styleClass="selectStyle" style="width:155">

						<html:option value="">
	    						请选择	    					
	    					</html:option>
						<html:options collection="diagnoseList" property="value"
							labelProperty="label" />

					</html:select>


				</td>

				<td class="labelStyle">
					是&nbsp;否&nbsp;&nbsp;就&nbsp;诊
				</td>
				<td class="valueStyle" colspan="3">

					<html:select property="isVisit" styleClass="selectStyle" style="width:155">

						<html:option value="">
	    						请选择	    					
	    					</html:option>
						<html:option value="yes">
	    						是	    					
	    					</html:option>
						<html:option value="noyes">
	    						否	    					
	    					</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					预约就诊时间
				</td>
				<td class="valueStyle">
					<html:text property="bookVisitTime" readonly="true"
						styleClass="writeTextStyle" />
					<img alt="选择日期" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','bookVisitTime',false);">
				</td>
				<td class="labelStyle">
					实际就诊时间
				</td>
				<td colspan="3" class="valueStyle">
					<html:text property="visitTime" readonly="true"
						styleClass="writeTextStyle" />
					<img alt="选择日期" src="../../html/img/cal.gif"
						onclick="openCal('BookMedicinfoBean','visitTime',false);">
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					医院审核意见
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="hospitalAdvice"
						styleClass="writeTextStyle" cols="76" rows="5" />
				</td>
			</tr>
			<tr>
				<td height="16" class="labelStyle">
					导&nbsp;&nbsp;&nbsp;诊&nbsp;&nbsp;&nbsp;员
				</td>
				<td class="valueStyle">
					<html:text property="navigator" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="navigator" >--%>

					<%--	    					<html:option value="">--%>
					<%--	    						请选择							--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:optionsCollection property="" name=""/>--%>
					<%--	    				</html:select>	    			--%>
				</td>
				<td rowspan="4" class="labelStyle" styleClass="selectStyle">
					优&nbsp;惠&nbsp;政&nbsp;策
				</td>
				<td class="valueStyle" colspan="3" rowspan="4">

					<%--	    				<html:select multiple="true" size="5" property="favours">--%>
					<%--	    					<html:options Collection="" name=""  property=""/>--%>
					<%--	    				</html:select>--%>

					<html:textarea property="favours" cols="31" rows="8"
						styleClass="writeTextStyle"></html:textarea>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					床&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位
				</td>
				<td class="valueStyle">
					<html:text property="bed" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="bed" styleClass="selectStyle">--%>
					<%----%>
					<%--	    					<html:option value="">--%>
					<%--	    						请选择	    					--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:option value="yes">--%>
					<%--	    						是	    					--%>
					<%--	    					</html:option>--%>
					<%--	    					<html:option value="noyes">--%>
					<%--	    						否	    					--%>
					<%--	    					</html:option>--%>
					<%--	    				</html:select>	    			--%>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					就&nbsp;诊&nbsp;&nbsp;项&nbsp;目
				</td>
				<td class="valueStyle">
					<html:text property="items" styleClass="writeTextStyle"></html:text>
					<%--	    				<html:select property="items" styleClass="selectStyle">--%>
					<%----%>
					<%--	    					<html:option value="">--%>
					<%--	    						请选择	    					--%>
					<%--	    					</html:option>--%>
					<%--	    				</html:select>	    		--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					总&nbsp;计&nbsp;&nbsp;费&nbsp;用
				</td>
				<td class="valueStyle">

					<html:text property="charge" styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					就&nbsp;医&nbsp;&nbsp;结&nbsp;果
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="visitResult" cols="76"
						styleClass="writeTextStyle" rows="3" />
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					跟&nbsp;踪&nbsp;&nbsp;服&nbsp;务
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="traceService" cols="76"
						styleClass="writeTextStyle" rows="3" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注
				</td>
				<td colspan="5" class="valueStyle">
					<html:textarea property="remark" cols="76"
						styleClass="writeTextStyle" rows="3" />
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

