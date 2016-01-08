<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>市场供求操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript>
</SCRIPT>

	<script language="javascript" src="../js/common.js"></script>
	<script language="javascript" src="../js/clockCN.js"></script>
	<script language="javascript" src="../js/clock.js"></script>

	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
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
			<logic:equal name="opertype" value="detail">
			document.getElementById('btnOper').style.display="none";
			</logic:equal>
			<logic:equal name="opertype" value="insert">
    				document.getElementById('spanHead').innerHTML="添加信息";
					document.forms[0].action = "../sad.do?method=toSadOper&type=insert";
					document.getElementById('btnOper').value="添加";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="修改信息";
					document.forms[0].action = "../sad.do?method=toSadOper&type=update";
					document.getElementById('btnOper').value="修改";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="删除信息";
					document.forms[0].action = "../sad.do?method=toSadOper&type=delete";
					document.getElementById('btnOper').value="删除";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		
		//执行验证
		<logic:equal name="opertype" value="insert">
 		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#sadRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#dictSadType").formValidator({empty:false,onshow:"请选择供求类型",onfocus:"供求类型必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择供求类型"});			
			$("#sadTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#custName").formValidator({onshow:"请输入联系人",onfocus:"联系人不能为空",oncorrect:"联系人合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人两边不能有空符号"},onerror:"联系人不能为空"});
			$("#productName").formValidator({onshow:"请输入产品名称",onfocus:"产品名称不能为空",oncorrect:"产品名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品名称两边不能有空符号"},onerror:"产品名称不能为空"});
<%--			$("#deployBegin").formValidator({onshow:"请输入开始日期",onfocus:"开始日期不能为空",oncorrect:"开始日期合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"开始日期两边不能有空符号"},onerror:"开始日期不能为空"});--%>
			$("#custTel").formValidator({onshow:"请输入联系电话",onfocus:"联系电话不能为空",oncorrect:"联系电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系电话两边不能有空符号"},onerror:"联系电话不能为空"});	
			$("#productCount").formValidator({onshow:"请输入产品数量",onfocus:"产品数量不能为空",oncorrect:"产品数量合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品数量两边不能有空符号"},onerror:"产品数量不能为空"});
<%--			$("#deployEnd").formValidator({onshow:"请输入截止日期",onfocus:"截止日期不能为空",oncorrect:"截止日期合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"截止日期两边不能有空符号"},onerror:"截止日期不能为空"}).CompareValidator({desID:"deployBegin",operateor:">"});--%>
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#sadRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#dictSadType").formValidator({empty:false,onshow:"请选择供求类型",onfocus:"供求类型必须选择",oncorrect:"OK!"}).inputValidator({empty:false,min:1,onerror: "没有选择供求类型"});			
			$("#sadTime").formValidator({onshow:"请输入受理时间",onfocus:"受理时间不能为空",oncorrect:"受理时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理时间两边不能有空符号"},onerror:"受理时间不能为空"});
			$("#custName").formValidator({onshow:"请输入联系人",onfocus:"联系人不能为空",oncorrect:"联系人合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系人两边不能有空符号"},onerror:"联系人不能为空"});
			$("#productName").formValidator({onshow:"请输入产品名称",onfocus:"产品名称不能为空",oncorrect:"产品名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品名称两边不能有空符号"},onerror:"产品名称不能为空"});
<%--			$("#deployBegin").formValidator({onshow:"请输入开始日期",onfocus:"开始日期不能为空",oncorrect:"开始日期合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"开始日期两边不能有空符号"},onerror:"开始日期不能为空"});--%>
			$("#custTel").formValidator({onshow:"请输入联系电话",onfocus:"联系电话不能为空",oncorrect:"联系电话合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"联系电话两边不能有空符号"},onerror:"联系电话不能为空"});	
			$("#productCount").formValidator({onshow:"请输入产品数量",onfocus:"产品数量不能为空",oncorrect:"产品数量合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"产品数量两边不能有空符号"},onerror:"产品数量不能为空"});
<%--			$("#deployEnd").formValidator({onshow:"请输入截止日期",onfocus:"截止日期不能为空",oncorrect:"截止日期合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"截止日期两边不能有空符号"},onerror:"截止日期不能为空"});--%>
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
			
			
			function checkTelNumber(theField) {
		 	var pattern = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)/;
		
		 	if(theField.value == "") return true;
		 	if (!pattern.test(theField.value)) {
		 		alert("请正确填写电话号码！");
		 		theField.focus();
		 		theField.select();
		 		return false;
		 	}
		
			return true;
			}
			
			function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.sadRid,"受理工号")) return false;
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
	       	if (!checkNotNull(addstaffer.custTel,"用户电话")) return false;
	   		if (!checkTelNumber(addstaffer.custTel)) return false;
           return true;
        }
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../sad.do?method=toSadOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function userupdate()
				{
					 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../sad.do?method=toSadOper&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function userdel()
				{
			 		document.forms[0].action="../sad.do?method=toSadOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{
			if(opener.parent.topp){
				//opener.parent.topp.document.all.btnSearch.click();
				opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;				
			}
			
		}
			</script>

</head>

<body onload="init()" onunload="toback()" class="loadBody" style="OVERFLOW: auto">

<logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
	</script>
</logic:notEmpty>

	<html:form action="/sad" method="post" styleId="formValidate">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" id="showInfoTxt">
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

		<table width="100%" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr><html:hidden property="sadId"/>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<html:text property="sadRid" styleClass="writeTextStyle" readonly="true" styleId="sadRid"/>
<%--					<font color="#ff0000">*</font>--%>
					<span id="sadRidTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					供求类型
				</td>
				<td class="valueStyle">
					<html:select property="dictSadType" styleClass="selectStyle" style="width:130px" styleId="dictSadType">
						<html:option value="">请选择</html:option>
						<html:options collection="sadTypeList" property="value" labelProperty="label" />
					</html:select>
					<div id="dictSadTypeTip" style="width:0px;display:inline;"></div>
				</td>
				<td class="labelStyle">
					受理日期
				</td>
				<td class="valueStyle">
					<html:text property="sadTime" styleClass="writeTextStyle" size="17" styleId="sadTime"/>
					<div id="sadTimeTip" style="width:0px;display:inline;"></div>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					联 系 人
				</td>
				<td class="valueStyle">
					<html:text property="custName" styleClass="writeTextStyle" styleId="custName"/>
					<span id="custNameTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					产品名称
				</td>
				<td class="valueStyle">
					<html:text property="productName" styleClass="writeTextStyle" styleId="productName"/>
					<span id="productNameTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					开始日期
				</td>
				<td class="valueStyle">
					<html:text property="deployBegin" styleClass="writeTextStyle" size="17" styleId="deployBegin"/>					
	    					<img alt="选择日期" src="../html/img/cal.gif"
						onclick="openCal('asdBean','deployBegin',false);">
					<span id="deployBeginTip" style="width: 15px;display:inline;"></span>
				</td>
			</tr>
			<tr align="left">
				<td class="labelStyle">
					联系电话
				</td>
				<td class="valueStyle">
					<html:text property="custTel" styleClass="writeTextStyle" styleId="custTel"/>
					<span id="custTelTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					产品数量
				</td>
				<td class="valueStyle">
					<html:text property="productCount" styleClass="writeTextStyle" size="20" styleId="productCount"/>
					<span id="productCountTip" style="width: 15px;display:inline;"></span>
				</td>
				<td class="labelStyle">
					截止日期
				</td>
				<td class="valueStyle">
					<html:text property="deployEnd" styleClass="writeTextStyle" size="17" styleId="deployEnd"/>					
	    					<img alt="选择日期" src="../html/img/cal.gif"
						onclick="openCal('asdBean','deployEnd',false);">
					<span id="deployEndTip" style="width: 15px;display:inline;"></span>
				</td>
			</tr>
			<tr align="left">				
				<td class="labelStyle">
					邮&nbsp;&nbsp;&nbsp;&nbsp;编
				</td>
				<td class="valueStyle">
					<html:text property="post" styleClass="writeTextStyle"/>&nbsp;
				</td>
				<td class="labelStyle">
					产品规格
				</td>
				<td class="valueStyle">
					<html:text property="productScale" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
				<logic:equal name="opertype" value="insert">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=insert"/>
				</logic:equal>
				<logic:equal name="opertype" value="detail">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=detail"/>
				</logic:equal>
				<logic:equal name="opertype" value="update">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=update"/>
				</logic:equal>
				<logic:equal name="opertype" value="delete">
				<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean&opertype=delete"/>
				</logic:equal>
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=asdBean"/>--%>
				</td>
			</tr>
			<tr align="center">
				<td class="labelStyle">
					联系地址
				</td>
				<td class="valueStyle" colspan="3" align="left">
					<html:text property="custAddr" styleClass="writeTextStyle" size="50" readonly="true"/>
					<input type="button" name="btnadd"   value="选择" onClick="window.open('sad/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"/>
				</td>
				<td class="labelStyle" rowspan="2" colspan="2" width="210" style="text-indent: 0px;">
					<logic:equal name="opertype" value="insert">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
					<logic:equal name="opertype" value="update">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
					</logic:equal>
<%--					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					备&nbsp;&nbsp;&nbsp;&nbsp;注
				</td>
				<td colspan="3" class="valueStyle" >
					<html:textarea property="remark" cols="55" rows="6" styleClass="writeTextStyle"/>
				</td>
			</tr>
			<tr>
    			<td colspan="6"  align="center" class="buttonAreaStyle">
    				<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="详细"  class="buttonStyle"/>
				</logic:present>
<%--    			<logic:equal name="opertype" value="insert">--%>
<%--    				<input type="button" name="btnadd"   value="添加" onclick="useradd()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="update">--%>
<%--    				<input type="button" name="btnupd"    value="确定" onclick="userupdate()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
<%--    			<logic:equal name="opertype" value="delete">--%>
<%--    				<input type="button" name="btndel"    value="删除" onclick="userdel()" class="buttonStyle"/>--%>
<%--    			</logic:equal>--%>
    			
    				<input type="button" name="" value="关闭"    onClick="javascript:window.close();" class="buttonStyle"/>
    			
    			</td>
    		</tr>
			<html:hidden property="sadId"/>
		</table>
		<%
		String id = (String)((excellence.framework.base.dto.IBaseDTO)request.getAttribute("asdBean")).get("sadId");
		String p = "../upload/show.jsp?t=oper_sadinfo.sad_id&id="+id + "&opertype="+request.getAttribute("opertype");
		%>
		<jsp:include flush="true" page="<%= p %>" />
	</html:form>

</body>
</html:html>
<script>

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

