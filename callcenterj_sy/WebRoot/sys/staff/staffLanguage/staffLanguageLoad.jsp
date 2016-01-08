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
    
    <title>���Բ���</title>

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
        	if (!checkNotNull(addstaffer.dictLanguageType,"��������")) return false;
			if (!checkNotNull(addstaffer.dictLanguageDegree,"���Եȼ�")) return false;
	   
           return true;
        }
        
        
				function staffLanguageadd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
				 		document.forms[0].action="../staffLanguage.do?method=toStaffLanguageOper&type=insert";
				 		document.forms[0].submit();
			 		}
				}
				function staffLanguageupdate()
				{
				var f =document.forms[0];
    	    		if(checkForm(f)){
					 	document.forms[0].action="../staffLanguage.do?method=toStaffLanguageOper&type=update";
					 	document.forms[0].submit();
			 		}
				}
				function staffLanguagedel()
				{
			 		document.forms[0].action="../staffLanguage.do?method=toStaffLanguageOper&type=delete";
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
	
	<html:form action="/sys/staff/staffLanguage" method="post">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
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
  
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
    			<tr>
	    		<td class="tdbgcolorloadright">
    				��������
    			</td>
    			<td class="tdbgcolorloadleft">
	    				<html:select property="dictLanguageType">
		    				<html:option value="">
		    				��ѡ��
		    				</html:option>
		    				<html:option value="english">
		    					Ӣ��
		    				</html:option>
		    				<html:option value="japanese">
		    					����
		    				</html:option>
	    				</html:select>
	    		</tr>
	    		<tr>
	    		<td class="tdbgcolorloadright">
    				���Եȼ�
    			</td>
    			<td class="tdbgcolorloadleft">
						<html:select property="dictLanguageDegree">
	    				<html:option value="">
	    				��ѡ��
	    				</html:option>
	    				<html:option value="3">
	    					3��
	    				</html:option>
	    				<html:option value="4">
	    					4��
	    				</html:option>
	    		
	    			
	    				</html:select>
    			</td>
	    			</tr>

    		<tr>
    			<td colspan="4" bgcolor="#ffffff" align="center">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnadd" class="button" value="���" onclick="staffLanguageadd()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnupd"  class="button" value="�޸�" onclick="staffLanguageupdate()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btndel"  class="button" value="ɾ��" onclick="staffLanguagedel()"/>
    			</logic:equal>
    			
    				<input type="button" name="" value="�ر�"  class="button" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>
    		<html:hidden property="id"/>

    	</table>
    	</html:form>
  </body>
</html:html>
