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
      <bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
  </head>
  <body>
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td align="center" class="tdbgpic1">
          <div align="left">
            &nbsp;
            <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.titleT" />
          </div>
        </td>
        <td align="center" class="tdbgpic1">
          &nbsp;
        </td>
        <td align="center" class="tdbgpic1">
          &nbsp;
        </td>
        <td align="center" class="tdbgpic1">
          <div align="right">
            <input type="submit" name="Submit" value="Ìí ¼Ó">
          </div>
        </td>
      </tr>
      <tr class="tdbgpiclist">
        <td>
            <bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.meetingName" />
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.meetingThing" />
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.meetingRemark" />
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.meeting.meetingoperlist.oper" />
        </td>
      </tr>

      <logic:iterate id="meetInfoList" name="meetInfoLists" indexId="i">
      		<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
        <tr>
          <td class="<%=style%>">
              <bean:write name="meetInfoList" property="meetingName" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="meetInfoList" property="meetingThing" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="meetInfoList" property="meetingRemark" filter="true" />
          </td>
          <td class="<%=style%>">
          	<img alt="<bean:message key='hl.bo.oa.resource.meeting.meetingoperlist.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="window.open('payOrder.do?method=tableone&oper=add','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
			<img alt="<bean:message key='hl.bo.oa.resource.meeting.meetingoperlist.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="window.open('payOrder.do?method=tableone&oper=add','windows','350.650,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="4">
            <page:page name="resourceTurning" style="first"/>
        </td>
      </tr>
    </table>
  </body>
</html:html>
