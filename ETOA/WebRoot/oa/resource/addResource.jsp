<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>添加资源信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../../css/styleA.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<html:form action="/absenceWork.do" method="POST">
		<table width="60%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#d8d8e5">
			<tr>
				<td colspan="2" align="center" class="tdbgpic1">
					添加资源信息
				</td>
			</tr>
			<tr>
				<td width="31%" align="right" class="tdbgcolor1">
					&nbsp;

				</td>
				<td width="69%" class="tdbgcolor1">
					&nbsp;

				</td>
			</tr>
			<tr>
				<td align="right" class="tdbgcolor2">
					资源类型：
				</td>
				<td class="tdbgcolor2">
					<html:select property="resourceType">
						<html:option value="">
                请选择
              </html:option>
						<html:optionsCollection name="ctreelist" label="label" value="value" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td align="right" class="tdbgcolor1">
					<span class="tdbgcolor2"><a href="#">资源负责人：</a></span>
				</td>
				<td class="tdbgcolor1">
					<span class="tdbgcolor2"><html:text property="resourcePrincipal" maxlength="10" size="10" /></span>
				</td>
			</tr>
			<tr>
				<td align="right" class="tdbgcolor2">
					自然状况：
				</td>
				<td class="tdbgcolor2">
					<html:textarea property="resourceThing" rows="6" cols="40" />
				</td>
			</tr>
			<tr>
				<td align="right" class="tdbgcolor1">
					备 注：
				</td>
				<td class="tdbgcolor1">
					<span class="tdbgcolor2"><html:textarea property="resourceRemark" rows="6" cols="30" /></span>
				</td>
			</tr>
			<tr>
				<td align="right" class="tdbgcolor1">
					&nbsp;

				</td>
				<td class="tdbgcolor1">
					&nbsp;

				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" class="tdbgcolor1">
					<html:submit property="submitB" value="提 交" />
					&nbsp;&nbsp;
					<html:reset property="resetB" value="重 置" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
