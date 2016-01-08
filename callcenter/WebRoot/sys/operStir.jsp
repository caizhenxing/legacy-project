<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

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
    <script>
	
	function openurl(u)
		{
			window.open("/callcenter/pcc/policeinfo/info.do?method=toInfoMain"+"&id="+u,"newwindows"+u);
		}
</script>
  </head>
  
  <body>
	<APPLET  code="et/bo/cc/applet/AppJApplet" archive="CCApplet.jar" width=750 height=120 MAYSCRIPT>
	<PARAM NAME=ip VALUE=192.168.1.2>
	</APPLET>
  </body>
</html:html>
