<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>切换</title>
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/jquery/plug/tab.js"></script>
<script type="text/javascript" src="../js/jquery/plug/tabchange.js"></script>
<style type="text/css">
@IMPORT url("../css/tab.css");

body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	font-family: "黑体";
}

#page {
	position: absolute;
	top: 30px;
	left: 0;
	width: 1220px;
	height: 600px;
	border: solid 1px #cccccc;
	/*height: expression(parentNode . parentNode . offsetHeight-25);*/
}

#tab_menu {
	position: absolute;
	left: 0px;
	padding: 0px;
	width: 1020;
	height: 60px;
	top: 0px;overflow: hidden;
}
</style>
</head>
<body>
<div id="page"></div>
<div id="tab_menu"></div>
<input name="btnSearch" type="button" id="btnSearchTel" onclick="searchTel()" value="查找电话" style="display:none;"/>
<input id="txtTelId" name="txtTel" type="text" style="display:none;" />
</body>
</html>
