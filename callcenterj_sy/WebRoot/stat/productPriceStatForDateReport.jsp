<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../style.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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

		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js" ></script>
  </head>
  
  <body class="listBody">
		<table id="tbl1" width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle" width="20%">产品名称</td>
		    <td class="listTitleStyle" width="20%">时间</td>
		    <td class="listTitleStyle" width="20%">价格最大值</td>
		    <td class="listTitleStyle" width="20%">价格最小值</td>
<%--		    <td class="listTitleStyle">呼入<logic:equal name="condition" value="count">数量(个)</logic:equal><logic:notEqual name="condition" value="count">时长(秒)</logic:notEqual> </td>--%>
<%--		    <td class="listTitleStyle">呼出<logic:equal name="condition" value="count">数量(个)</logic:equal><logic:notEqual name="condition" value="count">时长(秒)</logic:notEqual></td>--%>
		    <td class="listTitleStyle" width="20%">价格平均值</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"oddStyle":"evenStyle";
			%>
		  <tr>
					<td >
						<bean:write name="c" property="productName" filter="false" />
					</td>
<%--					<td >--%>
<%--						<bean:write name="c" property="type1" filter="false" />--%>
<%--					</td>--%>
<%--					<td >--%>
<%--						<bean:write name="c" property="type2" filter="false" />--%>
<%--					</td>--%>
					<td >
						<bean:write name="c" property="date" filter="false" />
					</td>
					<td >
						<bean:write name="c" property="maxValue" filter="false" />
					</td>
					<td >
						<bean:write name="c" property="minValue" filter="false" />
					</td>
					<td >
						<bean:write name="c" property="avgValue" filter="false" />
					</td>
				</tr>
		  </logic:iterate>
		  	
		  		  <tr>
					<td colspan="5" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
					<div style="text-align:right;">
						<input type="button" class="buttonStyle" value="导出Excel" onclick="parseTbl('tbl1','各产品价格的最大值、最小值、平均值','<%=basePath %>')" />
					</div>
					</td>
				</tr>
		</table>
  </body>
</html:html>