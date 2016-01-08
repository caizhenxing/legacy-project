<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>


<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />

	<title>日志查询</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<%--    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />--%>
	<%--    <script language="JavaScript" src="../../js/calendar.js"></script>--%>
	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<SCRIPT language=javascript src="../../js/form.js" type=text/javascript></SCRIPT>
	<SCRIPT language=javascript src="../../js/calendar.js"
		type=text/javascript>
</SCRIPT>
	<script language="javascript" src="../../js/calendar3.js"></script>
	<script language="javascript" src="../../js/common.js"></script>

	<script language="javascript" src="../../js/clock.js"></script>
	<script language="javascript">
    	//查询
     function isInBag(s,bag){
      var i;
      for (i = 0; i < s.length; i++){
      var c = s.charAt(i);
      if (bag.indexOf(c) == -1) return false;
       }
      return true;
      }
      
      function isLetterNumber(content2){
         if (isInBag(content2,"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")) return true;
        else return false;
          }

      function isTel(content2){
          if (isInBag(content2,",;/()-0123456789 ")) return true;
            else return  false;
         }
      
    	function query(){
    	
         var telNum = document.forms[0].telNum.value;
        
    	 if(!isTel(telNum)){
           alert("请输入正确电话格式！");
           document.forms[0].telNum.focus();
          return false;
          }

    		document.forms[0].action = "../cclog/calloutLog.do?method=toCallOutList";
    		document.forms[0].target = "bottomm";
    		document.forms[0].submit();
    	}
    	
    	    	function changetwo(){

			var info_group=document.forms[0].tagAllInfo.value;
			
			document.forms[0].action="../cclog/telQuery.do?method=toTelQuery"
			document.forms[0].target = "topp";
			document.forms[0].submit();
		}
		function changethree(){

			var info_sort=document.forms[0].tagBigInfo.value;
			
			document.forms[0].action="../cclog/telQuery.do?method=toTelQuery"
			document.forms[0].target = "topp";
			document.forms[0].submit();
		}
    	
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
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>


</head>

<body class="conditionBody" onload="document.forms[0].btnSearch.click()">

	<html:form action="/callcenter/cclog/calloutLog.do" method="post">
		<table width="780" border="0" align="center" cellpadding="0"
			cellspacing="1" class="nivagateTable">
			<tr>
				<td class="navigateStyle">
					当前位置&ndash;&gt;群呼日志
				</td>
			</tr>
		</table>
		<table width="780" border="0" align="center" cellpadding="1"
			cellspacing="1" class="conditionTable">
			<tr>
				<td class="queryLabelStyle">
					开始时间
				</td>
				<td class="valueStyle">
					<html:text property="beginTime" styleClass="writeTextStyle2"
						 />
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('CallOutForm','beginTime',false);">
					&nbsp;
					<html:select property="beginHour">
						<option value="">请选择</option>
						<html:option value="0"></html:option>
						<html:option value="1"></html:option>
						<html:option value="2"></html:option>
						<html:option value="3"></html:option>
						<html:option value="4"></html:option>
						<html:option value="5"></html:option>
						<html:option value="6"></html:option>
						<html:option value="7"></html:option>
						<html:option value="8"></html:option>
						<html:option value="9"></html:option>
						<html:option value="10"></html:option>
						<html:option value="11"></html:option>
						<html:option value="12"></html:option>
						<html:option value="13"></html:option>
						<html:option value="14"></html:option>
						<html:option value="15"></html:option>
						<html:option value="16"></html:option>
						<html:option value="17"></html:option>
						<html:option value="18"></html:option>
						<html:option value="19"></html:option>
						<html:option value="20"></html:option>
						<html:option value="21"></html:option>
						<html:option value="22"></html:option>
						<html:option value="23"></html:option>
					</html:select>
					时
					<html:select property="beginMinute">
						<option value="">请选择</option>
						<html:option value="0"></html:option>
						<html:option value="1"></html:option>
						<html:option value="2"></html:option>
						<html:option value="3"></html:option>
						<html:option value="4"></html:option>
						<html:option value="5"></html:option>
						<html:option value="6"></html:option>
						<html:option value="7"></html:option>
						<html:option value="8"></html:option>
						<html:option value="9"></html:option>
						<html:option value="10"></html:option>
						<html:option value="11"></html:option>
						<html:option value="12"></html:option>
						<html:option value="13"></html:option>
						<html:option value="14"></html:option>
						<html:option value="15"></html:option>
						<html:option value="16"></html:option>
						<html:option value="17"></html:option>
						<html:option value="18"></html:option>
						<html:option value="19"></html:option>
						<html:option value="20"></html:option>
						<html:option value="21"></html:option>
						<html:option value="22"></html:option>
						<html:option value="23"></html:option>
						<html:option value="24"></html:option>
						<html:option value="25"></html:option>
						<html:option value="26"></html:option>
						<html:option value="27"></html:option>
						<html:option value="28"></html:option>
						<html:option value="29"></html:option>
						<html:option value="30"></html:option>
						<html:option value="31"></html:option>
						<html:option value="32"></html:option>
						<html:option value="33"></html:option>
						<html:option value="34"></html:option>
						<html:option value="35"></html:option>
						<html:option value="36"></html:option>
						<html:option value="37"></html:option>
						<html:option value="38"></html:option>
						<html:option value="39"></html:option>
						<html:option value="40"></html:option>
						<html:option value="41"></html:option>
						<html:option value="42"></html:option>
						<html:option value="43"></html:option>
						<html:option value="44"></html:option>
						<html:option value="45"></html:option>
						<html:option value="46"></html:option>
						<html:option value="47"></html:option>
						<html:option value="48"></html:option>
						<html:option value="49"></html:option>
						<html:option value="50"></html:option>
						<html:option value="51"></html:option>
						<html:option value="52"></html:option>
						<html:option value="53"></html:option>
						<html:option value="54"></html:option>
						<html:option value="55"></html:option>
						<html:option value="56"></html:option>
						<html:option value="57"></html:option>
						<html:option value="58"></html:option>
						<html:option value="59"></html:option>
					</html:select>
					分
				</td>
				<td class="queryLabelStyle">
					结束时间
				</td>
				<td class="valueStyle">
					<html:text property="endTime" styleClass="writeTextStyle2"
						/>
					<img alt="选择时间" src="../../html/img/cal.gif"
						onclick="openCal('CallOutForm','endTime',false);">
					时
					<html:select property="endHour">
						<option value="">请选择</option>
						<html:option value="0"></html:option>
						<html:option value="1"></html:option>
						<html:option value="2"></html:option>
						<html:option value="3"></html:option>
						<html:option value="4"></html:option>
						<html:option value="5"></html:option>
						<html:option value="6"></html:option>
						<html:option value="7"></html:option>
						<html:option value="8"></html:option>
						<html:option value="9"></html:option>
						<html:option value="10"></html:option>
						<html:option value="11"></html:option>
						<html:option value="12"></html:option>
						<html:option value="13"></html:option>
						<html:option value="14"></html:option>
						<html:option value="15"></html:option>
						<html:option value="16"></html:option>
						<html:option value="17"></html:option>
						<html:option value="18"></html:option>
						<html:option value="19"></html:option>
						<html:option value="20"></html:option>
						<html:option value="21"></html:option>
						<html:option value="22"></html:option>
						<html:option value="23"></html:option>
					</html:select>
					分
					<html:select property="endMinute">
						<option value="">请选择</option>
						<html:option value="0"></html:option>
						<html:option value="1"></html:option>
						<html:option value="2"></html:option>
						<html:option value="3"></html:option>
						<html:option value="4"></html:option>
						<html:option value="5"></html:option>
						<html:option value="6"></html:option>
						<html:option value="7"></html:option>
						<html:option value="8"></html:option>
						<html:option value="9"></html:option>
						<html:option value="10"></html:option>
						<html:option value="11"></html:option>
						<html:option value="12"></html:option>
						<html:option value="13"></html:option>
						<html:option value="14"></html:option>
						<html:option value="15"></html:option>
						<html:option value="16"></html:option>
						<html:option value="17"></html:option>
						<html:option value="18"></html:option>
						<html:option value="19"></html:option>
						<html:option value="20"></html:option>
						<html:option value="21"></html:option>
						<html:option value="22"></html:option>
						<html:option value="23"></html:option>
						<html:option value="24"></html:option>
						<html:option value="25"></html:option>
						<html:option value="26"></html:option>
						<html:option value="27"></html:option>
						<html:option value="28"></html:option>
						<html:option value="29"></html:option>
						<html:option value="30"></html:option>
						<html:option value="31"></html:option>
						<html:option value="32"></html:option>
						<html:option value="33"></html:option>
						<html:option value="34"></html:option>
						<html:option value="35"></html:option>
						<html:option value="36"></html:option>
						<html:option value="37"></html:option>
						<html:option value="38"></html:option>
						<html:option value="39"></html:option>
						<html:option value="40"></html:option>
						<html:option value="41"></html:option>
						<html:option value="42"></html:option>
						<html:option value="43"></html:option>
						<html:option value="44"></html:option>
						<html:option value="45"></html:option>
						<html:option value="46"></html:option>
						<html:option value="47"></html:option>
						<html:option value="48"></html:option>
						<html:option value="49"></html:option>
						<html:option value="50"></html:option>
						<html:option value="51"></html:option>
						<html:option value="52"></html:option>
						<html:option value="53"></html:option>
						<html:option value="54"></html:option>
						<html:option value="55"></html:option>
						<html:option value="56"></html:option>
						<html:option value="57"></html:option>
						<html:option value="58"></html:option>
						<html:option value="59"></html:option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input name="btnSearch" type="button"  
						value="查询" onclick="query()" class="buttonStyle"/>
				</td>
			</tr>
			<tr>
				<td class="queryLabelStyle">
					来电号码
				</td>
				<td class=valueStyle>
					<html:text property="telNum" styleClass="writeTextStyle" />
				</td>
				<td class="queryLabelStyle">
					外呼类型
				</td>
				<td class=valueStyle>
					<html:select property="calloutType">
						<option value="voice">语音栏目</option>
						<option value="message">语音消息</option>
					</html:select>
				</td>
				<td align="center" class="queryLabelStyle">
					<input type="reset" value="刷新" class="buttonStyle"/>
				</td>
			</tr>
			<tr height="1px">
				<td colspan="10" class="buttonAreaStyle">
					<%--		    <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pccye' key='sys.add'/>" onclick="popUp('windows','../pcc/callinFirewall.do?method=toCallinFireWallLoad&type=insert',680,400)"/>--%>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>