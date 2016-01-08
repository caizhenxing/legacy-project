<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/leaf_right.tld" prefix="leafRight" %>

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
	<link href="./../../images/css/styleA.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/tools.js"></script>
	<script language="javascript">
	    function killErrors() {
		return true;
		}
		window.onerror = killErrors;
		function execModify()
		{
			window.opener.parent.frames["topp"].window.location.reload();
  			window.opener.parent.frames["operationframeTree"].window.location.reload();
			
			document.getElementById('operType').value="update";
    		document.forms[0].submit();
    		
    		window.close();
    		//document.getElementById("treeForm").submit();
		}
		
		function execDelete()
		{
			//document.getElementById("treeForm").action = "./../department/deptTree.do?method=operParamTree&operType=delete";
    		window.opener.parent.frames["topp"].window.location.reload();
  			window.opener.parent.frames["operationframeTree"].window.location.reload();
    		
    		document.getElelemntById('operType').value="delete";
    		document.getElementById("treeForm").submit();
    		
    		window.close();
		}
		
		function execAdd()
		{
			///sys/department/deptTree.do?method=operParamTree&operType=insert
			window.opener.parent.frames["topp"].window.location.reload();
  			window.opener.parent.frames["operationframeTree"].window.location.reload();
			
			document.getElementById('operType').value="add";
    		document.forms[0].submit();
		
			window.close();
		}
	</script>
  </head>
  
  <body><br>
    <html:form styleId="paramTreeBean" action="/sys/department/deptTree.do?method=operParamTree">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  
  <tr>
    <td  class="tdbgcolorloadright">部门管理<br></td>
    <td  class="tdbgcolorloadleft">
		带<font color="red">*</font>的为基表(base_tree)字段
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="tdbgcolorloadright">部门号(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="id" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="tdbgcolorloadright">部门名(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="label" />
		
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">别名(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="nickName" />	
	<br></td>
  </tr>
    <tr>
    <td  class="tdbgcolorloadright">节点类型(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="type" />	
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">上级部门(*)<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="parentName" readonly="true" />
		<html:hidden property="parentId" />
	<br>
<%--	<input type="hidden" name="method" value="operParamTree" />--%>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">是否弹出<br></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="target">
        	<html:option value="operationframeTree">不弹出</html:option>
        	<html:option value="blank">弹出</html:option>
        </html:select>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">动作<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="action" />
	<br>
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">节点图标<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="icon" />
	<br>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">创建时间(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="createTime" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="tdbgcolorloadright">修改时间(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="modifyTime" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="tdbgcolorloadright">备注(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:textarea property="remark" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
  <tr>
    <td></td>
    <td  class="tdbgcolorloadbuttom">
      <logic:notPresent name="opertype">
      <img alt="增加" src="../../images/sysoper/particular.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>
      <img alt="修改" src="../../images/sysoper/update.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="删除" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
<%--	  <img alt="删除" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/departmentTreeDelete.jsp?id=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
	  </logic:notPresent>
	 <logic:equal name="opertype" value="update">
		     <img alt="修改" src="../../images/sysoper/update.gif" onclick="execModify()" width="16" height="16" border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="delete">
		     <img alt="删除" src="../../images/sysoper/del.gif" onclick="execDelete()" width="16" height="16"  border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="add">
		     <img alt="增加" src="../../images/sysoper/particular.gif" onclick="execAdd()" width="16" height="16"  border="0"/>
	 </logic:equal>
	</td>
  </tr>
</table>
</html:form>
<table  width="539" height="59">
<tr>
<td>
<%--<font color="red">这个标签&lt;leafRight:btn &gt;可对按钮授权 以后如果扩展可对Struts标签&lt;html:text &gt;即文本框授权</font>--%>
</td>
</tr>
<tr>
<td>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_add" value="增加" onclick="alert('增加按钮点击了')" />--%>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_delete" value="删除" onclick="alert('删除按钮点击了')" />--%>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_update" value="修改" onclick="alert('修改按钮点击了')" />--%>
</td>
</tr>
</table>
  </body>
</html:html>
