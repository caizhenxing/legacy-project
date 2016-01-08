<%@ page language="java" contentType="text/html; charset=GBK"
	import="org.jfree.chart.JFreeChart,org.jfree.chart.ChartRenderingInfo,org.jfree.chart.servlet.ServletUtilities,org.jfree.chart.entity.StandardEntityCollection"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ include file="../style.jsp"%>
<html>
	<head>
		<title></title>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../js/public.js"></script>
		<script language="javascript">
			function createExcel()
			{
				var oImg = document.getElementById('statImg');
				if(oImg)
				{
					var src = oImg.src;
					//DisplayChart?filename=jfreechart-19229.png
					var index = src.indexOf("=");
					var imgName = src.substring(index+1);
					//var url = '../commonExcelServlet?imgName='+imgName;
					window.frames['createExcelI'].document.getElementById('imgName').value=imgName;
	window.frames['createExcelI'].document.getElementById('submitBtn').click();
				}
			}
		</script>
	</head>
	<%
		JFreeChart chart = (JFreeChart) request.getAttribute("chart");
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		String fileName = ServletUtilities.saveChartAsPNG(chart, 500, 375,
				info, session);
		String graphURL = request.getContextPath()
				+ "/servlet/DisplayChart?filename=" + fileName;
	%>
	<body class="conditionBody" >
		<table align="center" width="800">
			<tr>
				<td align=center>
					<font color="red"><div id="showTitle"
							style="text-align:center;width:800px;" id="showTitle"></div> </font>
				</td>
			</tr>
		</table>
		<center>
		<div>
			<img id="statImg" style="display:block" src="<%=graphURL%>" border="1" >
			<span style="width:100%;"><input type="button" value="导出excel" onclick="createExcel()"></span>	
		</div>
		</center>
		
		<div style="display:none;">
			<iframe name="createExcelI" id="createExcelI" src="./commonImgExcel.jsp"></iframe>
		</div>
<%--<script>--%>
<%--function getimg() {--%>
<%--event.returnValue=false;--%>
<%--show.window.location.href=event.srcElement.src;--%>
<%--timer=setInterval(checkload,100)--%>
<%--}--%>
<%--function checkload(){--%>
<%--if(show.readyState!="complete")--%>
<%--{show.document.execCommand("SaveAs");--%>
<%--  clearInterval(timer)}--%>
<%--}--%>
<%----%>
<%--</script>--%>
<%--<img src="<%=graphURL%>" border="1" oncontextmenu="getimg()">--%>
<%----%>
<%--<iframe src="" name=show style="width:0;height:0"></iframe>--%>
<%--		<Input   Type=Button   Value="保存"   OnClick="document.execCommand('SaveAs',false,'.\\<%=excellence.common.util.time.TimeUtil.getTheTimeStr(new java.util.Date(), "yyyy-MM-dd-hh-mm-ss")%>.xls')">--%>
<%--		<OBJECT   classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2   height=0   id=WebBrowser   width=0></object>   --%>
<%--  <input   onclick=document.all.WebBrowser.ExecWB(4,1)   type=button   value=另存为>--%>
	</body>
</html>
