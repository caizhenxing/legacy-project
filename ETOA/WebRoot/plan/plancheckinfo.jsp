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
    <SCRIPT language="javascript">
    function update()
    {
    	document.forms[0].submit();
    }
    </script>
    <title>阶段工作计划审核</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
   <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../js/common.js"></script>
    <script language="javascript" src="../js/calendar.js"></script>
  </head>
  
  <body>
 
  <html:form action="/workplan.do?method=updatePlan&check=t">
  <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
  <!--  -->
  <!--  -->
 
 	<TR>
    <td colspan="4" class="tdbgpicload">阶段工作计划</TD>
  </TR>
  <html:hidden property="id"/>
  <tr>
    <td class="tdbgcolorloadright">阶段计划标题</td>
    <td  class="tdbgcolorloadleft"><html:text property="planTitle"/></td>
  
    <td class="tdbgcolorloadright">阶段计划副标</td>
    <td  class="tdbgcolorloadleft"><html:text property="planSubhead"/></td>
  </tr>
  <TR>
    <td class="tdbgcolorloadright">阶段计划开始时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planBeignTime"  onfocus="calendar()"></html:text></TD>
  
    <td class="tdbgcolorloadright">阶段计划结束时间</TD>
    <td  class="tdbgcolorloadleft"><html:text property="planEndTime"  onfocus="calendar()"></html:text></TD>
  </TR>
   <TR>
    <td class="tdbgcolorloadright">阶段计划时间范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planTimeType">
    		<html:options collection="planTimeList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
    	<td class="tdbgcolorloadright">审核</td>
    <td  class="tdbgcolorloadleft">
    
    <html:select property="checkId">
      		<html:option value="">
      			请选择
      		</html:option>
    		<html:options collection="auditingList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
    
<%--    <html:select property="checkId">--%>
<%--    --%>
<%--    --%>
<%--    --%>
<%--    <html:option value="2">待审核</html:option>--%>
<%--    <html:option value="1">发布</html:option>--%>
<%--    <html:option value="0">不发布</html:option>--%>
<%--    </html:select>--%>
   </td>
	<!--  
    <td class="tdbgcolorloadright">阶段计划范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planDomainType">
    
    		<html:options collection="planDomainList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
    	-->
  </tr> 
  
  <!--  
  <tr>
  <td class="tdbgcolorloadright">上层计划</TD>
    <td  class="tdbgcolorloadleft">
    <html:select property="parentId">
    <html:option value="">------</html:option>
    		<html:options collection="planList"
  							property="value"
  							labelProperty="label"/>
    	</html:select>
	</TD>
    
    <td class="tdbgcolorloadright">计划可视范围</td>
    <td  class="tdbgcolorloadleft">
    <html:select property="planViewType">
    		<html:options collection="planViewList"
  							property="value"
  							labelProperty="label"/>
    	</html:select></td>
  </tr> 
-->

<%--  专门用做意见审核 --%>
<%--  <tr>--%>
<%--  	<td></td>--%>
<%--  	<td>ddddddd</td>--%>
<%--  	<td>ssss</td>--%>
<%--  	<td>aa</td>--%>
<%--  	--%>
<%--  	--%>
<%--  </tr>--%>
  <tr>
    <td  class="tdbgcolorloadright">阶段计划信息</td>
    <td  class="tdbgcolorloadleft"><html:textarea property="planInfo" rows="5" cols="30" /></td>
  
    <td  class="tdbgcolorloadright">备注</td>
    <td  class="tdbgcolorloadleft"><html:textarea  property="remark" rows="5" cols="30" ></html:textarea></td>
  </tr>
  <tr>
    <td colspan="4"  class="tdbgcolorloadbuttom">
    
  
    
    <a href="javascript:update()" >审核阶段计划</a>
     
  	</td>
  </tr>
</table>
   		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		 <logic:notEmpty name="listTitle">
		 <tr>
		 <td colspan="6" class="tdbgpicload"><bean:write name="listTitle" /></TD>
		 </tr>
		 </logic:notEmpty>
		  <tr>
		    <td class="tdbgpiclist">计划名</td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.begintime"/></td>
		    <td class="tdbgpiclist"><bean:message key="oa.privy.plan.endtime"/></td>
		    <td class="tdbgpiclist">计划简介</td>
		    <td class="tdbgpiclist">属于阶段计划</td>
		    <td class="tdbgpiclist">操作</td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
		  <%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  <tr>
		    <td class="<%=style%>"><bean:write name="c" property="name" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="beginTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="endTime" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="missionInfo" filter="true"/></td>
		    <td class="<%=style%>"><bean:write name="c" property="planName" filter="true"/></td>
		    <td class="<%=style%>"><html:link action="/workmission.do?method=changemission&type=up" paramId="id" paramName="c" paramProperty="id" onclick="popUp('windows','',880,300)" target="windows">修改</html:link></td>
		   </tr>
		  </logic:iterate>
		</table>
</html:form>
  </body>
</html:html>
