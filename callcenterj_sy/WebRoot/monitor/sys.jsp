<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.eclipse.swt.internal.extension.Extension" %>
<%@ page import="java.text.NumberFormat" %>
<%@ include file="../style.jsp"%>
<%
String b = request.getParameter("b");
if(b!=null){
	int usages = Extension.GetCpuUsages();				//CPU使用率
	int avail = Extension.GlobalMemoryStatus().dwAvailPhys / 1024;	//内存可用数
out.print(usages+","+avail);
}else{
	int total = Extension.GlobalMemoryStatus().dwTotalPhys / 1024;	//内存总数
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
</head>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<body class="listBody">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;系统监控
					</td>
				</tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
				<tr>
					<td class="labelStyle" width="10%">CPU使用率</td>
					<td class="valueStyle" width="10%"><span id="usages"></span></td>
					<td class="labelStyle" width="10%">内存可用数</td>
					<td class="valueStyle" width="10%"><span id="avail"></span>&nbsp;KB</td>
					<td class="labelStyle" width="10%">内存总数</td>
					<td class="valueStyle" width="10%"><span id="total"><%= total %></span>&nbsp;KB</td>
				
				</tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						硬盘信息
					</td>
				</tr>
		</table>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
  <tr>
    <td class="listTitleStyle">本地磁盘</td>
    <td class="listTitleStyle">总大小</td>
    <td class="listTitleStyle">可用空间</td>
  </tr>
<%
	NumberFormat nf = NumberFormat.getInstance();	//返回当前默认语言环境的通用数字格式
		nf.setMaximumFractionDigits(2);				//设置数的小数部分的最大为2位数
		nf.setMinimumFractionDigits(0);				//设置数的小数部分允许的最小位数
		
String[] drives = Extension.GetLogicalDrives();
for (int i = 0; i < drives.length; i++) {
	String disk = drives[i];				//取磁盘
	if(Extension.GetDriveType(disk) == 3){	//判断是否磁盘
		String diskTotal = nf.format(((float)Extension.GetDiskFreeSpace(disk).totalNumberOfBytes)/1024/1024/1024);
		String diskFree = nf.format(((float)Extension.GetDiskFreeSpace(disk).totalNumberOfFreeBytes)/1024/1024/1024);

	String style = i % 2 == 0 ? "oddStyle": "evenStyle";
%>
  <tr>
    <td >( <%= disk %> )</td>
    <td ><%= diskTotal %> GB</td>
    <td ><%= diskFree %> GB</td>
  </tr>
<%
	}
}
%>
</table>

</body>
</html>
<script>  
changesrc();
function changesrc(){
	sendRequest("?b=y",null);
	window.setTimeout(changesrc,1000);//设置屏幕刷时间间隔为2000毫秒，可以修改
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
            	var res = XMLHttpReq.responseText;
				//window.alert(res); 
				var str = res.split(",");
				document.getElementById("usages").innerHTML = str[0] + " %";
				document.getElementById("avail").innerHTML = str[1];
				     
            } else { //页面不正常
                //window.alert("您所请求的页面有异常。");
            }
        }
    }
</script>
<%
}
%>