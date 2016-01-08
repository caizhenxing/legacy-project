<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title>语音留言管理</title>

	<link href="./../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="../js/calendar3.js"></script>
	<script language="javascript" src="../js/json.js"></script>
	<script language="javascript" src="../js/common.js"></script>
	<script type="text/javascript">
	var styleName="oddStyle";
	function addQuestion(){
		table=document.getElementById("questions");
		tr=table.insertRow();
		index=tr.rowIndex;
		td=tr.insertCell();
		td.className=styleName;
		td.insertAdjacentHTML("afterBegin",	"<select><option value='---'>请选择</option><option value='001'>单选题</option><option value='002'>多选题</option><option value='003'>问答题</option></select>");
		td=tr.insertCell();
		td.insertAdjacentHTML("afterBegin", "<input type='text' />");
		td=tr.insertCell();
		td.insertAdjacentHTML("afterBegin", "<input type='text' />");
		td=tr.insertCell();
		td.insertAdjacentHTML("afterBegin", "<input type='button' onclick='deleteQuestion(this)' value='删除' />");
		if(styleName=="oddStyle")
			styleName="evenStyle";
		else
			styleName="oddStyle";
	}
	function deleteQuestion(obj){
		document.getElementById("questions").deleteRow(obj.parentNode.parentNode.rowIndex);
	}
	function doadd(){
		getQuestionInfo();	
		document.forms[0].action="../inquiry.do?method=toSave&type=insert";
		document.forms[0].submit();
	}
	function doupdate(){
		getQuestionInfo();
		document.forms[0].action="../inquiry.do?method=toSave&type=update";
		document.forms[0].submit();
	}
	function dodel(){
		document.forms[0].action="../inquiry.do?method=toSave&type=delete";
		document.forms[0].submit();
	}
	function toback()
	{
		//opener.parent.topp.document.all.btnSearch.click();
		opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		
	}
	function getQuestionInfo(){
		table=document.getElementById("questions");
		trs=table.rows;
		for(i=0;i<trs.length;i++){
			tr=trs.item(i);
			tds=tr.cells;
			questionType=tds.item(0).childNodes(0).value;
			question=tds.item(1).childNodes(0).value;
			alternatives=tds.item(2).childNodes(0).value;
			if(questionType&&questionType!="---"){
				document.forms[0].questions.value=document.forms[0].questions.value+","+"{questionType:'"+questionType+"',question:'"+question+"',alternatives:'"+alternatives+"'}";
			}
		}
		document.forms[0].questions.value="["+document.forms[0].questions.value.substring(1)+"]";
	}
	function openCalendar(formName,targetName){
		var cal=new calendar3(document.forms[formName].elements[targetName],window.event);
		cal.year_scroll = true;
		cal.time_comp = false;
		cal.popup();
	}
</script>

</head>
<body  class="loadBody">
	<html:form action="/sys/voiceLeave.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tablebgcolor">
				<tr>
					<td class="navigateStyle">
						当前位置&ndash;&gt;查看语音留言
					</td>
				</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="contentTable">
			<tr>

				<td class="labelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle" />
				</td>
				<td class="labelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle"/>
				</td>

			</tr>
			<tr>
				<td class="labelStyle">
					语音路径
				</td>
				<td class="valueStyle">
					<html:text property="wavPath" styleClass="writeTextStyle"/>
				</td>
				<td class="labelStyle">
					是否处理
				</td>
				<td class="valueStyle">
					<html:text property="ifDispose" styleClass="writeTextStyle"/>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					处理意见
				</td>

				<td class="valueStyle">
					<html:textarea property="disposeSuggest" styleClass="writeTextStyle" rows="5" cols="45" />
				</td>
				<td class="labelStyle">
					
				</td>
				<td class="valueStyle">
					
				</td>
			</tr>
			<tr>
				<td class="navigateStyle" colspan="4" style="text-align:right;">
					<input type="button" value="关闭"  onclick="window.close()" class="buttonStyle" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
