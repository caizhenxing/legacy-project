<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
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
		//返回页面
		function toback(){
<%--			opener.parent.topp.document.all.btnSearch.click();--%>
		}
    </script>
  </head>
  
  <body onunload="toback()" bgcolor="#eeeeee">
  
    <html:form action="/pcc/cclog" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
	      <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.policenum"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.name"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.birthday"/></td>
		    <td>职务</td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.byunit"/></td>
		  </tr>
		  <tr>
		    <td><bean:write name="cclogBean" property="fuzzNo" filter="false"/></td>
		    <td><bean:write name="cclogBean" property="name" filter="false"/></td>
		    <td><bean:write name="cclogBean" property="birthday" filter="false"/></td>
		    <td><bean:write name="cclogBean" property="tagPoliceKind" filter="false"/></td>
		    <td><bean:write name="cclogBean" property="tagUnit" filter="false"/></td>
		  </tr>
		</table>
		<br/>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor" style="table-layout: fixed;word-wrap: break-word">
		  <tr>
		  	<td colspan="3" align="center" class="tdbgcolorloadbuttom">
		  		<bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.questioninfo"/>
		  	</td>
		  </tr>
		  <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.fuzzno"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.question"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.content"/></td>
		  </tr>
		  <logic:iterate id="c" name="list">
		  <tr>
		    <td class="tdbgcolorlist1"><bean:write name="c" property="fuzzno" filter="false"/></td>
		    <td class="tdbgcolorlist1"><bean:write name="c" property="quInfo" filter="false"/></td>
		    <td class="tdbgcolorlist1"><bean:write name="c" property="content" filter="false"/></td>
		    <!--  
		    <td class="tdbgcolorlist1">
		    <img alt="<bean:message bundle='pcc' key='et.pcc.assay.question.questionlist.moreinfo'/>" src="<bean:write name='imagesinsession'/>sysoper/particular.gif" onclick="window.open('phoneinfo/phone.do?method=toInfoLoad&type=see&tempid=<bean:write name='c' property='id'/>','windowsnew','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		    -->
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="3" align="center" class="tdbgcolorloadbuttom">
		    	<input name="addgov" type="button" class="buttom" value="<bean:message key='agrofront.common.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
