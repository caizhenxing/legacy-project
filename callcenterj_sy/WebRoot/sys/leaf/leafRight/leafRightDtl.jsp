<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html >
  <head>
    <html:base />
    
    <title>叶子节点操作</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="../../js/tools.js"></script>
	<script type="text/javascript">
		function add()
				{
			 		document.forms[0].action="./../../leafRight/leafRight.do?method=opertree&type=insert";
			 		document.forms[0].submit();
				}
		function update()
				{
			 		document.forms[0].action="./../../leafRight/leafRight.do?method=opertree&type=update";
			 		document.forms[0].submit();
				}
				

	function del()
				{
			 		document.forms[0].action="./../../leafRight/leafRight.do?method=opertree&type=delete";
			 		document.forms[0].submit();
				}
		
		function toback()
		{
			opener.parent.operationframeTree.document.all.btnsel.click();
		}
	</script>
  </head>


 <body onunload="toback()">
 <logic:notEmpty name="operSign">
 	<script>
 		alert("操作成功");
 		window.close();
 	</script>
 </logic:notEmpty>
<html:form action="/sys/leafRight/leafRight.do" method="post">
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
	<tr>
		<td align="center"  bgcolor="#ffffff" colspan="3">叶子节点操作</td>
	</tr>
	<tr>
		<td align="center"  bgcolor="#ffffff" colspan="3">
		
			<logic:equal name="type" value="add">
				增加
			</logic:equal>
			<logic:equal name="type" value="delete">
				删除
			</logic:equal>
			<logic:equal name="type" value="update">
				修改
			</logic:equal>
		
		</td>
	</tr>
<%--	<tr>--%>
<%--		<td class="tdbgcolorloadright">--%>
<%--			节点ID--%>
<%--		</td>--%>
<%--		<td class="tdbgcolorloadleft">--%>
<%--			<html:text property="treeId" styleClass="input"/>--%>
<%--		</td>--%>
<%--	</tr>--%>
	
	<tr>
		<td class="tdbgcolorloadright">
			权限类型
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="treeType" styleClass="input" value="leaf_right_btn" readonly="true"/>
			<html:hidden property="icon" value="expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;" />
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			显示名称
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="label" styleClass="input"/>
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			昵称
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="nickName" styleClass="input"/>
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			备注
		</td>
		<td class="tdbgcolorloadleft">
			<html:textarea property="remark" rows="5" cols="50"/>
		</td>
		<html:hidden property="id"/>
	</tr>
	<tr>
		<td align="center" bgcolor="#ffffff" colspan="4">
			<logic:equal name="type" value="insert">
				<input type="button" name="addbtn" value="增加" onclick="add()"/>
			</logic:equal>
			<logic:equal name="type" value="update">
				<input type="button" name="updatebtn" value="修改" onclick="update()"/>
			</logic:equal>
			<logic:equal name="type" value="delete">
				<input type="button" name="deletebtn" value="删除" onclick="del()"/>
			</logic:equal>
				<input type="button" name="closebtn" value="关闭" onclick="javascript:close"/>
		</td>
	</tr>
</table>
</html:form>
  </body>
</html:html>
