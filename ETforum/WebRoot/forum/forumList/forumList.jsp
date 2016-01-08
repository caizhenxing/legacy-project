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
      <html:form action="/forum/forumList" method="post">
<%-- jps include 头 --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- 加 --%>

<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
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
<logic:iterate id="c" name="hashmap" indexId="i">          
             <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td height="40" background="../../images/forum/topbg.gif"><bean:write name="c" property="key"/></td>
            </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            
            <tr>
              <td colspan="2" background="../../images/forum/di.jpg"><div align="center">主讨论区</div></td>
                <td width="60" height="32" background="../../images/forum/di.jpg"><div align="center">主题</div></td>
                <td width="56" height="32" background="../../images/forum/di.jpg"><div align="center">回复</div></td>
                <td width="100" height="32" background="../../images/forum/di.jpg"><div align="center">最后更新</div></td>
                <td width="100" height="32" background="../../images/forum/di.jpg"><div align="center">版主</div></td>
              </tr>
            <logic:iterate id="cValue" name="c" property="value" >
              <tr>
                <td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>
                <td width="479"><div align="left">
                <a href="../forumList.do?method=toPostList&itemid=<bean:write name='cValue' property='id'/>"><span class="bold"><bean:write name="cValue" property="name" filter="true"/></span></a>
                </div></td>
                <td><div align="center"><bean:write name='cValue' property='postNum'/></div></td>
                <td><div align="center"><bean:write name='cValue' property='answerTimes'/></div></td>
                <td><div align="center"><bean:write name='cValue' property='updateTime' format="yyyy-MM-dd HH:mm:ss"/></div></td>
                <td><div align="center"><bean:write name='cValue' property='host'/></div></td>
              </tr>
             </logic:iterate>
          </table>
</logic:iterate>
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
         <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td background="../../images/forum/di.jpg">&nbsp; <span class="danzhi">当前在线： <bean:write name="forumUserCount"/>人</span></td>
            </tr>
            <tr>
              <td background="../../images/forum/di.jpg">&nbsp; 
              <span class="danzhi">
                在线用户：
              <logic:iterate id="c" name="userList">
                 <bean:write name="c"  filter="true"/>
              </logic:iterate>
              </span>
              </td>
            </tr>
          </table>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
    
<%--		      <logic:iterate id="c" name="userList" indexId="i">--%>
<%--                 <bean:write name="c"  filter="true"/>&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--              </logic:iterate>--%>
<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include>

<%--加--%>
<%--  --%>
		

<%----%>

<%----%>
  </html:form>
  
  </body>
</html:html>
