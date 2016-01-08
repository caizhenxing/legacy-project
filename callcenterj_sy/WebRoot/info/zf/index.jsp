<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>信息推送</title>
<style type="text/css">
body{margin:0px;}
.table_liang{ font:18px 黑体; color:#696969; border-collapse:collapse; margin-top:4px; text-align:right; padding-right:8px;}
.table_liang td{border:1px solid #ffffff;}
.table_liang thead td{color:#696969;padding-top:3px;}
.table_liang tbody td{color:#9c8b00; font-weight:bold;}
</style> 
</head>

<body style="background:url(images/top_bj.gif) repeat-x top left;" scroll="no">
<table width="1280" height="100" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td style="background:url(images/zf_top_bj.jpg) no-repeat top left;">
    <div style="font:14px; position:absolute; top:8px; left:1140px;" align="center" id="showtime"></div>
    </td>
  </tr>
</table>
<table width="1280" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="150" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;"><img src="images/zxzj_tu.gif" style="margin:2px 5px -5px 5px;"/>在线专家：</td>
    <td width="77" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px; padding-top:5px;"><span style="color:#000000;font:14px 宋体;">首席专家：</span></td>
    <td width="290" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px; padding-top:5px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><span style="color:#FF0000;font:14px 宋体;">
        <strong><marquee width="100%" height="100%" SCROLLAMOUNT="4">袁秋文  杜绍范  刘英  赵义平  王克  宋宝辉  项亚萍  宣景宏  李金凤  李燕嘉  杨英  田敬华</marquee></strong>
        </span></td>
      </tr>
    </table></td>
    <td width="77" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;"><span style="color:#000000;font:14px 宋体;">列席专家：</span></td>
    <td width="290" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><span style="color:#FF0000;font:14px 宋体;">
        <strong><marquee width="100%" height="100%" SCROLLAMOUNT="4">张奎男  王邵莹  孙军  陈国华  孙义忠  都业弘  周鹏飞  吕宏伟  杨文革  刘华  马凤君  史春生  杨宏  林惠家  唐春福  黄岳海  赵伟  黄毅  姜国君  刘权海  吕春修  朱宝玉  孔繁义  郭晓雷  韩春凤  宋国柱  朴春树  李素莉  宋雅坤</marquee></strong>
        </span></td>
      </tr>
    </table></td>
    <td width="77" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;"><span style="color:#000000;font:14px 宋体;">值班专家：</span></td>
    <td width="290" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><span style="color:#FF0000;font:14px 宋体;">
        <strong><marquee width="100%" height="100%" SCROLLAMOUNT="4">孙继锋  杨波  初军  孟祥玲  陈笑微  焦宁  范红</marquee></strong>
        </span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="7" valign="middle" style="font:16px 黑体; color:#0066b3; height:26px; padding-left:5px;">
    
    	<table width="100%">
    		<tr>
    			<td valign="middle" style="font:16px 黑体; color:#0066b3; width:150; height:40px;">
    				<img src="images/jdgz_tu.gif" style="margin:2px 5px -5px 5px;"/>焦点关注：
    			</td>
    			<td>
    			<iframe src="../../screen/screen.do?method=toJiaoDianAnliList" height="100%" width="100%" scrolling="no" frameborder="0"></iframe>
    			</td>
    		</tr>
    	</table>
    </td>
  </tr>
</table>

<table width="1280" border="0" cellspacing="0" cellpadding="0">
  <tr>
   <td height="65" align="center" valign="top" style="background:url(images/liang_bj.gif) no-repeat top center;" id="num">
   </td>
  </tr>
</table>
<table width="1280" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" style="background:url(images/jiao_tu.gif) no-repeat top center; height:7px;"></td>
  </tr>
  <tr>
  	<td align="center">
			<table width="1266" height="100%" border="0" cellspacing="0" cellpadding="0" style="border-left:#84a9c2 1px solid; border-right:#84a9c2 1px solid; border-bottom:#84a9c2 1px solid;">
		  <tr>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td height="40" align="center" style="background-color:#d3e3ef;"><img id="topic"/></td>
			  </tr>
			</table>
			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');">
              <tr>
                <td height="470">
					<div width="100%">
                		<iframe name="iframe1" id="iframe1" src="" height="100%" width="100%" scrolling="no" frameborder="0"></iframe>
					</div>
				</td>
              </tr>
            </table></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>

</body>
</html>
<script>
	
	var filepath = "huawu,count,column,dcbg";
	var topicImg = "title_hwsk.gif,title_sdzxltj.gif,title_lmzxltj.gif,title_dcbg.gif";
	var paths = filepath.split(",");
	var topicImgs = topicImg.split(",");
	
	var timeout = 10000;
	var j = 0;
	var l = paths.length;
	switchPage();
	function switchPage(){
		document.iframe1.location = paths[j]+"/index.jsp";
		//document.iframe1.location = "dcbg/index.jsp";
		document.getElementById("topic").src = "images/"+topicImgs[j];
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
		document.getElementById("showtime").innerHTML = "<b>"+yy+"年"+MM+"月"+dd+"日<br>"+ww+"<br>"+HH+":"+mm+":"+ss+"</b>" ;
		setTimeout(show,1000);
	
	}
	//////////////////////////////////////////
	sendRequest();
	setTimeout(sendRequest,3000);
	
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