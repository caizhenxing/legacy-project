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
				document.forms[0].action = "hotline.do?method=toMarAnalysisOper&type=insert";
				document.getElementById('spanHead').innerHTML="��ӽ�ũ������Ϣ";
				document.getElementById('buttonSubmit').value="���";
			</logic:equal>
			<logic:equal name="opertype" value="update">
				document.forms[0].action = "hotline.do?method=toMarAnalysisOper&type=update";
				document.getElementById('spanHead').innerHTML="�޸Ľ�ũ������Ϣ";
				document.getElementById('buttonSubmit').value="�޸�";
			</logic:equal>
			<logic:equal name="opertype" value="delete">
				document.forms[0].action = "hotline.do?method=toMarAnalysisOper&type=delete";
				document.getElementById('spanHead').innerHTML="ɾ����ũ������Ϣ";
				document.getElementById('buttonSubmit').value="ɾ��";
				v_flag="del"
			</logic:equal>
		}
		//ִ����֤
			
		<logic:equal name="opertype" value="insert">
		$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#hotlineContent").formValidator({onshow:"��������������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	
		
		})
		</logic:equal>
		<logic:equal name="opertype" value="update">
		  	$(document).ready(function(){
			$.formValidator.initConfig({formid:"marAnalysisBean",onerror:function(msg){alert(msg)}});	
			$("#hotlineContent").formValidator({onshow:"��������������",onfocus:"�������ݲ���Ϊ��",oncorrect:"�������ݺϷ�"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"�����������߲����пշ���"},onerror:"�������ݲ���Ϊ��"});	
		
		})
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
		<html:form action="/screen/hotline" method="post" styleId="hotlineFormBean" onsubmit="return formAction();">
			<html:hidden property="id" />
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
				<tr>
					<td class="navigateStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--		    				��ǰλ��&ndash;&gt;���������Ϣ--%>
<%--		    			</logic:equal>--%>
<%--						<logic:equal name="opertype" value="detail">--%>
				    		��ǰλ��&ndash;&gt;<span id="spanHead">�鿴��ũ������Ϣ</span>
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
				
				<tr height="200">
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle" style="font-color:#000000">
					<FCK:editor instanceName="hotlineContent" height="250">
						<jsp:attribute name="value">
							<bean:write name="hotlineFormBean" property="hotlineContent"
								filter="false" />
						</jsp:attribute>
					</FCK:editor>
					<span id="hotlineContentTip" style="width: 0px;display:inline;"></span>
				</td>
			</tr>
				
				<tr>
					<td colspan="2" align="right" class="buttonAreaStyle">
<%--						<logic:equal name="opertype" value="insert">--%>
<%--							<input type="button" name="btnAdd" class="buttonStyle" value="���" onclick="add()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="update">--%>
<%--							<input type="button" name="btnUpdate" class="buttonStyle" value="ȷ��" onclick="update()" />--%>
<%--						</logic:equal>--%>
<%--						<logic:equal name="opertype" value="delete">--%>
<%--							<input type="button" name="btnDelete" class="buttonStyle" value="ɾ��" onclick="del()" />--%>
<%--						</logic:equal>--%>
						<input type="submit" name="button" id="buttonSubmit" value="�ύ"  class="buttonStyle"/>
						<input type="button" name="" value="�ر�" class="buttonStyle" onClick="window.close();" />
					</td>
				</tr>
			</table>					
		</html:form>
	</body>
</html:html>