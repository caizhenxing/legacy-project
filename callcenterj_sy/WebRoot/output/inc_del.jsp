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
			<input name="Submit1" type="button" id="Submit1" onClick="selAllCheckBox('educe')" value="反选" class="buttonStyle">
			<input name="outputID" type="hidden" id="outputID" value="">
<%--			<input name="dbType" type="hidden" id="dbType" value='<%= (String)request.getParameter("dbType") %>'>--%>
			<input name="Submit4" type="submit" id="Submit4" onClick="clearTopValues()" class="buttonStyle" value="批量删除" />
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
function selAllCheckBox(checkboxname){//全选方法
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
function clearAllCheckBox(checkboxname) {//取消全选
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			checkboxs[i].checked = false;
			clearTopValue(checkboxs[i].value);
		}
	}
}
function setTopValue(v){//添加某值
	
	var topObj = topO;

	var topValue = topObj.value;
	if(topValue != ""){
		topValue += "," + v;
	}else{
		topValue = v;
	}
	topObj.value = topValue;
}
function clearTopValue(v){//清除某值
	
	var topObj =  topO;

	var topValue = topObj.value;
	var index = topValue.indexOf(v);
	if(index!=-1){
		var tv1 = "";
		var tv2 = "";
		if(v.length+index == topValue.length){//判断是否是最后一个，如果是最后一个，则去掉前边的那个逗号
			tv1 = topValue.substring(0,index-1);
			tv2 = "";
		}else{
			tv1 = topValue.substring(0,index);
			tv2 = topValue.substring(v.length+1+index,topValue.length);
		}
		topObj.value = tv1 + tv2;
	}
}
function clearTopValues(){//当点击导出后清除全部值

	var topObj =  topO;//alert("44 = "+topObj.value);
	document.getElementById('outputID').value = topObj.value;//设置需要提交的值
	topObj.value = "";//清空临时保存的值
	var checkboxs = document.all.item("educe");
	for (i=0; i<checkboxs.length; i++) {//清除当页选中状态
		checkboxs[i].checked = false;
	}
}
function equalsCheckbox(){//比较值后显示是否选中

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
function setCheckbox(obj){//根据对象状态判断操作是选中还是取消
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
function setCheckbox(obj){//根据对象状态判断操作是选中还是取消
	if(obj.checked){
		setTopValue(obj.value);
	}else{
		clearTopValue(obj.value);
	}
}
</script>
