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
    
    <title>数据库备份设定操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>


 <SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript></SCRIPT>
 <script language="javascript" src="../../js/clockCN.js"></script>
 <script language="javascript" src="../../js/clock.js"></script>


<%
	String type = request.getAttribute("opertype").toString();
 %>

<script type="text/javascript">
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.dbtype,"选择备份文件时间方式")) return false;
			if(document.forms[0].dbtype.value!='day')
        	if (!checkNotNull(addstaffer.begindate,"选择备份文件日期")) return false;
			
        	if (!checkNotNull(addstaffer.begintime,"选择备份文件时间")) return false;

           return true;
        }
        
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../../bak.do?method=tobakOper&type=insert";
			 		document.forms[0].submit();

			 		}
				}
				function userupdate()
				{
			 		document.forms[0].action="../../bak.do?method=tobakOper&type=update";
			 	
			 		document.forms[0].submit();
				}
				function userdel()
				{
			 		document.forms[0].action="../../bak.do?method=tobakOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
		
		
			function dbtypeChange()
			{
				document.forms[0].action="../../bak.do?method=dbtypeChange&btntype=<%=type%>";
				document.forms[0].submit();
			}
			</script>

  </head>
  
  <body onunload="toback()">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/bak.do" method="post">
  
     <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    	<logic:equal name="pageType" value="inster">
		    		添加信息
		    	</logic:equal>
		    	<logic:equal name="pageType" value="look">
		    		查看信息
		    	</logic:equal>
		    	<logic:equal name="pageType" value="update">
		    		修改信息
		    	</logic:equal>
		    </td>
		  </tr>
		</table>
  
    	<table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">

				<tr>
	    			 <td class="tdbgcolorloadright">
	    				选择备份文件时间方式
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    			<html:select property="dbtype" onchange="dbtypeChange()">		
	    			<html:option value="">请选择</html:option>
	        		<html:option value="day">日备份</html:option>
	        		<html:option value="week">周备份</html:option>
	        		<html:option value="month">月备份</html:option>
	        		<html:option value="quarter">季备份</html:option>
	        	  </html:select>
	    				<font color="#ff0000">*</font>
	    			</td>
	    		</tr>
	    		<logic:notEqual value="day" name="btntype">
        		<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件日期
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="begindate" onfocus="calendar()"/><font color="#ff0000">*</font>
	    					
	    				</td>
	    			</tr>
        		</logic:notEqual>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					选择备份文件时间
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="begintime" styleClass="input"/>
          		            <input type="button" class="button" value="时间" onclick="OpenTime(document.all.begintime);"/>
          		            <font color="#ff0000">*</font>
	    				</td>
	    			</tr>
	    			
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					备注
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="remark" size="50" styleClass="input"/>
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
