
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
<SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>

  </head>
  
  <body>
  
      <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
  
  <html:form action="/workplan.do?method=createMission">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="4" class="tdbgpicload">工作计划</TD>
  </TR>
  <tr>
    <td class="tdbgcolorloadright">任务名</td>
    <td  class="tdbgcolorloadleft"><html:text property="name"/></td>
  
    <td class="tdbgcolorloadright">计划名</td>
    <td  class="tdbgcolorloadleft"><html:select property="planId">
    		<html:options collection="tl"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright">任务开始时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planBeignTime"  onfocus="calendar()"></html:text></TD>
  
    <td class="tdbgcolorloadright">任务结束时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planEndTime"  onfocus="calendar()"></html:text></TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright">优先级类型</TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionPriType">
    		<html:options collection="tl"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>

    <td class="tdbgcolorloadright">优先级</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionPri">
    		<html:options collection="tl"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
  </tr> 
  

  <tr>
    <td class="tdbgcolorloadright">任务信息</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="missionInfo" rows="5" cols="30"/></td>
  
    <td class="tdbgcolorloadright"></td>
    <td  class="tdbgcolorloadleft"></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><font color="red">任务标识</font></td>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionSign">
    		<html:option value="1" lable="完成结束"/>
    		<html:option value="0" lable="未完成结束"/>
    		<html:option value="2" lable="执行中"/>
    		<html:option value="3" lable="等待中"/>
    	</html:select></td>
  
    <td class="tdbgcolorloadright"></td>
    <td  class="tdbgcolorloadleft"></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><font color="red">任务完成信息</font></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="missionInfo" rows="5" cols="30"/></td>
  
    <td class="tdbgcolorloadright"><font color="red">备注</font></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="remark" rows="5" cols="30"></html:textarea></td>
  </tr>
  <tr>
    <td colspan="4"  class="tdbgcolorloadbuttom">
    
    <html:submit><bean:message bundle='sys' key='sys.update'/></html:submit>
    
    
  	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
