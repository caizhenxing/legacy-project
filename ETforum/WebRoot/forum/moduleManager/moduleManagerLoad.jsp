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
 
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.topicTitle,"模块名")) return false;
            if (!checkNotNull(addstaffer.parentId,"上一级模块")) return false;
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../moduleManager.do?method=toModuleOper&type=insert";
    		  document.forms[0].submit();
    	    }
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
    <html:form action="/forum/moduleManager" method="post">
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="tdbgpicload">
		        模块添加
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		     <td class="tdbgcolorqueryright" width="30%">项目类别</td>
		     <td class="tdbgcolorqueryleft">
		        <html:hidden property="forumSort" value="son"/> 
		         子论坛
		     </td>
		  </tr>      
          <tr>
		    <td class="tdbgcolorqueryright" width="30%">模块名</td>
		    <td class="tdbgcolorqueryleft"><html:text property="topicTitle" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright" width="30%">上一级模块</td>
		    <td class="tdbgcolorqueryleft">
		        <html:select property="parentId">		
                  <html:option value=""></html:option>
                  <html:optionsCollection name="moduleLabel" label="label" value="value"/>
                </html:select>
            </td>
          </tr>
          <tr>
		    <td class="tdbgcolorqueryright" width="30%">模块图片</td>
		    <td class="tdbgcolorqueryleft"><html:text property="topicPhoto" styleClass="input"/></td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright" width="30%">模块描述</td>
		    <td class="tdbgcolorqueryleft"><html:text property="topicDescribe" styleClass="input"/></td>
		  </tr>  
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="添加" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="修改" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="删除" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="返回" onClick="javascript: history.back();"/>
		    </td>
		  </tr>
		</table>  
    </html:form>
  </body>
</html:html>
