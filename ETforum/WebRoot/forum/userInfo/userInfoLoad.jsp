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
  
  <style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href=".zhi" rel="stylesheet" type="text/css" />
<link href="css/styleA.css" rel="stylesheet" type="text/css" />

<style type="text/css">
<!--
.STYLE8 {
	color: #FF6600;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
	font-weight: bold;
}
-->
</style>

  <body bgcolor="#eeeeee">
  <html:form action="/forum/userInfo" method="post">
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
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
          <tr>
            <td colspan="4" background="../../images/forum/di.jpg">&nbsp;</td>
            </tr>
          <tr>
            <td height="40" colspan="4"><div align="center" class="font-big STYLE8"><bean:write name="userInfo" property="id" filter="ture"/></div></td>
            </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
            </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 1.基本资料 </td>
            </tr>
          <tr>
            <td width="137">&nbsp;性别：</td>
            <td width="379">&nbsp;不告诉你</td>
            <td width="124">&nbsp;注册时间：</td>
            <td width="276">&nbsp;2006-10-10&nbsp; 10：13 </td>
          </tr>
          <tr>
            <td>&nbsp;总共发表：</td>
            <td>&nbsp;发表：44&nbsp;&nbsp; 被删主题：0&nbsp;&nbsp;&nbsp; 被删回复：0&nbsp;&nbsp; 精华贴数：0 <br />
              &nbsp;最后发表时间：2006-12-12 10：13 </td>
            <td>&nbsp;访问：</td>
            <td>&nbsp;最后访问时间：2006-12-12 10：13<br />
              &nbsp;最后登录时间：2006-12-12 10：13</td>
          </tr>
          <tr>
            <td>&nbsp;生肖：</td>
            <td>&nbsp;未填</td>
            <td>&nbsp;星座：</td>
            <td>&nbsp;未填 </td>
          </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 2.社区资料 </td>
            </tr>
          <tr>
            <td>&nbsp;头衔：</td>
            <td>&nbsp;没有</td>
            <td>&nbsp;级别：</td>
            <td>&nbsp;究级天王[荣誉](坛主）</td>
          </tr>
          <tr>
            <td>&nbsp;积分/蓝魔币/等级分</td>
            <td>&nbsp;6积分/248蓝魔币/44</td>
            <td>&nbsp;门派：</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="4" background="images/topbg.gif">&nbsp;&nbsp; 3.联系资料 </td>
            </tr>
          <tr>
            <td>&nbsp;主页：</td>
            <td>&nbsp;没有</td>
            <td>&nbsp;信箱：</td>
            <td>&nbsp;用户不愿公开</td>
          </tr>
          <tr>
            <td>&nbsp;QQ：</td>
            <td>&nbsp;没有</td>
            <td>&nbsp;来自：</td>
            <td>&nbsp;未填</td>
          </tr>
          <tr>
            <td>&nbsp;ICQ:</td>
            <td>&nbsp;没有</td>
            <td>&nbsp;MSN：</td>
            <td>&nbsp;没有</td>
          </tr>
          <tr>
            <td colspan="4" background="../../images/forum/topbg.gif">&nbsp;&nbsp; 4.其它资料 </td>
            </tr>
          <tr>
            <td>&nbsp;自我简介：</td>
            <td colspan="3">&nbsp;</td>
            </tr>
          <tr>
            <td>&nbsp;签名：</td>
            <td colspan="3">&nbsp;</td>
            </tr>
          <tr>
            <td colspan="4"><div align="center">相关动作：<img src="../../images/forum/mybbs.gif" width="18" height="18" />发短消息 <img src="../../images/forum/whos_online.gif" width="30" height="30" /> 查找所有我发的贴子 <img src="../../images/forum/mybbs.gif" width="18" height="18" />加入我好友录</div></td>
            </tr>
          <tr>
            <td colspan="4" background="../../images/forum/di.jpg"><div align="center"><a href="/ETforum/forum/forumList.do?method=toForumList&moduleId=1">论坛首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a onclick="history.back()">返回前页</a> </div>
              <div align="left"></div></td>
            </tr>
        </table>
<%----%>




<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--加--%>
</html:form>
  </body>
</html:html>
