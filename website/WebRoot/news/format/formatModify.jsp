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
    <title>���Ÿ�ʽά��</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

   <link href="Admin_Style.css" rel="stylesheet" type="text/css">
   
    <script language="javascript">
    	//ѡ����������ϵ������Ϣ
    	function selectTypeInfo(){
    		var classId = document.forms[0].classId.value;
    		document.forms[0].action = "../formatOrder.do?method=formatList&classId="+ classId;
    		document.forms[0].submit();
    	}
    	
    	//ѡ����ʾ��ʽ
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
           <td width="168" class="td1"><div align="center" class="style6">������ʽ��</div></td>
           <td width="507" class="td1"><div align="left">
           <bean:write name='formatInfo' property='formatType'/> </div></td>
         </tr>
         
         <tr>
           <td class="td1"><div align="center"><span class="style4"><span class="style5">��ʽ���ţ�</span></span></div></td>
           <td class="td1"><div align="left">
                   <html:hidden name='formatInfo' property='classId' write="true"/>
               </div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style6">������Ŀ��</div></td>
           <td class="td1">
             <div align="left">
               <input type="text" name="articleNum" value="<bean:write name='formatInfo' property='articleNum'/>" size="5">
             </div></td></tr>
         <tr>
           <td class="td1"><div align="center" class="style6">��ʾ���ͣ�</div></td>
           <td class="td1">
             <div align="left">
               <select name="showType" onChange="selectShowType()">
	             <logic:equal name='formatInfo' property='showType' value="1" scope="request">
	             	<option value="<bean:write name='formatInfo' property='showType'/>" selected>���±����б�</option>
	                <option value="2">���±���+��������</option>
			        <option value="3">СͼƬ+���±���</option>
	             </logic:equal>
	             <logic:equal name='formatInfo' property='showType' value="2" scope="request">
	                 <option value="<bean:write name='formatInfo' property='showType'/>" selected>���±���+��������</option>
	                 <option value="1" selected>���±����б�</option>
	                 <option value="3">СͼƬ+���±���</option>
	             </logic:equal>
	             <logic:equal name='formatInfo' property='showType' value="3" scope="request">
	                 <option value="<bean:write name='formatInfo' property='showType'/>" selected>СͼƬ+���±���</option>
	                 <option value="1" selected>���±����б�</option>
	                 <option value="2">���±���+��������</option>
	             </logic:equal>
               </select>
           </div></td>
         </tr>
         <tr>
           <td height="68" class="td1"><div align="center" class="style6">��ʾ���ݣ�</div></td>
           <td class="td1"><div align="left"><p>
             <input type="checkbox" name="showProperty" value="true" <logic:equal name='formatInfo' property='showProperty' value="true" scope="request">checked</logic:equal> />
             <span class="style7">��������</span>             
             <input type="checkbox" name="showTile" value="true" <logic:equal name='formatInfo' property='showTile' value="true" scope="request">checked</logic:equal> />
             ���±���
             <input type="checkbox" name="author" value="true" <logic:equal name='formatInfo' property='author' value="true" scope="request">checked</logic:equal> />
�� ��           </p>
             <p>      <input type="checkbox" name="clickNum" value="true" <logic:equal name='formatInfo' property='clickNum' value="true" scope="request">checked</logic:equal> />
               �����
                 <input type="checkbox" name="showDate" value="true" <logic:equal name='formatInfo' property='showDate' value="true" scope="request">checked</logic:equal> />
               ����ʱ��
               <input type="checkbox" name="showMore" value="true" <logic:equal name='formatInfo' property='showMore' value="true" scope="request">checked</logic:equal> /> 
           �����࡭��������             </p></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style4">��������ַ�����</div></td>
           <td class="td1"><div align="left">
             <input type="text" name="showTileMax" value="<bean:write name='formatInfo' property='showTileMax'/>" size="5">
           &nbsp;<font color="#FF0000">���Ϊ�գ�����ʾ�������⡣��ĸ��һ���ַ������������ַ���</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">������ʽ��</div></td>
           <td class="td1"><div align="left">�� ɫ ��
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
&nbsp;&nbsp;�� �ͣ�
<select name="titleFontType">
                 
            <logic:equal name='formatInfo' property='titleFontType' value="1" scope="request">
            <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     ����</option>
   		     <option value="2">б��</option>
               <option value="3">��+б</option>
	        </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontType' value="2" scope="request">
   		     <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     б��</option>
   		     <option value="2">����</option>
                <option value="3">��+б</option>
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontType' value="3" scope="request">
   		     <option value="<bean:write name='formatInfo' property='titleFontType'/>" selected>
   		     ��+б</option>
   		     <option value="2">����</option>
                <option value="1">б��</option>
   		     </logic:equal>
            </select>
&nbsp;&nbsp;�ִ�С��
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
   		     ��С
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="x-small" scope="request">
   		     ��С
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="small" scope="request">
   		     С
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="medium" scope="request">
   		     ��
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="large" scope="request">
   		     ��
   		     </logic:equal>
   		     <logic:equal name='formatInfo' property='titleFontSize' value="x-large" scope="request">
   		     �ش�
   		     </logic:equal></option>
             <option value="10px">10</option>
             <option value="12px">12</option>
             <option value="14px">14</option>
		     <option value="16px">16</option>
		     <option value="18px">18</option>
		     <option value="24px">24</option>
		     <option value="36px">36</option>
		     <option value="xx-small">��С</option>
		     <option value="x-small">��С</option>
		     <option value="small">С</option>
		     <option value="medium">��</option>
		     <option value="large">��</option>
		     <option value="x-large">�ش�</option>
           </select>
           </div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">���������ַ���:</div></td>
           <td class="td1"><div align="left">
             <input type="text" name="contextNum" value="<bean:write name='formatInfo' property='contextNum'/>" size="5">
           &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">ֻ�е���ʾ������Ϊ������+���ݡ�ʱ����Ч</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">���ڷ�Χ:</div></td>
           <td class="td1"><div align="left">ֻ��ʾ���
             <input type="text" name="orderDate" value="<bean:write name='formatInfo' property='orderDate'/>" size="5">
           ���ڵ�����&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">���Ϊ�գ�����ʾ��������������</font></div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">�����ֶΣ�</div></td>
           <td class="td1">
             <div align="left">
          <select name="showOrderColumn">
               
           	<logic:equal name='formatInfo' property='showOrderColumn' value="NEWS_ID" scope="request">
       		    <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       			    ����ID
       		    </option>
       		    <option value="LAST_DATE">����ʱ��</option>
       		    <option value="CLICK_NUM">�������</option>
       	    </logic:equal>
       	     <logic:equal name='formatInfo' property='showOrderColumn' value="LAST_DATE" scope="request">
       		     <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       			     ����ʱ��
       		     </option>
       		     <option value="NEWS_ID">����ID</option>
       		     <option value="CLICK_NUM">�������</option>
       	     </logic:equal>
       	     <logic:equal name='formatInfo' property='showOrderColumn' value="CLICK_NUM" scope="request">
       		     <option value="<bean:write name='formatInfo' property='showOrderColumn'/>" selected>
       		     �������
       		     </option>
       		     <option value="NEWS_ID">����ID</option>
       		     <option value="LAST_DATE">����ʱ��</option>
       	     </logic:equal>
			    
      </select>
        &nbsp;</div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center" class="style8">���򷽷���</div></td>
           <td class="td1"><div align="left">
          <select name="showOrderType">
            <option value="<bean:write name='formatInfo' property='showOrderType'/>" selected>
       	      <logic:equal name='formatInfo' property='showOrderType' value="asc" scope="request">
       		      ����
       	      </logic:equal>
       	      <logic:equal name='formatInfo' property='showOrderType' value="desc" scope="request">
       		      ����
       	      </logic:equal>
            </option>
            <option value="asc">����</option>
            <option value="desc" selected>����</option>
          </select>
        &nbsp;</div></td>
         </tr>
         <tr>
           <td class="td1"><div align="center"></div></td>
           <td class="td1">��</td>
         </tr>
         <tr>
           <td class="td1"><div align="center"></div></td>
           <td class="td1"><input type="submit" name="Submit" value="�� ��"></td>
         </tr>
         <tr>
           <td colspan="2" class="td1"><div align="center"></div>
           ��</td>
         </tr>
       </table>
     </div>
     </html:form>
  </body>
  
  <logic:equal name='order' value="1" scope="request">
      <script language=javascript>
      	window.alert('��ʽ�Ѹ���^_^');
      </script>
  </logic:equal>
</html:html>
