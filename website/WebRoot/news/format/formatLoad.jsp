<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
        //���
    	function checkForm(addstaffer){
<%--    		if (!checkNotNull(addstaffer.name,"�û���")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.password,"����")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.repassword,"�ظ�����")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.question,"��������")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.answer,"�����")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.groomuser,"�Ƽ���")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.email,"�����ʼ�")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.val,"��֤��")) return false;--%>
<%--    		if (addstaffer.password.value !=addstaffer.repassword.value)--%>
<%--            {--%>
<%--            	alert("��������������벻һ�£�");--%>
<%--            	addstaffer.password.focus();--%>
<%--            	return false;--%>
<%--            }--%>
    		return true;
    	}
    	//���
    	function add(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../format.do?method=operFormat&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//�޸�
    	function update(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../format.do?method=operFormat&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//ɾ��
<%--    	function delete(){--%>
<%--    		var f =document.forms[0];--%>
<%--    		if(checkForm(f)){--%>
<%--    		document.forms[0].action = "../format.do?method=operFormat&type=delete";--%>
<%--    		document.forms[0].submit();--%>
<%--    		}--%>
<%--    	}--%>
      	//
    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:225px;dialogHeight:225px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
				//����ҳ��
		function toback(){
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee" onunload="toback()">
    <logic:notEmpty name="operSign">
	   <script>window.close();alert("<bean:write name='operSign'/>");window.close();</script>
	</logic:notEmpty>
    <html:form action="/news/format" method="post">
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload">
		    <logic:equal name="type" value="detail">
		     ��ϸ
		    </logic:equal>
		    <logic:equal name="type" value="insert">
		     ��ʽ���
		    </logic:equal>
		    <logic:equal name="type" value="update">
		     ��ʽ�޸�
		    </logic:equal>
		    <logic:equal name="type" value="delete">
		     ��ʽɾ��
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright" width="30%">��ʽ����:</td>
		    <td class="tdbgcolorqueryleft"><html:text property="styleDescribe" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">������Ŀ:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:text property="newsNum" styleClass="input" size="5"></html:text>
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">���Ϊ�գ�����ʾ��������</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">��ʾ����:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="showStyle" styleClass="input">		
	        		<html:option value="1" >���±����б�</html:option>
	        		<html:option value="2" >���±���+��������</html:option>
<%--	        		<html:option value="3" >СͼƬ+���±���</html:option>--%>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright" width="30%">��ʾ����:</td>
		    <td class="tdbgcolorqueryleft">
            <p>
<%--             <input type="checkbox" name="showProperty" disabled value="true" <logic:equal name='formatInfo' property='showProperty' value="true" scope="request">checked</logic:equal> />--%>
             <html:multibox property="articleProperty" value="1"></html:multibox>  
               �������� 
             <html:multibox property="author" value="1"></html:multibox>           
               ����&nbsp;&nbsp;&nbsp;&nbsp;
             <html:multibox property="clickTimes" value="1"></html:multibox>   
               �������<br>
             <html:multibox property="updatetime" value="1"></html:multibox>  
               ����ʱ��
             <html:multibox property="isHot" value="1"></html:multibox>  
               �ȵ��־
             <html:multibox property="showMore" value="1"></html:multibox>  
             �����࡭��������
<%--             <input type="checkbox" name="author" disabled value="true" <logic:equal name='formatInfo' property='author' value="true" scope="request">checked</logic:equal> />--%>
<%--              �� ��</p>--%>
<%--             <p>      <input type="checkbox" name="clickNum" disabled value="true" <logic:equal name='formatInfo' property='clickNum' value="true" scope="request">checked</logic:equal> />--%>
<%--               �����--%>
<%--                 <input type="checkbox" name="showDate" disabled value="true" <logic:equal name='formatInfo' property='showDate' value="true" scope="request">checked</logic:equal> />--%>
<%--               ����ʱ��--%>
<%--               <input type="checkbox" name="showMore" disabled value="true" <logic:equal name='formatInfo' property='showMore' value="true" scope="request">checked</logic:equal> /> --%>
<%--           �����࡭��������             </p>--%>
</td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">��������ַ�����:</td>  
		    <td class="tdbgcolorqueryleft">
		      <div align="left">   
		        <html:text property="titleCharNum" size="5" styleClass="input"></html:text>
                &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">���Ϊ�գ�����ʾ�������⡣��ĸ��һ���ַ������������ַ���</font></div>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">������ʽ:</td>
		    <td class="tdbgcolorqueryleft">	    	
		       <html:select property="titleCharColor" styleClass="input">
		         <html:option value="">��ɫ</html:option>	
                 <html:option value="#000000" style="background-color:#000000"></html:option>
                 <html:option value="#FFFFFF" style="background-color:#FFFFFF"></html:option>
                 <html:option value="#008000" style="background-color:#008000"></html:option>
                 <html:option value="#800000" style="background-color:#800000"></html:option>
                 <html:option value="#808000" style="background-color:#808000"></html:option>
                 <html:option value="#000080" style="background-color:#000080"></html:option>
                 <html:option value="#800080" style="background-color:#800080"></html:option>
                 <html:option value="#808080" style="background-color:#808080"></html:option>
                 <html:option value="#FFFF00" style="background-color:#FFFF00"></html:option>
                 <html:option value="#00FF00" style="background-color:#00FF00"></html:option>
                 <html:option value="#00FFFF" style="background-color:#00FFFF"></html:option>
                 <html:option value="#FF00FF" style="background-color:#FF00FF"></html:option>
                 <html:option value="#FF0000" style="background-color:#FF0000"></html:option>
                 <html:option value="#0000FF" style="background-color:#0000FF"></html:option>
                 <html:option value="#008080" style="background-color:#008080"></html:option>
               </html:select>
<%--                &nbsp;&nbsp;�� �ͣ�--%>
<%--                <html:select property="titleCharFont" styleClass="input">		--%>
<%--	        		<html:option value="1" >����</html:option>--%>
<%--	        		<html:option value="2" >б��</html:option>--%>
<%--	        		<html:option value="3" >��+б</html:option>--%>
<%--	        	</html:select>--%>
                &nbsp;&nbsp;�ִ�С��
          <html:select property="titleCharSize" styleClass="input">
             <html:option value="12px">12</html:option>
             <html:option value="14px">14</html:option>
		     <html:option value="16px">16</html:option>
		     <html:option value="18px">18</html:option>
		     <html:option value="24px">24</html:option>
		     <html:option value="36px">36</html:option>
		     <html:option value="xx-small">��С</html:option>
		     <html:option value="x-small">��С</html:option>
		     <html:option value="small">С</html:option>
		     <html:option value="medium">��</html:option>
		     <html:option value="large">��</html:option>
		     <html:option value="x-large">�ش�</html:option>
           </html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">���������ַ���:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:text property="contentCharNum" styleClass="input" size="5"></html:text>
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">ֻ�е���ʾ������Ϊ������+���ݡ�ʱ����Ч</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">��������:</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:multibox property="hotArticle" value="1"></html:multibox>  
                  ��������
                <html:multibox property="tuijianArticle" value="1"></html:multibox>  
                  �Ƽ�����
                &nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">�������ѡ������ʾ��������</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">���ڷ�Χ:</td>
		    <td class="tdbgcolorqueryleft">ֻ��ʾ���	    	
		    	<html:text property="dateRange" styleClass="input" size="5"></html:text>���ڵ�����
		    	&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FF0000">���Ϊ�գ�����ʾ��������������</font>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">�����ֶ�:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="paixuField" styleClass="input">		
	        		<html:option value="1" >����Id</html:option>
	        		<html:option value="2" >����ʱ��</html:option>
	        		<html:option value="3" >�������</html:option>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>  
		    <td class="tdbgcolorqueryright">���򷽷�:</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:select property="paixuMethod" styleClass="input">		
	        		<html:option value="desc">����</html:option>
	        		<html:option value="asc" >����</html:option>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="type" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="���" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="type" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="�޸�" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="type" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="ɾ��" onclick="delete()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="�ر�" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	    </table>
    </html:form>
  </body>
</html:html>
