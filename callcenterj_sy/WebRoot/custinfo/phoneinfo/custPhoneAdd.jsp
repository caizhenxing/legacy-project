<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../../style.jsp"%>
 <logic:notEmpty name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		
		window.close();
	</script>
</logic:notEmpty>
<html:html lang="true">
<head>
	<html:base />

	<title>�绰������</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript">
    	//��ѯ
    	function add(){
    		var excelPath = document.forms[0].excelPath.value;
    		if("" == excelPath) {
    			alert("����ѡ��Excel�ļ���");
    			return;
    		}
    		document.forms[0].action = "../phoneinfo.do?method=toPhoneCountAdd";
    		document.forms[0].target = "contents";
    		document.forms[0].submit();
    		window.close();
    	}
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}

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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
   	</script>
	<style type="text/css">
<!--
body,td,th {
	font-size: 13px;
}
-->
</style>
</head>

<body class="conditionBody" >
	<html:form action="/custinfo/phoneinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					��ǰλ��&ndash;&gt;�û���Ϣ���
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="tablebgcolor">
			<tr>
				<td class="queryLabelStyle">
					&nbsp;ѡ��Excel�ļ�
				</td>
				<td class="valueStyle">
					<input type="file" name="excelPath" size="35">
				</td>
<%--				<td class="queryLabelStyle">--%>
<%--					&nbsp;�û��绰--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:text property="cust_tel_home" size="13"--%>
<%--						styleClass="writeTextStyle" />--%>
<%--				</td>--%>
<%--				<td class="queryLabelStyle">--%>
<%--					&nbsp;�û����--%>
<%--				</td>--%>
<%--				<td class="valueStyle">--%>
<%--					<html:select property="dict_cust_type" styleClass="writeTypeStyle"--%>
<%--							onchange="selectType()">--%>
<%--							<%--%>
<%--								String type=(String)request.getSession().getAttribute("type");--%>
<%--								if(type!=null){--%>
<%--							 %>--%>
<%--							<html:option value="SYS_TREE_0000002108">������Ա</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002102" >����ϯԱ</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002103" >��ר��</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002104" >����ҵ</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002105" >��ý��</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002106" >������</html:option>--%>
<%--							<html:option value="SYS_TREE_0000002109" >����ͨ�û�</html:option>							--%>
<%--							 <%--%>
<%--							 	}else {--%>
<%--							 	--%>
<%--							  %>--%>
<%--							<html:option value="">��ѡ��</html:option>--%>
<%--							<html:options collection="telNoteTypeList" property="value"--%>
<%--								labelProperty="label" styleClass="writeTypeStyle" />--%>
<%--							<%--%>
<%--								}--%>
<%--							 %>--%>
<%--							--%>
<%--					</html:select>--%>
<%--				</td>--%>
				<td align="right" class="queryLabelStyle">
					<input name="btnSearch" type="button" class="buttonStyle"
						value="���" onclick="add()" />
					<input type="reset" value="ˢ��" class="buttonStyle">
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle">
					ʹ��˵����
				</td>
				<td class="valueStyle" colspan="2">
					��������Ա��Excel�ļ���������������ݡ�
				</td>
			</tr>
			<tr height="1px" class="buttonAreaStyle">
				<td colspan="9">

				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
