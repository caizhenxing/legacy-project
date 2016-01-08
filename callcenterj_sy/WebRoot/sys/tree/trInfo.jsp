
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
    
    <title><bean:message bundle="sys" key="sys.tree.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
	
<link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/tools.js"></script>
<script language="javascript">
	document.onkeydown = function(){event.keyCode = (event.keyCode == 13)?9:event.keyCode;}
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
  <html:form action="/sys/tree.do?method=update">
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <tr>
    <td colspan="2" class="tdbgpicload"><bean:message bundle="sys" key="sys.tree.title"/></td>
  </tr>
  <tr>
   <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.id"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="id" readonly="true" />
		
	</td>
  </tr>
  
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.name"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="name" />
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.domain"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="domian" />
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.uniqueName"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="procAlias" />
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.parentId"/></td>
    <td  class="tdbgcolorloadleft">
		<html:text property="parentId" />
		
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.display"/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="tagShow">
        			<html:option value="1">-<bean:message bundle="sys" key="sys.tree.display.yes"/>-</html:option>
        			<html:option value="0"><bean:message bundle="sys" key="sys.tree.display.no"/></html:option>
        			
        			</html:select>
		
	</td>
  </tr>
   <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.system"/></td>
    <td  class="tdbgcolorloadleft">
		<html:select property="tagShow">
        			<html:option value="1">-<bean:message bundle="sys" key="sys.tree.system.yes"/>-</html:option>
        			<html:option value="0"><bean:message bundle="sys" key="sys.tree.system.no"/></html:option>
        			
        			</html:select>
		
	</td>
  </tr>
  <tr>
    <td  class="tdbgcolorloadright"><bean:message bundle="sys" key="sys.tree.remark"/></td>
    <td  class="tdbgcolorloadleft">
	<html:textarea property="remark"/>
	</td>
  </tr> 
  <tr>
    <td colspan="2"  class="tdbgcolorloadbuttom">
    <img alt="<bean:message bundle='sys' key='sys.insert'/>" src="<bean:write name='imagesinsession'/>sysoper/add.gif" onclick="popUp('windows','tree.do?method=load&type=insert&parentId=<bean:write name='TreeInfo' property='id'/>',480,400)" width="16" height="16" target="windows" border="0"/>
    <img alt="<bean:message bundle='sys' key='sys.update'/>" src="<bean:write name='imagesinsession'/>sysoper/update.gif" onclick="javascript:updateIt()" width="16" height="16" border="0"/>
    <img alt="<bean:message bundle='sys' key='sys.delete'/>" src="<bean:write name='imagesinsession'/>sysoper/del.gif" onclick="popUp('windows','tree.do?method=todelete&id=<bean:write name='TreeInfo' property='id'/>',200,200)" width="16" height="16" target="windows" border="0"/>
</td>
  </tr>
</table>
</html:form>
  </body>
</html:html>
