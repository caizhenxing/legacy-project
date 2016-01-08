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
     <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td background="../../images/forum/di.jpg"><div align="center">控制面板首页</div></td>
              <td background="../../images/forum/di.jpg"><div align="center">编辑个人资料</div></td>
              <td background="../../images/forum/di.jpg"><div align="center"><a href="../postQuery.do?method=toMySavePostList">收藏夹</a></div></td>
              <td background="../../images/forum/di.jpg"><div align="center">短消息</div></td>
              <td background="../../images/forum/di.jpg"><div align="center"><a href="/ETforum/forum/userInfo.do?method=toUserInfoList">联系人管理</a></div></td>
<%--              <td background="../../images/forum/di.jpg"><div align="center">邀请和激活</div></td>--%>
<%--              <td background="../../images/forum/di.jpg"><div align="center">帐户绑定</div></td>--%>
            </tr>
     </table>
  </body>
</html:html>
