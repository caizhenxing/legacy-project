<%@ page contentType="text/html; charset=gbk"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>信息推送</title>
<style type="text/css">
body{margin:0px;}
</style>
</head>

<body style="background:url(images/top_bj.gif) repeat-x top left;" scroll="no">
<table width="1280" height="100" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td style="background:url(images/top_bj.jpg) no-repeat top left;">
    	<div style="font:14px; position:absolute; top:8px; left:1140px;" align="center" id="showtime"></div>
    </td>
  </tr>
</table>
<table width="1280" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<table width="100%">
    		<tr>
    			<td valign="middle" style="font:16px 黑体; color:#0066b3; width:150; height:40px; padding-left:5px;">
    				<img src="images/kx_tu.gif" style="margin:0px 5px -3px 5px;"/>
    				<strong>12361</strong>快讯：
    			</td>
    			<td>
    			<iframe src="../../screen/screen.do?method=toQuickMessageAdd" height="100%" width="100%" scrolling="no" frameborder="0"></iframe>
    			</td>
    		</tr>
    	</table>
    </td>
  </tr>
</table>

<table width="1280" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" style="background:url(images/jiao_tu.gif) no-repeat top; height:7px;"></td>
  </tr>
  <tr>
  	<td align="center" valign="top">
		<table width="1266"  border="0" cellspacing="0" cellpadding="0" style="border-left:#84a9c2 1px solid; border-right:#84a9c2 1px solid; border-bottom:#84a9c2 1px solid;">
		  <tr>
			<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td align="center" style="background-color:#d3e3ef;" height="40"><img id="topic"/></td>
			  </tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr='#d4e4f0', endColorStr='#ffffff', gradientType='0');">
              <tr>
                <td height="560">
                	<div width="100%">
                		<iframe name="iframe1" id="iframe1" src="" height=100% width="100%" scrolling="no" frameborder="0"></iframe>
					</div>
				</td>
              </tr>
            </table>
            </td>
		  </tr>
		</table>
	</td>
  </tr>
</table>

</body>
</html>
<script>
	
	var filepath = "dayPrice,jnscfx,sadInfo,jindian,yznztj,zztj";
	var topicImg = "title_mrjg.gif,title_jrscfx.gif,title_mrgq.gif,title_jdal.gif,title_yznzdj.gif,title_zjtj.gif";
	var paths = filepath.split(",");
	var topicImgs = topicImg.split(",");
	
	var timeout = 10000;
	var j = 0;
	var l = paths.length;
	switchPage();
	function switchPage(){
		document.iframe1.location = paths[j]+"/index.jsp";
		//document.iframe1.location = "zztj/index.jsp";
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
	
</script>