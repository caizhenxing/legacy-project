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

	<title>农产品价格库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/common.js"></script>
	<script language="javascript" src="../js/clock.js"></script>
	<SCRIPT language=javascript src="../js/calendar3.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="../js/clockCN.js"></script>


	<script type="text/javascript">
 function add()
 	{
 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfoLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfoList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
 	}
	function goExcel()
	{
		document.forms[0].action="../operpriceinfo.do?method=toOperPriceinfoExcelList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
	}
 </script>
 <% 
 	String atype = request.getParameter("atype");
 	String curPosition = "农产品价格库";
 	if("dayOfPrice".equals(atype))
 	{
 		curPosition = "每日价格";
 	}
 %>
</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">
	<html:form action="/operpriceinfo" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;<%=curPosition %>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('priceinfoBean','beginTime',false);">
				</td>
				<td class="queryLabelStyle">
					产品价格
				</td>
				<td class="valueStyle">
					<html:text property="productPrice" styleClass="writeTextStyle2"
						style="width:70px" size="10" />
				</td>
				<td class="queryLabelStyle">
					产&nbsp;&nbsp;&nbsp;&nbsp;地
				</td>
				<td  class="valueStyle">
					<html:text property="custAddr" styleClass="writeTextStyle2"
						style="width:100px" size="50" readonly="true"/>
					<html:button property="button" value="选择" style="width:30px"
						styleClass="buttonStyle"
						onclick="window.open('priceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')"
						onmousemove="this.style.cursor='pointer';" />
				</td>
				<td class="queryLabelStyle">
					价格类型
				</td>
				<td class="valueStyle">
					<html:select property="dictPriceType" styleClass="selectStyle" style="width:80px">
						<html:option value="">
	    						请选择
	    					</html:option>
						<html:options collection="priceList" property="value"
							labelProperty="label" />
					</html:select>
				</td>
				<td class="labelStyle">
				工号
				</td>
				<td class="labelStyle" align="center">
							<html:select property="agent_id">
						<option value="">请选择</option>
						<logic:iterate id="u" name="userList">
							<html:option value="${u.userId}">${u.userId}</html:option>						
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"
						size="10" />
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('priceinfoBean','endTime',false);">
				</td>
				<td class="queryLabelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<select name="state" id="state" class="selectStyle" style="width:70px">
					<%
					String str_state = request.getParameter("state");
					if("wait".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option selected="selected">待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("back".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option selected="selected">驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}else if("pass".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option selected="selected">已审</option>
						<option>发布</option>
					<%
					}else if("issuance".equals(str_state)){
					%>
					<option value="">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option selected="selected">发布</option>
					<%
					}else{
					%>
					<option value="" selected="selected">全部</option>
						<option>原始</option>
						<option>待审</option>
						<option>驳回</option>
						<option>已审</option>
						<option>发布</option>
					<%
					}
					 %>

					</select>
				</td>
				<td class="queryLabelStyle">
					产&nbsp;&nbsp;&nbsp;&nbsp;品
				</td>
				<td colspan="3" class="valueStyle">
					<select name="dictProductType1" class="selectStyle"
						onChange="select1(this)" style="width:110px">
						<OPTION value="">
							请选择大类
						</OPTION>
						<jsp:include flush="true" page="select_product.jsp" />
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
				<td class="labelStyle" align="center" colspan="2">
					<input type="button" name="btnSearch" value="查询"  class="buttonStyle" onclick="query()" />
					<input type="button" name="btnSearch" value="导出"  class="buttonStyle" onclick="goExcel()" />
					<input type="reset"  class="buttonStyle" value="刷新"  >
				</td>
			</tr>
			<tr height="1px">
				<td colspan="11" class="navigateStyle">
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
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse1);
			this.producttd = "dict_product_type2_span";
		}else if(sid == "dictProductType2"){
			sendRequest("select_product.jsp", "svalue="+svalue+"&sid="+sid, processResponse2);
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