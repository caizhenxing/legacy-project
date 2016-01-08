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
      <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <script language="JavaScript" src="../../js/validate-resource.js" ></script>
    <SCRIPT language="javascript">
      //添加人员List
      function addSelect() {
        var page        = "meetingManager.do?method=toUserList&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj         = document.meetingBean;

        window.showModalDialog(page, obj, winFeatures);
      }
    </SCRIPT>
  </head>
  <body>
    <logic:empty name="meetingInfo">
      <html:form action="/oa/meetingManager.do?method=addMeetingInfo" method="POST" onsubmit="return validate_addmeetroom(this)">
        <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
            <td colspan="2" class="tdbgpicload">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.titleT" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingName" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text property="meetingName" size="20" styleClass="input"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingThing" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea property="meetingThing" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingRemark" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea property="meetingRemark" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingPrincipal" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" property="meetingPrincipal" size="10" styleClass="input"/>
              &nbsp;
              <html:button property="selectB" value="人员" onclick="addSelect()" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdbgcolorloadbuttom">
              <input name="Submit" type="submit" class="bottom" value="提 交" />
            </td>
          </tr>
        </table>
      </html:form>
    </logic:empty>
    <logic:notEmpty name="meetingInfo">
      <html:form action="/oa/meetingManager.do?method=updateMeetingInfo" method="POST">
        <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <html:hidden name="meetingInfo" property="meetingId" />
          <tr>
            <td colspan="2" class="tdbgpicload">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.titleT" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingName" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text name="meetingInfo" property="meetingName" size="20" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingThing" />
            </td>
            <td class="tdbgcolorloadleft">
              <span class="tdbgcolor2"><html:textarea name="meetingInfo" property="meetingThing" rows="6" cols="30" /></span>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingRemark" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea name="meetingInfo" property="meetingRemark" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.meeting.addmeetroom.meetingPrincipal" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" name="meetingInfo" property="meetingPrincipal" size="10" />
              &nbsp;
              <html:button property="selectB" value="人员" onclick="addSelect()" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdbgcolorloadbuttom">
              <input name="Submit" type="submit" class="bottom" value="提 交" />
            </td>
          </tr>
        </table>
      </html:form>
    </logic:notEmpty>
    <logic:notEmpty name="page">
<%
      out.println("<script language=javascript>");
      out.print("window.alert('");%><html:errors name="page"/><% out.println("')");
      out.println("</script>");
%>
    </logic:notEmpty>
  </body>
</html:html>
