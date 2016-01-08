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
   
   	<td class="tdbgcolorlist2" bordercolor=""><b>阶段计划标题</b></td>
   	<td class="tdbgcolorloadleft"> <bean:write name="planTitle"/></td>
   	<td class="tdbgcolorlist2"><b>阶段计划副标</b></td>
   	<td class="tdbgcolorloadleft"><bean:write name="planSubhead"/></td>
   
   </tr>
   <tr>
   
   <td class="tdbgcolorlist2"><b>阶段计划开始时间</b></td>
   <td class="tdbgcolorloadleft"><bean:write name="planBeignTime"/></td>
   <td class="tdbgcolorlist2"><b>阶段计划结束时间</b></td>
   <td class="tdbgcolorloadleft"><bean:write name="planEndTime"/></td>
   
   </tr>
   
   </table>
  
  <br>

  
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 <logic:notEmpty name="listTitle">
		 <tr>
		 <td colspan="6" class="tdbgpicload"><bean:write name="listTitle" /></TD>
		 </tr>
		 </logic:notEmpty>
		  <tr>
		    <td class="tdbgcolorlist2"><b>详细计划名</b></td>
		    <td class="tdbgcolorlist2"><b><bean:message key="oa.privy.plan.begintime"/></b></td>
		    <td class="tdbgcolorlist2"><b><bean:message key="oa.privy.plan.endtime"/></b></td>
		    <td class="tdbgcolorlist2"><center><b>详细计划</b></center></td>
<%--		    <td class="tdbgpiclist">阶段计划标题</td>--%>
		    <td class="tdbgcolorlist2"><b>操作</b></td>
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
<%--		    <td class="<%=style%>"><bean:write name="c" property="planName" filter="true"/></td>--%>
		    <td class="<%=style%>"><html:link action="/workmission.do?method=changemission&type=mi" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',880,700)" target="windows">填写</html:link></td>
		   </tr>
		  </logic:iterate>
		</table>
</table>
</html:form>
  </body>
</html:html>