<%@ page language="java" pageEncoding="gb2312" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>
      <bean:message key="oa.privy.plan.title"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2">
      
      
      <tr>
        <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" class="tdbgpicload">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td valign="top" bgcolor="#F2F2F7" class="tdbgpicload">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="31" valign="middle"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" /></td>
                          <td width="565" height="20" align="left" class="td2">
                            <bean:message key="oa.privy.plan.title"/>
                          </td>
                          <td width="27" align="center"></td>
                          <td width="30" align="left">
                          <a href="/ETOA/oa/mainOper.do?method=toMain">
                          <img src="<bean:write name='imagesinsession'/>minimize.gif" width="12" height="12" border="0"/>
                          </a>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top" class="tdbgpicmain">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <logic:iterate id="c" name="planList">
                        <tr>
                          <td width="5%" align="center" class="tdbgpicmain"><img src="<bean:write name='imagesinsession'/>button_main_title_front.gif" width="10" height="11" /></td>
                          <td width="16%" class="tdbgpicmain">
                            <html:link action="/workmission.do?method=changemission&type=mi" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',880,300)" target="windows">
                            <bean:write name="c" property="name"/>
							</html:link>
                          </td>
                          <td width="68%" align="center" class="tdbgpicmain">
                            (<bean:write name="c" property="beginTime"/>--<bean:write name="c" property="endTime"/>)
                          </td>
                        </tr>
						</logic:iterate>
                      </table>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="84%" align="right">&nbsp;
                            
                          </td>
                          <td width="16%">
                           
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td valign="top">
          
        </td>
      </tr>
    </table>
  </body>
</html:html>
