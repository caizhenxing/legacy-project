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
    <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript" src="../../js/common.js"></script>
  </head>
  
  <body>
    <html:form action="/sys/station/station.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message bundle="sys" key="sys.station.stationlist.dowhat"/></td>
		    <td><bean:message bundle="sys" key="sys.station.stationlist.person"/></td>
		    <td><bean:message bundle="sys" key="sys.station.stationlist.describe"/></td>
		    <td><bean:message  bundle="sys" key="sys.oper"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <bean:write name='c' property='personname' filter='true'/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="deplevel" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="describe" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message bundle="sys" key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="popUp('windows','station.do?method=toStationLoad&type=update&id=<bean:write name='c' property='id'/>',650,350)" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message bundle="sys" key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="popUp('windows','station.do?method=toStationLoad&type=delete&id=<bean:write name='c' property='id'/>',650,350)" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="stationpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
