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
    
    <title><bean:message key="et.oa.hr.hrManagerDetail.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//添加
    	function add(){
    		document.forms[0].action = "../hr.do?method=operHr&type=insert";
    		document.forms[0].submit();
    	}
    	//修改
    	function update(){
    		document.forms[0].action = "../hr.do?method=toHrLoad&type=update";
    		document.forms[0].submit();
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../hr.do?method=toHrLoad&type=delete";
    		document.forms[0].submit();
    	}
    	
    	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		//返回页面
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()">
   
  
    <html:form action="/oa/assissant/hr" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgpicload"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.employeeInfo"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 <logic:iterate id="c" name="companyDetail">
		 <tr>
		    <html:hidden property="id"/>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.name"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="name" filter="true"/></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.age"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="age" filter="true"/></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.sex"/></TD>
            <TD class="tdbgcolorloadleft"><logic:equal name="c" property="sex" value="1">男</logic:equal><logic:notEqual name="c" property="sex" value="1">女</logic:notEqual></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.birth"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="birth" filter="true"/></TD>
          </tr>
<%----%>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.moblie"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="moblie" filter="true"/></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.homePhone"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="homePhone" filter="true"/></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.companyPhone"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="companyPhone" filter="true"/></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.homeAddr"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="homeAddr" filter="true"/></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.companyAddr"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="companyAddr" filter="true"/></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.postCode"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="postCode" filter="true"/></TD>
          </tr>
<%----%>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.department"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="department" filter="true"/></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.station"/></TD>
            <TD class="tdbgcolorloadleft"><bean:write name="c" property="station" filter="true"/></TD>
          </tr>

		  </logic:iterate>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='sys' key='sys.close'/>" onclick="javascript:window.close();"/>		    
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
