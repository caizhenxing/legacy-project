<%@ page language="java" pageEncoding="gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />    
    <script type='text/javascript' src='/callcenterj_sy/dwr/interface/casetype.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
    <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
    
    <script type="text/javascript">
    //���� ����--->С��
    function bigChange(obj){   
    	var pid=obj.value;
    	document.all.type2.length=1;    	    	
    	if(pid!="")
    	casetype.getSmallTypeByBigType(pid,getSmallList);
    }
    
    function getSmallList(obj){
    	if(document.getElementById("add_span_p").style.display!="none"){
    		document.all.bigType.value =document.all.type1.value;
    	}
    	document.all.type2.length=1;
    	DWRUtil.addOptions(document.getElementById("type2"),obj);
    }
    //----------С��----
    function smallChange(obj){
    	if(document.getElementById("add_span_c").style.display!="none"){
    		document.all.smallType.value = obj.options[obj.selectedIndex].text;
    	}
    }

    //���
    function addP(){
    	var pname=document.all.bigType.value;
    	if(pname!=""){
    		casetype.addBigType(pname,getBigTypeList_add);    	
    	}else{
    		alert('���಻��Ϊ��');
    	}    	
    }
    
    function addC(){
    	var pid=document.all.type1.value;
    	var cname=document.all.smallType.value;
    	if(pid!=""&&cname!=""){
    		var cname=document.all.smallType.value;    		
    		casetype.addSmallType(pid,cname,getSmallList_add);    		
    	}else if(pid==""){
    		alert('��ѡ�����');
    	}else if(cname==""){
    		alert('С�಻��Ϊ��');
    	}
    		
    }
  	
  	function getBigTypeList_add(obj){
  		if(obj==null){
  			alert("���ʧ�ܣ��ô����Ѵ��ڣ�");
  		}else{
  			getBigTypeList(obj);
  			alert('��ӳɹ�');    		
  		}
  		coverSpan('add','p');
  	}
    
    function getSmallList_add(obj){  	 	
	    if(obj==null){
	    	alert('���ʧ�ܣ���С���Ѿ�����');
	    }else{
	    	getSmallList(obj);
	    	alert('��ӳɹ�');    	
	    }
	    coverSpan('add','c');
    }
        
    //�޸�
    function editP(){    	
    	var pid=document.all.type1.value;
    	var pname=document.all.bigType.value;
    	if(pid!=""&&pname!=""){
    		casetype.updateBigType(pid,pname,getBigTypeList_edit);    	
    	}else if(pid==""){
    		alert('��ѡ��Ҫ�޸ĵĴ���');
    	}else if(pname==""){    		
    		alert('���಻��Ϊ��');
    	}
    }
    
    function editC(){	   
	    var v_bigtype=document.all.type1.value;
	    var cid=document.all.type2.value;
    	var cname=document.all.smallType.value;
    	if(cid!=""&&cname!=""){
    		var cname=document.all.smallType.value;    		
    		casetype.updateSmallType(cid,v_bigtype,cname,getSmallList_edit);    		
    	}else if(cid==""){
    		alert('��ѡ��Ҫ�޸ĵ�С��');
    	}else if(cname==""){
    		alert('С�಻��Ϊ��');
    	}
    }
   
    function getBigTypeList_edit(obj){
     	getBigTypeList(obj);
	    if(obj==null){
	    	casetype.getBigType(getBigTypeList);
	    	alert('�޸�ʧ�ܣ���鿴�ô����Ƿ��Ѿ�����');
	    }else{
	    	alert('�޸ĳɹ�');    	
	    }
	    coverSpan('add','p');
    }
    
    function getSmallList_edit(obj){     	
	    if(obj==null){
	    	alert('�޸�ʧ�ܣ���鿴��С���Ƿ��Ѿ�����');
	    }else{
	    	getSmallList(obj);
	    	alert('�޸ĳɹ�');    	
	    }
	    coverSpan('add','c');
    }
    
   
    //ɾ��
    function delP(){	    
	    var pid=document.all.type1.value;    	
    	if(pid!=""){
    		if(confirm("������ʹ��ɾ�����ܣ��Ƿ������")){
    			casetype.deleteBigType(pid,getBigTypeList_del);    	
    		}
    	}else{    		
    		alert('��ѡ��Ҫɾ���Ĵ���');
    	}
    }
    
    function delC(){	   
	    var cid=document.all.type2.value;    	
    	if(cid!=""){
    		if(confirm("������ʹ��ɾ�����ܣ��Ƿ������")){    		
    			casetype.deleteSmallType(cid,getSmallList_del);
    		}
    	}else{
    		alert('��ѡ��Ҫɾ����С��');
    	}
    }
        
    function getBigTypeList_del(obj){
	    if(obj==0){
	    	casetype.getBigType(getBigTypeList);
	    	alert('ɾ���ɹ�!');	    	
	    }else if(obj==1){	    	
	    	alert('ɾ��ʧ��!');    	
	    }else if(obj==2){
	    	alert('ɾ��ʧ�ܣ��ô��಻���ڣ�');
	    }
	    
	    coverSpan('add','p');
    }
    
    function getSmallList_del(obj){    	
	    if(obj==0){	    	
	    	var pid=document.all.type1.value;    	
    		casetype.getSmallTypeByBigType(pid,getSmallList);	    	
	    	alert('ɾ���ɹ�!');	    	
	    }else if(obj==1){	    	
	    	alert('ɾ��ʧ��!');    	
	    }
	    coverSpan('add','c');
    }
    
    //------------------------    
    function getBigTypeList(obj){		
   		document.all.type1.length=1;
    	document.all.type2.length=1; 
    	document.all.type2.options[0].selected=true;   	
    	DWRUtil.addOptions(document.getElementById("type1"),obj);    	
    }
    
    //-----------------------
    function choiceDiv(obj){
		document.getElementById("div_add").style.display="";
	    if(obj.id=="add"){	    	
	    	document.getElementById("but_p").value="��Ӵ���";
	    	document.getElementById("oper_but_p").value="���";
	    	document.getElementById("oper_but_p").onclick=function() {
				addP();
			};
			
			document.getElementById("but_c").value="���С��";
	    	document.getElementById("oper_but_c").value="���";
	    	document.getElementById("oper_but_c").onclick=function() {
				addC();
			};
    		    	
	    }else if(obj.id=="edit"){
	    	document.getElementById("but_p").value="�޸Ĵ���";
	    	document.getElementById("oper_but_p").value="�޸�";
	    	document.getElementById("oper_but_p").onclick=function() {
				editP();
			};
			
			document.getElementById("but_c").value="�޸�С��";
	    	document.getElementById("oper_but_c").value="�޸�";
	    	document.getElementById("oper_but_c").onclick=function() {
				editC();
			};

	    }else if(obj.id=="del"){
	    	document.getElementById("but_p").value="ɾ������";
	    	document.getElementById("oper_but_p").value="ɾ��";
	    	document.getElementById("oper_but_p").onclick=function() {
				delP();
			};
			
			document.getElementById("but_c").value="ɾ��С��";
	    	document.getElementById("oper_but_c").value="ɾ��";
	    	document.getElementById("oper_but_c").onclick=function() {
				delC();
			};
		
	    }else{
	    	document.getElementById("div_add").style.display="none";
	    	coverSpan("add","p");
	    	coverSpan("add","c");
	    }
    }
    
   //---------------------------------------------   
    function discoverSpan(oper,tar){
    	document.getElementById("span_"+oper+"_but_"+tar).style.display="none";
    	document.getElementById(oper+"_span_"+tar).style.display="";
    	if(tar=='p'&&document.all.type1.value!=""){
    		document.all.bigType.value=document.all.type1.value
    	}else if(tar=='c'&&document.all.type2.value!=""){    		
    		var obj = document.all.type2;
    		document.all.smallType.value=obj.options[obj.selectedIndex].text;
    	}
    }
    
    function coverSpan(oper,tar){
    	document.getElementById("span_"+oper+"_but_"+tar).style.display="";
    	document.getElementById(oper+"_span_"+tar).style.display="none";
    	if(tar=='p'){
    	document.forms[0].bigType.value="";
    	}else if(tar=='c'){
    	document.forms[0].smallType.value="";
    	}
    }
    
    </script>
    <title>casetype.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
  </head>
   
  <body>
    <html:form action="/caseinfo/caseType">
    	���д��ࣺ
    	<html:select property="type1" onchange="bigChange(this)">
    	<html:option value="">--��ѡ��--</html:option>
    	<html:optionsCollection name="bigtypelist"/>
    	</html:select>&nbsp;
     	����С�ࣺ	
    	<html:select property="type2" onchange="smallChange(this)">
    	<html:option value="">--��ѡ��--</html:option>
    	</html:select>
    	
    	<br><hr>
    	������
    	<input id="add" type="button" value="���" onclick="choiceDiv(this);" >&nbsp;
    	<input id="edit" type="button" value="�޸�" onclick="choiceDiv(this);" >&nbsp;
    	<input id="del" type="button" value="ɾ��" onclick="choiceDiv(this);" >&nbsp;
    	<input id="cancel" type="button" value="ȡ��" onclick="choiceDiv(this);" >
    	<br><hr>
    	
    	<div id="div_add" style="display:none">
	    	<span id="span_add_but_p">
	    		<input id="but_p" type="button" value="��Ӵ���" onclick="discoverSpan('add','p');" >
	    	</span>
	    	<span id="add_span_p" style="display:none">
		    	<html:text property="bigType"></html:text>		    	
		    	<input id="oper_but_p" type="button" value="���" onclick="addP()" >
		    	<input type="button" value="ȡ��" onclick="coverSpan('add','p');" >
	    	</span>
	    	<br>
	    	<span id="span_add_but_c">
	    		<input id="but_c" type="button" value="���С��" onclick="discoverSpan('add','c');" >
	    	</span>
	    	<span id="add_span_c" style="display:none">
		    	<html:text property="smallType"></html:text>
		    	<input id="oper_but_c" type="button" value="���" onclick="addC()" >
		    	<input type="button" value="ȡ��" onclick="coverSpan('add','c');" >
	    	</span>
	    	<br>
    	</div>
    	
    </html:form>    
  </body>
</html:html>