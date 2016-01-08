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
    //联动 大类--->小类
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
    //----------小类----
    function smallChange(obj){
    	if(document.getElementById("add_span_c").style.display!="none"){
    		document.all.smallType.value = obj.options[obj.selectedIndex].text;
    	}
    }

    //添加
    function addP(){
    	var pname=document.all.bigType.value;
    	if(pname!=""){
    		casetype.addBigType(pname,getBigTypeList_add);    	
    	}else{
    		alert('大类不能为空');
    	}    	
    }
    
    function addC(){
    	var pid=document.all.type1.value;
    	var cname=document.all.smallType.value;
    	if(pid!=""&&cname!=""){
    		var cname=document.all.smallType.value;    		
    		casetype.addSmallType(pid,cname,getSmallList_add);    		
    	}else if(pid==""){
    		alert('请选择大类');
    	}else if(cname==""){
    		alert('小类不能为空');
    	}
    		
    }
  	
  	function getBigTypeList_add(obj){
  		if(obj==null){
  			alert("添加失败！该大类已存在！");
  		}else{
  			getBigTypeList(obj);
  			alert('添加成功');    		
  		}
  		coverSpan('add','p');
  	}
    
    function getSmallList_add(obj){  	 	
	    if(obj==null){
	    	alert('添加失败，该小类已经存在');
	    }else{
	    	getSmallList(obj);
	    	alert('添加成功');    	
	    }
	    coverSpan('add','c');
    }
        
    //修改
    function editP(){    	
    	var pid=document.all.type1.value;
    	var pname=document.all.bigType.value;
    	if(pid!=""&&pname!=""){
    		casetype.updateBigType(pid,pname,getBigTypeList_edit);    	
    	}else if(pid==""){
    		alert('请选择要修改的大类');
    	}else if(pname==""){    		
    		alert('大类不能为空');
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
    		alert('请选择要修改的小类');
    	}else if(cname==""){
    		alert('小类不能为空');
    	}
    }
   
    function getBigTypeList_edit(obj){
     	getBigTypeList(obj);
	    if(obj==null){
	    	casetype.getBigType(getBigTypeList);
	    	alert('修改失败，请查看该大类是否已经存在');
	    }else{
	    	alert('修改成功');    	
	    }
	    coverSpan('add','p');
    }
    
    function getSmallList_edit(obj){     	
	    if(obj==null){
	    	alert('修改失败，请查看该小类是否已经存在');
	    }else{
	    	getSmallList(obj);
	    	alert('修改成功');    	
	    }
	    coverSpan('add','c');
    }
    
   
    //删除
    function delP(){	    
	    var pid=document.all.type1.value;    	
    	if(pid!=""){
    		if(confirm("请慎重使用删除功能，是否继续？")){
    			casetype.deleteBigType(pid,getBigTypeList_del);    	
    		}
    	}else{    		
    		alert('请选择要删除的大类');
    	}
    }
    
    function delC(){	   
	    var cid=document.all.type2.value;    	
    	if(cid!=""){
    		if(confirm("请慎重使用删除功能，是否继续？")){    		
    			casetype.deleteSmallType(cid,getSmallList_del);
    		}
    	}else{
    		alert('请选择要删除的小类');
    	}
    }
        
    function getBigTypeList_del(obj){
	    if(obj==0){
	    	casetype.getBigType(getBigTypeList);
	    	alert('删除成功!');	    	
	    }else if(obj==1){	    	
	    	alert('删除失败!');    	
	    }else if(obj==2){
	    	alert('删除失败！该大类不存在！');
	    }
	    
	    coverSpan('add','p');
    }
    
    function getSmallList_del(obj){    	
	    if(obj==0){	    	
	    	var pid=document.all.type1.value;    	
    		casetype.getSmallTypeByBigType(pid,getSmallList);	    	
	    	alert('删除成功!');	    	
	    }else if(obj==1){	    	
	    	alert('删除失败!');    	
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
	    	document.getElementById("but_p").value="添加大类";
	    	document.getElementById("oper_but_p").value="添加";
	    	document.getElementById("oper_but_p").onclick=function() {
				addP();
			};
			
			document.getElementById("but_c").value="添加小类";
	    	document.getElementById("oper_but_c").value="添加";
	    	document.getElementById("oper_but_c").onclick=function() {
				addC();
			};
    		    	
	    }else if(obj.id=="edit"){
	    	document.getElementById("but_p").value="修改大类";
	    	document.getElementById("oper_but_p").value="修改";
	    	document.getElementById("oper_but_p").onclick=function() {
				editP();
			};
			
			document.getElementById("but_c").value="修改小类";
	    	document.getElementById("oper_but_c").value="修改";
	    	document.getElementById("oper_but_c").onclick=function() {
				editC();
			};

	    }else if(obj.id=="del"){
	    	document.getElementById("but_p").value="删除大类";
	    	document.getElementById("oper_but_p").value="删除";
	    	document.getElementById("oper_but_p").onclick=function() {
				delP();
			};
			
			document.getElementById("but_c").value="删除小类";
	    	document.getElementById("oper_but_c").value="删除";
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
    	案列大类：
    	<html:select property="type1" onchange="bigChange(this)">
    	<html:option value="">--请选择--</html:option>
    	<html:optionsCollection name="bigtypelist"/>
    	</html:select>&nbsp;
     	案列小类：	
    	<html:select property="type2" onchange="smallChange(this)">
    	<html:option value="">--请选择--</html:option>
    	</html:select>
    	
    	<br><hr>
    	操作：
    	<input id="add" type="button" value="添加" onclick="choiceDiv(this);" >&nbsp;
    	<input id="edit" type="button" value="修改" onclick="choiceDiv(this);" >&nbsp;
    	<input id="del" type="button" value="删除" onclick="choiceDiv(this);" >&nbsp;
    	<input id="cancel" type="button" value="取消" onclick="choiceDiv(this);" >
    	<br><hr>
    	
    	<div id="div_add" style="display:none">
	    	<span id="span_add_but_p">
	    		<input id="but_p" type="button" value="添加大类" onclick="discoverSpan('add','p');" >
	    	</span>
	    	<span id="add_span_p" style="display:none">
		    	<html:text property="bigType"></html:text>		    	
		    	<input id="oper_but_p" type="button" value="添加" onclick="addP()" >
		    	<input type="button" value="取消" onclick="coverSpan('add','p');" >
	    	</span>
	    	<br>
	    	<span id="span_add_but_c">
	    		<input id="but_c" type="button" value="添加小类" onclick="discoverSpan('add','c');" >
	    	</span>
	    	<span id="add_span_c" style="display:none">
		    	<html:text property="smallType"></html:text>
		    	<input id="oper_but_c" type="button" value="添加" onclick="addC()" >
		    	<input type="button" value="取消" onclick="coverSpan('add','c');" >
	    	</span>
	    	<br>
    	</div>
    	
    </html:form>    
  </body>
</html:html>