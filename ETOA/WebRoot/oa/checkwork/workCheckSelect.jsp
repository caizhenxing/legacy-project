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
      <bean:message key="hl.bo.oa.checkwork.workcheckselect.title" />
    </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword 3">
    <meta http-equiv="description" content="This is my page">
    <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript></script>
    <script language="JavaScript" src="../js/validate_checkwork.js"></script>
    <script language="JavaScript" src="../../js/calendar.js"></script>
    <script language="javascript">
      function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.startT,"<bean:message bundle='sys' key='sys.common.beginTime'/>")) return false;
            if (!checkNotNull(addstaffer.endT,"<bean:message bundle='sys' key='sys.common.endTime'/>")) return false;
            if(compdate(addstaffer.startT.value,addstaffer.endT.value)) return false;
              return true;
            }
      //查询
      function query() {
        var f =document.forms[0];
	    if(checkForm(f)){
        document.forms[0].action = "../checkWork.do?method=toList";
        document.forms[0].target = "bottomm";
        document.forms[0].submit();
        }
      }
      //添加人员List
      function addSelect() {
        var page        = "absenceWork.do?method=toUserList&page=resign";
        var winFeatures = "dialogHeight:300px; dialogLeft:200px;";
        var obj         = document.workCheckBean;

        window.showModalDialog(page, obj, winFeatures);
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
    function compdate(stime,etime){
	var ass,aD,aS;
	var bss,bD,bS;
	//a=document.create.Inject_Cycle2.value;	//得到签约时间；
	//b=document.create.Cycle2.value;		  	//得到内容制作时间；	
	ass=stime.split("-");					  	//以"-"分割字符串，返回数组；
	aD=new Date(ass[0],ass[1],ass[2]);		//格式化为Date对像;
	aS=aD.getTime();	//得到从 1970 年 1 月 1 日开始计算到 Date 对象中的时间之间的毫秒数
	
	bss=etime.split("-");
	bD=new Date(bss[0],bss[1],bss[2]);
	bS=bD.getTime();
	
	if(aS>bS){
		alert("<bean:message bundle='sys' key='sys.common.beginLtEndTime'/>");
		create.Cycle2.focus();
		return ture;
	}
	return false;
}
    </script>
  </head>
  <body>
    <html:form action="/oa/checkWork.do" method="POST" onsubmit="return validate_workcheckSelect(this)">
      <table width="50%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
        <tr>
          <td colspan="4"  class="tdbgcolorquerytitle">
              <bean:message bundle="sys" key="sys.current.page"/><bean:message key="hl.bo.oa.checkwork.workcheckselect.selectT" />
          </td>
        </tr>
        <tr>
          <td  class="tdbgcolorqueryright"><bean:message key="hl.bo.oa.checkwork.workcheckselect.startT" /></td>
          <td class="tdbgcolorqueryleft"><html:text maxlength="10" property="startT" size="10" styleClass="input" readonly="true" onfocus="calendar()"/></td>
          <td  class="tdbgcolorqueryright"><bean:message key="hl.bo.oa.checkwork.workcheckselect.endT" /></td>
          <td class="tdbgcolorqueryleft"><html:text maxlength="10" property="endT" size="10" styleClass="input" readonly="true" onfocus="calendar()"/></td>
          
        </tr>
        <tr>
          <td  class="tdbgcolorqueryright"><bean:message key="hl.bo.oa.checkwork.workcheckselect.department" /></td>
          <td class="tdbgcolorqueryleft"><html:select property="department">
              <html:option value="y"><bean:message bundle="sys" key="sys.common.select"/></html:option>
              <html:optionsCollection name="departLists" label="label" value="value" styleClass="input"/>
            </html:select>
          </td>
         <td  class="tdbgcolorqueryright"><bean:message bundle="sys" key="sys.common.name"/></td>
         <td class="tdbgcolorqueryleft">
            <html:select property="repairUser">
              <html:option value="y"><bean:message bundle="sys" key="sys.common.select"/></html:option>
              <html:optionsCollection name="nameList" label="label" value="value" styleClass="input"/>
            </html:select>
<%--         <html:text property="repairUser" styleClass="input"/>--%>
<%--         <input name="Submit2" type="button" class="bottom" value="<bean:message key='hl.bo.oa.checkwork.resign.User'/>"  onclick="addSelect()" />--%>
         </td>
         </tr>
         <tr>
          <td colspan="4"  class="tdbgcolorquerybuttom">
              <input name="Submit" type="button" class="bottom" value="<bean:message bundle='sys' key='sys.select'/>" onclick="query()"/>
<%--              <input name="btnAdd" type="button" class="bottom" value="补签登记" onclick="popUp('windows','../oa/absenceWork.do?method=toResign',680,400)"/>              --%>
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html:html>
