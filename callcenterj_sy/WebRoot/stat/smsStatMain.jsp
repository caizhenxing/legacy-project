<%@ page language="java" contentType="text/html; charset=GBK"%>


<html>
  <head>
  
    <title>座席电话量统计</title>
	<link href="images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <frameset name="dict" rows="10%,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="../stat/smsStat.do?method=toSmsStatQuery" noresize>
	<frame name="bottomm" src="../html/content.jsp" noresize>
  </frameset>
</html:html>
