
<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html  locale="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="sys" key="sys.dep.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
	
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/tools.js"></script>
<script language="javascript">
	
function insertIt(){
		
		
		
		popUp('windows','',480,400);
	}
	
	
	function updateIt()
	{
			var flag;
    		flag = confirm("您确定要修改这条数据吗？");
    		if(flag==1){
    			document.forms[0].submit();
    		}
    		
	}
	function deleteIt()
	{
			var flag;
    		flag = confirm("您确定要删除这条数据吗？");
    		if(flag==0){
    			return false;
    		}
    		
	}
	
	
</script>
  </head>
  
  
  <body>
  <html:form action="/sys/dep.do?method=update">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message bundle="sys" key="sys.dep.title"/></td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.dep.id"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="id" readonly="true" />
		
	</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.dep.name"/></td>
    <td class="tdbgcolorloadleft">
		<html:text property="name" />
	</td>
  </tr>
  
  
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.dep.parentId"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="parentId" />
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright">管理人</td>
    <td  class="tdbgcolorloadleft">
		<html:select property="admin">
		<html:options collection="userList"
  							property="value"
  							labelProperty="label"/>
        			</html:select>
		
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.dep.display"/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="tagShow">
        			<html:option value="1">-<bean:message bundle="sys" key="sys.dep.display.yes"/>-</html:option>
        			<html:option value="0"><bean:message bundle="sys" key="sys.dep.display.no"/></html:option>
        			
        			</html:select>
		
	</td>
  </tr>
  
  <tr>
    <td class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.dep.remark"/></td>
    <td class="tdbgcolorloadleft">
	<html:textarea property="remark"/>
	</td>
  </tr> 
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <img alt="<bean:message bundle='sys' key='sys.insert'/>" src="<bean:write name='imagesinsession'/>sysoper/add.gif" onclick="popUp('windows','dep.do?method=load&type=insert&parentId=<bean:write name='DepInfo' property='id'/>',600,200)" width="16" height="16" target="windows" border="0"/>
    <img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="javascript:updateIt()" width="16" height="16" border="0"/>
    <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="popUp('windows','dep.do?method=todelete&id=<bean:write name='DepInfo' property='id'/>',200,200)" width="16" height="16" target="windows" border="0"/>

</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
