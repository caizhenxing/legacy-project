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
      <bean:message key="hl.bo.oa.checkwork.resign.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="../js/validate_checkwork.js"></script>
    <script language="javascript" src="../assissant/conference/manage/js/clockCN.js"></script>
    <script language="javascript" src="../assissant/conference/manage/js/clock.js"></script>
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></script>
    <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></script>
    <script language="javascript">
    function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.absenceUser,"补签人员")) return false;
            if (!checkNotNull(addstaffer.repairDate,"补签日期")) return false;
            if (!checkNotNull(addstaffer.repairTime,"补签时间")) return false;
              return true;
            }
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
        var page        = "absenceWork.do?method=toUserList&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj         = document.AbsenceBean;

        window.showModalDialog(page, obj, winFeatures);
      }
      function s()
	{
	    //alert("wo shi shui");
	    var f =document.forms[0];
	    if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/absenceWork.do?method=addResign";
    	document.forms[0].submit();
    	}
	}
	//返回页面
<%--		function toback(){--%>
<%--			opener.parent.topp.document.all.Submit.click();--%>
<%--		}--%>
    </script>
  </head>
  <body>
    <html:form action="/oa/absenceWork.do?method=addResign" method="post" onsubmit="return validate_resign(this)">
      <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <td colspan="4" class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message key="et.oa.checkwork.load.repairRegister"/>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.checkwork.resign.department" /></td>
          <td  class="tdbgcolorloadleft">
            <html:select property="department" styleClass="input">
              <html:optionsCollection name="departLists" label="label" value="value" />
            </html:select>
          </td>
          <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.checkwork.resign.repairUser" /></td>
          <td  class="tdbgcolorloadleft">
            <html:select property="absenceUser">
              <html:option value=""><bean:message bundle="sys" key="sys.common.select"/></html:option>
              <html:optionsCollection name="nameList" label="label" value="value" styleClass="input"/>
            </html:select>
          </td>
        </tr>
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.load.repairDate" /></td>
          <td  class="tdbgcolorloadleft"><html:text maxlength="10" property="repairDate" size="10" styleClass="input" readonly="true" onfocus="calendar()"/></td>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.load.repairTime" /></td>
          <td  class="tdbgcolorloadleft"><input type="text" name="repairTime" maxlength="10" size="10"  Class="input">
          <input type="button" value="<bean:message bundle='sys' key='sys.common.time'/>" onclick="OpenTime(document.all.repairTime);"/>
          </td>
          
        </tr>
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.load.repairReason" /></td>
          <td  class="tdbgcolorloadleft" colspan="3"><html:textarea property="repairReason" rows="5" cols="40" /></td>
<%--          <td class="tdbgcolorloadright"></td>--%>
<%--          <td  class="tdbgcolorloadleft"></td>--%>
        </tr>
        
        <tr>
          <td colspan="4"  class="tdbgcolorloadbuttom">
             <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.submit'/>" onclick="s()"/>
            
          </td>
        </tr>
      </table>
    </html:form>
  </body>
  <logic:notEmpty name="page">
	<script>alert("<html:errors name='page'/>");window.close();</script>
	</logic:notEmpty>
  <logic:notEmpty name="page">

  </logic:notEmpty>
</html:html>
