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
    <html:form action="/forum/userManager" method="post">
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
<%--把代码加到这--%>
<%----%>
<table width="916" border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="#CCCCCC">
          <tr>
            <td colspan="2" background="../../images/forum/di.jpg" align="center">&nbsp; 查看用户资料</td>
          </tr>
          <tr>
            <td width="330" colspan="2">&nbsp;
            <bean:write name="dto" property="id"/>
          </td></tr>
          <tr>
            <td>&nbsp; 昵称：</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="name"/>
                </div>
            </label></td>
          </tr>
          <tr><td>
                 &nbsp; 注册时间：</td>
            <td width="580"><label>
              <div align="left">
                <bean:write name="dto" property="registerDate" format="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 上次登陆时间：</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="lastLogin" format="yyyy-MM-dd HH:mm:ss"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 注册IP：</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="registerIp"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 邮箱：</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="email"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; QQ号码：</td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="qq"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 用户头衔： </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="userLevel"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 发帖数： </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="sendPostNum"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 回帖数： </td>
            <td><label>
              <div align="left">
               <bean:write name="dto" property="answerPostNum"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; 积分: </td>
            <td><label>
              <div align="left">
                <bean:write name="dto" property="point"/>
                </div>
            </label></td>
          </tr>
<%--          <tr>--%>
<%--            <td>&nbsp; 发贴数大于： </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="endSendPost" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 多少天没有登录论坛： </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="noLoginDate" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 最近一次登录IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="newlyIp" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 注册时IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="ip" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; 用户组： </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <select name="select">--%>
<%--                  <option>坛主</option>--%>
<%--                  <option>超级坛主</option>--%>
<%--                  <option>区版主</option>--%>
<%--                  <option>版主</option>--%>
<%--                  <option>普通用户</option>--%>
<%--                  <option>认证用户</option>--%>
<%--                  <option>禁止发言</option>--%>
<%--                  <option>游客</option>--%>
<%--                </select>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
          <tr>
            <td height="30" colspan="2"><label>
              <div align="left">
                <input type="button" name="Submit" value="返回上一页" onclick="history.back()"/>
                
<%--                &nbsp; --%>
<%--                <html:reset value="清空"/>--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit3" value="论坛短信" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit4" value="论坛邮件" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit5" value="用户奖罚" />--%>
              </div>
              </label></td>
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
    </html:form>
  </body>
</html:html>
