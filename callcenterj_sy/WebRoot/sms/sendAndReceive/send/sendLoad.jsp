<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    <title>已发短信操作</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style type="text/css">
<!--
#fontStyle {
	font-family: "宋体";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
<link href="../../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
<SCRIPT language=javascript src="../../../js/calendar3.js" type=text/javascript/>
 <script language="javascript" src="../../../js/form.js"></script>
<script type="text/javascript">
function delOper()
{
	document.forms[0].action="../send.do?method=toSendOper&type=delete";		
	document.forms[0].submit();
}		
function toback()
{
	//opener.parent.topp.document.all.btnSearch.click();
	opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
}
</script>
</head>

<body onunload="toback()" class="loadBody">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/sms/sendAndReceive/send" method="post">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="navigateStyle">
		    	<logic:equal name="opertype" value="detail">
		    		查看短信
		    	</logic:equal>
		    	<logic:equal name="opertype" value="delete">
		    		删除短信
		    	</logic:equal>
		    </td>
		  </tr>
		</table>
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
    		<tr>
	    			<td  width="20%" class="labelStyle">
	    				接受号码
	    			</td>
	    			<td id="fontStyle" class="valueStyle">
	    				<bean:write name="sendBean" property="receiveNum"/>
	    			</td>
	    	</tr>
	    	<tr>
	    			<td class="labelStyle">
	    					发送时间
	    			</td>
	    			<td id="fontStyle" class="valueStyle">
	    					<bean:write name="sendBean" property="operTime"/>
	    			</td>
	    	</tr>
<%--	    	<tr>--%>
<%--	    			<td class="labelStyle">--%>
<%--	    					发送次数--%>
<%--	    			</td>--%>
<%--	    			<td id="fontStyle" class="valueStyle">--%>
<%--	    					<bean:write name="sendBean" property="operCount"/>--%>
<%--	    			</td>--%>
<%--	    	</tr>--%>
<%--	    	<tr>--%>
<%--	    			<td class="labelStyle">--%>
<%--	    					预定发送时间--%>
<%--	    			</td>--%>
<%--	    			<td id="fontStyle" class="valueStyle">--%>
<%--	    					<bean:write name="sendBean" property="schedularTime"/>--%>
<%--	    			</td>--%>
<%--	    	</tr>--%>
	    	<tr>
	    			<td class="labelStyle">
	    					运&nbsp;营&nbsp;商
	    			</td>
	    			<td id="fontStyle" class="valueStyle">
	    					<bean:write name="sendBean" property="management"/>
	    			</td>
	    	</tr>
	    	<tr>
	    			<td class="labelStyle">
	    					短信内容
	    			</td>
	    			<td id="fontStyle" class="valueStyle">
	    					<bean:write name="sendBean" property="content"/>
	    			</td>
	    	</tr>
	    	<tr>
    			<td colspan="4"  align="center" class="buttonAreaStyle">
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="delBtn"    value="删除" onclick="delOper()" class="buttonStyle"/>
    			</logic:equal>
    				<input type="button" name="closeBtn" value="关闭"    onClick="javascript:window.close();" class="buttonStyle"/>
    			</td>
    		</tr>
			<html:hidden property="id"/>
    	</table>
    	</html:form>
  </body>
</html:html>
