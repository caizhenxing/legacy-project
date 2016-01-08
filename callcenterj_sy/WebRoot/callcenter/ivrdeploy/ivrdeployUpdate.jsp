<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>IVR常量管理</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 
<%
	String id=(String)request.getParameter("id");
	//System.out.println("str_id==="+id);
%>	
<link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/form.js"></script>
	<script language="javascript">
<%--	function checkForm(addstaffer)--%>
<%--    {--%>
<%--    	if (!checkNotNull(addstaffer.key,"按键"))return false;--%>
<%--     --%>
<%--    	return true;		--%>
<%--    }--%>
    	function update(){
<%--    	     var f =document.forms[0];--%>
<%--		    if(checkForm(f)){ 	    --%>
		     var key = document.forms[0].key.value;
		    if(key == ""){
    		    alert("按键不能为空！！");		  
    		    return;
    		  }
    		document.forms[0].action = "../../callcenter/ivrdeploy.do?method=operIVR";
			document.forms[0].submit(); 
			
    	}	
    	function Recording()
    	{
    		 document.forms[0].action = "../../callcenter/ivrdeploy.do?method=recording&state=uptel";	 
    		 document.forms[0].submit();
    		
    	}
    		//返回页面
     function toback(){
           window.opener.location.href = window.opener.location.href; 
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
    	</script>

    	
  </head>
  
    	
  <body>
<logic:notEmpty name="operSign">
<script>
alert("操作成功");
toback();
window.close();
</script>
</logic:notEmpty>
    <html:form action="/callcenter/ivrdeploy">
   		
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
  			<tr>
				<td class="tdbgpicload">
						IVR常量管理
				</td>
			</tr>
		</table>
		
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">					
<%-- <html:hidden property="id" value="<%=str%>"/>--%>
					   <tr>
					    <td class="tdbgcolorqueryright">编号</td>
					    <td colspan="6" class="tdbgcolorqueryleft">
						<html:text  property="id" styleClass="input" readonly="true"/>
					    </td>
					  </tr>
					  <tr>
					    <td class="tdbgcolorqueryright">配置信息</td>
					    <td colspan="6" class="tdbgcolorqueryleft">
						<html:text  property="content" styleClass="input" readonly="true" />
					    </td>
					  </tr>
					<logic:notEmpty name="voicefilepath">
					  <tr>
					    <td class="tdbgcolorqueryright">按钮</td>
					    <td colspan="6" class="tdbgcolorqueryleft">
<%--					    <html:hidden property=" "/>--%>
					    <html:hidden property="key" value=" "/>
						<input type="button"  value="声音" onclick="popUp('windows','../callcenter/ivrdeploy.do?method=recordingIDU&state=uptel&id=<%=id%>',800,800)"/>
<%--						&id=<%=id%>--%>
					    </td>
					  </tr>
					</logic:notEmpty>
					<logic:equal name="key" value="text">
					  <tr>
					    <td class="tdbgcolorqueryright">按键设置</td>
					    <td colspan="6" class="tdbgcolorqueryleft">
					    <html:text  property="key" styleClass="input" />
					    </td>
					  </tr>
					</logic:equal>
					<logic:equal name="key" value="opt">
					  <tr>
					    <td class="tdbgcolorqueryright">按键设置</td>
					    <td colspan="6" class="tdbgcolorqueryleft">
					    <html:select property="key" >
							<html:option value="#">#</html:option>
							<html:option value="*">*</html:option>
				        </html:select>
					    </td>
					  </tr>
					</logic:equal>
					  
	</table>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    
		     <input name="btnUpadate" type="button" class="buttom"value="修改" onclick="update()"/>&nbsp;&nbsp;
		       <input name="addgov" type="button"  value="关闭" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
<%--		  <html:hidden property="id"/>--%>
	</table>
    </html:form>
    <script language="javascript">
	 
    </script>
  </body>
</html:html>
2