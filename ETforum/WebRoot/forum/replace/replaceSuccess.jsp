<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
        <script language="javascript">
		//�����û�
		function query(){    
    	      document.forms[0].action = "../replace.do?method=toReplace";
    		  document.forms[0].submit();   
    	}

    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/replace" method="post">
    <%-- jps include ͷ --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- �� --%>

<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
<%--   �ӵ�����    --%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
          <tr>
            <td background="../../images/forum/di.jpg" align="center">&nbsp;&nbsp;<strong class="danzhi">�����ɹ�</strong></td>
            </tr>
          <tr>
            <td height="30"><div align="center" class="danzhi"><strong><input type="submit" name="Submit" value="����" onclick="query()"/></strong></div></td>
          </tr>
        </table>


<%----%>
<%--�Ѵ���ӵ���--%>
<%----%>




<%--  �������  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--��--%>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
    </html:form>
  </body>
</html:html>
