<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    
    <script language="javascript">
    	//添加
    	function add(){
    		document.forms[0].action = "../role/Role.do?method=operRole&type=insert";
    		document.forms[0].submit();
    	}
    	//修改
    	function update(){
    		document.forms[0].action = "../role/Role.do?method=operRole&type=update";
    		document.forms[0].submit();
    	}
    	//删除
    	function del(){
    		document.forms[0].action = "../role/Role.do?method=operRole&type=delete";
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
	<script>window.close();alert("<html:errors name='idus_state'/>");window.close();</script>
	</logic:notEmpty>
  
    <html:form action="/sys/role/Role" method="post">
		 <table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
   			 <td colspan="2" class="tdbgpicload"><bean:message bundle="sys" key="sys.role.roleManagerLoad.role"/></td>
  			</tr>
		 
          <tr>
           <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.role.roleManagerLoad.roleName"/></td>
            <td  class="tdbgcolorloadleft">
		    <html:text property="name"></html:text>
		    <html:hidden property="id"/>
	        </td>
         </tr>
  
         <tr>
           <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.role.roleManagerLoad.delMark"/></td>
            <td  class="tdbgcolorloadleft">
		    <html:select property="delMark">
			<html:option value="1"><bean:message bundle="sys" key="sys.role.roleManagerLoad.zhengchang"/></html:option>
			<html:option value="0"><bean:message bundle="sys" key="sys.role.roleManagerLoad.dongjie"/></html:option>
		    </html:select>
	        </td>
         </tr>
         <tr>
            <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.role.roleManagerLoad.remark"/></td>
            <td  class="tdbgcolorloadleft">
	        <html:textarea cols="20" rows="4" property="remark"></html:textarea>
	        </td>
        </tr> 
		<tr>
		     <td colspan="2"  class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerLoad.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerLoad.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerLoad.delete'/>" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.role.roleManagerLoad.chanel'/>" onclick="javascript:window.close();"/></td>
		  
		</tr>
	</table>
    </html:form>
  </body>
</html:html>
