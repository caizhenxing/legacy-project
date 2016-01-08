<%@ page language="java"  contentType="text/html;charset=GBK"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="et.bo.sys.login.bean.UserBean" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%@ include file="./../style.jsp"%>
<% 
	UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	//座席管理面板
	String opseating = "common";
	String userId = "";
	String userName = "";
	if(ub!=null)
	{
		opseating = ub.getUserGroup();
		userId = ub.getUserId();
		userName = ub.getUserName();
		//System.out.println("userid "+userId+" userName "+userName);
	}
	pageContext.setAttribute("opseating",opseating);
	java.text.DateFormat f=new java.text.SimpleDateFormat("yy-MM-dd HH:mm:ss");
	String nowDate = f.format(new Date());
	String cssinsession = styleLocation;
	String bgColor = "#8FBEDB";
	String tdAColor = "#63A908";
	String tdABorder = "#FFFFFF";
	if("chun".equals(cssinsession))
	{
		bgColor = "#9FD26C";
		tdAColor = "#63A908";
		tdABorder = "#FFFFFF";
	}
	else if("xia".equals(cssinsession))
	{
		bgColor = "#8FBEDB";
		tdAColor = "#4698D7";
		tdABorder = "#FFFFFF";
	}
	else if("qiu".equals(cssinsession))
	{
		bgColor = "#FFDFA0";
		tdAColor = "#FE9A17";
		tdABorder = "#FFDFA0";
	}
	else if("dong".equals(cssinsession))
	{
		bgColor = "#C2DEF3";
		tdAColor = "#A6CFEE";
		tdABorder = "#FFFFFF";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%--<html xmlns="http://www.w3.org/1999/xhtml">--%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>座席面板</title>
<link rel="stylesheet" type="text/css" href="./../style/<%=cssinsession%>/images/zx/zx.css">
<STYLE type=text/css>
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.anniu {
	background-image: url(./../style/<%=cssinsession%>/images/zx/dl8.jpg);
}
.anys{
	font-family: "宋体";
	font-size: 14px;
	font-style: normal;
	line-height: 17px;
	font-weight: normal;
	font-variant: normal;
	color: #FFFFFF;
	background-color: <%=tdAColor%>;
	text-align: center;
	margin: 1px;
	padding: 1px;
	height: 15px;
	width: 200px;
	border: 1px outset <%=tdABorder%>;
}
/*链接样式*/
<%--/* 网站链接总的css定义:可定义内容为链接字体颜色、样式等 */--%>
<%--a{text-decoration: none;} /* 链接无下划线,有为underline */ --%>
<%--a:link {color: #FFFFFF;} /* 未访问的链接 */--%>
<%--a:visited {color: #60A003;} /* 已访问的链接 */--%>
<%--a:hover{color: #60A003;} /* 鼠标在链接上 */ --%>
<%--a:active {color:#FFFFFF;} /* 点击激活链接 */--%>
/* 网站链接总的css定义:可定义内容为链接字体颜色、样式等 */
a{text-decoration: none;} /* 链接无下划线,有为underline */ 
a:link {color: #FFFFFF;} /* 未访问的链接 */
a:visited {color: #FFFFFF;} /* 已访问的链接 */
a:hover{color: #FFFFFF;} /* 鼠标在链接上 */ 
a:active {color:#FFFFFF;} /* 点击激活链接 */
.wenben{
	height:20px;
}
</STYLE>
</head>
	<!-- 引入dwr -->
	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentWorkSumTimeService.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentDayQuestionVolumeService.js'></script>
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentIncommingNoteService.js'></script>	
  	<script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentListService.js'></script>
 	<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	

<!-- ocx 空间占用空间调整它位置让它消失 -->
<div style="position:absolute; top:-800px;">
<object id= "xx" classid="clsid:CF850467-2EFA-490D-8702-59905FBA32CB" codebase="./EasyAGC.cab#version=2,0,0,1"  name="ss" width="8" height="8" >
</object>
<script language="javascript" src="./../js/agentOper.js"></script>
<script language="javascript" src="./../js/ajax.js"></script>
<script language="javascript" src="./../js/all.js"></script>
<script language="javascript" src="./../js/agentState.js"></script>
<script language="javascript" src="./../js/packAgentOuter.js"></script>
<script language="javascript" src="./../js/et/tools.js"></script>

<script language="javascript">
	var isTreeHold = 0;
	//dwr设座席工作总时
	function setAgentWorkSum(workSum)
	{
		document.getElementById('txtWorkSumTime').value=workSum;
	}
	//dwr设座席日咨询量
	function setAgentDayQuestionSum(workSum)
	{
		document.getElementById('txtDayIncommingVol').value=workSum;
	}
	//dwr设置座席当日来电统计
	function setAgentDayIncomming(tells)
	{
		if(tells!='')
		{
			var slt = document.getElementById('incomingNodeSelect');
			if(slt)
			{
				var length = slt.length;
				if(length>0)
				{
					for(var i=1; i<length; i++)
					{
						slt.options[1] = null;
					}
				}
				var sArr = tells.split(":");
				for(var i=0; i<sArr.length; i++)
				{
				
					slt.options[slt.length] = new Option(sArr[i], sArr[i]);
				}
			}
		}
	}
    //电话呼入弹出窗口句柄
    var handleOpenWin = -1;
	function openWin(Url)
	{
		var Height = 600;
		var Width = 800;
		var name = "座席功能导航";
		var Top = (screen.height -Height)/2;
		var Left = (screen.width -Width)/2;
		var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0);
		//,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0,location=0
		screenWin.focus();
	}
	function openWHWin(Url,width,height)
	{
		var Height = height;
		var Width = width;
		var name = "座席功能导航";
		var Top = (screen.height -Height)/2;
		var Left = (screen.width -Width)/2;
		var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0);
		//var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0);
		//,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0,location=0
		screenWin.focus();
	}
	
	//座席登陆事件
	function execLogin() {
	var i = login(document.getElementById("textworkid").value, document.getElementById("textlineid").value, document.getElementById("textgroupid").value);
	//btnLogin btnLaLogoff
	if (i == 0) {
		clickLoginState();
		agentListService.addInAgentList(<%=userId%>,'<%=userName%>');
	}
	}
	//座席注销事件
	function execLaLogoff() {
	var i = laLogoff();
	if (i == 0) {
		clickLogoffState();
		agentListService.removeAgentInList(<%=userId%>);
	}
	}
	//暂停
	function execLaACW() {
	var i = laACW();
	if (i == 0) {
		clickPauseState();
	}
	}
	//就绪
	function execLaWCA() {
	var i = laWCA();
	if (i == 0) {
		clickGoOnState();
	} else {
	}
	}
	//点击挂机按钮挂机
	function softOnHook()
	{
		//挂机时取服务总时
  agentWorkSumTimeService.getWorkSumTime(getObj('textworkid').value,'',setAgentWorkSum);
  //挂机时取日资讯量
  agentDayQuestionVolumeService.getDayQuestionVolume(getObj('textworkid').value,'', setAgentDayQuestionSum);
  getCurTalkTime(incommingTime,'txtCurTalkTime'); //座席挂机显示通话时间
  hookOnState();
  changeIncomingIcon(1, 'imgIncoming');
  evtChangeAgentState('txtAgentState','通话结束'); 
  var i = execOnHook();
  alert(i);
	}
	//软接听
	function softAnswer()
	{
		incommingTime = new Date(); //座席应答 记录应答时间
		execAnswer();
	}
	function loginLogic()
	{
		execLogin();
<logic:present name="opseating">
	<logic:equal name="opseating" value="opseating">
//班长座席登录设置优先级为最后登录 2009-03-04
atMonitorLevel();
	</logic:equal>
</logic:present>
	}
</script>


<!-- 捕获事件begin -->
<%----%>
<%----%>
<script for="xx" EVENT="TLAECallin">
 //有电话进来
 hasTelState();
 changeIncomingIcon(0, 'imgIncoming');
 //获取主叫号码
 callerImcomming =  tlaGetcallerid();
 getObj('outlineNum').value=callerImcomming;
 //电话打入弹出 电话接听页面agentCallInOper.html
 handleOpenWin = MyOpenWin('./agentCallInOper.html','电话呼入',540,280);
 //document.getElementById("btnAnswer").click(); //这个是处理弹屏页面
</script> 

<script for="xx" EVENT="TLAECalloutresult">
  try
  {
	  if (obj1.tlaGetcalloutresult() == 0)
	  {
	  	var outLine = obj1.tlaGetuserdata();

	    //呼叫成功 写入专家线号
	    getObj('txtThreeLinenum').value=outLine;
	    var threeState = getObj('btnIsThreeCallExpert').value;
	    if(threeState == 1)
	    {
	    	//atTlaActivate();
	    	threeState = 0;
	    }
	  }
	  else 	
	  {
	    //呼叫失败 专家线号设置-1
	    getObj('txtThreeLinenum').value=-1;
	  	evtChangeAgentState('txtAgentState','呼叫');
	  }
  }
  catch(e)
  {
  	//alert(e.name + ':' + e.message);
  }
</script>
<script for="xx" EVENT="TLAETransferresult">
try
{
//tlaGettansferresult
  if (obj1.tlaGettansferresult() == 0)
  {
  	//alert('转移成功');
    changeIncomingIcon(1, 'imgIncoming'); //普通
  }
  else 	
  {
    //alert("转移失败!");
  }
  evtChangeAgentState('txtAgentState','转移'); 
}
catch(e)
{
	//alert('error :'+e.name+":"+e.message);
}
</script>
<script for="xx" EVENT="TLAEACW">
  //您已被强制强行离席
  //关掉弹出接电话窗口
  try
  {
     if(handleOpenWin != -1)
     {
	handleOpenWin.close();
	handleOpenWin = -1;
     }
  }
  catch(e)
  {
      alert('error :'+e.name+":"+e.message);
  }
  alert("呼叫已转入其他座席 请注意及时接听呼叫！"); 
  evtChangeAgentState('txtAgentState','呼叫已转入其他座席 请注意及时接听呼叫！'); 
  strongPauseState();
</script>
<script for="xx" EVENT="TLAELogoff">
  alert("您已被强制强制注销！"); 
  evtChangeAgentState('txtAgentState','您已被强制强制注销'); 
  strongLogoffState();
</script>
<script for="xx" EVENT="TLAEClosed">
  changeNetStateIcon(1, 'imgNetState');
</script>
<script for="xx" EVENT="TLAECallreleased">
	alert('对方已经挂机.......');
  //getWorkSumTime('txtWorkSumTime',getObj('textworkid').value);//挂机时取服务总时
  //挂机时取服务总时
  agentWorkSumTimeService.getWorkSumTime(getObj('textworkid').value,'',setAgentWorkSum);
  //挂机时取日资讯量
  agentDayQuestionVolumeService.getDayQuestionVolume(getObj('textworkid').value,'', setAgentDayQuestionSum);
  getCurTalkTime(incommingTime,'txtCurTalkTime'); //座席挂机显示通话时间
  if(isTreeHold==1)
  {
  	acceptTelState();
  	isTreeHold = 0;
  }else
  {
  	hookOnState();
  }
  changeIncomingIcon(1, 'imgIncoming');
  evtChangeAgentState('txtAgentState','通话结束'); 
</script>
<script for="xx" EVENT="TLAEAnswer">
  
  callerImcomming =  tlaGetcallerid();
  getObj('outlineNum').value=callerImcomming;
  //摘机时取服务总时
  agentWorkSumTimeService.getWorkSumTime(getObj('textworkid').value,'',setAgentWorkSum);
  //摘机时取日资讯量
  agentDayQuestionVolumeService.getDayQuestionVolume(getObj('textworkid').value,'', setAgentDayQuestionSum);
  
  acceptTelState();
    try
  {
     if(handleOpenWin != -1)
     {
	handleOpenWin.close();
	handleOpenWin = -1;
     }
  }
  catch(e)
  {
      alert('error :'+e.name+":"+e.message);
  }
  openWinIncoming(callerImcomming);
  evtChangeAgentState('txtAgentState','应答'); 
</script>
<script for="xx" EVENT="TLAEClosed">
  netCloseState();
  changeIncomingIcon(0, 'imgIncoming'); //普通图标
  alert("网络中断！"); 
  
</script>
<!-- 2009-02-17 加入的 功能 被保持的外线已经挂机 -->
<script for="xx" EVENT="TLAEHoldcallreleased">
	//alert('被保持的外线已经挂机');
  //挂机时取服务总时
  agentWorkSumTimeService.getWorkSumTime(getObj('textworkid').value,'',setAgentWorkSum);
  //挂机时取日资讯量
  agentDayQuestionVolumeService.getDayQuestionVolume(getObj('textworkid').value,'', setAgentDayQuestionSum);
  getCurTalkTime(incommingTime,'txtCurTalkTime'); //座席挂机显示通话时间
   if(isTreeHold==1)
  {
  	acceptTelState();
  	isTreeHold = 0;
  }else
  {
  	hookOnState();
  }
  changeIncomingIcon(1, 'imgIncoming');
  evtChangeAgentState('txtAgentState','被保持的外线已经挂机'); 
</script>
<!-- 捕获事件end -->


</div>


<body onload="bodyOnLoad()" style="margin-top:-25px;padding-top:0px;" bgcolor="#9ec3eb">
<!-- 输入变量 -->
<form  name="form1" method="post" action="">
    <TABLE width="206" border=0 cellPadding=0 cellSpacing=0 style="margin-top:0px;padding-top:0px;">
  <TBODY bgcolor="#256392">
   <tr>
          <td width="206" height="10" align="center" valign="center" border="0">
          <table width="205" height="25" border="0" cellpadding="0" cellspacing="1" style="margin-top:0px;padding-top:0px;">
            <tr>
              <td width="33" bgcolor="#256392"><span class="wenzi7"><img src="../images/zx/dRen.gif" width="16" height="16" /></span></td>
              <td width="60" bgcolor="#256392"><span class="wenzi7"><bean:write name="userInfoSession" property="userName"/></span></td>
              <logic:equal name="userInfoSession" property="userGroup" value="operator">
                <td width="60" bgcolor="#256392"><span class="wenzi7">座席员</span></td>
              </logic:equal>
              <logic:equal name="userInfoSession" property="userGroup" value="opseating">
                <td width="60" bgcolor="#256392"><span class="wenzi7">座席长</span></td>
              </logic:equal>
              <td width="97" bgcolor="#256392"><span class="wenzi7"><bean:message bundle="sys" key="sys.hello" /></span></td>
            </tr>
          </table></td>
        </tr>
   <tr>
       <td width="206" height="20" align="center" valign="center" bgcolor="#9EC3EB" class="biaoti" style="display:none;"><img src="../style/<%=cssinsession%>/images//zx/sy9.gif" width="205" height="45" /></td>
   </tr>
  <TR>
    <TD height=169 align=center vAlign=center bgcolor="#CBCED3"><!-- #CBCED3 #9ec3eb-->
    	<table width="205" border="0" cellpadding="0" cellspacing="0" class="mianban">
      <tr >
        <td width="94"><img src="./../style/<%=cssinsession%>/images//zx/dl15.gif" width="94" height="23" /></td>
        <td width="108" align="center" bgcolor="#CBCED3" >
  			<input id="txtAgentState" name="textfield2" type="text" class="wenben" size="16" />
        </td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl16.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
       		<input id="txtAgentLoginTime" name="textfield2" type="text" class="wenben" size="16" value="<%=nowDate%>"/>
        </td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl17.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
        <input id="txtCurrentTime" name="textfield2" type="text" class="wenben" size="16" />
        <script>
			document.getElementById('txtCurrentTime').value=new Date().format('yy-MM-dd hh:mm:ss');
			setInterval("document.getElementById('txtCurrentTime').value=new Date().format('yy-MM-dd hh:mm:ss');",1000);
		</script>
        </td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl18.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
		<input id="txtDayIncommingVol" name="textfield2" type="text" class="wenben" size="16" />
		</td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl20.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
        <input id="txtWorkSumTime" name="textfield2" type="text" class="wenben" size="16" />
        </td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl21.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
        <input id="txtCurTalkTime" name="textfield2" type="text" class="wenben" size="16" />
        </td>
      </tr>
      <tr>
        <td><img src="./../style/<%=cssinsession%>/images//zx/dl23.gif" width="94" height="23" /></td>
        <td align="center" bgcolor="#CBCED3">
        <select name="textfield10" id="incomingNodeSelect" onmouseover="agentIncommingNoteService.getIncommingNote(getObj('textworkid').value,'',setAgentDayIncomming)" style="width:104px;">
        	<option value="0">查看来电</option>
        </select>
        </td>
      </tr>
    </table></TD>
  </TR>
  <TR>
    <TD height="75" align=center vAlign=center bgColor="#9ec3eb" style=" padding:0; margin:0;">
    <table width="210" height="75" border="0" cellpadding="0" cellspacing="2">
      <tr>
        <td colspan="2">
        	<table cellpadding="0">
          <tr>
            <td height="1"></td>
          </tr>
          </table>
      </td>
        </tr>
      <tr>
         <td height="2"  align="center"><input name="btn1" id="btnLogin" onclick="loginLogic();form1.submit;" type="button" class="anniu" style="display:inline;" value="登&nbsp;&nbsp;&nbsp;&nbsp;录" /><input name="btn1" id="btnLaLogoff" onclick="execLaLogoff()" style="display:none;" type="button" class="anniu" value="注&nbsp;&nbsp;&nbsp;&nbsp;销" /></td>
         <td height="2" align="center"><input id="btnAnswer" onclick="softAnswer()" name="btn1" style="display:inline;" type="button" class="anniu" value="接&nbsp;&nbsp;&nbsp;&nbsp;听" /><input id="btnOnHook" onclick="softOnHook()" name="btn1" style="display:none;" type="button" class="anniu" value="挂&nbsp;&nbsp;&nbsp;&nbsp;断" /></td>
      </tr>
      <tr>
			<td height="2" align="center"><input name="btn1" id="btnLaACW" type="button" onclick="execLaACW()" class="anniu" value="暂&nbsp;&nbsp;&nbsp;&nbsp;停" /><input name="btn1" type="button" id="btnLaWCA" onclick="execLaWCA()" class="anniu" value="开&nbsp;&nbsp;&nbsp;&nbsp;通" style="display:none;" /></td>
            <td height="2" align="center"><input name="btn1" type="button" id="btnHold" class="anniu" onclick="execHold()" value="等&nbsp;&nbsp;&nbsp;&nbsp;待" /><input name="btn1" type="button" id="btnUnHold" onclick="execUnHold()" class="anniu" value="继&nbsp;&nbsp;&nbsp;&nbsp;续" style="display:none;" /></td>
      </tr>
      <tr>
         <td align="center"><input id="btnPrepConference" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="电话会议" onclick="prepConference()" /></td>
         <td align="center"><input id="btnPrepCallExpert" onclick="execPrepCallExpert()" name="Submit32" type="button" class="anniu" value="三方通话"  /></td>
      </tr>
      <tr>
         <td align="center"><input id="btnPrepChangeToIvr" onclick="agentOpenToIvr()" name="btn1" type="button" class="anniu" value="转&nbsp;自&nbsp;动" /><div style="display:none;"><input id="btnVoicePlay" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="语音播放" onclick="execPrepVoicePlay()" style="display:none;"/></div></td>
         <td align="center"><input id="btnOutCall" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="外&nbsp;&nbsp;&nbsp;&nbsp;呼" onclick="prepMakeCallOut()" /></td>
      </tr>
      <tr>
        <td align="center">
           <input id="btnMsgManager" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="消息管理" onclick="openWin('../messages/messages.do?method=toMessagesMain')" />
        </td>
        <td align="center">
         	<input id="btnChangeInLine" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="转&nbsp;内&nbsp;线" onclick="prepMakeCallIn()" />
        </td>
      </tr>
      <tr>
        <td align="center"><input id="btnTelBook" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="电&nbsp;话&nbsp;本" onclick="openWin('../custinfo/phoneinfo.do?method=toPhoneMain')" /></td>
        <td align="center">
            <input id="btnOutlineAppraise" onclick="execOutlineAppraise('座席满意度选择.vds')" name="btn1" type="button" class="anniu" value="用户评价" />
        </td>
      </tr>
       	<logic:present name="opseating">
	<logic:equal name="opseating" value="opseating">
	    <tr>
      <td align="center">
	<input id="btnTest" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="座席长面板" onclick="openWHWin('./AgentMonitorPanel.jsp',800,400)" style="display:block;"/>
	  </td>
      <td></td>
      </tr>
	</logic:equal>
	</logic:present>
    
      <tr>
        <td colspan="2"><table cellpadding="0">
          <tr>
            <td height="1"></td>
          </tr>
        </table></td>
        </tr>
    </table></TD>
  </TR>
  <TR>
    <TD vAlign=center align=center bgColor="#9ec3eb"><table height="25" border="0" align="right" cellpadding="0" cellspacing="1">
      <tr>
        <td>
        	<img src="./../style/<%=cssinsession%>/images//zx/dl7.jpg" width="150" height="6" style="display:none;" />&nbsp;&nbsp;<img id="imgNetState" src="./../style/<%=cssinsession%>/images//zx/dl13.gif" width="16" height="16" style="display:none;" />&nbsp;&nbsp;<img id="imgIncoming" src="./../style/<%=cssinsession%>/images//zx/dl12.gif" width="17" height="16" style="display:none;"/>
        </td>
      </tr>
 </TABLE>
</from>
<!-- 结束输入 -->
<div style="display:none;">
<!-- ivr 有一个特殊节点要外线号 -->
<input type="text" id="txtChangeToIvr" />
<input type="text" id="outlineNum" />
<input type="text" id="txtChangeToIvr" />
<input id="btnChangeToIvr" type="button" value="test" onclick="execTransfertoivr('fileName')" />

<!-- 选则内线 这个功能弹出 -->
<%--<select name="select" id="txtInline" onmouseover="createOptionForInnerLine('txtInline')" style="width:88px;" style="display:none">--%>
<%-- 	 <option value="-1">选择内线</option>--%>
<%--</select>--%>
<!-- 转内线相关 -->
<input type="text" id="txtInline"  value="-1" />
<input id="btnExecChangeInLine" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="转&nbsp;内&nbsp;线" onclick="execMakeCallIn()" />
<!-- 三方呼叫相关 -->
<input type="text" id="txtThreeLinenum"  value="-1" /><!-- 挂三方用的 -->
<input id="btnCallThree" value="执行呼叫三方" onclick="exexMageCallThree()" /> 
<input id="btnThreeAllOnHook" value="三方全挂" onclick="threeOnHook()" />
<input id="btnThreeAgentOnHook" value="座席挂" onclick="threeAgentOnHook()" />
<input id="btnThreeExpertOnHook" value="三方挂" onclick="threeExpertOnHook()" />
<input type="text" id="btnIsThreeCallExpert"  /><!-- 座席是否正在呼叫三方 如果呼叫三方时 调用atTlaActivate() 实现三方通话 -->
<input type="button" id="btnTlaSwap" onclick="atTlaSwap()" value="切换外线" />
<input type="button" id="btnTlaActivate" onclick="atTlaActivate()" value="三方正式通话" />
<!-- 外呼相关 -->
<!-- 结束转内线相关 -->
<input type="text" id="textAgentCallThree" />
<input type="text" id="textAgentCallThreePort" />
<input type="button" id="btnAgentCallThree" value="呼叫专家" onclick="execCallExpert()" />
<!-- 多语音播报业务相关 -->
<input type="text" id="txtS_ivrtype" />
<input type="text" id="txtOperationType" />
<input type="button" id="btnMultiIVR" value="多语音播报" onclick="execMultiIVR()" />
<!-- 会议业务相关 -->
<input type="text" id="txtConfNum"  value="-1" />
<input id="btnExecConference" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="转&nbsp;内&nbsp;线" onclick="execConf()" />

</div>
<table width="800px" style="display:none;">
	<tr>
	    <td colspan="3" >ip::<input type="text" id="ctextip" name="textip" value="192.168.1.15"><br><br><br><br></td>
	</tr>
	<tr>
		<td class="big">工号::<input type="text" id="textworkid" name="textworkid" size="15" value="<%=ub.getUserId() %>" /><br><br><br><br></td><!-- ub.getUserId() -->
	  
	  <td class="big">线号::<input type="text" id="textlineid" name="textlineid" size="15" value="<%=ub.getLineId() %>" /><br><br><br><br></td><!-- ub.getLineId() -->
	  
	  <td class="big">组号::<input type="text" id="textgroupid" name="textgroupid" size="15" value="<%=1 %>" /><!-- ub.getUserGroup() --><input type="button" id="btnLoginExec" onclick="execLogin()"/><br><br><br><br></td>
	</tr>
	
   <tr>
   		<td><br><br><br><br></td>
		<td><input style="display:inline;" name="textfield" id="txtOutLine" type="text" size="10" /><br><br><br><br></td>
        <td><input id="execBtnOutCall" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="外呼" onclick="execMakeCallOut()" /><br><br><br><br></td>
     </tr>
</table> 
<input name="outputID" type="hidden" id="outputID" value="">     
<!-- ************************************************************************ -->
	<table width="100%" >
	<tr>
	<td align="center" style="padding-left:-7px;" bgcolor="#9ec3eb" >
<%--	<newtree:enuRootNav tree="modTreeSession" imgClass="navAgentImg" skins="<%=styleLocation %>" childAction="./agentNav.do?method=toMainControl" childTarget="blank" style="agentNavTbl" onclick="openWin"/>--%>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000162')" ><img src="../style/<%=cssinsession%>/images/ct1.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000167')" ><img src="../style/<%=cssinsession%>/images/ct2.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000461')" ><img src="../style/<%=cssinsession%>/images/ct3.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000170')" ><img src="../style/<%=cssinsession%>/images/ct4.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000261')" ><img src="../style/<%=cssinsession%>/images/ct5.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000000601')" ><img src="../style/<%=cssinsession%>/images/ct6.jpg" border="0"></a><br/>
		<a  href="javascript:openWin('./agentNav.do?method=toMainControl&tree=SYS_TREE_0000002281')" ><img src="../style/<%=cssinsession%>/images/ct7.jpg" border="0"></a><br/>
	</td>
	</tr>
	</table>
	<!--<input type="button" value="test" onclick="MyOpenWin('./agentCallInOper.html','电话呼入',540,280)" />-->
	<!--<input  onclick="execPrepCallExpert()" name="Submit32" type="button" class="anniu" value="测试三方通话"  />-->
<%--	<!-- 2008-08-18 测试文本 --><input type="text" onclick="alert(obj1.tlaTransfertoivr('多语音播报agent.vds','niuyangshichang'))">--%>
<%--	<!-- 2008-02-10 测试座席日资询量统计 --><input type="text" onclick="agentDayQuestionVolumeService.getDayQuestionVolume(getObj('textworkid').value,'2008-06-26', setAgentDayQuestionSum)">--%>
<%--<!-- 2008-02-10 测试座席日服务总时统计 --><input type="text" onclick="agentWorkSumTimeService.getWorkSumTime(getObj('textworkid').value,'2008-06-26',setAgentWorkSum)">--%>
<%--<!-- 2008-02-10 测试座席日来电 --><input type="text" onclick="agentIncommingNoteService.getIncommingNote(getObj('textworkid').value,'2008-06-26',setAgentDayIncomming)">--%>
<%--<input type="button" onclick="execOutlineAppraise('座席满意度选择.vds')" />--%>
<%-- <input type="button" value="外呼" onclick="openWinIncoming('22')" /> --%>
<%--<a href="./AgentMonitorPanel.jsp" target="_blank">test</a>--%>
</body>
</html>

