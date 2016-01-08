<%@ page language="java" contentType="text/html;charset=GB2312" %>

<html>
<jsp:directive.include file="/base/default.jspf"/>
<body class="main_body">
<form name="f" action="" method="post">
<input type="hidden" name="oid" value="<c:out value='${oid}'/>"/>
<c:set var="parent" value="${entityObject.parent}"/>
<div class="update_subhead">
	<span class="switch_open" onClick="StyleControl.switchDiv(this,$('parenttable'))" title="�����ڵ�">������Ϣ</span>
</div>
<table border="0" cellpadding="0" cellspacing="0" class="Detail" id="parenttable">
	<tr>
		
		<td class="attribute">�ϼ�����</td>
		<td><input name="dummy.parent.name" value="<c:out value='${parent.name}'/>"class="readonly" readonly="readonly"/>
		</td>
		<td class="attribute">��������</td>
		<td><input bisname="��������" maxlength="50" name="entityObject.name" value="<c:out value='${entityObject.name}'/>"/>
		<span class="font_request">*</span>&nbsp;
		</td>			
	</tr>														
	<tr>
		 
	</tr>
	
</table>
	
<script type="text/javascript">
var msgInfo_ = new msgInfo();
	if (CurrentPage == null) {
		var CurrentPage = {};
	}
		
	CurrentPage.reset = function () {
		document.f.reset();
	}
		
	CurrentPage.submit = function () {
		if (!CurrentPage.validation()) {
			return;
		}
		if(!verifyAllform()){return false;}
		<%--all ��֤--%>
		if(CurrentPage.ValidateAll()==false){
			return false;
		}
		if (CurrentPage.CheckNum_expectPerson(document.getElementById('bean.expectPerson')) == false) {
	    	return false;
		}
		if (CurrentPage.CheckNum_showSequence(document.getElementById('bean.showSequence')) == false) {
	    	return false;
		}
		FormUtils.post(document.forms[0], '<c:url value='/hr/TblHrDeptAction.do'/>');
	}
	CurrentPage.validation = function () {
		return Validator.Validate(document.forms[0],5);
	}
	
	CurrentPage.initValideInput = function () {
		document.getElementById('bean.deptCd').dataType = 'Require';
		document.getElementById('bean.deptCd').msg =  msgInfo_.getMsgById('HR_I068_C_1',['���ű���']);
		document.getElementById('bean.deptName').dataType = 'Require';
		document.getElementById('bean.deptName').msg =  msgInfo_.getMsgById('HR_I068_C_1',['��������']);
		document.getElementById('bean.expectPerson').dataType = 'Integer';
		document.getElementById('bean.expectPerson').msg =  msgInfo_.getMsgById('HR_I069_C_1',['���Ŷ�Ա����']);
		document.getElementById('bean.showSequence').dataType = 'Integer';
		document.getElementById('bean.showSequence').msg = msgInfo_.getMsgById('HR_I069_C_1',['��ʾ˳��']);
	}
	
	CurrentPage.initValideInput();
	
	CurrentPage.ValidateAll = function () {
    	var success=true;
    <%--
    if ($('bean.webAdress').value!=""){
    	if (CurrentPage.CheckURL($('bean.webAdress').value)==false)
           {
             Validator.warnMessage ( '��վ��ַ��ʽ����'); 
             success=false;
           } 
	}
	--%>
	<%--
  	if ($('bean.email').value!=""){ 
    	if (CurrentPage.CheckEmail($('bean.email').value)==false)
           {
             Validator.warnMessage (msgInfo_.getMsgById('HR_I078_C_1',['��������'])); 
             success=false;
           } 
   }
   if($('bean.telephone').value!=""){
   		if (CurrentPage.CheckPhone($('bean.telephone').value)==false)
           {
             Validator.warnMessage ( msgInfo_.getMsgById('HR_I077_C_1',['��ϵ�绰'])); 
             success=false;
           }
   }
   if($('bean.fax').value!=""){
   		if (CurrentPage.CheckPhone($('bean.fax').value)==false){
             Validator.warnMessage ( msgInfo_.getMsgById('HR_I079_C_1',['����'])); 
             success=false;
        }
   	}
   	--%>
   	return success;
}
	
	
	
	CurrentPage.create = function() {
		$('bean.deptCd').value = '';
		$('oid').value = '';
		$('step').value = 'edit';
		FormUtils.post(document.forms[0], '<c:url value='/hr/TblHrDeptAction.do'/>');
	}

	CurrentPage.CheckNum_expectPerson = function(name){
		var str=name.value
        if (!isNaN(str)||!str=="")
     	{
			var number = parseInt(str);	
			if (number >= 0 && number <= 10000 || str=="")
			{
				return true;
			}else{
				Validator.warnMessage(msgInfo_.getMsgById('HR_I071_C_2',['���Ŷ�Ա����','0~10000']));
				name.focus();
         		return false;
         	}
      	}else{	
 			return false;  		
 	  	}
	}
	
	CurrentPage.CheckNum_showSequence = function(name){
		var str=name.value
        if (!isNaN(str)||!str=="")
     	{
			var number = parseInt(str);	
			if (number >= 0 && number <= 1000 || str=="")
			{
				return true;
			}else{
				Validator.warnMessage(msgInfo_.getMsgById('HR_I071_C_2',['��ʾ˳��','0~1000']));
				name.focus();
         		return false;
         	}
      	}else{	
 			return false;  		
 	  	}
	}
	function Validator.afterSuccessMessage(){
		if(parent.codeTree){
			parent.codeTree.Update(parent.codeTree.SelectId);
		}
	}
	<%--������ѡ��--%>
	CurrentPage.selectEmp = function(empInfoPre){
		definedWin.openListingUrl(empInfoPre,'<c:url value='/hr/SelectInfoAction.do?step=selectEmpInfo'/>');
	}
</script>
</form>
</body>
</html>
