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
		//∑µªÿ“≥√Ê
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()" bgcolor="#eeeeee">
    <logic:notEmpty name="operSign">
	<script>
	alert("<bean:message bundle='pccye' name='operSign'/>"); window.close();
	</script>
	</logic:notEmpty>
  
    <html:form action="/pcc/cclog" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload"><bean:message bundle="pccye" key="sys.common.detail"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright" width="30%"><bean:message bundle="pccye" key="et.pcc.cclog.phoneNum"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="phoneNum" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.beginTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="beginTime" styleClass="input" readonly="true"/></td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="et.pcc.cclog.operateTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="operateTime" styleClass="input"/></td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.endTime"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="endTime" styleClass="input" readonly="true"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pccye" key="sys.common.remark"/></td>
		    <td class="tdbgcolorqueryleft"><html:textarea property="remark" rows="5" cols="50"/></td>
		  </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message  bundle='pccye' key='sys.del'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='pccye' key='sys.close'/>" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
