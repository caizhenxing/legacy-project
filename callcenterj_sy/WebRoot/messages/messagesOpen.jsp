<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>����Ϣ</title>

<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	//��Ϣ
	function message(){
		window.open("../messages/messages.do?method=toMessagesMain","windowsmessage","");
		window.close();
	}
	function message2(){
		window.open("../flow/flow.do?method=toFlowMain","windowsmessage","");
		window.close();
	}
	
	setTimeout("window.opener=null;window.close();",10000);
</script>
</head>

<body class="loadBody">
<html:form action="/messages/messages" method="post">

<table width="100%" border="0" align="center" class="contentTable">
  <tr>
    <td class="labelStyle"></td>
    <td class="valueStyle">
    <%
    String state = request.getParameter("state");
    if(state != null && !state.equals("0")){
    %>
    	<a onclick="message2()">����<%= state %>���¼�¼��Ҫ��ˣ���ע����գ�</a>
    <%
    }else{
    %>
    	<a onclick="message()">�����µĶ���Ϣ����ע����գ�</a>
    <%
    }
    %>
    
    </td>
  </tr>
</table>

</html:form>
</body>
</html>
