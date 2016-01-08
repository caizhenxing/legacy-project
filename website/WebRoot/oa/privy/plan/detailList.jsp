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
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		   <html:button  property="aa" style="button"  onclick="hideFrame()"> <bean:message bundle="sys" key="sys.hide"/> </html:button>
		  <tr class="tdbgpiclist">
		    <td><bean:message key="oa.privy.plan.type"/></td>
		    <td><bean:message key="oa.privy.plan.begintime"/></td>
		    <td><bean:message key="oa.privy.plan.endtime"/></td>
		    <td><bean:message bundle="sys" key="sys.oper"/></td>
		  </tr>
		  
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
  			String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="planSign" filter="true"/></td>
			<td class="<%=style%>"><bean:write name="c" property="beginDate" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="endDate" filter="true"/></td>
		   	<td class="<%=style%>">
		   	<img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('planDetail.do?method=info&type=update&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
			<a href="../planDetail.do?method=delete&id=<bean:write name='c' property='id'/>">
			<img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" target="windows" border="0"/>
			</a>
			<img alt="<bean:message bundle='sys' key='sys.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('planDetail.do?method=info&type=detail&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  
		</table>
    
  </body>
</html:html>
