<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GB2312"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/moduleManager" method="post">
<%--       ͷ     --%>
          <TABLE width="100%" cellSpacing=1 cellPadding=6 align=center>
            <TBODY>
              <TR>
                <TD style="FONT-WEIGHT: bold; COLOR: #ffffff" bgColor=#6da6d1 colSpan=10>
                  <DIV>
                    <DIV style="DISPLAY: inline; FLOAT: left; COLOR: #ffffff"><STRONG><A 
                      name=section1></A>������̳</STRONG></DIV>
                    <DIV style="DISPLAY: inline; FLOAT: right"><A style="COLOR: #ffffff" 
                      href="http://192.168.1.9:8088/bmb/admin.php?bmod=setforum.php#top">[ 
                          ���ص��˵� ]</A></DIV></DIV>
                </TD>
              </TR>
            </TBODY>
          </TABLE>
<%--      ͷ����    --%>
       <br>
      
<%--  ��������    --%>
            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
              <TBODY>
                <TR>
                  <TD>
                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                      <TBODY>
                        <TR>
                          <TD bgColor=#ffffff><FONT face=verdana><STRONG><bean:write name="forumName" filter="true"/></STRONG><br>   
                            <A href="../moduleManager.do?method=toParentModuleLoad&type=insert&createType=parent">�½�����̳</A>
                          | <A href="../moduleManager.do?method=toModuleLoad&type=insert&createType=son">�½�����̳</A> <BR></FONT>
                          </TD>
                        </TR>
                       </TBODY>
                      </TABLE>
                   </TD>
                 </TR>
               </TBODY>
            </TABLE>
<%--   �������ý���    --%>
            
<%--      ������̳ģ������         --%>
<logic:iterate id="c" name="moduleMap" indexId="i">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
              <TBODY>
                <TR>
                  <TD>
                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                      <TBODY>
                        <TR>
                          <TD bgColor=#ffffff><FONT face=verdana>&nbsp;&nbsp;&nbsp;&nbsp;<STRONG>��̳��:</STRONG> 
                            <%--        ��̳��<bean:write>                  --%>
                            <bean:write name="c" property="key"/> 
<%--                          <A href="../moduleManager.do?method=toParentModuleLoad&type=update&id=<bean:write name='c' property='key'/>" >�༭����̳</A>--%>
<%--                        | <A href="../moduleManager.do?method=toParentModuleLoad&type=delete&id=<bean:write name='c' property='key'/>" >ɾ������̳</A> --%>
<%--                              <A href="../moduleManager.do?method=toModuleLoad&type=insert">�½�����̳</A> --%>
               
                        
<%--          ��ģ�����ÿ�ʼ                  --%>
<logic:iterate id="cValue" name="c" property="value" >
                            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
                              <TBODY>
                                <TR>
                                  <TD>
                                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                                      <TBODY>
                                        <TR>
                                          <TD bgColor=#ffffff><FONT face=verdana>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<STRONG>����̳��:</STRONG> 
                                         <%--    ����̳��<bean:write>    --%>
                                          <bean:write name='cValue' property='topicTitle' filter='true'/><BR>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <A href="../moduleManager.do?method=toModuleLoad&type=update&id=<bean:write name='cValue' property='id' filter='true'/>" >�༭������̳</A> 
                                          | <A href="../moduleManager.do?method=toModuleLoad&type=delete&id=<bean:write name='cValue' property='id' filter='true'/>" >ɾ��������̳</A> 
                                          </TD>
                                        </TR>
                                      </TBODY>
                                    </TABLE>
                                  </TD>
                                </TR>
                              </TBODY>
                            </TABLE>
</logic:iterate>                         
<%--          ��ģ�����ý���                  --%>
                            
                             <BR>
<%--                       --%>
                         </TD>
                       </TR>
                      </TBODY>
                   </TABLE>
                 </TD>
                </TR>
               </TBODY>
             </TABLE>
</logic:iterate>
<%--      ������̳ģ�����ý���         --%>             
    </html:form>
  </body>
</html:html>
