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
    
    <title><bean:message bundle="pcc" key="et.pcc.fuzz.fuzzload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  
  <body bgcolor="#eeeeee">
  
    <html:form action="/pcc/policefuzz/fuzz.do" method="post">
		<table width="100%" border="0" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message bundle="pcc" key="et.pcc.fuzz.updatePoliceNum.errortitle"/></td>
		  </tr>
		  <tr>
		    <td colspan="2" align="center">
		    <bean:message bundle="pcc" name="errors"/>
		    </td>
		  </tr>

		  <tr>
		    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>" onclick="javascript:history.back();"/>
		    </td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
