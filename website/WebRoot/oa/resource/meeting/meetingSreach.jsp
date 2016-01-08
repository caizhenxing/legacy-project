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
      <bean:message key="hl.bo.oa.resource.meeting.sreach.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../../js/clockCN.js"></script>
    <script language="javascript" src="../../../js/clock.js"></script>
    <script language="JavaScript" src="../../../js/calendar.js" ></script>
    <script language="javascript">
      //查询
      function query() {
        document.forms[0].action = "/ETOA/oa/meetingManager.do?method=sreachMeetingUse";
        document.forms[0].target = "bottomm";

        document.forms[0].submit();
      }

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
          editorWin = window.open(loc, win_name, menubar + 'resizable,scrollbars=auto,width=' + w + ',height=' + h);
        }
      }

      editorWin.focus();

      //causing intermittent errors// 日历
      function openwin(param) {
        var aResult = showCalDialog(param);

        if (aResult != null) {
          param.value = aResult;
        }
      }

      function showCalDialog(param) {
        var url     = "checkwork/calendar.html";
        var aRetVal = showModalDialog(url, "status=no", "dialogWidth:182px;dialogHeight:215px;status:no;");

        if (aRetVal != null) {
          return aRetVal;
        }

        return null;
      }
    </script>
  </head>
  <body>
    <html:form action="/oa/meetingManager.do" method="post">
      <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <td colspan="4" class="tdbgcolorquerytitle">
          	<bean:message bundle="sys" key="sys.current.page"/>
            <bean:message key="hl.bo.oa.resource.meeting.sreach.title" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorqueryright">
            使用时间从
          </td>
          <td class="tdbgcolorqueryleft">
            <html:text property="startDate" size="20" readonly="true" styleClass="input" onclick="calendar()"/>
<%--            <br>--%>
<%--            <html:text maxlength="10" property="startHour" size="10" styleClass="input"/>--%>
<%--            <input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.startHour);"/>--%>
          </td>
          <td class="tdbgcolorqueryright">
            到
          </td>
          <td class="tdbgcolorqueryleft">
            <html:text property="endDate" size="20" readonly="true" styleClass="input" onclick="calendar()"/>
<%--            <br>--%>
<%--            <html:text maxlength="10" property="endHour" size="10" styleClass="input"/>--%>
<%--            <input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.endHour);"/>--%>
          </td>
          </tr>
          
        <tr>
          <td class="tdbgcolorqueryright">
            会议室名
          </td>
          <td class="tdbgcolorqueryleft">
            <html:select property="meetingNames" styleClass="input">		
                  <html:option value="">请选择</html:option>
                  <html:optionsCollection name="meetingRoomNames" label="label" value="value"/>
            </html:select>
          </td>
          <td class="tdbgcolorqueryright">
            申请人员
          </td>
          <td class="tdbgcolorqueryleft">
           <html:text  property="applyUser"  styleClass="input"/>
          </td>
          </tr>
          <tr>
          <td colspan="4"  class="tdbgcolorquerybuttom">
            <html:button property="SubmitB"  onclick="query()"><bean:message bundle="sys" key="sys.select" /></html:button>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
