<%@ page language="java" pageEncoding="gb2312" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>
      <bean:message key="hl.bo.oa.checkwork.workchecklist.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.date"/>
        </td>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.onduty"/>
        </td>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.offduty"/>
        </td>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.repairmsg"/>
        </td>
<%--        <td class="tdbgpiclist">--%>
<%--          未登记--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--         未签到--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          请假--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          外出--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          出差--%>
<%--        </td>--%>
      </tr>
      <logic:iterate id="checkLists" name="checkLists" indexId="i">
      <%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  	  %>
        <tr>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="checkDate" filter="true" />
            <logic:equal name="checkLists" property="week"  value="1">
              <bean:message bundle="sys" key="sys.common.week.monday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="2">
              <bean:message bundle="sys" key="sys.common.week.tuesday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="3">
              <bean:message bundle="sys" key="sys.common.week.wednesday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="4">
              <bean:message bundle="sys" key="sys.common.week.thursday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="5">
              <bean:message bundle="sys" key="sys.common.week.friday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="6">
              <bean:message bundle="sys" key="sys.common.week.saturday"/>
            </logic:equal>
            <logic:equal name="checkLists" property="week"  value="7">
              <bean:message bundle="sys" key="sys.common.week.sunday"/>
            </logic:equal>
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="cz" filter="true" />
<%--            <logic:equal name="checkLists" property="later"  value="1">--%>
<%--              迟到--%>
<%--            </logic:equal>--%>
<%--            <logic:equal name="checkLists" property="later"  value="0">--%>
<%--              未签到--%>
<%--            </logic:equal>--%>
           <bean:message name="checkLists" property="later" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="zt" filter="true" />
<%--            <logic:equal name="checkLists" property="early"  value="2">--%>
<%--              早退--%>
<%--            </logic:equal>--%>
<%--            <logic:equal name="checkLists" property="early"  value="0">--%>
<%--              未签到 --%>
<%--            </logic:equal>--%>
          <bean:message name="checkLists" property="early" />
          </td>
          <td class=<%=style%>>
            <bean:write name="checkLists" property="repairTime" filter="true" />
          </td>
        </tr>
      </logic:iterate>
    </table>
<br>
<%-- 请假   --%>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.qingjiaReason"/> 
        </td>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.beginDate"/>
        </td>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.endDate"/>
        </td>
<%--        <td class="tdbgpiclist">--%>
<%--         预计归来时间--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          未登记--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--         未签到--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          请假--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          外出--%>
<%--        </td>--%>
<%--        <td class="tdbgpiclist">--%>
<%--          出差--%>
<%--        </td>--%>
      </tr>
      <logic:iterate id="qingjiaList" name="qingjiaList" indexId="i">
      <%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  	  %>
        <tr>
          <td class=<%=style%>>
            <bean:write name="qingjiaList" property="reason" filter="true" />
          </td>
<%--          <td class=<%=style%>>--%>
<%--            <bean:write name="qingjiaList" property="checkDate" filter="true" />--%>
<%--          </td>--%>
          <td class=<%=style%>>
            <bean:write name="qingjiaList" property="startDate" filter="true" />
            <bean:write name="qingjiaList" property="sTime" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="qingjiaList" property="endDate" filter="true" />
            <bean:write name="qingjiaList" property="eTime" filter="true" />
          </td>
        </tr>
      </logic:iterate>
    </table>
    <br>
    <%--  出差  --%>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.chuchaiReason"/>
        </td>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.beginDate"/>
        </td>
        <td class="tdbgpiclist">
          <bean:message key="et.oa.checkwork.laterOrEarly.endDate"/>
        </td>
      </tr>
      <logic:iterate id="chuchaiList" name="chuchaiList" indexId="i">
      <%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  	  %>
        <tr>
          <td class=<%=style%>>
            <bean:write name="chuchaiList" property="reason" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="chuchaiList" property="startDate" filter="true" />
            <bean:write name="chuchaiList" property="sTime" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="chuchaiList" property="endDate" filter="true" />
            <bean:write name="chuchaiList" property="eTime" filter="true" />
          </td>
        </tr>
      </logic:iterate>
    </table>
    <br>
    <%--  外出  --%>
<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
      <tr>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.waichuReason"/>
        </td>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.applyTime"/>
        </td>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.outTime"/>
        </td>
        <td class="tdbgpiclist">
         <bean:message key="et.oa.checkwork.laterOrEarly.returnTime"/>
        </td>
      </tr>
      <logic:iterate id="waichuList" name="waichuList" indexId="i">
      <%
 		 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  	  %>
        <tr>
          <td class=<%=style%>>
            <bean:write name="waichuList" property="reason" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="waichuList" property="checkDate" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="waichuList" property="startDate" filter="true" />
            <bean:write name="waichuList" property="sTime" filter="true" />
          </td>
          <td class=<%=style%>>
            <bean:write name="waichuList" property="endDate" filter="true" />
            <bean:write name="waichuList" property="eTime" filter="true" />
          </td>
        </tr>
      </logic:iterate>
    </table>
    <br>
    <table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="tdbgcolorloadbuttom">
		    <input name="addgov" type="button" class="buttom" value="<bean:message bundle='sys' key='sys.close'/>" onClick="javascript:window.close();"/>
		    </td>
		  </tr>
	</table>
  </body>
</html:html>
