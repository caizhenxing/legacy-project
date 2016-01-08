<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=gbk"%>


<html>
  <head>
  
    <title>金农市场分析大屏幕维护</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

  </head>
  
  <frameset name="dict" rows="10%,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="../screen/marAnalysis.do?method=toMarAnalysisQuery2" noresize>
	<frame name="bottomm" scrolling="no" src="../html/content.jsp" noresize>
  </frameset>
</html>
