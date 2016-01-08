<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<%@ include file="../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>����׷�ٴ���Ļ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<style type="text/css">
<!--
#fontStyle {
	font-family: "����";
	font-size: 12px;
	font-style: normal;
}
-->
</style>
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	
	<link REL=stylesheet href="../markanainfo/css/divtext.css"
		type="text/css" />
	<script language="JavaScript" src="../markanainfo/js/divtext.js"></script>
		<!-- jquery��֤ -->
	<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
	
	<script src="../js/jquery/formValidator.js" type="text/javascript"
		charset="UTF-8"></script>
	<script src="../js/jquery/formValidatorRegex.js"
		type="text/javascript" charset="UTF-8"></script>
<!-- end jquery��֤ -->
	<script type="text/javascript">
	
		//��ʼ��
		function init(){
			<logic:equal name="opertype" value="insert">
    				document.getElementById('spanHead').innerHTML="�����Ϣ";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=insert";
					document.getElementById('btnOper').value="���";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="update">
   					document.getElementById('spanHead').innerHTML="�޸���Ϣ";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=update";
					document.getElementById('btnOper').value="�޸�";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			<logic:equal name="opertype" value="delete">
   					document.getElementById('spanHead').innerHTML="ɾ����Ϣ";
					document.forms[0].action = "../focusTracking.do?method=toFocusTracking&type=delete";
					document.getElementById('btnOper').value="ɾ��";
					document.getElementById('btnOper').style.display="inline";
   			</logic:equal>
   			//toback();
		}
		//ִ����֤
		<logic:equal name="opertype" value="insert">
    	$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#ftPeriod").formValidator({onshow:"�������ڵ�",onfocus:"�ڵڲ���Ϊ��",oncorrect:"�ڵںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ڵ����߲����пշ���"},onerror:"�ڵڲ���Ϊ��"});
			$("#ftTitle").formValidator({onshow:"���������",onfocus:"���ⲻ��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ⲻ��Ϊ��"});
			$("#ftSummary").formValidator({onshow:"������ժҪ��Ϣ",onfocus:"ժҪ��Ϣ����Ϊ��",oncorrect:"ժҪ��Ϣ�Ϸ�"}).inputValidator({min:1,empty:{rightempty:false,emptyerror:"ժҪ��Ϣ��߲����пշ���"},onerror:"ժҪ��Ϣ����Ϊ��"});		
			
		})
   		</logic:equal>
   		<logic:equal name="opertype" value="update">
   		$(document).ready(function(){
			$.formValidator.initConfig({formid:"formValidate",onerror:function(msg){alert(msg)}});
			$("#ftPeriod").formValidator({onshow:"�������ڵ�",onfocus:"�ڵڲ���Ϊ��",oncorrect:"�ڵںϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�ڵ����߲����пշ���"},onerror:"�ڵڲ���Ϊ��"});
			$("#ftTitle").formValidator({onshow:"���������",onfocus:"���ⲻ��Ϊ��",oncorrect:"����Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�������߲����пշ���"},onerror:"���ⲻ��Ϊ��"});
			$("#ftSummary").formValidator({onshow:"������ժҪ��Ϣ",onfocus:"ժҪ��Ϣ����Ϊ��",oncorrect:"ժҪ��Ϣ�Ϸ�"}).inputValidator({min:1,empty:{rightempty:false,emptyerror:"ժҪ��Ϣ��߲����пշ���"},onerror:"ժҪ��Ϣ����Ϊ��"});

		})
   		</logic:equal>
						
		function toback()
		{

			//opener.parent.topp.document.all.btnSearch.click();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
		}
			</script>

</head>

<body onunload="toback()" class="loadBody" onload="init()">
	<logic:notEmpty name="operSign">
		<script>
	alert("�����ɹ�"); window.close();	
	</script>
	</logic:notEmpty>

	<html:form action="/focusTracking.do" method="post" styleId="formValidate">
	<html:hidden property="ftId" />
	
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
				��ǰλ��&ndash;&gt;
				<span id="spanHead">��ϸ</span>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
		
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
					<html:text property="ftPeriod" styleClass="writeTextStyle" styleId="ftPeriod"/>
					<span id="ftPeriodTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>		
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle" >
					<html:text property="ftTitle" styleClass="writeTextStyle" size="40" styleId="ftTitle" />
					<span id="ftTitleTip" style="width: 0px;display:inline;"></span>
				</td>
				
			</tr>
			<tr>
				<td class="labelStyle">
					ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ
				</td>
				<td class="valueStyle" >
					<html:textarea property="ftSummary" styleClass="writeTextStyle"  styleId="ftSummary" rows="7" cols="70"></html:textarea>
<%--					<html:text property="ftSummary" styleClass="writeTextStyle"  styleId="ftSummary" />--%>
					<span id="ftSummaryTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			
			<logic:equal name="opertype" value="detail">
	    	<tr>
				<td class="labelStyle">
					���һ�β�����
				</td>
				<td class="valueStyle">
					<html:text property="ftCreateUser" styleClass="writeTextStyle" />
					
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					���һ�β���ʱ��
				</td>
				<td class="valueStyle">
					<html:text property="ftCreateTime" styleClass="writeTextStyle" />
				</td>
			</tr>		
	    	</logic:equal>			
						
			<tr>
				<td colspan="2" align="center" class="buttonAreaStyle">
				 	<logic:present name="opertype"> 
				<input type="submit" style="display:none;" name="btnOper" id="btnOper"  value="��ϸ"  class="buttonStyle"/>
				</logic:present>
					<input type="button" name="" value="�ر�"  
						onClick="javascript:window.close();" class="buttonStyle"/>

				</td>
			</tr>
		</table>	
	</html:form>
</body>
</html:html>
