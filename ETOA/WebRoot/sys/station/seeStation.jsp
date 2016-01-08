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
    
    <title><bean:message bundle="sys" key="sys.station.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />

  </head>
  
  <body onunload="toback()">
  
    <html:form action="/sys/station/station.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    	<bean:message bundle="sys" key="sys.station.seestation.moreinfo"/>
		    </td>
		  </tr>

		   <logic:iterate id="c" name="list">
			  <tr>
			    <td class="tdbgcolorloadright">
			     <bean:write name="c" property="personname" filter="true"/>
			    </td>
			    <td class="tdbgcolorloadleft">
			     <bean:write name="c" property="personlist" filter="true"/>
			    </td>
			  </tr>
		   </logic:iterate>

		 
		  <tr>
		    <td colspan="2">
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
