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
    <SCRIPT language="javascript">
    function update()
    {
    	document.forms[0].submit();
    }
    </script>
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/common.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
  </head>
  
  <body>
  <html:form action="/workplan.do?method=updatePlan">
  
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		
		 <tr>
		 <td class="tdbgpicload">
		 <!--<html:link action="/workplan.do?method=add" onclick="popUp('newplan','',880,600)" target="newplan">�ƶ��׶μƻ�</html:link>
		 <html:link action="/workplan.do?method=addmission&type=only" onclick="popUp('newmission','',880,400)" target="newmission">�ƶ���ϸ�ƻ�</html:link>
		 --> 
		 <html:link action="/workplan.do?method=myplan" target="bottomm">�ҵĽ׶μƻ�</html:link>
		 <html:link action="/workmission.do?method=mymission" target="bottomm">�ҵ���ϸ�ƻ�</html:link>
		 <html:link page="/plan/planday.jsp" target="bottomm">��ϸ�ƻ���ѯ</html:link>
		 <html:link page="/plan/plan.jsp" target="bottomm">�׶μƻ���ѯ</html:link>
		 </TD>
		 </tr>
		 
		</table>
</html:form>
  </body>
</html:html>
