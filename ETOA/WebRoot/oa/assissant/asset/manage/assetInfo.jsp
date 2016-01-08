<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<script language="javascript" src="../../../../js/form.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key='hl.bo.oa.asset'/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../../../js/form.js"></script>
<SCRIPT>
	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.assetsOper,"<bean:message key='hl.bo.oa.asset.assetsOper'/>")) return false;
            if (!checkNotNull(addstaffer.assetsId,"<bean:message key='hl.bo.oa.asset.assetsId'/>")) return false;
            if (!checkNotNull(addstaffer.assetsNum,"<bean:message key='hl.bo.oa.asset.assetsNum'/>")) return false;
            if (!checkNotNull(addstaffer.assetsWithfor,"<bean:message key='hl.bo.oa.asset.assetsWithfor'/>")) return false;
            if (!checkNotNull(addstaffer.assetsType,"<bean:message key='hl.bo.oa.asset.assetsType'/>")) return false;
            if (!checkNotNull(addstaffer.assetsName,"<bean:message key='hl.bo.oa.asset.assetsName'/>")) return false;
            
           // if (!checkCodeRange(addstaffer.code, "身份证号", 15, 18)) return false;
              return true;
    }

	function a()
	{
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action="/ETOA/oa/assissant/asset/assetsOperAction.do?method=addAssetInfo";
    		document.forms[0].submit();
    	}
	}
	function c()
	{
		
		//parent.mainFrame.location.href="fffffffffffffff";
		//window.opener.parent.mainFrame.location.href="/ETOA/sys/group/GroupOper.do?method=search";
		window.close();
	}
	function d()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=del";
    	document.forms[0].submit();
	}
	function u()
	{
		document.forms[0].action="/ETOA/oa/assissant/conference/conferOper.do?method=update";
    	document.forms[0].submit();
	}
	function ud()
	{
		document.location.href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=checkBatchNum";
	}
	function change()
	{
		var pl =document.getElementsByName("assetsOper");
		ple =pl[0];
		/*
		if(ple.value !="")
			alert(ple.value);
			*/
		document.location.href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=checkBatchNum&AssetPC="+ple.value;	
	}
</SCRIPT>
  </head>
  
  <body>
  <html:form action="/oa/assissant/asset/assetsOperAction">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key="hl.bo.oa.asset.select"/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsOper"/></td>
    <td align="center" class="tdbgcolorloadleft">
					<P align="left">
						<logic:notEqual name="type" value="v">
						<html:select property="assetsOper" onchange="change()">
							<html:option value=""><bean:message key="hl.bo.oa.asset.pleaseSelect"/></html:option>
							<html:options collection="bl"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
						</logic:notEqual>
						
						<logic:equal name="type" value="v"> 
						<html:select property="assetsOper" >
							<html:option value=""><bean:message key="hl.bo.oa.asset.pleaseSelect"/></html:option>
							<html:options collection="bl"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
						</logic:equal>
						<logic:equal name="type" value="v">
						<a href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetOperLoad&type=v&did=<bean:write name='assetsForm' property="assetsOper"/>" target="_blank"><bean:message key="hl.bo.oa.asset.message5"/></a>
						</logic:equal>
						<logic:equal name="show" value="show">
						<a href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetOperLoad&type=v&did=<bean:write name='assetsForm' property="assetsOper"/>" target="_blank"><bean:message key="hl.bo.oa.asset.message5"/></a>
						</logic:equal>
					 </P>
				</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsId"/></td>
    <td align="center" class="tdbgcolorloadleft">
					<P align="left">
						<html:text property="assetsId"></html:text>
					</P>
				</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsNum"/></td>
    <td align="center" class="tdbgcolorloadleft">
					<P align="left">
						<html:text property="assetsNum"></html:text>&nbsp;
						<logic:equal name="show" value="show">
							<bean:message key="hl.bo.oa.asset.message1"/><bean:write name="onum"/><bean:message key="hl.bo.oa.asset.message2"/><bean:write name="inum"/>
						</logic:equal>
					</P>
				</td>
  </tr> 
  <!-- -->
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsWithfor"/></td>
    <td align="center" class="tdbgcolorloadleft">
					<P align="left">
					<html:select property="assetsWithfor">
							<html:option value=""><bean:message key="hl.bo.oa.asset.pleaseSelect"/></html:option>
							<html:options collection="dl"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
					</P>
				</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsType"/></td>
    <td align="center" class="tdbgcolorloadleft">
					<P align="left">						
						<html:select property="assetsType">
							<html:option value=""><bean:message key="hl.bo.oa.asset.pleaseSelect"/></html:option>
							<html:option value="0"><bean:message key="hl.bo.oa.asset.guding"/></html:option>
							<html:option value="-1"><bean:message key="hl.bo.oa.asset.xiaohao"/></html:option>
						</html:select>
					</P>
				</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.assetsName"/></td>
    <td align="center" class="tdbgcolorloadleft">
    				<P align="left">						
						<html:text property="assetsName" ></html:text>						
					</P>
					
				</td>
  </tr>
  <tr>
    <td align="right" class="tdbgcolorloadright"><bean:message key="hl.bo.oa.asset.info_remark"/></td>
    <td align="center" class="tdbgcolorloadleft">
    		<P align="left">
						<html:textarea property="info_remark" />
					</P>
	</td>
  </tr>   
  <!-- -->
  <tr>
    <td colspan="2" align="center" class="tdbgcolorloadbuttom">
	<logic:present name="rnum">    
    <logic:lessEqual name="rnum" value="0">

    	<font color="red"><bean:message key="hl.bo.oa.asset.message3"/></font><br>
    </logic:lessEqual>
    </logic:present>
    <logic:notPresent name="rnum">
    	<logic:equal name="type" value="i">
    	<bean:message key="hl.bo.oa.asset.message4"/><br>
    	</logic:equal>
    </logic:notPresent>
    <logic:equal name="type" value="i">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="a()"/>
    </logic:equal>
    <logic:equal name="type" value="u">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="u()"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="d()"/>
    </logic:equal>
    <logic:notEqual name="type" value="i">
    <input name="Submit3" type="reset" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" />
    </logic:notEqual>
    <input name="cccc" type="button" value="<bean:message key='agrofront.common.close'/>" onclick="c()"/>
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
