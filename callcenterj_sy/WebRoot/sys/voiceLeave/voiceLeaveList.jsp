<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html:html lang="true">
  <head>
    <html:base />
    
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
		function openWin(url)
		{
			window.open(url,'','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
		}
		function doNo()
		{
		}
	</script>
	<script language="javascript">
		function goDispose(url)
		{
			window.open(url,'','width=800,height=600,status=no,resizable=yes,scrollbars=yes,top=200,left=280');
		}
   	</script>
	
  </head>
  
  <body class="listBody">
    <html:form action="custinfo/custinfo.do"  method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		  <td class="listTitleStyle" width="10%">呼入号码</td>
<%--		    <td class="listTitleStyle" width="10%">id</td>--%>
		    <td class="listTitleStyle" >开始时间</td>
		    <td class="listTitleStyle" >结束时间</td>
<%--		    <td class="listTitleStyle" width="15%">语音路径</td>--%>
		    <td class="listTitleStyle" width="10%">是否处理</td>
		    
		    <td class="listTitleStyle" width="100px">操作</td>
		  </tr>
		  <logic:iterate id="pagelist" name="list" indexId="i">
		  <%
			String style = i.intValue() % 2 == 0 ? "oddStyle": "evenStyle";
		  %>
		  <tr>
		  	<td ><bean:write name="pagelist" property="caller" filter="true"/></td>
<%--		  	<td ><bean:write name="pagelist" property="id" filter="true"/></td>--%>
		    <td ><bean:write name="pagelist" property="beginTime" filter="true"/></td>
		    <td ><bean:write name="pagelist" property="endTime" filter="true"/></td>
<%--		    <td ><bean:write name="pagelist" property="wavPath" filter="true"/></td>--%>
            <td ><bean:write name="pagelist" property="ifDispose" filter="true"/></td>  
            
            <td >
            <img alt="处理" src="../../style/<%=styleLocation%>/images/Processing.gif"
							onclick="openWin('./../sys/voiceLeave.do?method=toPrepDispose&id=<bean:write name="pagelist" property="id" filter="true"/>')"
							width="16" height="16" border="0" />
			<img alt="详细" src="../../style/<%=styleLocation%>/images/Detailed.gif"
							onclick="openWin('./../sys/voiceLeave.do?method=toVoiceInfo&id=<bean:write name="pagelist" property="id" filter="true"/>')"
							width="16" height="16" border="0" />
			<a href="<bean:write name='pagelist' property='wavPath'/>">
			<img alt="收听录音" src="../../style/<%=styleLocation%>/images/Listening.gif"
							width="16" height="16" border="0" />
			</a>
            </td>                
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5" class="pageTable">
		    	<page:page name="voicepageTurning" style="second"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
