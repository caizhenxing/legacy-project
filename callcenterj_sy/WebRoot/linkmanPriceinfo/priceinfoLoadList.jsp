<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html locale="true">
<head>
	<html:base />

	<title>联络员报价录入</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	
	<SCRIPT language=javascript src="../js/calendar3.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../js/form.js" type=text/javascript> </SCRIPT>
   	
   	<!-- jquery验证 -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
   	
   	<script type="text/javascript">	
		//执行验证
		$(document).ready(function(){			
			$.formValidator.initConfig({formid:"PriceInfo",onerror:function(msg){alert(msg)}});
			$("#priceRid").formValidator({onshow:"请输入受理工号",onfocus:"受理工号不能为空",oncorrect:"受理工号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"受理工号两边不能有空符号"},onerror:"受理工号不能为空"});
			$("#deployTime").formValidator({onshow:"请输入时间",onfocus:"时间不能为空",oncorrect:"时间合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"时间两边不能有空符号"},onerror:"时间不能为空"});
			$("#custName").formValidator({onshow:"请输入用户姓名",onfocus:"用户姓名不能为空",oncorrect:"用户姓名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户姓名两边不能有空符号"},onerror:"用户姓名不能为空"});
		})
		
		function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].custName;
			arrparm[1] = document.forms[0].cust_id;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/linkmanpriceinfo.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }		
	</script>	
   		
<%--	<script type="text/javascript">--%>
<%--		function checkForm(priceinfoBean){--%>
<%--            if (!checkNotNull(priceinfoBean.custAddr,"地址")) return false;--%>
<%--            return true;--%>
<%--   	}--%>
<%--	</script>--%>
</head>

<body class="loadBody" onload="deployTimeDate()">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); 
	window.close();
	</script>
	</logic:notEmpty>
<%--onsubmit="return checkForm(this)"--%>
	<html:form action="linkmanpriceinfo.do?method=toOperPriceinfo&type=insertList" method="post" styleId="PriceInfo">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					联络员报价录入
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable" id="table">
			<tr>
				<td class="valueStyle" colspan="2">
					受理工号
					<html:text property="priceRid" styleClass="writeTextStyle"
						size="10" readonly="true" styleId="priceRid"/>
					<span id="priceRidTip" style="width: 10px;display:inline;"></span>
				</td>
				<td class="valueStyle" colspan="2">
					时间
					<html:text property="deployTime" styleClass="writeTextStyle"
						size="10" styleId="deployTime"/>					
					<img alt="选择时间" src="../html/img/cal.gif"
						onclick="openCal('linkmanpriceinfoBean','deployTime',false);">
					<span id="deployTimeTip" style="width: 10px;display:inline;"></span>
				</td>
						
				<td class="valueStyle" colspan="3">
				    联络员
					<html:text property="custName" styleClass="writeTextStyle"
						style="width:87px" styleId="custName" readonly="true"/>
					<img  src="../style/<%=styleLocation%>/images/detail.gif" alt="选择联络员" onclick="dep()" width="16" height="16" border="0"/>
					<html:hidden property="cust_id" />
					<span id="custNameTip" style="width: 10px;display:inline;"></span>
				</td>
				</tr>
			<tr>
				<td class="valueStyle" colspan="7">
				地址
					<html:text property="custAddr" styleClass="writeTextStyle" size="35" styleId="custAddr" readonly="true"/>
					<input name="add" type="button" id="add" value="选择"   onClick="window.open('priceinfo/select_address.jsp','','width=500,height=140,status=no,resizable=yes,scrollbars=yes,top=200,left=400')">
					<span id="custAddrTip" style="width: 15px;display:inline;"></span> 
				</td>
			</tr>
			<tr>
				<td class="listTitleStyle">
					产品大类
				</td>
				<td class="listTitleStyle">
					产品小类
				</td>
				<td class="listTitleStyle">
					产品名称
				</td>
				<td class="listTitleStyle">
					价格类型
				</td>
				<td class="listTitleStyle">
					价格
				</td>
				<td class="listTitleStyle">
					当地情况及备注
				</td>
				<td class="listTitleStyle">
					<input name="add" type="button" id="add" value="添加"   onClick="addtr()">
				</td>
			</tr>
			<%
			for (int i = 0; i < 8; i++) {
			%>
			<tr>
				<td class="labelStyle">
					<select name="dict_product_type1" class="selectStyle" onChange="select1(this)">
						<OPTION value="">请选择大类</OPTION>
      				  	<jsp:include flush="true" page="select_product.jsp"/>
				     </select>
				</td>
				<td class="labelStyle" id="dict_product_type2_td_<%= i %>">
					<select name="dict_product_type2" class="selectStyle">
						<OPTION value="">请选择小类</OPTION>
					</select>
				</td>
				<td class="labelStyle" id="product_name_td_<%= i %>">
					<select name="product_name" class="selectStyle">
						<OPTION value="">请选择名称</OPTION>
					</select>
				</td>
				<td class="labelStyle">
					<html:select property="dict_price_type" styleClass="selectStyle">
						<html:options collection="priceList" property="value" labelProperty="label" />
					</html:select>
					
				</td>
				<td class="labelStyle">
					<input name="product_price" type="text" class="writeTextStyle" id="product_price" size="10">
				</td>
				<td class="labelStyle">
					<input name="remarkj" type="text" class="writeTextStyle" id="remarkj" size="25">
				</td>
				<td class="labelStyle">
					
				</td>
			</tr>
			<%
			}
			%>
			<tr>
				<td colspan="7" align="center" class="buttonAreaStyle">
					<font color="#000000"><logic:notEmpty name="addok"><bean:write name="addok"/></logic:notEmpty></font>
					<input type="submit" name="btnAdd"   value="保存">
					<input type="button" value="关闭" onClick="javascript:window.close();"/>
				</td>
			</tr>
		</table>

	</html:form>
</body>
</html:html>
<%
java.util.List priceList = (java.util.List)request.getAttribute("priceList");
String scriptStr = "";
for(int i = 0; i<priceList.size(); i++){
	excellence.common.tools.LabelValueBean lvb	= (excellence.common.tools.LabelValueBean)priceList.get(i);
	scriptStr += "<OPTION value='"+ lvb.getValue() +"'>"+ lvb.getLabel() +"</OPTION>";
}

%>
<script>

	var selectStr = "<%= scriptStr %>";
	
	function deployTimeDate(){
		var date=new Date();
		document.getElementById("deployTime").value=date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDate(); 
	}

	function select1(obj){
		var ssid = obj.name;
		var ssvalue = obj.options[obj.selectedIndex].text;
		var rowindex = obj.parentNode.parentNode.rowIndex-3;//要改变的是哪行的
		if(ssvalue == "")
			return;
		if(ssid == "dict_product_type1"){
			sendRequest("select_product.jsp", "ssvalue="+ssvalue+"&ssid="+ssid, processResponse1);
			this.producttd = "dict_product_type2_td_" + rowindex;
		}else if(ssid == "dict_product_type2"){
			sendRequest("select_product.jsp", "ssvalue="+ssvalue+"&ssid="+ssid, processResponse2);
			this.producttd = "product_name_td_" + rowindex;
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
				document.getElementById(producttd).innerHTML = "<select name='dict_product_type2' class='selectStyle'  onChange='select1(this)'><OPTION>请选择小类</OPTION>"+res+"</select>";
                
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
				document.getElementById(producttd).innerHTML = "<select name='product_name' class='selectStyle'><OPTION>请选择名称</OPTION>"+res+"</select>";
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }
	}
	
	function addtr(){
		var table = document.getElementById("table");
		var rowindex = table.rows.length-1;
		var tdindex = rowindex-2;
		tr = table.insertRow(rowindex);
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<select name="dict_product_type1" class="selectStyle" onChange="select1(this)"><option value="">请选择大类</option><option value="花卉">花卉</option><option value="家禽">家禽</option><option value="家畜">家畜</option><option value="经济作物">经济作物</option><option value="粮食作物">粮食作物</option><option value="生产资料">生产资料</option><option value="食用菌">食用菌</option><option value="蔬菜">蔬菜</option><option value="水产类">水产类</option><option value="水果">水果</option><option value="特种养殖">特种养殖</option><option value="药材">药材</option><option value="油料作物">油料作物</option></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "dict_product_type2_td_"+tdindex;
		td.innerHTML = '<select name="dict_product_type2" class="selectStyle"  onChange="select1(this)"><OPTION value="">请选择小类</OPTION></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.id = "product_name_td_"+tdindex;
		td.innerHTML = '<select name="product_name" class="selectStyle"><OPTION value="">请选择名称</OPTION></select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<select name="dict_price_type" class="selectStyle">'+ selectStr +'</select>';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<input name="product_price" type="text" class="writeTextStyle" id="product_price" size="10">';
		td = tr.insertCell();
		td.className = "labelStyle";
		td.innerHTML = '<input name="remarkj" type="text" class="writeTextStyle" id="remarkj" size="25">';
		td = tr.insertCell();
		td.className = "listTitleStyle";
		td.innerHTML = '<input name="del" type="button" id="del" value="删除" onClick="deltr(this)">';
	}
	function deltr(obj){
		document.getElementById("table").deleteRow(obj.parentNode.parentNode.rowIndex);
	}

</script>

