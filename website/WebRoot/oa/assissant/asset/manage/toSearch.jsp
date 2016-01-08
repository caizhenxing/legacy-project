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
    
    <title>toSearch.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

	<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script>
	function s()
	{
		document.forms[0].action="/ETOA/oa/assissant/asset/assetsOperAction.do?method=search";
    	document.forms[0].submit();
	}
	function so()
	{
		document.forms[0].action="/ETOA/oa/assissant/asset/assetsOperAction.do?method=searchOper";
    	document.forms[0].submit();
	}
	function a()
		{
			window.open("/ETOA/oa/assissant/conference/conferOper.do?method=toLoad&type=i","a");
		}
	function assetinfo()
	{
		window.open("/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetInfoLoad&type=i","assetinfo","width=600,height=300");
	}
	function assetoper()
	{
		window.open("/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetOperLoad&type=i","assetoper","width=600,height=400");
	}	
</script>
  </head>
  
  <body>
  <br>
  <html:form action="/oa/assissant/asset/assetsOperAction" target="mainFrame">
    <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="tdbgcolorquerytitle">
    <bean:message bundle="sys" key="sys.current.page"/>
    <bean:message key='hl.bo.oa.asset.assetManageApp'/>
    </td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.asset.assetsId'/></td>
    <td class="tdbgcolorqueryleft"><html:text property="assetsId"></html:text></td>
    <td class="tdbgcolorqueryright">
						<bean:message key='hl.bo.oa.asset.assetsName'/>
				</td>
    <td class="tdbgcolorqueryleft"><html:text property="assetsName"></html:text></td>
  </tr>
  <tr>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.asset.assetsType'/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="assetsType">
    						<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:option value="0"><bean:message key='hl.bo.oa.asset.guding'/></html:option>
							<html:option value="-1"><bean:message key='hl.bo.oa.asset.xiaohao'/></html:option>
						</html:select></td>
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.asset.assetsOper'/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="assetsOper" >
    						<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:options collection="bl"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
    </td>
  </tr>
  
  <tr>
    <td colspan="4" class="tdbgcolorquerybuttom">&nbsp;
	    <INPUT name="Submit" type="button" class="bottom" value="<bean:message key='agrofront.common.search'/>" onclick="s()">&nbsp;
	    <INPUT name="reset" type="reset" value="<bean:message key='agrofront.common.cannal'/>">&nbsp;
	    <INPUT name="addgov" type="button" class="buttom" value="<bean:message key='hl.bo.oa.asset.assetsCheckIn'/>" onclick="assetoper()">&nbsp;
	    <INPUT name="addgov" type="button" class="buttom" value="<bean:message key='hl.bo.oa.asset.addInfo'/>" onclick="assetinfo()">
  	</td>
  </tr>
  
</table>
</html:form>
  </body>
</html:html>
