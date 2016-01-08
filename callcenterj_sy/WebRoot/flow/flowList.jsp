<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
  <head>
    <html:base />
    
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	
  </head>
  
  <body class="listBody">

		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle">所 属 库</td>
		    <td class="listTitleStyle">首次提交人</td>
		    <td class="listTitleStyle">首次提交时间</td>
		    <td class="listTitleStyle">最近提交人</td>
		    <td class="listTitleStyle">最近提交时间</td>
		    <td class="listTitleStyle">审核状态</td>
		    <td class="listTitleStyle">操作状态</td>
		    <td class="listTitleStyle">操作</td>
		  </tr>
		  <logic:iterate id="pagelist" name="list" indexId="i">
		  <%
			String style = i.intValue() % 2 == 0 ? "oddStyle": "evenStyle";
		  %>
		  <tr style="line-height: 21px;">
		  	<td><bean:write name="pagelist" property="type" filter="true"/></td>
		    <td><bean:write name="pagelist" property="submit_id" filter="true"/></td>
		    <td><bean:write name="pagelist" property="submit_time" filter="true"/></td>
		    <td><bean:write name="pagelist" property="submit_id_end" filter="true"/></td>
            <td><bean:write name="pagelist" property="submit_time_end" filter="true"/></td>
            <td><bean:write name="pagelist" property="state" filter="true"/></td>
            <td><bean:write name="pagelist" property="is_read" filter="false"/></td>
            <td>
			<img alt="修改" src="../style/<%=styleLocation %>/images/update.gif" 
				onclick="update(this,'<bean:write name="pagelist" property="flow_id"/>')" >
            </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="8" class="pageTable"><page:page name="userpageTurning" style="second"/></td>
		  </tr>
		</table>

  </body>
</html:html>
<script>
	
	var url, width, height;
	
	function update(obj, flow_id){
		var type = obj.parentNode.parentNode.cells[0].innerText;
		if(type == "普通案例库"){
			url = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=update&id=" + flow_id;
			width = "800";
			height = "460";
		}else if(type == "焦点案例库"){
			url = "../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id=" + flow_id;
			width = "800";
			height = "475";
		}else if(type == "会诊案例库"){
			url = "../caseinfo/hzinfo.do?method=tohzinfoLoad&type=update&id=" + flow_id;
			width = "800";
			height = "485";
		}else if(type == "效果案例库"){
			url = "../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoLoad&type=update&id=" + flow_id;
			width = "800";
			height = "508";
		}else if(type == "农产品供求库"){
			url = "../sad.do?method=toSadLoad&type=update&id=" + flow_id;
			width = "657";
			height = "305";
		}else if(type == "农产品价格库"){
			url = "../operpriceinfo.do?method=toOperPriceinfoLoad&type=update&id=" + flow_id;
			width = "657";
			height = "305";
		}else if(type == "焦点追踪库"){
			url = "../focusPursue.do?method=toFocusPursueLoad&type=update&id=" + flow_id;
			width = "1010";
			height = "675";
		}else if(type == "市场分析库"){
			url = "../markanainfo.do?method=toMarkanainfoLoad&type=update&id=" + flow_id;
			width = "1010";
			height = "675";
		}else if(type == "企业信息库"){
			url = "../operCorpinfo.do?method=toOperCorpinfoLoad&type=update&id=" + flow_id;
			width = "780";
			height = "440";
		}else if(type == "普通医疗服务信息库"){
			url = "../medical/medicinfo.do?method=toMedicinfoLoad&type=update&id=" + flow_id;
			width = "590";
			height = "475";
		}else if(type == "预约医疗服务信息库"){
			url = "../medical/bookMedicinfo.do?method=toBookMedicinfoLoad&type=update&id=" + flow_id;
			width = "600";
			height = "665";
		}else if(type == "调查问卷设计库"){
			url = "../inquiry.do?method=toLoad&type=update&id=" + flow_id;
			width = "700";
			height = "300";
		}else if(type == "调查信息分析库"){
			flow_id = flow_id.substring(6, flow_id.length);//去掉前边几个字母后才是对的
			url = "../inquiry.do?method=toReportLoad&id=" + flow_id;
			width = "1015";
			height = "675";
		}else{
			alert("没有找到所属库链接");
			return;
		}
		openWin(url,width,height);
	}
	
	function openWin(url,width,height){
		var top = (window.screen.availHeight-30-height)/2;
		var left = (window.screen.availWidth-10-width)/2;
		window.open(url,'','width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=auto,resizable=yes,status=yes');
	}

</script>
