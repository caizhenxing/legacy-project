<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<logic:notEmpty name="operSign">
	<script>
		alert("操作成功"); window.close();
	</script>
</logic:notEmpty>
<%@ include file="../style.jsp"%>

<html>
	<head>
		<link href="style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="js/calendar3.js"></script>
		
<!-- jquery验证 -->
	<script src="js/jquery/jquery_last.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="css/validator.css"></link>	
	<script src="js/jquery/formValidator.js" type="text/javascript"charset="UTF-8"></script>
	<script src="js/jquery/formValidatorRegex.js"type="text/javascript" charset="UTF-8"></script>

		<script type="text/javascript">
		<logic:empty name="inquiryForm" property="reportTopic">
		$(document).ready(function(){			
			$.formValidator.initConfig({formid:"inquiryID",onerror:function(msg){alert(msg)}});
			$("#topic").formValidator({onshow:"请输入调查主题",onfocus:"调查主题不能为空",oncorrect:"调查主题合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"调查主题两边不能有空符号"},onerror:"调查主题不能为空"});
			$("#organizers").formValidator({onshow:"请输入组织者",onfocus:"组织者不能为空",oncorrect:"组织者合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"组织者两边不能有空符号"},onerror:"组织者不能为空"});
			$("#organiztion").formValidator({onshow:"请输入发起机构",onfocus:"发起机构不能为空",oncorrect:"发起机构合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"发起机构两边不能有空符号"},onerror:"发起机构不能为空"});
						
		})
		</logic:empty>
		
		function aaa(){
			document.forms[0].action = "inquiry.do?method=toReportDelete";
			document.forms[0].submit();
		}
		</script>
	</head>

<body class="loadBody">
<html:form action="/inquiry.do?method=toReportUpdate" method="post" styleId="inquiryID">
<html:hidden property="id" />
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					调查报告
				</td>
			</tr>
		</table>
<table width="1015" border="0" cellpadding="0" cellspacing="1" class="contentTable">
  <tr>
    <td class="labelStyle">标&nbsp;&nbsp;&nbsp; 题</td>
    <td class="valueStyle"><html:text property="reportTopic" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">副&nbsp; 标&nbsp; 题</td>
    <td class="valueStyle" colspan="3"><html:text property="reportTopic2" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">调查主题</td>
    <td class="valueStyle">
    <html:text property="topic" styleClass="writeTextStyle" size="30" styleId="topic"/>
    <div id="topicTip" style="width:10px;display:inline;"></div>
    </td>
    <td class="labelStyle">组&nbsp; 织&nbsp; 者</td>
    <td class="valueStyle" colspan="3">
    <html:text property="organizers" styleClass="writeTextStyle" size="30" styleId="organizers"/>
    <div id="organizersTip" style="width:100px;display:inline;"></div>
    </td>
  </tr>
  <tr>
    <td class="labelStyle">发起机构</td>
    <td class="valueStyle">
    <html:text property="organiztion" styleClass="writeTextStyle" size="30" styleId="organiztion"/>
    <div id="organiztionTip" style="width:10px;display:inline;"></div>
    </td>
    <td class="labelStyle">参与  人 员</td>
    <td class="valueStyle" colspan="3"><html:text property="actors" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">撰 稿 人</td>
    <td class="valueStyle"><html:text property="reportCopywriter" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">关&nbsp; 键&nbsp; 字</td>
    <td class="valueStyle" colspan="3"><html:text property="reportKeyword" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">调查时间</td>
    <td class="valueStyle">
    <html:text property="beginTime" styleClass="writeTextStyle" size="18"/>
    <img alt="选择时间" src="html/img/cal.gif" onclick="openCal('inquiryForm','beginTime',false);">
    至
    <html:text property="endTime" styleClass="writeTextStyle" size="18"/>
    <img alt="选择时间" src="html/img/cal.gif" onclick="openCal('inquiryForm','endTime',false);">
    </td>
    <td class="labelStyle">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 要</td>
    <td class="valueStyle" colspan="3"><html:text property="reportAbstract" styleClass="writeTextStyle" size="50"/></td>
  </tr>
  <tr>
    <td class="labelStyle">调查样本</td>
    <td class="valueStyle"><html:text property="reportSwatch" styleClass="writeTextStyle" size="50"/></td>
    <td class="labelStyle">调查有效率</td>
    <td class="valueStyle">
    	<html:text property="reportEfficiency" styleClass="writeTextStyle"/>
    </td>
     <td class="labelStyle">审核状态</td>
    <td class="valueStyle">
    <logic:equal name="opertype" value="insert">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=insert"/>
	</logic:equal>
	<logic:equal name="opertype" value="detail">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=detail"/>
	</logic:equal>
	<logic:equal name="opertype" value="update">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=update"/>
	</logic:equal>
	<logic:equal name="opertype" value="delete">
	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm&opertype=delete"/>
	</logic:equal>
<%--    	<jsp:include flush="true" page="../flow/incState.jsp?form=inquiryForm"/>--%>
    </td>
  </tr>
    <tr>
    <td class="labelStyle">报告正文</td>
    <td colspan="5" class="valueStyle">
    	<FCK:editor instanceName="reportContent">
			<jsp:attribute name="value"><bean:write name="inquiryForm" property="reportContent" filter="false"/></jsp:attribute>
			<jsp:attribute name="height">350</jsp:attribute>
		</FCK:editor>
    </td>
    </tr>
    <tr>
    <td class="labelStyle">报告评论</td>
    <td colspan="5" class="valueStyle"><html:textarea property="reportReview" styleClass="writeTextStyle" rows="3" cols="153"/></td>
    </tr>
    <tr>
    <td class="labelStyle">备&nbsp;&nbsp;&nbsp; 注</td>
    <td colspan="5" class="valueStyle"><html:textarea property="reportRemark" styleClass="writeTextStyle" rows="3" cols="153"/></td>
    </tr>
    <tr>
				<td colspan="6" align="right" class="buttonAreaStyle">
					<logic:notEmpty name="inquiryForm" property="reportTopic">
						<input type="button" name="submit3" value="删除" class="buttonStyle" onclick="aaa()">
					</logic:notEmpty>
					<input type="submit" name="submit1" value="确定" class="buttonStyle" >
					<input type="button" name="submit2" value="关闭" class="buttonStyle" onClick="javascript:window.close();" >
				</td>
			</tr>
</table>
</html:form>
</body>
</html>
