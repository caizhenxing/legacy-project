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
      <bean:message key="hl.bo.oa.resource.meeting.applyroom.titleT"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../../js/clockCN.js"></script>
    <script language="javascript" src="../../../js/clock.js"></script>
    <script language="JavaScript" src="../../js/validate-resource.js" ></script>
    <script language="JavaScript" src="../../../js/calendar.js" ></script>
    <script language="javascript">
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

      //添加人员List
      function addSelect() {
        var page = "meetingManager.do?method=toSelectUser&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj = document.meetingBean;

        window.showModalDialog(page, obj, winFeatures);
      }
    </script>
    
  </head>
  <body>
    <html:form action="/oa/meetingManager.do?method=applyMeeting" method="post" onsubmit="return validate_applyRoom(this)">
      <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <td colspan="2" class="tdbgpicload">
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.titleT"/>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.meetingName"/>
          </td>
          <td class="tdbgcolorloadleft">
            <html:select property="meetingName" styleClass="input">
              <html:option value="">请选择                
              </html:option>
              <html:optionsCollection name="resourceList" label="label" value="value" />
            </html:select>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
          	<bean:message key="hl.bo.oa.resource.meeting.applyroom.useDate"/>
          </td>
          <td class="tdbgcolorloadleft">
            <html:text property="useDate" size="10" readonly="" styleClass="input" onfocus="calendar()"/>&nbsp;</td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.timeAera"/>
          </td>
          <td class="tdbgcolorloadleft">
            <html:text maxlength="10" property="startHour" size="10" styleClass="input" readonly="true"/>
            <input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.startHour);"/>
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.timeline"/>
            <html:text maxlength="10" property="endHour" size="10" styleClass="input" readonly="true"/>
            <input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.endHour);"/>
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.to"/>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.applyReason"/>
          </td>
          <td class="tdbgcolorloadleft">
            <html:textarea property="applyReason" rows="6" cols="30" />
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright">
            <bean:message key="hl.bo.oa.resource.meeting.applyroom.applyUser"/>
          </td>
          <td class="tdbgcolorloadleft">
            <html:text maxlength="10" property="applyUser" size="10" styleClass="input"/>
            <html:button property="selectB" value="人 员" onclick="addSelect()" />
          </td>
        </tr>
        <tr>
          <td colspan="2" class="tdbgcolorloadbuttom">
            <input name="Submit" type="submit" class="bottom" value="提 交" />
          </td>
        </tr>
      </table>
    </html:form>
  </body>
  <logic:notEmpty name="page">
	<%
	    out.println("<script language=javascript>");
	    out.print("window.alert('");%><html:errors name="page"/><% out.println("')");
	    out.println("</script>");
	%>
  </logic:notEmpty>
</html:html>
