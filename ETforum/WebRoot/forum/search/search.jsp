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
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.topicTitle,"模块名")) return false;
            if (!checkNotNull(addstaffer.parentId,"上一级模块")) return false;
              return true;
            }
    	function query(){    
    	      document.forms[0].action = "../search.do?method=toSearchList";
    		  document.forms[0].submit();   
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../moduleManager.do?method=toModuleOper&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../moduleManager.do?method=toModuleOper&type=delete";
    		document.forms[0].submit();
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
		//返回页面
		function toback(){
			opener.parent.topp.document.all;
		}
    </script>
    
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/search" method="post">
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
<jsp:include flush="true" page="../common/navigation.jsp"></jsp:include>
<table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
</table>
      
<%--把代码加到这--%>
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td colspan="2" background="images/di.jpg"><div align="left">&nbsp; 消息：搜索程序</div></td>
              </tr>
            <tr>
              <td width="416" align="left" valign="top"><table width="416" border="0" cellpadding="3">
                <tr>
                  <td width="102">&nbsp; 帖子标题：				  </td>
				  <td width="296">
                    <label>
                    <html:text property="title" styleClass="input"/>
                    </label></td>
                </tr>
                <tr>
                  <td>&nbsp; 作者：
				  </td>
				  <td>   
                    <label>
                    <html:text property="userkey" styleClass="input"/>
                    </label></td>
                </tr> 
                <tr>
                  <td>&nbsp;&nbsp; 选择搜索时间：</td>
				  <td>
                    <label>
                    <html:select property="dateType" styleClass="input">		
<%--                       <html:optionsCollection name="dateList" label="label" value="value"/>--%>
                       <html:option value="">├搜索全部帖子</html:option>
                       <html:option value="day">├一天内的帖子</html:option>
                       <html:option value="week">├一个星期内的帖子</html:option>
                       <html:option value="month">├一个月内的帖子</html:option>
                       <html:option value="twoMonth">├两个月内的帖子</html:option>
                       <html:option value="threeMonth">├三个月内的帖子</html:option>
                       
                    </html:select>
                    </label></td>
                </tr>
              </table></td>
              <td width="494" valign="top">&nbsp; 选择搜索版块：<br />
                &nbsp;
                <label>
                    <html:select property="itemId">		
                       <html:option value="">搜索全部板块</html:option>
                       <html:optionsCollection name="allModule" label="label" value="value"/>
                    </html:select>
                </label></td>
            </tr>
            <tr>
              <td colspan="2" background="images/di.jpg" align="center"> 
                <input name="btnAdd" type="button" class="bottom" value="查询" onClick="query()"/>&nbsp;&nbsp;
                <html:reset value="重写"/>
              </td>
            </tr>
          </table>
<%----%>




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
