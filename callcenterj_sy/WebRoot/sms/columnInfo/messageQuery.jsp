<%@ page contentType="text/html; charset=gbk" language="java"
	errorPage=""%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
	<logic:equal name="operSign" value="dingzhishibai">
		<script>
  		alert("�Ѿ����ƹ���ҵ�񣬲����ظ����ƣ�");
<%--  		document.parent.topp.document.all.Submit4.click();--%>
		document.close();
  	</script>
	</logic:equal>
	<logic:equal name="operSign" value="tuidingshibai">
		<script>
  		alert("�Ѿ��˶�����ҵ�񣬲����ظ��˶���");
<%--  		document.all.Submit4.click();--%>
  	</script>
	</logic:equal>
	<logic:equal name="operSign" value="sys.common.operSuccess">
		<script>
		alert("�����ɹ�");
		//opener.parent.topp.document.all.btnSearch.click();//ִ�в�ѯ��ť�ķ���
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
<%--		document.parent.topp.document.all.Submit4.click();--%>
<%--		document.getElementById("btnsearch").click();--%>
<%--        window.close();--%>
<%--        opener.parent.topp.document.all.btnSearch.click()--%>
		
	</script>
	</logic:equal>
</logic:notEmpty>
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<script language="javascript" src="../js/form.js"></script>
		<script language="javascript" src="../js/clockCN.js"></script>
		<script language="javascript" src="../js/clock.js"></script>
		<SCRIPT language="javascript" src="../js/calendar3.js"
			type="text/javascript"></script>
		<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />

		<title>���Ŷ��Ʋ�ѯ</title>
		<script type='text/javascript' src='../js/msg.js'></script>
		<script type="text/javascript">
	// ��ѯ���Ƽ�¼
	function query(){
   		document.forms[0].action = "columnInfo.do?method=toMessageList";
   		document.forms[0].target = "bottomm";
		document.forms[0].submit();
	}
	//���
   	function add(){
   		
   		popUp('windows','columnInfo.do?method=toMessagesLoad&type=insert',680,300)
<%--   		document.forms[0].action = "columnInfo.do?method=toColumnInfoOper";--%>
<%--		document.forms[0].submit();--%>
   	}
   	//�޸�
   	function update(){
   		document.forms[0].action = url + "update";
		document.forms[0].submit();
   	}
   	//ɾ��
   	function del(){
		if(confirm("Ҫ����ɾ����������'ȷ��',��ֹ��������'ȡ��'")){
   		
   		document.forms[0].action = url + "delete";
   		document.forms[0].submit();
   		
   		}
   	}
   	
   	function change(){
   		if(document.forms[0].orderType.value == "orderProgramme") {
   			document.all.Submit.value = "�� ��";
<%--   			document.getElementById("div1").style.display = 'block';--%>
   		}
   		if(document.forms[0].orderType.value == "customize") {   			
   			document.all.Submit.value = "�� ��";
   		}
   		if(document.forms[0].orderType.value == "") {
   			document.all.Submit.value = "�� ��";
   		}
   	}
	
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
</script>



	</head>

	<body class="loadBody">
		<html:form action="/sms/columnInfo" method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="nivagateTable">
				<tr>
					<td class="navigateStyle">
						��ǰλ��&ndash;&gt;����ά��
					</td>
				</tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="conditionTable">
				<tr>
					<td class="labelStyle">
						���ű���
					</td>
					<td class="valueStyle">
						<html:text property="columnName" styleClass="writeTextStyle"/>
					</td>
					<td class="labelStyle">
						������Ŀ
					</td>
					<td class="valueStyle">
						<html:select property="columnInfo">
							<%--      	<html:option value="tianqiyubao" >����Ԥ��</html:option>--%>
							<option value="">
								��ѡ��
							</option>
							<html:options collection="list" property="value"
								labelProperty="label" />
						</html:select>
					</td>
					
					<td class="labelStyle" align="right">
						<input type="button" id="btnsearch" name="Submit4" value="�� ѯ" onClick="query()"
							class="buttonStyle">
						<input type="button" name="Submit" id="Submit" value="�� ��"
							onClick="add()" class="buttonStyle">
						<input type="reset" name="aa" value="�� ��" class="buttonStyle">
					</td>
				</tr>

				<tr class="buttonAreaStyle">
					<td colspan="7" align="right"></td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
