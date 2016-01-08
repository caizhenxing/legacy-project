<%@ page language="java" import="java.util.*" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>


<html>
  <head>
  
    <title>¿Í»§</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <frameset name="dict" rows="77,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="custinfo.do?method=toCustinfoQuery" noresize>
	<frame name="bottomm" scrolling="no" src="custinfo.do?method=toCustinfoList" noresize>
  </frameset>
</html>
