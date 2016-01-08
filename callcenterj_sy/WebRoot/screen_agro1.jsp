<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="./css/styles.css" media="screen" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="./js/jquery/jquery-1.2.6.pack.js"></script>	
<script type="text/javascript" src="./js/jquery/plug/jquery.aslideshow.pack.js"></script>

<script language="javascript">
	$(document).ready(function(){
		$('#MySlideshow').slideshow({
			width:1050,//宽度
			height:788,//高度
			time:6000,//时间间隔
			title:true,//是否显示标题
			effect:'growX'
		}
		);
	});
</script>

<title>政府</title>
</head>

<body>

<div id="MySlideshow">
    <iframe src="./screen/gov/1024/CallLogStatis.html"></iframe>
	<iframe src="./screen/gov/1024/Case Study.html"></iframe>
	<iframe src="./screen/gov/1024/Expert Hotline.html"></iframe>
    <iframe src="./screen/gov/1024/Focus tracking.html"></iframe>
    <iframe src="./screen/gov/1024/Live traffic.html"></iframe>
    <iframe src="./screen/gov/1024/Market Analysis.html"></iframe>
    <iframe src="./screen/gov/1024/McKinnon Hotline.html"></iframe>
    <iframe src="./screen/gov/1024/Price Kanban.html"></iframe>
    <iframe src="./screen/gov/1024/special survey.html"></iframe>
	<iframe src="./screen/gov/1024/traffic statistics.html"></iframe>
</div>

</body>
</html>
