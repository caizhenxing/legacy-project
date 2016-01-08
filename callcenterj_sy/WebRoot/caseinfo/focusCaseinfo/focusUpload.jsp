
<%@ page language="java" contentType="text/html;charset=GB2312" %>

<%@ include file="../../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>上传照片</title>
    
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
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    <script language="javascript">
<%--        //返回--%>
<%--		function selImgSrc(){--%>
<%--			window.parent.document.all.a.value = document.forms[0].back.value;--%>
<%--		}--%>
    	//检查
    	function check(){
    		var x = document.getElementById("file");
    		if(x.value=="")
    		{
    		
    			alert("上传照片不允许为空");
    			return false;
    		}
    		else
    		{
	    		if(!x||!x.value)return;
	    		var patn = /\.jpg$|\.jpeg$|\.gif$|/i;
	    		if(patn.test(x.value)){
	    			
	    		}else{
	    			alert("您输入的格式不正确，请看看下面的说明");
	    			return false;
	    		}
    		}
    	}
    	
    </script>
  <%
  String str=request.getAttribute("id").toString();
 //out.println(str);
  %>
  </head>
   
  <body class="conditionBody">
    <logic:equal name="check" value="success">
   		<script>
   			alert("上传成功");
   			window.close();
   		</script>
    </logic:equal>
  
    <html:form action="/focusUploadFile.do?method=upload" method="post" enctype="multipart/form-data">
    <html:hidden property="id" value="<%=str%>"/>
    
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
		  <tr>
		    <td class="navigateStyle">
		    当前位置&ndash;&gt;焦点案例图片上传
		    </td>
		  </tr>
		</table>
    
    <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
    
    	<tr>
	    	<td class="labelStyle">
	    				上传图片
	    	</td>
	    	<td>
			    <logic:equal name="check" value="">
					<html:file property="file" styleClass="buttonStyle"/>
					<html:submit styleClass="buttonStyle" onclick="return check()">上传</html:submit>
				</logic:equal>
				<logic:equal name="url" value="">
			    <input type="hidden" name="back" value="http://">
			    </logic:equal>
			    <logic:notEqual name="url" value="">
			    <input type="hidden" name="back" value="<bean:write name='url'/>">
			    </logic:notEqual>
   		 </td>
    </tr>
  </table>
    </html:form>
  </body>
</html:html>
