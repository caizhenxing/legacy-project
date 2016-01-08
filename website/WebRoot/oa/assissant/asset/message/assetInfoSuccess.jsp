
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
    
    <title><bean:message key='agrofront.common.success'/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script>
	function c()
	{
		window.opener.parent.mainFrame.location.href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=search";
		window.close();
	}
	function ao(str)
	{		
		window.location.href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=checkBatchNum&AssetPC="+str;
	}
</script>
  </head>
  
  <body>
     <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" class="tdbgpic1"><bean:message key='hl.bo.oa.asset.assetManageApp'/></td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td width="100%" class="tdbgcolorloadright"><div align="center"><bean:message key='agrofront.common.success'/></div></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadbuttom"><div align="center">
    <logic:greaterThan name="rnum" value="0">
    <input name="Submit" type="submit" class="bottom" value="下一个" onclick="ao('<bean:write name='assetsOper'/>')" />	
    </logic:greaterThan>
      <input name="Submit" type="submit" class="bottom" value="<bean:message key='agrofront.common.close'/>" onclick="c()" />
    </div></td>
  </tr>
</table>
  </body>
</html:html>
