
<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>公告列表</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

	<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
   <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" class="tdbgpicload"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top" bgcolor="#F2F2F7" class="tdbgpicload"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38" valign="middle"><img src="<bean:write name='imagesinsession'/>button_main_title.gif"/></td>
            <td width="890" height="26" align="left" class="td2">公告</td>
            <td width="42" align="center"></td>
            <td width="48" align="left">
            <a href="../mainOper.do?method=toMain">
										<IMG src="<bean:write name='imagesinsession'/>minimize.gif" border="0">
										</a></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" class="tdbgpicmain"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <logic:iterate id="c" name="list">
          <tr>
            <td width="5%" align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
            <td width="68%" class="tdbgpic2">
            <a href="../operaffiche.do?method=toAficheInfo&id=<bean:write name='c' property='id' filter='true'/>">
            <bean:write name="c" property="title" filter="true"/></a>
            </td>
            <td width="11%" align="center" class="tdbgpicmain"><bean:write name="c" property="author" filter="true"/></td>
            <td width="16%" align="center" class="tdbgpicmain"><bean:write name="c" property="newsTime" filter="true"/></td>
          </tr>
          </logic:iterate>
        </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="84%" align="right" class="tdbgcolorloadbuttom">
                  	<page:page name="agropageTurning" style="first"/>
                  </td>
                  <td width="16%" align="center" class="tdbgcolorloadbuttom"></td>
                </tr>
            </table></td>
      </tr>
    </table></td>
  </tr>
</table>
  </body>
</html:html>
