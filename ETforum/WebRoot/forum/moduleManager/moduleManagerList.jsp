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
<%--       头     --%>
          <TABLE width="100%" cellSpacing=1 cellPadding=6 align=center>
            <TBODY>
              <TR>
                <TD style="FONT-WEIGHT: bold; COLOR: #ffffff" bgColor=#6da6d1 colSpan=10>
                  <DIV>
                    <DIV style="DISPLAY: inline; FLOAT: left; COLOR: #ffffff"><STRONG><A 
                      name=section1></A>管理论坛</STRONG></DIV>
                    <DIV style="DISPLAY: inline; FLOAT: right"><A style="COLOR: #ffffff" 
                      href="http://192.168.1.9:8088/bmb/admin.php?bmod=setforum.php#top">[ 
                          返回到菜单 ]</A></DIV></DIV>
                </TD>
              </TR>
            </TBODY>
          </TABLE>
<%--      头结束    --%>
       <br>
      
<%--  总体设置    --%>
            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
              <TBODY>
                <TR>
                  <TD>
                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                      <TBODY>
                        <TR>
                          <TD bgColor=#ffffff><FONT face=verdana><STRONG><bean:write name="forumName" filter="true"/></STRONG><br>   
                            <A href="../moduleManager.do?method=toParentModuleLoad&type=insert&createType=parent">新建父论坛</A>
                          | <A href="../moduleManager.do?method=toModuleLoad&type=insert&createType=son">新建子论坛</A> <BR></FONT>
                          </TD>
                        </TR>
                       </TBODY>
                      </TABLE>
                   </TD>
                 </TR>
               </TBODY>
            </TABLE>
<%--   总体设置结束    --%>
            
<%--      具体论坛模块设置         --%>
<logic:iterate id="c" name="moduleMap" indexId="i">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
              <TBODY>
                <TR>
                  <TD>
                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                      <TBODY>
                        <TR>
                          <TD bgColor=#ffffff><FONT face=verdana>&nbsp;&nbsp;&nbsp;&nbsp;<STRONG>论坛名:</STRONG> 
                            <%--        论坛名<bean:write>                  --%>
                            <bean:write name="c" property="key"/> 
<%--                          <A href="../moduleManager.do?method=toParentModuleLoad&type=update&id=<bean:write name='c' property='key'/>" >编辑父论坛</A>--%>
<%--                        | <A href="../moduleManager.do?method=toParentModuleLoad&type=delete&id=<bean:write name='c' property='key'/>" >删除父论坛</A> --%>
<%--                              <A href="../moduleManager.do?method=toModuleLoad&type=insert">新建子论坛</A> --%>
               
                        
<%--          子模块设置开始                  --%>
<logic:iterate id="cValue" name="c" property="value" >
                            <TABLE cellSpacing=0 cellPadding=0 width="92%" align=center bgColor=#448abd>
                              <TBODY>
                                <TR>
                                  <TD>
                                    <TABLE cellSpacing=1 cellPadding=3 width="100%">
                                      <TBODY>
                                        <TR>
                                          <TD bgColor=#ffffff><FONT face=verdana>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<STRONG>子论坛名:</STRONG> 
                                         <%--    子论坛名<bean:write>    --%>
                                          <bean:write name='cValue' property='topicTitle' filter='true'/><BR>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <A href="../moduleManager.do?method=toModuleLoad&type=update&id=<bean:write name='cValue' property='id' filter='true'/>" >编辑该子论坛</A> 
                                          | <A href="../moduleManager.do?method=toModuleLoad&type=delete&id=<bean:write name='cValue' property='id' filter='true'/>" >删除此子论坛</A> 
                                          </TD>
                                        </TR>
                                      </TBODY>
                                    </TABLE>
                                  </TD>
                                </TR>
                              </TBODY>
                            </TABLE>
</logic:iterate>                         
<%--          子模块设置结束                  --%>
                            
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
<%--      具体论坛模块设置结束         --%>             
    </html:form>
  </body>
</html:html>
