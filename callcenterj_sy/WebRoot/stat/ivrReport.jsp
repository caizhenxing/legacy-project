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
    <script language="javascript" src="../../js/et/style.js"></script>
    <script language="javascript">

    </script>
    	<script language="javascript">
		var basePath='<%=basePath%>';
	</script>
	<script language="javascript" src="../js/Table.js" ></script>
  </head>
  
  <body class="listBody">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="listTable" id="changeColorTbl" class="listTable">
		  <tr class="tdbgpiclist">
		    <td class="listTitleStyle" width="50%">
		    	<logic:notEmpty name="querytype">
				<logic:equal name="querytype" value="ͳ�ƴ���">��Ŀ����</logic:equal>
				<logic:equal name="querytype" value="ͳ��ʱ��">��Ŀ����</logic:equal>
				<logic:equal name="querytype" value="ͳ�����ڴ���">����</logic:equal>
				<logic:equal name="querytype" value="ͳ������ʱ��">����</logic:equal>
			</logic:notEmpty>
		    <logic:empty name="querytype">��Ŀ����</logic:empty>	
		    </td>
		    <td class="listTitleStyle" width="50%">
		    <logic:notEmpty name="querytype">
				<logic:equal name="querytype" value="ͳ�ƴ���">�δ����</logic:equal>
				<logic:equal name="querytype" value="ͳ��ʱ��">�δ�ʱ��</logic:equal>
				<logic:equal name="querytype" value="ͳ�����ڴ���">�δ����</logic:equal>
				<logic:equal name="querytype" value="ͳ������ʱ��">�δ�ʱ��</logic:equal>
			</logic:notEmpty>
			<logic:empty name="querytype">�������</logic:empty>		
		    </td>
<%--		    <td class="tdbgpiclist">�������Ͷ�</td>--%>
<%--		    <td class="tdbgpiclist">�ܻ�����</td>--%>
		  </tr>
		  
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle"
				: "evenStyle";
			%>
		  <tr align="center" >
					<td>
						<bean:write name="c" property="IVRmodule" filter="false" />
					</td>
					<td>
						<bean:write name="c" property="count" filter="false" />
					</td>
<%--					<td>--%>
<%--						<bean:write name="c" property="type3" filter="false" />--%>
<%--					</td>--%>
				</tr>
		  </logic:iterate>
		  <logic:notEmpty name="list">
		  <tr>
		  		<td class="evenStyle">
		  			�ܼ�
		  		</td>
				<td class="evenStyle">
					<bean:write name="c" property="num" filter="false" />
				</td>
			</tr>
			</logic:notEmpty>
		  		  		  <tr>
					<td colspan="2" class="listTitleStyle" align="right" style="margin:0px;padding-right:0px;">
					<div style="text-align:right;">
						<input type="button" class="buttonStyle" value="����Excel" onclick="parseTbl('changeColorTbl','IVRģ�����ͳ��','<%=basePath %>')" />
					</div>
					</td>
				</tr>
		</table>
  </body>
</html:html>