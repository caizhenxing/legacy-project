<%@ page language="java" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <base href="<%=basePath%>">
    
    <title>市场分析库</title>
    
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
  <logic:iterate id="mark" name="list" type="et.po.OperMarkanainfo">
    <table width="100%"  align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="valueStyle" rowspan="4">
				</td>
				<td class="labelStyle">
					主&nbsp;办&nbsp;方
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="frontFor" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
					<bean:write name="mark" property="frontFor"  />
				</td>
				<td class="labelStyle">
					承&nbsp;办&nbsp;方
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="underTake" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<bean:write name="mark" property="underTake"  />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					支持热线
				</td>
				<td class="valueStyle">
<%--					<html:text property="supportTel" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="supportTel"  />
				</td>
				<td class="labelStyle">
					支持网站
				</td>
				<td class="valueStyle">
<%--					<html:text property="supportSite" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="supportSite"  />
				</td>
				<td class="labelStyle">
					联系邮箱
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="contactMail" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="contactMail"  />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					主&nbsp;&nbsp;&nbsp;&nbsp;编
				</td>
				<td class="valueStyle">
<%--					<html:text property="chiefEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="chiefEditor"  />
				</td>
				<td class="labelStyle">
					副&nbsp;主&nbsp;编
				</td>
				<td class="valueStyle">
<%--					<html:text property="subEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="subEditor"  />
				</td>
				<td class="labelStyle">
					首席编辑
				</td>
				<td class="valueStyle">
<%--					<html:text property="firstEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="firstEditor"  />
				</td>
				<td class="labelStyle">
					责任编辑
				</td>
				<td class="valueStyle">
<%--					<html:text property="chargeEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="chargeEditor"  />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					期&nbsp;&nbsp;&nbsp;&nbsp;第
				</td>
				<td class="valueStyle">
<%--					<html:text property="period" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="period"  />
				</td>
				<td class="labelStyle">
					出刊日期
				</td>
				<td class="valueStyle">
<%--					<html:text property="createTime" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="createTime"  />
				</td>
				<td class="labelStyle">
					报送单位
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="sendUnit" size="20"--%>
<%--						styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="sendUnit"  />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					品&nbsp;&nbsp;&nbsp;&nbsp;种
				</td>
				<td class="valueStyle" colspan="4">
					<bean:write name="mark" property="dictProductType" />
<%--					<select name="dictProductType1" class="selectStyle"--%>
<%--						onChange="select1(this)">--%>
<%--						<OPTION value="">--%>
<%--							请选择大类--%>
<%--						</OPTION>--%>
<%--						<jsp:include flush="true" page="../priceinfo/select_product.jsp" />--%>
<%--					</select>--%>
<%--					<span id="dict_product_type2_span"> <select--%>
<%--							name="dictProductType2" class="selectStyle"--%>
<%--							onChange="select1(this)">--%>
<%--							<OPTION value="">--%>
<%--								请选择小类--%>
<%--							</OPTION>--%>
<%--						</select>--%>
<%--					</span>--%>
<%--					<span id="product_name_span"> --%>
<%--						<logic:empty name="markanainfoBean" property="dictProductType">--%>
<%--							<select name="dictProductType" class="selectStyle" style="width:70px">--%>
<%--								<OPTION value="">请选择名称</OPTION>--%>
<%--							</select>--%>
<%--						</logic:empty>--%>
<%--						<logic:notEmpty name="markanainfoBean" property="dictProductType">--%>
<%--								<html:text property="dictProductType" styleClass="writeTextStyle" />--%>
<%--						</logic:notEmpty>--%>
<%--					</span>--%>
				</td>
				<td class="labelStyle">
					评&nbsp;&nbsp;&nbsp;&nbsp;别
				</td>
				<td class="valueStyle">
				<bean:write name="mark" property="dictCommentType" />
<%--											<html:select property="dictCommentType" styleClass="selectStyle">--%>
<%--						<html:option value="周评">周评</html:option>--%>
<%--						<html:option value="月评">月评</html:option>--%>
<%--						<html:option value="季评">季评</html:option>--%>
<%--						<html:option value="年评">年评</html:option>--%>
<%--					</html:select>--%>
				</td>
				<td class="labelStyle">
					审核状态
				</td>
				<td class="valueStyle">
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=markanainfoBean"/>--%>
					<bean:write name="mark" property="state" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					主&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="4">
<%--					<html:text property="chiefTitle" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<bean:write name="mark" property="chiefTitle" />
				</td>
				<td class="labelStyle">
					副&nbsp;标&nbsp;题
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="subTitle" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
					<bean:write name="mark" property="subTitle" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					摘&nbsp;&nbsp;&nbsp;&nbsp;要
				</td>
				<td class="valueStyle" colspan="8">
<%--					<html:text property="summary" styleClass="writeTextStyle" size="124" />--%>
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<bean:write name="mark" property="summary" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					正&nbsp;&nbsp;&nbsp;&nbsp;文
				</td>
				<td colspan="8" class="valueStyle" style="font-color:#000000">
<%--					<FCK:editor instanceName="markanaContent">--%>
<%--						<jsp:attribute name="value">--%>
<%--							<bean:write name="markanainfoBean" property="markanaContent"--%>
<%--								filter="false" />--%>
<%--						</jsp:attribute>--%>
<%--					</FCK:editor>--%>
						<bean:write name="mark" property="markanaContent" />
				</td>
			</tr>
	</table>
	<br />
	<br />
	</logic:iterate>
  </body>
</html:html>
