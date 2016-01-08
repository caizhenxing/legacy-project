<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<html:base />
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			var address=document.forms[0].address.value;			
<%--			if(address==null||address==""){--%>
<%--				alert("地址不能为空！")--%>
<%--				return false;--%>
<%--			}--%>
			document.forms[0].action="../stat/priceStatForProduct.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/priceStatForProduct.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
			var time = new Date();
			document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
			time.setYear(time.getYear()-1);
			document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
		}
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
				//window.alert(res); sdfsf
				document.getElementById("product_name_span").innerHTML = "<select name='productName' class='selectStyle'><OPTION  value=''>请选择名称</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
		
		</script>
	</head>
	<body class="conditionBody" onload="setTime()">
		<html:form action="/stat/priceStatForProduct" method="post"
			target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;产地产品最值/均值统计
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('priceStatForProductForm','beginTime',false);"
							size="10" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('priceStatForProductForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						产品大类
					</td>
					<td class="valueStyle">
						<select name="dictProductType1" class="selectStyle"
							onChange="select1(this)">
							<OPTION value="">
								请选择大类
							</OPTION>
							<jsp:include flush="true" page="select_product.jsp" />
						</select>
						<span id="dict_product_type2_span"> <select
								name="dictProductType2" class="selectStyle"
								onChange="select1(this)">
								<OPTION value="">
									请选择小类
								</OPTION>
							</select> </span>
						<span id="product_name_span"> <select name="productName"
								class="selectStyle">
								<OPTION value="">
									请选择名称
								</OPTION>
							</select> </span>

					</td>
					<td class="LabelStyle">
						生成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle"
							style="width:80px">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()"
							styleClass="checkBoxStyle">3D图像</html:checkbox>
					</td>
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch"  class="buttonStyle" value="统计"
							onclick="dodisplay()" />

					</td>
				</tr>

				<tr>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('priceStatForProductForm','endTime',false);"
							size="10" />
						<img alt="选择时间" src="../html/img/cal.gif"
							onclick="openCal('priceStatForProductForm','endTime',false);">
					</td>
					<td class="LabelStyle">
						地&nbsp;&nbsp;&nbsp;&nbsp;址
					</td>
					<td class="valueStyle" >
						<html:text property="address" size="30"
							styleClass="writeTextStyle" />
						<input type="button" value="选择地址"
							onclick="window.open('select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')" />
<%--						<font color="red">*</font>--%>
					</td>
					<td class="LabelStyle">
						价格类型
					</td>
					<td class="valueStyle" >						
						<html:select property="productType" styleClass="selectStyle">
							<html:option value="">请选择</html:option>
							<html:option value="SYS_TREE_0000000627">收购价</html:option>
							<html:option value="SYS_TREE_0000000628">批发价</html:option>
							<html:option value="SYS_TREE_0000000629">零售价</html:option>
						</html:select>
					</td>
					<td class="LabelStyle" align="center">
						<input class="buttonStyle" type="reset" value="刷新">
					</td>
				</tr>

				<tr height="1px">
					<td colspan="10" class="buttonAreaStyle">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
<%--<script>
	
	function selecttype(obj){
		var svalue = obj.options[obj.selectedIndex].text;
		var name = obj.name;
		if(svalue != "请选择大类"){
			sendRequest("../custinfo/select_type.jsp", "svalue="+svalue, processResponse2);
		}else{
			document.getElementById("caseAttr2_td").innerHTML = '<input type="text" name="caseAttr2" value="" class="writeTextStyle">';
		}
	}
	
	function getAccid(v){
		sendRequest("../../focusPursue/getAccid.jsp", "state="+v, processResponse);
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
	
	function processResponse2() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById("caseAttr2_td").innerHTML = "<select name='caseAttr2' class='selectStyle'>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}

</script>--%>
