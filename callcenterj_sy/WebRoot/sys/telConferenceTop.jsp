<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="et.bo.sys.login.bean.UserBean" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html:base />

<%@ include file="../style.jsp"%>
<%
java.text.DateFormat f=new java.text.SimpleDateFormat("yyyy��MM��dd�� EEE");
styleLocation = "chun";
%>
<% 
	UserBean ub = (UserBean)session.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	//��ϯ�������
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
%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>excellence-tech</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.rq {	font-family: "����";
	font-size: 12px;
	font-style: normal;
	line-height: 15px;
	font-weight: normal;
	font-variant: normal;
	color: #FFFFFF;
	text-align: center;
}
-->
</style>
	<link href="../style/<%=styleLocation%>/images/zx/zx.css" rel="stylesheet" type="text/css" />		
	<script language="javascript">
		function goRefresh()
		{
			parent.window.location.reload();   
		}
		function goLogin()
		{
			parent.window.location="../index2.jsp";  
		}
	</script>
	
		<!-- ����dwr -->	
<script type='text/javascript' src='/callcenterj_sy/dwr/interface/confManagerService.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
<script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>
	</head>
	<!-- ocx �ռ�ռ�ÿռ������λ��������ʧ -->
<div style="position:absolute; top:-800px;">
<object id= "xx" classid="clsid:CF850467-2EFA-490D-8702-59905FBA32CB" codebase="./EasyAGC.cab#version=2,0,0,1"  name="ss" width="8" height="8" >
</object>


<script language="javascript" src="./../js/agentOper.js"></script>
<script language="javascript" src="./../js/ajax.js"></script>
<script language="javascript" src="./../js/all.js"></script>
<script language="javascript" src="./../js/agentState.js"></script>
<script language="javascript" src="./../js/packAgentOuter.js"></script>
<script language="javascript" src="./../js/et/tools.js"></script>
<SCRIPT LANGUAGE="JavaScript">
    var obj1 = '';
    function connect(ip)
	{
		var i = obj1.tlaOpen(ip);
		return i;
	}
	function connect(ip)
	{
		  var i = obj1.tlaOpen(ip);
		  return i;
	}
    function onloadInit()
    {
    	obj1 = document.getElementById('xx');
	//alert(serverIp);
    	connect(serverIp);
    }

	function logOutIn(v_id){
		document.getElementById(v_id).style.display="none";
		if(v_id=="span_login"){
			document.getElementById("span_logout").style.display="";	
			loginAction();
		}else{
			document.getElementById("span_login").style.display="";
			logoutAction();		
		}
	}
	function callStartShut(v_id){
		document.getElementById(v_id).style.display="none";
		if(v_id=="span_startCall"){
			document.getElementById("span_shutCall").style.display="";
			startCallAction();
		}else{
			document.getElementById("span_startCall").style.display="";
			shutCallAction();
		}

	}
	function meetStartShut(v_id){		
		document.getElementById(v_id).style.display="none";
		if(v_id=="span_startMeet"){
			document.getElementById("span_shutCallMeet").style.display="";
			startMeetAction();
		}else{
			document.getElementById("span_startMeet").style.display="";
			shutMeetAction();
		}
	}

	
	function loginAction(){
		//document.getElementById("textworkid").value, document.getElementById("textlineid").value, document.getElementById("textgroupid").value
		var i = obj1.tlaLogin(document.getElementById("textlineid").value,document.getElementById("textworkid").value,0); 
		if(i!=0)
		{
			alert("��¼ʧ�� �ߺ�:"+document.getElementById("textlineid").value+";����:"+document.getElementById("textworkid").value+";���:+"+0);
		}
	}
	function logoutAction(){

		var i = obj1.tlaLogoff();
		if(i!=0)
		{
			alert("ע��ʧ��");
		}
	}
	function confMakeCallOut()
	{
		var i = execSimpleMakeCallOut();
		if(i!=0)
		{
			alert('���ʧ��'+i);
		}
	}
	
	function startCallAction(){
		//alert("�������");
		//prepMakeCallOut(); //�����jsp ./operCallOut.jsp
		window.open("./confCallInOut.jsp", "", "width=450,height=102,status=yes,resizable=yes,scrollbars=yes,top=200,left=200");
	}
	function shutCallAction(){
		//alert("�Ҷ����");
		 var i = obj1.tlaOnhook();
		 if(i!=0)
		 {
		 	alert("�Ҷ����ʧ��");
		 }
	}
	function joinInOutConf(telNum)
	{
		var confNum = '1';
		if (confNum != 0) {
			var i = obj1.tlaTransfertoivr("NewConfRoom.vds", telNum);
			if (i == 0) {
				//printAgentState("\u53d1\u8d77\u4f1a\u8bae\u6210\u529f");
			} else {
				alert('�������ʧ��');
			}
			alert(i+":"+telNum);
			return i;
		}
	}
	

	function startMeetAction(){
		//alert("����������");
		//execSimpleConf();
		var i = TransfertoivrAppendParam("ConfRoom.vds", 1);
		if(i!=0)
		{
			alert("�������ʧ��");
		}
	}
	function shutMeetAction(){
		//alert("�����������");
		//�ҵ�����ϯ ��ϯ�һ���ͽ�����
		var i = obj1.tlaOnhook();
		 if(i!=0)
		 {
		 	alert("��������ʧ��");
		 }
	}
	function meetStop()
	{
		if(confirm('ȷ�Ͻ�������'))
		confManagerService.meetExit();

	}
</SCRIPT>
<script language="javascript">
	//dwr����ϯ������ʱ
	function setAgentWorkSum(workSum)
	{
		document.getElementById('txtWorkSumTime').value=workSum;
	}
	//dwr����ϯ����ѯ��
	function setAgentDayQuestionSum(workSum)
	{
		document.getElementById('txtDayIncommingVol').value=workSum;
	}
	//dwr������ϯ��������ͳ��
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
    //�绰���뵯�����ھ��
    var handleOpenWin = -1;
	function openWin(Url)
	{
		var Height = 600;
		var Width = 800;
		var name = "��ϯ���ܵ���";
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
		var name = "��ϯ���ܵ���";
		var Top = (screen.height -Height)/2;
		var Left = (screen.width -Width)/2;
		var screenWin = window.open(Url,name,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0);
		//,"width=" + Width +",height=" + Height +",top=" + Top + ",left=" + Left + "",resizable=0,scrollbars=1,status=0,location=0
		screenWin.focus();
	}
	
	//��ϯ��½�¼�
	function execLogin() {
		alert(confLogin(document.getElementById("textworkid").value, document.getElementById("textlineid").value, document.getElementById("textgroupid").value));
	}
	//��ϯע���¼�
	function execLaLogoff() {
		simpleLogoff();
	}

	function execConfLogin() {
	var i = login(document.getElementById("textworkid").value, document.getElementById("textlineid").value, document.getElementById("textgroupid").value);
	//btnLogin btnLaLogoff
		if (i != 0) {
			alert(" ��¼ʧ�� ");
		}
	}
	//��ϯע���¼�
	function execConfLaLogoff() {
	var i = laLogoff();
	if (i != 0) {
		alert(" ע��ʧ�� ");
	}
	}
	//��ͣ
	function execLaACW() {
	var i = laACW();
	if (i == 0) {
		clickPauseState();
	}
	}
	//����
	function execLaWCA() {
	var i = laWCA();
	if (i == 0) {
		clickGoOnState();
	} else {
	}
	}
	//����һ���ť�һ�
	function softOnHook()
	{

		//execSimpleOnHook();
		 var i = obj1.tlaOnhook();
		 if(i!=0)
		 {
		 	alert("�Ҷ����ʧ��");
		 }
	}
	//�����
	function softAnswer()
	{
		incommingTime = new Date(); //��ϯӦ�� ��¼Ӧ��ʱ��
		execAnswer();
	}
		//��ϯ����������빤��
	function agentJoinConf()
	{
			document.getElementById('txtOutLine').value='12316'
			document.getElementById('execBtnOutCall').click();
	}
</script>


<!-- �����¼�begin -->
<%----%>
<%----%>
<script for="xx" EVENT="TLAECallin">
 //�е绰����

 //��ȡ���к���
 callerImcomming =  tlaGetcallerid();
 getObj('outlineNum').value=callerImcomming;
 //�绰���뵯�� �绰����ҳ��agentCallInOper.html

</script> 

<script for="xx" EVENT="TLAECalloutresult">
  try
  {
	  if (obj1.tlaGetcalloutresult() == 0)
	  {
	  	var outLine = obj1.tlaGetuserdata();
	    //���гɹ� д��ר���ߺ�
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
	    //����ʧ�� ר���ߺ�����-1
	    getObj('txtThreeLinenum').value=-1;
	  	evtChangeAgentState('txtAgentState','����');
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
  	//alert('ת�Ƴɹ�');

  }
  else 	
  {
    //alert("ת��ʧ��!");
  }
}
catch(e)
{
	//alert('error :'+e.name+":"+e.message);
}
</script>
<script for="xx" EVENT="TLAEACW">
  //���ѱ�ǿ��ǿ����ϯ
  //�ص������ӵ绰����
  
  alert("������ת��������ϯ ��ע�⼰ʱ�������У�"); 
</script>
<script for="xx" EVENT="TLAELogoff">
  alert("���ѱ�ǿ��ǿ��ע����"); 
</script>
<script for="xx" EVENT="TLAEClosed">
  alert("�����������ж�");
</script>
<script for="xx" EVENT="TLAECallreleased">
	alert('�����Ѿ��һ�.......');
</script>
<script for="xx" EVENT="TLAEAnswer">
  
  callerImcomming =  tlaGetcallerid();
  getObj('outlineNum').value=callerImcomming;
</script>
<script for="xx" EVENT="TLAEClosed">
  alert("�����жϣ�"); 
  
</script>
<!-- 2009-02-17 ����� ���� �����ֵ������Ѿ��һ� -->
<script for="xx" EVENT="TLAEHoldcallreleased">
  alert('�����ֵ������Ѿ��һ�'); 
</script>
<!-- �����¼�end -->


</div>
	<body onload="onloadInit()">
		<table width="900" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td  height="69" background="../style/chun/images/sy2.jpg"> 
					<img src="../style/chun/images/sy1.jpg" width="825"
						height="98" />	</td>
    <td width="527" align="center">
    	<table width="199" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="65"  colspan="2" >
          <div style="background:url(../style/chun/images/sy2.jpg) ; width:100%;height:62px; position:relative; top:-2px;">
          <br />
          <table width="156" height="50" border="0" align="center" cellpadding="0" cellspacing="2">
            <tr>
              <td width="59" rowspan="2"></td>
              <td width="116" ></td>
            </tr>
            <tr>
              <td ></td>
            </tr>
          </table>
          </div>
         </td>
        </tr>
        <tr>
          <td width="148" height="29" style="border:0px;"><div style="position:relative;padding:0px; width:148px;top:-1px; height:29px;background-image:url(../style/chun/images/sy3.jpg) "></div></td>
          <td style="border:0px;"><div style="position:relative;padding:0px; width:45px;top:-1px;left:-3px; height:29px;background-image:url(../style/chun/images/sy4.jpg) "></div></td>
        </tr>
      </table></td>
  </tr>
</table>
<div style="position:absolute;left:10px;bottom:5px;">		
		<table width="130"  border="0" cellpadding="0" cellspacing="1" >
            <tr >
              <td width="60" height="15"><span class="wenzi7"><bean:write name="userInfoSession" property="userName"/></span></td>             
              <td width="60" ><span class="wenzi7"><bean:message bundle="sys" key="sys.hello" /></span></td>
            </tr>
          </table>							
</div>

<div style="position:absolute;right:10px;top:6px;width:250px;">
	<img src="../style/chun/images/dl58.jpg" width="74" height="19" onclick="goRefresh()" style="display:inline;cursor:pointer;"/>	
	<img src="../style/chun/images/dl56.jpg" width="74" height="19" style="display:inline;cursor:pointer;" onclick="popUp('111','help/help.html','800','600')"/>
	<img src="../style/chun/images/dl57.jpg" width="74" height="19" onclick="goLogin()" style="display:inline;cursor:pointer;" />	
</div>

<div  style="position:absolute;right:18px;bottom:3px;"><font color="#3167A5" size="2em"><%=f.format(new java.util.Date())%></font></div>

<div  style="position:absolute;left:460px;bottom:5px;">
<span id="span_login">
<input type="button" value="��¼" onclick="logOutIn('span_login');" />
</span>
<span id="span_logout" style="display:none">
<input type="button" value="ע��" onclick="logOutIn('span_logout');" />
</span>
<span id="span_startCall">
<input type="button" value="���" onclick="startCallAction()" />
</span>
<span id="span_shutCall" style="display:none">
<input type="button" value="�Ҷ�" onclick="callStartShut('span_shutCall');" />&nbsp;</span><span id="span_outLineJoinMeet">&nbsp;</span><span id="span_outLineJoinMeet"></span><span id="span_startMeet">
</span>
<span>
<input type="button" value="��ϯ����" onclick="agentJoinConf()" />
</span>
<span id="span_shutCallMeet" >
<input type="button" value="��������" onclick="meetStop()" />
</span>
</div>
<!-- һЩ�����ֶ����ڱ�����Ϣ -->
<table width="800px" style="display:none;">
	<tr>
	    <td colspan="3" >ip::<input type="text" id="ctextip" name="textip" value="192.168.1.15"><br><br><br><br></td>
	</tr>
	<tr>
		<td class="big">����::<input type="text" id="textworkid" name="textworkid" size="15" value="<%=ub.getUserId() %>" /><br><br><br><br></td><!-- ub.getUserId() -->
	  
	  <td class="big">�ߺ�::<input type="text" id="textlineid" name="textlineid" size="15" value="<%=ub.getLineId() %>" /><br><br><br><br></td><!-- ub.getLineId() -->
	  
	  <td class="big">���::<input type="text" id="textgroupid" name="textgroupid" size="15" value="<%=1 %>" /><!-- ub.getUserGroup() --><input type="button" id="btnLoginExec" onclick="execLogin()"/><br><br><br><br></td>
	</tr>
	
   <tr>
   		<td><br><br><br><br></td>
		<td><input style="display:inline;" name="textfield" id="txtOutLine" type="text" size="10" /><br><br><br><br></td>
        <td><input id="execBtnOutCall" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="���" onclick="confMakeCallOut()" /><br><br><br><br></td>
     </tr>
</table> 
<input name="outputID" type="hidden" id="outputID" value="" />  
<!-- �������� -->
<div style="display:none;">
<!-- ivr ��һ������ڵ�Ҫ���ߺ� -->
<input type="text" id="txtChangeToIvr" />
<input type="text" id="outlineNum" />
<input type="text" id="txtChangeToIvr" />
<input id="btnChangeToIvr" type="button" value="test" onclick="execTransfertoivr('fileName')" />

<!-- ѡ������ ������ܵ��� -->
<%--<select name="select" id="txtInline" onmouseover="createOptionForInnerLine('txtInline')" style="width:88px;" style="display:none">--%>
<%-- 	 <option value="-1">ѡ������</option>--%>
<%--</select>--%>
<!-- ת������� -->
<input type="text" id="txtInline"  value="-1" />
<input id="btnExecChangeInLine" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="ת&nbsp;��&nbsp;��" onclick="execSimpleMakeCallIn()" />
<!-- ����������� -->
<input type="text" id="txtThreeLinenum"  value="-1" /><!-- �������õ� -->
<input id="btnCallThree" value="ִ�к�������" onclick="exexMageCallThree()" /> 
<input id="btnThreeAllOnHook" value="����ȫ��" onclick="threeOnHook()" />
<input id="btnThreeAgentOnHook" value="��ϯ��" onclick="threeAgentOnHook()" />
<input id="btnThreeExpertOnHook" value="������" onclick="threeExpertOnHook()" />
<input type="text" id="btnIsThreeCallExpert"  /><!-- ��ϯ�Ƿ����ں������� �����������ʱ ����atTlaActivate() ʵ������ͨ�� -->
<input type="button" id="btnTlaSwap" onclick="atTlaSwap()" value="�л�����" />
<input type="button" id="btnTlaActivate" onclick="atTlaActivate()" value="������ʽͨ��" />
<!-- ������ -->
<!-- ����ת������� -->
<input type="text" id="textAgentCallThree" />
<input type="text" id="textAgentCallThreePort" />
<input type="button" id="btnAgentCallThree" value="����ר��" onclick="execCallExpert()" />
<!-- ����������ҵ����� -->
<input type="text" id="txtS_ivrtype" />
<input type="text" id="txtOperationType" />
<input type="button" id="btnMultiIVR" value="����������" onclick="execMultiIVR()" />
<!-- ����ҵ����� -->
<input type="text" id="txtConfNum"  value="-1" />
<input id="btnExecConference" style="PADDING-RIGHT: 1px;PADDING-LEFT: 1px" name="btn1" type="button" class="anniu" value="ת&nbsp;��&nbsp;��" onclick="execConf()" />

<input id="txtAgentState" type="text" style="display:none;" />

</div>
</body>
</html>