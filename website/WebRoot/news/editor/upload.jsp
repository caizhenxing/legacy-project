
<%@ page language="java" contentType="text/html;charset=GB2312" %>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>�氮��Ϸ����</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
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
    <script language="javascript">
        //����
		function selImgSrc(){
			window.parent.document.all.a.value = document.forms[0].back.value;
		}
    	//���
    	function check(){
    		var x = document.getElementById("file");
    		if(!x||!x.value)return;
    		var patn = /\.jpg$|\.jpeg$|\.gif$|/i;
    		if(patn.test(x.value)){
    			
    		}else{
    			alert("������ĸ�ʽ����ȷ���뿴�������˵��");
    			return false;
    		}
    	}
    </script>
  </head>
  
  <body onload="selImgSrc()">
    <html:form action="editor/uploadFile.do" method="post" enctype="multipart/form-data">
    <logic:equal name="check" value="">
		<html:file property="file"/>
		<html:submit onclick="check()">�ϴ�</html:submit>
	</logic:equal>
	<logic:equal name="url" value="">
    <input type="hidden" name="back" value="http://">
    </logic:equal>
    <logic:notEqual name="url" value="">
    <input type="hidden" name="back" value="<bean:write name='url'/>">
    </logic:notEqual>
    <logic:equal name="check" value="success">
    	<%
    		out.println("�ϴ��ɹ���");
    	%>
    </logic:equal>
    
    </html:form>
  </body>
</html:html>
