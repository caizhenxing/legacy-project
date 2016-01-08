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
    <html:form action="/oa/privy/addressList" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="et.oa.privy.addressList.name"/></td>
		    <td><bean:message key="et.oa.privy.addressList.email"/></td>
		    <td><bean:message key="et.oa.privy.addressList.peronsalPhone"/></td>
		    <td><bean:message key="et.oa.privy.addressList.fax"/></td>
		    <td><bean:message key="et.oa.privy.addressList.mobile"/></td>
		    <td><bean:message bundle="sys" key="sys.oper"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="name" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="personalEmail" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="personalPhone" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="fax" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="mobile" filter="true"/></td>
		    <td align="center" class="<%=style%>">
		    <img alt="<bean:message key='agrofront.oa.assissant.hr.hrManagerList.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=detail&addressListSign=detail&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <logic:equal name="c" property="sign" value="0">
		    <img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=update&addressListSign=company&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=delete&addressListSign=company&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </logic:equal>
		    <logic:equal name="c" property="sign" value="1">
		    <img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=update&addressListSign=personal&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=delete&addressListSign=personal&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </logic:equal>
		    <logic:equal name="c" property="sign"  value="2"> 
			<img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=update&addressListSign=common&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('addressList.do?method=toAddressListLoad&type=delete&addressListSign=common&id=<bean:write name="c" property="id"/>','windows','480.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </logic:equal>
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
