
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
    
    <title>searchResult.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

	<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />

  </head>
  
  <body>
  
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr class="tdbgpiclist">
    <td><bean:message key="hl.bo.oa.asset.assetsId"/></td>
    <td><bean:message key="hl.bo.oa.asset.assetsName"/></td>
    <td><bean:message key="hl.bo.oa.asset.assetsType"/></td>
    <td><bean:message key="hl.bo.oa.asset.assetsOper"/></td>
    <td><bean:message key="hl.bo.oa.asset.oper"/></td>
  </tr>

  <logic:iterate name="list" id="l" indexId="i">
			<%
				String skty =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
  <tr>
    <td height="24" class=<%=skty%>><bean:write name="l" property="assetsId"/></td>
    <td class=<%=skty%>><bean:write name="l" property="assetsName"/></td>
    <td class=<%=skty%>>
    	<logic:equal name="l" property="assetsType" value="0"><bean:message key="hl.bo.oa.asset.guding"/></logic:equal>
    	<logic:equal name="l" property="assetsType" value="-1"><bean:message key="hl.bo.oa.asset.xiaohao"/></logic:equal>
    </td>
    <td class=<%=skty%>><bean:write name="l" property="assetsOper"/></td>
    <td align="center" class=<%=skty%>>
    <img alt="<bean:message key='hl.bo.oa.asset.oper'/>" src="<bean:write name='imagesinsession'/>sysoper/operator.gif" onclick="window.open('assetsOperAction.do?method=toOperAsset&did=<bean:write name='l' property='assetsId'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>
    <img alt="<bean:message key='hl.bo.oa.asset.see'/>" src="<bean:write name='imagesinsession'/>sysoper/look.gif" onclick="window.open('assetsOperAction.do?method=toAssetInfoLoad&type=v&did=<bean:write name='l' property='assetsId'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="_blank" border="0"/>
    </td>
  </tr>
  </logic:iterate>
  <tr>
    <td colspan="5">
    <page:page name="AssetInfoTurning" style="first"/>
    </td>
    </tr>
</table>
  <span class="tdbgcolor1"> </span>
  </body>
</html:html>
