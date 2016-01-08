<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="et.po.OperInquiryCard" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="excellence.framework.base.dto.impl.DynaBeanDTO" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="et.po.OperInquiryinfo" />
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>

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
	<logic:iterate id="list" name="resultlist">
		<table width="100%" align="center" cellpadding="0" cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle" align="center">
					�������
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.dictInquiryType}
				</td>
				<td class="labelStyle" align="center">
					��ʼʱ��
				</td>
				<%
					Map map = (Map) pageContext.getAttribute("list");
					OperInquiryinfo po2 = (OperInquiryinfo) map.get("one");

					String begin = "";
					if (po2.getBeginTime() != null) {
						begin = po2.getBeginTime().toLocaleString();
						begin = begin.substring(0, begin.indexOf(" "));
					}
					pageContext.setAttribute("begin", begin);

					String end = "";
					if (po2.getBeginTime() != null) {
						end = po2.getEndTime().toLocaleString();
						end = end.substring(0, end.indexOf(" "));
					}
					pageContext.setAttribute("end", end);
				%>
				<td class="valueStyle" align="center">
					${begin}
				</td>
				<td class="labelStyle" align="center">
					�������
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.organiztion}
				</td>
			</tr>
			<tr>
				<td class="labelStyle" align="center">
					�ʾ�����
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.topic}
				</td>
				<td class="labelStyle" align="center">
					����ʱ��
				</td>
				<td class="valueStyle" align="center">
					${end}
				</td>
				<td class="labelStyle" align="center">
					��&nbsp;֯&nbsp;��
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.organiztion}
				</td>
			</tr>
			<tr>
				<td class="labelStyle" align="center">
					����Ŀ��
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.aim}
				</td>
				<td class="labelStyle" align="center">
					���״̬
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.state}
				</td>
				<td class="labelStyle" align="center">
					������Ա
				</td>
				<td class="valueStyle" align="center">
					${pageScope.list.one.actors}
				</td>
			</tr>
			<tr>
				<td class="labelStyle" align="center" colspan="2">
					��������
				</td>
				<td class="labelStyle" align="center" colspan="2">
					��������
				</td>
				<td class="labelStyle" align="center" colspan="2">
					����𰸱�ѡ���ͬ��ѡ����;�Ÿ�����˫���Զ���д
				</td>
			</tr>
			<%
				Map num = (Map) pageContext.getAttribute("list");
				int size = Integer.parseInt(num.get("four").toString());
				List li = (List) num.get("two");
				out.println(li.size());
				pageContext.setAttribute("li", li);
				for (int i = 0; i < size; i++) {
					DynaBeanDTO dto = (DynaBeanDTO) li.get(i);
					pageContext.setAttribute("dictQuestionType", dto.get("dictQuestionType").toString());
					pageContext.setAttribute("question", dto.get("question").toString());
					pageContext.setAttribute("alternatives", dto.get("alternatives").toString());
			%>
			<tr>
				<td class="valueStyle" align="center" colspan="2">
					${dictQuestionType}
				</td>
				<td class="valueStyle" align="center" colspan="2">
					${question}
				</td>
				<td class="valueStyle" align="center" colspan="2">
					${alternatives}
				</td>
			</tr>
			<%
			}
			%>
		</table>
		<br />
		<br />
	</logic:iterate>
</body>
</html:html>
