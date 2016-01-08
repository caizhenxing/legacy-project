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
      <bean:message key="hl.bo.oa.checkwork.workchecklist.title" />
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
        <td class="tdbgpiclist">
          部门
        </td>
        <td class="tdbgpiclist">
          <bean:message key="hl.bo.oa.checkwork.workchecklist.name" />
        </td>
        <td class="tdbgpiclist">
          迟到
        </td>
        <td class="tdbgpiclist">
          早退
        </td>
        <td class="tdbgpiclist">
         未签到
        </td>
        <td class="tdbgpiclist">
          请假
        </td>
        <td class="tdbgpiclist">
          外出
        </td>
        <td class="tdbgpiclist">
          出差
        </td>
        <td class="tdbgpiclist">
          详细信息
        </td>
      </tr>
      <logic:iterate id="checkLists" name="checkLists" indexId="i">
      <%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  	  %>
        <tr>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="department" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="repairUser" filter="true" />
          </td>
          <td class=<%=style%>>
            <html:link action="/oa/checkWork.do?method=toLaterOrEarlyList&st=1&et=1" paramId="employeeId" paramName="checkLists" paramProperty="employeeId" onclick="popUp('windows','',480,400)" target="windows">
		    <bean:write name="checkLists" property="cz" filter="true" />
		    </html:link>
          </td>
          <td class=<%=style%>>
		    <a href="/ETOA/oa/checkWork.do?method=toLaterOrEarlyList&st=<bean:write name='checkLists' property='startT' filter='true'/>&et=<bean:write name='checkLists' property='endT' filter='true' />&employeeId=<bean:write name='checkLists' property='employeeId' filter='true' />">
		    <bean:write name="checkLists" property="zt" filter="true" />
		    </a>
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="weiqiandao" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="qj" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="wc" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="cc" filter="true" />
          </td>
          <td class=<%=style%>>
          <img alt="<bean:message key='agrofront.oa.assissant.hr.hrManagerList.detail'/>" src="<bean:write name='imagesinsession'/>sysoper/confirm.gif" onclick="window.open('checkWork.do?method=toLaterOrEarlyList&st=<bean:write name='checkLists' property='startT' filter='true'/>&et=<bean:write name='checkLists' property='endT' filter='true' />&employeeId=<bean:write name='checkLists' property='employeeId' filter='true' />','windows','480.400,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>
          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="8" >
            <page:page name="checkworkTurning" style="first"/>
          
        </td>
      </tr>
    </table>
  </body>
</html:html>
