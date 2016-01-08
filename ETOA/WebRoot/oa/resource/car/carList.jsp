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
     <bean:message key="hl.bo.oa.resource.car.list.title"/> 
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
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
            &nbsp;
            <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.list.title"/>
        </td>
      </tr>
      <tr class="tdbgpiclist">
        <td>
            <bean:message key="hl.bo.oa.resource.car.list.carCode"/>
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.car.list.carName"/>
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.car.list.carThing"/>
        </td>
        <td>
            <bean:message key="hl.bo.oa.resource.car.list.carRemark"/>
        </td>
        <td colspan="2" class="tdbgpiclist">
            <bean:message key="hl.bo.oa.resource.car.list.oper"/>
        </td>
      </tr>
      <logic:iterate id="carInfo" name="carInfos" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
        <tr>
          <td class="<%=style%>">
              <bean:write name="carInfo" property="carCode" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="carInfo" property="carName" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="carInfo" property="carThing" filter="true" />
          </td>
          <td class="<%=style%>">
              <bean:write name="carInfo" property="carRemark" filter="true" />
          </td>
          <td colspan="2" class="<%=style%>">
<%--              <html:link page ="/oa/carManager.do?method=uppCarInfo&operSign=update" paramId="id" paramName="carInfo" paramProperty="carId"><bean:message key="hl.bo.oa.resource.car.list.update"/></html:link>--%>
<%--              /<html:link page ="/oa/carManager.do?method=toDelPage&operSign=delete" paramId="id" paramName="carInfo" paramProperty="carId"><bean:message key="hl.bo.oa.resource.car.list.delete"/></html:link>--%>
                <a href="../../carManager.do?method=uppCarInfo&operSign=update&id=<bean:write name='carInfo' property='carId' filter='true'/>"><img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" width="16" height="16" target="windows" border="0"/></a>
                <a href="../../carManager.do?method=toDelPage&operSign=delete&id=<bean:write name='carInfo' property='carId' filter='true'/>">
		  	      <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" width="16" height="16" target="windows" border="0"/>
          	    </a>
          </td>
        </tr>
      </logic:iterate>
      <tr>
        <td colspan="6">
          <page:page name="carTurning" style="first"/>
        </td>
      </tr>
    </table>
  </body>
</html:html>
