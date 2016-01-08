<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
    <html:form action="/oa/communicate/email.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="et.oa.communicate.emailbox.emailboxlist.emailboxname"/></td>
		    <td><bean:message key="et.oa.communicate.emailbox.emailboxlist.emailboxaddr"/></td>
		    <td><bean:message key="et.oa.communicate.emailbox.emailboxlist.emailsmtp"/></td>
		    <td><bean:message key="et.oa.communicate.emailbox.emailboxlist.emailpop"/></td>
		    <td><bean:message bundle="sys" key="sys.oper"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <bean:write name='c' property='name' filter='true'/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="mailboxname" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="smtp" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="pop" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message key='agrofront.common.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('emailbox.do?method=toEmailBoxLoad&type=update&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message key='agrofront.common.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('emailbox.do?method=toEmailBoxLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5">
				<page:page name="mailboxpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
