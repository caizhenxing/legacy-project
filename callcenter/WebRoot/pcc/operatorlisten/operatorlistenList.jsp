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
  
  <body bgcolor="#eeeeee">
    <html:form action="/pcc/operatorlisten/operatorlisten.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message bundle="pcc" key="et.pcc.operatorlisten.operatorlistenList.fuzzno"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.operatorlisten.operatorlistenList.phonenum"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.operatorlisten.operatorlistenList.begintime"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.operatorlisten.operatorlistenList.endtime"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.operatorlisten.operatorlistenList.recfile"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="fuzzno" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="phonenum" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="begintime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="endtime" filter="true"/></td>
		    <td class="<%=style%>">
		    <a href="<bean:write name='c' property='recfile' filter='true'/>" target="_blank">
		    <bean:write name="c" property="recfile" filter="true"/>
		    </a>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5">
				<page:page name="operatorListenpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
