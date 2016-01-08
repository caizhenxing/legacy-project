<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<logic:notEmpty name="operSign">
  <logic:equal name="operSign" value="dingzhishibai">
  	<script>
  		alert("已经订制过该业务，不能重复订制！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="tuidingshibai">
  	<script>
  		alert("已经退订过该业务，不能重复退订！");
  		window.close();
  	</script>
  </logic:equal>
  <logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("操作成功");
		//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
<%--		opener.parent.bottomm.document.execCommand('Refresh');--%>
		window.close();
	</script>
	</logic:equal>
</logic:notEmpty>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<script language="javascript" src="../js/form.js"></script>
<script language="javascript" src="../js/clockCN.js"></script>
<script language="javascript" src="../js/clock.js"></script>
<SCRIPT language="javascript" src="../js/calendar3.js" type="text/javascript"></script>
<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />

<title>操作</title>
<script type='text/javascript' src='../js/msg.js'></script>
<script type="text/javascript">
	// 查询订制记录
	function tijiao(){		
   		document.forms[0].action = "columnInfo.do?method=toColumnInfoUpdate";
   		document.forms[0].target = "contents";
		document.forms[0].submit();
		window.close();
	}
	function toback(){			
			if(opener.parent){
				//opener.parent.topp.document.all.btnSearch.click();//执行查询按钮的方法
				var link = opener.parent.bottomm.document.location.href;
				alert(opener.parent)
				alert(link)
				if(link.indexOf("pagestate") == -1){
					link += "&pagestate=1";
				}
				opener.parent.bottomm.document.location = link;
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
<html:form action="/sms/columnInfo" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class ="nivagateTable">
  <tr>
    <td class="navigateStyle">
     当前位置&ndash;&gt;设定栏目信息
    </td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="conditionTable">
  <tr>
     <td class="labelStyle">
     	栏目信息
     </td>
     <td class="valueStyle">
       <html:text property="columnInfo" styleClass="writeTextStyle"/>
     </td> 
  <tr class="buttonAreaStyle">
    <td colspan="4" align="right">
      <input type="button" name="Submit4" value="提 交" onClick="tijiao()"  >
    </td>
  </tr>
</table>
<html:hidden property="nickname"/>
</html:form>
</body>
</html>
