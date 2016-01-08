<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=gbk"%>


<html>
  <head>
  
    <title>消息管理</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <frameset name="dict" rows="10%,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="messages.do?method=toMessagesQuery" noresize>
	<frame name="bottomm" scrolling="yes" src="messages.do?method=toMessagesList" noresize>
  </frameset>
</html>
