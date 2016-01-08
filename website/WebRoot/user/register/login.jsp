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
    	//登陆
    	function login(){
    		document.forms[0].action = "../userOper/register.do?method=login";
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
	
	<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td height="26" background="images/biaoti1_03.jpg" class="s_headline1">欢迎您来到论坛中心</td>
              </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td>&nbsp;</td>
              </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" background="images/topbg.gif">
            <tr>
              <td height="26">&nbsp;&nbsp;&nbsp; <span class="s_headline1">登录<img src="images/inter.gif" width="18" height="18" /></span></td>
              </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="26" background="images/biaoti1_03.jpg" class="s_headline1">请输入您的用户名，论坛密码登录</td>
            </tr>
            <tr>
              <td><table width="916" border="1" cellspacing="0" cellpadding="0">
                    <logic:present name="error">
				      <tr>
				        <td colspan="2" align="center"> 
				        
				        <font color="red"><bean:message name="error"/></font>
				        </td>
				      </tr>
				  </logic:present>
                  <tr>
                    <td width="208" class="input">用户名
                    </td>
                    <td width="702" class="input"><label>
                      <html:text property="name"/>
                    </label></td>
                  </tr>
                  <tr>
                    <td class="input">密码</td>
                    <td class="input"><label>
                      <html:password property="password"/>
                      （忘记了密码？）<br />
                    </label></td>
                  </tr>
                  <tr>
                    <td><div align="left" class="STYLE1">请输入验证码：</div></td>
                      <td><label>
                                <html:text property="val"/>
          						<img border='0' src='../../RandomPicClient.jsp'/>
                        </label></td>
                    </tr>
					<tr>
                    <td colspan="2" align="center">
                          <input type="button" name="btnLogin" value="登录" onclick="login()">
                          <input type="reset" name="btnReset" value="重新添写">
	                 </td>
                      </tr>
              </table></td>
            </tr>
          </table>
	
	
	
	<%--  这里结束  --%>
	        </td>
	      </tr>
	      
	    </table>
	    </td>
	  </tr>
	</table>
		
  	</html:form>
  </body>
</html:html>
