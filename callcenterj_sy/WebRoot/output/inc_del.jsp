<%@ page contentType="text/html; charset=gbk"%>
<%
	String p = request.getServletPath();
	String l = "";
	if(p.contains("messages")==true){
		l = "../output.do?method=toDelMessages";
	}
%>
<table style="font-size: 12px;" width="100%">
	<tr>
	<form action="<%= l %>" method="post" name="outputForm" id="outputForm" target="_blank">
		<td align="left">
			<input name="Submit1" type="button" id="Submit1" onClick="selAllCheckBox('educe')" value="��ѡ" class="buttonStyle">
			<input name="outputID" type="hidden" id="outputID" value="">
<%--			<input name="dbType" type="hidden" id="dbType" value='<%= (String)request.getParameter("dbType") %>'>--%>
			<input name="Submit4" type="submit" id="Submit4" onClick="clearTopValues()" class="buttonStyle" value="����ɾ��" />
		</td>
	</form>
	</tr>
</table>
<script>

	var topO = window.parent.parent.frames.topFrame;
	if(topO){
		topO = window.parent.parent.frames.item("topFrame").document.getElementById('outputID')
	}else{
		topO = window.parent.parent.opener.parent.frames.topFrame.document.getElementById('outputID');
	}
function selAllCheckBox(checkboxname){//ȫѡ����
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		if (checkboxs.length==null) {
			if(checkboxs.checked){
				checkboxs.checked = false;
				clearTopValue(checkboxs.value);
			}else{
				checkboxs.checked = true;
				setTopValue(checkboxs.value);
			}
			return;
		}
		for (i=0; i<checkboxs.length; i++) {
			if(checkboxs[i].checked){
				checkboxs[i].checked = false;
				clearTopValue(checkboxs[i].value);
			}else{
				checkboxs[i].checked = true;
				setTopValue(checkboxs[i].value);
			}
		}
	}
}
function clearAllCheckBox(checkboxname) {//ȡ��ȫѡ
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			checkboxs[i].checked = false;
			clearTopValue(checkboxs[i].value);
		}
	}
}
function setTopValue(v){//���ĳֵ
	
	var topObj = topO;

	var topValue = topObj.value;
	if(topValue != ""){
		topValue += "," + v;
	}else{
		topValue = v;
	}
	topObj.value = topValue;
}
function clearTopValue(v){//���ĳֵ
	
	var topObj =  topO;

	var topValue = topObj.value;
	var index = topValue.indexOf(v);
	if(index!=-1){
		var tv1 = "";
		var tv2 = "";
		if(v.length+index == topValue.length){//�ж��Ƿ������һ������������һ������ȥ��ǰ�ߵ��Ǹ�����
			tv1 = topValue.substring(0,index-1);
			tv2 = "";
		}else{
			tv1 = topValue.substring(0,index);
			tv2 = topValue.substring(v.length+1+index,topValue.length);
		}
		topObj.value = tv1 + tv2;
	}
}
function clearTopValues(){//��������������ȫ��ֵ

	var topObj =  topO;//alert("44 = "+topObj.value);
	document.getElementById('outputID').value = topObj.value;//������Ҫ�ύ��ֵ
	topObj.value = "";//�����ʱ�����ֵ
	var checkboxs = document.all.item("educe");
	for (i=0; i<checkboxs.length; i++) {//�����ҳѡ��״̬
		checkboxs[i].checked = false;
	}
}
function equalsCheckbox(){//�Ƚ�ֵ����ʾ�Ƿ�ѡ��

	var topObj =  topO;
	var checkboxs = document.all.item("educe");
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			if(topObj.value.indexOf(checkboxs[i].value)!=-1){
				checkboxs[i].checked = true;
			}
		}
	}
}
function setCheckbox(obj){//���ݶ���״̬�жϲ�����ѡ�л���ȡ��
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
function setCheckbox(obj){//���ݶ���״̬�жϲ�����ѡ�л���ȡ��
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
</script>
