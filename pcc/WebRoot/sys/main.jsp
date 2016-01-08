
<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><bean:message key="sys.title"/></title>
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
<link href="css/styleA.css" rel="stylesheet" type="text/css" />
</head>

<frameset rows="93,*,30" frameborder="no" border="0" framespacing="0">
  <html:frame frameName="topFrame" page="/sys/top.html"  scrolling="No" noresize="noresize"  title="topFrame" />
  <frameset name="agro" cols="180,*" frameborder="no" border="0" framespacing="0" >
		<html:frame frameName="tree" page="/sys/tree.jsp"  scrolling="yes" noresize="noresize"  title="tree" />
		<frameset cols="11,*" frameborder="no" border="0" framespacing="0">
		  	<html:frame frameName="mid" page="/sys/mid.htm" scrolling="No" noresize="noresize" title="mid" />
			<html:frame  frameName="contents" page="/oa/mainOper.do?method=toMain" />
		</frameset>
  </frameset>
  <html:frame frameName="bottomFrame" page="/sys/buttom.html"  scrolling="No" noresize="noresize"  title="bottomFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>
