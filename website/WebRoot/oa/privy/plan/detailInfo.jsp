
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
    
    <title>info.jsp</title>
    
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
<SCRIPT>
    function checkForm(addstaffer){
        if (!checkNotNull(addstaffer.beginDate,"<bean:message key='oa.privy.plan.begintime'/>")) return false;
        if (!checkNotNull(addstaffer.endDate,"<bean:message key='oa.privy.plan.endtime'/>")) return false; 
        if (!checkNotNull(addstaffer.planInfo,"<bean:message key='oa.privy.plan.content'/>")) return false;          
          return true;
         }
	function a()
	{
	    var f =document.forms[0];
        if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/privy/planDetail.do?method=insert";
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
		document.forms[0].action="/ETOA/oa/privy/planDetail.do?method=delete";
    	document.forms[0].submit();
	}
	function u()
	{
		document.forms[0].action="/ETOA/oa/privy/planDetail.do?method=update";
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
  <html:form action="/oa/privy/planDetail.do">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="2" class="tdbgpicload"><bean:message key="oa.privy.plan.title"/></TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.type"/></TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="planSign">
    		<html:options collection="tl"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
  </TR>
  
  <logic:equal name="type" value="update">
  <html:hidden property="id"/>
  </logic:equal>
  
   <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.begintime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="beginDate" onfocus="calendar()"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.endtime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="endDate" onfocus="calendar()"/>
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.content"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="planInfo"/></td>
  </tr> 
  
  <logic:equal name="type" value="detail">
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carryuser"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="carryUser"/></td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carryinfo"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="carryInfo"></html:textarea></td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carrystate"/></TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="carryState">
    <html:option value="0"><bean:message key="oa.privy.plan.carrystate.over"/></html:option>
    		<html:option value="1"><bean:message key="oa.privy.plan.carrystate.no"/></html:option>
    </html:select>
	</TD>
  </TR>
  </logic:equal>
  <logic:equal name="type" value="insert">
  <html:hidden property="planId"/>
  </logic:equal>
  <logic:equal name="type" value="back">
  <html:hidden property="id"/>
   <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carryuser"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="carryUser"/></td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carryinfo"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="carryInfo"></html:textarea></td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.carrystate"/></TD>
    <td class="tdbgcolorloadleft">
    <html:select property="carryState">
    		<html:option value="0"><bean:message key="oa.privy.plan.carrystate.over"/></html:option>
    		<html:option value="1"><bean:message key="oa.privy.plan.carrystate.no"/></html:option>
    </html:select>
	</TD>
  </TR>
  </logic:equal>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.remark"/></td>
    <td class="tdbgcolorloadleft"><html:textarea property="remark"></html:textarea></td>
  </tr>
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="insert">
    <html:button property="aa" onclick="a()"><bean:message bundle='sys' key='sys.insert'/></html:button>
    </logic:equal>
    <logic:equal name="type" value="update">
    <html:button  property="bb"  onclick="u()"><bean:message bundle='sys' key='sys.update'/></html:button>
    </logic:equal>
    <logic:equal name="type" value="detail">
  			<p><html:button property="cc"  onclick="back()"><bean:message bundle='sys' key='sys.back'/></html:button></p>
  		</logic:equal>
  <logic:equal name="type" value="back">
    <html:button  property="dd"   onclick="u()"><bean:message bundle='sys' key='sys.submit'/></html:button>
    </logic:equal>
    
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
