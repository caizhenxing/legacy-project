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



<%----%>
<jsp:include flush="true" page="../common/navigation.jsp"></jsp:include>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
<jsp:include flush="true" page="panel.jsp"></jsp:include>          
<%--把代码加到这--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="450" height="36" colspan="3" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">主题</div></td>
                <td width="150" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">讨论区</div></td>
                <td width="50" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">点击</div></td>
                <td width="50" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">回复</div></td>
                <td width="116" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">时间</div></td>
                <td width="100" background="../../images/forum/di.jpg" class="s_headline2"><div align="center">操作</div></td>
              </tr>
            <logic:iterate id="c" name="postlist" indexId="i">
            <tr>
                <td width="50"><div align="center"><img src="../../images/forum/forum_new.gif" width="28" height="32" /></div></td>
                <td width="50"><div align="center"><img src="../../images/emotion/28.gif" width="19" height="19" /></div></td>
                <td width="350" height="36" class="s_headline1"><div align="left"> &nbsp;<img src="../../images/forum/y09.gif" width="10" height="10" /> <a href="text-moren.html"><bean:write name="c" property="title" filter="true"/></a></div></td>
                <td width="150" class="s_headline1"><div align="center"><bean:write name="c" property="moduleName" filter="true"/></div></td>
                <td width="50" class="s_headline1"><div align="center"><bean:write name="c" property="clickTimes" filter="true"/></div></td>
                <td width="50" class="s_headline1"><div align="center"><bean:write name="c" property="answerTimes" filter="true"/></div></td>
                <td  width="120" class="s_headline1"><div align="center"><bean:write name="c" property="postAt" filter="true" format="yyyy-MM-dd HH:mm:ss"/></div></td>
                <td  width="100" class="s_headline1"><div align="center">
                <a href="../userInfo.do?method=toUserInfoLoad&userId=<bean:write name='c' property='userkey'/>">
		            <bean:write name="c" property="userkey" filter="true"/>
		        </a>
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
						  <page:page name="searchPostpageTurning" style="first"/>				   
					  </div>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
          </table>
<%----%>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
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
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
  </body>
</html:html>
