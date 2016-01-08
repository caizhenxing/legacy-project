<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

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

	<title>来电详细操作</title>

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
	<SCRIPT language=javascript src="./../js/calendar.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="./../js/common.js"></script>
	<%--<script language="javascript" src="../js/clockCN.js"></script>--%>
	<script language="javascript" src="./../js/clock.js"></script>
	<SCRIPT language="javascript" src="./../js/form.js"
		type=text/javascript></SCRIPT>
	<script language="javascript" src="./../js/ajax.js"></script>
	<script language="javascript" src="./../js/all.js"></script>

	<script type="text/javascript">
	function selecttype1(){
		//专家类别id
		var billnum = document.getElementById('billNum').value;
		getClassExpertsInfo('expertName','',billnum);
		//动态生成的select id 为 expert_name
		
	}
			
			
		function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:182px;dialogHeight:215px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
			
	function comptime(beginTime,endTime)
    {

			var beginTimes=beginTime.substring(0,10).split('-');
			var endTimes=endTime.substring(0,10).split('-');
			
			beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
			endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
			 
			var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
			
			if(a<0)
			{
				return -1;
			}
			else if (a>0)
				{
				return 1;
				}
			else if (a==0)
			{
				return 0;
			}
			else
			{
				return 'exception'
			}
	}
			
			
			
		function checkForm(addstaffer){
        	if (!checkNotNull(addstaffer.priceRid,"受理工号")) return false;
        	if (!checkNotNull(addstaffer.custName,"用户姓名")) return false;
            return true;
        }
        
				function add()
				{
				    var f =document.forms[0];
				    var treeid=document.getElementById("treeId").value;
				    if(treeid==''){
				    	alert('请选择父节点id!');
				    	return false;
				    }
				    //不验证
    	    		if(true){
    	    		var voicePathObj = document.getElementById("voicePath");
    	    		var selectFileObj = document.getElementById("selectFile");
    	    		voicePathObj.value = selectFileObj.value;
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=insert";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				function update()
				{
			 		document.forms[0].action="../incomming/incommingInfo.do?method=toOperIncommingInfo&type=update";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
				}
				function del()
				{
					var delflag=confirm('是否确定删除？');
					if(true==delflag){
			 		document.forms[0].action="../ccIvrTreevoiceDetail.do?method=toOperPriceinfo&type=delete";
			 		document.getElementById('btnSubmit').click();
			 		//document.forms[0].submit();
			 		}
				}
				
		function toback()
		{

			opener.parent.topp.document.all.btnSearch.click();
			//pener.parent.topp.document.all.btnSearch.click();
<%--			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;--%>
		
		}
		function modifyVoicePath()
		{
			document.getElementById('selectFile').style.display='inline';
		}
		function onloadDoWithVoicePath()
		{
			var opertype =  '<bean:write name="opertype" />';
			if('update'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="inline";
			}
			else if('insert'==opertype)
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="inline";
				voicePath.style.display="none";
				modifyVoicePathHref.style.display="none";
			}
			else
			{
				var modifyVoicePathHref = document.getElementById('modifyVoicePathHref');
				var voicePath = document.getElementById('voicePath');
				var selectFile = document.getElementById('selectFile');
				selectFile.style.display="none";
				voicePath.style.display="inline";
				modifyVoicePathHref.style.display="none";
			}
		}
			</script>

</head>

<body onunload="toback()" class="loadBody">

	<logic:notEmpty name="operSign">
		<script>
	alert("操作成功"); window.close();
	
	</script>
	</logic:notEmpty>

	<html:form action="/incomming/incommingInfo.do" method="post">

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="navigateStyle">
					<logic:equal name="opertype" value="insert">
		    		添加信息
		    	</logic:equal>
					<logic:equal name="opertype" value="detail">
		    		查看信息
		    	</logic:equal>
					<logic:equal name="opertype" value="update">
		    		修改信息
		    	</logic:equal>
					<logic:equal name="opertype" value="delete">
		    		删除信息
		    	</logic:equal>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="contentTable">
			<tr>
				<td class="labelStyle">
					来&nbsp;&nbsp;&nbsp;&nbsp;电
				</td>
				<td class="valueStyle">
					<html:text property="tel_num" styleClass="writeTextStyle" size="20"
						readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					受理时间
				</td>
				<td class="valueStyle">
					<html:text property="addtime" styleClass="writeTextStyle" size="20"
						readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle" width="100">
					咨询栏目
				</td>
				<td class="valueStyle" width="100">
					<%--					<html:text property="dictQuestionType1" styleClass="writeTextStyle" size="20" readonly="true" />--%>

					<html:select property="dictQuestionType1" styleClass="selectStyle">
						<html:option value="政策咨询"></html:option>
						<html:option value="种植咨询"></html:option>
						<html:option value="养殖咨询"></html:option>
						<html:option value="项目咨询"></html:option>
						<html:option value="环保咨询"></html:option>
						<html:option value="重大事件上报"></html:option>
						<html:option value="信息订制"></html:option>
						<html:option value="金农通"></html:option>
						<html:option value="企业服务"></html:option>
						<html:option value="医疗服务"></html:option>
						<html:option value="价格行情"></html:option>
						<html:option value="价格报送"></html:option>
						<html:option value="供求发布"></html:option>
						<html:option value="热线调查"></html:option>
						<html:option value="万事通"></html:option>
						<html:option value="供求查询"></html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					受理工号
				</td>
				<td class="valueStyle">
					<html:text property="respondent" styleClass="writeTextStyle"
						size="20" readonly="true" />
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					解决状态
				</td>
				<td class="valueStyle">
					<html:select property="dictIsAnswerSucceed"
						styleClass="writeTextStyle">
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=dict_is_answer_succeed" />
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					解决方式
				</td>
				<td class="valueStyle">
					<select name="answerMan" class="writeTextStyle">
						<jsp:include flush="true"
							page="../custinfo/textout.jsp?selectName=answer_man" />
					</select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					是否回访
				</td>
				<td class="valueStyle">
					<html:select styleId="dictIsCallback" property="dictIsCallback"
						styleClass="writeTextStyle">
						<html:option value="">请选择</html:option>
						<html:option value="否">否</html:option>
						<html:option value="是">是</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					受理专家
				</td>
				<td class="valueStyle">
					<html:select styleId="billNum" property="billNum"
						styleClass="writeTextStyle" onchange="selecttype1()">
						<html:option value="0">选择专家类别</html:option>
						<html:options collection="expertList" property="value"
							labelProperty="label" styleClass="writeTypeStyle" />
						<html:option value="0">金农热线</html:option>
					</html:select>
					<html:select styleId="expertName" property="expertName"
						styleClass="writeTextStyle">
						<html:option value="">选择专家</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="labelStyle">
					咨询内容
				</td>
				<td class="valueStyle">
					<html:textarea property="questionContent" rows="10" cols="90"
						styleClass="writeTextStyle" />
				</td>
			</tr>
			<tr>
				<td class="labelStyle">
					热线答复
				</td>
				<td class="valueStyle" class="tdbgcolorloadleft">
					<html:textarea property="answerContent" rows="10" cols="90"
						styleClass="writeTextStyle" />
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center" class="buttonAreaStyle">
					<logic:equal name="opertype" value="insert">
						<input type="button" name="btnAdd" class="buttonStyle" value="添加"
							onclick="add()" />
					</logic:equal>
					<logic:equal name="opertype" value="update">
						<input type="button" name="btnUpdate" class="buttonStyle"
							value="修改" onclick="update()" />
					</logic:equal>
					<logic:equal name="opertype" value="delete">
						<input type="button" name="btnDelete" class="buttonStyle"
							value="删除" />
					</logic:equal>

					<input type="button" name="" value="关闭" class="buttonStyle"
						onClick="javascript:window.close();" />

				</td>
			</tr>
			<tr style="display: none;">
				<html:hidden property="id" styleClass="writeTextStyle" />
				<%--				<html:hidden property="talkId" styleClass="writeTextStyle"  />--%>
				<%--				<html:hidden property="mainId" styleClass="writeTextStyle"  />--%>
				<input type="hidden" name="opertype"
					value="<%=(String) request.getAttribute("opertype")%>" />
				<input type="submit" id="btnSubmit" value="提交"
					style="display: none;" />
			</tr>
		</table>

	</html:form>
</body>
</html:html>