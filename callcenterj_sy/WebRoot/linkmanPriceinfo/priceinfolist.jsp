<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>联络员报价列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript" src="../js/common.js"></script>

</head>

<body class="listBody" onload="equalsCheckbox()">

	<table width="100%" align="center" cellpadding="0" cellspacing="1" class="listTable">
		<tr>
			<td class="listTitleStyle" width="50">
				选择
			</td>
			<td class="listTitleStyle" width="80">
				联络员编号
			</td>
			<td class="listTitleStyle" width="100">
				联络员姓名
			</td>
			<td class="listTitleStyle">
				产品名称
			</td>
			<td class="listTitleStyle" width="80">
				报价时间
			</td>
			<td class="listTitleStyle" width="70">
				价格类型
			</td>
			<td class="listTitleStyle">
				审核状态
			</td>
			<td class="listTitleStyle" width="80">
				操作
			</td>

		</tr>
		<logic:iterate id="c" name="list" indexId="i">
			<%
				String style = i.intValue() % 2 == 0 ? "oddStyle" : "evenStyle";
			%>

			<tr style="line-height: 21px;">
				<td>
					<input name="educe" type="checkbox" id="educe"
						onclick="setCheckbox(this)"
						value="<bean:write name='c' property='priceId'/>">
				</td>
				<td>
					<bean:write name="c" property="cust_number" filter="true" />
				</td>				
				<td>
					<bean:write name="c" property="custName" filter="true" />
				</td>
				
				<td>
					<bean:write name="c" property="productName" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="deployTime" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="dictPriceType" filter="true" />
				</td>
				<td>
					<bean:write name="c" property="state" filter="true" />
				</td>
				<td>
					<img alt="详细" src="../style/<%=styleLocation%>/images/detail.gif"
						onclick="popUp('1<bean:write name='c' property='priceId'/>','linkmanpriceinfo.do?method=toOperPriceinfoLoad&type=detail&id=<bean:write name='c' property='priceId'/>',720,260)"
						width="16" height="16" border="0" />
					<img alt="修改" src="../style/<%=styleLocation%>/images/update.gif"
						onclick="popUp('2<bean:write name='c' property='priceId'/>','linkmanpriceinfo.do?method=toOperPriceinfoLoad&type=update&id=<bean:write name='c' property='priceId'/>',720,260)"
						width="16" height="16" border="0" />
					<img alt="删除" src="../style/<%=styleLocation%>/images/del.gif"
						onclick="popUp('3<bean:write name='c' property='priceId'/>','linkmanpriceinfo.do?method=toOperPriceinfoLoad&type=delete&id=<bean:write name='c' property='priceId'/>',720,260)"
						width="16" height="16" border="0" />
				</td>
			</tr>
		</logic:iterate>
		<tr style="line-height: 21px;">
			<td colspan="3" class="pageTable">
				<jsp:include flush="true" page="../output/inc.jsp?dbType=price" />
			</td>
			<td colspan="4" class="pageTable">
				<page:page name="operpriceinfopageTurning" style="second" />
			</td>
			<td style="text-align:right" class="pageTable">
			<%
							UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
							String userGroup = ub.getUserGroup();
							if (!userGroup.equals("operator")) {
			%>	
<%--				<input type="button" name="btnstatistic" value="统计" class="buttonStyle"--%>
<%--					onclick="popUp('statisticWindows','operpriceinfo.do?method=toPopStatistic',500,40)" />--%>
			<%
							}
			%>		
				<input type="button" name="btnadd" value="添加" class="buttonStyle" onclick="popUp('operpriceinfoWindows','linkmanpriceinfo.do?method=toOperPriceinfoLoad&type=insertList',860,312)"/>
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<input type="button" name="btnadd1" value="各产品价格最大值、最小值和平均值统计" class="buttonStyle"
					onclick="parent.document.location.href='./stat/productPriceForDate.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd2" value="各产品的价格数量统计" class="buttonStyle"
					onclick="parent.document.location.href='./stat/priceStatByProduct.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd3" value="各产地各产品的价格最大值、最小值和平均值统计" class="buttonStyle"
					onclick="parent.document.location.href='./stat/priceStatForProduct.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd3" value="座席员受理的价格数量统计" class="buttonStyle"
					onclick="parent.document.location.href='./stat/priceInfo.do?method=toMain'" style="display='none'"/>
					
				<input type="button" name="btnadd3" value="各价格类型下的价格数量统计" class="buttonStyle"
					onclick="parent.document.location.href='./stat/priceInfoForType.do?method=toMain'" style="display='none'"/>
			</td>
		</tr>
	</table>
</body>
</html:html>