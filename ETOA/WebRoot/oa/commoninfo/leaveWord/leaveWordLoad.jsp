<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript>
    </SCRIPT>
    
    <script language="javascript">
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.sortName,"<bean:message key='et.oa.commoninfo.leaveWord.title'/>")) return false;
            if (!checkNotNull(addstaffer.content,"<bean:message key='et.oa.commoninfo.leaveWord.load.content'/>")) return false;
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../leaveWord.do?method=operLeaveWord&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../leaveWord.do?method=operLeaveWord&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../leaveWord.do?method=operLeaveWord&type=delete";
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
			opener.parent.topp.document.all.btnSearch.click();
		}
    </script>
  </head>
  
  <body onunload="toback()">
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/oa/commoninfo/leaveWord" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpicload"><bean:message key="et.oa.commoninfo.leaveWord.load"/></td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
          <tr>  
            <html:hidden property="id"/>
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.leaveWord.title"/></TD>
            <TD class="tdbgcolorloadleft"><html:text property="title" styleClass="input"></html:text> </TD>
          </tr>

          <tr>  
            <TD class="tdbgcolorloadright"><bean:message key="et.oa.commoninfo.leaveWord.load.content"/></TD>
            <TD class="tdbgcolorloadleft"><html:textarea  property="content" rows="5" cols="40" ></html:textarea></TD>
          </tr>
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message key='agrofront.oa.assissant.hr.hrManagerLoad.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
