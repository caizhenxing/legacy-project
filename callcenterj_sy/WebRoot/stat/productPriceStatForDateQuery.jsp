<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp" %>
<html>
	<head>
	<html:base />
		<title></title>
		<link href="../style/<%=styleLocation %>/style.css" rel="stylesheet" type="text/css" />
		<script language=javascript src="../js/calendar3.js"></script>
		<script>
		function dodisplay(){
		setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
			document.forms[0].action="../stat/productPriceForDate.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/productPriceForDate.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function setTime(){
			var time = new Date();
			document.forms[0].endTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
			time.setYear(time.getYear()-1);
			document.forms[0].beginTime.value = time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();			
		}
		</script>
	</head>
	<body class="conditionBody" onload="setTime()">
		<html:form action="/stat/productPriceForDate" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						当前位置&ndash;&gt;时间产品最值/均值统计
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="conditionTable">

			
<%--				<tr class="conditionoddstyle"><td class="labelStyle">--%>
<%--					案例大类--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:select property="caseAttr1" styleClass="selectStyle"--%>
<%--						onchange="selecttype(this)">--%>
<%--						<html:option value="">请选择大类</html:option>--%>
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
<%--				</td>--%>
<%--				<td class="labelStyle">--%>
<%--					案例小类--%>
<%--				</td>--%>
<%--				<td class="valueStyle" id="caseAttr2_td">--%>
<%--					<html:text property="caseAttr2" styleClass="writeTextStyle" />--%>
<%--				</td>--%>
<%--				<td class="labelStyle">--%>
<%--					案例种类--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:select property="caseAttr3" styleClass="selectStyle">--%>
<%--						<html:option value="品种介绍">品种介绍</html:option>--%>
<%--						<html:option value="栽培管理">栽培管理</html:option>--%>
<%--						<html:option value="养殖管理">养殖管理</html:option>--%>
<%--						<html:option value="疫病防治">疫病防治</html:option>--%>
<%--						<html:option value="虫草防除">虫草防除</html:option>--%>
<%--						<html:option value="收获贮运">收获贮运</html:option>--%>
<%--						<html:option value="产品加工">产品加工</html:option>--%>
<%--						<html:option value="市场行情">市场行情</html:option>--%>
<%--						<html:option value="饲料配制">饲料配制</html:option>--%>
<%--						<html:option value="农资使用">农资使用</html:option>--%>
<%--						<html:option value="设施修建">设施修建</html:option>--%>
<%--						<html:option value="政策管理">政策管理</html:option>--%>
<%--						<html:option value="其他综合">其他综合</html:option>--%>
<%--					</html:select>--%>
<%--				</td>--%>
<%--				<td class="LabelStyle" align="center">--%>
<%--						<input type="button" name="btnSearch" value="查询"  --%>
<%--							onclick="dodisplay()" />--%>
<%--				</td>	</tr>--%>
			
					<tr>
					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('productPriceForDateForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('productPriceForDateForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('productPriceForDateForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('productPriceForDateForm','endTime',false);">
					</td>
<%--					<td class="LabelStyle">--%>
<%--						产品名称--%>
<%--					</td>--%>
<%--					<td class="valueStyle">--%>
<%--						<html:select property="productName" styleClass="selectStyle">--%>
<%--							<html:option value="">请选择</html:option>--%>
<%--							<html:option value="棉花">棉花</html:option>--%>
<%--							<html:option value="小麦">小麦</html:option>--%>
<%--							<html:option value="芝麻">芝麻</html:option>--%>
<%--						</html:select>--%>
<%--					</td>--%>
				
					<td class="LabelStyle">
						生成
					</td>
					<td class="valueStyle">
						<html:select property="chartType" styleClass="selectStyle">
							<html:option value="">表格</html:option>
							<html:option value="bar">柱图</html:option>
							<html:option value="pie">饼图</html:option>
							<html:option value="line">曲线图</html:option>
						</html:select>
						<html:checkbox property="is3d" onclick="dodisplay3d()" styleClass="checkBoxStyle">3D图像</html:checkbox>
					</td>
					
					<td class="LabelStyle" align="center">
						<input type="button" name="btnSearch" value="统计"  
							onclick="dodisplay()" />
<%--						<input type="reset" value="刷新"  >--%>
					</td>
				</tr>
				<tr>
					<td class="LabelStyle">
						产品名称
					</td>
					<td colspan="5" class="valueStyle">
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
				<td class="LabelStyle" align="center">
					<input type="reset" value="刷新"  >
				</td>
				</tr>
				<tr height="1px">
					<td colspan="9" class="buttonAreaStyle">
					</td>				
				</tr>
			</table>
		</html:form>
	</body>
</html>
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