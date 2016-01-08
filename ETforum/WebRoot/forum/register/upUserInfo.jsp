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
    <script language="javascript">
    	//跳转到注册信息页
    	function jump(){
    		document.forms[0].action = "../userOper/register.do?method=toRegister";
    		document.forms[0].submit();
    	}
    	//修改用户信息
    	function updateUserInfo(){
    		document.forms[0].action = "../userOper/register.do?method=updateForumUser";
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/userOper/register" method="post">
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
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2" align="center">编缉个人资料</td>
        </tr>
        <tr>
          <td width="41%">新的密码?(如果不希望修改密码，请留空) </td>
          <td width="59%">
          	<html:text property="repassword"/>
          </td>
        </tr>
        <tr>
          <td>密码提示问题</td>
          <td>
          	<html:text property="question"/>
          </td>
        </tr>
        <tr>
          <td>密码提示答案</td>
          <td>
          	<html:text property="answer"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>您的QQ号码</td>
          <td>
          	<html:text property="qq"/>
          </td>
        </tr>
        <tr>
          <td> 您的MSN地址</td>
          <td>
          	<html:text property="msn"/>
          </td>
        </tr>
        <tr>
          <td>?您的ICQ号码</td>
          <td>
          	<html:text property="icq"/>
          </td>
        </tr>
        <tr>
          <td>?如果您有个人主页</td>
          <td>
          	<html:text property="homepage"/>
          </td>
        </tr>
        <tr>
          <td>个性化签名</td>
          <td>
          	<html:textarea property="underwrite"/>
          </td>
        </tr>
        <tr>
          <td>自我简介<BR>
            ?
          限一百字以内</td>
          <td>
          	<html:textarea property="introself"/>
          </td>
        </tr>
        <tr>
          <td>性别</td>
          <td>
          	<html:text property="sex"/>
          </td>
        </tr>
        <tr>
          <td>生日</td>
          <td>
          	<html:text property="birthday"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td colspan="2" align="center">
<%--          <input type="button" name="returnIndex" value="返回首页">--%>
          <input type="button" name="returnFirst" value="返回前页" onclick="javascript:history.back()">
          <input type="button" name="confimSubmit" value="确认修改" onclick="updateUserInfo()">
          <input type="reset" name="clear" value="清除重写"></td>
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
