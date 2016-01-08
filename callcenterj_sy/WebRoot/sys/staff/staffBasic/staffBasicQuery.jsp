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
    
    <title>ְ����ѯ</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="../../../images/css/styleA.css" rel="stylesheet" type="text/css" />
 <link href="../../../images/css/jingcss.css" rel="stylesheet" type="text/css" />
 <SCRIPT language=javascript src="../../../js/form.js" type=text/javascript></SCRIPT>
 
 <script type="text/javascript">

 	function addinter()
 	{
 		document.forms[0].action="../intersave.do?method=tointerSaveLoad";
 		document.forms[0].submit();
 	}
 	
 	function query()
 	{
 		document.forms[0].action="../staffBasic.do?method=toStaffBasicList";
 		document.forms[0].target="bottomm";
 		document.forms[0].submit();
 		window.close();
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
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
 </script>
 
  </head>
  
  <body >
    <html:form action="/sys/staff/staffBasic" method="post">
    
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		    <td class="tdbgcolorquerytitle">
		    ��ǰλ��&ndash;&gt;ְ����Ϣ
		    </td>
		  </tr>
		</table>
    
    	<table width="90%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td class="tdbgcolorloadright">
    				��λ����
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="BStaffName" styleClass="input"/>
    			</td>
    			<td class="tdbgcolorloadright">
    				�ǳ�
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="BStaffNickname" styleClass="input"/>
    			</td>
    			<td class="tdbgcolorloadright">
    				Ա�����
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="BStaffNum" styleClass="input"/>
    			</td>
    		</tr>
    		    		<tr>
    			<td class="tdbgcolorloadright">
    				�ֻ�����
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="linkMobileNum" styleClass="input"/>
    			</td>
    			<td class="tdbgcolorloadright">
    				סլ�绰
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="linkHomeNum" styleClass="input"/>
    			</td>
    			<td class="tdbgcolorloadright">
    				�Ƿ���ְ
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:text property="dictIsBeginwork" styleClass="input"/>
    			</td>
    		</tr>
    		    		<tr>
    			<td class="tdbgcolorloadright">
    				֤������ 
    			</td>
    			<td class="tdbgcolorloadleft">
    				<html:select property="BDictPaperType">
    					<html:option value="">
    						��ѡ��
    					</html:option>
    						
    					<html:option value="residents">
    						�������֤
    					</html:option>
    					
    					<html:option value="soldiers">
    						����֤
    					</html:option>
    				</html:select>
    			</td>
    			<td class="tdbgcolorloadright">
    				֤������
    			</td>
    			<td class="tdbgcolorloadleft" colspan="3">
    				<html:text property="BPaperNum" size="50" styleClass="input"/>
    			</td>
    		
    		</tr>
    		<tr>
    			
    			<td colspan="6" class="tdbgcolorloadright">
    				<input type="button" name="btnSearch" value="��ѯ"  class="button" onclick="query()"/>
<%--    				<input type="button" name="btnadd" value="���" onclick="popUp('windows','intersave.do?method=tointerSaveLoad&type=insert',650,200)"/>--%>
    				<input type="reset" value="ˢ��"  class="button"/>
    			</td>
    			
    		</tr>
    		
<%--    		<html:hidden property="interUsername"/>--%>
    	</table>
    </html:form>
  </body>
</html:html>
/