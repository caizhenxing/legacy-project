<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="hl.bo.email.emailload.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <link REL=stylesheet href="../css/divtext.css" type="text/css">
    <script language="JavaScript" src="../js/divtext.js"></script>
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.takeList,"�������б�")) return false;
        	if (!checkNotNull(addstaffer.emailTitle,"��������")) return false;
           return true;
        }
    	//���
    	function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    		document.forms[0].elements["emailInfo"].value = document.getElementById(maindiv).innerHTML;
    		document.forms[0].action = "../email.do?method=operEmail&type=send";
    		document.forms[0].submit();
    		}
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
		//����ҳ��
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
		//���...
		function addSelectOne(){
			var page = "/ETOA/oa/communicate/email.do?method=toEmaiSelectUser";
			var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
			var obj = document.forms[0];
			window.showModalDialog(page,obj,winFeatures);
		}
		//���(...)
		function addSelectTwo(){
			var page = "/ETOA/oa/communicate/email.do?method=toEmaiSelectList";
			var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
			var obj = document.forms[0];
			window.showModalDialog(page,obj,winFeatures);
		}
		//�ϴ�
		function upload(){
			window.open('email.do?method=upload','','');
		}
		//���浽�ݸ���
		function save(){
		    var f =document.forms[0];
    	    if(checkForm(f)){
				document.forms[0].elements["emailInfo"].value = document.getElementById(maindiv).innerHTML;
	    		document.forms[0].action = "../email.do?method=operEmail&type=savedraft";
	    		document.forms[0].submit();
    		}
		}
    </script>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");</script>
	</logic:notEmpty>
  
    <html:form action="/oa/communicate/email.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message key="agrofront.email.emailLoad.title"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.senduser"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="sendUser" styleClass="input" readonly="true"/>
		    <input type="button" name="btnUpload" value="<bean:message key='agrofront.email.emailLoad.upload'/>" onclick="upload()"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.takeuser"/></td>
		    <td class="tdbgcolorloadleft">
			<html:text property="takeList" size="50" styleClass="input"/>
			<input type="button" name="btnTakeUser" value="<bean:message key='agrofront.email.emailLoad.add'/>" onclick="addSelectOne('take')">
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.select"/></td>
		    <td class="tdbgcolorloadleft">
					<html:text property="copyList" size="50" styleClass="input" /> <input type="button" name="btnCopyList" value="<bean:message key='agrofront.email.emailLoad.add'/>" onclick="addSelectTwo('copy')">
					<html:select property="chk">
						<html:option value="copy"><bean:message key="agrofront.email.emailLoad.copylist"/></html:option>
						<html:option value="secret"><bean:message key="agrofront.email.emailLoad.secretlist"/></html:option>
					</html:select>
			</td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.emailtitle"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:text property="emailTitle" styleClass="input"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorloadright"><bean:message key="agrofront.email.emailLoad.emailinfo"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="emailInfo" style="width:1px;height:1px;"/>
		    <script language="JavaScript" src="../js/format.js"></script>
		    </td>
		  </tr>
		 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <input name="btnSave" type="button" class="bottom" value="<bean:message key='agrofront.email.emailLoad.save'/>" onclick="save()" />&nbsp;&nbsp;
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.email.emailLoad.send'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" />
		    <input name="btnClose" type="button" class="bottom" value="<bean:message key='oa.communicate.email.answeremail.close'/>" onclick="javascript:window.close();"/>
		   </td>
		   
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
