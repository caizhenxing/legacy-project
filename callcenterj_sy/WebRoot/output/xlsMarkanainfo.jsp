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
    
    <title>�г�������</title>
    
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
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="frontFor" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
					<bean:write name="mark" property="frontFor"  />
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
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
					֧������
				</td>
				<td class="valueStyle">
<%--					<html:text property="supportTel" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="supportTel"  />
				</td>
				<td class="labelStyle">
					֧����վ
				</td>
				<td class="valueStyle">
<%--					<html:text property="supportSite" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="supportSite"  />
				</td>
				<td class="labelStyle">
					��ϵ����
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="contactMail" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="contactMail"  />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
<%--					<html:text property="chiefEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="chiefEditor"  />
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle">
<%--					<html:text property="subEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="subEditor"  />
				</td>
				<td class="labelStyle">
					��ϯ�༭
				</td>
				<td class="valueStyle">
<%--					<html:text property="firstEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="firstEditor"  />
				</td>
				<td class="labelStyle">
					���α༭
				</td>
				<td class="valueStyle">
<%--					<html:text property="chargeEditor" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="chargeEditor"  />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
<%--					<html:text property="period" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="period"  />
				</td>
				<td class="labelStyle">
					��������
				</td>
				<td class="valueStyle">
<%--					<html:text property="createTime" styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="createTime"  />
				</td>
				<td class="labelStyle">
					���͵�λ
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="sendUnit" size="20"--%>
<%--						styleClass="writeTextStyle" />--%>
					<bean:write name="mark" property="sendUnit"  />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					Ʒ&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle" colspan="4">
					<bean:write name="mark" property="dictProductType" />
<%--					<select name="dictProductType1" class="selectStyle"--%>
<%--						onChange="select1(this)">--%>
<%--						<OPTION value="">--%>
<%--							��ѡ�����--%>
<%--						</OPTION>--%>
<%--						<jsp:include flush="true" page="../priceinfo/select_product.jsp" />--%>
<%--					</select>--%>
<%--					<span id="dict_product_type2_span"> <select--%>
<%--							name="dictProductType2" class="selectStyle"--%>
<%--							onChange="select1(this)">--%>
<%--							<OPTION value="">--%>
<%--								��ѡ��С��--%>
<%--							</OPTION>--%>
<%--						</select>--%>
<%--					</span>--%>
<%--					<span id="product_name_span"> --%>
<%--						<logic:empty name="markanainfoBean" property="dictProductType">--%>
<%--							<select name="dictProductType" class="selectStyle" style="width:70px">--%>
<%--								<OPTION value="">��ѡ������</OPTION>--%>
<%--							</select>--%>
<%--						</logic:empty>--%>
<%--						<logic:notEmpty name="markanainfoBean" property="dictProductType">--%>
<%--								<html:text property="dictProductType" styleClass="writeTextStyle" />--%>
<%--						</logic:notEmpty>--%>
<%--					</span>--%>
				</td>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
				</td>
				<td class="valueStyle">
				<bean:write name="mark" property="dictCommentType" />
<%--											<html:select property="dictCommentType" styleClass="selectStyle">--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--						<html:option value="����">����</html:option>--%>
<%--					</html:select>--%>
				</td>
				<td class="labelStyle">
					���״̬
				</td>
				<td class="valueStyle">
<%--					<jsp:include flush="true" page="../flow/incState.jsp?form=markanainfoBean"/>--%>
					<bean:write name="mark" property="state" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="4">
<%--					<html:text property="chiefTitle" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<bean:write name="mark" property="chiefTitle" />
				</td>
				<td class="labelStyle">
					��&nbsp;��&nbsp;��
				</td>
				<td class="valueStyle" colspan="3">
<%--					<html:text property="subTitle" styleClass="writeTextStyle"--%>
<%--						size="35" />--%>
					<bean:write name="mark" property="subTitle" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					ժ&nbsp;&nbsp;&nbsp;&nbsp;Ҫ
				</td>
				<td class="valueStyle" colspan="8">
<%--					<html:text property="summary" styleClass="writeTextStyle" size="124" />--%>
<%--					<font color="#ff0000" id="fontStyle">*</font>--%>
					<bean:write name="mark" property="summary" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					��&nbsp;&nbsp;&nbsp;&nbsp;��
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
