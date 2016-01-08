<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="./css/styles.css" media="screen" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="./js/jquery/jquery-1.3.1.min.js"></script>	


<script language="javascript">
	var screenWidth =  window.screen.width;
	$(document).ready(function(){
		if(screenWidth==1024){
			document.location.href = './screen/gov/1024/screen1024.jsp';
		}else{
			document.location.href = './screen/gov/1336/screen1336.jsp';
		}
	});
</script>

<title>政府大屏幕系统</title>
</head>

<body>
	
	


</body>
</html>
