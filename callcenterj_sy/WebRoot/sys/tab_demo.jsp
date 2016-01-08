<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/jquery/plug/tab.js"></script>
<script type="text/javascript" src="../js/jquery/plug/demo.js"></script>
<style type="text/css">
@IMPORT url("../css/tab.css");

html,body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	font-family: "黑体";
}

#page {
	position: absolute;
	top: 28px;
	left: 0;
	width: 400px;
	height: 400px;
	border: solid 1px #cccccc;
	/*height: expression(parentNode . parentNode . offsetHeight-25);*/
}


.page_iframe {
	width: 400px;
	height: 400px;
}

#tab_menu {
	position: absolute;
	left: 0px;
	padding: 0px;
	width: 100%;
	height: 60px;
	top: 427px;overflow: hidden;
}
#page2 {
	position: absolute;
	top: 28px;
	left: 500px;
	width: 400px;
	height: 400px;
 	border: solid 1px #cccccc;
	/*height: expression(parentNode . parentNode . offsetHeight-25);*/
}

#tab_menu2 {
	position: absolute;
	left: 0px;
	top: 0px;
	padding: 0px;
	width: 100%;
	height: 60px;
	overflow: hidden;
}
</style>
</head>
<body>
<div id="page"></div>
<div id="tab_menu"></div>

<div id="page2"></div>
<div id="tab_menu2"></div>
</body>
</html>
