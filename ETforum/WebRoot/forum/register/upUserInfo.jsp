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
    <script language="javascript">
    	//��ת��ע����Ϣҳ
    	function jump(){
    		document.forms[0].action = "../userOper/register.do?method=toRegister";
    		document.forms[0].submit();
    	}
    	//�޸��û���Ϣ
    	function updateUserInfo(){
    		document.forms[0].action = "../userOper/register.do?method=updateForumUser";
    		document.forms[0].submit();
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/userOper/register" method="post">
        <%-- jps include ͷ --%>
  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>
<%-- �� --%>

<table width="1000" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
  <tr background="../../images/forum/nabiaoti_03.jpg">
    <td>
    
    <table width="966" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#FFFFFF">
<%--   �ӵ�����    --%>



<%----%>
<%--�Ѵ���ӵ���--%>
<%----%>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2" align="center">�༩��������</td>
        </tr>
        <tr>
          <td width="41%">�µ�����?(�����ϣ���޸����룬������) </td>
          <td width="59%">
          	<html:text property="repassword"/>
          </td>
        </tr>
        <tr>
          <td>������ʾ����</td>
          <td>
          	<html:text property="question"/>
          </td>
        </tr>
        <tr>
          <td>������ʾ��</td>
          <td>
          	<html:text property="answer"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>����QQ����</td>
          <td>
          	<html:text property="qq"/>
          </td>
        </tr>
        <tr>
          <td> ����MSN��ַ</td>
          <td>
          	<html:text property="msn"/>
          </td>
        </tr>
        <tr>
          <td>?����ICQ����</td>
          <td>
          	<html:text property="icq"/>
          </td>
        </tr>
        <tr>
          <td>?������и�����ҳ</td>
          <td>
          	<html:text property="homepage"/>
          </td>
        </tr>
        <tr>
          <td>���Ի�ǩ��</td>
          <td>
          	<html:textarea property="underwrite"/>
          </td>
        </tr>
        <tr>
          <td>���Ҽ��<BR>
            ?
          ��һ��������</td>
          <td>
          	<html:textarea property="introself"/>
          </td>
        </tr>
        <tr>
          <td>�Ա�</td>
          <td>
          	<html:text property="sex"/>
          </td>
        </tr>
        <tr>
          <td>����</td>
          <td>
          	<html:text property="birthday"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td colspan="2" align="center">
<%--          <input type="button" name="returnIndex" value="������ҳ">--%>
          <input type="button" name="returnFirst" value="����ǰҳ" onclick="javascript:history.back()">
          <input type="button" name="confimSubmit" value="ȷ���޸�" onclick="updateUserInfo()">
          <input type="reset" name="clear" value="�����д"></td>
        </tr>
      </table>



<%--  �������  --%>
        </td>
      </tr>
      
    </table>
    </td>
  </tr>
</table>


<%--��--%>
<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> 
     
	  </html:form>
  </body>
</html:html>
