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
    
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>
    <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.name,"姓名")) return false;
            if (!checkNotNull(addstaffer.age,"年龄")) return false;
            if (!checkIntegerRange(addstaffer.age,"年龄",10,60)) return false;
            if (!checkNotNull(addstaffer.birth,"出生日期")) return false;
            if (!checkNotNull(addstaffer.code,"身份证号")) return false;
          //  if (!checkLength2(addstaffer.code, "身份证号", 18, 15)) return false;
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../hr.do?method=operHr&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../hr.do?method=operHr&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../hr.do?method=operHr&type=delete";
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
		function addSelect(receivers){
      
		var page = "/ETOA/oa/assissant/hrphoto.do?method=upload&type=page";
		
		window.open(page);
		
	}
    </script>
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/assissant/hr" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpicload"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.employeeInfo"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.name"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="name" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.age"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="age" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.sex"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="sex">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.man"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.women"/></html:option>
		    </html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.birth"/></TD>
			<TD class="tdbgcolorloadleft"><html:text property="birth" styleClass="input"  readonly="true" onfocus="calendar()"></html:text>
            </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.nation"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="nation">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="nationlist"
  							property="value"
  							labelProperty="label"/>
    	</html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.blighty"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="blighty">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="provinceType"
  							property="value"
  							labelProperty="label"/>
    	</html:select>  </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.polity"/></TD>
            <TD class="tdbgcolorloadleft">
           <html:select property="polity">
    		<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="politylist"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
            </TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.marriage"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="marriage">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.Ymarriage"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.Nmarriage"/></html:option>
		    </html:select></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.lover"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="lover" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.code"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="code" maxlength="18" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.moblie"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="moblie" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.homePhone"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="homePhone" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.companyPhone"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyPhone" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.homeAddr"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="homeAddr" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.companyAddr"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="companyAddr" styleClass="input"></html:text></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.postCode"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="postCode" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.studyLevel"/></TD>
            <TD class="tdbgcolorloadleft"><html:select property="studyLevel"><html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
    		<html:options collection="degreelist"
  							property="value"
  							labelProperty="label"/>
    	</html:select></TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.almaMater"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="almaMater" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.department"/></TD>
            <TD class="tdbgcolorloadleft">
            <html:select property="department">		
        	<html:option value=""><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.select"/></html:option>
        		<html:optionsCollection name="departLists" label="label" value="value"/>
       	    </html:select>
            </TD>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.station"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="station" styleClass="input"></html:text> </TD>
          </tr>
          <tr>
            
            <TD class="tdbgcolorloadright">员工编号</TD>
            <TD class="tdbgcolorloadleft"><html:text property="employeeId" styleClass="input"></html:text> </TD>
            <TD class="tdbgcolorloadright">是否在职</TD>
            <TD class="tdbgcolorloadleft" colspan="3">
            <html:select property="isLeave">
			<html:option value="1">在职</html:option>
			<html:option value="0">离职</html:option>
		    </html:select></TD>
          </tr>
          <tr>
            <TD class="tdbgcolorloadright">入职时间</TD>
            <TD class="tdbgcolorloadleft"><html:text property="beginWorkTime" styleClass="input"  readonly="true" onfocus="calendar()"></html:text> 
            <TD class="tdbgcolorloadright">离职时间</TD>
            <TD class="tdbgcolorloadleft"><html:text property="endWorkTime" styleClass="input"  readonly="true" onfocus="calendar()"></html:text> 
            </TD>
          </tr>
          <TR>
            <TD class="tdbgcolorloadright">员工照片</TD>
            <html:hidden property="employeePhoto"/>
            <TD class="tdbgcolorloadleft" >
            <html:link action="/oa/assissant/hrphoto.do?method=upload&type=page" target="window.open()">
            点击上传头像
            </html:link>
            </TD>
          
            <TD class="tdbgcolorloadright">离职原因</TD>
            <TD class="tdbgcolorloadleft" ><html:textarea  property="endWorkRemark" rows="5" ></html:textarea> </TD>
          </TR>
          <tr>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.delSign"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3">
            <html:select property="delSign">
			<html:option value="1"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.zhengchang"/></html:option>
			<html:option value="0"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.dongjie"/></html:option>
		    </html:select></TD>
          </tr>
          <TR>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.recode"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3"><html:textarea  property="recode" rows="5" cols="65" ></html:textarea> </TD>
          </TR>
          <TR>
            <TD class="tdbgcolorloadright"><bean:message key="agrofront.oa.assissant.hr.hrManagerLoad.remark"/></TD>
            <TD class="tdbgcolorloadleft" colspan="3"><html:textarea  property="remark" rows="5" cols="65" ></html:textarea> </TD>
          </TR>
          
          
           
         
          
          
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="reset" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.chanel'/>" />&nbsp;&nbsp;
		    <input name="addgov" type="button" class="buttom" value="<bean:message key='agrofront.common.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
