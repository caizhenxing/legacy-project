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
    <td  class="tdbgcolorloadright">���Ź���<br></td>
    <td  class="tdbgcolorloadleft">
		��<font color="red">*</font>��Ϊ����(base_tree)�ֶ�
	<br></td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="tdbgcolorloadright">���ź�(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="id" readonly="true" />
	<br></td>
  </tr>
  </logic:notEqual>
  <tr>
    <td  class="tdbgcolorloadright">������(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="label" />
		
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">����(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="nickName" />	
	<br></td>
  </tr>
    <tr>
    <td  class="tdbgcolorloadright">�ڵ�����(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="type" />	
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">�ϼ�����(*)<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="parentName" readonly="true" />
		<html:hidden property="parentId" />
	<br>
<%--	<input type="hidden" name="method" value="operParamTree" />--%>
	<input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">�Ƿ񵯳�<br></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="target">
        	<html:option value="operationframeTree">������</html:option>
        	<html:option value="blank">����</html:option>
        </html:select>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">����<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="action" />
	<br>
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">�ڵ�ͼ��<br></td>
    <td  class="tdbgcolorloadleft">
        <html:text property="icon" />
	<br>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">����ʱ��(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="createTime" readonly="true" />
	</td>
  </tr>
  <logic:notEqual name="opertype" value="add">
  <tr>
    <td  class="tdbgcolorloadright">�޸�ʱ��(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="modifyTime" readonly="true" />
	</td>
  </tr>
  </logic:notEqual>
     <tr>
    <td  class="tdbgcolorloadright">��ע(*)<br></td>
    <td  class="tdbgcolorloadleft">
		<html:textarea property="remark" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
  <tr>
    <td></td>
    <td  class="tdbgcolorloadbuttom">
      <logic:notPresent name="opertype">
      <img alt="����" src="../../images/sysoper/particular.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=add&parentId=<bean:write name="paramTreeBean" property="id" />&parentName=<bean:write name="paramTreeBean" property="label" />&type=<bean:write name="paramTreeBean" property="type" />',650,550)" width="16" height="16" border="0"/>
      <img alt="�޸�" src="../../images/sysoper/update.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=update&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16" border="0"/>
      <img alt="ɾ��" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/deptTree.do?method=toParamDtl&opertype=delete&tree=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>
<%--	  <img alt="ɾ��" src="../../images/sysoper/del.gif" onclick="popUp('windowsPersonWork','./../department/departmentTreeDelete.jsp?id=<bean:write name="paramTreeBean" property="id" />',650,550)" width="16" height="16"  border="0"/>--%>
	  </logic:notPresent>
	 <logic:equal name="opertype" value="update">
		     <img alt="�޸�" src="../../images/sysoper/update.gif" onclick="execModify()" width="16" height="16" border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="delete">
		     <img alt="ɾ��" src="../../images/sysoper/del.gif" onclick="execDelete()" width="16" height="16"  border="0"/>
	 </logic:equal>
	  <logic:equal name="opertype" value="add">
		     <img alt="����" src="../../images/sysoper/particular.gif" onclick="execAdd()" width="16" height="16"  border="0"/>
	 </logic:equal>
	</td>
  </tr>
</table>
</html:form>
<table  width="539" height="59">
<tr>
<td>
<%--<font color="red">�����ǩ&lt;leafRight:btn &gt;�ɶ԰�ť��Ȩ �Ժ������չ�ɶ�Struts��ǩ&lt;html:text &gt;���ı�����Ȩ</font>--%>
</td>
</tr>
<tr>
<td>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_add" value="����" onclick="alert('���Ӱ�ť�����')" />--%>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_delete" value="ɾ��" onclick="alert('ɾ����ť�����')" />--%>
<%--<leafRight:btn scopeName="userRoleLeafRightInsession" nickName="btn_dept_leaf_update" value="�޸�" onclick="alert('�޸İ�ť�����')" />--%>
</td>
</tr>
</table>
  </body>
</html:html>
