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
    <script language="javascript" src="../../js/common.js"></script>
    <script language="javascript">
        //ѡ������
    	function selectAll() {
			for (var i=0;i<document.forms[0].cchk.length;i++) {
				var e=document.forms[0].cchk[i];
				e.checked=!e.checked;
			}
		}
		//ɾ��ѡ��
		function delSelect(){
			document.forms[0].action = "../outemail.do?method=operEmail&type=delete";
			document.forms[0].submit();
		}
		//����ɾ����ѡ�ļ�
		function delForever(){
			document.forms[0].action = "../outemail.do?method=operEmail&type=deleteForever";
			document.forms[0].submit();
		}
		//����ҳ
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
    </script>
  </head>
  
  <body>
    <logic:notEmpty name="idus_state">
	<script>alert("<html:errors name='idus_state'/>");</script>
	</logic:notEmpty>
    <html:form action="/oa/communicate/outemail.do" method="post">
    	<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
    		<tr>
    			<td>
    				<input type="button" name="btnPutRec" value="<bean:message key='oa.communicate.outemail.outeremaillist.sendbox'/>" onclick="popUp('windows','outemail.do?method=toEmailLoad&type=insert',650,550)"/>
    			</td>
    		</tr>
    	</table>
		<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="tablebgcolor">
		  <tr class="tdbgpiclist">
		    <td><bean:message key="agrofront.email.emailList.select"/></td>
		    <td><bean:message key="agrofront.email.emailList.takeuser"/></td>
		    <td><bean:message key="agrofront.email.emailList.emailtitle"/></td>
		    <td><bean:message key="agrofront.email.emailList.answer"/></td>
		  </tr>
		  <logic:iterate id="c" name="list" indexId="i">
			<%
				String style =i.intValue()%2==0?"tdbgcolorlist1":"tdbgcolorlist2";
			%>
		  <tr>
		    <td class="<%=style%>">
		    <html:multibox property="cchk"><bean:write name='c' property='id' filter='true'/></html:multibox>
		    </td>
		    <td class="<%=style%>"><bean:write name="c" property="takeUser" filter="true"/></td>
		    <td class="<%=style%>">
		    <a href="../outemail.do?method=toEmailLoad&type=see&id=<bean:write name='c' property='id' filter='true'/>" target="_blank">
		    <bean:write name="c" property="emailTitle" filter="true"/>
		    </a>
		    </td>
		    <td class="<%=style%>">
		    <img alt="<bean:message key='agrofront.email.emailList.answer'/>" src="<bean:write name='imagesinsession'/>sysoper/answer.gif" onclick="window.open('outemail.do?method=toEmailLoad&type=answer&id=<bean:write name='c' property='id'/>','windows','750.700,scrollbars=yes')" width="16" height="16" target="windows" border="0"/>
		    </td>
		  </tr>
		  </logic:iterate>
		  <tr>
		  	<td class="tdbgcolor2">
		  	<a href="javascript:selectAll()"><bean:message key="agrofront.afiche.aficheList.selectornot"/></a>
		    <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
		  	</td>
		    <td colspan="3" align="right" class="tdbgcolor2">
				<page:page name="outemailpageTurning" style="first"/>
		    </td>
		  </tr>
		  <tr>
		  	<td colspan="4">

	        <input type="button" name="btnPutRec" value="<bean:message key='agrofront.email.emailList.putrecycle'/>" onclick="delSelect()"/>
		  	<input type="button" name="btnDel" value="<bean:message key='agrofront.email.emailList.delete'/>" onclick="delForever()"/>
		  	</td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
