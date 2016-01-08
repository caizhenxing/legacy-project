<%@ page language="java" contentType="text/html; charset=GBK"%>


<html>
  <head>
  
    <title>按地区统计来电量</title>
	<link href="images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <frameset name="dict" rows="15%,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="../stat/address.do?method=toQuery" noresize>
	<frame name="bottomm" src="../html/content.jsp" noresize>
  </frameset>
</html:html>
