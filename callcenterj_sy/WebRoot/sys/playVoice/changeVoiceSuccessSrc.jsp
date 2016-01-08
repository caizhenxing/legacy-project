<%@ page language="java" pageEncoding="GB18030"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%@ include file="../../style.jsp"%>
<%
	response.setHeader("Expires", "0");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>播放语音</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
			type="text/css" />
		<style type="text/css">
	<!--
	/*页面边距*/
	body {
		margin-left: 1px;
		margin-top: 1px;
		margin-right: 1px;
		margin-bottom: 1px;
	}
	.text-selected{
		background: #ccffff;
	}
	-->
	</style>
		<script language="javascript">
		var filePath = 0;
		<%
			String filePath = (String)request.getAttribute("filePath");
			if(filePath != null)
			{
				out.print(" filePath = '"+filePath+"'");
			}
		%>
		//播放语音
		if(filePath!=0)
		{
			//语音转换成功了 到座席面板 播放语音
			
			playComposeVoice(filePath);
			window.close();
		}
		//多语音播放的函数
		function playComposeVoice(filePath)
		{
			opener.document.getElementById('txtS_ivrtype').value=filePath;
			opener.document.getElementById('txtOperationType').value='播放语音.vds';
			opener.document.getElementById('btnMultiIVR').click();
		}
	</script>
	</head>

	<body>
		<form action="./../playVoice.do?method=toCharacter2VoiceFile"
			method="post">
			<table width="400" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="400" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="3">
									<img
										src="./callcenterj_sy/../../style/<%=styleLocation%>/images/bofang/bf1.jpg"
										width="400" height="52" />
								</td>
							</tr>
							<tr>
								<td>
									<img src="/callcenterj_sy/../../style/<%=styleLocation%>/images/bofang/bf2.jpg"
										width="56" height="346" />
								</td>
								<td width="282" align="center"
									background="../../style/<%=styleLocation%>/images/bofang/bf4.jpg">
									<textarea name="affixTxt" class="lundong" id="tZhantei"></textarea>
								</td>
								<td>
									<img
										src="./../../style/<%=styleLocation%>/images/bofang/bf3.jpg"
										width="62" height="346" />
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<table width="400" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<img
													src="./../../style/<%=styleLocation%>/images/bofang/bf6.jpg"
													width="177" height="32" />
											</td>
											<td width="161" align="center"
												background="../../style/<%=styleLocation%>/images/bofang/bf8.jpg">
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															&nbsp;
														</td>
														<td>
															&nbsp;
														</td>
														<td>
															&nbsp;
														</td>
													</tr>
												</table>
											</td>
											<td>
												<img
													src="./../../style/<%=styleLocation%>/images/bofang/bf7.jpg"
													width="62" height="32" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<img src="../../style/<%=styleLocation%>/images/bofang/bf5.jpg"
										width="400" height="20" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
