
<%@ page language="java" contentType="text/html;charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="et.bo.sys.login.bean.UserBean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
String userId = "";
if(ub!=null)
{
	userId = ub.getUserId();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>金农咨询服务中心工作平台</title>
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

  	 <script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentListService.js'></script>
 	 <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	 <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>

	<script language="javascript">

		window.onbeforeunload = function() 
		{
			 agentListService.removeAgentInList(<%=userId%>);
			 var n = window.event.screenX - window.screenLeft;
			 var b = n > document.documentElement.scrollWidth-20;
			 if(b && window.event.clientY < 0 || window.event.altKey)
			 {
			     window.event.returnValue="确定要退出本页吗？"; 
			 }
		}

	</script>


<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
</head>

<frameset rows="97,*,28" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="./topAgent.jsp" name="topFrame" scrolling="no" marginwidth="0" marginheight="0" id="topFrame" />
  <frameset name="exce" rows="*" cols="210,5,*" framespacing="2" frameborder="no" border="2">
    <frame src="./agentPanel.jsp" name="tree" scrolling="auto" marginwidth="0" marginheight="0" id="leftFrame" />
    <html:frame frameName="mid" page="/sys/agentmid.jsp" scrolling="No"  noresize="true" title="mid"/>
	<html:frame frameName="right" page="/sys/agentTab.jsp" scrolling="No"  noresize="true" title="rightFrame"/>
  </frameset>
  <frame src="./buttom.jsp" scrolling="no" marginwidth="0" marginheight="0" />
</frameset>
</html>
