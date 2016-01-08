<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>焦点追踪库</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		 	table{
	border: 1px solid #000000;
	 	}
	 	
		td{
			border:1px solid #000000;
		}
	</style>
	</style>
  </head>
  
  <body>
    <logic:iterate id="trace" name="list" type="et.po.OperFocusinfo">
<%--    <bean:write name="trace" property="focusId" />--%>
    
    <!-- ##################################### -->
    <table width="100%" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td width="10%" rowspan="4" class="valueStyle">				</td>
				<td width="22%" class="labelStyle">
					主&nbsp;办&nbsp;方				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="trace" property="frontFor" />
					
				</td>
				<td width="9%" class="labelStyle">
					承&nbsp;办&nbsp;方				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="trace" property="underTake" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					支持热线
				</td>
				<td width="7%" class="valueStyle">
			  		<bean:write name="trace" property="supportTel" />
			  </td>
				<td width="23%" class="labelStyle">
					支持网站				</td>
				<td width="6%" class="valueStyle">
			  		<bean:write name="trace" property="supportSite" />
			  </td>
				<td class="labelStyle">
					联系邮箱
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="trace" property="contactMail" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					主&nbsp;&nbsp;&nbsp;&nbsp;编
				</td>
				<td class="valueStyle">
					<bean:write name="trace" property="chiefEditor" />
				</td>
				<td class="labelStyle">
					副&nbsp;主&nbsp;编
				</td>
				<td class="valueStyle">
					<bean:write name="trace" property="subEditor" />
				</td>
				<td class="labelStyle">
					首席编辑
				</td>
				<td width="14%" class="valueStyle">
			 		<bean:write name="trace" property="firstEditor" />
			  </td>
				<td width="8%" class="labelStyle">
					责任编辑				</td>
				<td width="1%" class="valueStyle">
<%--					<html:text property="chargeEditor" styleClass="writeTextStyle" />--%>
			  		<bean:write name="trace" property="chargeEditor" />
			  </td>
			</tr>
			<tr>
				<td class="labelStyle">
					期&nbsp;&nbsp;&nbsp;&nbsp;第
				</td>
				<td class="valueStyle">
<%--					<html:text property="period" styleClass="writeTextStyle" />--%>
					<bean:write name="trace" property="period" />
				</td>
				<td class="labelStyle">
					出刊日期
				</td>
				<td class="valueStyle">
<%--					<html:text property="createTime" styleClass="writeTextStyle" />--%>
					<bean:write name="trace" property="createTime" />
				</td>
				<td class="labelStyle">
					报送单位
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="sendUnit" size="20"--%>
<%--						styleClass="writeTextStyle" />--%>
					<bean:write name="trace" property="sendUnit" />
				</td>
			<tr>
				<td class="labelStyle">
					品&nbsp;&nbsp;&nbsp;&nbsp;种
				</td>
				<td class="valueStyle" colspan="4">
					<bean:write name="trace" property="dictProductType" />
				</td>
				<td class="labelStyle">
					版&nbsp;&nbsp;&nbsp;&nbsp;别
				</td>
				<td class="valueStyle">
					<bean:write name="trace" property="dictFocusType" />
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
					<bean:write name="trace" property="state" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					主&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="4">
					<bean:write name="trace" property="chiefTitle" />
				</td>
				<td class="labelStyle">
					副&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="3">
					<bean:write name="trace" property="subTitle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle" colspan="8">
					<bean:write name="trace" property="summary" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					正&nbsp;&nbsp;&nbsp;&nbsp;文
				</td>
				<td colspan="8">
<%--					<FCK:editor instanceName="fucosContent">--%>
						<bean:write name="trace" property="fucosContent" />
<%--					</FCK:editor>--%>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					领导批示
				</td>
				<td class="valueStyle" colspan="7">
					<bean:write name="trace" property="leadInstruction" />
				</td>
				<!--
				<td class="labelStyle" rowspan="4">
					上传标识：<br />
					可以上传多个不同格式的符件<br />
					以其中最后一个图片作为标识！
				</td>
				<td class="labelStyle" rowspan="4" colspan="2" width="180"
					style="text-indent: 0px;">
					<iframe frameborder="0" width="100%" scrolling="No" src="../upload/up2.jsp" allowTransparency="true"></iframe>
				</td>
				-->
			</tr>

		</table>
		<br />
		<br />
	</logic:iterate>
  </body>
</html:html>
