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
  <html:form action="/workplan.do">
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 
		  <tr>
		    <td class="tdbgpiclist">阶段计划名</td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.begintime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.endtime"/></td>
		    <td class="tdbgpiclist">阶段计划副标</td>
		    <td class="tdbgpiclist">阶段计划范围</td>
		    <td class="tdbgpiclist">详细</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="planTitle" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planBeignTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planEndTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planSubhead" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planDomain" filter="true"/></td>
			<td class="<%=style%>"><html:link action="/workplan.do?method=planInfo&check=t" paramId="id" paramName="c" paramProperty="id" onclick="popUp('newplan','',880,600)" target="newplan">审核</html:link></td>
		   </tr>
		  </logic:iterate>
		  <td colspan="7" ><page:page name="planinfoPageTurning" style="first"/></td>

</table>
</html:form>
  </body>
</html:html>