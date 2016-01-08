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
    <script language="JavaScript" src="../../../js/calendar.js"></script>
    <script language="javascript">
    	//添加
    	function add(){
    		document.forms[0].action = "../addressList.do?method=operAddressList&type=insert";
    		document.forms[0].submit();
    	}
    	//修改
    	function update(){
    		document.forms[0].action = "../addressList.do?method=toAddressListLoad&type=update";
    		document.forms[0].submit();
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../addressList.do?method=toAddressListLoad&type=delete";
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
   
  
    <html:form action="/oa/privy/addressList" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgpicload">查看详细</td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 <tr>
		    <html:hidden property="id"/>
            <TD class="tdbgcolorloadright" ><b><bean:message key="et.oa.privy.addressList.load.inSort"/></b></TD>
            <TD class="tdbgcolorloadleft" colspan="3"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.sort"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="sort" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.nameAndPerson"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.name"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="name" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.appellation"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="appellation" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.company"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="company" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.station"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="station" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.emailAddress"/></b> </TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personal"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalEmail" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.business"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessEmail" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.other"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="otherEmail" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.phone"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personal"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPhone" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.business"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessPhone" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.fax"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="fax" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.mobile"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="mobile" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.beepPager"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="beepPager" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.other"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="otherPhone" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.address"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.businessAddress"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessAddress" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.post"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessPost" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personalAddress"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalAddress" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.post"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPost" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.otherInfo"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.companyPage"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyPage" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personalPage"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPage" styleClass="input" readonly="true"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.birthday"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="birthday" styleClass="input" readonly="true" onfocus="calendar()"></html:text> 
            </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.remark"/></TD>
            <TD class="tdbgcolorloadleft"><html:textarea  property="remark" rows="5" cols="40" readonly="true"></html:textarea></TD>
          </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
<%--            <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.update'/>" onclick="update()"/>&nbsp;&nbsp;--%>
<%--		    &nbsp;--%>
<%--		    <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.delete'/>" onclick="del()"/>&nbsp;&nbsp;--%>
<%--		    &nbsp;--%>
<%--		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.chanel'/>" onclick="javascript:window.close();"/>--%>
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
