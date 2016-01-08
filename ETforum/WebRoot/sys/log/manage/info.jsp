
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>info.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
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
<link href="../../../css/styleA.css" rel="stylesheet" type="text/css" />
<SCRIPT>
	function a()
	{
		document.forms[0].action="/ETforum/sys/user/UserOper.do?method=add";
    	document.forms[0].submit();
	}
	function c()
	{
		//window.close();
		//parent.mainFrame.location.href="fffffffffffffff";
		window.opener.parent.mainFrame.location.href="fffffffffffffff";
	}
	function d()
	{
		document.forms[0].action="/ETforum/sys/user/UserOper.do?method=del";
    	document.forms[0].submit();
	}
	function u()
	{
		document.forms[0].action="/ETforum/sys/user/UserOper.do?method=update";
    	document.forms[0].submit();
	}
</SCRIPT>
  </head>
  
  <body bgcolor="#eeeeee">
  <html:form action="/user/UserOper">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5"> 
  <!--  -->
  <!--  -->
  <TR>
    <TD align="center" class="tdbgpic1">名 称</TD>
    <TD align="center" class="tdbgpic1">必 添</TD>
  </TR>
  <TR>
    <TD class="tdbgcolor2"><DIV align="right">I D</DIV></TD>
    <TD align="center" class="tdbgcolor2">
    
    <logic:equal name="type" value="i">
    <html:text property="userId"></html:text>
    </logic:equal>
    <logic:notEqual name="type" value="i">
    <html:text property="userId" disabled="true"></html:text>
    <html:hidden property="userId"/>
    </logic:notEqual>
	</TD>
  </TR>
  <TR>
    <TD width="16%" class="tdbgcolor1"><DIV align="right">用户名</DIV></TD>
    <TD width="84%" align="center" class="tdbgcolor1">
		<html:text property="userName"></html:text>
		
	</TD>
  </TR>
  <TR>
    <TD class="tdbgcolor2"><DIV align="right">角色</DIV></TD>
    <TD align="center" class="tdbgcolor2">
    <html:select property="sysRole">
    		<html:options collection="rl"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
  </TR>
  <TR>
    <TD align="right" class="tdbgcolor1"><DIV align="right">组</DIV></TD>
    <TD align="center" class="tdbgcolor1">
	<html:select property="sysGroup">
    		<html:options collection="gl"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
  </TR>
  <logic:notEqual name="type" value="i">
  	<html:hidden property="password"/>
  </logic:notEqual>
  <logic:equal name="type" value="i">
  <TR>
    <TD class="tdbgcolor2"><DIV align="right">密码</DIV></TD>
    <TD align="center" class="tdbgcolor2">
		<html:password property="password"></html:password>
	</TD>
  </TR><TR>
    <TD align="right" class="tdbgcolor1"><DIV align="right">验证密码</DIV></TD>
    <TD align="center" class="tdbgcolor1">
		<html:password property="repassword"></html:password>
	</TD>
  </TR>
  </logic:equal>
  <TR>
    <TD class="tdbgcolor2"><DIV align="right">部门</DIV></TD>
    <TD align="center" class="tdbgcolor2">
		<html:select property="departmentId">
    		<html:option value="">请选择</html:option>
    		<html:option value="1">d1</html:option>
    		<html:option value="2">d2</html:option>
    	</html:select>
	</TD>
  </TR><TR>
    <TD align="right" class="tdbgcolor1"><DIV align="right">冻结</DIV></TD>
    <TD align="center" class="tdbgcolor1">
    	<html:select property="freezeMark">
    		<html:option value="">请选择</html:option>
    		<html:option value="1">正常</html:option>
    		<html:option value="0">冻结</html:option>
    	</html:select>
		
	</TD>
  </TR><TR>
    <TD align="center" class="tdbgpic1">名 称</TD>
    <TD align="center" class="tdbgpic1">非必添</TD>
  </TR><tr>
    <td class="tdbgcolor2"><div align="right">全名</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="realName"></html:text>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">性别</div></td>
    <td align="center" class="tdbgcolor1"> <html:radio property="sexId" value="1" >男</html:radio> <html:radio property="sexId" value="0">女</html:radio>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">identityKind</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="identityKind"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">identityCard</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="identityCard"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">生日</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="birthday"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">countryId</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="countryId"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">provinceId</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="provinceId"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">qq</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="qq"></html:text>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">bloodType</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="qq"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">address</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="address"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">postalcode</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="postalcode"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">mobile</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="mobile"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">finishSchool</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="finishSchool"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">speciality</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="speciality"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">workId</div></td>
    <td align="center" class="tdbgcolor2">
		<html:text property="workId"/>
	</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolor1"><div align="right">homepage</div></td>
    <td align="center" class="tdbgcolor1">
		<html:text property="homepage"/>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolor2"><div align="right">2</div></td>
    <td align="center" class="tdbgcolor2">
		
	</td>
  </tr>
  <tr>
    <td class="tdbgcolor1">&nbsp;    </td>
    <td align="center" class="tdbgcolor1">
    <logic:equal name="type" value="i">
    <input name="Submit2" type="button" class="bottom" value="添加" onclick="a()"/>
    </logic:equal>
    <logic:equal name="type" value="u">
    <input name="Submit2" type="button" class="bottom" value="修改" onclick="u()"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="删除" onclick="d()"/>
    </logic:equal>
    <input name="Submit3" type="reset" class="bottom" value="重填" />
    
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
