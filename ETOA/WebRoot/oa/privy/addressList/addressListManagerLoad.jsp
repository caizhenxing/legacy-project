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
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.name,"<bean:message key='et.oa.privy.addressList.name'/>")) return false;
<%--            if (!checkNotNull(addstaffer.age,"年龄")) return false;--%>
<%--            if (!checkIntegerRange(addstaffer.age,"年龄",10,60)) return false;--%>
<%--            if (!checkNotNull(addstaffer.birth,"出生日期")) return false;--%>
<%--            if (!checkNotNull(addstaffer.code,"身份证号")) return false;--%>
<%--            if (!checkLength2(addstaffer.code, "身份证号", 18, 15)) return false;--%>
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../addressList.do?method=operAddressList&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../addressList.do?method=operAddressList&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../addressList.do?method=operAddressList&type=delete";
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
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/privy/addressList" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpicload"><bean:message key="et.oa.privy.addressList.load.title"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <logic:equal name="addressListSign" value="company">
		    <html:hidden property="sign" value="0"/>
		    </logic:equal>
		    <logic:equal name="addressListSign" value="personal">
		     <html:hidden property="sign" value="1"/>
		    </logic:equal>
		    <logic:equal name="addressListSign" value="common">
		     <html:hidden property="sign" value="2"/>
		    </logic:equal>
            <TD class="tdbgcolorloadright" ><b><bean:message key="et.oa.privy.addressList.load.inSort"/></b></TD>
            <TD class="tdbgcolorloadleft" colspan="3"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.sort"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="sort">		
            <html:option value=""><bean:message key="et.oa.privy.addressList.sort"/></html:option>
            <html:optionsCollection name="sortList" label="label" value="value"/>
            </html:select></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.nameAndPerson"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.name"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="name" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.appellation"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="appellation" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.company"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="company" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.station"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="station" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.emailAddress"/></b> </TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personal"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalEmail" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.business"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessEmail" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.other"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="otherEmail" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.phone"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personal"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPhone" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.business"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessPhone" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.fax"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="fax" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.mobile"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="mobile" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.beepPager"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="beepPager" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.other"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="otherPhone" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.address"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.businessAddress"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessAddress" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.post"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="businessPost" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personalAddress"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalAddress" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.post"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPost" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><b><bean:message key="et.oa.privy.addressList.load.otherInfo"/></b></TD>
            <TD class="tdbgcolorloadleft"></TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.companyPage"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyPage" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.personalPage"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="personalPage" styleClass="input"></html:text> </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.birthday"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="birthday" styleClass="input" readonly="true" onfocus="calendar()"></html:text> 
            </TD>
          </tr>
          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.privy.addressList.load.remark"/></TD>
            <TD class="tdbgcolorloadleft"><html:textarea  property="remark" rows="5" cols="40" ></html:textarea></TD>
          </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message bundle='sys' key='sys.cancel'/>" />&nbsp;&nbsp;
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
