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
		 <tr>
		    <html:hidden property="id"/>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.name"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="name" readonly="true" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.age"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="age" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.sex"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="sex" styleClass="input">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.man"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.women"/></html:option>
		    </html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.birth"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="birth" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.nation"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="nation" styleClass="input">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="nationlist"
  							property="value"
  							labelProperty="label"/>
    	</html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.blighty"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="blighty" styleClass="input">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="provinceType"
  							property="value"
  							labelProperty="label"/>
    	</html:select> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.polity"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="polity" styleClass="input">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="politylist"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
            </TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.marriage"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="marriage" styleClass="input">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.Ymarriage"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.Nmarriage"/></html:option>
		    </html:select></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.lover"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="lover" readonly="true" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.code"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="code" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.moblie"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="moblie" readonly="true" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.homePhone"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="homePhone" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.companyPhone"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyPhone" readonly="true" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.homeAddr"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="homeAddr" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.companyAddr"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyAddr" readonly="true" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.postCode"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="postCode" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.studyLevel"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="studyLevel" styleClass="input"><html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="degreelist"
  							property="value"
  							labelProperty="label"/>
    	</html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.almaMater"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="almaMater" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.department"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="department"styleClass="input"><html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="departLists"
  							property="value"
  							labelProperty="label"/>
    	</html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.station"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="station" readonly="true" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            
            <TD class="tdbgcolorloadright">员工编号</TD>
            <TD class="tdbgcolorloadleft"><html:text property="employeeId" styleClass="input"></html:text> </TD>
            <TD class="tdbgcolorloadright">是否在职</TD>
            <TD class="tdbgcolorloadleft" colspan="3">
            <html:select property="isLeave" styleClass="input">
			<html:option value="1">在职</html:option>
			<html:option value="0">离职</html:option>
		    </html:select></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright">入职时间</TD>
            <TD class="tdbgcolorloadleft"><html:text property="beginWorkTime" styleClass="input"  readonly="true" ></html:text> 
            <TD class="tdbgcolorloadright">离职时间</TD>
            <TD class="tdbgcolorloadleft"><html:text property="endWorkTime" styleClass="input"  readonly="true"></html:text> 
            </TD>
          </tr>
          <TR>
            <TD class="tdbgcolorloadright">员工照片</TD>
            <TD class="tdbgcolorloadleft" ><img src="<bean:write name='HRBean' property='employeePhoto'/>" width="60" height="75"/></TD>
          
            <TD class="tdbgcolorloadright">离职原因</TD>
            <TD class="tdbgcolorloadleft" ><html:textarea  property="endWorkRemark" rows="5" ></html:textarea> </TD>
          </TR>
          <tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.delSign"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3"><html:select property="delSign" styleClass="input">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.zhengchang"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.dongjie"/></html:option>
		    </html:select></TD>
          </tr>
          <TR>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.recode"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3"><html:textarea  property="recode" rows="5" cols="65" readonly="true"></html:textarea> </TD>
          </TR>
          <TR>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerDetail.remark"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3"><html:textarea  property="remark" rows="5" cols="65" readonly="true"></html:textarea> </TD>
          </TR>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
            <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    &nbsp;
		    <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    &nbsp;
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerDetail.chanel'/>" onclick="javascript:window.close();"/>
		    
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
