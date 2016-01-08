<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

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
      <bean:message key="hl.bo.oa.resource.car.approve.title"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
  </head>
  <body>
    <html:form action="/oa/carApprove.do?method=toApprovePage&sign=select" method="post">
      <table width="605" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
         <tr>
          <td colspan="5" class="tdbgcolorquerytitle">
          		<bean:message bundle="sys" key="sys.current.page"/>
                <bean:message key="hl.bo.oa.resource.car.approve.titleT"/>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorqueryright">
              
          </td>
          <td class="tdbgcolorqueryright">
              <bean:message key="hl.bo.oa.resource.car.approve.selectword"/>
          </td>
          <td class="tdbgcolorqueryleft">
              <html:select property="approveType">
                <html:option key="" value=""><bean:message key='et.oa.resource.car.select'/></html:option>
                <html:option key="0" value="0" ><bean:message key='hl.bo.oa.resource.car.approve.noapproveType'/></html:option>
                <html:option key="1" value="1" ><bean:message key='hl.bo.oa.resource.car.approve.waitapproveType'/></html:option>
                <html:option key="2" value="2" ><bean:message key='hl.bo.oa.resource.car.approve.overapproveType'/></html:option>
              </html:select>
          </td>
          <td class="tdbgcolorqueryleft">
              <input type="submit" name="SubmitB" value="<bean:message key='et.oa.resource.car.search'/>" />
          </td>
          <td class="tdbgcolorqueryleft">
          
          </td>
        </tr>
        <tr>
          <td class="tdbgpiclist">
              <bean:message key="hl.bo.oa.resource.car.approve.applyUser"/>
          </td>
          <td class="tdbgpiclist">
              <bean:message key="hl.bo.oa.resource.car.approve.applyReason"/>
          </td>
          <td class="tdbgpiclist">
              <bean:message key="hl.bo.oa.resource.car.approve.code"/>
          </td>
          <td class="tdbgpiclist">
              <bean:message key="hl.bo.oa.resource.car.approve.state"/>
          </td>
          <td class="tdbgpiclist">
              <bean:message key="hl.bo.oa.resource.car.approve.oper"/>
          </td>
        </tr>
        <logic:iterate id="carUserList" name="carUserLists" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
          <tr>
            <td class="<%=style%>">
                <bean:write name="carUserList" property="applyUser" filter="true" />
            </td>
            <td class="<%=style%>">
                <bean:write name="carUserList" property="applyReason" filter="true" />
            </td>
            <td class="<%=style%>">
                <bean:write name="carUserList" property="code" filter="true" />
            </td>
            <td class="<%=style%>">
                <bean:write name="carUserList" property="state" filter="true" />
            </td>
            <td class="<%=style%>">
<%--                <html:link page="/oa/carApprove.do?method=ApprovePage" paramId="id" paramName="carUserList" paramProperty="id">--%>
<%--                  <bean:message key="hl.bo.oa.resource.car.approve.something"/>--%>
<%--                </html:link>--%>
                <a href="../../carApprove.do?method=ApprovePage&id=<bean:write name='carUserList' property='id' filter='true'/>">
		  	      <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" width="16" height="16"  border="0"/>
          	    </a>
            </td>
          </tr>
        </logic:iterate>
        <tr>
          <td colspan="5">
              <page:page name="carTurning" style="first"/>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
    <logic:notEmpty name="approve">
<%
    out.println("<script language=javascript>");
    out.print("window.alert('");%><html:errors name="approve"/><% out.println("')");
    out.println("</script>");
%>
  </logic:notEmpty>
</html:html>
