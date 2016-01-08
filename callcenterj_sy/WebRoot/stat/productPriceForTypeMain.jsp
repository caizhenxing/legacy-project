<%@ page language="java" contentType="text/html; charset=GBK"%>


<html>
  <head>
  
    <title>农产品价格库统计</title>

  </head>
  
  <frameset name="dict" rows="18%,*" border="0" frameborder="0"  framespacing="0">
  	<frame name="topp" scrolling="no" src="./stat/productPriceStatForServiceType.do?method=toQuery" noresize>
	<frame name="bottomm" src="./html/content.jsp" noresize>
  </frameset>
</html:html>
