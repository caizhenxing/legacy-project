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
   <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript">
    	//≤È—Ø
    	function query(){
    		document.forms[0].action = "../role/Role.do?method=toRoleList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/sys/role/Role" method="post">
      	<table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  			<tr>
    			<td colspan="4"  class="tdbgcolorquerytitle"><bean:message bundle="sys" key="sys.current.page"/><bean:message bundle='sys' key='sys.role'/></td>
  			</tr>
  			<tr>
            <td  class="tdbgcolorqueryright"><bean:message bundle="sys" key="sys.role.roleManagerQuery.roleName"/></td>
            <td class="tdbgcolorqueryleft"><html:text property="name"></html:text></td>
            </tr>
            
		  <tr>
		    <td colspan="2"  class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerQuery.query'/>" onclick="query()"/>
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerQuery.add'/>" onclick="popUp('windows','../role/Role.do?method=toRoleLoad&type=insert',480,300)"/>
		    </td>
		    
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
