
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
    
    <title><bean:message key='hl.bo.oa.conference'/></title>
    
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
    function checkComma(comma, content)
    {
    	var ec =comma.value.indexOf(",");
    	var cc =comma.value.indexOf("，");
    	//alert(ec+"..."+cc);    	
    	if(ec !=-1 || cc !=-1)
    	{
	    	alert(content);
    		return false;
    	}
    	else
    	{			    	
    		return true;	
    	}
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
	function addOwnerSelect()
    {
		var page = "/ETOA/oa/assissant/conference/conferOper.do?method=toSelectOwnerUser&page=resign";
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
	function exam(did)
	{
		var f =document.forms[0];
    	if(checkForm(f)){
    		var flag =confirm("<bean:message key='hl.bo.oa.conference.info.message2'/>");
			if(flag ==true)
			{
	    		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=exam&did="+did;
		    	document.forms[0].submit();
    		}
    		else
    		{
    		
    		}
	    }
	}
	function sub()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=end";
    	document.forms[0].submit();
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
						<input type="button" value="<bean:message key='hl.bo.oa.conference.info.time'/>" onclick="OpenTime(document.all.synodHour);" />
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
  <!-- 审批 -->
  <!-- 
  		已审批显示审批人
  		未审批显示修改
  		驳回显示修改
  -->
  <logic:equal name="type" value="end">
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.docAndSummary'/></td>
    <td class="tdbgcolorloadleft"><bean:message key='hl.bo.oa.asset.select'/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.summary'/></td>
    <td class="tdbgcolorloadleft">
						<html:textarea property="endDoc"></html:textarea>
				</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.conference.info.synodFile'/></td>
    <td class="tdbgcolorloadleft">					
					<html:text property="synodFile" readonly="true"></html:text>
						<a href="/ETOA/oa/assissant/doc.do?method=toDocLoad&type=insertConferenceDoc&did=<bean:write name='confereceForm' property='id' />" target="_blank"><bean:message key='hl.bo.oa.conference.info.docHref'/></a>
				</td>
  </tr>
  </logic:equal>
  <!-- 审批 -->
  <!-- -->
  <tr>
    <td colspan="2" class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="end">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.submit'/>" onclick="sub()"/>
    </logic:equal>
    <logic:equal name="type" value="exam">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='hl.bo.oa.conference.info.examResult'/>" onclick="exam('<bean:write name='confereceForm' property='id'/>')"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="d()"/>
    </logic:equal>
    <input name="Submit3" type="reset" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" />
    <input name="cccc" type="button" value="<bean:message key='agrofront.common.close'/>" onclick="c()"/>
    
    </td>
  </tr>
</table>
</html:form>
<logic:present name="docPath">
  	<script>
  		document.forms[0].synodFile.value="<bean:write name='docPath'/>";
  	</script>
  </logic:present>
  </body>
</html:html>
