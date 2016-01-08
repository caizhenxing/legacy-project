
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>info.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/clockCN.js"></script>
<script language="javascript" src="js/clock.js"></script>

<script language="javascript" src="../../../../js/form.js"></script>
<script language="javascript" src="../../../../js/calendar.js"></script>
<SCRIPT>
	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.synodDate,"<bean:message key='hl.bo.oa.conference.info.date'/>")) return false;
            if (!checkNotNull(addstaffer.synodHour,"<bean:message key='hl.bo.oa.conference.info.conferenceDate'/>")) return false;
            if (!checkNotNull(addstaffer.synodAddr,"<bean:message key='hl.bo.oa.conference.info.conferenceAdd'/>")) return false;
            if (!checkNotNull(addstaffer.synodOwner,"<bean:message key='hl.bo.oa.conference.info.synodOwner'/>")) return false;
            if (!checkComma(addstaffer.synodOwner,"<bean:message key='hl.bo.oa.conference.info.message1'/>")) return false;
            if (!checkNotNull(addstaffer.synodPeople,"<bean:message key='hl.bo.oa.conference.info.synodPeople'/>")) return false;
            if (!checkNotNull(addstaffer.synodTopic,"<bean:message key='hl.bo.oa.conference.info.synodTopic'/>")) return false;
           // if (!checkCodeRange(addstaffer.code, "身份证号", 15, 18)) return false;
              return true;
    }

	function a()
	{
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=add";
	    	document.forms[0].submit();
	    }
	}
	function c()
	{
		
		//parent.mainFrame.location.href="fffffffffffffff";
		//window.opener.parent.mainFrame.location.href="/ETOA/sys/group/GroupOper.do?method=search";
		window.close();
	}
	function d()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=del";
    	document.forms[0].submit();
	}
	function u()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=update";
    	document.forms[0].submit();
	}
	function ud()
	{
	
	}
	
	//添加人员List
    function addSelect()
    {
		var page = "/ETOA/oa/assissant/conference/conferOper.do?method=toSelectUser&page=resign";
		var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
		var obj = document.confereceForm;
		window.showModalDialog(page,obj,winFeatures);
	}
	//
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
</SCRIPT>
  </head>
  
  <body>
  <html:form action="/oa/assissant/conference/conferOper">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key='hl.bo.oa.conference'/></td>
  </tr>
   <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.conferenceDate'/></td>
    <td class="tdbgcolorloadleft">
						<bean:message key='hl.bo.oa.conference.info.date'/>
						<html:text property="synodDate" size="10" readonly="true" onfocus="calendar()"></html:text>
						&nbsp;&nbsp;
						<bean:message key='hl.bo.oa.conference.info.time'/>
						<html:text property="synodHour" size="5" readonly="true"></html:text>
						<input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.synodHour);"/>
						<html:hidden property="id" />
				</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.conferenceAdd'/></td>
    <td class="tdbgcolorloadleft">						
						<html:select property="synodAddr">
							<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:options collection="al"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodOwner'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="synodOwner" readonly="true"></html:text>
						<input type="button" name="selectB" value="<bean:message key='hl.bo.oa.conference.info.personnel'/>" onclick="addOwnerSelect()"/>
				</td>
  </tr> 
  <!-- -->
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodPeople'/></td>
    <td class="tdbgcolorloadleft">
						<html:textarea property="synodPeople" readonly="true"/>
						<input type="button" name="selectB" value="<bean:message key='hl.bo.oa.conference.info.personnel'/>" onclick="addSelect()"/>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodTopic'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="synodTopic"></html:text>
				</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodOutline'/></td>
    <td class="tdbgcolorloadleft">
						<html:textarea property="synodOutline" />
						<html:hidden property="applyTime"></html:hidden>
				</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.info_remark'/></td>
    <td class="tdbgcolorloadleft">
						<html:textarea property="remark" />
						<html:hidden property="flowId"></html:hidden>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright">
					<bean:message bundle='sys' key='sys.workflow.workflowinstance'/>
				</td>
    <td class="tdbgcolorloadleft">
						<html:text property="flowId"></html:text>
				</td>
  </tr> 
  <!-- 审批 -->
  <logic:equal name="type" value="exam">
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.workflow.check'/></td>
    <td class="tdbgcolorloadleft"><bean:message bundle='sys' key='sys.workflow.select'/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodTopic'/></td>
    <td class="tdbgcolorloadleft">
						<html:select property="examResult">
							<html:option value=""><bean:message bundle='sys' key='sys.workflow.nocheck'/></html:option>
							<html:option value="1"><bean:message bundle='sys' key='sys.workflow.checkpass'/></html:option>
							<html:option value="0"><bean:message bundle='sys' key='sys.workflow.checkfalse'/></html:option>
						</html:select>
				</td>
  </tr> 
  </logic:equal>
  <!-- 审批 -->
  <!-- -->
  <tr>
    <td colspan="2" class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="i">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="a()"/>
    </logic:equal>
    <logic:equal name="type" value="u">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="u()"/>
    </logic:equal>
    <logic:equal name="type" value="exam">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='hl.bo.oa.conference.info.examResult'/>" onclick="exam()"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="d()"/>
    </logic:equal>
    
    <input name="cccc" type="button" value="<bean:message key='agrofront.common.close'/>" onclick="c()"/>
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
