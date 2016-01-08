<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%--<jsp:directive.page import="org.jfree.chart.JFreeChart"/>--%>
<%--<jsp:directive.page import="et.bo.chartDate.ChartInfo"/>--%>
<%--<jsp:directive.page import="org.jfree.chart.ChartRenderingInfo"/>--%>
<%--<jsp:directive.page import="org.jfree.chart.entity.StandardEntityCollection"/>--%>
<%--<jsp:directive.page import="org.jfree.chart.servlet.ServletUtilities"/>--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 <link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">

    </script>

	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js" ></script>
  </head>
  
  <body class="listBody">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable" id="changeColorTbl">
		  <tr class="tdbgpiclist">
		    <td class="listTitleStyle" width="33%">模块名称</td>
		    <td class="listTitleStyle" width="33%">点击率</td>
		    <td class="listTitleStyle" width="34%">统计值</td>
<%--		    <td class="tdbgpiclist">总话务量</td>--%>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr align="center" >
					<td>
						<bean:write name="c" property="Xaxes" filter="false" />
					</td>
					<td>
						<bean:write name="c" property="Yaxes" filter="false" />
					</td>
					<td>
						<bean:write name="c" property="XYsum" filter="false" />
					</td>
<%--					<td>--%>
<%--						<bean:write name="c" property="type3" filter="false" />--%>
<%--					</td>--%>
				</tr>
		  </logic:iterate>
		  		  		  <tr>
					<td colspan="3" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
					<div style="text-align:right;">
						<input type="button" class="buttonStyle" value="导出Excel" onclick="parseTbl('changeColorTbl','普通医疗使用率统计','<%=basePath %>')" />
					</div>
					</td>
				</tr>
		</table>
  </body>
</html:html>