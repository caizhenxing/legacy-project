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
      <bean:message key="hl.bo.oa.resource.car.apply.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <script language="JavaScript" src="../../js/validate-resource.js" ></script>
    <script language="JavaScript" src="../../../js/calendar.js" ></script>
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></script>
    <script language="javascript">
      // 时间页
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
        var page        = "carManager.do?method=toUserList&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj         = document.carOperBean;

        window.showModalDialog(page, obj, winFeatures);
      }

      function selectCarInfo() {
        document.forms[0].action = "/ETOA/oa/carManager.do?method=toApplyPage&pageSign=addCarPage";

        document.forms[0].submit();
      }
      
      function checkForm(addstaffer){
            if (!validate_applyCar(addstaffer)) return false;
                return true;
            }
      
      function checkFormApprove(addstaffer){
            if (!checkNotNull(addstaffer.principalUser,"审批人")) return false;
                return true;
            }
      
      function submitit(){
        var f =document.forms[0];
        //alert(checkForm(f));
        if(checkForm(f))
      	{
      	document.forms[0].submit();
      	}
      }
      
      function submititApprove(){
        var f =document.forms[0];
        //alert(checkForm(f));
        if(checkFormApprove(f))
      	{
      	document.forms[0].submit();
      	}
      }
    </script>
  </head>
  <body>
    
    <logic:empty name="sign">
<%--    添加申请--%>
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="tdbgpicload">
          <bean:message key="hl.bo.oa.resource.car.apply.titleT" />
        </td>
      </tr>
    </table>
      <html:form action="/oa/carManager.do?method=addApplyInfo" method="post" onsubmit="return validate_applyCar(this)">
        <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">
              <strong>
                &nbsp;
                <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.apply.titleF" />
              </strong>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.apply.applyUser" />
            </td>
            <td width="28%" class="tdbgcolorloadleft">
              <html:text maxlength="10" property="applyUser" size="10" styleClass="input"/>
            </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.apply.applyReason" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea property="applyReason" rows="6" cols="30" />
            </td>
            <td colspan="2" class="tdbgcolorloadleft">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.startDate" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" property="startDate" size="10" onfocus="calendar()" styleClass="input"/>
              &nbsp;</td>
            <td colspan="2" class="tdbgcolorloadright">
              
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.endDate" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" property="endDate" size="10" onfocus="calendar()" styleClass="input"/>
              &nbsp;
              </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">
                  &nbsp;
                  <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.apply.titleE" />

            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.operUser" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" property="operUser" size="10"  styleClass="input"/>
              <input type="button" name="selectB" value="<bean:message key="et.oa.resource.car.people"/>" onclick="addSelect()" />
            </td>
            <td width="21%" class="tdbgcolorloadleft">
                
            </td>
            <td width="30%" class="tdbgcolorloadright">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.code" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:select property="code" onchange="selectCarInfo()" styleClass="input">
                <html:optionsCollection name="codeList" label="label" value="value" />
              </html:select>
            </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td rowspan="2" class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.carInfo" />
            </td>
            <td colspan="3" rowspan="2" class="tdbgcolorloadleft">
              <logic:notEmpty name="carInfo">
                <bean:write name="carInfo" />
              </logic:notEmpty>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright"></td>
          </tr>
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">

            </td>
          </tr>
          <tr>
 			<td colspan="4" class="tdbgcolorloadbuttom">
                <input type="button" name="SubmitB" value="<bean:message bundle='sys' key='sys.submit'/>" onclick="submitit()" />
            </td>
          </tr>
        </table>
      </html:form>
    </logic:empty>
    <logic:notEmpty name="sign">
<%--    审批--%>
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="tdbgpicload">
         <bean:message key="et.oa.resource.car.carApprove"/>
        </td>
      </tr>
    </table>
      <html:form action="/oa/carApprove.do?method=doApprove" method="post">
        <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <html:hidden name="useList" property="id"/>
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">
              <strong>
                &nbsp;
                <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.apply.titleF" />
              </strong>
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.apply.applyUser" />
            </td>
            <td class="tdbgcolorloadleft" colspan="3">
              <html:text maxlength="10" name="useList" property="applyUser" size="10" readonly="true" styleClass="input"/>
            </td>
<%--            <td class="tdbgcolorloadright">--%>
<%----%>
<%--            </td>--%>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
              <bean:message key="hl.bo.oa.resource.car.apply.applyReason" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:textarea name="useList" property="applyReason" rows="6" cols="30" readonly="true" />
            </td>
            <td colspan="2" class="tdbgcolorloadright">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.startDate" />
                <bean:message key="et.oa.resource.car.begintime"/>
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" name="useList" property="startDate" size="10" readonly="true" styleClass="input"/>
              &nbsp;
            </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.endDate" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" name="useList" property="endDate" size="10" readonly="true" styleClass="input"/>
              &nbsp;
            </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">
                  <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.apply.titleE" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.operUser" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" name="useList" property="operUser" size="10" readonly="true" styleClass="input"/>
            </td>
            <td class="tdbgcolorloadright" colspan="2"></td>
<%--            <td class="tdbgcolorloadleft">--%>
<%--              &nbsp;--%>
<%--            </td>--%>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.code" />
            </td>
            <td class="tdbgcolorloadleft">
              <html:text maxlength="10" name="useList" property="code" size="10" readonly="true" styleClass="input"/>
            </td>
            <td colspan="2" class="tdbgcolorloadright">

            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.carInfo" />
            </td>
            <td colspan="3" class="tdbgcolorloadleft">
              <bean:write name="useList" property="carInfo" />
            </td>
          </tr>
<%--          <tr>--%>
<%--            <td class="tdbgcolorloadright"></td>--%>
<%--          </tr>--%>
          <tr>
            <td colspan="4" class="tdbgcolorloadleft">
                  <img src="../image/item_point.gif" width="4" height="7" /><bean:message key="hl.bo.oa.resource.car.apply.approveType" />
            </td>
          </tr>
          <tr>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.approveType" />
            </td>
            <td class="tdbgcolorloadleft">
                <html:select property="approveType" styleClass="input">
                  <html:option key="" value=""><bean:message key='et.oa.resource.car.select'/></html:option>
                  <html:option key="0" value="0">
                    <bean:message key="hl.bo.oa.resource.car.apply.noapproveType" />
                  </html:option>
                  <html:option key="2" value="2">
                    <bean:message key="hl.bo.oa.resource.car.apply.overapproveTyp" />
                  </html:option>
                </html:select>
            </td>
            <td class="tdbgcolorloadright">
                <bean:message key="hl.bo.oa.resource.car.apply.approveperson" />
            </td>
            <td class="tdbgcolorloadleft">
              &nbsp;
              <html:text maxlength="10" property="principalUser" size="10" styleClass="input"/>
            </td>
          </tr>
          <tr>
            <td colspan="4" class="tdbgcolorloadbuttom">
                <input type="button" name="SubmitB" value="<bean:message bundle="sys" key="sys.submit"/>" onclick="submititApprove()"/>
                <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:history.back();"/>
            </td>
          </tr>
        </table>
      </html:form>
    </logic:notEmpty>
  </body>
  <logic:notEmpty name="page">
<%
    out.println("<script language=javascript>");
    out.print("window.alert('");%><html:errors name="page"/><% out.println("')");
    out.println("</script>");
%>
  </logic:notEmpty>
</html:html>
