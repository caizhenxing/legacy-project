<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="java.util.Calendar,java.text.SimpleDateFormat"%>
<%@ include file="../../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html:html locale="true">
  <head>
    <html:base />
    
    <title>�½���Ϣ</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <script language="javascript" src="../../js/form.js"></script>
   <script language="javascript" src="../../js/clockCN.js"></script>
   <script language="javascript" src="../../js/clock.js"></script>
  <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"type="text/css" />
  
<style type="text/css">
<!--
#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}
-->
</style>

  <SCRIPT language="javascript" src="../../js/calendar3.js" type="text/javascript">
	</SCRIPT>
    <script type="text/javascript">
  
    	function dep()
		{
			var arrparm = new Array();
			arrparm[0] = document.forms[0].userName;
			arrparm[1] = document.forms[0].receiveNum;
			select(arrparm);
		}
		 function select(obj)
	   	 {
			
			var page = "<%=request.getContextPath()%>/sms/modermSend.do?method=select&value="
			var winFeatures = "dialogWidth:500px; dialogHeight:520px; center:1; status:0";
	
			window.showModalDialog(page,obj,winFeatures);
		 }
		 
		 
  function checknumber(String) 
{ 
	var Letters = "1234567890,��"; 
	var i; 
	var c; 
	for( i = 0; i < String.length; i++ ) 
	{ 
		c = String.charAt( i ); 
		if (Letters.indexOf( c ) == -1) 
		{ 
			return false; 
		} 
	} 
		return true; 
} 
   function isTel(num)
  {
	var test=num;
	var part=test.split(",");
	for( i=0;i<num.value.length;i++)
	{
		if((num.value.charAt(i)>="0"&&num.value.charAt(i)<="9"))
		{
		}
		else
		{
			return false;
		}
	}
	return isint;
   }

	function save()
	{
		 var sendNum = document.forms[0].sendNum.value;
   	      var receiveNum = document.forms[0].receiveNum.value;
   	      var content = document.forms[0].content.value;
   	      var schedularTime = document.forms[0].schedularTime.value;
   	      var receiveManId = document.forms[0].receiveManId.value;
   	      var date = document.forms[0].schedularTime.value;
   	       var bt = document.forms[0].endtime.value;
   	       var sendTypeReg = document.forms[0].sendType;
   	       var  time = document.forms[0]. begintime.value;
   	       var et=date+" "+time;
		         if(sendNum==""){
                   alert("���ͺ��벻��Ϊ��");
                   return;
                 }
                 if(!checknumber(sendNum)){
				      alert("���������֣�");
		          	  return ;
				 }
				 if(document.forms[0].sendNum.value.length>11){
			      alert("���Ȳ��ܳ���11�ַ���");
			      
			      return false;
			    }
			     if(receiveNum!=""){
			      if(!regTel(receiveNum)){
                   alert("���պ����ʽ����ȷ!");         
                   return;
                   }
                 }
                 
                 
                   if(receiveManId!="")
                   {
                 	if(!regName(receiveManId)){
	                   document.forms[0].receiveManId.select();
	                   document.forms[0].receiveManId.focus();
	                   return;
                   }
                 }
                 
                 if(receiveNum==""&& receiveManId==""){
                   alert("���պ���ͽ����˲���ͬʱΪ��,��ѡһ��!");         
                   return;
                 }
             
             if(schedularTime!=""&&time!="")
             {
             	sendTypeReg.checked = true; 
             }    
               
                 
             if(sendTypeReg.checked)
             {
                 if(schedularTime==""){
                   alert("Ԥ���������ڲ���Ϊ��");
                   return;
                 }
                 if(time==""){
                   alert("��ʼʱ�䲻��Ϊ��");
                   return;
                 }
    		     if (bt!=null&&et!=null)
	              {
	      
	        	   	var timeReg =  comptime(bt,et);
	      	
			      	 if(timeReg=="-1")
				      	{
				      		alert("ʱ�䲻����ϵͳʱ��֮ǰ");
				      		return false;
				      	}
	              }
	          }
	          
		    	  document.forms[0].action ="../../sms/modermSend.do?method=operModerSendMessage&type=save";
		    	  document.forms[0].submit();
	}
	
	  function comptime(beginTime,endTime)
       {

            var bt=beginTime.substring(0,10)+"-"+beginTime.substring(11,13)+"-"+beginTime.substring(14,16);
            var et=endTime.substring(0,10)+"-"+endTime.substring(11,13)+"-"+endTime.substring(14,16);
			var beginTimes=bt.substring(0,10).split('-');
			var endTimes=et.substring(0,10).split('-');	
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19); 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
function regTel(parms){
		var parmsreplace = parms.replace("��",",");
		var strs=parmsreplace.split(",");
		var flag=false;
	
		
		if(strs.length>0)
			{	
			
				var str3 = parmsreplace.substring(parmsreplace.length-1,parmsreplace.length);
				if(str3!=',')
				{
					alert("�밴��ȷ��ʽ���� ���磺13889896868,13889936584,");
					flag = "false";
				}
				else
				{
					flag=true;
				}
			}
		return flag;
}

function regName(name){
		var parmsreplace = name.replace("��",",");
		var strs=parmsreplace.split(",");
		var flagName=false;

		if(strs.length>0)
			{	
				var str3 = parmsreplace.substring(parmsreplace.length()-1,parmsreplace.length());
				if(str3!=',')
				{
					alert("�밴��ȷ��ʽ���� ���磺����,����,");
					flagName = "false";
				}
				else
				{
					flag=true;
				}
			}
		return flagName;
}

   	function add()
   	{
   	      var sendNum = document.forms[0].sendNum.value;
   	      var receiveNum = document.forms[0].receiveNum.value;
   	      var content = document.forms[0].content.value;
   	      var schedularTime = document.forms[0].schedularTime.value;
   	      var receiveManId = document.forms[0].receiveManId.value;
   	      var date = document.forms[0].schedularTime.value;
   	      var receiveManId = document.forms[0].receiveManId.value;
   	      var bt = document.forms[0].endtime.value;
   	      var sendTypeReg = document.forms[0].sendType;
   	      
   	      var  time = document.forms[0]. begintime.value;
   	      var et=date+" "+time;
		         if(sendNum==""){
                   alert("���ͺ��벻��Ϊ��");
                   document.forms[0].sendNum.select();
                   return;
                 }
                 if(!checknumber(sendNum)){
	                 document.forms[0].sendNum.select();
	                 document.forms[0].sendNum.focus();
				      alert("���������֣�");
		          return ;
				    }
				if(document.forms[0].sendNum.value.length>11){
			      alert("���Ȳ��ܳ���11�ַ���");
			      
			      return false;
			    }
			     if(receiveNum!=""){
                   if(!regTel(receiveNum)){
                   alert("���պ����ʽ����ȷ!");
                   document.forms[0].receiveNum.select();
                   document.forms[0].receiveNum.focus();         
                   return;
                   }
                 }
                 
                  if(receiveManId!="")
                  {
                 	if(!regName(receiveManId)){
	                   document.forms[0].receiveManId.select();
	                   document.forms[0].receiveManId.focus();
	                   return;
                   }
                 }
                 
                 if(receiveNum==""&& receiveManId==""){
                   alert("���պ���ͽ����˲���ͬʱΪ��,��ѡһ��!");         
                   return;
                 }
                if(schedularTime!=null&&time!=null)
                {
                	sendTypeReg.checked = true;
                }
  				if(sendTypeReg.checked){
	                 if(schedularTime==""){
	                   alert("Ԥ���������ڲ���Ϊ��");
	                   return;
	                 }
	                 if(time==""){
	                   alert("��ʼʱ�䲻��Ϊ��");
	                   return;
	                 }
    		     if(bt!=null&&et!=null)
	            {
	      
		        	   var timeReg =  comptime(bt,et);
		      	
			      	   if(timeReg=="-1")
				      	{
				      		alert("ʱ�䲻����ϵͳʱ��֮ǰ");
				      		return false;
				      	}
	            }
	         }
		    	  document.forms[0].action ="../../sms/modermSend.do?method=operModerSendMessage&type=send";
		    	  document.forms[0].submit();
 	}

    	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
		//����ҳ��
     function toback(){
			window.location.reload();   
		}
		
	 function tobackClear(){
			  document.forms[0].reset();
		}
		
		
		var frm = document.forms[0];
		var imc_using = 1
		
		var sign = " ������:��Ϣ��ѯ";
		var msgNum = 0;

		function CountNum()
		{
			var temp = document.forms[0].content.value;
			if(temp != null)
			    msgNum = temp.length;
			else
				msgNum = 0;
			
		    var wordnum = msgNum;
		    var linenum;
		    document.all("CurWordNum").innerText = msgNum + "��";
		    
		    if(wordnum<=70)
		    {
		            linenum = 1;
		    }
		    else if((wordnum%65) == 0)
		    {
		            linenum = wordnum/65;
		    }
		    else
		    {
		            linenum = parseInt(wordnum/65) + 1;
		    }
		    document.all("CurLineNum").innerText = linenum + "��";
		}

		

		
   </script>
  </head>
  
  <body onload="tobackClear()" class="loadBody">
<logic:notEmpty name="operSign">
<script>
alert("�����ɹ�");
</script>
</logic:notEmpty>



  <%
   
  String str=request.getParameter("id");
  //out.println(str);
  int i=0;
  %>
  <%
        Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String systime = sdf.format(cal.getTime());
		
 %> 
    <html:form action="/sms/modermSend.do" method="post">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		  <tr>
		    <html:hidden property="id" value="<%=str%>"/>		    
		     <td class="navigateStyle">��ǰλ��->���ŷ���</td>
		  </tr>
		</table>
		
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		   <html:hidden property="endtime" value="<%=systime%>"/>
		  <tr>  
		    <td class="labelStyle">���ͺ���</td>
		    <td class="valueStyle">
		     <html:text property="sendNum" styleClass="writeTextStyle" maxlength="11" size="11"></html:text><font color="red" id="fontStyle">*</font>
		     </td>
		  </tr>
          <tr>  
		    <td class="labelStyle">�ӷ�����</td>
		    <td class="valueStyle">
		     <html:text property="receiveNum" styleClass="writeTextStyle"  size="65"/>
		     <html:hidden property="userName"/>
		     <img  src="../../style/<%=styleLocation%>/images/detail.gif" alt="��ӱ����û�" onclick="dep()" width="16" height="16" border="0"/>
		     <font color="red" id="fontStyle"><br>ע���밴��13312451470,13912362011,��</font><font color="red" id="fontStyle">��</font><font color="red"  id="fontStyle">��ʽ���롣</font>
		    <tr>
		      <td class="labelStyle">
		      ��&nbsp;��&nbsp;��
		      </td>
		      <td class="valueStyle">	
			    <html:text property="receiveManName" styleClass="writeTextStyle"  size="65"/>
			  </td>
			</tr>
		  <tr>
		      <td class="labelStyle">
		      	����ʱ��
		      </td>
		      <td class="valueStyle">	
			    <html:text property="schedularTime" styleClass="writeTextStyle"/>
			    
			     <img alt="ѡ������" src="../../html/img/cal.gif"
						onclick="openCal('modermSendBean','schedularTime',false);">
 				<font id="fontStyle"> ����ʱ�� </font>
				<html:text property="begintime" maxlength="10" size="10"  styleClass="input" readonly="true"/>
          		<input type="button"   value="ѡ��ʱ��" onclick="OpenTime(document.all.begintime);" class="buttonStyle"/>
				
				<div style="display:none;"><html:checkbox property="sendType" ><font id="fontStyle">��ʱ����</font></html:checkbox></div><br>
<%--			    <font color="red" id="fontStyle">ע�����ϣ����ʱ���ͣ�����ع�ѡ����ʱ���͡�����һ����д��Ԥ���������ڡ��롰����ʱ�䡱��</font>--%>
			 
				</td>
			  
			  
		   </tr>
	  
          <tr>  
		    <td class="labelStyle">��������</td>
		    <td class="valueStyle"><html:textarea property="content" styleClass="writeTextStyle" cols="80"  rows="5" onkeyup="CountNum();" onchange="CountNum();" onfocus="CountNum();"></html:textarea>
		    	&nbsp;<br>
              <font id="fontStyle">��ǰ����������</font><span class="cpx12red" id="CurWordNum"><font color="red"  id="fontStyle">0��</font></span>
              <font id="fontStyle">��ǰ��������</font><span class="cpx12red" id="CurLineNum"><font color="red"  id="fontStyle">0��</font></span>
		    </td>
		  </tr>
	  
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="buttonAreaStyle">	       
		     <input name="btnadd" type="button"   value="����" onclick="add()" class="buttonStyle"/>   
		     <input name="btnsave" type="button"   value="����" onclick="save()" class="buttonStyle"/>   
		     <input name="resetbtn" type="reset"   value="ˢ��" class="buttonStyle"/>
		    </td>
		  </tr>
		  <html:hidden property="receiveManId"/>
	</table>
    </html:form>
   
  </body>
</html:html>
