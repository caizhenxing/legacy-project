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
    
    <title>IVR节点信息</title>

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
			
			//语音类型 voiceType 当功能节点为conftype时语音类型不可见
			var MvoiceType = document.getElementById("MvoiceType");
			var RvoiceType = document.getElementById("RvoiceType");
			var CvoiceType = document.getElementById("CvoiceType");
			MvoiceType.style.display="inline";
			RvoiceType.style.display="block";
			var MexperterList = document.getElementById("MexperterList");
			var RexperterList = document.getElementById("RexperterList");
			MexperterList.style.display = "none";
			RexperterList.style.display = "none";
			
			//点播 订制/取消
			var RorderProgramme = document.getElementById('RorderProgramme'); 
			var RcustomizeCancel = document.getElementById('RcustomizeCancel'); 
			RorderProgramme.style.display="none";
			RcustomizeCancel.style.display = "none";
			//点单语音时 录音图片可见
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
			
			
			//处理录音图片显示隐常
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
			//语音类型 voiceType 当功能节点为conftype时语音类型不可见
			var MvoiceType = document.getElementById("MvoiceType");
			var RvoiceType = document.getElementById("RvoiceType");
			var CvoiceType = document.getElementById("CvoiceType");
			//点单语音时 录音图片可见
			var imgRecord = document.getElementById("imgRecord");
			//处理录音图片显示隐常
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
			//必选的
			
			var SCfuncType=document.getElementById('Cfunctype');
			var Ccontent=document.getElementById('Ccontent');
			var SCvoiceType=document.getElementById('CvoiceType');
			
			var SCexperterList = document.getElementById("CexperterList");
			if(SCfuncType.value=='')
			{
				alert("请选择功能类型");
				return false;
			}
			if(Ccontent)
			{
				if(Ccontent.value=='')
				{
					alert("请填写节点名");
					return false;
				}
			}
			if(SCvoiceType.value=='')
			{
				if(!SCfuncType.value=='conftype')
				alert("请选择节语音类型");
				return false;
			}
			if(SCfuncType.value=='conftype')
			{
				if(SCexperterList.value=='')
				{
	
					alert("请选则专家列表");
					return false;
				}
			}
			//可选项
			var MtelKey = document.getElementById('MtelKey');
			var CtelKey = document.getElementById('CtelKey');
			if(MtelKey.style.display=="block"||MtelKey.style.display=="inline")
			{
				if(CtelKey.value=='')
				{
					alert("请填写电话按键");
					return false;
				}
			}
			var MlengthSize = document.getElementById('MlengthSize');
			var ClengthSize = document.getElementById('ClengthSize');
			if(MlengthSize.style.display=="block"||MlengthSize.style.display=="inline")
			{
				if(ClengthSize.value=='')
				{
					alert("请选填写证位数");
					return false;
				}
			}
	
			var McheckValue = document.getElementById('McheckValue');
			var CcheckValue = document.getElementById('CcheckValue');
			if(McheckValue.style.display=="block"||McheckValue.style.display=="inline")
			{
				if(CcheckValue.value=='')
				{
					alert("请选择验证结果");
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
					alert("请填写别名");
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
    <td  class="labelStyle">参数管理<br></td>
    <td>
		<span class="selectStyle">带<font color="red">*</font>的为基表(base_tree)字段</span>
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="labelStyle">参数ID(*)<br></td>
    <td>
		<html:text property="id" styleClass="writeTextStyle" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="labelStyle">参数名(*)<br></td>
    <td>
		<html:text property="label" styleClass="writeTextStyle"/>
		
	<br></td>
  </tr>
   <tr>
    <td  class="labelStyle">别名(*)<br></td>
    <td>
		<html:text property="nickName" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
  <tr>
    <td  class="labelStyle">是否为根节点(*)<br></td>
    <td>
		<html:select property="isRoot" styleClass="selectStyle">
        	<html:option value="0">子节点</html:option>
        	<html:option value="1">根节点</html:option>
        </html:select>	
	<br></td>
  </tr>
    <tr>
    <td  class="labelStyle">节点类型(*)<br></td>
    <td>
		<html:text property="type" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
   <tr>
    <td  class="labelStyle">上级参数(*)<br></td>
    <td>
        <html:text property="parentName" readonly="true" styleClass="writeTextStyle"/>
		<html:hidden property="parentId" />
	<br>
<%--	<input type="hidden" name="method" value="operParamTree" />--%>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="labelStyle">是否弹出<br></td>
    <td>
        <html:text property="target" styleClass="writeTextStyle"/>
<%--		<html:select property="target">--%>
<%--        	<html:option value="operationframeTree">不弹出</html:option>--%>
<%--        	<html:option value="blank">弹出</html:option>--%>
<%--        </html:select>--%>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">动作<br></td>
    <td>
        <html:text property="action" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
   <tr>
    <td  class="labelStyle">节点图标<br></td>
    <td >
        <html:text property="icon"styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">创建时间(*)<br></td>
    <td>
		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="labelStyle">修改时间(*)<br></td>
    <td>
		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="labelStyle">备注(*)<br></td>
    <td>
		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
   <tr>
   <td colspan="2">
    <!-- beginCcIvrTreeInfo -->
   <tr>
    <td  class="labelStyle">CcIvrTreeInfo字段<br></td>
    <td>
		
	<br></td>
  </tr>

  <tr>
    <td  class="labelStyle">功能类型<br></td>
    <td>
<%--		<html:text property="Cfunctype" styleClass="input"/>--%>
		<html:select property="Cfunctype" styleId="Cfunctype" styleClass="selectStyle" onchange="onSelectChange('Cfunctype')">
    	<html:option value="">请选择</html:option>
    	<logic:equal value="onlyCase" name="pFuncType">
    	<html:option value="case">查询标签</html:option>
    	</logic:equal>
    	<logic:equal value="conference" name="pFuncType">
    	<html:option value="conftype">会议类型</html:option>
    	</logic:equal>
    	<logic:equal value="common" name="pFuncType">
    	<html:option value="manwork">转人工</html:option>
    	<html:option value="filesplay">多语音播放</html:option>
    	<html:option value="select">流程选择</html:option>
    	<html:option value="conference">电话会议</html:option>
<%--    	<html:option value="conftype">会议类型</html:option>--%>
<%--注释调用的时候在注回来 --%>
<%--    	<html:option value="input">输入采集</html:option>--%>
<%--    	<html:option value="query">查询数据库</html:option>--%>
<%--    	<html:option value="appraise">人工评价</html:option>--%>
<%--    	<html:option value="wait">等待</html:option>--%>
    	</logic:equal>
		</html:select>	
		<span><font color="#ff0000">*</font></span>
	</td>
  </tr>
  <tr>
    <td  class="labelStyle">别名<br></td>
    <td>
		<html:hidden property="Cnickname" styleId="Cnickname" styleClass="input" />
		<input type="text" id="inputCNickName" class="writeTextStyle"/>
		<select id="selectCNickName" class="selectStyle">
			<option value="policeId">警员号</option>
			<option value="policePassword">密码</option>
		</select>
		<span id="MnickName" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
    <tr>
    <td  class="labelStyle">节点名称<br></td>
    <td>
		<html:text property="Ccontent" styleClass="writeTextStyle" styleId="Ccontent"/>
		<span><font color="#ff0000">*</font></span>
	</td>
  </tr>
    <tr id="RvoiceType" style="display:block;">
    <td  class="labelStyle">语音类型<br></td>
    <td>
		<html:select property="CvoiceType"  styleId="CvoiceType" styleClass="selectStyle" onchange="changeVoiceType()">
    	<html:option value="">请选择</html:option>
    	<html:option value="none">无语音</html:option>
    	<html:option value="onlyfile">单语音</html:option>
    	<html:option value="allfiles">多语音</html:option>
		</html:select>
		<span id="MvoiceType"><font color="#ff0000">*</font></span>
	</td>
  </tr>

  <tr id="RorderProgramme" style="display:none;">
    <td  class="labelStyle">点播<br></td>
    <td>
            <html:checkbox property="CorderProgramme" ></html:checkbox><span class="selectStyle">点播请按3</span>
	</td>
  </tr>

  <tr id="RcustomizeCancel" style="display:none;">
    <td  class="labelStyle">订制/取消<br></td>
    <td>
    <html:checkbox property="CcustomizeCancel"></html:checkbox><span class="selectStyle">订制请按4/取消请按5</span>
	</td>
  </tr>
  <tr id="RexperterList" style="display:block;">
    <td  class="labelStyle">专家类表<br></td>
    <td>
		<html:select property="CexpertId"  styleId="CexperterList" styleClass="selectStyle">
    	<html:option value="">请选择</html:option>
		<html:options collection="experterList"
							property="value"
							labelProperty="label"/>
		</html:select>
		<span id="MexperterList"><font color="#ff0000">*</font></span>
	</td>
  </tr>
    
  <tr style="display:none;">
    <td  class="labelStyle">电话号码 <br></td>
    <td>
		<html:text property="CtelNum" styleClass="writeTextStyle"/>
	</td>
  </tr>
  <logic:equal value="case" name="isCase">
   <tr id="RtelKey" style="display:none">
  </logic:equal>
  <logic:notEqual value="case" name="isCase">
    <tr id="RtelKey">
  
    <td  class="labelStyle">选择电话按键 <br></td>
    <td>
		<html:text property="CtelKey"  styleClass="writeTextStyle" styleId="CtelKey" style="display:inline"/>
		<span id="MtelKey" style="display:inline"><font color="#ff0000">*</font></span>
	</td>
  </tr>
  </logic:notEqual>
      <tr id="RlengthSize" style="display:none">
    <td  class="labelStyle">验证位数<br></td>
    <td>
		<html:text property="ClengthSize"  styleClass="writeTextStyle"/>
		<span id="MlengthSize" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
  
   <tr id="RcheckValue" style="display:none">
    <td  class="labelStyle">验证结果 <br></td>
    <td>
		<html:select property="CcheckValue" styleClass="selectStyle">
		<html:option value="">请选择</html:option>
    	<html:option value="true">正确</html:option>
    	<html:option value="false">错误</html:option>
		</html:select>
		<span id="McheckValue" style="display:none;"><font color="#ff0000">*</font></span>
	</td>
  </tr>
   
   
  <tr>
    
    <td  class="buttonAreaStyle" colspan="2">
      <input type="submit" id="submitBtn"   style="display:none;"/>
      <img style="display:none" id="imgRecord" alt="录音信息增加" src="../../style/<%=styleLocation %>/images/addReport.gif"
      onclick="popUp('windowsPersonWork','./.././ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoMain&addTreeInfoId=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <logic:notPresent name="opertype">
<%--      <img alt="增加CcIvrTreeInfo" src="../../images/sysoper/particular.gif" onclick="popUp('windowsPersonWork','./../ccIvrTreeinfo.do?method=toCcIvrTreeinfoLoad&type=insert&treeId==<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>--%>
      <img alt="语音信息增加" src="../../style/<%=styleLocation %>/images/addReport.gif"
      onclick="popUp('windowsPersonWork','./.././ccIvrTreevoiceDetail.do?method=toCcIvrTreeinfoMain&addTreeInfoId=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="增加" src="../../style/<%=styleLocation %>/images/add.gif" 
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />&pFuncType=<bean:write name="paramTreeBean" property="Cfunctype" />',650,550)" width="16" height="16" border="0"/>
      <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif" 
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"
      onclick="popUp('windowsPersonWork','./../ivr/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
<%--	  <img alt="删除" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/departmentTreeDelete.jsp?id=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
	  </logic:notPresent>
	 <logic:equal name="opertype" value="update">
		     <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"
		     onclick="execModify()" width="16" height="16" border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="delete">
		     <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"
		     onclick="execDelete()" width="16" height="16"  border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="add">
		     <img alt="增加" src="../../style/<%=styleLocation %>/images/add.gif" 
		     onclick="execAdd()" width="16" height="16"  border="0"/>
	 </logic:equal>
	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>