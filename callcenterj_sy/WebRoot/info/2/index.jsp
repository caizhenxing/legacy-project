<%@ page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>信息推送</title>
<style type="text/css">

body {
	margin: 0px;
}
/* "农民页面" 眉头 */
.Farmer{
	background-image: url(images/Brow.jpg);
}
/* “专家页面”标题导航 */
.Navigation{
	width: 100%;
	text-align: center;
	background-color: #93B6DE;
}
/* 深蓝色文字，粗体，宋体 */
.Blue{
	font-family: "楷体_GB2312";
	font-size: 21px;
	font-weight: bolder;
	color: #003300;
	font-style: normal;
	text-align: center;
	vertical-align: bottom;
}
/* 蓝色文字，宋体，标题 */
.Darkblue
{
	font-size: 20px;
	font-weight: bolder;
	color: #003366;
	font-family: "楷体_GB2312";
	text-align: center;
	vertical-align: bottom;
}
/* 文本，短的 */
.Text{
	font-family: "宋体";
	background-color: #85AAD6;
	font-size: 22px;
	font-weight: bold;
	color: #FFFF99;
	text-indent: 5px;
	text-align: left;
	vertical-align: bottom;
}
/* 文本，长的 */
.Text2{
	font-family: "宋体";
	background-color: #85AAD6;
	font-size: 22px;
	font-weight: bold;
	color: #FFFF99;
	text-indent: 2px;
	text-align: left;
}
/* 数据显示 */
.Data{
	background-color: #ECECEC;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #85AAD6;
	font-family: "楷体_GB2312";
	font-size: 20px;
	line-height: 24px;
	font-weight: bold;
	color: #003366;
	text-align: center;
}
/* ”农民页面“眉头”div样式（时间） */
.Time{
	font-size: 15px;
	font-weight: bold;
	text-align: center;
	margin-left: 970px;
	margin-right: 5px;
	margin-top: 12px;
}
.STYLE1 {color: #003366; font-family: "楷体_GB2312"; text-align: center; vertical-align: bottom; font-weight: bolder;}

</style>
</head>

<body>
<table width="1280" border="0" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td height="90" colspan="8" valign="top" class="Farmer">
    	<div class="Time" id="showtime"></div>
    </td>
  </tr>
  <tr class="Navigation">
    <td height="28"><img src="images/Human.jpg" width="22" height="20"></td>
    <td class="Blue">在线专家</td>
    <td class="Darkblue">首席专家</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">袁秋文  杜绍范  刘英  赵义平  王克  宋宝辉  项亚萍  宣景宏  李金凤  李燕嘉  杨英  田敬华</marquee></td>
    <td class="Darkblue">列席专家</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">张奎男  王邵莹  孙军  陈国华  孙义忠  都业弘  周鹏飞  吕宏伟  杨文革  刘华  马凤君  史春生  杨宏  林惠家  唐春福  黄岳海  赵伟  黄毅  姜国君  刘权海  吕春修  朱宝玉  孔繁义  郭晓雷  韩春凤  宋国柱  朴春树  李素莉  宋雅坤</marquee></td>
    <td class="Darkblue">值班专家</td>
    <td width="260" class="Text"><marquee width="100%" height="100%" SCROLLAMOUNT="4">孙继锋  杨波  初军  孟祥玲  陈笑微  焦宁  范红</marquee></td>
  </tr>
  <tr  class="Navigation">
    <td height="28"><img src="images/Picture.jpg" width="21" height="17"></td>
    <td class="Blue">焦点关注</td>
    <td colspan="6" class="Text2">
    	<iframe src="../../screen/screen.do?method=toJiaoDianAnliList" height="100%" width="100%" scrolling="no" frameborder="0" allowTransparency="true"></iframe>
    </td>
  </tr>
</table>
<span id="num">
<table width="1280" height="25" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="Data">
  	<td>咨询总量</td>
    <td>读取中</td>
    <td>今日咨询量</td>
    <td>读取中</td>
    <td>生产</td>
    <td>读取中</td>
    <td>市场</td>
    <td>读取中</td>
    <td>政策</td>
    <td>读取中</td>
    <td>医疗</td>
    <td>读取中</td>
    <td>其他</td>
    <td>读取中</td>
  </tr>
</table>
</span>
<table width="1280" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="550" align="center" bgcolor="#D4D4D4">
	<br>
	<iframe name="iframe1" id="iframe1" src="" height="100%" width="100%" scrolling="No" frameborder="0"></iframe></td>
  </tr>
</table>

<table width="1280" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" bgcolor="#6AA5CF">&nbsp;</td>
  </tr>
</table>


</body>
</html>

<script>
	
	var filepath = "hwsk1.jsp,sdzx2.jsp,lmzx3.jsp,dcbg4.jsp";
	var paths = filepath.split(",");
	
	var timeout = 10000;
	var j = 0;
	var l = paths.length;
	switchPage();
	function switchPage(){
		document.iframe1.location = paths[j];
		//document.iframe1.location = "dcbg4.jsp";
		window.setTimeout(switchPage,timeout);
		j++;
		if(l  == j){
			j = 0;
		}
	}
	
	show();
	function show(){
	
		var date = new Date();
		var yy = date.getYear();
		var MM = date.getMonth() + 1;
		var dd = date.getDate(); 
		var HH = date.getHours();
		var mm = date.getMinutes();
		var ss = date.getSeconds();
		
		switch (date.getDay()) {
		case 0 :
			ww = "星期日";
			break;
		case 1 :
			ww = "星期一";
			break;
		case 2 :
			ww = "星期二";
			break;
		case 3 :
			ww = "星期三";
			break;
		case 4 :
			ww = "星期四";
			break;
		case 5 :
			ww = "星期五";
			break;
		case 6 :
			ww = "星期六";
			break;
		}
		HH = HH < 10 ? "0"+HH : HH;
		mm = mm < 10 ? "0"+mm : mm;
		ss = ss < 10 ? "0"+ss : ss;
		document.getElementById("showtime").innerHTML = "<b>"+yy+"年"+MM+"月"+dd+"日<br>"+ww+"<br>"+HH+":"+mm+":"+ss+"</b><br><br>" ;
		window.setTimeout(show,1000);
	
	}

	//////////////////////////////////////////
	sendRequest();
	
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
	function sendRequest() {
		createXMLHttpRequest();
		XMLHttpReq.open("post", "../../screen/screen.do?method=toZixunSumDto", true);
		XMLHttpReq.onreadystatechange = processResponse;//指定响应函数
		XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		XMLHttpReq.send(null);  // 发送请求
		window.setTimeout(sendRequest,3000);
	}
	// 处理返回信息函数
    function processResponse() {
    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
        	if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息
            	var res=XMLHttpReq.responseText;
				//window.alert(res); 
				document.getElementById('num').innerHTML = res;
                
            } else { //页面不正常
                window.alert("您所请求的页面有异常。");
            }
        }

    }
</script>

