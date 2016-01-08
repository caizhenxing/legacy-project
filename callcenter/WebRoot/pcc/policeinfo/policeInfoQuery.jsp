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
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript">
		
    	//添加
    	function add(){
    		document.forms[0].action = "info.do?method=policeInfoList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	//关闭frame窗体
    	function frameClose(){
    		document.forms[0].action = "info.do?method=finishOper";
    		document.forms[0].target = "contents";
    		document.forms[0].submit();
    		window.parent.close();
    		
    	}
    	
    </script>
    
    
  </head>
  
  <body bgcolor="#eeeeee">
	
    <html:form action="/pcc/policeinfo/info.do?method=policeInfoList" method="post">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    <bean:message key="sys.current.page"/>
		    <bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.title"/>
		    &nbsp;&nbsp;&nbsp;呼入电话号:<bean:write name="phonenum"/>
		    </td>
		  </tr>
		</table>
		<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
	       <tr>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.policenum"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.name"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.birthday"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.mobilephone"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.duty"/></td>
		    <td><bean:message bundle="pcc" key="et.pcc.policeinfo.policequery.byunit"/></td>
		  </tr>
		  <tr>
		    <td><bean:write name="fuzzno" property="fuzzNo" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="name" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="birthday" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="mobileTel" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="tagPoliceKind" filter="false"/></td>
		    <td><bean:write name="fuzzno" property="tagUnit" filter="false"/></td>
		  </tr>

		  <tr>
		    <td colspan="6" class="tdbgcolorquerybuttom">
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.add'/>" onclick="add()"/>
		    <input name="btnFinish" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.finish'/>" onClick="frameClose()"/>
		    </td>
		  </tr>
		  
		</table>
		<html:hidden property="pid"/>
    </html:form>
  </body>
</html:html>
