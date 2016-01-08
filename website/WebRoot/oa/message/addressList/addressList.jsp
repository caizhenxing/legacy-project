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
      <bean:message key="hl.bo.oa.message.addrList.list.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
      function popUp(win_name, loc, w, h, menubar, center) {
        var NS        = (document.layers) ? 1 : 0;
        var editorWin;

        if (w == null) {
          w = 500;
        }

        if (h == null) {
          h = 350;
        }

        if (menubar == null || menubar == false) {
          menubar = "";
        } else {
          menubar = "menubar,";
        }

        if (center == 0 || center == false) {
          center = "";
        } else {
          center = true;
        }

        if (NS) {
          w += 50;
        }

        if (center == true) {
          var sw = screen.width;
          var sh = screen.height;

          if (w > sw) {
            w = sw;
          }

          if (h > sh) {
            h = sh;
          }

          var curleft = (sw - w) / 2;
          var curtop  = (sh - h - 100) / 2;

          if (curtop < 0) {
            curtop = (sh - h) / 2;
          }

          editorWin = 
                  window.open(loc, win_name, 'resizable,scrollbars,width=' + w + ',height=' + h + ',left=' + curleft + ',top='
                  + curtop);
        } else {
          editorWin = window.open(loc, win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h);
        }

        editorWin.focus();
        //causing intermittent errors
      }
    </script>
  </head>
  <body>
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr class="tdbgpiclist">
        <td>
          <bean:message key="hl.bo.oa.message.addrList.list.name" />
        </td>
        <td>
          <bean:message key="hl.bo.oa.message.addrList.list.email" />
        </td>
        <td>
          <bean:message key="hl.bo.oa.message.addrList.list.mobile" />
        </td>
        <td>
          <bean:message key="hl.bo.oa.message.addrList.list.companyPhone" />
        </td>
        <td>
          <bean:message key="hl.bo.oa.message.addrList.list.moreT" />
        </td>
      </tr>
      <logic:iterate id="addressList" name="addressLists" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
        <tr>
          <td class="<%=style%>">
            <bean:write name="addressList" property="name" filter="true" />
          </td>
          <td class="<%=style%>">
            <bean:write name="addressList" property="email" filter="true" />
          </td>
          <td class="<%=style%>">
            <bean:write name="addressList" property="mobile" filter="true" />
          </td>
          <td class="<%=style%>">
            <bean:write name="addressList" property="companyPhone" filter="true" />
          </td>
          <td class="<%=style%>">
          	<img alt="<bean:message key='hl.bo.oa.message.addrList.list.more'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('addrList.do?method=addrListInfo&employeeId=<bean:write name="addressList" property="id"/>','windows','680.400,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>

          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="5">
            <page:page name="messageTurning" style="first"/>
        </td>
      </tr>
    </table>
  </body>
</html:html>
