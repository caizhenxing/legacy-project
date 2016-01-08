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
	<!-- 引入样式文件 -->
	<script language="javascript" src="../../js/et/style.js"></script>
	
	
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
    <html:form action="screen/scrspesur.do"  method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		  <td class="listTitleStyle" >调查主题</td>
<%--		    <td class="listTitleStyle" width="10%">id</td>--%>
		    <td class="listTitleStyle" >调查提交时间</td>
		    <td class="listTitleStyle" >概要内容</td>
		    <td class="listTitleStyle" >委托机构</td>
		    <td class="listTitleStyle" >撰稿人</td>
		    
		    <td class="listTitleStyle" width="100px">操作</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
			String style = i.intValue() % 2 == 0 ? "oddStyle": "evenStyle";
		  %>
		  <tr>
		  	<td ><bean:write name="c" property="sssTitle" filter="true"/></td>
<%--		  	<td ><bean:write name="pagelist" property="id" filter="true"/></td>--%>
		    <td ><bean:write name="c" property="sssCreateTime" filter="true"/></td>
		    <td ><bean:write name="c" property="sssSummary" filter="true"/></td>
		    <td ><bean:write name="c" property="sssDelegeteDep" filter="true"/></td>
<%--		    <td ><bean:write name="pagelist" property="wavPath" filter="true"/></td>--%>
            <td ><bean:write name="c" property="sssWritter" filter="true"/></td>  
            
            <td >
            
			<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="openWin('./../screen/scrspesur.do?method=toSSSLoad&opertype=detail&id=<bean:write name="c" property="sssId" filter="true"/>')"
							width="16" height="16" border="0" />
			<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"
							onclick="openWin('./../screen/scrspesur.do?method=toSSSLoad&opertype=update&id=<bean:write name="c" property="sssId" filter="true"/>')"
							width="16" height="16" border="0" />
		    <img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
							onclick="openWin('./../screen/scrspesur.do?method=toSSSLoad&opertype=delete&id=<bean:write name="c" property="sssId" filter="true"/>')"
							width="16" height="16" border="0" />
            </td>                
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5" class="pageTable">
		    	<page:page name="sssPageTurning" style="second"/>
		    </td>
		    	
		    <td class="pageTable">
		       <input type="button" value="添加"  onclick="openWin('../screen/scrspesur.do?method=toSSSLoad&opertype=insert')" class="buttonStyle" />
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
