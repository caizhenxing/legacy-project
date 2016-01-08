
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
    
    <title><bean:message bundle='sys' key='sys.user'/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/form.js"></script>
<script language="javascript" src="../../js/calendar.js"></script>
<SCRIPT>
	function checkForm(addstaffer){
			//alert(addstaffer.userName.value);
            if (!checkNotNull(addstaffer.userId,"<bean:message bundle='sys' key='sys.user.info.id'/>")) return false;
            //alert("eeeeeeeeeeeeeeeeeeeeeeeeee");            	
            if (!checkNotNull(addstaffer.userName,"<bean:message bundle='sys' key='sys.user.info.userName'/>")) return false;
            if (!checkNotNull(addstaffer.sysRole,"<bean:message bundle='sys' key='sys.user.info.sysRole'/>")) return false;
            if (!checkNotNull(addstaffer.sysGroup,"<bean:message bundle='sys' key='sys.user.info.sysGroup'/>")) return false;
            if (!checkNotNull(addstaffer.password,"<bean:message bundle='sys' key='sys.user.info.password'/>")) return false;
            if (!checkNotNull(addstaffer.repassword,"<bean:message bundle='sys' key='sys.user.info.repassword'/>")) return false;            

            if (addstaffer.password.value !=addstaffer.repassword.value)
            {
            	alert("<bean:message bundle='sys' key='sys.user.info.message1'/>");
            	return false;
            }
            if (!checkNotNull(addstaffer.departmentId,"<bean:message bundle='sys' key='sys.user.info.departmentId'/>")) return false;
            if (!checkNotNull(addstaffer.freezeMark,"<bean:message bundle='sys' key='sys.user.info.dongjiebiaozhi'/>")) return false;
              return true;
    }
    function checkFormUpdate(addstaffer)
    {
    	if (!checkNotNull(addstaffer.userId,"<bean:message bundle='sys' key='sys.user.info.id'/>")) return false;
    	if (!checkNotNull(addstaffer.userName,"<bean:message bundle='sys' key='sys.user.info.userName'/>")) return false;
        if (!checkNotNull(addstaffer.sysRole,"<bean:message bundle='sys' key='sys.user.info.sysRole'/>")) return false;
        if (!checkNotNull(addstaffer.sysGroup,"<bean:message bundle='sys' key='sys.user.info.sysGroup'/>")) return false;
        if (!checkNotNull(addstaffer.departmentId,"<bean:message bundle='sys' key='sys.user.info.departmentId'/>")) return false;
        if (!checkNotNull(addstaffer.freezeMark,"<bean:message bundle='sys' key='sys.user.info.dongjiebiaozhi'/>")) return false;
        return true;
    }
	function a()
	{
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action+="?method=add";
	    	document.forms[0].submit();
	    }	
	}
	function c()
	{
		//window.close();
		//parent.mainFrame.location.href="fffffffffffffff";
		window.opener.parent.mainFrame.location.href="fffffffffffffff";
	}
	function d()
	{
		document.forms[0].action+="?method=del";
    	document.forms[0].submit();
	}
	function u()
	{
		var f =document.forms[0];
		
    	if(checkFormUpdate(f)){
			document.forms[0].action+="?method=update";
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
</SCRIPT>
  </head>
  
  <body>
  <html:form action="/sys/user/UserOper">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message bundle='sys' key='sys.user.info.bitian'/></td>
  </tr>
  
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.id'/></TD>
    <td  class="tdbgcolorloadleft">
    <logic:equal name="type" value="i">
    <html:text property="userId"></html:text>
    </logic:equal>
    <logic:notEqual name="type" value="i">
    <html:text property="userId" readonly="true"></html:text>
    </logic:notEqual>
    
	</TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.userName'/></TD>
    <td  class="tdbgcolorloadleft">
		<html:text property="userName"></html:text>
	</TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.sysRole'/></TD>
    <td  class="tdbgcolorloadleft">
					
						<html:select property="sysRole">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:options collection="rl" property="value" labelProperty="label" />
						</html:select>
					
				</TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.sysGroup'/></TD>
     <td  class="tdbgcolorloadleft">
						<html:select property="sysGroup">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:options collection="gl" property="value" labelProperty="label" />
						</html:select>
				</TD>
  </TR>
  <logic:notEqual name="type" value="i">
  	<html:hidden property="password"/>
  </logic:notEqual>
  <logic:equal name="type" value="i">
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.password'/></TD>
    <td  class="tdbgcolorloadleft">
							<html:password property="password"></html:password>
					</TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.repassword'/></TD>
    <td  class="tdbgcolorloadleft">
							<html:password property="repassword"></html:password>
					</TD>
  </TR>
  </logic:equal>
  <TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.departmentId'/></TD>
    <td  class="tdbgcolorloadleft">
						<html:select property="departmentId">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:options collection="dl" property="value" labelProperty="label" />
						</html:select>
				</TD>
  </TR><TR>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.group.info.dongjie'/></TD>
    <td  class="tdbgcolorloadleft">
						<html:select property="freezeMark">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:option value="1"><bean:message bundle='sys' key='sys.group.info.common'/></html:option>
							<html:option value="0"><bean:message bundle='sys' key='sys.group.info.dongjie'/></html:option>
						</html:select>
	</TD>
  </TR>
  <TR>
    <td class="tdbgcolorloadright"></TD>
    <td  class="tdbgcolorloadleft"></TD>
  </TR><tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.realName'/></td>
    <td  class="tdbgcolorloadleft">
						<html:text property="realName"></html:text>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.sexId'/></td>
    <td  class="tdbgcolorloadleft">
					
						<html:radio property="sexId" value="1"><bean:message bundle='sys' key='sys.user.info.man'/></html:radio>
						<html:radio property="sexId" value="0"><bean:message bundle='sys' key='sys.user.info.woman'/></html:radio>
					
				</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.identityKind'/></td>
    <td  class="tdbgcolorloadleft">
						<html:text property="identityKind" />
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.identityCard'/></td>
    <td  class="tdbgcolorloadleft">
						<html:text property="identityCard" />
				</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.birthday'/></td>
    <td  class="tdbgcolorloadleft">
						<html:text property="birthday" readonly="true" onfocus="calendar()"/>
					
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.countryId'/></td>
    <td  class="tdbgcolorloadleft">
						<html:select property="countryId">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:options collection="cl" property="value" labelProperty="label" />
						</html:select>
				</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.provinceId'/></td>
    <td  class="tdbgcolorloadleft">
					<html:select property="provinceId">
							<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
							<html:options collection="provincel" property="value" labelProperty="label" />
						</html:select>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.qq'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="qq"></html:text>
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.bloodType'/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="bloodType">
			<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
			<html:options collection="bloodl" property="value" labelProperty="label" />
		</html:select>
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.address'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="address" />
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.postalcode'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="postalcode" />
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.mobile'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="mobile" />
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.finishSchool'/></td>
    <td  class="tdbgcolorloadleft">
    	<html:text property="finishSchool" />
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.speciality'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="speciality" />
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.workId'/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="workId">
			<html:option value=""><bean:message bundle='sys' key='sys.pselect'/></html:option>
			<html:options collection="workl" property="value" labelProperty="label" />
		</html:select>
	</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.user.info.homepage'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="homepage" />
	</td>
  </tr> 
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.property.remark'/></td>
    <td  class="tdbgcolorloadleft">
		<html:textarea property="remark"></html:textarea>
	</td>
  </tr>
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="i">
    <input name="Submit2" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.insert'/>" onclick="a()"/>
    </logic:equal>
    <logic:equal name="type" value="u">
    <input name="Submit2" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.update'/>" onclick="u()"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.delete'/>" onclick="d()"/>
    </logic:equal>
    <input name="Submit3" type="reset" class="bottom" value="<bean:message bundle='sys' key='sys.cancel'/>" />
    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:window.close();"/>
    
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
