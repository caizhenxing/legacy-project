<%@ page contentType="text/html; charset=gbk"%>
<%@ include file="../style.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>�Զ����ɴ𰸱�ѡ��</title>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
</head>

<body class="loadBody">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable" id="table1">
	<tr>
		<td class="navigateStyle" colspan="3">
			�Զ����ɴ𰸱�ѡ��
		</td>
	</tr>
	<tr class="listTitleStyle">
		<td>
			ѡ��
		</td>
		<td>
			�𰸱�ѡ�����ݣ�ÿ��һ����
		</td>
		<td>
			<input name="button" type="button" onClick="addRow();" value="���">
		</td>
	</tr>
	<tr class="evenStyle">
		<td>
			1
		</td>
		<td>
			<input name="alternatives" type="text" id="alternatives" size="30">
		</td>
		<td>
			<input name="delrow" type="button" onClick="delRow(this);" value="ɾ��">
		</td>
	</tr>
	<tr class="evenStyle">
		<td>
			2
		</td>
		<td>
			<input name="alternatives" type="text" id="alternatives" size="30">
		</td>
		<td>
			<input name="delrow" type="button" onClick="delRow(this);" value="ɾ��">
		</td>
	</tr>
	<tr class="evenStyle">
		<td>
			3
		</td>
		<td>
			<input name="alternatives" type="text" id="alternatives" size="30">
		</td>
		<td>
			<input name="delrow" type="button" onClick="delRow(this);" value="ɾ��">
		</td>
	</tr>
	<tr>
		<td class="buttonAreaStyle"  colspan="3" height="28">
			<input name="Submit1" type="button" onClick="ok();" value="ȷ��">
			<input name="Submit2" type="button" onClick="window.close();" value="�ر�">
		</td>
	</tr>
</table>
</body>
</html>

<script>
	function delRow(obj){
		document.getElementById("table1").deleteRow(obj.parentNode.parentNode.rowIndex);
	}
	function addRow(){
		var table = document.getElementById("table1");
		var rowindex = table.rows.length-1;
		var trNum = rowindex-1;
		tr = table.insertRow(rowindex);
		tr.className = "evenStyle";
		td = tr.insertCell();
		td.innerHTML = trNum;
		td = tr.insertCell();
		td.innerHTML = '<input name="alternatives" type="text" id="question" size="30">';
		td = tr.insertCell();
		td.innerHTML = '<input name="delrow" type="button" value="ɾ��" onClick="delRow(this)">';
	}
	function ok(){
		var alternatives = document.getElementsByName("alternatives");
		var str = "";
		for(var i = 0; i<alternatives.length; i++){
			var value = alternatives[i].value.trim();
			if(value != ""){
				str += value + ";";
			}
		}
		if(str != ""){
			str = str.substring(0, str.length-1);
		}
		var index = document.location.search.substr(1);
		var tableP = opener.document.getElementById("questions");
		var textObj = tableP.rows(Number(index)).cells(2).childNodes(0);
		textObj.value = str;
		window.close();
		
	}
	String.prototype.trim = function() {
    	// ��������ʽ��ǰ��ո��ÿ��ַ��������
    	return this.replace(/(^\s*)|(\s*$)/g, "");
	}
</script>
