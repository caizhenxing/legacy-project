<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="GB2312"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
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
	<link href="Admin_Style.css" rel="stylesheet" type="text/css">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
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
    		document.forms[0].action = "../newsArea.do?method=operArea&type=insert";
    		document.forms[0].submit();
    		}
    	}
    	//�޸�
    	function update(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../newsArea.do?method=operArea&type=update";
    		document.forms[0].submit();
    		}
    	}
    	//ɾ��
    	function del(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../newsArea.do?method=operArea&type=del";
    		document.forms[0].submit();
    		}
    	}
    	//ѡ����������ϵ������Ϣ
<%--    	function selectTypeInfo(){--%>
<%--    		var classId = document.forms[0].classId.value;--%>
<%--    		document.forms[0].action = "../formatOrder.do?method=formatList&classId="+ classId;--%>
<%--    		document.forms[0].submit();--%>
<%--    	}--%>
    	
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
   <html:form  action="/news/newsArea" method="post" >
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload">
		    <logic:equal name="type" value="detail">
		     ��ϸ
		    </logic:equal>
		    <logic:equal name="type" value="insert">
		     ���
		    </logic:equal>
		    <logic:equal name="type" value="update">
		     �޸�
		    </logic:equal>
		    <logic:equal name="type" value="delete">
		     ɾ��
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>
		    <td class="tdbgcolorqueryright" width="30%">���Ű������</td>
		    <td class="tdbgcolorqueryleft"><html:text property="newsAreaName" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">��ʽѡ��</td>
		    <td class="tdbgcolorqueryleft">	    	
		    	<html:select property="styleId">		
                    <html:optionsCollection name="areaList" label="label" value="value"/>
	        	</html:select>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">��ע</td>  
		    <td class="tdbgcolorqueryleft">
		        <html:textarea property="remark" cols="50" rows="5"></html:textarea>
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
		     <input name="btnAdd" type="button" class="bottom" value="ɾ��" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="�ر�" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	</table>
	</html:form>
</html:html>