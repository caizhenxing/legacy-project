
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
    
    <title><bean:message bundle='sys' key='sys.group'/></title>
    
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
<script language="javascript" src="../../js/tools.js"></script>
<SCRIPT>
	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.name,"<bean:message bundle='sys' key='sys.group.info.groupname'/>")) return false;
            if (!checkNotNull(addstaffer.freezeMark,"<bean:message bundle='sys' key='sys.group.info.dongjie'/>")) return false;
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
		window.close();
	}
	function d()
	{
		document.forms[0].action+="?method=del";
    	document.forms[0].submit();
	}
	//返回页面
	function toback(){
		opener.parent.topFrame.document.all.Submit.click();
	}
	
	function u()
	{
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action+="?method=update";
	    	document.forms[0].submit();
	    }
	}
</SCRIPT>
  </head>
  
  <body onunload="toback()">
  
      <logic:notEmpty name="idus_state">
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
	
  <html:form action="/sys/group/GroupOper.do">
    <table width="40%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.group.info.groupname'/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="name"></html:text>
		<html:hidden property="id"/>
	</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.group.info.dongjie'/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="freezeMark">
			<html:option value="1"><bean:message bundle='sys' key='sys.group.info.common'/></html:option>
			<html:option value="0"><bean:message bundle='sys' key='sys.group.info.dongjie'/></html:option>
		</html:select>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle='sys' key='sys.property.remark'/></td>
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
    <input name="cccc" type="button" value="<bean:message bundle='sys' key='sys.close'/>" onclick="c()"/>
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
