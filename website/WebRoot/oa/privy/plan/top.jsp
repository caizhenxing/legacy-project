<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>



<script type="text/javascript" language="JavaScript">
  
//在载入时对页面进行调整
    	function loadFrame(){
    		parent.dict.rows = "30%,*,40%";
    	}
    	//隐藏不显示的那部分
    	function hideFrame(){
    		parent.dict.rows = "30%,*,0%";
    	}
  // -->
</script>
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
    <script language="javascript" src="../../../js/common.js"></script>
  </head>
  
  <body>
  <html:form action="/oa/privy/plan.do?method=check">
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
  <TR>
    <td colspan="2" class="tdbgpicload"><bean:message key="oa.privy.plan.title"/></TD>
  </TR>
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
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.user"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="employeeId"/></td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.time"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="planDate"/></td>
  </tr>
 	<tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.begintime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="beginDate"/></td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.endtime"/></td>
    <td  class="tdbgcolorloadleft"><html:text property="endDate"/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="oa.privy.plan.remark"/></td>
    <td  class="tdbgcolorloadleft"><html:textarea property="remark"></html:textarea></td>
  </tr>
  
</table>
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.type"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.begintime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.endtime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.content"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.carryinfo"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="planSign" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="beginDate" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="endDate" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planInfo" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="carryInfo" filter="true"/></td>
		   </tr>
		  </logic:iterate>
		</table>
</html:form>
  </body>
</html:html>
