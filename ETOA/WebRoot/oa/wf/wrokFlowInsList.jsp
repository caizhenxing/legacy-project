<%@ page 
language="java"
import="java.util.*"
contentType="text/html; charset=GBK"
pageEncoding="GBK"
%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>list.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  <logic:iterate id="FlowBean1" name="wlist" >
    <tr>
    <td  align="center" class=""  >
		    <html:link action="/flow.do?method=create" paramId="id" paramName="FlowBean1" paramProperty="id" onclick="popUp('windows','',480,400)" target="windows" >
		    <bean:write name="FlowBean1" property="name"/>
		   
		    </html:link>
			</td> 
	</tr>
	</logic:iterate><br/>
	------------------------------------------------------
    <br/>
    <logic:iterate id="FlowBean" name="list" >
    <tr>
    <td  align="center" class=""  >
		    <html:link action="/flow.do?method=load" paramId="id" paramName="FlowBean" paramProperty="id" onclick="popUp('windows','',480,400)" target="windows" >
		    <bean:write name="FlowBean" property="name"/>
		    <bean:write name="FlowBean" property="time"/>
		    </html:link>
			</td>
			
			    
		  </tr>
	</logic:iterate>
  </body>
</html:html>
