<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>IVR�ڵ���Ϣ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
	type="text/css" />
	<script language="javascript" src="../../js/tools.js"></script>
	<script language="javascript">
	
	function killErrors() {
	return false;
	}
	
	//window.onerror = killErrors;
		function execModify()
		{
			//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();
			
			document.getElementById('operType').value="update";
			if(checkForm())
			{
    			document.getElementById("submitBtn").click();
    		}
    		//window.close();
    		//document.getElementById("paramTreeBeanForm").submit();
		}
		
		function execDelete()
		{
			//document.getElementById("treeForm").action = "./../department/deptTree.do?method=operParamTree&operType=delete";
    		//document.getElelemntById('operType').value="delete";
    		//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();
    		
    		document.getElementById('operType').value="delete";
    		document.getElementById("submitBtn").click();
    		
    		//window.close();
		}
		
		function execAdd()
		{
			///sys/department/deptTree.do?method=operParamTree&operType=insert
			
			//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();

			document.getElementById('operType').value="add";
			if(checkForm())
			{
				document.getElementById("submitBtn").click();
    			//document.getElementById("paramTreeBeanForm").submit();
    		}
    		//window.close();
		}
		function onSelectChange(objId)
		{
			
			var objSelect = document.getElementById(objId);
			var MtelKey = document.getElementById('MtelKey');
			var RtelKey = document.getElementById('RtelKey');
			var MlengthSize = document.getElementById('MlengthSize');
			
			var McheckValue = document.getElementById('McheckValue');
			
			var inputCNickName = document.getElementById('inputCNickName');
			var selectCNickName = document.getElementById('selectCNickName');
			var MnickName = document.getElementById('MnickName');
			
			//�������� voiceType �����ܽڵ�Ϊconftypeʱ�������Ͳ��ɼ�
			var MvoiceType = document.getElementById("MvoiceType");
			var RvoiceType = document.getElementById("RvoiceType");
			var CvoiceType = document.getElementById("CvoiceType");
			MvoiceType.style.display="inline";
			RvoiceType.style.display="block";
			var MexperterList = document.getElementById("MexperterList");
			var RexperterList = document.getElementById("RexperterList");
			MexperterList.style.display = "none";
			RexperterList.style.display = "none";
			
			//�㲥 ����/ȡ��
			var RorderProgramme = document.getElementById('RorderProgramme'); 
			var RcustomizeCancel = document.getElementById('RcustomizeCancel'); 
			RorderProgramme.style.display="none";
			RcustomizeCancel.style.display = "none";
			//�㵥����ʱ ¼��ͼƬ�ɼ�
			var imgRecord = document.getElementById("imgRecord");
			
			if(objSelect.value=='select')
			{
				MtelKey.style.display="inline";
				RtelKey.style.display="block";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='conference')
			{
				MtelKey.style.display="inline";
				RtelKey.style.display="block";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='conftype')
			{
				RexperterList.style.display = "block";
				MexperterList.style.display = "inline";
				MvoiceType.style.display="none";
				RvoiceType.style.display="none";
				MtelKey.style.display="inline";
				RtelKey.style.display="block";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='input')
			{
				MtelKey.style.display="inline";
				RtelKey.style.display="block";
				MlengthSize.style.display="inline";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="none";
				document.getElementById("RlengthSize").style.display="block";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="none";
				selectCNickName.style.display="inline";
				MnickName.style.display="inline";
			}
			else if(objSelect.value=='case')
			{
				MtelKey.style.display="none";
				RtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="inline";
				//document.getElementById("RtelKey").style.display="none";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="block";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='appraise')
			{
				MtelKey.style.display="none";
				RtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="inline";
				selectCNickName.style.display="none";
				MnickName.style.display="inline";
			}
			else if(objSelect.value=='wait')
			{
				MtelKey.style.display="none";
				RtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="inline";
				selectCNickName.style.display="none";
				MnickName.style.display="inline";
			}
			else if(objSelect.value=='filesplay')
			{
				RorderProgramme.style.display="block";
				RcustomizeCancel.style.display = "block";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				RtelKey.style.display="block";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else
			{
				//MtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				RtelKey.style.display="block";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			
			
			//����¼��ͼƬ��ʾ����
			if('block'==RvoiceType.style.display)
			{
				if(CvoiceType.value=='onlyfile')
				{
					imgRecord.style.display="inline";
				}
			}	
			else
			{
				imgRecord.style.display="none";
			}
			var cnickNamej = document.getElementById('Cnickname');
			if(inputCNickName.value==0)
			{
				inputCNickName.value=cnickNamej.value;
			}
			if(selectCNickName.value==0)
			{
				selectCNickName.value=cnickNamej.value;	
			}
		}
		function onLoadSelect(objId)
		{
			var objSelect = document.getElementById(objId);
			var MtelKey = document.getElementById('MtelKey');
			var RtelKey = document.getElementById('RtelKey');
			
			var MlengthSize = document.getElementById('MlengthSize');
			
			var McheckValue = document.getElementById('McheckValue');
			
			var inputCNickName = document.getElementById('inputCNickName');
			var selectCNickName = document.getElementById('selectCNickName');
			var MnickName = document.getElementById('MnickName');
			var Cnickname = document.getElementById("Cnickname");
			
			if(objSelect.value=='select')
			{
				MtelKey.style.display="inline";
				document.getElementById("RtelKey").style.display="block";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='input')
			{
				MtelKey.style.display="inline";
				MlengthSize.style.display="inline";
				document.getElementById("RlengthSize").style.display="block"
				McheckValue.style.display="none";
				
				inputCNickName.style.display="none";
				selectCNickName.style.display="inline";
				MnickName.style.display="inline";
			}
			else if(objSelect.value=='case')
			{
				MtelKey.style.display="none";
				RtelKey.style.display = "block";
				MlengthSize.style.display="none";
				McheckValue.style.display="inline";
				//document.getElementById("RcheckValue").style.display="block"
				
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}
			else if(objSelect.value=='appraise')
			{
				MtelKey.style.display="none";
				RtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="inline";
				selectCNickName.style.display="none";
				MnickName.style.display="inline";
			}
			else if(objSelect.value=='wait')
			{
				MtelKey.style.display="none";
				RtelKey.style.display="none";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				//document.getElementById("RtelKey").style.display="block";
				document.getElementById("RlengthSize").style.display="none";
				document.getElementById("RcheckValue").style.display="none";
				inputCNickName.style.display="inline";
				selectCNickName.style.display="none";
				MnickName.style.display="inline";
			}
			else
			{
				MtelKey.style.display="inline";
				MlengthSize.style.display="none";
				McheckValue.style.display="none";
				RtelKey.style.display = "block";
				inputCNickName.style.display="block";
				selectCNickName.style.display="none";
				MnickName.style.display="none";
			}		
			//inputCNickName.value=Cnickname.value;
			//selectCNickName.value=Cnickname.value;	
			//alert(inputCNickName.value+":"+selectCNickName.value);
		}
		function changeVoiceType()
		{
			//�������� voiceType �����ܽڵ�Ϊconftypeʱ�������Ͳ��ɼ�
			var MvoiceType = document.getElementById("MvoiceType");
			var RvoiceType = document.getElementById("RvoiceType");
			var CvoiceType = document.getElementById("CvoiceType");
			//�㵥����ʱ ¼��ͼƬ�ɼ�
			var imgRecord = document.getElementById("imgRecord");
			//����¼��ͼƬ��ʾ����
			if(CvoiceType.value=='onlyfile')
			{
				imgRecord.style.display="inline";
			}	
			else
			{
				imgRecord.style.display="none";
			}
		
		}
		function checkForm()
		{
			return true;
			//��ѡ��
			
			var SCfuncType=document.getElementById('Cfunctype');
			var Ccontent=document.getElementById('Ccontent');
			var SCvoiceType=document.getElementById('CvoiceType');
			
			var SCexperterList = document.getElementById("CexperterList");
			if(SCfuncType.value=='')
			{
				alert("��ѡ��������");
				return false;
			}
			if(Ccontent)
			{
				if(Ccontent.value=='')
				{
					alert("����д�ڵ���");
					return false;
				}
			}
			if(SCvoiceType.value=='')
			{
				if(!SCfuncType.value=='conftype')
				alert("��ѡ�����������");
				return false;
			}
			if(SCfuncType.value=='conftype')
			{
				if(SCexperterList.value=='')
				{
	
					alert("��ѡ��ר���б�");
					return false;
				}
			}
			//��ѡ��
			var MtelKey = document.getElementById('MtelKey');
			var CtelKey = document.getElementById('CtelKey');
			if(MtelKey.style.display=="block"||MtelKey.style.display=="inline")
			{
				if(CtelKey.value=='')
				{
					alert("����д�绰����");
					return false;
				}
			}
			var MlengthSize = document.getElementById('MlengthSize');
			var ClengthSize = document.getElementById('ClengthSize');
			if(MlengthSize.style.display=="block"||MlengthSize.style.display=="inline")
			{
				if(ClengthSize.value=='')
				{
					alert("��ѡ��д֤λ��");
					return false;
				}
			}
	
			var McheckValue = document.getElementById('McheckValue');
			var CcheckValue = document.getElementById('CcheckValue');
			if(McheckValue.style.display=="block"||McheckValue.style.display=="inline")
			{
				if(CcheckValue.value=='')
				{
					alert("��ѡ����֤���");
					return false;
				}
			}
			var inputCNickName = document.getElementById('inputCNickName');
			var selectCNickName = document.getElementById('selectCNickName');
			var MnickName = document.getElementById('MnickName');
			var Cnickname = document.getElementById("Cnickname");
			if(MnickName.style.display=="block"||MnickName.style.display=="inline")
			{
				if(inputCNickName.value==''&&selectCNickName.value=='')
				{
					alert("����д����");
					return false;
				}
				if(inputCNickName.style.display=="block"||inputCNickName.style.display=="inline")
				{
					//Cnickname.value = selectCNickName.value;
					//alert(111+''+inputCNickName.value);
					Cnickname.value = inputCNickName.value;
				}
				else 
				{
				//alert(222+''+selectCNickName.value);
					Cnickname.value = selectCNickName.value;
				}
				//alert(333);
			}
			
			return true;
		}
	</script>
  </head>
  
  <body class="loadBody" onload="onSelectChange('Cfunctype')"><br>
    <html:form styleId="paramTreeBeanForm" action="/sys/ivr/paramTree.do?method=operParamTree">
   <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
  
  <tr>
    <td  class="labelStyle">��������<br></td>
    <td>
		<span class="selectStyle">��<font color="red">*</font>��Ϊ����(base_tree)�ֶ�</span>
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="labelStyle">����ID(*)<br></td>
    <td>
		<html:text property="id" styleClass="writeTextStyle" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="labelStyle">������(*)<br></td>
    <td>
		<html:text property="label" styleClass="writeTextStyle"/>
		
	<br></td>
  </tr>
   <tr>
    <td  class="labelStyle">����(*)<br></td>
    <td>
		<html:text property="nickName" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
  <tr>
    <td  class="labelStyle">�Ƿ�Ϊ���ڵ�(*)<br></td>
    <td>
		<html:select property="isRoot" styleClass="selectStyle">
        	<html:option value="0">�ӽڵ�</html:option>
        	<html:option value="1">���ڵ�</html:option>
        </html:select>	
	<br></td>
  </tr>
    <tr>
    <td  class="labelStyle">�ڵ�����(*)<br></td>
    <td>
		<html:text property="type" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
   <tr>
    <td  class="labelStyle">�ϼ�����(*)<br></td>
    <td>
        <html:text property="parentName" readonly="true" styleClass="writeTextStyle"/>
		<html:hidden property="parentId" />
	<br>
<%--	<input type="hidden" name="method" value="operParamTree" />--%>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="labelStyle">�Ƿ񵯳�<br></td>
    <td>
        <html:text property="target" styleClass="writeTextStyle"/>
<%--		<html:select property="target">--%>
<%--        	<html:option value="operationframeTree">������</html:option>--%>
<%--        	<html:option value="blank">����</html:option>--%>
<%--        </html:select>--%>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">����<br></td>
    <td>
        <html:text property="action" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
   <tr>
    <td  class="labelStyle">�ڵ�ͼ��<br></td>
    <td >
        <html:text property="icon"styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">����ʱ��(*)<br></td>
    <td>
		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="labelStyle">�޸�ʱ��(*)<br></td>
    <td>
		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="labelStyle">��ע(*)<br></td>
    <td>
		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
   <tr>
   <td colspan="2">
    <!-- beginCcIvrTreeInfo -->
   <tr>
    <td  class="labelStyle">CcIvrTreeInfo�ֶ�<br></td>
    <td>
		
	<br></td>
  </tr>

  <tr>
    <td  class="labelStyle">��������<br></td>
    <td>
<%--		<html:text property="Cfunctype" styleClass="input"/>--%>
		<html:select property="Cfunctype" styleId="Cfunctype" styleClass="selectStyle" onchange="onSelectChange('Cfunctype')">
    	<html:option value="">��ѡ��</html:option>
    	<logic:equal value="onlyCase" name="pFuncType">
    	<html:option value="case">��ѯ��ǩ</html:option>
    	</logic:equal>
    	<logic:equal value="conference" name="pFuncType">
    	<html:option value="conftype">��������</html:option>
    	</logic:equal>
    	<logic:equal value="common" name="pFuncType">
    	<html:option value="manwork">ת�˹�</html:option>
    	<html:option value="filesplay">����������</html:option>
    	<html:option value="select">����ѡ��</html:option>
    	<html:option value="conference">�绰����</html:option>
<%--    	<html:option value="conftype">��������</html:option>--%>
<%--ע�͵��õ�ʱ����ע���� --%>
<%--    	<html:option value="input">����ɼ�</html:option>--%>
<%--    	<html:option value="query">��ѯ���ݿ�</html:option>--%>
<%--    	<html:option value="appraise">�˹�����</html:option>--%>
<%--    	<html:option value="wait">�ȴ�</html:option>--%>
    	</logic:equal>
		</html:select>	
		<span><font color="#ff0000">*</font></span>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">����<br></td>
    <td>
		<html:hidden property="Cnickname" styleId="Cnickname" styleClass="input" />
		<input type="text" id="inputCNickName" class="writeTextStyle"/>
		<select id="selectCNickName" class="selectStyle">
			<option value="policeId">��Ա��</option>
			<option value="policePassword">����</option>
		</select>
		<span id="MnickName" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
    <tr>
    <td  class="labelStyle">�ڵ�����<br></td>
    <td>
		<html:text property="Ccontent" styleClass="writeTextStyle" styleId="Ccontent"/>
		<span><font color="#ff0000">*</font></span>
	</td>
  </tr>
    <tr id="RvoiceType" style="display:block;">
    <td  class="labelStyle">��������<br></td>
    <td>
		<html:select property="CvoiceType"  styleId="CvoiceType" styleClass="selectStyle" onchange="changeVoiceType()">
    	<html:option value="">��ѡ��</html:option>
    	<html:option value="none">������</html:option>
    	<html:option value="onlyfile">������</html:option>
    	<html:option value="allfiles">������</html:option>
		</html:select>
		<span id="MvoiceType"><font color="#ff0000">*</font></span>
	</td>
  </tr>

  <tr id="RorderProgramme" style="display:none;">
    <td  class="labelStyle">�㲥<br></td>
    <td>
            <html:checkbox property="CorderProgramme" ></html:checkbox><span class="selectStyle">�㲥�밴3</span>
	</td>
  </tr>

  <tr id="RcustomizeCancel" style="display:none;">
    <td  class="labelStyle">����/ȡ��<br></td>
    <td>
    <html:checkbox property="CcustomizeCancel"></html:checkbox><span class="selectStyle">�����밴4/ȡ���밴5</span>
	</td>
  </tr>
  <tr id="RexperterList" style="display:block;">
    <td  class="labelStyle">ר�����<br></td>
    <td>
		<html:select property="CexpertId"  styleId="CexperterList" styleClass="selectStyle">
    	<html:option value="">��ѡ��</html:option>
		<html:options collection="experterList"
							property="value"
							labelProperty="label"/>
		</html:select>
		<span id="MexperterList"><font color="#ff0000">*</font></span>
	</td>
  </tr>
    
  <tr style="display:none;">
    <td  class="labelStyle">�绰���� <br></td>
    <td>
		<html:text property="CtelNum" styleClass="writeTextStyle"/>
	</td>
  </tr>
  <logic:equal value="case" name="isCase">
   <tr id="RtelKey" style="display:none">
  </logic:equal>
  <logic:notEqual value="case" name="isCase">
    <tr id="RtelKey">
  
    <td  class="labelStyle">ѡ��绰���� <br></td>
    <td>
		<html:text property="CtelKey"  styleClass="writeTextStyle" styleId="CtelKey" style="display:inline"/>
		<span id="MtelKey" style="display:inline"><font color="#ff0000">*</font></span>
	</td>
  </tr>
  </logic:notEqual>
      <tr id="RlengthSize" style="display:none">
    <td  class="labelStyle">��֤λ��<br></td>
    <td>
		<html:text property="ClengthSize"  styleClass="writeTextStyle"/>
		<span id="MlengthSize" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
  
   <tr id="RcheckValue" style="display:none">
    <td  class="labelStyle">��֤��� <br></td>
    <td>
		<html:select property="CcheckValue" styleClass="selectStyle">
		<html:option value="">��ѡ��</html:option>
    	<html:option value="true">��ȷ</html:option>
    	<html:option value="false">����</html:option>
		</html:select>
		<span id="McheckValue" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
   
   
  <tr>
    
    <td  class="buttonAreaStyle" colspan="2">
      <input type="submit" id="submitBtn"   style="display:none;"/>
      <img style="display:none" id="imgRecord" alt="¼����Ϣ����" src="../../style/<%=styleLocation %>/images/addReport.gif"
      onclick="popUp('windowsPersonWork','./.././ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoMain&addTreeInfoId=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <logic:notPresent name="opertype">
<%--      <img alt="����CcIvrTreeInfo" src="../../images/sysoper/particular.gif" onclick="popUp('windowsPersonWork','./../ccIvrTreeinfo.do?method=toCcIvrTreeinfoLoad&type=insert&treeId==<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>--%>
      <img alt="������Ϣ����" src="../../style/<%=styleLocation %>/images/addReport.gif"
      onclick="popUp('windowsPersonWork','./.././ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoMain&addTreeInfoId=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="����" src="../../style/<%=styleLocation %>/images/add.gif" 
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />&pFuncType=<bean:write name="paramTreeBean" property="Cfunctype" />',650,550)" width="16" height="16" border="0"/>
      <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif" 
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
<%--	  <img alt="ɾ��" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/departmentTreeDelete.jsp?id=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
	  </logic:notPresent>
	 <logic:equal name="opertype" value="update">
		     <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif"
		     onclick="execModify()" width="16" height="16" border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="delete">
		     <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"
		     onclick="execDelete()" width="16" height="16"  border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="add">
		     <img alt="����" src="../../style/<%=styleLocation %>/images/add.gif" 
		     onclick="execAdd()" width="16" height="16"  border="0"/>
	 </logic:equal>
	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>