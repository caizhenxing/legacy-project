<%@ page language="java" pageEncoding="gb2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>
      <bean:message key="hl.bo.oa.message.addrList.messageInfo.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <logic:iterate id="employeeObj" name="employeeObjs">
        <tr>
          <td colspan="4" class="tdbgpicload">
              <img src="image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.message.addrList.messageInfo.title1" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.message.addrList.messageInfo.department" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="department" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.station" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="station" write="true" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.message.addrList.messageInf.name" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="name" write="true" />
          </td>
          <td class="tdbgcolorloadright">&nbsp;
            
          </td>
          <td class="tdbgcolorloadleft">&nbsp;
            
          </td>
        </tr>
        <tr>
          <td colspan="4" class="tdbgcolorloadright">
                <img src="image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.message.addrList.messageInfo.title2" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.ownEmail" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="ownEmail" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.otherEmail" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="otherEmail" write="true" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.other" />
          </td>
          <td class="tdbgcolorloadleft">

          </td>
          <td class="tdbgcolorloadright">
          	&nbsp;
          </td>
          <td class="tdbgcolorloadleft">
            
          </td>
        </tr>
        <tr>
          <td colspan="4" class="tdbgcolorloadright">
                &nbsp;
                <img src="image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.message.addrList.messageInfo.title3" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.mobile" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="mobile" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.companyPhone" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="companyPhone" write="true" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.homePhone" />
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="homePhone" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.other" />Æä Ëû£º
          </td>
          <td class="tdbgcolorloadleft">
              <html:hidden name="employeeObj" property="homePhone" write="true" />
          </td>
        </tr>
        <tr>
          <td colspan="4" class="tdbgcolorloadright">
                <img src="image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.message.addrList.messageInfo.title4" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.companyAddr" />
          </td>
          <td class="tdbgcolorloadleft">
            <html:hidden name="employeeObj" property="companyAddr" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.postCode" />
          </td>
          <td class="tdbgcolorloadleft">
            <html:hidden name="employeeObj" property="postCode" write="true" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.homeAddr" />
          </td>
          <td class="tdbgcolorloadleft">
            <html:hidden name="employeeObj" property="homeAddr" write="true" />
          </td>
          <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.message.addrList.messageInfo.homePost" />
          </td>
          <td class="tdbgcolorloadleft">
            <html:hidden name="employeeObj" property="homePost" write="true" />
          </td>
        </tr>
        <tr>
          <td colspan="4" class="tdbgcolorloadright">
                <img src="image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.message.addrList.messageInfo.other" />
          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="4" align="center" class="tdbgcolorloadbuttom">
            ¡º&nbsp;
            <a href="javascript:window.close()"><bean:message key="hl.bo.oa.message.addrList.messageInfo.close"/></a>
            &nbsp;¡»
        </td>
      </tr>
    </table>
  </body>
</html:html>
