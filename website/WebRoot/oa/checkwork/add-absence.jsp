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
      <bean:message key="hl.bo.oa.checkwork.addabsence.title" />
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
            if (!checkNotNull(addstaffer.absenceUser,"<bean:message bundle='sys' key='sys.common.name'/>")) return false;
            if (!checkNotNull(addstaffer.startDate,"<bean:message bundle='sys' key='sys.common.beginTime'/>")) return false;
            if (!checkNotNull(addstaffer.endDate,"<bean:message bundle='sys' key='sys.common.endTime'/>")) return false;
<%--            if (!checkNotNull(addstaffer.age,"年龄")) return false;--%>
<%--            if (!checkIntegerRange(addstaffer.age,"年龄",10,60)) return false;--%>
<%--            if (!checkNotNull(addstaffer.birth,"出生日期")) return false;--%>
<%--            if (!checkNotNull(addstaffer.code,"身份证号")) return false;--%>
<%--            if (!checkLength2(addstaffer.code, "身份证号", 18, 15)) return false;--%>
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
      function sq()
	{
	    var f =document.forms[0];
	    if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/absenceWork.do?method=addAbsence&type=qingjia";
    	document.forms[0].submit();
    	}
	}
	function sw()
	{
	    var f =document.forms[0];
	    if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/absenceWork.do?method=addAbsence&type=waichu";
    	document.forms[0].submit();
    	}
	}
	function sc()
	{
	    var f =document.forms[0];
	    if(checkForm(f)){
		document.forms[0].action="/ETOA/oa/absenceWork.do?method=addAbsence&type=chuchai";
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
    <html:form action="/oa/absenceWork.do?method=addAbsence" method="POST" onsubmit="return validate_addabsence(this)">
      <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <logic:equal name="operType" value="qingjia">
		     <td colspan="4" class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message key="et.oa.checkwork.load.qingjiaRegister"/></td>
		     <html:hidden property="absenceType" value="1"/>
		  </logic:equal>
          <logic:equal name="operType" value="waichu">
		     <td colspan="4" class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message key="et.oa.checkwork.load.waichuRegister"/></td>
		     <html:hidden property="absenceType" value="2"/>
		  </logic:equal>
		  <logic:equal name="operType" value="chuchai">
		     <td colspan="4" class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message key="et.oa.checkwork.load.chuchaiRegister"/></td>
		     <html:hidden property="absenceType" value="3"/>
		  </logic:equal>
        </tr>
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.department" /></td>
          <td class="tdbgcolorloadleft">
            <html:select property="department" styleClass="input">
              <html:optionsCollection name="departLists" label="label" value="value" />
            </html:select>
          </td>
          <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.common.name" /></td>
          <td class="tdbgcolorloadleft">
<%--          <html:text  property="absenceUser"  styleClass="input"/>--%>
          <html:select property="absenceUser">
              <html:option value=""><bean:message bundle="sys" key="sys.common.select" /></html:option>
              <html:optionsCollection name="nameList" label="label" value="value" styleClass="input"/>
            </html:select>
          </td>
          
        </tr>
<%--        <tr>--%>
<%--          <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.checkwork.addabsence.absenceTypeT" /></td>--%>
<%--          <td class="tdbgcolorloadleft">--%>
<%--            <html:select property="absenceType" styleClass="input">--%>
<%--              <html:option value="">--%>
<%--                <bean:message key="hl.bo.oa.checkwork.addabsence.select" />--%>
<%--              </html:option>--%>
<%--              <html:optionsCollection name="absenceType" label="label" value="value" />--%>
<%--            </html:select>--%>
<%--          </td>--%>
<%--          <td class="tdbgcolorloadright"></td>--%>
<%--          <td class="tdbgcolorloadleft"></td>--%>
<%--        </tr>--%>
        
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.laterOrEarly.beginDate" /></td>
          <td class="tdbgcolorloadleft"><html:text property="startDate" styleClass="input" readonly="ture" onfocus="calendar()"/>
          <br><input type="text" name="sTime" maxlength="10" size="10"  Class="input">
          <input type="button" value="<bean:message bundle='sys' key='sys.common.time'/>" onclick="OpenTime(document.all.sTime);"/></td>
          
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.laterOrEarly.endDate" /></td>
          <td class="tdbgcolorloadleft"><html:text property="endDate" styleClass="input" readonly="true" onfocus="calendar()"/>
          <br><input type="text" name="eTime" maxlength="10" size="10"  Class="input">
          <input type="button" value="<bean:message bundle='sys' key='sys.common.time'/>" onclick="OpenTime(document.all.eTime);"/></td>
        </tr>
        
        <tr>
          <td class="tdbgcolorloadright"><bean:message key="et.oa.checkwork.load.absenceReason" /></td>
          <td class="tdbgcolorloadleft" colspan="3"><html:textarea property="reason" rows="5" cols="40" /></td>
<%--          <td class="tdbgcolorloadright"></td>--%>
<%--           <td class="tdbgcolorloadleft"></td>--%>
        </tr>
        <tr>
          <td colspan="4"  class="tdbgcolorloadbuttom">
             <logic:equal name="operType" value="qingjia">
		     <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.submit'/>" onclick="sq()"/>         
		  </logic:equal>
          <logic:equal name="operType" value="waichu">
		     <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.submit'/>" onclick="sw()"/>         
		  </logic:equal>
		  <logic:equal name="operType" value="chuchai">
		     <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.submit'/>" onclick="sc()"/>         
		  </logic:equal> 
		  <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='sys' key='sys.cancel'/>" />       
          </td>
        </tr>
      </table>
    </html:form>
  </body>

    <logic:notEmpty name="page">
	<script>alert("<html:errors name='page'/>");window.close();</script>
	</logic:notEmpty>
</html:html>
