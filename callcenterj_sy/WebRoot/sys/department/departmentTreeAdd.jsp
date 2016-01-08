<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


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
		function execModify()
		{
			document.getElementById("treeForm").action = "./../department/deptTree.do?method=operParamTree&operType=update";
    		//document.forms[0].submit();
    		document.getElementById("treeForm").submit();
		}
	</script>
	<%
	    String pName = request.getParameter("pName");
	    if(pName != null)
	    {
	    	pName = new String(pName.getBytes("iso8859-1"),"GBK");
	    }
	    else
	    	pName = "";
    %>
  </head>
  
  <body>
    <html:form styleId="treeForm" action="/sys/department/deptTree.do?method=operParamTree&operType=insert">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td  class="tdbgcolorloadright">部门名<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="label" />
		
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">别名<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="nickName" />	
	<br></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">上级部门<br></td>
    <td  class="tdbgcolorloadleft">
        <html:hidden property="parentId" value="<%=request.getParameter("pId") %>" />
		<html:text property="parentName" readonly="true" value="<%=pName %>"/>
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">是否弹出<br></td>
    <td  class="tdbgcolorloadleft">
        <html:select property="target">
        	<html:option value="operationframe">不弹出</html:option>
        	<html:option value="blank">弹出</html:option>
        </html:select>
	<br><input type="hidden" name="method" value="operParamTree" /><input type="hidden" name="operType" id="operType" value="" /></td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright">动作<br></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="action"></html:text>
	<br></td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">备注<br></td>
    <td  class="tdbgcolorloadleft">
		<html:textarea property="remark" rows="5" cols="35"></html:textarea>
	<br></td>
  </tr>
  <tr>
    <td></td>
    <td  class="tdbgcolorloadbuttom">
	<input type="submit" value="增加" onclick="window.close()">
	</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
