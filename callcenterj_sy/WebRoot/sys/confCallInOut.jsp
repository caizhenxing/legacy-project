<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="java.util.StringTokenizer" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="./../style.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>����</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link href="./../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />
	<script language="javascript" src="./../js/common.js"></script>

  	 <script type='text/javascript' src='/callcenterj_sy/dwr/interface/agentListService.js'></script>
 	 <script type='text/javascript' src='/callcenterj_sy/dwr/engine.js'></script>
	 <script type='text/javascript' src='/callcenterj_sy/dwr/util.js'></script>



<script type="text/javascript">
	function execCall()
	{
		var radios=document.getElementsByName("callType");
        for(var i=0;i<radios.length;i++)
        {
            if(radios[i].checked==true)
            {
                if('out'==radios[i].value)
                {
                	goCallOutLine();
                }
                else
                {
                	goChangeInLine();
                }
             }
        }
	}
	function goCallOutLine()
	{
		var outline = document.getElementById('handInputOutLine').value;
		if(outline!=0)
		{
			//opener.document.getElementById
			opener.document.getElementById('txtOutLine').value=outline;
			opener.document.getElementById('execBtnOutCall').click();
			//window.close();
		}
		else
		{
			alert('����д���ߺ���!');
		}
	}
	function goQueryOutLine()
	{
		window.open('../callcenter/userInfo.do?method=toCustinfoMain','','width=800,height=380,status=yes,resizable=yes,scrollbars=yes,top=200,left=200');
	}
	function goChangeInLine()
	{
		var inline = document.getElementById('inLine').value;
		if(inline!=-1)
		{
			opener.document.getElementById('txtInline').value=inline;
			opener.document.getElementById("btnExecChangeInLine").click();
			//window.close();
		}
		else
		{
			alert('��ѡ�����ߺ���!');
		}
	}
	function joinConf()
	{
		var telNum=document.getElementById('handInputOutLine').value;
		if(telNum!=0)
		{
			opener.joinInOutConf(telNum);
		}
		else
		{
			alert('����д���к���');
		}
	}
	function checks()
    {
        var radios=document.getElementsByName("callType");
        for(var i=0;i<radios.length;i++)
        {
            if(radios[i].checked==true)
            {
                if('out'==radios[i].value)
                {
                	document.getElementById('outLine').style.display="inline";
                	document.getElementById('inLine').style.display="none";
                }
                else
                {
                	loadInLine();
                	document.getElementById('outLine').style.display="none";
                	document.getElementById('inLine').style.display="inline";
                }
            }
        }
    }
    
    
    
    //���������û����б�
	function loadInLine(){
		agentListService.getAgentList(addInUserList);
	}
	//��ѡ���ʱ����������û����б�
	function addInUserList(data){
		var agent_num = document.getElementById('inLine');
    	DWRUtil.removeAllOptions(agent_num);
		DWRUtil.addOptions(agent_num,{'-1':'��ѡ����ϯ����'});
		DWRUtil.addOptions(agent_num,data);
	}
	//�Ҷϵ绰
	function execHook(){
		//opener.document.getElementById('btnOnHook').click();
		opener.softOnHook();
	}
</script>
</head>

<body class="loadBody">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle" colspan="2">
					��ǰλ��->����
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px;">
					&nbsp;���к���
				</td>
				<td class="labelStyle">
					&nbsp;<span id="outLine"><input type="text" id="handInputOutLine" class="writeTextStyle" />&nbsp;<input type="button" id="queryOutLine" class="buttonStyle" value="��ѯ" onclick="goQueryOutLine()" /></span><select style="display:none;width:150px;" id="inLine"><option value="-1">ѡ������</select>
				</td>
			</tr>
			<tr>
				<td class="valueStyle">
		
				</td>
				<td class="labelStyle" align="left">
					<input type="radio" name="callType" value="out" onclick="checks()" checked> ���<input type="radio" name="callType" value="in" onclick="checks()" > �ں� &nbsp;<input type="button" value="����" onclick="execCall()"/>&nbsp;<input type="button" value="�Ҷ�" onclick="execHook()"/>&nbsp;<input type="button" value="�������" onclick="joinConf()" />&nbsp;<input type="button" value="�ر�" onclick="window.close()" />
				</td>
			</tr>
			<tr>
				<td class="valueStyle" style="margin-right:10px; height:20px;" colspan="4">
				</td>
			</tr>
		</table>

</body>
</html:html>

