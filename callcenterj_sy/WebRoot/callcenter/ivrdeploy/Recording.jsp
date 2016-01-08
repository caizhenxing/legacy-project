
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
<title>录音</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<style type="text/css">

<!--
	.css {
	font-family: "宋体";
	font-size: 12px;
	color: #FFFFFF;
	text-align: center;
}
.anniu {
	background-image: url(tp/tanchu9.gif);
	background-repeat: no-repeat;
	background-position: center;
	width: 35px;
	height: 32px;
}

#ProgressBar {
	font:12px Verdana, Arial, Helvetica, sans-serif;
	border:1px solid #5B94DF;
}
#Pointer {
	border:1px solid  #FFFFFF;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#DDE9F9, endColorstr=#81ACE7);
}
#Lable {
	position:absolute;
	width:100%;
	:100%;
	text-align: center;
}
-->
</style>
<%
	String id = request.getAttribute("id").toString();

 %>
 <%
 	String btnNoLook = request.getAttribute("btnNoLook").toString();
 	String str = null;
 	if(btnNoLook.equals("false"))
 	{
 		str = "run()";
 	}
 	else if(btnNoLook.equals("true"))
 	{
 		str = "";
 	}
 	
  %>
<script language="javascript" src="../../js/form.js"></script>
<script type="text/javascript">

 function toback(){
			window.location.reload();
		}
		
	i=0;
	function run() {
		i++;
		p =i+"%";
		document.getElementById("Pointer").style.width = p;
		document.getElementById("Lable").innerHTML = p;
		flag = window.setTimeout(run,1500);
		if(i == 100) { window.clearTimeout(flag); i=0;}
	}
	
	function record()
	{
		document.forms[0].action = "../../callcenter/ivrdeploy.do?method=recordingIDU&state=record&id=<%=id%>";
		document.forms[0].submit();

	}
	
	
	function stop(str)
	{
		document.forms[0].action = "../../callcenter/ivrdeploy.do?method=recordingIDU&state=stop&id=<%=id%>&returnValue="+str;
		document.forms[0].submit();

	}
	
	
	function begin()
	{
		document.forms[0].action = "../../callcenter/ivrdeploy.do?method=recordingIDU&state=begin&id=<%=id%>";
		document.forms[0].submit();
	}
	
	function recordRun()
	{
		 run();
	}
	
	function beginRun()
	{
		toback();
	}


</script>
  </head >
  
  
 <body onload="<%=str%>">
<html:form action="/callcenter/ivrdeploy" method="post">
  
 <logic:equal name="record" value="error">
	<script>
	alert("错误");
	
	window.close();
	</script>
  </logic:equal>
  
  <logic:equal name="RegIp" value="false">
  <script>
  	alert("此IP未在系统中注册,您不能进行录音");
	window.close();
<%--  	beginRun();--%>
  </script>
  </logic:equal>		
		
		

  <table width="600" height="470" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="113" colspan="3" background="../../images/tp/tanchu.gif">&nbsp;</td>
  </tr>
  <tr>
    <td width="125" height="254" background="../../images/tp/tanchu3.gif">&nbsp;</td>
    <td width="368" align="center" valign="top" background="../../images/tp/tanchu5.gif"><br />
      <table width="364" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="368" height="26" background="../../images/tp/tanchu6.gif" class="css">播放内容</td>
      </tr>
      <tr>
        <td>
        <html:textarea property="content" cols="49" rows="6" />
<%--        <textarea name="textfield" cols="49" rows="6"></textarea>--%>
        
        </td>
      </tr>
      <tr>
        <td><table width="360" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="7"><table cellpadding="０">
              <tr>
                <td height="1"><table cellpadding="０">
                  <tr>
                    <td height="1"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
            </tr>
            <logic:equal name="btnNoLook" value="Look">
            <tr>
            <td width="57">&nbsp;</td>
            <td width="38"><img src="../../images/tp/tanchu9.gif" width="33" height="33" onclick="record()"/></td>
            <td width="69">&nbsp;</td>
            <td width="33"><img src="../../images/tp/tanchu11.gif" width="33" height="33" onclick="stop()"/></td>
            <td>&nbsp;</td>
            <td width="33"><img src="../../images/tp/tanchu13.gif" width="33" height="33" onclick="begin()"/></td>
            <td width="57">&nbsp;</td>
          </tr>
          </logic:equal>
          
          
          <logic:notEqual name="btnNoLook" value="Look">
          <tr>
            <td width="57">&nbsp;</td>
            <logic:equal name="btnNoLook" value="true">
            <td width="38"><img src="../../images/tp/tanchu9.gif" width="33" height="33" onclick="record()"/></td>
            </logic:equal>
            <logic:equal name="btnNoLook" value="false">
            <td width="38"><img src="../../images/tp/tanchu10.gif" width="33" height="33" onclick="record()"/></td>
            </logic:equal>
            
            
            <logic:equal name="btnBcolse" value="false">
            <td width="69">&nbsp;</td>
            <td width="33"><img src="../../images/tp/tanchu11.gif" name="imgR1" width="33" height="33" onclick="stop('R')"/></td>
            <td>&nbsp;</td>
            </logic:equal>
            
            <logic:equal name="btnBcolse" value="true"> 
            <td width="69">&nbsp;</td>
            <td width="33"><img src="../../images/tp/tanchu11.gif" name="imgB2" width="33" height="33" onclick="stop('B')"/></td>
            <td>&nbsp;</td>
            </logic:equal>
            
            <logic:equal name="btnNoLook" value="true"> 
            <td width="69">&nbsp;</td>
            <td width="33"><img src="../../images/tp/tanchu11.gif" name="imgB2" width="33" height="33" onclick="stop('C')"/></td>
            <td>&nbsp;</td>
            </logic:equal>
            
            <logic:equal name="btnNoLook" value="true">
            <td width="33"><img src="../../images/tp/tanchu13.gif" width="33" height="33" onclick="begin()"/></td>
            </logic:equal>
            <logic:equal name="btnNoLook" value="false">
            <td width="38"><img src="../../images/tp/tanchu14.gif" width="33" height="33" onclick="record()"/></td>
            </logic:equal>
            
            <td width="57">&nbsp;</td>
          </tr>
          </logic:notEqual>
          
          
          <tr>
            <td>&nbsp;</td>
            <td><img src="../../images/tp/tanchu16.gif" width="33" height="25" /></td>
            <td>&nbsp;</td>
            <td><img src="../../images/tp/tanchu17.gif" width="33" height="25" /></td>
            <td width="73">&nbsp;</td>
            <td><img src="../../images/tp/tanchu18.gif" width="33" height="25" /></td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><table cellpadding="０">
          <tr>
            <td height="1"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="30" style="padding:2px">
		<div id="ProgressBar">
			<div id="Lable"></div>
			<div id="Pointer" style="width:0%"></div>
		</div></td>
      </tr>
    </table></td>
    <td width="107" background="../../images/tp/tanchu4.gif">&nbsp;</td>
  </tr>
  <tr>
    <td height="105" colspan="3" background="../../images/tp/tanchu2.gif">&nbsp;</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
