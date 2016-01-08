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
    
    <title>paramTreeDtl.jsp</title>

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
	return true;
	}
	//window.onerror = killErrors;
		function execModify()
		{
			//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();
			//window.opener.parent.parent.frames["tree"].window.location.href="./../../sys/login.do?method=reloadModule";
			document.getElementById('operType').value="update";
    		document.forms[0].submit();
    		
    		//window.close();
    		//document.getElementById("treeForm").submit();
		}
		
		function execDelete()
		{
			//document.getElementById("treeForm").action = "./../department/deptTree.do?method=operParamTree&operType=delete";
    		//document.getElelemntById('operType').value="delete";
    		//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();
    		
    		document.getElementById('operType').value="delete";
    		document.forms[0].submit();
    		
    		//window.close();
		}
		
		function execAdd()
		{
			///sys/department/deptTree.do?method=operParamTree&operType=insert
			
			//window.opener.parent.frames["topp"].window.location.reload();
  			//window.opener.parent.frames["operationframeTree"].window.location.reload();

			document.getElementById('operType').value="add";
    		document.forms[0].submit();
    		
    		//window.close();
		}
	</script>
  </head>
  
  <body class="treeBody"><br>
    <html:form styleId="paramTreeBean" action="/sys/parameter/paramTree.do?method=operParamTree">
<table width="502" border="0" cellpadding="0" cellspacing="1">
  
  <tr>
    <td  class="Title">��������<br></td>
    <td class="Title">
		<span style="display:none;">��<font color="red">*</font>��Ϊ����(base_tree)�ֶ�</span>
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="Left_title">����ID(*)<br></td>
    <td class="Right_title">
		<html:text property="id" styleClass="writeTextStyle" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="Left_title">������(*)<br></td>
    <td class="Right_title">
		<html:text property="label" styleClass="writeTextStyle"/>
		
	<br></td>
  </tr>
   <tr>
    <td  class="Left_title">����(*)<br></td>
    <td class="Right_title">
		<html:text property="nickName" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
  <tr>
    <td  class="Left_title">�Ƿ�Ϊ���ڵ�(*)<br></td>
    <td class="Right_title">
		<html:select property="isRoot" styleClass="selectStyle">
        	<html:option value="0">�ӽڵ�</html:option>
        	<html:option value="1">���ڵ�</html:option>
        </html:select>	
	<br></td>
  </tr>
    <tr>
    <td  class="Left_title">�ڵ�����(*)<br></td>
    <td class="Right_title">
		<html:text property="type" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
   <tr>
    <td  class="Left_title">�ϼ�����(*)<br></td>
    <td class="Right_title">
        <html:text property="parentName" styleClass="writeTextStyle" readonly="true" />
		<html:hidden property="parentId" />
	<br>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="Left_title">�Ƿ񵯳�<br></td>
    <td class="Right_title">
        <html:text property="target" styleClass="writeTextStyle"/>
	</td>
  </tr>
  <tr>
    <td class="Left_title">����<br></td>
    <td class="Right_title">
        <html:text property="action" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
   <tr>
    <td class="Left_title">�ڵ�ͼ��<br></td>
    <td class="Right_title">
        <html:text property="icon" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
  <tr>
    <td  class="Left_title">����ʱ��(*)<br></td>
    <td class="Right_title">
		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="Left_title">�޸�ʱ��(*)<br></td>
    <td class="Right_title">
		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="Left_title">��ע<br></td>
    <td class="Right_title">
		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
  <tr>
    
    <td  class="Title" colspan="2">
      <logic:notPresent name="opertype">
      <img alt="����" src="../../style/<%=styleLocation %>/images/add.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>
      <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
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
<%--    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">--%>
<%--  --%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">��������<br></td>--%>
<%--    <td>--%>
<%--		<span style="display:none;">��<font color="red">*</font>��Ϊ����(base_tree)�ֶ�</span>--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <logic:notEqual name="opertype" value="add">--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">����ID(*)<br></td>--%>
<%--    <td >--%>
<%--		<html:text property="id" styleClass="writeTextStyle" readonly="true" />--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  </logic:notEqual>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">������(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="label" styleClass="writeTextStyle"/>--%>
<%--		--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">����(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="nickName" styleClass="writeTextStyle"/>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">�Ƿ�Ϊ���ڵ�(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:select property="isRoot" styleClass="selectStyle">--%>
<%--        	<html:option value="0">�ӽڵ�</html:option>--%>
<%--        	<html:option value="1">���ڵ�</html:option>--%>
<%--        </html:select>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--    <tr>--%>
<%--    <td  class="labelStyle">�ڵ�����(*)<br></td>--%>
<%--    <td >--%>
<%--		<html:text property="type" styleClass="writeTextStyle"/>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">�ϼ�����(*)<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="parentName" styleClass="writeTextStyle" readonly="true" />--%>
<%--		<html:hidden property="parentId" />--%>
<%--	<br>--%>
<%--	<input type="hidden" name="operType" id="operType" value="" /></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">�Ƿ񵯳�<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="target" styleClass="writeTextStyle"/>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">����<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="action" styleClass="writeTextStyle"/>--%>
<%--	<br>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">�ڵ�ͼ��<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="icon" styleClass="writeTextStyle"/>--%>
<%--	<br>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">����ʱ��(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <logic:notEqual name="opertype" value="add">--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">�޸�ʱ��(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  </logic:notEqual>--%>
<%--     <tr>--%>
<%--    <td  class="labelStyle">��ע(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    --%>
<%--    <td  class="buttonAreaStyle" colspan="2">--%>
<%--      <logic:notPresent name="opertype">--%>
<%--      <img alt="����" src="../../style/<%=styleLocation %>/images/add.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>--%>
<%--      <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>--%>
<%--      <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
<%--	  </logic:notPresent>--%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--		     <img alt="�޸�" src="../../style/<%=styleLocation %>/images/update.gif"--%>
<%--		     onclick="execModify()" width="16" height="16" border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	  <logic:equal name="opertype" value="delete">--%>
<%--		     <img alt="ɾ��" src="../../style/<%=styleLocation %>/images/del.gif"--%>
<%--		     onclick="execDelete()" width="16" height="16"  border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	  <logic:equal name="opertype" value="add">--%>
<%--		     <img alt="����" src="../../style/<%=styleLocation %>/images/add.gif"--%>
<%--		     onclick="execAdd()" width="16" height="16"  border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--</table>--%>
</html:form>
  </body>
</html:html>
