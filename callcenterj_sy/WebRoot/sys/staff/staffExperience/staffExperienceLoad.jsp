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
    
    <title>ְ����������</title>

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
        	if (!checkNotNull(addstaffer.dictExperienceType,"��������")) return false;
	   
           return true;
        }
				function StaffExperienceadd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffExperience.do?method=toStaffExperienceOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				
				function StaffExperienceupdate()
				{
				 var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffExperience.do?method=toStaffExperienceOper&type=update";
			 	
			 		document.forms[0].submit();
			 		}
				}
				
				function StaffExperiencedel()
				{
			 		document.forms[0].action="../staffExperience.do?method=toStaffExperienceOper&type=delete";
			 		
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
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/sys/staff/staffExperience" method="post">
  
     <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
     	
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    	<logic:equal name="opertype" value="insert">
		    		�����Ϣ
		    	</logic:equal>
		    	<logic:equal name="opertype" value="detail">
		    		�鿴��Ϣ
		    	</logic:equal>
		    	<logic:equal name="opertype" value="update">
		    		�޸���Ϣ
		    	</logic:equal>
		    	<logic:equal name="opertype" value="delete">
		    		ɾ����Ϣ
		    	</logic:equal>
		    </td>
		  </tr>
		</table>
  
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    			<tr>
    			
	    			<td class="tdbgcolorloadright">
	    				��������
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    				<html:select property="dictExperienceType">
	    				<html:option value="">
	    				��ѡ��
	    				</html:option>
	    				<html:option value="work">
	    				����
	    				</html:option>
	    				<html:option value="teach">
	    				����
	    				</html:option>
	    			
	    				</html:select><font color="#ff0000">*</font>
	    			</td>
	    		</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					��ʼʱ��
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="beginTime" onfocus="calendar()" styleClass="input"/>
	    				</td>
	    			
	    			</tr>
	    			<tr>
	    			
	    				<td class="tdbgcolorloadright">
	    					����ʱ��
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="endTime" onfocus="calendar()" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					������λ/ѧУ����
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="company" size="40" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					����ְ��
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="duty" size="40" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					��ַ
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="address" size="60" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					֤����
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="proveMan" size="30" styleClass="input"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="tdbgcolorloadright">
	    					������Ϣ
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:textarea property="message"  rows="7" cols="50"/>
	    				</td>
	    			</tr>
    		<html:hidden property="id"/>
    		<tr>
    			<td colspan="4" bgcolor="#ffffff" align="center">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnadd" class="button" value="���" onclick="StaffExperienceadd()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnupd"  class="button" value="�޸�" onclick="StaffExperienceupdate()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btndel"  class="button" value="ɾ��" onclick="StaffExperiencedel()"/>
    			</logic:equal>
    			
    				<input type="button" name="" value="�ر�"  class="button" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>

    	</table>
    	</html:form>
  </body>
</html:html>
