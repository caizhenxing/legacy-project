<%@ page language="java" contentType="text/html; charset=GBK"
	import="org.jfree.chart.JFreeChart,org.jfree.chart.ChartRenderingInfo,org.jfree.chart.servlet.ServletUtilities,org.jfree.chart.entity.StandardEntityCollection"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
	<head>
		<title></title>

	</head>
	<%
		JFreeChart chart = (JFreeChart) request.getAttribute("chart");
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		String fileName = ServletUtilities.saveChartAsPNG(chart, 690, 395,
				info, session);
		String graphURL = request.getContextPath()
				+ "/servlet/DisplayChart?filename=" + fileName;
	%>
	<body style="MARGIN: 0px; BACKGROUND-COLOR: #ffffff">
		<table align="center" width="100%">
			<tr>
				<td align="center">
					<font color="red"><div id="showTitle"
							style="text-align:center;width:1024px;" id="showTitle"></div> </font>
				</td>
			</tr>
		</table>
		<center>
			<img src="<%=graphURL%>" border="1">
		</center>
	</body>
</html>
