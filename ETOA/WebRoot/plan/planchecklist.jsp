<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>




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
    <script language="javascript" src="../js/common.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
    <script language="javascript" src="../js/tools.js"></script>
    
    
    <script type="text/javascript">
    	
<%--    	function myrandom()--%>
<%--    	{--%>
<%--    		var = i;--%>
<%--    		--%>
<%--    		i = Math.round(Math.random()*10000)) --%>
<%--    		--%>
<%--    		return i;--%>
<%--    	}--%>
    	
    	function toback(name)
    	{
    	
			var flag;
    		flag = window.confirm("您确定要对<"+ name +">这条数据操作吗？");
    	
    		if(flag==false){
    			return false;
    		}
    		
    			opener.parent.toppp.document.all.btnSearch.click();
    	
			
		}
    	
    </script>
    
  </head>
  
  <body>
  <html:form action="/workplan.do">
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 
		  <tr>
		    <td class="tdbgpiclist">阶段计划名</td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.begintime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.endtime"/></td>
		    <td class="tdbgpiclist">阶段计划副标</td>
		    <td class="tdbgpiclist">计划人</td>
		    <td class="tdbgpiclist">详细</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="planTitle" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planBeignTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planEndTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planSubhead" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planDomain" filter="true"/></td>
			<td class="<%=style%>">
			
			
			
			<logic:equal name="auditingType" value="another" >
			
			<html:link action="/workplan.do?method=planInfo&check=t" paramId="id" paramName="c" paramProperty="id"  target="_blank">审核</html:link>	
<%--			<html:link action="/workplan.do?method=myupdateplan&linkType=true&type=del" paramId="id" paramName="c" paramProperty="id" onclick="return toback()" target="">删除</html:link>--%>
			<a href="../workplan.do?method=myupdateplan&linkType=true&type=del&id=<bean:write name="c" property="id" filter="true"/>" onclick="return toback('<bean:write name="c" property="planTitle" filter="true"/>')">删除</a>
			</logic:equal>
			
			<logic:equal name="auditingType" value="auditingdel">
<%--			<html:link action="/workplan.do?method=myupdateplan&linkType=false&type=recover" paramId="id" paramName="c" paramProperty="id" onclick="toback()" target="">恢复</html:link>--%>
			<a href="../workplan.do?method=myupdateplan&linkType=false&type=recover&id=<bean:write name="c" property="id" filter="true"/>" onclick="return toback('<bean:write name="c" property="planTitle" filter="true"/>')">恢复</a>
			</logic:equal>
			
			
			
			</td>
		   </tr>
		  </logic:iterate>
		  
		  <td colspan="7" ><page:page name="planinfoPageTurning" style="first"/></td>

</table>
</html:form>
  </body>
</html:html>