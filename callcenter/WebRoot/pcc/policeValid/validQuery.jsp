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
		
    	//查询
    	function query(){
    		document.forms[0].action = "valid.do?method=toValidList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    </script>

  </head>
  
  <body bgcolor="#eeeeee">
	
    <html:form action="/pcc/policeValid/valid.do" method="post">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    警务人员注册信息查询列表
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorqueryright">警号</td>
		    <td class="tdbgcolorqueryleft">
		    <html:text property="fuzzNo" styleClass="input"/>
		    </td>
		    <td class="tdbgcolorqueryright">姓名</td>
		    <td class="tdbgcolorqueryleft"><html:text property="name" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">身份证</td>
		    <td class="tdbgcolorqueryleft">
		    <html:text property="idcard" styleClass="input"/>
		    </td>
		    <td class="tdbgcolorqueryright">部门</td>
		    <td class="tdbgcolorqueryleft">
		    <html:select property="department">		
        		<html:option value="0" ><bean:message bundle="pcc" key="sys.pleaseselect"/></html:option>
        		<html:optionsCollection name="ctreelist" label="label" value="value"/>
        	</html:select>
		    </td>
		  </tr>
		  
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    <input name="btnSearch" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.search'/>" onclick="query()"/>
		    </td>
		  </tr>
		  
		</table>
    </html:form>
  </body>
</html:html>
