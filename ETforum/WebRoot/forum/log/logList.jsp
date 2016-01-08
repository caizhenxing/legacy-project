<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/userInfo" method="post">
    <%-- jps include 头 --%>
<%--  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>--%>
<%-- 加 --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
<%--   加到这里    --%>



<%----%>
<%--把代码加到这--%>
<%----%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="200" height="36" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">用户名</div></td>
                <td width="166" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">动作</div></td>
                <td width="250" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">操作区域</div></td>
                <td width="150" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">操作时间</div></td>
                <td width="200" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">ip</div></td>
<%--                <td width="100" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">操作</div></td>--%>
              </tr>
            <logic:iterate id="c" name="logList" indexId="i">
            <tr>
<%--                <td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>--%>
<%--                <td width="50"><div align="center"><img src="../../images/emotion/28.gif" width="19" height="19" /></div></td>--%>
                <td  height="36" class="s_headline1"><div align="left"> &nbsp; <a href="../userInfo.do?method=toUserInfoLoad&userId=<bean:write name='c' property='userId'/>" target="_blank"><bean:write name="c" property="userId" filter="true"/></a></div></td>
                <td  class="s_headline1"><div align="center"><bean:write name="c" property="action" filter="true"/></div></td>
                <td  class="s_headline1"><div align="center"><bean:write name="c" property="moduleName" filter="true"/></div></td>
<%--                <td width="50" class="s_headline1"><div align="center"><bean:write name="c" property="answerTimes" filter="true"/></div></td>--%>
                <td   class="s_headline1"><div align="center"><bean:write name="c" property="operTime" filter="true" format="yyyy-MM-dd HH:mm:ss"/></div></td>
                <td   class="s_headline1"><div align="center">
<%--                <a href="../userInfo.do?method=toUserInfoLoad&userId=<bean:write name='c' property='userkey'/>">--%>
		            <bean:write name="c" property="ip" filter="true"/>
<%--		        </a>--%>
                </div>
                </td>
            </tr>
            </logic:iterate>
</table>

<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="638" background="../../images/forum/di.jpg">
              </td>
                <td width="278" background="../../images/forum/di.jpg">
                 <table width="330" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="../../images/forum/di.jpg" colspan="6">
                      <div align="center">               	  
						  <page:page name="forumLogpageTurning" style="second"/>				   
					  </div>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
          </table>



<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--加--%>
<%--<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> --%>
    </html:form>
  </body>
</html:html>
