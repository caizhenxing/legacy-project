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
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
    	//查询
    	function query(id){
    		document.forms[0].action = "../cclog.do?method=answerMan&id="+id;
    		document.forms[0].submit();
    	}

		//返回页面
		function toback(){
		
			window.opener.parent.document.location.reload();
		}
    </script>
    
    
  </head>
  
  <body onunload="toback()" bgcolor="#eeeeee">
  
  	<logic:notEmpty name="idus_state">
		<script>window.close();alert("<bean:message bundle='pcc' name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
	
    <html:form action="/callcenter/cclog" method="post">
      	<table width="70%" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		    <td class="tdbgcolorquerytitle">
			    回复人信息添加
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright">回复人信息列表</td>
		    <td class="tdbgcolorqueryleft">
		    <html:select property="answerman">
						<html:option value="">请选择</html:option>
						<html:options collection="chenggonglist" property="value" labelProperty="label"/>
				</html:select>
		    </td>
<%--		    <td class="tdbgcolorqueryright"></td>--%>
<%--		    <td class="tdbgcolorqueryleft"></td>--%>
		  </tr>
		  <tr>
		    <td colspan="4" class="tdbgcolorquerybuttom">
		    	<%
					String cclogid = request.getAttribute("policecallinid").toString();
				%>
		    	<input name="btnSearch" type="button" class="bottom" value="回复人添加" onclick="query('<%=cclogid%>')"/>
		    	<input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.cancel'/>" onclick="javascript:window.close();"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
