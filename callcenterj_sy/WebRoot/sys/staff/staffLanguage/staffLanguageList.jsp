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
    
    <title>interSaveList.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
<%--  <link href="<bean:write name='cssinsession'/>" rel="stylesheet" type="text/css" />--%>
     <script language="javascript" src="../../js/common.js"></script>
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
 		document.forms[0].action="../staffLanguage.do?method=toStaffLanguageList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 	}
 </script>
  </head>
  
  <body>
	<html:form action="/sys/staff/staffLanguage" method="post">

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    ��ǰλ��&ndash;&gt;������Ϣ
		    </td>
		  </tr>
		</table>
    
    	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td class="tdbgcolorloadright">
    				��������
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:select property="dictLanguageType">
    				<html:option value="">
    				��ѡ��
    				</html:option>
    				<html:option value="english">
    					Ӣ��
    				</html:option>
    				<html:option value="japanese">
    					����
    				</html:option>
    		
    			
    				</html:select>
    			</td>
    			<td class="tdbgcolorloadright">
    				���Եȼ�
    			</td>
    			<td class="tdbgcolorloadleft">
						<html:select property="dictLanguageDegree">
	    				<html:option value="">
	    				��ѡ��
	    				</html:option>
	    				<html:option value="3">
	    					3��
	    				</html:option>
	    				<html:option value="4">
	    					4��
	    				</html:option>
	    		
	    			
	    				</html:select>
    			</td>
    			
    		</tr>
    		<tr>
    			
    			<td colspan="4" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="��ѯ"  class="button" onclick="query()"/>
    				<input type="button" name="btnadd" value="���" onclick="popUp('staffLanguagewindows','staffLanguage.do?method=toStaffLanguageLoad&type=insert',650,150)"/>
    				<input type="reset" value="ˢ��"  class="button"/>
    			</td>
    			
    		</tr>
    		
    	</table>
	<br>
	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr>
<%--		    <td class="tdbgpiclist">��ַ����</td>--%>
		    <td class="tdbgpiclist">��������</td>
		    <td class="tdbgpiclist">���Եȼ�</td>
		    <td class="tdbgpiclist" width="15%">����</td>
		    
		  </tr>
		  	  <logic:iterate id="c" name="list" indexId="i">
		 	<%
 			 String style=i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
  		  %>
		  	  
		  <tr>
		    
		    <td ><bean:write name="c" property="dictLanguageType" filter="true"/></td>
		    <td ><bean:write name="c" property="dictLanguageDegree" filter="true"/></td>

		    
            <td width="15%" >
            <img alt="��ϸ" src="../../../images/sysoper/particular.gif" onclick="popUp('staffLanguagewindows','staffLanguage.do?method=toStaffLanguageLoad&type=detail&id=<bean:write name='c' property='id'/>',650,150)" width="16" height="16" border="0"/>
            <img alt="�޸�" src="../../../images/sysoper/update.gif" onclick="popUp('staffLanguagewindows','staffLanguage.do?method=toStaffLanguageLoad&type=update&id=<bean:write name='c' property='id'/>',650,150)" width="16" height="16" border="0"/>
		    <img alt="ɾ��" src="../../../images/sysoper/del.gif" onclick="popUp('staffLanguagewindows','staffLanguage.do?method=toStaffLanguageLoad&type=delete&id=<bean:write name='c' property='id'/>',650,150)" width="16" height="16"  border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		    <td colspan="3">
				<page:page name="staffLanguagepageTurning" style="second"/>
		    </td>
		  </tr>
	</table>
		  
	</html:form>
  </body>
</html:html>
 