
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	
<%@ page import="et.bo.sys.login.bean.UserBean" %>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ include file="../style.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title><bean:message bundle='sys' key='sys.clew' />
	</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<%--	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
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
	
	<!-- 引入dwr -->
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/messageService.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	
	<script type="text/javascript" src="../js/et/tools.js"></script>
	<script type="text/javascript" src="../js/pop/pop.js"></script>
	<script type="text/javascript" src="../js/pop/pop_1.js"></script>
	
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	
	<% 
		UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USER_IN_SESSION);
		String agentNum = ub.getUserId();
	%>
	
	<script type="text/javascript">
		//根据人员查询，如果这个人有短消息的信息，则弹出提示框
		function searchMessage(){		
			messageService.getCommonMessageByagent('<%=agentNum%>',callbackShow);
			messageService.getMessageByagent('<%=agentNum%>',callbackdispose);
		}
		//处理普通短消息
		function callbackShow(data){
		//alert(data);
			//if(data!=''){
				document.getElementById('showUrl').value = data;
				//document.getElementById('showBtn').click();
			//}
			//alert('1');
		}
		
		//处理业务短消息
		function callbackdispose(data){		
			var v_mw = document.getElementById('showUrl').value;
			//alert(v_mw);
			if(data!=''){
				//alert(data);// b:50    1:80   10/t
				//var MSG_test = new CLASS_MSN_MESSAGE_1("a_test",120,100,"短消息提示：","您有1封消息",data);//空间不足test  
				//var v_h = 110+data.split(',').length*5;
				var MSG_test = new CLASS_MSN_MESSAGE_1("a_test",215,0,"短消息提示：","您有1封消息",data,'',v_mw);  	    			    		
	    		MSG_test.rect(null,null,null,screen.height-50);
	    		MSG_test.speed    = 10; 
	    		MSG_test.step    = 5; 
	    		MSG_test.show();				
			}else if(v_mw.length>1){
				var MSG_test = new CLASS_MSN_MESSAGE_1("a_test",200,120,"短消息提示：","您有1封消息",'','',v_mw);  	    		
	    		MSG_test.rect(null,null,null,screen.height-50);
	    		MSG_test.speed    = 10; 
	    		MSG_test.step    = 5; 
	    		MSG_test.show();
			}
		}
		
		function show(){
			var MSG1 = new CLASS_MSN_MESSAGE("aa",200,120,"短消息提示：","您有1封消息","您有一封新的普通短消息！");  
    		MSG1.rect(null,null,null,screen.height-50); 
    		MSG1.speed    = 10; 
    		MSG1.step    = 5; 
    		MSG1.show();  
		}
		
		//显示处理
		function showDispose(){
			var MSG1 = new CLASS_MSN_MESSAGE("aa",200,120,"短消息提示：","您有1封消息","您有一封新的普通短消息！");  
    		MSG1.rect(null,null,null,screen.height-50); 
    		MSG1.speed    = 10; 
    		MSG1.step    = 5; 
    		MSG1.show();  
		}
	
		function showMessage(){
			var url = document.getElementById('showUrl').value;
			popUp('windows',url,750,280)
		}
		
		function showMessageTest(){			
			popUp('windows','../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoMain',800,550);
		}
	</script>

</head>

<body onload="setInterval('searchMessage()',10000)">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		background="../style/<%=styleLocation%>/images/zx/dl24.jpg">
		<tr>
			<td height="33" align="right">
				<input type="button" id="showBtn" name="显示" value="显示" onclick="show()" style="display:none;"/>
				<input type="text" id="showUrl" name="showText" value="" style="display:none;"/>
				<input type="text" id="disponseText" name="disponseText" value="" style="display:none;"/>
			</td>
		</tr>
	</table>
</body>

</html:html>
