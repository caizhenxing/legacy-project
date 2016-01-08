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
    <html:form action="/oa/communicate/email.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="et.oa.commoninfo.book.booklist.bookname"/></td>
		    <td><bean:message key="et.oa.commoninfo.book.booklist.bookauthor"/></td>
		    <td><bean:message key="et.oa.commoninfo.book.booklist.bookstate"/></td>
		    <td><bean:message key="agrofront.common.operater"/></td>
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
		    <bean:write name="c" property="bookAuthor" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="borrowState" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message key='agrofront.common.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('book.do?method=toBookLoad&type=update&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
			<img alt="<bean:message key='agrofront.common.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('book.do?method=toBookLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
			<img alt="<bean:message key='et.oa.commoninfo.book.booklist.lose'/>" src="<bean:write name='imagesinsession'/>sysoper/lose.gif" onclick="window.open('book.do?method=toBookLoad&type=lose&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
			<img alt="<bean:message key='et.oa.commoninfo.book.booklist.morebookinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('book.do?method=toBookLoad&type=see&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		   <logic:equal name="c" property="borrowState" value="Î´½è³ö">
		   <img alt="<bean:message key='et.oa.commoninfo.book.booklist.borrowbook'/>" src="<bean:write name='imagesinsession'/>sysoper/borrowbook.gif" onclick="window.open('book.do?method=toBookLoad&type=borrow&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		   </logic:equal>
		   <logic:equal name="c" property="borrowState" value="ÒÑ½è³ö">
		   <img alt="<bean:message key='et.oa.commoninfo.book.booklist.getbackbook'/>" src="<bean:write name='imagesinsession'/>sysoper/returnbook.gif" onclick="window.open('book.do?method=toBookLoad&type=return&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		   <img alt="<bean:message key='et.oa.commoninfo.book.booklist.continueborrow'/>" src="<bean:write name='imagesinsession'/>sysoper/reborrow.gif" onclick="window.open('book.do?method=toBookLoad&type=reborrow&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>		   
		    </logic:equal>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="4">
				<page:page name="bookpageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
