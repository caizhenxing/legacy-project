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
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="../../../css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	function check(){
    		var x = document.getElementById("file");
    		if(!x||!x.value)return;
<!--    		var patn = /\.txt$|\.doc$|\.pdf$|\.xls$/i;-->
    		var patn = /\.doc$/i;
    		if(patn.test(x.value)){
    		
    		}else{
    			alert("<bean:message key='et.oa.assi.document.notformat'/>");
    			return false;
    		}
    	}
    </script>

  </head>
  
  <body>
    <html:form action="/oa/assissant/docUploadFile.do" method="post" enctype="multipart/form-data">
		<html:file property="file" styleClass="input" onchange="changeSrc(this)"/>
		<html:submit onclick="check()" styleClass="input"><bean:message key='et.oa.assi.document.upload'/></html:submit>
    </html:form>
    <logic:present name="uploadList">
    	<logic:iterate id="c" name="uploadList">
    		<bean:write name="c"/><bean:message key='et.oa.assi.document.uploadsuccess'/>
    	</logic:iterate>
    </logic:present>  
    <logic:equal name="check" value="success">
    	<script>alert("<bean:message key='et.oa.assi.document.uploadsuccess'/>");</script>
    </logic:equal>    
    <logic:equal name="check" value="fail">
    	<script>alert("<bean:message key='et.oa.assi.document.uploaderror'/>");</script>
    </logic:equal>
<script type="text/javascript">
oFileChecker.onreadystatechange = function ()
{
  if (oFileChecker.readyState == "complete")
  {
    checkSize();
  }
}

function checkSize()
{
  var limit = 2048 * 1024;

  if (oFileChecker.fileSize > limit)
  {
    alert("<bean:message key='et.oa.assi.document.overflow'/>");
  }
  else
  {   
  }
}
</script>



  </body>
</html:html>
