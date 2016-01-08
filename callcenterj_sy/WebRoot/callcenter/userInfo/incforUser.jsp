<%@ page contentType="text/html; charset=gbk"%>
<%
	String p = request.getServletPath();
	String l = "";
	if(p.indexOf("caseinfo")!=-1){
		l = "../../output.do?method=toOutputFile";
	}else{
		l = "../output.do?method=toOutputFile";
	}
%>
<tr style="display:none">
	<form action="/callcenter/userInfo.do?method=toCustinfoMain" method="post" name="userInfoBean" id="userInfoBean" target="_blank">
		<td colspan="7" class="labelStyle" align="right">
			<input name="outputID" type="hidden" id="outputID" value="">
			<input name="Submit4" type="Button" id="Submit4" onClick="clearTopValues()"
				value="提交"  />
<%--			<input name="Submit2" type="button" id="Submit2"--%>
<%--				onClick="clearAllCheckBox('educe')" value="取消全选" />--%>
			
		</td>
	</form>
</tr>
<script>

function clearTopValues(){//当点击“提交”后清除全部值

	var topObj = window.parent.frames.item("bottomm").document.getElementById('outputID');
	document.getElementById('outputID').value = topObj.value;//设置需要提交的值
	//alert(topObj.value);
	if(topObj.value!=0)
	{	//txtOutLine handInputOutLine
		parent.opener.document.getElementById('handInputOutLine').value=topObj.value;
		//parent.opener.document.getElementById('execBtnOutCall').click();
		parent.window.close();
	}
	else
	{
		alert('电话号不能为空!');
	}
	topObj.value = "";//清空临时保存的值
	var checkboxs = document.all.item("educe");
	for (i=0; i<checkboxs.length; i++) {
		checkboxs[i].checked = false;
	}
}


function selAllCheckBox(checkboxname){//全选方法
	var checkboxs = document.all.item(checkboxname);
	var values = "";
	if (checkboxs!=null) {
		for (i=0; i<checkboxs.length; i++) {
			checkboxs[i].checked = true;
			setTopValue(checkboxs[i].value);
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
	
	var topObj = window.parent.frames.item("bottomm").document.getElementById('outputID');
	var topValue = topObj.value;
	
<%--	if(topValue != ""){--%>
<%--		topValue += "," + v;--%>
<%--	}else{--%>
<%--		topValue = v;--%>
<%--	}--%>
	//alert(v);
	topValue = v;
	topObj.value = topValue;
}
function clearTopValue(v){//清除某值
	
	var topObj = window.parent.parent.frames.item("topFrame").document.getElementById('outputID');
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

function equalsCheckbox(){//比较值后显示是否选中

	var topObj = window.parent.parent.frames.item("topFrame").document.getElementById('outputID');
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
	}
	else{
		clearTopValue(obj.value);
	}
}

</script>
