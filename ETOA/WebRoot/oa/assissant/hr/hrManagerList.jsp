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
    <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body>
    <html:form action="/oa/assissant/hr" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.name"/></td>
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.sex"/></td>
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.birth"/></td>
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.department"/></td>
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.station"/></td>
		    <td><bean:message key="agrofront.oa.assissant.hr.hrManagerList.operation"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="name" filter="true"/></td>
		    <td class="<%=style%>"><logic:equal name="c" property="sex" value="0">Å®</logic:equal><logic:notEqual name="c" property="delMark" value="0">ÄÐ</logic:notEqual></td>
		    <td class="<%=style%>"><bean:write name="c" property="birth" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="department" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="station" filter="true"/></td>
		    <td align="center" class="<%=style%>">
		    <img alt="<bean:message key='agrofront.oa.assissant.hr.hrManagerList.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('hr.do?method=toHrLoad&type=detail&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    &nbsp;
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="6">
				<page:page name="agropageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
