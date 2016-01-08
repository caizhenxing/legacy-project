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
    
    <title><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.documentShenpi"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            //if (!checkNotNull(addstaffer.name,"姓名")) return false;
            //if (!checkNotNull(addstaffer.age,"年龄")) return false;
            //if (!checkIntegerRange(addstaffer.age,"年龄",10,60)) return false;
            //if (!checkNotNull(addstaffer.birth,"出生日期")) return false;
            //if (!checkNotNull(addstaffer.code,"身份证号")) return false;
          	//if (!checkLength2(addstaffer.code, "身份证号", 18, 15)) return false;
              return true;
            }
    	//审批
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../doc.do?method=operDoc&type=shenpi";
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
		//返回页面
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>window.close();alert("<bean:write name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/assissant/doc" method="post">
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" class="tdbgpicload"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.documentShenpi"/></td>
		  </tr>
		<tr>
		    <html:hidden property="id"/>
		    <html:hidden property="folderId"/>
		    <html:hidden property="folderWord"/>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.folderName"/></TD>
            <td  class="tdbgcolorloadleft"><html:text property="folderName" styleClass="input" readonly="true"></html:text></TD>
          </tr>            
          <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.folderCode"/></TD>
            <td  class="tdbgcolorloadleft"><html:text property="folderCode" styleClass="input" readonly="true"></html:text></TD>
          </tr>  
          <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.folderType"/></TD>
            <td  class="tdbgcolorloadleft">
            <html:text property="folderType" styleClass="input" readonly="true"/></TD>
          </tr>   
          <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.createTime"/></TD>
            <td  class="tdbgcolorloadleft">
            <html:text property="createTime" styleClass="input" readonly="true"/></TD>
          </tr>
           <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.updateTime"/></TD>
            <td  class="tdbgcolorloadleft">
            <html:text property="updateTime" styleClass="input" readonly="true"/></TD>
          </tr>  
          <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.folderVersion"/></TD>
            <td  class="tdbgcolorloadleft">
            <html:text property="folderVersion" styleClass="input" readonly="true"/></TD>
          </tr>
          <tr>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.folderSign"/></TD>
            <td  class="tdbgcolorloadleft">
            <html:select property="folderSign" styleClass="input">
            <html:option value="Y"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.pass"/></html:option>
			<html:option value="N"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.notpass"/></html:option>
		    </html:select>
		   </TD>
          </tr>   
          <TR>
            <td class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.document.docManagerShenPi.remark"/></TD>
            <td  class="tdbgcolorloadleft"><html:textarea  property="remark" rows="5" cols="65" readonly="true"></html:textarea> </TD>
          </TR>
		  <tr>	
		    <td colspan="2"  class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="shenpi">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.document.docManagerShenPi.shenpi'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.document.docManagerShenPi.chanel'/>" /></td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
