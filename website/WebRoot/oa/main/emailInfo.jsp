<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>操作</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link REL=stylesheet href="../css/divtext.css" type="text/css">
    <script language="JavaScript" src="../js/divtext.js"></script>
    
    <script language="javascript">
    	//添加
    	function load(){
    		document.getElementById(maindiv).innerHTML = document.forms[0].elements["emailInfo"].value;
    	}
    </script>
  </head>
  
  <body onload="load()">
  
    <html:form action="/oa/communicate/email.do" method="post">
    <logic:iterate id="c" name="list">
          <table width="770" border="0" cellspacing="0" cellpadding="2">

            <tr>
              <td align="right" class="tdbgpicmain">
                接收人：
              </td>
              <td class="tdbgpicmain">
		        	<bean:write name='c' property="sendUser" filter="true"/>
              </td>
            </tr>
            <tr>
              <td align="right">
                发送主题：
              </td>
              <td>
                <bean:write name='c' property="emailTitle" filter="true"/>
              </td>
            </tr>
            <tr>
              <td align="right" class="tdbgpicmain">
                发送内容：
              </td>
              <td class="tdbgpicmain">
                <bean:write name='c' property="emailInfo" filter="false"/>
              </td>
            </tr>
           
          </table>

          <table width="730" border="0" align="center" cellpadding="2" cellspacing="2">
            <tr>
              <td align="center">
                <a href="javascript:history.back()">【关闭窗口】</a>
              </td>
            </tr>
          </table>

		</logic:iterate>
    </html:form>
  </body>
</html:html>
