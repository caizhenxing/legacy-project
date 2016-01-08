
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
<SCRIPT language="javascript">
    function checkForm(addstaffer){
        if (!checkNotNull(addstaffer.planTitle,"计划名称")) return false;
        if (!checkNotNull(addstaffer.beginDate,"开始时间")) return false;
        if (!checkNotNull(addstaffer.endDate,"结束时间")) return false;           
          return true;
         }
	function a()
	{
	   var f =document.forms[0];
       if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/privy/plan.do?method=insert";
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
  
      <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
  
  <html:form action="/oa/privy/plan.do">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="2" class="tdbgpicload"><bean:message key="oa.privy.plan.title"/></TD>
  </TR>
  <logic:equal name="type" value="detail">
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.id"/></TD>
    <td  class="tdbgcolorloadleft"><html:text property="id"></html:text></TD>
  </TR>
  </logic:equal>
  <logic:equal name="type" value="update">
  <html:hidden property="id"/>
  </logic:equal>
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.name"/></TD>
    <td  class="tdbgcolorloadleft"><html:text property="planTitle"></html:text></TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.type"/></TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="planType">
    		<html:options collection="tl"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
  </TR>
  
  
 
 
  <logic:equal name="type" value="detail">
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.user"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="employeeId"/></td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.time"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="planDate"/></td>
  </tr>
  </logic:equal>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.begintime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="beginDate" onfocus="calendar()"/></td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.endtime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="endDate" onfocus="calendar()"/></td>
  </tr>
  <logic:notEqual name="type" value="update">
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.approveman"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="approveMan"/></td>
  </tr> 
  </logic:notEqual>
  <logic:equal name="type" value="detail">
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.approvetime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="approveTime"/></td>
  </tr> 
  </logic:equal>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.remark"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="remark" rows="5" cols="30"></html:textarea></td>
  </tr>
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="insert">
    <html:button property="botton1" onclick="a()"><bean:message bundle='sys' key='sys.insert'/></html:button>
    </logic:equal>
    <logic:equal name="type" value="update">
    <html:button property="botton2"  onclick="u()"><bean:message bundle='sys' key='sys.update'/></html:button>
    </logic:equal>
    <logic:equal name="type" value="detail">
  		<html:button  property="botton3" onclick="back()"><bean:message bundle='sys' key='sys.back'/></html:button>
  	</logic:equal>
  	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
