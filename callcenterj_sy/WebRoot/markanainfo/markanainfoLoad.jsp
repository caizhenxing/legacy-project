<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>市场分析操作</title>

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
	<SCRIPT language=javascript src="../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../js/common.js"></script>
	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language="javascript" src="../js/form.js" type=text/javascript>
</SCRIPT>
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
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=insert";
					document.getElementById('btnOper').value="添加";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=update";
					document.getElementById('btnOper').value="修改";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../markanainfo.do?method=toMarkanainfo&type=delete";
					document.getElementById('btnOper').value="删除";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		//执行验证
		//品种     版别 改 评别
		
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#frontFor").formValidator({onshow:"请输入主办方",onfocus:"主办方不能为空",oncorrect:"主办方合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主办方两边不能有空符号"},onerror:"主办方不能为空"});
			$("#underTake").formValidator({onshow:"请输入承办方",onfocus:"承办方不能为空",oncorrect:"承办方合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"承办方两边不能有空符号"},onerror:"承办方不能为空"});
			$("#supportTel").formValidator({onshow:"请输入支持热线",onfocus:"支持热线不能为空",oncorrect:"支持热线合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"支持热线两边不能有空符号"},onerror:"支持热线不能为空"});
			$("#supportSite").formValidator({onshow:"请输入支持网站",onfocus:"支持网站不能为空",oncorrect:"支持网站合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"支持网站两边不能有空符号"},onerror:"支持网站不能为空"});
			$("#contactMail").formValidator({onshow:"请输入联系邮箱",onfocus:"联系邮箱不能为空",oncorrect:"联系邮箱合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系邮箱两边不能有空符号"},onerror:"联系邮箱不能为空"});
			$("#chiefEditor").formValidator({onshow:"请输入主编",onfocus:"主编不能为空",oncorrect:"主编合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主编两边不能有空符号"},onerror:"主编不能为空"});
			$("#subEditor").formValidator({onshow:"请输入副主编",onfocus:"副主编不能为空",oncorrect:"副主编合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"副主编两边不能有空符号"},onerror:"副主编不能为空"});			
			$("#firstEditor").formValidator({onshow:"请输入首席编辑",onfocus:"首席编辑不能为空",oncorrect:"首席编辑合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"首席编辑两边不能有空符号"},onerror:"首席编辑不能为空"});
			$("#chargeEditor").formValidator({onshow:"请输入责任编辑",onfocus:"责任编辑不能为空",oncorrect:"责任编辑合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"责任编辑两边不能有空符号"},onerror:"责任编辑不能为空"});
			$("#period").formValidator({onshow:"请输入期第",onfocus:"期第不能为空",oncorrect:"期第合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"期第两边不能有空符号"},onerror:"期第不能为空"});
			$("#createTime").formValidator({onshow:"请输入出刊时间",onfocus:"出刊时间不能为空",oncorrect:"出刊时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"出刊时间两边不能有空符号"},onerror:"出刊时间不能为空"});
			$("#sendUnit").formValidator({onshow:"请输入报送单位",onfocus:"报送单位不能为空",oncorrect:"报送单位合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"报送单位两边不能有空符号"},onerror:"报送单位不能为空"});
			$("#dictProductType").formValidator({empty:false,onshow:"请选择品种",onfocus:"品种必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择品种"});
			$("#dictCommentType").formValidator({empty:false,onshow:"请选择评别",onfocus:"评别必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择评别"});
			$("#chiefTitle").formValidator({onshow:"请输入主标题",onfocus:"主标题不能为空",oncorrect:"主标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主标题两边不能有空符号"},onerror:"主标题不能为空"});
			$("#summary").formValidator({onshow:"请输入摘要信息",onfocus:"摘要信息不能为空",oncorrect:"摘要信息合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"摘要信息两边不能有空符号"},onerror:"摘要信息不能为空"});		
<%--			正文--%>
			
		})
   		</logic:equal>
   		<logic:equal name="opertype" value="update">
   		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#frontFor").formValidator({onshow:"请输入主办方",onfocus:"主办方不能为空",oncorrect:"主办方合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主办方两边不能有空符号"},onerror:"主办方不能为空"});
			$("#underTake").formValidator({onshow:"请输入承办方",onfocus:"承办方不能为空",oncorrect:"承办方合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"承办方两边不能有空符号"},onerror:"承办方不能为空"});
			$("#supportTel").formValidator({onshow:"请输入支持热线",onfocus:"支持热线不能为空",oncorrect:"支持热线合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"支持热线两边不能有空符号"},onerror:"支持热线不能为空"});
			$("#supportSite").formValidator({onshow:"请输入支持网站",onfocus:"支持网站不能为空",oncorrect:"支持网站合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"支持网站两边不能有空符号"},onerror:"支持网站不能为空"});
			$("#contactMail").formValidator({onshow:"请输入联系邮箱",onfocus:"联系邮箱不能为空",oncorrect:"联系邮箱合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系邮箱两边不能有空符号"},onerror:"联系邮箱不能为空"});
			$("#chiefEditor").formValidator({onshow:"请输入主编",onfocus:"主编不能为空",oncorrect:"主编合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主编两边不能有空符号"},onerror:"主编不能为空"});
			$("#subEditor").formValidator({onshow:"请输入副主编",onfocus:"副主编不能为空",oncorrect:"副主编合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"副主编两边不能有空符号"},onerror:"副主编不能为空"});			
			$("#firstEditor").formValidator({onshow:"请输入首席编辑",onfocus:"首席编辑不能为空",oncorrect:"首席编辑合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"首席编辑两边不能有空符号"},onerror:"首席编辑不能为空"});
			$("#chargeEditor").formValidator({onshow:"请输入责任编辑",onfocus:"责任编辑不能为空",oncorrect:"责任编辑合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"责任编辑两边不能有空符号"},onerror:"责任编辑不能为空"});
			$("#period").formValidator({onshow:"请输入期第",onfocus:"期第不能为空",oncorrect:"期第合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"期第两边不能有空符号"},onerror:"期第不能为空"});
			$("#createTime").formValidator({onshow:"请输入出刊时间",onfocus:"出刊时间不能为空",oncorrect:"出刊时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"出刊时间两边不能有空符号"},onerror:"出刊时间不能为空"});
			$("#sendUnit").formValidator({onshow:"请输入报送单位",onfocus:"报送单位不能为空",oncorrect:"报送单位合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"报送单位两边不能有空符号"},onerror:"报送单位不能为空"});
			$("#dictProductType").formValidator({empty:false,onshow:"请选择品种",onfocus:"品种必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择品种"});
			$("#dictCommentType").formValidator({empty:false,onshow:"请选择评别",onfocus:"评别必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择评别"});
			$("#chiefTitle").formValidator({onshow:"请输入主标题",onfocus:"主标题不能为空",oncorrect:"主标题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"主标题两边不能有空符号"},onerror:"主标题不能为空"});
			$("#summary").formValidator({onshow:"请输入摘要信息",onfocus:"摘要信息不能为空",oncorrect:"摘要信息合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"摘要信息两边不能有空符号"},onerror:"摘要信息不能为空"});		
<%--			正文--%>
		})
   		</logic:equal>
		
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
        	if (!checkNotNull(addstaffer.frontFor,"主办方")) return false; 
        	
        	if (!checkNotNull(addstaffer.underTake,"承办方")) return false;
        	if (!checkNotNull(addstaffer.chiefTitle,"主标题")) return false;
        	if (!checkNotNull(addstaffer.summary,"摘要")) return false;
        	if (!checkTelNumber(addstaffer.supportTel)) return false;
			if (!checkEmail(addstaffer.contactMail)) return false;

           return true;
        }
				function add()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
    	    			document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=insert";
			 			document.forms[0].submit();
			 		}
				}
				function update()
				{
					var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function del()
				{
			 		document.forms[0].action="../markanainfo.do?method=toMarkanainfo&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
			</script>

</head>

<body onunload="toback()" onload="init()" class="loadBody">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/markanainfo.do" method="post" styleId="formValidate">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				当前位置&ndash;&gt;
				<span id="spanHead">详细</span>
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
<%
String id2 = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("markanainfoBean")).get("markanaId");
	String p2 = "../upload/ico.jsp?t=oper_markanainfo.markana_id&id=" + id2;
			%>
			<tr>
				<td class="valueStyle" rowspan="4">
					<img alt="产品标识" width="90" height="60" src='<jsp:include flush="true" page="<%=p2 %>"/>'>
				</td>
				<td class="labelStyle">
					主&nbsp;办&nbsp;方
				</td>
				<td class="valueStyle" colspan="3">
				<logic:notEmpty name="markanainfoBean" property="frontFor">
					<html:text property="frontFor" styleClass="writeTextStyle" size="35" styleId="frontFor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="frontFor">
					<html:text property="frontFor" styleClass="writeTextStyle" size="35" styleId="frontFor" value="辽宁省农村经济委员会信息中心"/>
				</logic:empty>				
					<span id="frontForTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					承&nbsp;办&nbsp;方
				</td>
				<td class="valueStyle" colspan="3">
				<logic:notEmpty name="markanainfoBean" property="underTake">
					<html:text property="underTake" styleClass="writeTextStyle" size="35" styleId="underTake" />
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="underTake">
					<html:text property="underTake" styleClass="writeTextStyle" size="35" styleId="underTake" value="12316金农热线"/>
				</logic:empty>
					<span id="underTakeTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					支持热线
				</td>
				<td class="valueStyle">
				<logic:notEmpty name="markanainfoBean" property="supportTel">
					<html:text property="supportTel" styleClass="writeTextStyle" styleId="supportTel"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="supportTel">
					<html:text property="supportTel" styleClass="writeTextStyle" styleId="supportTel" value="12316"/>
				</logic:empty>				
					<span id="supportTelTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					支持网站
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="supportSite">
					<html:text property="supportSite" styleClass="writeTextStyle" styleId="supportSite"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="supportSite">
					<html:text property="supportSite" styleClass="writeTextStyle" styleId="supportSite" value="http://www.jinnong.cc/"/>
				</logic:empty>				
					<span id="supportSiteTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					联系邮箱
				</td>
				<td class="valueStyle" colspan="3">					
					<logic:notEmpty name="markanainfoBean" property="contactMail">
					<html:text property="contactMail" styleClass="writeTextStyle" styleId="contactMail"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="contactMail">
					<html:text property="contactMail" styleClass="writeTextStyle" styleId="contactMail" value="yangbo--119119@163.com"/>
				</logic:empty>				
					<span id="contactMailTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					主&nbsp;&nbsp;&nbsp;&nbsp;编
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="chiefEditor">
					<html:text property="chiefEditor" styleClass="writeTextStyle" styleId="chiefEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="chiefEditor">
					<html:text property="chiefEditor" styleClass="writeTextStyle" styleId="chiefEditor" value="牟恩东"/>
				</logic:empty>				
					<span id="chiefEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					副&nbsp;主&nbsp;编
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="subEditor">
					<html:text property="subEditor" styleClass="writeTextStyle" styleId="subEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="subEditor">
					<html:text property="subEditor" styleClass="writeTextStyle" styleId="subEditor" value="孙继锋"/>
				</logic:empty>				
					<span id="subEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					首席编辑
				</td>
				<td class="valueStyle">
					<logic:notEmpty name="markanainfoBean" property="firstEditor">
					<html:text property="firstEditor" styleClass="writeTextStyle" styleId="firstEditor"/>
				</logic:notEmpty>
				<logic:empty name="markanainfoBean" property="firstEditor">
					<html:text property="firstEditor" styleClass="writeTextStyle" styleId="firstEditor" value="杨波"/>
				</logic:empty>				
					<span id="firstEditorTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					责任编辑
				</td>
				<td class="valueStyle">
					<html:text property="chargeEditor" styleClass="writeTextStyle" styleId="chargeEditor"/>
					<span id="chargeEditorTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					期&nbsp;&nbsp;&nbsp;&nbsp;第
				</td>
				<td class="valueStyle">
					<html:text property="period" styleClass="writeTextStyle" styleId="period"/>
					<span id="periodTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					出刊时间
				</td>
				<td class="valueStyle">
					<html:text property="createTime" styleClass="writeTextStyle" styleId="createTime"/>					
					<img alt="选择时间" src="../html/img/cal.gif" onclick="openCal('markanainfoBean','createTime',false);">
					<span id="createTimeTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					报送单位
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="sendUnit" size="20" styleClass="writeTextStyle" styleId="sendUnit"/>
					<span id="sendUnitTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					品&nbsp;&nbsp;&nbsp;&nbsp;种
				</td>
				<td class="valueStyle" colspan="4">
					<html:select property="dictProductType" styleId="dictProductType" styleClass="selectStyle">
							<option value="">请选择</option>
							<html:options collection="list" property="value" labelProperty="label" />
					</html:select>
					<span id="dictProductTypeTip" style="width: 0px;display:inline;"></span>
<%--					<select name="dictProductType1" class="selectStyle"--%>
<%--						onChange="select1(this)">--%>
<%--						<OPTION value="">--%>
<%--							请选择大类--%>
<%--						</OPTION>--%>
<%--						<jsp:include flush="true" page="../priceinfo/select_product.jsp" />--%>
<%--					</select>--%>
<%--					<span id="dict_product_type2_span"> <select--%>
<%--							name="dictProductType2" class="selectStyle"--%>
<%--							onChange="select1(this)">--%>
<%--							<OPTION value="">--%>
<%--								请选择小类--%>
<%--							</OPTION>--%>
<%--						</select>--%>
<%--					</span>--%>
<%--					<span id="product_name_span"> --%>
<%--						<logic:empty name="markanainfoBean" property="dictProductType">--%>
<%--							<select name="dictProductType" class="selectStyle" style="width:70px">--%>
<%--								<OPTION value="">请选择名称</OPTION>--%>
<%--							</select>--%>
<%--						</logic:empty>--%>
<%--						<logic:notEmpty name="markanainfoBean" property="dictProductType">--%>
<%--								<html:text property="dictProductType" styleClass="writeTextStyle" />--%>
<%--						</logic:notEmpty>--%>
<%--					</span>--%>
				</td>
				<td class="labelStyle">
					评&nbsp;&nbsp;&nbsp;&nbsp;别
				</td>
				<td class="valueStyle">
					<html:select property="dictCommentType" styleClass="selectStyle" styleId="dictCommentType">
						<html:option value="">请选择</html:option>
						<html:option value="周评">周评</html:option>
						<html:option value="月评">月评</html:option>
						<html:option value="季评">季评</html:option>
						<html:option value="年评">年评</html:option>
					</html:select>
					<span id="dictCommentTypeTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<jsp:include flush="true" page="../flow/incState.jsp?form=markanainfoBean"/>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					主&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="4">
					<html:text property="chiefTitle" styleClass="writeTextStyle"
						size="41" styleId="chiefTitle" />
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<span id="chiefTitleTip" style="width: 0px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					副&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="3">
					<html:text property="subTitle" styleClass="writeTextStyle"
						size="35" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle" colspan="8">
					<html:text property="summary" styleClass="writeTextStyle" size="124" styleId="summary" />
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<span id="summaryTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					正&nbsp;&nbsp;&nbsp;&nbsp;文
				</td>
				<td colspan="8" class="valueStyle" style="font-color:#000000">
					<FCK:editor instanceName="markanaContent">
						<jsp:attribute name="value">
							<bean:write name="markanainfoBean" property="markanaContent"
								filter="false" />
						</jsp:attribute>
					</FCK:editor>
					<span id="markanaContentTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>


			<tr>
				<td class="labelStyle">
					一审建议
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise1" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
				<td class="labelStyle" rowspan="3">
				  <logic:equal name="opertype" value="insert">
					上传标识：<br />
					可以上传多个不同格式的符件<br />
					以其中最后一个图片作为标识！
				  </logic:equal>
				  <logic:equal name="opertype" value="update">
					上传标识：<br />
					可以上传多个不同格式的符件<br />
					以其中最后一个图片作为标识！
				  </logic:equal>
				</td>
				<td class="labelStyle" rowspan="3" colspan="2" width="180"
					style="text-indent: 0px;">
				  <logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
				  </logic:equal>
				  <logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
				  </logic:equal>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					二审建议
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise2" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					三审建议
				</td>
				<td class="valueStyle" colspan="5">
					<html:textarea property="checkAdvise3" styleClass="writeTextStyle"
						rows="2" cols="80" />
				</td>
			</tr>

			<tr>
				<td colspan="9" align="center" class="buttonAreaStyle">
				<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="详细"  class="buttonStyle"/>
				</logic:present>
				
<%--					<logic:equal name="opertype" value="insert">--%>
<%--						<input type="button" name="btnAdd"   value="添加"--%>
<%--							onclick="add()" class="buttonStyle" />--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="update">--%>
<%--						<input type="button" name="btnUpdate"  --%>
<%--							value="确定" onclick="update()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>
<%--					<logic:equal name="opertype" value="delete">--%>
<%--						<input type="button" name="btnDelete"  --%>
<%--							value="删除" onclick="del()" class="buttonStyle"/>--%>
<%--					</logic:equal>--%>

					<input type="button" name="" value="关闭"  
						onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
			<html:hidden property="markanaId" />
		</table>
		<%
	String id = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute("markanainfoBean")).get("markanaId");
	String p = "../upload/show.jsp?t=oper_markanainfo.markana_id&id=" + id;
	%>
	<jsp:include flush="true" page="<%= p %>"/>
	</html:form>

</body>
</html:html>
<script>

	function select1(obj){
		var sid = obj.name;
		var svalue = obj.options[obj.selectedIndex].text;
		if(svalue == "")
			return;
		if(sid == "dictProductType1"){
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("../priceinfo/select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
		}
	}
	
	function getAccid(v){
		sendRequest("../focusPursue/getAccid.jsp", "state="+v);
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


		function processResponse1() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("dict_product_type2_span").innerHTML = "<select name='dictProductType2' class='selectStyle'  onChange='select1(this)'><OPTION value=''>请选择小类</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("product_name_span").innerHTML = "<select name='dictProductType' class='selectStyle'><OPTION  value=''>请选择名称</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	

</script>
