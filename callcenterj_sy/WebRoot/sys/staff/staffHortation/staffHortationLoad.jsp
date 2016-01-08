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
    
    <title>奖罚操作</title>

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
<SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>
 
 <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
</SCRIPT>


			<script type="text/javascript">
			
			function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.hortationType,"奖惩类型")) return false;

	   
           return true;
        }
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffHortation.do?method=toStaffHortationOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				function userupdate()
				{
				var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffHortation.do?method=toStaffHortationOper&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				function userdel()
				{
			 		document.forms[0].action="../staffHortation.do?method=toStaffHortationOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			//opener.parent.bottomm.document.all.btnSearch.click();
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
	
  <html:form action="/sys/staff/staffHortation" method="post">
  
     <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
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
  
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    			<tr>
    			
	    			<td class="tdbgcolorloadright">
	    				奖惩类型
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    				<html:select property="hortationType">
	    				<html:option value="">
	    				请选择
	    				</html:option>
	    				<html:option value="award">
	    				奖励
	    				</html:option>
	    				<html:option value="fine">
	    				罚款
	    				</html:option>
	    			
	    				</html:select><font color="#ff0000">*</font>
	    			</td>
	    		</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					奖惩时间
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="hortationTime" onfocus="calendar()" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					奖惩类型
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:textarea property="hortationInfo" rows="5" cols="50"/>
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
