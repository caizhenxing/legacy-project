<%@ page language="java" contentType="text/html;charset=GB2312" %>
<jsp:directive.include file="/base/default.jspf" />
<html>
	<head>
		<script type="text/javascript" src="<s:url value="/chart/js/swfobject.js"/>"></script>
		<script type="text/javascript">
			swfobject.embedSWF("<s:url value="/chart/open-flash-chart.swf"/>", "my_chart", "1024", "300", "9.0.0", 
			"expressInstall.swf",
			{"data-file":"<s:url value="/json-chart/chart/ch.do"/>"});
		</script>
	</head>
	<body>
		<div id="my_chart"></div>
	</body>
</html>