<%@ page language="java"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />   
    
    <title>产品类别信息维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	<!-- 引入dwr -->	
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/addressMaintenanceService.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/productTypeService.js'></script>
 	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
  	 <script type="text/javascript">
  	 var type = ''; //1 2 3 大类 小类 名称
  	 var operType = ''; // add update delete
  	 var operCount = 0; //计数器
  	 function addValues(objId,values)
  	 {
  	 	var slt = document.getElementById(objId);
			if(slt)
			{
				var length = slt.length;
				if(length>0)
				{
					for(var i=1; i<length; i++)
					{
						slt.options[1] = null;
					}
				}
				var sArr = values.split("#");
				for(var i=0; i<sArr.length; i++)
				{
					slt.options[slt.length] = new Option(sArr[i], sArr[i]);
				}
			}
  	 }
  	 
  	 
	function setName1(values)
	{
		if(values!='')
		{
			addValues('citys',values);
		}
	}
    function setName2(values)
	{
		if(values!='')
		{
			addValues('areas',values);
		}
	}
	function getAllName2()
	{
		var name1 = document.getElementById('citys').value;
		if(name1 != 0)
		{
			productTypeService.getAllName2(name1,setName2);
		}
		else
		{
			alert('请选择产品大类');
		}
	}
	function setName3(values)
	{
		if(values!='')
		{
			addValues('villagesAtowns',values);
		}
	}
	//名称
	function getAllName3()
	{
		var name2 = document.getElementById('areas').value;

		if(name2 != 0)
		{
			productTypeService.getAllName3(name2,setName3);
		}
		else
		{
			alert('请选择产品小类');
		}
	}
	
	//cityName areaName villagesAtownsName communityAvillage
	function clearItem(ids)
	{
		if(ids!=0)
		{
			var idarr = ids.split("#");
			for(var i=0; i<idarr.length; i++)
			{
				var id = ids[i];
				var obj = document.getElementById(id);
				if(obj)
				{
					obj.value = "";
				}
			}
		}
	}
	function addOper(obj)
	{
		getObj('subOperType').style.display="block";
		operType='add';
		setValue(obj);
	}
	function updateOper(obj)
	{
		getObj('subOperType').style.display="block";
		operType='update';
		setValue(obj);
	}
	function deleteOper(obj)
    {
    	getObj('subOperType').style.display="block";
    	operType = 'delete';
    	setValue(obj);
    }
    
     function setValue(obj)
    {
    	if(obj)
    	{
    		var v = obj.value;
    		var elems = document.getElementsByName('childOper');
    		for(var i=0; i<elems.length; i++)
    		{
    			if(operCount==0)
    				elems[i].value=v+elems[i].value;
    			else
    			{
    				elems[i].value=v+elems[i].value.substring(2);
    			}
    		}
    		operCount++;
    	}
    }
    
    function getObj(id)
    {
    	return document.getElementById(id);
    }
 //////////////////////////////////////////////////////////////   
    
    function getNum(type,num) //增加时return num 其它 num-1
    {
    	if(type=='add')
    	{
    		return num-1;
    	}
    	return num;
    }
    function changeType(obj)
    {
    	var v = obj.value;
    	if(v.indexOf('产品大类')!=-1)
    	{
    		type="1";
  			var num = getNum(operType,1);
    		nonNullLevel(num);
    	}
    	else if(v.indexOf('产品小类')!=-1)
    	{
    		type='2';
    		var num = getNum(operType,2);
    		nonNullLevel(num);
    	}
    	else if(v.indexOf('产品名称')!=-1)
    	{
    		type='3';
    		var num = getNum(operType,3);
    		nonNullLevel(num);
    	}
    	document.getElementById('operType').innerHTML=v;
    }
    //验证表单是否合理
    function checkCondtion()
    {
    	var v = getObj('operType').innerHTML+'';
    	var count = 0;
    	if(v.indexOf('产品大类')!=-1)
    	{
  			var num = getNum(operType,1);
    		count = nonNullLevel(num);
    	}
    	else if(v.indexOf('产品小类')!=-1)
    	{
    		var num = getNum(operType,2);
    		count = nonNullLevel(num);
    	}
    	else if(v.indexOf('产品名称')!=-1)
    	{
    		var num = getNum(operType,3);
    		count = nonNullLevel(num);
    	}
    	return count;
    }
   
    function execOper()
    {
    	var count = checkCondtion();
    	if(count>0)
    	{
    		return;
    	}
    	else
    	{	var temp = getObj('operType').innerHTML;
    		if(temp==0)
    		{
    			alert('请选择操作类型');
    			return;
    		}
    		if(!confirm(temp))
    		{
    			return;
    		}
    	
	    	var operName=getObj('operName').value;
	    	if(operName==0)
	    	{
	    		alert('请填写名称');
	    		return;
	    	}
	    	//var operNum=getObj('operNum').value;
	    	var operNum='';
	    	var cityName=getObj('citys').value;
	    	var areaName=getObj('areas').value;
	    	var townName = getObj('villagesAtowns').value;
	
	    	var addType = document.getElementById('operType').innerHTML;
	    	//alert(type+':'+operType);
	    	//var type = ''; //1 2 3  大类 小类 名称 
	  	 	//var operType = ''; // add update delete
	  	 	if('add'==operType)
	  	 	{
	  	 		if(type==1)
	  	 		{
	  	 			//增加产品大类
	  	 			productTypeService.addName1(operName);
	  	 		}
	  	 		if(type==2)
	  	 		{
	  	 			//增加产品小类
	  	 			productTypeService.addName2(cityName,operName);
	  	 		}
	  	 		if(type==3)
	  	 		{
	  	 			//增加产品名称
	  	 			productTypeService.addName3(cityName,areaName,operName);
	  	 		}
	  	 	}
	  	 	else if('update'==operType)
	  	 	{
	  	 		if(type==1)
	  	 		{
	  	 			productTypeService.updateName1(operName,cityName);
	  	 		}
	  	 		if(type==2)
	  	 		{
	  	 			productTypeService.updateName2(cityName, operName,areaName);
	  	 		}
	  	 		if(type==3)
	  	 		{
	  	 			productTypeService.updateName3(cityName,areaName, operName,townName );
	  	 		}
	  	 	}
	  	 	else if('delete'==operType)
	  	 	{
	  	 	
	  	 		
	  	 		if(type==1)
	  	 		{
	  	 			productTypeService.deleteName1(cityName);
	  	 			
	  	 		}
	  	 		if(type==2)
	  	 		{
	  	 			productTypeService.deleteName2(cityName,areaName);
	  	 		}
	  	 		if(type==3)
	  	 		{
	  	 			productTypeService.deleteName3(cityName,areaName,townName);
	  	 		}
	  	 	}
  	 	}
    }
    function nonNullLevel(num) //多少个不能为空
    {
    	//citys areas villagesAtowns communityAvillage
    	var err = 0;
    	var count = 0;
    	if(count==0&&count<num)
    	{
    		count++;
    		var v = getObj('citys').value;
    		if(v==0)
    		{
    			alert('请选择产品大类');
    			err++;
    		}
    	}
    	if(count==1&&count<num)
    	{
    		count++;
    		var v = getObj('areas').value;
    		if(v==0)
    		{
    			alert('请选择产品小类');
    			err++;
    		}
    	}
    	if(count==2&&count<num)
    	{
    		count++;
    		var v = getObj('villagesAtowns').value;
    		if(v==0)
    		{
    			alert('请选择产品名称');
    			err++;
    		}
    	}
    	return err;
    }
        function changeValue(obj,id)
    {
    	if(obj.value!='0')
    	{
    		document.getElementById(id).value=obj.value;
    	}
    }
    </script>
  </head>
  
  <body>
    	<table>
    	<tr>
    	<td> 
    		产品大类:<select name="textfield10" id="citys" onmouseover="productTypeService.getAllName(setName1)" onchange="changeValue(this,'operName')" style="width:104px;">
        			<option value="0">选择产品大类</option>
        		</select>
        </td>
        <td>
    		产品小类:<select name="textfield10" id="areas" onmouseover="getAllName2()" onchange="changeValue(this,'operName')" style="width:104px;">
        			<option value="0">选择产品小类</option>
        		</select>
        </td>
        <td>
    		产品名称:<select name="textfield10" id="villagesAtowns" onmouseover="getAllName3()"  onchange="changeValue(this,'operName')" style="width:104px;">
        			<option value="0">选择产品名称</option>
        		</select>
        </td>
       
        <td>
        <input type="button" value="增加" onclick="addOper(this)" />
        <input type="button" value="修改" onclick="updateOper(this)" />
        <input type="button" value="删除" onclick="deleteOper(this)" />
        </td>
        </tr>
        </table>
        <table id="subOperType" style="display:none;">
        <tr>
           <td class="labelStyle">
              <input type="button" name="childOper" value="产品大类" onclick="changeType(this)" />
              <input type="button" name="childOper" value="产品小类" onclick="changeType(this)" />
              <input type="button" name="childOper" value="产品名称" onclick="changeType(this)" />
<%--              <input type="button" name="childOper" value="社区/村" onclick="changeType(this)" />--%>
           </td>
          </tr>
        </table>
         <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable"> 
          <tr>
          <td class="labelStyle" >
          当前操作
          </td>
          <td class="valueStyle" id="operType">
          </td>
          </tr>
          <tr>
           <td class="labelStyle">
              名称
           </td>
           <td class="valueStyle">
            <input type="text" id="operName" value="" />
	       </td>
          </tr>
  
<%--          <tr>--%>
<%--           <td class="labelStyle">--%>
<%--              编码--%>
<%--           </td>--%>
<%--           <td class="valueStyle">--%>
<%--            <input type="text" id="operNum" />--%>
<%--	       </td>--%>
<%--          </tr>--%>
          <tr>
           <td class="labelStyle">

           </td>
           <td class="valueStyle">
            <input type="button" value="确定" onclick="execOper()" />
	       </td>
          </tr>
        </table>
  </body>
</html:html>
