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
    
    <title>职工操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<link href="../../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
 <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%-- <link href="../images/css/jingcss.css" rel="stylesheet" type="text/css" />--%>
<%-- <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>--%>
 
 <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
</SCRIPT>
<%-- <script type="text/javascript">--%>
<%--	 function useradd()--%>
<%--	 {--%>
<%--	 	document.forms[0].action = "../user.do?method=toUserList";--%>
<%--    	document.forms[0]. = "bottomm";--%>
<%--	 }--%>
<%-- </script>--%>

			<script type="text/javascript">
			
			function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.companyName,"单位名称")) return false;
 			if (!checkNotNull(addstaffer.helpsign,"标识")) return false;
	   
           return true;
        }
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../workUnitInfo.do?method=toInterOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function userupdate()
				{
			 		document.forms[0].action="../workUnitInfo.do?method=toInterOper&type=update";
			 	
			 		document.forms[0].submit();
				}
				function userdel()
				{
			 		document.forms[0].action="../workUnitInfo.do?method=toInterOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearchUnit.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
			</script>

  </head>
  
  <body onunload="toback()">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/sys/staff/workUnitInfo" method="post">
  
     <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    	<logic:equal name="opertype" value="insert">
		    		添加信息
		    	</logic:equal>
		    	<logic:equal name="opertype" value="detail">
		    		查看信息
		    	</logic:equal>
		    	<logic:equal name="opertype" value="update">
		    		修改信息
		    	</logic:equal>
		    	<logic:equal name="opertype" value="delete">
		    		删除信息
		    	</logic:equal>
		    </td>
		  </tr>
		</table>
  
    	<table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    			<tr>
    			
	    			<td class="tdbgcolorloadright">
	    				单位名称
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    				<html:text property="companyName" styleClass="input"/>
	    				<font color="#ff0000">*</font>
	    			</td>
	    		</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					标识
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="helpsign" styleClass="input"/><font color="#ff0000">*</font>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					单位电话
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="companyTel" styleClass="input" size="40"/>
	    				</td>
					</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					传真
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="fax" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					邮编
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="post" styleClass="input"/>
	    				</td>
	    			</tr>
	    			
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					单位类型
	    				</td>
						<td class="tdbgcolorloadleft" colspan="3">
	    					<html:select property="dictCompanyType">
	    						<html:option value="">
	    						请选择
	    						</html:option>
	    						<html:option value="PBusiness">
	    						私企
	    						</html:option>
	    						<html:option value="CBusiness">
	    						国企
	    						</html:option>
	    					</html:select>
	    				</td>
	    			</tr>
	    			
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					单位主页
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="homepage" styleClass="input"/>
	    				</td>
	    			</tr>
	    			
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					单位地址
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="companyAddress" size="50" styleClass="input"/>
	    				</td>
	    			</tr>
	    			
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					单位描述
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:textarea property="remark" rows="5" cols="80" styleClass="input"/>
	    				</td>
	    			</tr>
    		
    		<tr>
    			<td colspan="4" bgcolor="#ffffff" align="center">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnadd" class="button" value="添加" onclick="useradd()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnupd"  class="button" value="修改" onclick="userupdate()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btndel"  class="button" value="删除" onclick="userdel()"/>
    			</logic:equal>
    			
    				<input type="button" name="" value="关闭"  class="button" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>
    		<html:hidden property="id"/>
    		
    	</table>
    	</html:form>
  </body>
</html:html>
