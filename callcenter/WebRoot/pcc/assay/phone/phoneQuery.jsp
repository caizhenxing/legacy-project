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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	//≤È—Ø
    	function query(){
    		document.forms[0].action = "../phone.do?method=toPhoneList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
    
    
  </head>
  
  <body bgcolor="#eeeeee">
	
    <html:form action="/pcc/assay/phone.do" method="post">
      	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    <bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.title"/>
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.time"/></td>
		    <td class="tdbgcolorqueryleft">	    	
				<html:select property="operatetime">	
					<html:option value=""><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
					<html:option value="3"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.3in"/></html:option>
					<html:option value="5"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.5in"/></html:option>
					<html:option value="10"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.10in"/></html:option>
					<html:option value="30"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.30in"/></html:option>
					<html:option value="30m"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.30m"/></html:option>
				</html:select>
		    </td>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pcc" key="et.pcc.assay.phone.phonequery.errorphone"/></td>
		    <td class="tdbgcolorqueryleft">
			    <html:select property="isvalidin">		
	        		<html:option value=""><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
	        		<html:optionsCollection name="errorphone" label="label" value="value"/>
	        	</html:select>
		    </td>
		  </tr>
          <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="begintime" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(begintime)"/></td>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="endtime" styleClass="input" readonly="true" />
		    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endtime)"/></td>
		  </tr>
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle="pcc" key='sys.search'/>" onclick="query()"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
