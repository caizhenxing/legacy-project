<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="../../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>���ѯ</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
   
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
    <script language="javascript" src="../../js/tools.js"></script>
    <script language="javascript">
   
    	//��ѯ
    	function query(){
    		document.forms[0].action = "../group/Group.do?method=toGroupList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    		window.close();
    	}
    	
    </script>
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
    
  </head>
  
  <body class="conditionBody">
	
    <html:form action="/sys/group/Group" method="post">
    
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
		  <tr>
		    <td class="navigateStyle">
		    ��ǰλ��&ndash;&gt;�����
		    </td>
		  </tr>
		</table>
      	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">	
  			<tr>
            <td  class="labelStyle" align="left" width="123px">������</td>
            <td class="valueStyle"><html:text property="name" styleClass="writeTextStyle"></html:text></td>
<%--            <td class="valueStyle"><html:text property="name" styleClass="writeTextStyle"></html:text></td>--%>
<%--        <td  class="labelStyle">��&nbsp;��&nbsp;��</td>--%>
<%--            <td class="valueStyle"><html:text property="name" styleClass="writeTextStyle"></html:text></td>--%>
            <td class="labelStyle" align="right" style="text-align:right;" width="95px">
		    <input name="btnSearch" type="button" class="buttonStyle" value="��ѯ" onclick="query()"/>
		    <input type="reset" value="ˢ��" class="buttonStyle"
						onClick="parent.bottomm.document.location=parent.bottomm.document.location;" />
		    <input style="display:none;" id="btnAdd" name="btnAdd" type="button" class="buttonStyle" value="���" onclick="popUp('windows','../group/Group.do?method=toGroupLoad&type=insert',550,170)"/>
		    </td>
            </tr>
		</table>
    </html:form>
  </body>
</html:html>
