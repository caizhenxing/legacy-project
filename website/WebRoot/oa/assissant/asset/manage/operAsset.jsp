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
            if (!checkNotNull(addstaffer.operType,"<bean:message key='hl.bo.oa.asset.operType'/>")) return false;
            if (!checkNotNull(addstaffer.operassetsNum,"<bean:message key='hl.bo.oa.asset.assetsNum'/>")) return false;
                        
           // if (!checkCodeRange(addstaffer.code, "身份证号", 15, 18)) return false;
              return true;
    }
	function a()
	{
		var f =document.forms[0];
    	if(checkForm(f)){
			document.forms[0].action="/ETOA/oa/assissant/asset/assetsOperAction.do?method=addAssetOper";
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
	
	}
	function submitOper()
	{			
		var f =document.forms[0];
    	if(checkForm(f)){
			var flag =confirm("<bean:message key='hl.bo.oa.asset.yesorno'/>");
			if(flag ==true)
			{
	    		document.forms[0].action="/ETOA/oa/assissant/asset/assetsOperAction.do?method=operAsset";
		    	document.forms[0].submit();
    		}
    		else
    		{
    		
    		}			
	    }	
	}
</SCRIPT>
  </head>
  
  <body>
  <html:form action="/oa/assissant/asset/assetsOperAction">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key='hl.bo.oa.asset.oper'/></td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.operCode'/></td>
    <td class="tdbgcolorloadleft">					
						<html:text property="operCode" readonly="true"></html:text>
						<html:hidden property="operId" />&nbsp;
						<a href="/ETOA/oa/assissant/asset/assetsOperAction.do?method=toAssetOperLoad&type=v&did=<bean:write name='assetsForm' property="assetsOper"/>" target="_blank">查看批次信息</a>
				</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsName'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="operName" readonly="true"></html:text>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.operType'/></td>
    <td class="tdbgcolorloadleft">
						<html:select property="operType">
							<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:option value="zhuanchu"><bean:message key='hl.bo.oa.asset.zhuanchu'/></html:option>
							<html:option value="maichu"><bean:message key='hl.bo.oa.asset.maichu'/></html:option>
							<html:option value="zhejiu"><bean:message key='hl.bo.oa.asset.zhejiu'/></html:option>
							<html:option value="baofei"><bean:message key='hl.bo.oa.asset.baofei'/></html:option>
							<html:option value="fenpei"><bean:message key='hl.bo.oa.asset.fenpei'/></html:option>
						</html:select>
				</td>
  </tr> 
  <!-- -->
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.operTime'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="operTime" readonly="true"/>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsPrice'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="assetsPrice" readonly="true"></html:text>
				</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsNum'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="operassetsNum" />
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.inCompany'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="inCompany" />
	</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.inPeople'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="inPeople" />
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright">
					<bean:message key='hl.bo.oa.asset.outCompany'/>
				</td>
    <td class="tdbgcolorloadleft">
						<html:text property="outCompany"></html:text>
				</td>
  </tr> 
  
  <!--  -->
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.outPeople'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="outPeople" />
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright">
					<bean:message key='hl.bo.oa.asset.info_remark'/>
				</td>
    <td class="tdbgcolorloadleft">
						<html:textarea property="remark"></html:textarea>
				</td>
  </tr> 
   
  <!-- -->
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message key='hl.bo.oa.asset.item'/></td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsId'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="assetsId" readonly="true" />
						<html:hidden property="assetsOper"/>
	</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsName'/></td>
    <td class="tdbgcolorloadleft">
						<html:text property="assetsName" readonly="true"/>
				</td>
  </tr>
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsWithfor'/></td>
    <td class="tdbgcolorloadleft">
						<html:select property="assetsWithfor">
							<html:option value=""><bean:message key='hl.bo.oa.asset.pleaseSelect'/></html:option>
							<html:options collection="dl"
	  							property="value"
	  							labelProperty="label"/>
						</html:select>
	</td>
  </tr> 
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsType'/></td>
    <td class="tdbgcolorloadleft">
						<html:hidden property="assetsType" />
						<logic:equal name="assetsForm" property="assetsType" value="0"><bean:message key='hl.bo.oa.asset.guding'/></logic:equal>
						<logic:equal name="assetsForm" property="assetsType" value="-1"><bean:message key='hl.bo.oa.asset.xiaohao'/></logic:equal>
				</td>
  </tr> 
  
  
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message key='hl.bo.oa.asset.assetsNum'/></td>
    <td class="tdbgcolorloadleft">
					<logic:equal name="assetsForm" property="assetsType" value="-1">
						<html:text property="assetsNum" readonly="true"/>
					</logic:equal>
					<logic:equal name="assetsForm" property="assetsType" value="0">
						<html:text property="assetsNum" readonly="true"/>
					</logic:equal>	
				</td>
  </tr> 
  
   
  <!-- -->
  <tr>
    <td colspan="2" class="tdbgcolorloadbuttom">
    <logic:equal name="type" value="i">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.insert'/>" onclick="a()"/>
    </logic:equal>
    <logic:equal name="type" value="u">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.update'/>" onclick="u()"/>
    </logic:equal>
    <logic:equal name="type" value="d">
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.delete'/>" onclick="d()"/>
    </logic:equal>
    <input name="Submit2" type="button" class="bottom" value="<bean:message key='agrofront.common.submit'/>" onclick="submitOper()"/>
    <input name="Submit3" type="reset" class="bottom" value="<bean:message key='agrofront.common.cannal'/>" />
    <input name="cccc" type="button" value="<bean:message key='agrofront.common.close'/>" onclick="c()"/>
    
    </td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
