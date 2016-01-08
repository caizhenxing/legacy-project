<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/common.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
  </head>
  
  <body>
  <html:form action="/workmission.do">
  
  
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 
		  <tr>
		    <td class="tdbgpiclist">详细计划名<page:order name="name" page="planinfoPageTurning" path="imagesinsession"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.begintime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.endtime"/></td>
		    <td class="tdbgpiclist">详细计划简介</td>
		    <td class="tdbgpiclist">属于阶段计划</td>
		    <td class="tdbgpiclist">操作</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="name" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="beginTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="endTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="missionInfo" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planName" filter="true"/></td>
		    <td class="<%=style%>"><html:link action="/workmission.do?method=changemission&type=up" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',880,700)" target="windows">填写</html:link></td>
		   </tr>
		  </logic:iterate>
		  <td colspan="7" ><page:page name="planinfoPageTurning" style="first"/></td>
		
</table>
</html:form>
  </body>
</html:html>