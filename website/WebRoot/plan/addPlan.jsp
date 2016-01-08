
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
<SCRIPT language=javascript src="../js/form.js" type=text/javascript>
</SCRIPT>
<SCRIPT language=javascript src="../js/calendar.js" type=text/javascript>
</SCRIPT>
<SCRIPT language="javascript">
    function checkForm(addstaffer){
        if (!checkNotNull(addstaffer.planTitle,"计划名称")) return false;
        
        
          return true;
         }
	function a()
	{
	   var f =document.forms[0];
       if(checkForm(f)){
		
    	document.forms[0].submit();
    	}
	}
	function c()
	{
		//window.close();
		//parent.mainFrame.location.href="fffffffffffffff";
		window.opener.parent.mainFrame.location.href="fffffffffffffff";
	}
	function d()
	{
		document.forms[0].action="/ETOA/oa/privy/plan.do?method=delete";
    	document.forms[0].submit();
	}
	function u()
	{
		document.forms[0].action="/ETOA/oa/privy/plan.do?method=update";
    	document.forms[0].submit();
	}
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		function back(){
        window.close();
}	
</SCRIPT>
  </head>
  
  <body>

  
  
  <html:form action="/workplan.do?method=createPlan">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="4" class="tdbgpicload">阶段计划</TD>
  </TR>
  <tr>
    <td class="tdbgcolorloadright">阶段计划标题</td>
    <td  class="tdbgcolorloadleft"><html:text property="planTitle"/></td>
  
    <td class="tdbgcolorloadright">阶段计划关键字</td>
    <td  class="tdbgcolorloadleft"><html:text property="planSubhead"/></td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright">阶段计划开始时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planBeignTime" readonly="true" onfocus="calendar()"></html:text></TD>
  	<td class="tdbgcolorloadright">阶段计划结束时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planEndTime" readonly="true" onfocus="calendar()"></html:text></TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright">阶段计划时间范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planTimeType">
    		<html:options collection="planTimeList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
    	<td class="tdbgcolorloadright"></td>
    <td  class="tdbgcolorloadleft">
    </td>
	
	<!--<td class="tdbgcolorloadright">阶段计划范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planDomainType">
    
    		<html:options collection="planDomainList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
    	-->
  </tr> 
  
  <!--  
  <tr>
  <td class="tdbgcolorloadright">上层计划</TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="parentId">
    <html:option value="">------</html:option>
    		<html:options collection="planList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
    
    <td class="tdbgcolorloadright">计划可视范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planViewType">
    		<html:options collection="planViewList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
  </tr> 
-->
  <tr>
    <td class="tdbgcolorloadright">阶段计划信息</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="planInfo" rows="5" cols="30"/></td>
  
    <td class="tdbgcolorloadright">备注</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="remark" rows="5" cols="30"></html:textarea></td>
  </tr>
  <tr>
    <td colspan="4"  class="tdbgcolorloadbuttom">
    
    <html:button onclick="a()" property="s"><bean:message bundle='sys' key='sys.insert'/></html:button>
    
    
  	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
