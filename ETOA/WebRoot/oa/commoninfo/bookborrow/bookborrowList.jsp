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
  </head>
  
  <body>
    <html:form action="/oa/commoninfo/bookborrow.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="et.oa.commoninfo.bookborrowlist.bookname"/></td>
		    <td><bean:message key="et.oa.commoninfo.bookborrowlist.borrowman"/></td>
		    <td><bean:message key="et.oa.commoninfo.bookborrowlist.borrowtime"/></td>
		    <td><bean:message key="et.oa.commoninfo.bookborrowlist.backtime"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <bean:write name='c' property='bookName' filter='true'/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="bookUser" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="borrowTime" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="returnTime" filter="true"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="bookhistorypageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
