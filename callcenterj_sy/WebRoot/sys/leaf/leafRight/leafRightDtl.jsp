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
    
    <title>Ҷ�ӽڵ����</title>

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
 		alert("�����ɹ�");
 		window.close();
 	</script>
 </logic:notEmpty>
<html:form action="/sys/leafRight/leafRight.do" method="post">
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
	<tr>
		<td align="center"  bgcolor="#ffffff" colspan="3">Ҷ�ӽڵ����</td>
	</tr>
	<tr>
		<td align="center"  bgcolor="#ffffff" colspan="3">
		
			<logic:equal name="type" value="add">
				����
			</logic:equal>
			<logic:equal name="type" value="delete">
				ɾ��
			</logic:equal>
			<logic:equal name="type" value="update">
				�޸�
			</logic:equal>
		
		</td>
	</tr>
<%--	<tr>--%>
<%--		<td class="tdbgcolorloadright">--%>
<%--			�ڵ�ID--%>
<%--		</td>--%>
<%--		<td class="tdbgcolorloadleft">--%>
<%--			<html:text property="treeId" styleClass="input"/>--%>
<%--		</td>--%>
<%--	</tr>--%>
	
	<tr>
		<td class="tdbgcolorloadright">
			Ȩ������
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="treeType" styleClass="input" value="leaf_right_btn" readonly="true"/>
			<html:hidden property="icon" value="expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;" />
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			��ʾ����
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="label" styleClass="input"/>
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			�ǳ�
		</td>
		<td class="tdbgcolorloadleft">
			<html:text property="nickName" styleClass="input"/>
		</td>
	</tr>
	
	<tr>
		<td class="tdbgcolorloadright">
			��ע
		</td>
		<td class="tdbgcolorloadleft">
			<html:textarea property="remark" rows="5" cols="50"/>
		</td>
		<html:hidden property="id"/>
	</tr>
	<tr>
		<td align="center" bgcolor="#ffffff" colspan="4">
			<logic:equal name="type" value="insert">
				<input type="button" name="addbtn" value="����" onclick="add()"/>
			</logic:equal>
			<logic:equal name="type" value="update">
				<input type="button" name="updatebtn" value="�޸�" onclick="update()"/>
			</logic:equal>
			<logic:equal name="type" value="delete">
				<input type="button" name="deletebtn" value="ɾ��" onclick="del()"/>
			</logic:equal>
				<input type="button" name="closebtn" value="�ر�" onclick="javascript:close"/>
		</td>
	</tr>
</table>
</html:form>
  </body>
</html:html>
