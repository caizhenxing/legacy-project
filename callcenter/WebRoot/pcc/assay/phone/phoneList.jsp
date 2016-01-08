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
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/pcc/assay/phone.do" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonelist.phonenum"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonelist.opertime"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonelist.endtime"/></td>
		    <td class="tdbgpiclist"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonelist.operator"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <bean:write name="c" property="phone" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="operatetime" filter="true"/>
            </td>
		    <td class="<%=style%>">
		    <bean:write name="c" property="endtime" filter="true"/>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message bundle='pcc' key='et.pcc.assay.phone.phonelist.moreinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('phone.do?method=toPhoneLoad&type=see&id=<bean:write name='c' property='id'/>','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="3">
				<page:page name="phonepageTurning" style="first"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
