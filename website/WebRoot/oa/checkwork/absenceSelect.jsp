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
      <bean:message key="hl.bo.oa.checkwork.absenceSelect.title"/>
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="../js/validate-checkwork.js"></script>
    <script language="JavaScript" src="../../js/calendar.js"></script>
    <script language="javascript">
      //查询
      function query() {
        document.forms[0].action = "../absenceWork.do?method=absenceList";
        document.forms[0].target = "bottomm";

        document.forms[0].submit();
      }
      function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
      //时间页
      function openwin(param) {
        var aResult = showCalDialog(param);

        if (aResult != null) {
          param.value = aResult;
        }
      }

      function showCalDialog(param) {
        var url     = "checkwork/calendar.html";
        var aRetVal = showModalDialog(url, "status=no", "dialogWidth:182px;dialogHeight:215px;status:no;");

        if (aRetVal != null) {
          return aRetVal;
        }

        return null;
      }
    </script>
  </head>
  <body>
    <html:form action="/oa/absenceWork.do" method="POST">
      <table width="70%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <td colspan="4"  class="tdbgcolorquerytitle">
<%--          <logic:equal name="type" value="qingjia">--%>
<%--		     <bean:message bundle="sys" key="sys.current.page"/>请假查询 --%>
<%--		     <html:hidden property="absenceType" value="1"/>--%>
<%--		  </logic:equal>--%>
<%--          <logic:equal name="type" value="waichu">--%>
<%--		     <bean:message bundle="sys" key="sys.current.page"/>外出查询 --%>
<%--		     <html:hidden property="absenceType" value="2"/>--%>
<%--		  </logic:equal>--%>
<%--		  <logic:equal name="type" value="chuchai">--%>
<%--		     <bean:message bundle="sys" key="sys.current.page"/>出差查询 --%>
<%--		     <html:hidden property="absenceType" value="3"/>--%>
<%--		  </logic:equal>--%>
		     <bean:message bundle="sys" key="sys.current.page"/><bean:message key="et.oa.checkwork.absenceQuery"/>
         </td>
        </tr>
        <tr>
          <td  class="tdbgcolorqueryright" ><bean:message key="et.oa.checkwork.department"/></td>
          <td class="tdbgcolorqueryleft" ><html:select property="department">
              <html:option value="select">
                <bean:message key="hl.bo.oa.checkwork.absenceSelect.select"/>
              </html:option>
              <html:optionsCollection name="departLists" label="label" value="value" styleClass="input"/>
            </html:select>          </td>
          <td  class="tdbgcolorqueryright" ><bean:message bundle="sys" key="sys.common.name"/></td>
          <td class="tdbgcolorqueryleft" ><html:text  property="absenceUser"  styleClass="input"/></td>
         
        </tr>
        <tr>
          <td class="tdbgcolorqueryright" ><bean:message bundle="sys" key="sys.common.beginTime"/></td>
          <td class="tdbgcolorqueryleft" ><html:text property="startDate"  styleClass="input" readonly="true" onfocus="calendar()"/></td>
          <td class="tdbgcolorqueryright" ><bean:message bundle="sys" key="sys.common.endTime"/></td>
          <td class="tdbgcolorqueryleft" ><html:text property="endDate" styleClass="input" readonly="true" onfocus="calendar()"/></td>
        </tr>
        <tr>
          <td class="tdbgcolorqueryright" ><bean:message key="et.oa.checkwork.absenceType"/></td>
          <td class="tdbgcolorqueryleft" >
            <html:select property="absenceType">
              <html:option value="select"><bean:message bundle="sys" key="sys.common.pleaseSelect"/></html:option>
              <html:option value="1"><bean:message key="et.oa.checkwork.qingjia"/></html:option>
              <html:option value="2"><bean:message key="et.oa.checkwork.waichu"/></html:option>
              <html:option value="3"><bean:message key="et.oa.checkwork.chuchai"/></html:option>
            </html:select>
              </td>
          <td class="tdbgcolorqueryright" ></td>
          <td class="tdbgcolorqueryleft" ></td>
        </tr>
        <tr>
    <td colspan="4"  class="tdbgcolorquerybuttom">            
          <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.select'/>" onclick="query()"/>              
<%--          <logic:equal name="type" value="qingjia">--%>
<%--		     <input name="btnAdd" type="button" class="bottom" value="请假登记" onclick="popUp('windows','../oa/absenceWork.do?method=toAbsence&operType=qingjia',680,400)"/> --%>
<%--		  </logic:equal>--%>
<%--          <logic:equal name="type" value="waichu">--%>
<%--		     <input name="btnAdd" type="button" class="bottom" value="外出登记" onclick="popUp('windows','../oa/absenceWork.do?method=toAbsence&operType=waichu',680,400)"/> --%>
<%--		  </logic:equal>--%>
<%--		  <logic:equal name="type" value="chuchai">--%>
<%--		     <input name="btnAdd" type="button" class="bottom" value="出差登记" onclick="popUp('windows','../oa/absenceWork.do?method=toAbsence&operType=chuchai',680,400)"/> --%>
<%--		  </logic:equal>--%>
    </td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
