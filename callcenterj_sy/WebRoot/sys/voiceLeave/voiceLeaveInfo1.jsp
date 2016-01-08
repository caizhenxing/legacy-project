<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<%@ include file="../../style.jsp"%>
  <head>
    <html:base />
    
    <title>语音信息详细</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link REL=stylesheet href="../../markanainfo/css/divtext.css" type="text/css"/>
    <style type="text/css">
	<!--
	#fontStyle {
		font-family: "宋体";
		font-size: 12px;
		font-style: normal;
	}
	-->
	</style>
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
  </head>
  
  <body class="loadBody">
    <html:form action="/sys/voiceLeave.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
		  <tr>
		    <td class="labelStyle">开始时间</td>
		    <td class="valueStyle">
			    <html:text property="beginTime" styleClass="writeTextStyle" />
		    </td>
		  </tr>
		  <tr>
		    <td class="labelStyle">结束时间</td>
		    <td class="valueStyle">
		    <html:text property="endTime" styleClass="writeTextStyle"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="labelStyle">语音路径</td>
		    <td class="valueStyle">
		    <html:text property="wavPath" styleClass="writeTextStyle"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="labelStyle">是否处理</td>
		    <td class="valueStyle">
		    <html:text property="ifDispose" styleClass="writeTextStyle"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="labelStyle">处理意见</td>
		    <td class="valueStyle">
		    <html:textarea property="disposeSuggest" rows="10" cols="30" styleClass="writeTextStyle"></html:textarea>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
