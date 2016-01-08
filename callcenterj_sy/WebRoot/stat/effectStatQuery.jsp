<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
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
			document.forms[0].action="../stat/effectStat.do?method=toDisplay"
			document.forms[0].submit();
		}
		function dodisplay3d(){
			if(document.forms[0].chartType!=""){
			setSameDateTime(document.forms[0].beginTime,document.forms[0].endTime);
				document.forms[0].action="../stat/effectStat.do?method=toDisplay";
				document.forms[0].submit();
			}
		}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.format('yyyy-MM-dd');//time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		function time(){
				var time=new Date();
				document.forms[0].endTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				time.setYear(time.getYear()-1)
				document.forms[0].beginTime.value=time.getYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()
				
			}
		</script>
	</head>
	<body class="conditionBody" onload="time()">
		<html:form action="/stat/effectCaseInfoProperty" method="post" target="bottomm">

			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle"> 
						当前位置&ndash;&gt;座席审核量统计
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
<%--				</td></tr>--%>
				
					<tr>
					<td class="LabelStyle">
						开始时间
					</td>
					<td class="valueStyle">
						<html:text property="beginTime" styleClass="writeTextStyle2"
							onclick="openCal('effectCaseInfoPropertyForm','beginTime',false);" />
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('effectCaseInfoPropertyForm','beginTime',false);">
					</td>
					<td class="LabelStyle">
						截止时间
					</td>
					<td class="valueStyle">
						<html:text property="endTime" styleClass="writeTextStyle2"
							onclick="openCal('effectCaseInfoPropertyForm','endTime',false);"/>
						<img alt="选择时间" src="../html/img/cal.gif"
					onclick="openCal('effectCaseInfoPropertyForm','endTime',false);">
					</td>
					<td class="LabelStyle">
<%--						座席工号--%>
						受理工号
					</td>
					<td class="valueStyle">
						<html:select property="agentNum">
							<option value="">请选择</option>
							<logic:iterate id="u" name="user">
								<html:option value="${u.userId}">${u.userId}</html:option>						
							</logic:iterate>
						</html:select>
					</td>
				
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

</script>