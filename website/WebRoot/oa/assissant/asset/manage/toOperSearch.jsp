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
		window.open("/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetInfoLoad&type=i","assetinfo");
	}
	function assetoper()
	{
		window.open("/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetOperLoad&type=i","assetoper");
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
    <td class="tdbgcolorqueryright"><bean:message key='hl.bo.oa.asset.oper'/></td>
    <td class="tdbgcolorqueryleft">
    <html:select property="operType">
	    					<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
	    					<html:option value="mairu"><bean:message key='hl.bo.oa.asset.mairu'/></html:option>
	    					<html:option value="zhuanru"><bean:message key='hl.bo.oa.asset.zhuanru'/></html:option>
							<html:option value="zhuanchu"><bean:message key='hl.bo.oa.asset.zhuanchu'/></html:option>
							<html:option value="maichu"><bean:message key='hl.bo.oa.asset.maichu'/></html:option>
							<html:option value="zhejiu"><bean:message key='hl.bo.oa.asset.zhejiu'/></html:option>
							<html:option value="baofei"><bean:message key='hl.bo.oa.asset.baofei'/></html:option>
							<html:option value="fenpei"><bean:message key='hl.bo.oa.asset.fenpei'/></html:option>
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
	    &nbsp;<INPUT name="Submit" type="button" class="bottom" value="<bean:message key='hl.bo.oa.asset.assetOperSearch'/>" onclick="so()">&nbsp;	    
	    <INPUT name="reset" type="reset" value="<bean:message key='agrofront.common.cannal'/>">&nbsp;
	    
	    
  	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
