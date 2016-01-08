
<%@ page language="java" contentType="text/html;charset=GB2312" %>

<%@ page import="org.apache.struts.action.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>HtmlFile.jsp</title>
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
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script language="javascript">
    	function check(){
    		var x = document.getElementById("file");
    		if(!x||!x.value)return;
    		var patn = /\.jpg$|\.jpeg$|\.gif$|\.rm$|\.wmv|\.mp3$|\.swf$/i;
    		if(patn.test(x.value)){
    			
    		}else{
    			alert("您输入的格式不正确，请看看下面的说明");
    			return false;
    		}
    	}
    </script>

  </head>
  
  <body>
    <html:form action="/oa/communicate/inUploadFile.do" method="post" enctype="multipart/form-data">
		<html:file property="file"/>
		<html:submit onclick="check()" styleClass="input">上传</html:submit>
    </html:form>
    
    <logic:present name="inUploadList">
    	<logic:iterate id="c" name="inUploadList">
    		<bean:write name="c"/>上传成功！<br/>
    	</logic:iterate>
    </logic:present>
    
    <logic:equal name="check" value="success">
    	<script>alert('上传成功！');</script>
    </logic:equal>
    
    <logic:equal name="check" value="fail">
    	<script>alert('上传失败！');</script>
    </logic:equal>
    
  </body>
</html:html>
