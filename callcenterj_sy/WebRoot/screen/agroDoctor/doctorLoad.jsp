<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<%@ include file="../../style.jsp"%>

<logic:equal value="insertsuccess" name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		window.parent.bottomm.document.location=window.parent.bottomm.document.location;
	</script>
</logic:equal>
<logic:equal value="updatesuccess" name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:equal>
<logic:equal value="deletesuccess" name="operSign">
	<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		window.close();
	</script>
</logic:equal>
<html:html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>��ũ��������ά������</title>
		<script language="javascript" type="text/JavaScript" src="../../js/calendar3.js"></script>
		<script language="javascript" src="../../js/common.js"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
				
		<link REL=stylesheet href="../../markanainfo/css/divtext.css"
			type="text/css" />
		<script language="JavaScript" src="../../markanainfo/js/divtext.js"></script>
				
		<!-- jquery��֤ -->
		<script src="../js/jquery/jquery_last.js" type="text/javascript"></script>
		<link type="text/css" rel="stylesheet" href="../css/validator.css"></link>
		<script src="../js/jquery/formValidator.js" type="text/javascript" charset="UTF-8"></script>
		<script src="../js/jquery/formValidatorRegex.js" type="text/javascript" charset="UTF-8"></script>
		   
		<script type="text/javascript">		
		var v_flag="";
		function formAction(){
			if(v_flag=="del"){
				if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'"))
					return true;
				else
					return false;
			}
		}
		
		//��ʼ��
		function init(){	
			<logic:equal name="opertype" value="detail">
				document.getElementById('buttonSubmit').style.display="none";
			</logic:equal>		
			<logic:equal name="opertype" value="insert">
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=insert";
				document.getElementById('spanHead').innerHTML="��Ӽ�ͥҽ����Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸ļ�ͥҽ����Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "screenDoctor.do?method=toMarAnalysisOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ����ͥҽ����Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
				v_flag="del"
			</logic:equal>
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		//$(document).ready(function(){
			//$.formValidator.initConfig({formid:"doctorFormBean",onerror:function(msg){alert(msg)}});	
			//$("#healthcare").formValidator({onshow:"������ҽ�Ʊ�����ʶ",onfocus:"ҽ�Ʊ�����ʶ����Ϊ��",oncorrect:"ҽ�Ʊ�����ʶ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ҽ�Ʊ�����ʶ���߲����пշ���"},onerror:"ҽ�Ʊ�����ʶ����Ϊ��"});
			//$("#healthcare").formValidator({onshow:"��������ͨ����ʶ����η������ʩ",onfocus:"ҽ����ͨ����ʶ����η������ʩ����Ϊ��",oncorrect:"��ͨ����ʶ����η������ʩ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ͨ����ʶ����η������ʩ���߲����пշ���"},onerror:"��ͨ����ʶ����η������ʩ����Ϊ��"});		
		
		//})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	//$.formValidator.initConfig({formid:"doctorFormBean",onerror:function(msg){alert(msg)}});	
			//$("#healthcare").formValidator({onshow:"������ҽ�Ʊ�����ʶ",onfocus:"ҽ�Ʊ�����ʶ����Ϊ��",oncorrect:"ҽ�Ʊ�����ʶ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"ҽ�Ʊ�����ʶ���߲����пշ���"},onerror:"ҽ�Ʊ�����ʶ����Ϊ��"});
			//$("#healthcare").formValidator({onshow:"��������ͨ����ʶ����η������ʩ",onfocus:"ҽ����ͨ����ʶ����η������ʩ����Ϊ��",oncorrect:"��ͨ����ʶ����η������ʩ�Ϸ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"��ͨ����ʶ����η������ʩ���߲����пշ���"},onerror:"��ͨ����ʶ����η������ʩ����Ϊ��"});		
		
		//})
		</logic:equal>
	
<%--	var url = "event.do?method=toEventOper&type=";--%>
<%--	//���--%>
<%--   	function add(){  --%>
<%--   		document.forms[0].action = url + "insert";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//�޸�--%>
<%--   	function update(){--%>
<%--   		document.forms[0].action = url + "update";--%>
<%--		document.forms[0].submit();--%>
<%--   	}--%>
<%--   	//ɾ��--%>
<%--   	function del(){--%>
<%--		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){--%>
<%--   			document.forms[0].action = url + "delete";--%>
<%--   			document.forms[0].submit();--%>
<%--   		}--%>
<%--   	}--%>	
</script>
	</head>
<%
 Object o = request.getAttribute("photoPath");
 if(o != null){
 	System.out.println(o.toString());
 }
 %>
	<body class="loadBody" onload="init();">
		<html:form action="/screen/screenDoctor" method="post" styleId="doctorFormBean" onsubmit="return formAction();">
			<html:hidden property="id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				��ǰλ��&ndash;&gt;���������Ϣ--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		��ǰλ��&ndash;&gt;<span id="spanHead">��ͥҽ��</span>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--				    		��ǰλ��&ndash;&gt;�޸�������Ϣ--%>
<%--				    	</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--				    		��ǰλ��&ndash;&gt;ɾ��������Ϣ--%>
<%--				    	</logic:equal>--%>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				
				<tr>
				<td class="labelStyle">
					ҽ�Ʊ�����ʶ
				</td>
				<td class="valueStyle" style="font-color:#000000">
					<html:select property="docType">
					<option value="healthcare">ҽ�Ʊ�����ʶ</option>
					<option value="prevention">��ͨ����ʶ����η������ʩ</option>
					</html:select>
					<span id="typeTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
			<tr height="200">
				<td class="labelStyle">
					����
				</td>
				<td class="valueStyle" style="font-color:#000000">
					<FCK:editor instanceName="docContent" height="200">
						<jsp:attribute name="value">
							<bean:write name="doctorFormBean" property="docContent"
								filter="false" />
						</jsp:attribute>
					</FCK:editor>
					<span id="docContentTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
				
				<tr>
					<td colspan="2" align="right" class="buttonAreaStyle">
						<input type="submit" name="button" id="buttonSubmit" value="�ύ"  class="buttonStyle"/>

					</td>
				</tr>
			</table>					
		</html:form>
	</body>
</html:html>