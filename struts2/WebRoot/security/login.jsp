<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
  <head>
    <title>Login</title>
  </head>

  <body>
    <h1>Login</h1>

	<P>可用的用户:
	<P>
	<P>username <b>root</b>, password <b>root</b> (具有全部权限)
	<P>username <b>readonly</b>, password <b>readonly</b>(只有查看列表权限权限)
	<p>
<%
  String login_error=request.getParameter("login_error");
  if(login_error!=null){
%>
      <font color="red">
        登录失败。
      </font>
<%
  }
%>
    <form action="/struts2/j_acegi_security_check" method="POST">
      <table>
        <tr><td>User:</td><td><input type='text' name='j_username'></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>

        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    </form>
  </body>
</html>
