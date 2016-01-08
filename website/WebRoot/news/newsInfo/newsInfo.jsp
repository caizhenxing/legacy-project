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
      新闻详细信息
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  <body>
  	<html:form action="/news/opernews.do" method="post">
    <table width="770" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td height="45" background="<bean:write name='imagesinsession'/>main/news001.gif">
          <table width="770" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="47">
                &nbsp;
              </td>
              <td width="723" height="30" valign="bottom" class="td2">
                新闻阅读
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td>
        <logic:iterate id="c" name="list">
          <table width="770" border="0" cellspacing="0" cellpadding="2">
            <tr>
              <td width="176" align="right">
                &nbsp;
              </td>
              <td width="594">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="right" class="tdbgcolor2">
                新闻类别：
              </td>
              <td class="tdbgcolor2">
		        	<bean:write name='c' property="classid" filter="true"/>
              </td>
            </tr>
            <tr>
              <td align="right">
                文章标题：
              </td>
              <td>
                <bean:write name='c' property="title" filter="true"/>
              </td>
            </tr>
            <tr>
              <td align="right" class="tdbgcolor2">
                作者：
              </td>
              <td class="tdbgcolor2">
                <bean:write name='c' property="author" filter="true"/>
              </td>
            </tr>
           <tr>
              <td align="right">
                发表时间：
              </td>
              <td>
                <bean:write name='c' property="updatetime" filter="true"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height="50" valign="top" class="tdbgcolor2">
          <table width="730" border="0" align="center" cellpadding="2" cellspacing="2">
            <tr>
              <td>
                &nbsp;
              </td>
            </tr>
            <tr>
              <td valign="top" class="tdbgcolor1">
                <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">
                  <tr>
                    <td valign="top" bgcolor="#FFFFFF">
                      <table width="100%" border="0" cellspacing="5" cellpadding="5">
                        <tr>
                          <td valign="top">
							<bean:write name='c' property="content" filter="false"/>
						  </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td align="center">
                <a href="javascript:history.back()">【返回】</a>
              </td>
            </tr>
          </table>
          </logic:iterate>
        </td>
      </tr>
    </table>
   </html:form>
  </body>
</html:html>
