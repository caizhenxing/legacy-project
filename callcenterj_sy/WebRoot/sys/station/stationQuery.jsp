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
    
    <title>岗位管理查询</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
   <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript">
   
    	//查询
    	function query(){
    		document.forms[0].action = "station.do?method=toStationList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    		window.close();
    	}
    	
    </script>
    
    
  </head>
  
  <body>
	
    <html:form action="/sys/station/station.do" method="post">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message bundle="sys" key="sys.current.page"/>
		    <bean:message bundle="sys" key="sys.station.stationquery.depmanager"/>
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="sys" key="sys.station.stationquery.seldep"/></td>
		    <td class="tdbgcolorqueryleft">
		    <html:select property="departmentid">		
        	<html:option value="" ><bean:message bundle="sys" key="sys.station.stationquery.pleaseselect"/></html:option>
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		    <td class="tdbgcolorqueryright"></td>
		    <td class="tdbgcolorqueryleft"></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="sys" key="sys.station.stationquery.stationname"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="deppersonname" styleClass="input"/></td>
		    <td class="tdbgcolorqueryright"><bean:message bundle="sys" key="sys.station.stationquery.stationperson"/></td>
		    <td class="tdbgcolorqueryleft"><html:text property="deplevel" styleClass="input"/></td>
		  </tr>
		  
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="button" value="<bean:message bundle="sys" key='sys.select'/>" onclick="query()"/>
<%--		    <input name="btnAdd" type="button" class="button" value="<bean:message bundle="sys" key='sys.insert'/>" onclick="popUp('windows','station.do?method=toStationLoad&type=insert',650,250)"/>--%>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
