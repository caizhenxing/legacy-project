<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
    //添加规则
		function add(){    
    	      document.forms[0].action = "../replace.do?method=toOperReplace";
    		  document.forms[0].submit();   
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/replace" method="post">
    <%-- jps include 头 --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- 加 --%>

<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
<%--   加到这里    --%>



<%----%>
<%--把代码加到这--%>
<%----%>
       <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
          <tr>
            <td background="../../images/forum/di.jpg">&nbsp;&nbsp;<strong class="danzhi">欢迎来到讨论区管理程序 / 帖子词语过滤</strong></td>
            </tr>
          <tr>
            <td height="30"><div align="center" class="danzhi"><strong>帖子词语过滤</strong></div></td>
          </tr>
        </table>
         <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td height="30" background="../../images/forum/di.jpg"><div align="left" class="danzhi"><strong>&nbsp;功能说明：</strong></div></td>
              </tr>
            <tr>
              <td class="danzhi">&nbsp; <br />
               &nbsp; 不良词语过滤可以阻止一些不好的字眼出现在论坛中。你可以选择过滤的单词，和过滤后的单词。<br />
               &nbsp; 这样，不良词语在<strong>发表文章</strong>时，或在用户查看、引用时，都不会被显示。<br />
               &nbsp; 这意味着不良词语过滤是永久性的。当你增加一个新的过滤时，所有的文章都会被过滤交换.<br /></td>
            </tr>
          </table>
          <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td height="30" background="../../images/forum/di.jpg"><div align="left" class="danzhi"><strong>&nbsp;使用方法：</strong></div></td>
            </tr>
            <tr>
              <td class="danzhi">&nbsp; <br />
               &nbsp; &nbsp;输入一个要过滤的词语和过滤后的词语，并在中间加上 &quot;=&quot;   (等于号)。 <br />
                <strong>&nbsp; 注意，每行只能写一个！</strong><br />
                <br />
                <strong>&nbsp; 例如：</strong>damn=d**n<br />
                <br />
               &nbsp; 请注意：必须遵循正则表达式规则，否则可能导致发帖失败。.<br /></td>
            </tr>
          </table>
           <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
          <table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td height="30" background="../../images/forum/di.jpg"><div align="left" class="danzhi"><strong>&nbsp;请输入过滤单词：</strong></div></td>
            </tr>
            <tr>
              <td class="danzhi">&nbsp; <div align="center">
                <label>
<%--         规则文本框           --%>
<html:textarea property="ruleArray" cols="60" rows="8">
</html:textarea>
                </label>
                    <br />
                    <br />
                    <label></label>
              </div></td>
            </tr>
            <tr>
              <td height="30" class="danzhi"><label>
                <div align="center">
                  <input type="submit" name="Submit" value="提交" onclick="add()"/>
                  </div>
              </label></td>
            </tr>
          </table>
 <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>

<%--  这里结束  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--加--%>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
    </html:form>
  </body>
</html:html>
