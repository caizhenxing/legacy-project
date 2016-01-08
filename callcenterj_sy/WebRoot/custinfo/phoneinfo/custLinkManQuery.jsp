<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>
 
<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar3.js" type=text/javascript></SCRIPT>
	<script language="javascript">
    	//查询
    	function query(){
    		document.forms[0].action = "../phoneinfo.do?method=toLinkManList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}

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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
   	</script>
	<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/custinfo/phoneinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;联络员管理
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/quoteCircs/quoteCircs.do?method=toMain" target="contents"> 联络员报价统计</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/linkmanpriceinfo.do?method=toOperPriceinfoMain" target="contents">联络员报价浏览</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/event/event.do?method=toEventMain" target="contents">联络员事件管理</a>
				</td>
				<td class="valueStyle" align="center">
					<a href="/callcenterj_sy/eventResult/eventResult.do?method=toEventResultMain" target="contents">联络员事件反馈</a>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;联络员编号
				</td>
				<td class="valueStyle">
					<html:text property="cust_number" styleClass="writeTextStyle" size="14"/>
<%--					<img alt="选择日期" src="../../html/img/cal.gif"--%>
<%--						onclick="openCal('phoneinfo','cust_develop_time',false);">--%>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;用户姓名
				</td>
				<td class="valueStyle">
					<html:text property="user_name" styleClass="writeTextStyle" size="14"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;用户地址
				</td>
				<td class="valueStyle">
					<html:text property="cust_addr" styleClass="writeTextStyle" size="16"/>
						<input type="button" name="btnadd" class="buttonStyle" value="选择"
						onClick="window.open('../custinfo/phoneinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;用户类别
				</td>
				<td class="valueStyle">
					<html:select property="cust_way_by" style="width:100">
						<option value="">请选择</option>
						<option value="农业大户">农业大户</option>
						<option value="农业协会">农业协会</option>
						<option value="农产品经纪人">农产品经纪人</option>
						<option value="农资经销商">农资经销商</option>
						<option value="其他">其他</option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button" class="buttonStyle"
						value="查询" onclick="query()" />
<%--					<leafRight:btn name="btnSearch" nickName="phoneinfo_query" styleClass="buttonStyle" value="查询" onclick="query()" scopeName="userRoleLeafRightInsession" />--%>
<%--					<input type="reset"  class="buttonStyle" value="刷新"  >--%>
				</td>
			</tr>
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;用户电话
				</td>
				<td class="valueStyle">
					<html:text property="cust_tel_home" styleClass="writeTextStyle" size="14"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;受理工号
				</td>
				<td class="valueStyle">
					<html:select property="cust_rid" style="width:90">
					<option value="">请选择</option>
						<logic:iterate id="u" name="user">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
				<td class="queryLabelStyle">
					报价栏目
				</td>
				<td colspan="4" class="valueStyle">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)" style="width:110px">
						<OPTION value="">
							请选择大类
						</OPTION>
						<jsp:include flush="true" page="../select_product.jsp" />
					</select>
					<span id="dict_product_type2_span"> 
					<select	name="dictProductType2" class="selectStyle"
							onChange="select1(this)" style="width:110px">
							<OPTION value="">
								请选择小类
							</OPTION>
						</select> </span>
					<span id="product_name_span"> <select name="productName"
							class="selectStyle" style="width:110px">
							<OPTION value="">
								请选择名称
							</OPTION>
						</select> </span>
				</td>
				
			</tr>
			<tr>
				<td align="center" class="queryLabelStyle">
					&nbsp;工作方式
				</td>
				<td class="valueStyle">
					<html:select property="cust_work_way" style="width:90">
						<option value="">请选择</option>
						<option value="报价">报价</option>
						<option value="回访">回访</option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;淘汰与否
				</td>
				<td class="valueStyle">
					<html:select property="is_eliminate" style="width:90">
							<html:option value="">请选择</html:option>
							<html:option value="yes">是</html:option>
							<html:option value="no">否</html:option>
					</html:select>
				</td>
				<td class="queryLabelStyle">
					&nbsp;淘汰原因
				</td>
				<td class="valueStyle">
					<html:text property="eliminate_reason" size="16" styleClass="writeTextStyle" styleId="cust_tel_work"/>
				</td>
				<td align="center" class="queryLabelStyle">
					&nbsp;发展日期
				</td>
				<td class="valueStyle">
					<html:text property="cust_develop_time" styleClass="writeTextStyle" size="14"/>
					<img alt="选择日期" src="../../html/img/cal.gif"
						onclick="openCal('phoneinfo','cust_develop_time',false);">
				</td>
				<td align="center" class="queryLabelStyle">
<%--					<input name="btnSearch" type="button" class="buttonStyle"--%>
<%--						value="查询" onclick="query()" />--%>
<%--					<leafRight:btn name="btnSearch" nickName="phoneinfo_query" styleClass="buttonStyle" value="查询" onclick="query()" scopeName="userRoleLeafRightInsession" />--%>
					<input type="reset"  class="buttonStyle" value="刷新"  >
				</td>
			</tr>
		</table>
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
			sendRequest("../select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("../select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
			this.producttd = "product_name_span";
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
				document.getElementById("product_name_span").innerHTML = "<select name='productName' class='selectStyle'><OPTION  value=''>请选择名称</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>
