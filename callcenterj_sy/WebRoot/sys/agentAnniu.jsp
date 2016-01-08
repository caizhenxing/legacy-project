<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">
<head>
	<base href="http://192.168.1.16:8080/callcenterj_sy/sys/agentAnniu.jsp">

	<title>座席常用功能导航按钮</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="./../style/chun/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="./../js/common.js"></script>
	<script language="javascript" src="./../js/public.js"></script>
	<script type="text/javascript">
	//来电用户信息 http://192.168.1.16:8080/callcenterj_sy/custinfo/custinfo.do?method=toCustinfoMain
 	function navIncommingUser()
 	{
 		var url = '../custinfo/custinfo.do?method=toCustinfoMain';
 		window.parent.frames['contents'].document.location.href=url;
 	}
 	//服务记录查询
 	function navServicegQuery()
 	{
 		var url = '../incomming/incommingInfo.do?method=toIncommingInfoMain';
 		window.parent.frames['contents'].document.location.href=url;
 	}
 	function navIncommingInfoQuery()
 	{
 		///callcenterj_sy/sms/columnInfo.do?method=toMessageColumnInfoMain
 		var url = '../callcenter/cclog/telQuery.do?method=toMain';
 		window.parent.frames['contents'].document.location.href=url;
 	}
 </script>
    <style type="text/css">
    	/* CSS Document */
		<!--
		.anniu{
			border:0px;
			font-family: "宋体";
			font-size: 12px;
			line-height: 20px;
			font-weight: normal;
			color: #333333;
			background-image: url(./../style/chun/images/annuBig.jpg);
			text-align: center;
			height: 22px;
			width: 150px;
		}
		.anniuM{
			border:0px;
			font-family: "宋体";
			font-size: 12px;
			line-height: 20px;
			font-weight: normal;
			color: #333333;
			background-image: url(./../style/chun/images/annuMin.jpg);
			text-align: center;
			height: 22px;
			width: 150px;
		}
	.buttonStyle2{
	font-family: "宋体";
	font-size: 12px;
	color: red;
	background-color: #7EB0D5;
	text-align: center;
}
    a:link {
	color: #FFFFFF;
	text-decoration: none;
}
    a:visited {
	text-decoration: none;
	color: #FFFFFF;
}
    a:hover {
	text-decoration: none;
	color: #FFFFFF;
}
    a:active {
	text-decoration: none;
	color: #FFFFFF;
}
.buttonStyle2{
	font-family: "宋体";
	font-size: 12px;
	color: #FFFFFF;
	background-color: #7EB0D5;
	text-align: center;
}
-->
</style></head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>
<body style="margin:0px;padding:0px;">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
		<tr>
			<td width="100%" height="28" valign="bottom" class="midlistTitleStyle" style="text-align:left; padding-right:10px;"><table width="462" border="0" cellspacing="2" cellpadding="0" >
                   <tr>
                     <td width="12" height="20">&nbsp;</td>
                     <td width="146" class="buttonStyle2"><a href="javascript:navIncommingUser()">来电用户信息</a></td>
                     <td width="146" class="buttonStyle2"><a href="javascript:navServicegQuery()">服务记录查询</a></td>
                     <td width="146" class="buttonStyle2"><a href="javascript:navIncommingInfoQuery()">来电信息查询</a></td>
                   </tr>
                 </table></td>
			<td>
				

			</td>
		</tr>
	</table>
</body>
</html>
