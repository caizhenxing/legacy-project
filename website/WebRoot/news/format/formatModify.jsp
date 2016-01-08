<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="GB2312"%>
<%@include file="../include/include.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<link href="<%=path%>back/style_mid.css" rel="stylesheet" type="text/css">
   <style type=text/css>
<!--
.style4 {font-size: 14pt}
.style5 {font-size: 14}
.style6 {font-size: 16px}
.style7 {font-size: 10pt}
.style8 {font-size: 12pt}
-->
   </style>
<head>
    <html:base />
    <title>新闻格式维护</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

   <link href="Admin_Style.css" rel="stylesheet" type="text/css">
   
    <script language="javascript">
    	//选择条件相符合的类别信息
    	function selectTypeInfo(){
    		var classId = document.forms[0].classId.value;
    		document.forms[0].action = "../formatOrder.do?method=formatList&classId="+ classId;
    		document.forms[0].submit();
    	}
    	
    	//选择显示方式
    	function selectShowType(){
    		var showType = document.forms[0].showType.value;
    		if(showType == "2"){
    			document.forms[0].showProperty.disabled = true;
    			document.forms[0].showTile.disabled = true;
    			document.forms[0].author.disabled = true;
    			document.forms[0].clickNum.disabled = true;
    			document.forms[0].showDate.disabled = true;
    		}
    		if(showType == "3"){
    			document.forms[0].showProperty.disabled = true;
    			document.forms[0].showTile.disabled = true;
    			document.forms[0].author.disabled = true;
    			document.forms[0].clickNum.disabled = true;
    			document.forms[0].showDate.disabled = true;
    			document.forms[0].showMore.disabled = true;
    		}
			if(showType == "1"){
    			document.forms[0].showProperty.disabled = false;
    			document.forms[0].showTile.disabled = false;
    			document.forms[0].author.disabled = false;
    			document.forms[0].clickNum.disabled = false;
    			document.forms[0].showDate.disabled = false;
    			document.forms[0].showMore.disabled = false;
    		}
    	}
    </script>
</head>

  <body>
   <html:form action="formatOrder.do?method=formatOrder" method="post">
     <div align="center">
       <table width="691" border="0" class="table">
         <tr>
           <td width="168" class="td1"><div align="center" class="style6">所属样式：</div></td>
           <td width="507" class="td1"><div align="left">
           <bean:write name='formatInfo' property='formatType'/> </div></td>
         </tr>
         
         <tr>
           <td class="td1"><div align="center"><span class="style4"><span class="style5">样式代号：</span></span></div></td>
           <td class="td1"><div align="left">
                   <html:hidden name='formatInfo' property='classId' write="true"/>
               </div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style6">文章数目：</div></td>
           <td class="td1">
             <div align="left">
               <input type="text" name="articleNum" value="<bean:write name='formatInfo' property='articleNum'/>" size="5">
             </div></td></tr>
         <tr>
           <td class="td1"><div align="center" class="style6">显示类型：</div></td>
           <td class="td1">
             <div align="left">
               <select name="showType" onChange="selectShowType()">
	             <logic:equal name='formatInfo' property='showType' value="1" scope="request">
	             	<option value="<bean:write name='formatInfo' property='showType'/>" selected>文章标题列表</option>
	                <option value="2">文章标题+部分内容</option>
			        <option value="3">小图片+文章标题</option>
	             </logic:equal>
	             <logic:equal name='formatInfo' property='showType' value="2" scope="request">
	                 <option value="<bean:write name='formatInfo' property='showType'/>" selected>文章标题+部分内容</option>
	                 <option value="1" selected>文章标题列表</option>
	                 <option value="3">小图片+文章标题</option>
	             </logic:equal>
	             <logic:equal name='formatInfo' property='showType' value="3" scope="request">
	                 <option value="<bean:write name='formatInfo' property='showType'/>" selected>小图片+文章标题</option>
	                 <option value="1" selected>文章标题列表</option>
	                 <option value="2">文章标题+部分内容</option>
	             </logic:equal>
               </select>
           </div></td>
         </tr>
         <tr>
           <td height="68" class="td1"><div align="center" class="style6">显示内容：</div></td>
           <td class="td1"><div align="left"><p>
             <input type="checkbox" name="showProperty" value="true" <logic:equal name='formatInfo' property='showProperty' value="true" scope="request">checked</logic:equal> />
             <span class="style7">文章属性</span>             
             <input type="checkbox" name="showTile" value="true" <logic:equal name='formatInfo' property='showTile' value="true" scope="request">checked</logic:equal> />
             文章标题
             <input type="checkbox" name="author" value="true" <logic:equal name='formatInfo' property='author' value="true" scope="request">checked</logic:equal> />
作 者           </p>
             <p>      <input type="checkbox" name="clickNum" value="true" <logic:equal name='formatInfo' property='clickNum' value="true" scope="request">checked</logic:equal> />
               点击率
                 <input type="checkbox" name="showDate" value="true" <logic:equal name='formatInfo' property='showDate' value="true" scope="request">checked</logic:equal> />
               更新时间
               <input type="checkbox" name="showMore" value="true" <logic:equal name='formatInfo' property='showMore' value="true" scope="request">checked</logic:equal> /> 
           “更多……”字样             </p></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style4">标题最多字符数：</div></td>
           <td class="td1"><div align="left">
             <input type="text" name="showTileMax" value="<bean:write name='formatInfo' property='showTileMax'/>" size="5">
           &nbsp;<font color="#FF0000">如果为空，则显示完整标题。字母算一个字符汉字算两个字符。</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">标题样式：</div></td>
           <td class="td1"><div align="left">颜 色 ：
               <select name="titleFontColor" id="titleFontColor" size="1">
                 <option value="<bean:write name='formatInfo' property='titleFontColor'/>" style="background-color:<bean:write name='formatInfo' property='titleFontColor'/>" selected></option>
                 <option value="#000000" style="background-color:#000000"></option>
                 <option value="#FFFFFF" style="background-color:#FFFFFF"></option>
                 <option value="#008000" style="background-color:#008000"></option>
                 <option value="#800000" style="background-color:#800000"></option>
                 <option value="#808000" style="background-color:#808000"></option>
                 <option value="#000080" style="background-color:#000080"></option>
                 <option value="#800080" style="background-color:#800080"></option>
                 <option value="#808080" style="background-color:#808080"></option>
                 <option value="#FFFF00" style="background-color:#FFFF00"></option>
                 <option value="#00FF00" style="background-color:#00FF00"></option>
                 <option value="#00FFFF" style="background-color:#00FFFF"></option>
                 <option value="#FF00FF" style="background-color:#FF00FF"></option>
                 <option value="#FF0000" style="background-color:#FF0000"></option>
                 <option value="#0000FF" style="background-color:#0000FF"></option>
                 <option value="#008080" style="background-color:#008080"></option>
             </select>
&nbsp;&nbsp;字 型：
<select name="titleFontType">
                 
            <logic:equal name='formatInfo' property='titleFontType' value="1" scope="request">
            <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     粗体</option>
   		     <option value="2">斜体</option>
               <option value="3">粗+斜</option>
	        </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontType' value="2" scope="request">
   		     <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     斜体</option>
   		     <option value="2">粗体</option>
                <option value="3">粗+斜</option>
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontType' value="3" scope="request">
   		     <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     粗+斜</option>
   		     <option value="2">粗体</option>
                <option value="1">斜体</option>
   		     </logic:equal>
            </select>
&nbsp;&nbsp;字大小：
<select name="titleFontSize">
             <option value="<bean:write name='formatInfo' property='titleFontSize'/>" selected>
             <logic:equal name='formatInfo' property='titleFontSize' value="10px" scope="request">
   		     10
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="12px" scope="request">
   		     12
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="14px" scope="request">
   		     14
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="16px" scope="request">
   		     16
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="18px" scope="request">
   		     18
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="24px" scope="request">
   		     24
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="36px" scope="request">
   		     36
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="xx-small" scope="request">
   		     极小
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="x-small" scope="request">
   		     特小
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="small" scope="request">
   		     小
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="medium" scope="request">
   		     中
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="large" scope="request">
   		     大
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="x-large" scope="request">
   		     特大
   		     </logic:equal></option>
             <option value="10px">10</option>
             <option value="12px">12</option>
             <option value="14px">14</option>
		     <option value="16px">16</option>
		     <option value="18px">18</option>
		     <option value="24px">24</option>
		     <option value="36px">36</option>
		     <option value="xx-small">极小</option>
		     <option value="x-small">特小</option>
		     <option value="small">小</option>
		     <option value="medium">中</option>
		     <option value="large">大</option>
		     <option value="x-large">特大</option>
           </select>
           </div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">文章内容字符数:</div></td>
           <td class="td1"><div align="left">
             <input type="text" name="contextNum" value="<bean:write name='formatInfo' property='contextNum'/>" size="5">
           &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">只有当显示类型设为“标题+内容”时才有效</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">日期范围:</div></td>
           <td class="td1"><div align="left">只显示最近
             <input type="text" name="orderDate" value="<bean:write name='formatInfo' property='orderDate'/>" size="5">
           天内的文章&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">如果为空，则显示所有天数的文章</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">排序字段：</div></td>
           <td class="td1">
             <div align="left">
          <select name="showOrderColumn">
               
           	<logic:equal name='formatInfo' property='showOrderColumn' value="NEWS_ID" scope="request">
       		    <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       			    文章ID
       		    </option>
       		    <option value="LAST_DATE">更新时间</option>
       		    <option value="CLICK_NUM">点击次数</option>
       	    </logic:equal>
       	     <logic:equal name='formatInfo' property='showOrderColumn' value="LAST_DATE" scope="request">
       		     <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       			     更新时间
       		     </option>
       		     <option value="NEWS_ID">文章ID</option>
       		     <option value="CLICK_NUM">点击次数</option>
       	     </logic:equal>
       	     <logic:equal name='formatInfo' property='showOrderColumn' value="CLICK_NUM" scope="request">
       		     <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       		     点击次数
       		     </option>
       		     <option value="NEWS_ID">文章ID</option>
       		     <option value="LAST_DATE">更新时间</option>
       	     </logic:equal>
			    
      </select>
        &nbsp;</div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">排序方法：</div></td>
           <td class="td1"><div align="left">
          <select name="showOrderType">
            <option value="<bean:write name='formatInfo' property='showOrderType'/>" selected>
       	      <logic:equal name='formatInfo' property='showOrderType' value="asc" scope="request">
       		      升序
       	      </logic:equal>
       	      <logic:equal name='formatInfo' property='showOrderType' value="desc" scope="request">
       		      降序
       	      </logic:equal>
            </option>
            <option value="asc">升序</option>
            <option value="desc" selected>降序</option>
          </select>
        &nbsp;</div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center"></div></td>
           <td class="td1">　</td>
         </tr>
         <tr>
           <td class="td1"><div align="center"></div></td>
           <td class="td1"><input type="submit" name="Submit" value="提 交"></td>
         </tr>
         <tr>
           <td colspan="2" class="td1"><div align="center"></div>
           　</td>
         </tr>
       </table>
     </div>
     </html:form>
  </body>
  
  <logic:equal name='order' value="1" scope="request">
      <script language=javascript>
      	window.alert('格式已更改^_^');
      </script>
  </logic:equal>
</html:html>
