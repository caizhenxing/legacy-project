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
    
    <title></title>

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

 <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>
   <SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>

<%
	String id = request.getAttribute("id").toString();
 %>
	<script type="text/javascript">
			
			
			function checkForm(addstaffer){

			if (!checkNotNull(addstaffer.companyName,"��λ����")) return false;
        	if (!checkNotNull(addstaffer.BStaffName,"ְ������")) return false;
        	if (!checkNotNull(addstaffer.BStaffNickname,"ְ���ǳ�")) 
        	{
        		return false;
        	}
        	else
        	{
        		var regu = "^[a-zA-Z\_]+$"; 
				var re = new RegExp(regu); 
				if (re.test(addstaffer.BStaffNickname.value)) { 
				return true; 
				}else{ 
				alert("ֻ��������Ӣ��");
				return false; 
				} 
        	}

	   
           return true;
        }
        
        
<%--   ��Ӳ���     --%>
				function staffBasicAdd()
				{
				    var f =document.forms[0];
    	    		if(checkForm(f)){
			 		document.forms[0].action="../staffBasic.do?method=toStaffBasicOper&type=insert";
			 		
			 		document.forms[0].submit();
			 		}
				}
				
<%--   �޸Ĳ���     --%>
				function staffBasicUpdate()
				{
				var f =document.forms[0];
    	    		if(checkForm(f)){
				 		document.forms[0].action="../staffBasic.do?method=toStaffBasicOper&type=update";
				 	
				 		document.forms[0].submit();
			 		}
				}
<%--   ɾ������     --%>
				function staffBasicDel()
				{
			 		document.forms[0].action="../staffBasic.do?method=toStaffBasicOper&type=delete";
			 		
			 		document.forms[0].submit();
				}
				
				
				
<%--  ˢ���¿��ҳ    ��ʾ��ְ������ҳ --%>
				function staffHortation()
				{
					document.forms[0].action="../staffHortation.do?method=toStaffHortationList&SBid=<%=id%>";
			 		document.forms[0].target="bottomm";
			 		document.forms[0].submit();
				}
<%--  ˢ���¿��ҳ    ��ʾ��ְ������ҳ --%>		
				function staffExperience()
				{
					document.forms[0].action="../staffExperience.do?method=toStaffExperienceList&SBid=<%=id%>";
			 		document.forms[0].target="bottomm";
			 		document.forms[0].submit();
					
				}
<%--  ˢ���¿��ҳ    ��ʾ��ְ������ҳ --%>			
				function staffLanguag()
				{
					document.forms[0].action="../staffLanguage.do?method=toStaffLanguageList&SBid=<%=id%>";
			 		document.forms[0].target="bottomm";
			 		document.forms[0].submit();
				}
<%--  ˢ���¿��ҳ    ��ʾ��ְ������ҳ --%>			
				function staffParentInfo()
				{
					document.forms[0].action="../staffParent.do?method=toStaffParentInfoList&SBid=<%=id%>";
			 		document.forms[0].target="bottomm";
			 		document.forms[0].submit();	
				}



				
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
		function showHide(className)
		{
			var classArr = document.getElementsByClassName(className);
			var oDiv = document.getElementById(className+'T');
			for(var i=0; i<classArr.length; i++)
			{
				var eShow = classArr[i].style.display;
				
				if(eShow)
				{
				}
				else
				{
					eShow = "block";
				}
				if('block'==eShow)
				{
					classArr[i].style.display = "none";
				}
				else
				{
					classArr[i].style.display = "block";
				}
			}
			if(oDiv)
			{
				var Text = oDiv.innerText;
				var subStr = Text.substring(1);
				var first = Text.substring(0,1);
				if("-"==first)
				{
					oDiv.innerText = "+"+subStr;
				}
				else if("+"==first)
				{
					oDiv.innerText = "-"+subStr;
				}
			}
		}
		document.getElementsByClassName = function(clsName){    var retVal = new Array();    var elements = document.getElementsByTagName("*");    for(var i = 0;i < elements.length;i++){        if(elements[i].className.indexOf(" ") >= 0){            var classes = elements[i].className.split(" ");            for(var j = 0;j < classes.length;j++){                if(classes[j] == clsName)                    retVal.push(elements[i]);            }        }        else if(elements[i].className == clsName)            retVal.push(elements[i]);    }    return retVal;}
			</script>
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�"); window.close();
	
	</script>
	</logic:notEmpty>
	
	
	
	<html:form action="/sys/staff/staffBasic" method="post">
<%--	 <html:hidden property="id"/>--%>
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
    			<td class="tdbgcolorloadleft" colspan="7">
    			 	<font color="#ff0000"><b>ְ��������Ϣ</b></font>
    			</td>
    		</tr>
    		
    		<tr>
			   	<td class="tdbgcolorloadright">
			   		��λ����
			    </td>
			    <td class="tdbgcolorloadleft">
<%--			    	����׿Խ�Ƽ����޹�˾--%>
			    	<html:select property="companyName">
			    	<html:option value="">
				      			��ѡ��
				    </html:option>
				    <html:options collection="unitWorkList"
				  							property="value"
				  							labelProperty="label"/>
			    	</html:select><font color="#ff0000">*</font>
			    </td>
			     <td class="tdbgcolorloadright">
			    	ְ�����
			    </td>
			    <td class="tdbgcolorloadleft">
			    <html:text property="BStaffNum" styleClass="input"/>
			    </td>
			    <td class="tdbgcolorloadright" rowspan="5">
			    	ְ����Ƭ
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2" rowspan="5">
			    	ssssssssss
			    </td>
			  </tr>
    		
			  <tr>
			    <td class="tdbgcolorloadright">
			    	ְ������
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BStaffName" styleClass="input"/><font color="#ff0000">*</font>
			    </td>
			     <td class="tdbgcolorloadright">
			    	ְ���ǳ�
			    </td>
			    <td class="tdbgcolorloadleft">
			    <html:text property="BStaffNickname" styleClass="input"/><font color="#ff0000">*</font>
			    </td>
			  </tr>
			  	  <tr>
			    <td class="tdbgcolorloadright">
			    	��&nbsp;&nbsp;&nbsp;&nbsp;��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BDictCountry" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	ʡ&nbsp;&nbsp;&nbsp;&nbsp;��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BDictProvince" styleClass="input"/>
			    </td>
			  </tr>
			  	  <tr>
			    <td class="tdbgcolorloadright">
			    	��&nbsp;&nbsp;&nbsp;&nbsp;��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BDictCity" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BDictDistrict" styleClass="input"/>
			    </td>
			  </tr>
			   <tr>
			    <td class="tdbgcolorloadright">
			    	��&nbsp;&nbsp;&nbsp;&nbsp;��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:select property="BStaffSex">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="1">
			    			��
			    		</html:option>
			    		<html:option value="2">
			    			Ů
			    		</html:option>
			    	</html:select>
			    </td>
			    <td class="tdbgcolorloadright">
			    	��&nbsp;&nbsp;&nbsp;&nbsp;��
			    </td>
			    <td class="tdbgcolorloadleft">
			    <html:text property="BNation" styleClass="input"/>
			    </td>
			  </tr>
			  <tr>
			    <td class="tdbgcolorloadright">
			    	��������
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="BBirthday" styleClass="input" onfocus="calendar()"/>
			    </td>
			    <td class="tdbgcolorloadright">
			    	���ѧ��
			    </td>
			   	<td class="tdbgcolorloadleft">
			    	<html:text property="BCtSchoolAge" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	�������ڵ�
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="BNationAt" styleClass="input" size="25"/>
			    </td>
			  </tr>
			 <tr>
			     <td class="tdbgcolorloadright">
			    	������ò
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:select property="BDictPolity">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="PartyMembers">
			    			��Ա
			    		</html:option>
			    		<html:option value="Members">
			    			��Ա
			    		</html:option>
			    	</html:select>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	֤������
			    </td>
			   <td class="tdbgcolorloadleft">
			    	<html:select property="BDictPaperType">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="IdCards">
			    			�������֤
			    		</html:option>
			    		<html:option value="Soldiers">
			    			����֤
			    		</html:option>
			    	</html:select>
			    </td>
			    <td class="tdbgcolorloadright">
			    	֤������
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="BPaperNum" styleClass="input" size="25"/>
			    </td>
			  </tr>
			  
			    <tr>
			    	<td class="tdbgcolorloadright">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
			    	<td class="tdbgcolorloadleft">
<%--			    	<html:text property="BDictDepartment" styleClass="input"/>--%>
			    	<html:select property="BDictDepartment">
			    	<html:option value="">
				      			��ѡ��
				    </html:option>
				    <html:options collection="departmentList"
				  							property="value"
				  							labelProperty="label"/>
				  	</html:select>
					</td>
					<td class="tdbgcolorloadright">
			    	ְ&nbsp;&nbsp;&nbsp;&nbsp;λ
			    </td>
			     <td class="tdbgcolorloadleft">
				<html:text property="BDictDuty" styleClass="input"/>
			    </td>
			    	<td class="tdbgcolorloadright">
			  		ְ���ȼ�
			  	</td>
			  	<td class="tdbgcolorloadleft">
			  		<html:text property="BGrade" styleClass="input"/>
			  	</td>
			    </tr>
			    <tr>
			    <td class="tdbgcolorloadright">
			    	�������
			    </td>
			    <td class="tdbgcolorloadleft" colspan="6">
			    	<html:select property="BHealthState">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="health">
			    			����
			    		</html:option>
			    		<html:option value="general">
			    			һ��
			    		</html:option>
			    	</html:select>
			    </td>
			
			  </tr>
			    <tr>
			  	<td class="tdbgcolorloadleft" colspan="7" style="cursor:hand" onclick="showHide('lianxi')">
			  		<font color="#ff0000"><B><div id="lianxiT">+��ϵ��ʽ</div></B></font>
			  	</td>
			  </tr>
			   <tr class="lianxi" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	�ֻ�����
			    </td>
			    <td class="tdbgcolorloadleft" colspan="3">
			    	<html:text property="linkMobileNum" styleClass="input" size="35"/>
			    </td>
			   
			   
			    <td class="tdbgcolorloadright">
			    	סլ�绰
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="linkHomeNum" styleClass="input" size="25"/>
			    </td>		  
			  </tr>
			  <tr class="lianxi" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	QQ��
			    </td>
			    <td class="tdbgcolorloadleft">
			    <html:text property="linkQq" styleClass="input"/>
			    </td>
			    <td class="tdbgcolorloadright">
			    	MSN
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="linkMsn" styleClass="input" />
			    </td>
			    <td class="tdbgcolorloadright">
			    	�ֻ���
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="linkExtNum" styleClass="input"/>
			    </td>
			  </tr>
			  <tr class="lianxi" style="display:none;">
			  	 <td class="tdbgcolorloadright">
			  		������ҳ
			  	</td>
			  	 <td class="tdbgcolorloadleft" colspan="6">
			  		<html:text property="linkHomepage" styleClass="input" size="60"/>
			  	</td>
			 </tr>
			 
			 
			 <tr>
			  	<td class="tdbgcolorloadleft" colspan="7" style="cursor:hand;" onclick="showHide('tongxun')">
			  		<font color="#ff0000"><B><div id="tongxunT">+ͨѶ��ʽ</div></B></font>
			  	</td>
			  </tr>
			   <tr class="tongxun" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	�������
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="CFaxNum" styleClass="input"/>
			    </td>
			    <td class="tdbgcolorloadright">
			    	��������
			    </td>
			    <td class="tdbgcolorloadleft" colspan="3">
			     <html:text property="CPostalcode" styleClass="input"/>
			    </td>
			  </tr>
			   <tr class="tongxun" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	��ϸ��ַ
			    </td>
			    <td class="tdbgcolorloadleft" colspan="6">
			    	<html:text property="CAddress" styleClass="input" size="80"/>
			    </td>
			  </tr>
			  
			  <tr>
			  	<td class="tdbgcolorloadleft" colspan="7" style="cursor:hand;" onclick="showHide('qita')">
			  		<font color="#ff0000"><B><div id="qitaT">+������Ϣ</div></B></font>
			  	</td>
			  </tr>
			  <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	�Ƿ���ְ
			    </td>
			    <td class="tdbgcolorloadleft">
			    <html:select property="dictIsBeginwork">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="yes">
			    			��ְ
			    		</html:option>
			    		<html:option value="no">
			    			����ְ
			    		</html:option>
			    	</html:select>
			    </td>
			    
		    	<td class="tdbgcolorloadright">
		    		�����ʸ�����
		    	</td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="AStudyTitle" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	����ְ��
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="ADictTechniclName" styleClass="input"/>
			    </td>
			  </tr>
			   <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	��������
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="AApproveOrgan" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	��ְʱ��
			    </td>
			    <td class="tdbgcolorloadleft">
			    	<html:text property="AEnterTime" styleClass="input" onfocus="calendar()"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	��ְʱ��
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="AOutTime" styleClass="input" onfocus="calendar()"/>
			    </td>
			  </tr>
			  <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	��ְԭ��
			    </td>
			    <td class="tdbgcolorloadleft" colspan="6">
			    	<html:textarea property="AOutWhy" rows="5" cols="80"/>
			    </td>
			  </tr>
			  <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	�ù���ʽ
			    </td>
			    <td class="tdbgcolorloadleft" colspan="2">
			    	<html:text property="ADictUseWorkState" styleClass="input"/>
			    </td>
			    
			    <td class="tdbgcolorloadright">
			    	���
			    </td>
			    <td class="tdbgcolorloadleft" colspan="3">
			    	 <html:select property="BDictIsMarry">
			    		<html:option value="">
			    		��ѡ��
			    		</html:option>
			    		<html:option value="yes">
			    			�ѻ�
			    		</html:option>
			    		<html:option value="no">
			    			δ��
			    		</html:option>
			    	</html:select>
			    </td>
			  </tr>
			  <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	�������
			    </td>
			    <td class="tdbgcolorloadleft" colspan="6">
			    	<html:textarea property="BMarriage" rows="5" cols="80"/>
			    </td>
			  </tr>
			  <tr class="qita" style="display:none;">
			    <td class="tdbgcolorloadright">
			    	��Ȥ����
			    </td>
			    <td class="tdbgcolorloadleft" colspan="6">
			    	<html:textarea property="BInterest" rows="5" cols="80"/>
			    </td>

			  </tr>
			  <tr>
    			<td colspan="7" bgcolor="#ffffff" align="center">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnadd" class="button" value="���" onclick="staffBasicAdd()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnupd"  class="button" value="�޸�" onclick="staffBasicUpdate()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btndel"  class="button" value="ɾ��" onclick="staffBasicDel()"/>
    			</logic:equal>
    			
    				<input type="button" name="" value="�ر�"  class="button" onClick="javascript:window.parent.close();"/>
    			
    			</td>
    		</tr>
    		<html:hidden property="staffId"/>
			 </table>
			 <logic:equal name="opertype" value="update">
			 <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
			 	<tr>
			 	
			 	<td>
			 		<input type="button" name="" value="ְ������" class="button" onclick="staffHortation()"/>&nbsp;
			 		<input type="button" name="" value="ְ������" class="button" onclick="staffExperience()"/>&nbsp;
			 		<input type="button" name="" value="��������" class="button" onclick="staffLanguag()"/>&nbsp;
			 		<input type="button" name="" value="ְ��������ϵ" class="button" onclick="staffParentInfo()"/>&nbsp;
			 	</td>
			 	
			 	</tr>
			 </table>
			 </logic:equal>
		</html:form>
  </body>
</html:html>
