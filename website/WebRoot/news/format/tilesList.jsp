<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312"%>
<%@include file="../include/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <html:base />
    
    <title>页面模块 选择</title>
    
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
		.style1 {
		font-size: 16px;
		font-weight: bold;
		}
	-->
	</style>
	 <script language="javascript">
    	//选择条件相符合的类别信息
    	function selectTypeInfo(){
    		var classId = document.forms[0].titlesList.value;
    		document.forms[0].action = "../tileAction.do?method=tilesSelect&childId="+ classId;
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body>

<table width="667" height="392" border="0" align="center">
  <tr>
    <td height="95"><div align="center">
      <table width="270" border="0">
        <tr>
          <td width="95" height="39"><div align="center">版式名称：</div></td>
          <td width="165">
          <html:form action="tileAction.do?method=tilesSelect" method="post">
			<div align="center">
              	<html:select property="titlesList" onchange="selectTypeInfo()">
         			<html:options collection="moduleList" property="value" labelProperty="label"/>
        		</html:select>
            </div>
          </html:form></td>
        </tr>
      </table>
    </div> </td>
    </tr>
  <tr>
    <td height="291" valign="top"><div align="center">
      <table width="447" height="34" border="0">
      	<tr>
      		<td width="157" height="30"><div align="center" class="style1"> 模块名称 </div></td>
          	<td width="145"><div align="center" class="style1"> 模块标记 </div></td>
          	<td width="131"><div align="center" class="style1"> 选择样式 </div></td>
      	</tr>
      	<logic:notEmpty name="list">
       	<logic:iterate id="moduleList" name="moduleList">
        <tr>
          <td width="157" height="30"><div align="center"><bean:write name='moduleList' property='label'/></div></td>
          <td width="145"><div align="center">tiles 标记：</div></td>
          <td width="131"><div align="center">
          	<html:link page ="/tileAction.do?method=selectStyle" paramId="moduleId" paramName="moduleList" paramProperty="value">修 改</html:link>
          	</div>
          </td>
        </tr>
        </logic:iterate>
        </logic:notEmpty>
      </table>
    </div></td>
  </tr>
</table>

  </body>
</html>
