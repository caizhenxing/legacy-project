<%@ page contentType="text/html; charset=gbk"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<html>
	<link href="../info/1/style.css" rel="stylesheet" type="text/css" />
	<body>
		<table width="1240" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="248" height="40" class="Title">
					专家推介
				</td>
				<td width="20">
					&nbsp;
				</td>
				<td width="993" class="Slogan">
					心系农业 情系农村 爱系农民
				</td>
			</tr>
			<tr>
				<td width="248" class="Subtitle2">
					专家照片
				</td>
				<td>
					&nbsp;
				</td>
				<td width="993" class="Subtitle2">
					专家简介
				</td>
			</tr>
		</table>

		<marquee direction="up" width="100%" height="530" SCROLLAMOUNT="5">

			<table width="1240" border="0" align="center" cellpadding="0" cellspacing="0" style="vertical-align: top;">
<logic:iterate id="pagelist" name="list" indexId="i">
				<tr bordercolor="#E1E8F0">
					<td width="240" height="300" class="Left_Case">
						<div class="div4">
							<img src="export.do?method=toExportPhoto&&id=<bean:write name="pagelist" property="id"/>" width="200" height="300" />
						</div>
					</td>
					<td width="20">
						&nbsp;
					</td>
					<td width="1000" class="Odd_Case">
						<div class="div3">
							<bean:write name="pagelist" property="name"/>
							<br><br>
							<bean:write name="pagelist" property="remark"/>
						</div>
					</td>
				</tr>
</logic:iterate>
			</table>

		</marquee>
</body>
</html>