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
    
    <title>Ա��������Ϣ</title>

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
 
<SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
</SCRIPT>


			<script type="text/javascript">
<%--			--%>
			function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.parentName,"��������")) return false;
	   
           return true;
        }
				function useradd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffParent.do?method=toStaffParentInfoOper&type=insert";
			 		document.forms[0].submit();
			 		}
				}
				function userupdate()
				{
				   var f =document.forms[0];
    	    		if(checkForm(f)){
				 		document.forms[0].action="../staffParent.do?method=toStaffParentInfoOper&type=update";
				 		document.forms[0].submit();
			 		}
				}
				function userdel()
				{
			 		document.forms[0].action="../staffParent.do?method=toStaffParentInfoOper&type=delete";
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
	
  <html:form action="/sys/staff/staffParent" method="post">
  
     <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    	<logic:equal name="pagetype" value="inster">
		    		�����Ϣ
		    	</logic:equal>
		    	<logic:equal name="pagetype" value="detail">
		    		�鿴��Ϣ
		    	</logic:equal>
		    	<logic:equal name="pagetype" value="update">
		    		�޸���Ϣ
		    	</logic:equal>
		    	<logic:equal name="pagetype" value="delete">
		    		ɾ����Ϣ
		    	</logic:equal>
		    </td>
		  </tr>
		</table>
  
    	<table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    	  <tr>
   			 <td colspan="4" class="tdbgpicload" >Ա��������Ϣ</td>
  		  </tr>
		 
          <tr>
           <td class="tdbgcolorloadright">��������</td>
            <td  class="tdbgcolorloadleft" colspan="4">
		    <html:text property="parentName"></html:text><font color="#ff0000">*</font>
	        </td>
         </tr>
    	 <tr>
    	    <td class="tdbgcolorloadright">������ϵ</td>
            <td  class="tdbgcolorloadleft">
		    <html:text property="parentConnection"></html:text>
	        </td>
	         <td class="tdbgcolorloadright" >������ò</td>
            <td  class="tdbgcolorloadleft">
		    <html:text property="dictParentPolity"></html:text>
	        </td>        
         </tr>
         <tr>
            <td class="tdbgcolorloadright" >������λ</td>
            <td  class="tdbgcolorloadleft">
		    <html:text property="work" size="40"></html:text>
	        </td>
            <td class="tdbgcolorloadright">��ϵ�绰</td>
            <td  class="tdbgcolorloadleft">
		    <html:text property="linkTel"></html:text>
	        </td>
          
         </tr>
         <tr>
            <td class="tdbgcolorloadright">��ע</td>
            <td  class="tdbgcolorloadleft" colspan="4">
	        <html:textarea cols="60" rows="4" property="remark"></html:textarea>
	        </td>
        </tr> 
    		<tr>
    			<td colspan="4" bgcolor="#ffffff" align="center">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnadd" class="button" value="���" onclick="useradd()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnupd"  class="button" value="�޸�" onclick="userupdate()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btndel"  class="button" value="ɾ��" onclick="userdel()"/>
    			</logic:equal>
    			
    				<input type="button" name="" value="�ر�"  class="button" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>
    		<html:hidden property="id"/>

    	</table>
    	</html:form>
  </body>
</html:html>
