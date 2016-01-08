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
    <html:form action="/oa/commoninfo/leaveWord" method="post">
        <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		    <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.leaveWord.seeLeaveWord'/>" onclick="seeQuery()"/>
		  	<input name="btnAdd" type="button" class="bottom" value="<bean:message key='et.oa.commoninfo.leaveWord.addLeaveWord'/>" onclick="popUp('windows','../commoninfo/leaveWord.do?method=toLeaveWordLoad&type=insert',680,400)"/>
		  	</td>
		   </tr>
		</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message bundle="sys" key="sys.common.name"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="sys" key="sys.common.time"/></td>
		    <td class="tdbgpiclist"><bean:message key="et.oa.commoninfo.leaveWord.title"/></td>
			<td class="tdbgpiclist"><bean:message bundle="sys" key="sys.oper"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="name" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="leaveDate" filter="true"/></td>
			<td class="<%=style%>"><bean:write name="c" property="title" filter="true"/></td>
		    <td align="center" class="<%=style%>">
		    <img alt="<bean:message bundle='sys' key='sys.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('leaveWord.do?method=toLeaveWordLoad&type=detail&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
            <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('leaveWord.do?method=toLeaveWordLoad&type=delete&id=<bean:write name='c' property='id'/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
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
