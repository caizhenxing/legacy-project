<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="../style.jsp"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	UserBean ub = (UserBean) session
			.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	//座席管理面板
	String opseating = "common";
	if (ub != null) {
		opseating = ub.getUserGroup();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>来电信息列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<style type="text/css">
	
	body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.anniu {
	margin-left: 190px;
	margin-right: 10px;
}

.wenzi1 {
	font-size: 13px;
	font-weight: normal;
	color: #000000;
	text-align: left;
}

div.sample_popup { z-index: +3; }
div.menu_form_header{
cursor:move
}
div.sample_popup div.menu_form_header
{
border-bottom: 0px;
cursor: default;
cursor:move;
width: 194px;
height: 20px;
line-height:24px;
text-align: left;
background:#A5C8F0;
color:#FFF;
text-decoration: none;
font-weight: 900;
font-size: 13px;
padding-left:1px;
overflow:hidden;
}
div.sample_popup div.menu_form_body
{
border: 3px solid #A5C8F0;
width: 194px;
background:#FFF;
font-size:12px;
text-align: center;
}
div.sample_popup img.menu_form_exit
{
float: right;
margin: 5px 5px 0px 0px;
cursor: pointer;
}

div.sample_popup form
{
margin: 0px;
padding: 8px 10px 10px 10px;
}
.menu_form_body a {
text-decoration: none;
}

</style>

	<script language="javascript" src="../js/tools.js"></script>
	<script language="javascript" src="../js/common.js"></script>
	<!-- 弹出提示层 -->
	<script type="text/javascript" src="../js/et/multi_popup.js"></script>


	<script language="javascript">
	
	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	
    	function openwin(){
    		var telNum = document.getElementById("txtPhoneId").value;
    		if(telNum!=''){
    			var element = document.getElementById('popup1');
				element.style.visibility = 'hidden';
				element.style.display = 'none';
    			var url = "./../custinfo/openwin.do?method=toOpenwinLoad&tel="+telNum;
				OpenWin2(url,'',width=840,height=575);
				
			}else{
    			alert("请输入电话号码！");
    			document.getElementById("txtPhoneId").focus();
    			return false;
    		}
    		
    	}
    	
    	function OpenWin2(Url,name,Width,Height) {
		//function OpenWin(Url,name,Width,Height,features) {
<%--		var Top = (screen.height -Height)/2;--%>
		var Top = screen.height - screen.height * 0.9 ;
		var Left = screen.width - screen.width * 0.9 ;
		var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + ",resizable=0,scrollbars=1,status=0,location=0");
		screenWin.focus();
		}
    	
    	function searchTel(tel) {
			parent.parent.document.getElementById('txtTelId').value = tel;
			parent.parent.document.getElementById('btnSearchTel').click();
		}
    	
    	
    </script>
</head>

<body class="listBody">
	<html:form action="/sys/group/Group" method="post">
	

	
	
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle">
					来电
				</td>
				<td class="listTitleStyle">
					受理时间
				</td>
				<td class="listTitleStyle">
					咨询栏目
				</td>
				<td class="listTitleStyle">
					咨询内容
				</td>
				<td class="listTitleStyle">
					受理工号
				</td>
				<td class="listTitleStyle">
					解决状态
				</td>
				<td class="listTitleStyle">
					解决方式
				</td>
				<td class="listTitleStyle">
					是否回访
				</td>
				<td class="listTitleStyle">
					受理专家
				</td>

				<td class="listTitleStyle">
					操作
				</td>
			</tr>

			<logic:iterate id="c" name="list" indexId="i">
				<%
					String style = i.intValue() % 2 == 0 ? "oddStyle"
										: "evenStyle";
				%>
				<tr>
					<td >
						<a href="javascript:searchTel('<bean:write name='c' property='tel_num' filter='true'/>')">
							<bean:write name='c' property='tel_num' filter='true' /> </a>
					</td>
					<td >
						<bean:write name="c" property="addtime" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="dictQuestionType1" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="questionContent" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="respondent" filter="true" />
					</td>

					<td >
						<bean:write name="c" property="dictIsAnswerSucceed" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="answerMan" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="dictIsCallback" filter="true" />
					</td>
					<td >
						<bean:write name="c" property="expertName" filter="true" />
					</td>

					<td >
						<img alt="详细" src="../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('incommingInfoDtl','../incomming/incommingInfo.do?method=toIncommingInfoLoad&type=detail&id=<bean:write 

name='c' property='id'/>',727,570)"
							width="16" height="16" border="0" />
						<img alt="修改" src="../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('incommingInfoUpdate','../incomming/incommingInfo.do?method=toIncommingInfoLoad&type=update&id=<bean:write 

name='c' property='id'/>',727,570)"
							width="16" height="16" border="0" />
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="9" class="pageTable">
					<page:page name="incommingInfoTurning" style="second" />
				</td>
				<td class="pageTable" width="95px" style="text-align: right">
					<input id="btnAdd" name="btnAdd" type="button" class="buttonStyle"
						value="添加" onclick="popup_show(1)" />
					<div class="sample_popup" id="popup1" style="visibility: hidden; display: none;">
						<div class="menu_form_header" id="popup_drag1">
						<img class="menu_form_exit" id="popup_exit1" src="../images/close.png"/>
						录入电话号码
					</div>
					<div class="menu_form_body">
						请输入电话号码
						<input type="text" id="txtPhoneId" value="" />
						<input type="button" value="确定"  onclick="openwin()"/> 
						<input type="button" value="清空"  onclick="reset()"/>
					<div>
				
				</div>
				</div>

				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
