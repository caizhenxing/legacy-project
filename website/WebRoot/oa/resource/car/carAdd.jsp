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
      <bean:message key="hl.bo.oa.resource.car.add.title"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <script language="JavaScript" src="../../js/validate-resource.js" ></script>
    <script language="javascript">
      //添加人员List
      function addSelect() {
        var page        = "carManager.do?method=toUserList&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj         = document.carOperBean;

        window.showModalDialog(page, obj, winFeatures);
      }
    </script>
  </head>
  <body>
      <logic:empty name="resourceInfo">
       <html:form action="/oa/carManager.do?method=addCarInfo" method="POST" onsubmit="return validate_addCar(this)">
        <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
            <td colspan="2" class="tdbgpicload">
              
              <bean:message key="hl.bo.oa.resource.car.add.titleT"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carCode"/>
            </td>
            <td class="tdbgcolorloadleft">
              
              <html:text property="carCode" size="20" styleClass="input"/>&nbsp;<bean:message key="et.oa.resource.car.forexample"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carName"/>
            </td>
            <td class="tdbgcolorloadleft">
              
              <html:text property="carName" size="20" styleClass="input"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carThing"/>
            </td>
            <td class="tdbgcolorloadleft">
                <html:textarea property="carThing" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carRemark"/>
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea property="carRemark" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.operUser"/>
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" property="operUser" size="10" styleClass="input"/>
              &nbsp;
              <input type="button" name="selectB" value="<bean:message key="et.oa.resource.car.driver"/>" onclick="addSelect()" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdbgcolorloadbuttom">
              <input name="Submit" type="submit" class="bottom" value="<bean:message bundle="sys" key="sys.submit"/>" />
            </td>
          </tr>
        </table>
        </html:form>
      </logic:empty>
      
<!-- 修改页面 -->
      <logic:notEmpty name="resourceInfo">
       <html:form action="/oa/carManager.do?method=updateCarInfo&operSign=update" method="POST" onsubmit="return validate_addCar(this)">
        <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <html:hidden name="resourceInfo" property="carId"/>
          <tr>
            <td  colspan="2" class="tdbgpicload">
              <bean:message key="hl.bo.oa.resource.car.add.titleT"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carCode"/>
            </td>
            <td class="tdbgcolorloadleft">
         
              <html:text name="resourceInfo" property="carCode" size="20" styleClass="input"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carName"/>
            </td>
            <td class="tdbgcolorloadleft">
              
              <html:text name="resourceInfo" property="carName" size="20" styleClass="input"/>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carThing"/>
            </td>
            <td class="tdbgcolorloadleft">
                <html:textarea name="resourceInfo" property="carThing" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.carRemark"/>
            </td>
            <td class="tdbgcolorloadleft">
             
              <html:textarea name="resourceInfo" property="carRemark" rows="6" cols="30" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.add.operUser"/>
            </td>
            <td class="tdbgcolorloadleft">
              <html:text name="resourceInfo" maxlength="10" property="operUser" size="10" styleClass="input"/>
              &nbsp;
              <input type="button" name="selectB" value="<bean:message key="et.oa.resource.car.driver"/>" onclick="addSelect()" />
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdbgcolorloadbuttom">
              <input name="Submit" type="submit" class="bottom" value="<bean:message bundle="sys" key="sys.submit"/>" />
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
