<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="GB2312"%>
<%@include file="../include/include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="<%=path%>back/style_mid.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {font-size: 16px}
.STYLE4 {font-size: 16px; font-weight: bold; }
-->
</style>
<script language="javascript">
	function selectTypeInfo(){
    		var showType = document.forms[0].showType.value;
    		document.forms[0].newsKey = showType;
    }
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title> 样式查询 </title>
</head>

<body>
<html:form action="formatOrder.do?method=formatMain" method="post">
  <p>&nbsp;</p>
          <table width="510" border="0" align="center">
            <tr>
              <td width="32" height="23" class="td1"><div align="center"><span class="STYLE1"></span><img src="format/image/folder.gif" width="18" height="12" /></div></td>
              <td width="123" class="td1"><div align="center" class="STYLE4">名 称</div></td>
              <td width="78" class="td1"><div align="center" class="STYLE4">样式代号</div></td>
              <td width="113" class="td1"><div align="center" class="STYLE4">显示类型</div></td>
              <td width="60" class="td1"><div align="center"><span class="STYLE1">详细</span></div></td>
              <td width="64" class="td1"><div align="center"><span class="STYLE1">删 除</span></div></td>
            </tr>
            <logic:iterate id="formatL" name="formatL">
            <tr>
              <td height="20" class="td1"><div align="center"><img src="format/image/face1.gif" /></div></td>
              <td class="td1"><div align="center"><bean:write name='formatL' property='formatType'/></div></td>
              <td class="td1"><div align="center"><bean:write name='formatL' property='classId'/></div></td>
              <td class="td1"><div align="center"><bean:write name='formatL' property='showTypeName'/></div></td>
              <td class="td1"><div align="center"><html:link action="formatOrder.do?method=formatList" paramId="classId" paramName="formatL" paramProperty="classId"><img src="format/image/more.png"  border="0"/></html:link></div></td>
              <td class="td1"><div align="center"><html:link action="formatOrder.do?method=formatDelete" paramId="classId" paramName="formatL" paramProperty="classId"><img src="format/image/deletle.png"  border="0"/></html:link></div></td>
            </tr>
            </logic:iterate>
            <tr>
              <td height="23" colspan="6" class="td1"><%@include file="/comm/page.jsp"%></td>
            </tr>
          </table>
          <p>&nbsp;</p>
</html:form>
</body>
</html>
