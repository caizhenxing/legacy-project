
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
    
    <title>详细计划</title>
    
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
<SCRIPT language=javascript src="../js/form.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../js/select.js" type=text/javascript>
</SCRIPT>
<SCRIPT language="javascript">
   
	function back()
	{
		window.opener.document.location.reload();
		
		window.close();
	}	
	function dep()
	{
		select(document.forms[0].missionClasses);
	}
	function completeaa(ppp)
	{
		appendaa(ppp);
	}
</SCRIPT>
  </head>
  
  <body>
  
  
  <html:form action="/workmission.do?method=updateMission">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="4" class="tdbgpicload">工作详细计划</TD>
  </TR>
  <html:hidden property="id"/>
  <html:hidden property="type"/>
  <tr>
    <td class="tdbgcolorloadright">详细计划名</td>
    <td  class="tdbgcolorloadleft"><html:text property="name"/></td>
  
    <td class="tdbgcolorloadright">指派单位</td>
    <td  class="tdbgcolorloadleft"><html:text property="missionClasses"  ondblclick="dep()"></html:text>
    <img  src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="dep()" width="16" height="16" border="0"/> 
    <!-- 
    <td  class="tdbgcolorloadleft"><html:select property="missionClasses">
    		<html:options collection="depList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
    	 -->
  </tr>
  <tr>
    <td class="tdbgcolorloadright">关键字</td>
    <td  class="tdbgcolorloadleft"><html:text property="keyword"/></td>
  
    <td class="tdbgcolorloadright">详细计划类型</td>
    <td  class="tdbgcolorloadleft"> <html:select property="missionType">
    		<html:options collection="typeList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>	</td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright">详细计划开始时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="beginTime" readonly="true" onfocus="calendar()"></html:text></TD>
  
    <td class="tdbgcolorloadright">详细计划结束时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="endTime" readonly="true" onfocus="calendar()"></html:text></TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright">创建人</TD>
    <td  class="tdbgcolorloadleft"><html:text property="createUser" readonly="true"></html:text></TD>
  
    <td class="tdbgcolorloadright"></TD>
    <td  class="tdbgcolorloadleft"></TD>
  </TR>
  <!--<TR>
    <td class="tdbgcolorloadright">优先级类型</TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionPriType">
    		<html:options collection="priTypeList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>

    <td class="tdbgcolorloadright">优先级</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionPri">
    		<html:options collection="priList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
  </tr> 
  -->

  <tr>
    <td class="tdbgcolorloadright">详细计划信息</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="missionInfo" rows="25" cols="30" readonly="true"/>
    <img  src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="completeaa(missionInfo)" width="16" height="16" border="0" alt="填写详细情况"/> </td>
    
  <td class="tdbgcolorloadright">完成情况</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="missionComplete" rows="25" cols="30" readonly="true"></html:textarea>
    <img  src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="completeaa(missionComplete)" width="16" height="16" border="0" alt="填写完成情况"/> </td>
    
    </tr>
  
  <TR>
    <td class="tdbgcolorloadright">是否完成</TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="missionSign">
    <html:option value="">请选择</html:option>
    	<html:option value="1">完成</html:option>	
    	<html:option value="0">未完成</html:option>
    	<html:option value="2">未开始</html:option>
    	<html:option value="3">进行中</html:option>
    	<html:option value="-1">不能完成</html:option>
    	</html:select>
	</TD>
	<td class="tdbgcolorloadright">备注</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="remark" rows="5" cols="30"></html:textarea></td>
  
  </tr>
  
  <tr>
    <td colspan="4"  class="tdbgcolorloadbuttom">
   
    <html:submit onclick="window.close()"><bean:message bundle='sys' key='sys.update'/></html:submit>

  	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
