<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>ְ������</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/common.js"></script>
<SCRIPT language=javascript src="../../../js/calendar.js" type=text/javascript>
</SCRIPT>
 <script type="text/javascript">
 
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
   	function query()
 	{
 		document.forms[0].action="../staffExperience.do?method=toStaffExperienceList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
 </script>
  </head>
  
  <body>
	<html:form action="/sys/staff/staffExperience" method="post">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    ��ǰλ��&ndash;&gt;������Ϣ
		    </td>
		  </tr>
		</table>
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    		<tr>
    				<td class="tdbgcolorloadright">
	    				��������
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    				<html:select property="dictExperienceType">
	    				<html:option value="">
	    				��ѡ��
	    				</html:option>
	    				<html:option value="work">
	    				����
	    				</html:option>
	    				<html:option value="teach">
	    				����
	    				</html:option>
						</html:select>
	    			</td>
    				<td class="tdbgcolorloadright">
	    					������λ/ѧУ����
	    			</td>
	    			<td class="tdbgcolorloadleft">
	    			<html:text property="company" size="40" styleClass="input"/>
	    			</td>
    		</tr>
    		<tr>
    			<td class="tdbgcolorloadright">
	    					��ʼʱ��
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="beginTime" onfocus="calendar()" styleClass="input"/>
	    				</td>
	    				<td class="tdbgcolorloadright">
	    					����ʱ��
	    				</td>
	    				<td class="tdbgcolorloadleft">
	    					<html:text property="endTime" onfocus="calendar()" styleClass="input"/>
	    				</td>
    		</tr>
    		<tr>
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="��ѯ"  class="button" onclick="query()"/>
    				<input type="button" name="btnadd" value="���" onclick="popUp('staffExperienceWindows','staffExperience.do?method=toStaffExperienceLoad&type=insert',650,400)"/>
    				<input type="reset" value="ˢ��"  class="button"/>
    			</td>
 
    		</tr>
    	</table>
		
	<br>
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td class="tdbgpiclist" width="10%">��������</td>
		    <td class="tdbgpiclist" width="10%">��ʼʱ��</td>
		    <td class="tdbgpiclist" width="10%">����ʱ��</td>
		    <td class="tdbgpiclist">��ַ</td>
		    <td class="tdbgpiclist" width="10%">����</td>
		    
		  </tr>
		  	  <logic:iterate id="c" name="list" indexId="i">
		 	<%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  	  
		  <tr>

		   
		    <td ><bean:write name="c" property="dictExperienceType" filter="true"/></td>
		    <td ><bean:write name="c" property="beginTime" filter="true"/></td>
		    <td ><bean:write name="c" property="endTime" filter="true"/></td>
		    <td ><bean:write name="c" property="address" filter="true"/></td>

		    
            <td width="10%" >
            <img alt="��ϸ" src="../../../images/sysoper/particular.gif" onclick="popUp('staffExperienceWindows','staffExperience.do?method=toStaffExperienceLoad&type=detail&id=<bean:write name='c' property='id'/>',650,400)" width="16" height="16" border="0"/>
            <img alt="�޸�" src="../../../images/sysoper/update.gif" onclick="popUp('staffExperienceWindows','staffExperience.do?method=toStaffExperienceLoad&type=update&id=<bean:write name='c' property='id'/>',650,400)" width="16" height="16" border="0"/>
		    <img alt="ɾ��" src="../../../images/sysoper/del.gif" onclick="popUp('staffExperienceWindows','staffExperience.do?method=toStaffExperienceLoad&type=delete&id=<bean:write name='c' property='id'/>',650,400)" width="16" height="16"  border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="5">
				<page:page name="StaffExperiencepageTurning" style="second"/>
		    </td>
		  </tr>
	</table>
		  
	</html:form>
  </body>
</html:html>
c