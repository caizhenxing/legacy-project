<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>Insert title here</title>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	
</head>
<script type="text/javascript">
	
	var frm = document.forms[0];
		var imc_using = 1
		
		var sign = " ������:��Ϣ��ѯ";
		var msgNum = 0;
	function CountNum()
		{
			var temp = document.forms[0].content.value;
			if(temp != null)
			    msgNum = temp.length;
			else
				msgNum = 0;
			
		    var wordnum = msgNum;
		   
		    document.all("CurWordNum").innerHTML ="<font color='red'  id='fontStyle'>"+msgNum + "</font>��" ;
		    
		}
	function yesno() {
			var columnName =document.getElementById("columnName").value;
			var content =document.getElementById("content").value;
    		if(columnName==null||columnName==""){
    			alert("�������Ʋ���Ϊ�գ�");
    			return false;
    		}
    		if(content==null||content==""){
    			alert("�������ݲ���Ϊ�գ�");
    			return false;
    		}
			if(confirm("�Ƿ���Ӷ���?")){
				if(msgNum>68){				
					document.all("check").innerHTML ="<font color='red'  id='fontStyle'>�������ݲ��ܳ���68��</font>" ;
					return false;
				}else{
					alert("��ӳɹ���");
					return true;
				}
			}else{
				return false;
			}
			
	}
</script>
<body class="conditionBody">
	<html:form action="/sms/columnInfo?method=toMessageColumnInfoLoad"  method="post" onsubmit="return yesno()">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td align="left" class="navigateStyle">
					��ǰλ��&ndash;&gt;����ά��
				</td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					������Ŀ
				</td>
				<td class="valueStyle">
					<html:select property="columnInfo" style="width:160px">
						<html:options collection="list" property="value"
							labelProperty="label" />

					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle" >
					���ű���
				</td>
				<td class="valueStyle">
					<input type="text" name="columnName" id="columnName" style="width:160px"><font color="red">*</font>
				</td>
			</tr>
			
			<tr>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
					<textarea rows="5" cols="50" class="" name="content" id="content"
						onkeyup="CountNum();" onchange="CountNum();" onfocus="CountNum();"></textarea><font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��ǰ��������
				</td>
				<td class="valueStyle">
					<span class="cpx12red" id="CurWordNum"><font color="red"
						id="fontStyle">0��</font> </span>
				</td>
			</tr>
			<tr>
				<td class="labelStyle" colspan="2" align="center">
					<input type="submit" value="���ȷ��" class="buttonStyle" >
					<span class="cpx12red" id="check"> </span>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
