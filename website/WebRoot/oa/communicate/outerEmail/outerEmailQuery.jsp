<%@ page language="java" pageEncoding="gb2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    <title>
      <bean:message key="hl.bo.oa.message.addrList.query.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />

  </head>
  <body>
    <html:form action="/oa/communicate/outemail.do" method="post">
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      	<logic:iterate id="c" name="list">
        <tr>
		  <td>
		  	<a href="../outemail.do?method=toEmailLoad&type=take&emailid=<bean:write name='c' property='id' filter='true'/>" target="operationframe">
		  	<img src="<bean:write name='imagesinsession'/>email/email.gif" width="40" height="35" border="0"/>
			<bean:write name="c" property="emailname" filter="true"/>
			</a>
			<br/>
          </td>
        </tr>
        </logic:iterate>
      </table>
    </html:form>
  </body>
</html:html>
