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
      <bean:message key="hl.bo.oa.resource.meeting.roominfo.title"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
    function delSelectIt(){
			if(confirm("确定要彻底删除选中的文章吗？一旦删除将不能恢复！")){
				document.forms[0].action = "../opernews.do?method=operRecycleInfo&oper=delselect";
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
    </script>
  </head>
  <body>
    <logic:notEmpty name="page">
     <%
      out.println("<script language=javascript>");
      out.print("window.alert('");%><html:errors name="page"/><% out.println("')");
      out.println("</script>");
     %>
   </logic:notEmpty>
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td colspan="6" class="tdbgcolorquerytitle">
            <bean:message bundle="sys" key="sys.current.page"/>
            <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.meeting.roominfo.title"/>
        </td>
      </tr>
      <tr>
        <td class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.meeting.roominfo.meetingName"/>
        </td>
        <td class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.meeting.roominfo.meetingRemark"/>
        </td>
        <td class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.meeting.roominfo.meetingThing"/>
        </td>
        <td class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.meeting.roominfo.meetingPrincipal"/>
        </td>
        <td colspan="2" class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.meeting.roominfo.oper"/>
        </td>
      </tr>
      <logic:iterate id="meetingInfo" name="meetingInfos" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
        <tr>
          <td class="<%=style%>">
              <bean:write name="meetingInfo" property="meetingName" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="meetingInfo" property="meetingRemark" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="meetingInfo" property="meetingThing" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="meetingInfo" property="meetingPrincipal" filter="true" />
          </td>
          <td colspan="2" class="<%=style%>">
<%--              <html:link page="/oa/meetingManager.do?method=toUppPage&operSign=update" paramId="id" paramName="meetingInfo" paramProperty="meetingId">--%>
<%--                <bean:message key="hl.bo.oa.resource.meeting.roominfo.update"/>--%>
<%--              </html:link>--%>
<%--              /--%>
<%--              <html:link page="/oa/meetingManager.do?method=toDelPage&operSign=delete" paramId="id" paramName="meetingInfo" paramProperty="meetingId">--%>
<%--                <bean:message key="hl.bo.oa.resource.meeting.roominfo.detele"/>--%>
<%--              </html:link>--%>
<%--            <a href="../meetingManager.do?method=toUppPage&operSign=update&id=<bean:write name='meetingInfo' property='meetingId' filter='true'>">--%>
<%--          	<img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" target="windows" border="0"/>--%>
<%--		  	</a>--%>
<%--		  	<a href="../meetingManager.do?method=toDelPage&operSign=delete&id=<bean:write name='meetingInfo' property='meetingId' filter='true'>">--%>
<%--		  	<img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" target="windows" border="0"/>--%>
<%--          	</a>--%>
                <a href="../../meetingManager.do?method=toUppPage&operSign=update&id=<bean:write name='meetingInfo' property='meetingId' filter='true'/>"><img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16"  border="0"/></a>
                <a href="../../meetingManager.do?method=toDelPage&operSign=delete&id=<bean:write name='meetingInfo' property='meetingId' filter='true'/>">
		  	      <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16"  border="0"/>
          	    </a>
<%--		    <a href="../addressListSort.do?method=toAddressListSortLoad&type=delete&addressListSign=personal&id=<bean:write name='c' property='id'/>"><img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" border="0"  /></a> --%>
          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="6">
          <page:page name="meetingTurning" style="first"/>
        </td>
      </tr>
    </table>
  </body>
</html:html>
