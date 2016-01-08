<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>������ϸ����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="../../js/calendar.js" type=text/javascript>
</SCRIPT>
<script language="javascript" src="../../js/common.js"></script>
<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
<script language="javascript" src="../../js/clock.js"></script>
<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>

<script type="text/javascript">
	
			
			
		function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.priceRid,"������")) return false;
        	
        	if (!checkNotNull(addstaffer.custName,"�û�����")) return false;
           return true;
        }
				function add()
				{
				    var f =document.forms[0];
				    var treeid=document.getElementById("treeId").value;
				    if(treeid==''){
				    	alert('��ѡ����Ŀ�ڵ�!');
				    	return false;
				    }
				    //����֤
    	    		if(true){
    	    		var voicePathObj = document.getElementById("voicePath");
    	    		var selectFileObj = document.getElementById("selectFile");
    	    		voicePathObj.value = selectFileObj.value;
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=insert";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				function update()
				{
					var voicePathObj = document.getElementById("voicePath");
    	    		var selectFileObj = document.getElementById("selectFile");
    	    		if(selectFileObj.value!=0)
    	    		{
    	    				voicePathObj.value = selectFileObj.value;
    	    		}
    				var createTypeObj = document.getElementById("createType").value;
    				if(createTypeObj=="tts"){
    					var remarkObj = document.getElementById("remark").value;
    					if(remarkObj==''){
    						alert("���ڱ�ע����дҪ���ɵ���Ƶ�ļ����ݣ�");
    						return;
    					}
    				}
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=update";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
				}
				function del()
				{
					var delflag=confirm('�Ƿ�ȷ��ɾ����');
					if(true==delflag){
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=delete";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
			//pener.parent.topp.document.all.btnSearch.click();
<%--			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;--%>
		
		}
		function modifyVoicePath()
		{
			document.getElementById('selectFile').style.display='inline';
		}
		function onloadDoWithVoicePath()
		{
			var opertype =  '<bean:write name="opertype" />';
			if('update'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="inline";
			}
			else if('insert'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="inline";
				voicePath.style.display="none";
				modifyVoicePathHref.style.display="none";
			}
			else
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="none";
			}
		}
			</script>

  </head>
  
  <body  class="loadBody" onload="onloadDoWithVoicePath()">
  
  <logic:notEmpty name="operSign">
	<script>
	alert("�����ɹ�");toback(); window.close();
	
	</script>
	</logic:notEmpty>
	
  <html:form action="/sys/ccIvrTreevoiceDetail.do" method="post">
  
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		   <td class="navigateStyle">
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
  
    	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
    			<tr style="display=none;">
	    			<td class="labelStyle">
	    				id         
	    			</td>
	    			<td class="valueStyle">
						<html:text property="id"  styleClass="writeTextStyle" readonly="true"/>
	    			</td>
	    		 </tr>
	    		 
	    		 <tr>
	    			<td class="labelStyle">
	    				����·��        
	    			</td>
	    			<td class="valueStyle" colspan="3">
	    				<input type="file"  id="selectFile" name="upfile1"  size="21"   class="input" style="display:none" />   
						<html:text property="voicePath" styleId="voicePath" size="24"  styleClass="writeTextStyle" style="display:inline" /><a id="modifyVoicePathHref" href="javascript:modifyVoicePath()">�޸�</a>
	    			</td>
	    		</tr>
	    		
<%--	    	<tr>--%>
<%--	    		<td class="labelStyle">--%>
<%--	    				�Ƿ�ʹ��        --%>
<%--	    			</td>--%>
<%--	    			<td class="valueStyle">--%>
<%--	    			<html:select property="isUse" styleClass="selectStyle">--%>
<%--						<html:option value="1">ʹ��</html:option>--%>
<%--						<html:option value="0">��ʹ��</html:option>--%>
<%--					</html:select>--%>
<%--	    			</td>--%>
<%--	    			 </tr>--%>
<%--	    			  <tr>--%>
<%--	    			<td class="labelStyle">--%>
<%--	    				����         --%>
<%--	    			</td>--%>
<%--	    			<td class="valueStyle" colspan="3">--%>
<%--						<html:text property="layerOrder" styleClass="writeTextStyle"/>--%>
<%--	    			</td>--%>
<%--	    		</tr>--%>
	    		 <tr style="display:none">
	    			<td class="labelStyle">
	    				��������         
	    			</td>
	    			<td class="valueStyle" colspan="3">
						<html:text property="languageType" styleClass="writeTextStyle"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="labelStyle">
	    				��Ŀ�ڵ�        
	    			</td>
	    			<td class="valueStyle" colspan="3">
<%--						<html:text property="treeId" styleClass="input"/>--%>
	    				<html:select property="treeId" styleClass="selectStyle">
		    				<html:option value="">��ѡ��</html:option>
				    		<html:options collection="IVRLVList"
				  							property="value"
				  							labelProperty="label"/>
				    	</html:select>	
	    			</td>
	    			 </tr>
	    			 <logic:notEqual name="opertype" value="insert">
	    			 <tr>
	    			<td class="labelStyle">
	    				������������        
	    			</td>
	    			<td class="valueStyle" colspan="3">
	    				<html:select property="createType" styleClass="selectStyle">
		    			<html:option value="file">ѡ�������ļ�</html:option>
		    			<html:option value="tts">TTS����</html:option>
				    	</html:select>	
	    			</td>
	    			 </tr>
	    			 </logic:notEqual>
	    		 <tr>
	    			<td class="labelStyle">
	    				��&nbsp;&nbsp;&nbsp;&nbsp;ע       
	    			</td>
	    			<td class="valueStyle">
						<html:textarea rows="5" cols="85" property="remark" styleClass="writeTextStyle"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    		</tr>
	    		
	    		
    		<tr>
    			<td colspan="6" bgcolor="#ffffff" align="center" class="buttonAreaStyle">
    			<logic:equal name="opertype" value="insert">
    				<input type="button" name="btnAdd" class="buttonStyle" value="���" onclick="add()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="update">
    				<input type="button" name="btnUpdate"  class="buttonStyle" value="�޸�" onclick="update()"/>
    			</logic:equal>
    			<logic:equal name="opertype" value="delete">
    				<input type="button" name="btnDelete"  class="buttonStyle" value="ɾ��" onclick="del()"/>
    			</logic:equal>
    				<input type="submit" id="btnSubmit" value="�ύ" style="display:none;" />
    				<input type="button" name="" value="�ر�"  class="buttonStyle" onClick="javascript:window.close();"/>
    			
    			</td>
    		</tr>
			<html:hidden property="id"/>
    	</table>
    	</html:form>
  </body>
</html:html>
