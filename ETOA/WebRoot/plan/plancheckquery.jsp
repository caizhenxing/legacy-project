
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
    
    <title><bean:message key="oa.privy.plan.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  <SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../js/common.js"></script>

<SCRIPT language=javascript src="../js/form.js" type=text/javascript>
</SCRIPT>

<SCRIPT language=javascript src="../js/select.js" type=text/javascript>
</SCRIPT>

  <script language="JavaScript" type="text/JavaScript">
  function submitt()
  {
  	document.forms[0].submit();
  }
  
  function dep()
{
	select(document.forms[0].user);
}
  </script>
  </head>
  
  <body>
  <br>
  <html:form action="/workplan.do?method=plancheckquery" target="bottommm">
    <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="4"  class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/>阶段计划审核查询</td>
  </tr>
  <html:hidden property="type"/>
  <tr>
    
    <td  class="tdbgcolorqueryright">计划人</td>
    <td class="tdbgcolorqueryleft">
<%--    <html:text property="user"/>--%>
     <html:text property="user"  ondblclick="dep()"></html:text>
    <img  src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="dep()" width="16" height="16" border="0"/> 
    </td>
    <td  class="tdbgcolorqueryright">查询类型:</td>
    <td class="tdbgcolorqueryleft">
       <html:select property="checkId">
      		<html:option value="">
      			请选择
      		</html:option>
    		<html:options collection="auditingList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
    
    </td>
  </tr>
<tr>
    
    <td  class="tdbgcolorqueryright">计划名</td>
    <td class="tdbgcolorqueryleft">
    <html:text property="planTitle"/>
    </td>
    <td  class="tdbgcolorqueryright">关键字</td>
    <td class="tdbgcolorqueryleft"><html:text property="planSubhead"/></td>
  </tr>
  <tr>
  	<td class="tdbgcolorqueryright">计划类型:</td>
  	<td class="tdbgcolorqueryleft" colspan="3">
		
  	       <html:select property="planTimeType">
      		<html:option value="">
      			请选择
      		</html:option>
    		<html:options collection="planTimeList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
    
    </td>
  	
  
 
  </tr>
  <tr>
    <td align="right" bgcolor="D3E8FD"><bean:message key="oa.privy.plan.begintime"/>介于</td>
    <td align="left" bgcolor="D3E8FD"><html:text property="planBeignTime" readonly="true" onfocus="calendar()"></html:text></td>
    <td  align="right" bgcolor="D3E8FD">和</td>
    <td align="left" bgcolor="D3E8FD"><html:text property="planEndTime" readonly="true" onfocus="calendar()"></html:text></td>
  </tr>
   <tr>
    <td  align="right" bgcolor="D3E8FD"><bean:message key="oa.privy.plan.endtime"/>介于</td>
    <td align="left" bgcolor="D3E8FD"><html:text property="planBeignTime1" readonly="true" onfocus="calendar()"></html:text></td>
    <td  align="right" bgcolor="D3E8FD">和</td>
    <td align="left" bgcolor="D3E8FD"><html:text property="planEndTime1" readonly="true" onfocus="calendar()"></html:text></td>
  </tr>
  
  <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom"><a href="javascript:submitt()" name="btnSearch"><bean:message bundle="sys" key="sys.select"/></a>
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
