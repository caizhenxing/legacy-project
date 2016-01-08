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
    <td  class="Title">参数管理<br></td>
    <td class="Title">
		<span style="display:none;">带<font color="red">*</font>的为基表(base_tree)字段</span>
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="Left_title">参数ID(*)<br></td>
    <td class="Right_title">
		<html:text property="id" styleClass="writeTextStyle" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="Left_title">参数名(*)<br></td>
    <td class="Right_title">
		<html:text property="label" styleClass="writeTextStyle"/>
		
	<br></td>
  </tr>
   <tr>
    <td  class="Left_title">别名(*)<br></td>
    <td class="Right_title">
		<html:text property="nickName" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
  <tr>
    <td  class="Left_title">是否为根节点(*)<br></td>
    <td class="Right_title">
		<html:select property="isRoot" styleClass="selectStyle">
        	<html:option value="0">子节点</html:option>
        	<html:option value="1">根节点</html:option>
        </html:select>	
	<br></td>
  </tr>
    <tr>
    <td  class="Left_title">节点类型(*)<br></td>
    <td class="Right_title">
		<html:text property="type" styleClass="writeTextStyle"/>	
	<br></td>
  </tr>
   <tr>
    <td  class="Left_title">上级参数(*)<br></td>
    <td class="Right_title">
        <html:text property="parentName" styleClass="writeTextStyle" readonly="true" />
		<html:hidden property="parentId" />
	<br>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="Left_title">是否弹出<br></td>
    <td class="Right_title">
        <html:text property="target" styleClass="writeTextStyle"/>
	</td>
  </tr>
  <tr>
    <td class="Left_title">动作<br></td>
    <td class="Right_title">
        <html:text property="action" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
   <tr>
    <td class="Left_title">节点图标<br></td>
    <td class="Right_title">
        <html:text property="icon" styleClass="writeTextStyle"/>
	<br>
	</td>
  </tr>
  <tr>
    <td  class="Left_title">创建时间(*)<br></td>
    <td class="Right_title">
		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="Left_title">修改时间(*)<br></td>
    <td class="Right_title">
		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="Left_title">备注<br></td>
    <td class="Right_title">
		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
  <tr>
    
    <td  class="Title" colspan="2">
      <logic:notPresent name="opertype">
      <img alt="增加" src="../../style/<%=styleLocation %>/images/add.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>
      <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"
      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
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
<%--    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">--%>
<%--  --%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">参数管理<br></td>--%>
<%--    <td>--%>
<%--		<span style="display:none;">带<font color="red">*</font>的为基表(base_tree)字段</span>--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <logic:notEqual name="opertype" value="add">--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">参数ID(*)<br></td>--%>
<%--    <td >--%>
<%--		<html:text property="id" styleClass="writeTextStyle" readonly="true" />--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  </logic:notEqual>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">参数名(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="label" styleClass="writeTextStyle"/>--%>
<%--		--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">别名(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="nickName" styleClass="writeTextStyle"/>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">是否为根节点(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:select property="isRoot" styleClass="selectStyle">--%>
<%--        	<html:option value="0">子节点</html:option>--%>
<%--        	<html:option value="1">根节点</html:option>--%>
<%--        </html:select>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--    <tr>--%>
<%--    <td  class="labelStyle">节点类型(*)<br></td>--%>
<%--    <td >--%>
<%--		<html:text property="type" styleClass="writeTextStyle"/>	--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">上级参数(*)<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="parentName" styleClass="writeTextStyle" readonly="true" />--%>
<%--		<html:hidden property="parentId" />--%>
<%--	<br>--%>
<%--	<input type="hidden" name="operType" id="operType" value="" /></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">是否弹出<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="target" styleClass="writeTextStyle"/>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">动作<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="action" styleClass="writeTextStyle"/>--%>
<%--	<br>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--   <tr>--%>
<%--    <td  class="labelStyle">节点图标<br></td>--%>
<%--    <td>--%>
<%--        <html:text property="icon" styleClass="writeTextStyle"/>--%>
<%--	<br>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">创建时间(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="createTime" styleClass="writeTextStyle" readonly="true" />--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  <logic:notEqual name="opertype" value="add">--%>
<%--  <tr>--%>
<%--    <td  class="labelStyle">修改时间(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:text property="modifyTime" styleClass="writeTextStyle" readonly="true" />--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--  </logic:notEqual>--%>
<%--     <tr>--%>
<%--    <td  class="labelStyle">备注(*)<br></td>--%>
<%--    <td>--%>
<%--		<html:textarea property="remark" styleClass="writeTextStyle" rows="5" cols="35"></html:textarea>--%>
<%--	<br></td>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    --%>
<%--    <td  class="buttonAreaStyle" colspan="2">--%>
<%--      <logic:notPresent name="opertype">--%>
<%--      <img alt="增加" src="../../style/<%=styleLocation %>/images/add.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>--%>
<%--      <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>--%>
<%--      <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"--%>
<%--      onclick="popUp('windowsPersonWork','./../parameter/paramTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
<%--	  </logic:notPresent>--%>
<%--	 <logic:equal name="opertype" value="update">--%>
<%--		     <img alt="修改" src="../../style/<%=styleLocation %>/images/update.gif"--%>
<%--		     onclick="execModify()" width="16" height="16" border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	  <logic:equal name="opertype" value="delete">--%>
<%--		     <img alt="删除" src="../../style/<%=styleLocation %>/images/del.gif"--%>
<%--		     onclick="execDelete()" width="16" height="16"  border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	  <logic:equal name="opertype" value="add">--%>
<%--		     <img alt="增加" src="../../style/<%=styleLocation %>/images/add.gif"--%>
<%--		     onclick="execAdd()" width="16" height="16"  border="0"/>--%>
<%--	 </logic:equal>--%>
<%--	</td>--%>
<%--  </tr>--%>
<%--</table>--%>
</html:form>
  </body>
</html:html>
