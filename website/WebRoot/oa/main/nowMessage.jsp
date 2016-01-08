
<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>即时消息</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td valign="top" bgcolor="#F2F2F7" class="tdbgpic1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="31" align="center"><img src="<bean:write name='imagesinsession'/>button_main_title.gif" width="12" height="10" /></td>
                    <td width="565" height="26" align="left" class="td2">即时消息</td>
                    <td width="27" align="center"><img src="<bean:write name='imagesinsession'/>minimize.gif" width="16" height="16" /></td>
                    <td width="30" align="left"><img src="<bean:write name='imagesinsession'/>most.gif" width="16" height="16" /></td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td valign="top" class="tdbgpic2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="8%" align="center" class="tdbgpic2"><img src="<bean:write name='imagesinsession'/>dot006.gif" width="11" height="9"></td>
                  <td width="92%" class="tdbgpic2"><a href="#"><img src="<bean:write name='imagesinsession'/>短消息.gif" border="0" width="88" height="21"></a></td>
                </tr>
                <tr>
                  <td align="center" class="tdbgpic2"><img src="<bean:write name='imagesinsession'/>dot006.gif" width="11" height="9"></td>
                  <td class="tdbgpic2"><a href="#"><img src="<bean:write name='imagesinsession'/>短消息2.gif" border="0" width="88" height="21"></a></td>
                </tr>
              </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="82%" align="right">&nbsp;</td>
                      <td width="18%">&nbsp;</td>
                    </tr>
                </table></td>
            </tr>
        </table>
  </body>
</html:html>
