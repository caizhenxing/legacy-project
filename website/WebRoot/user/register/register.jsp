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
    <script language="javascript" src="../../js/form.js"></script>
    <script type="text/javascript">
        //���
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.name,"�û���")) return false;
    		if (!checkNotNull(addstaffer.password,"����")) return false;
    		if (!checkNotNull(addstaffer.repassword,"�ظ�����")) return false;
    		if (!checkNotNull(addstaffer.question,"��������")) return false;
    		if (!checkNotNull(addstaffer.answer,"�����")) return false;
    		if (!checkNotNull(addstaffer.groomuser,"�Ƽ���")) return false;
    		if (!checkNotNull(addstaffer.email,"�����ʼ�")) return false;
    		if (!checkNotNull(addstaffer.val,"��֤��")) return false;
    		if (addstaffer.password.value !=addstaffer.repassword.value)
            {
            	alert("��������������벻һ�£�");
            	addstaffer.password.focus();
            	return false;
            }
    		return true;
    	}
    	//ע��
    	function register(){
    		var f =document.forms[0];
    		if(checkForm(f)){
    		document.forms[0].action = "../userOper/register.do?method=register";
    		document.forms[0].submit();
    		}
    	}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/user/userOper/register" method="post">
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



		<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td><table width="916" border="1" cellspacing="5" cellpadding="0">
                <tr>
                  <td height="26" background="images/biaoti1_03.jpg"><span class="s_headline1">&nbsp;&nbsp;&nbsp; </span>ע�����<span class="s_headline1">&nbsp;&nbsp;</span><span class="STYLE2 STYLE3 STYLE4">&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp; &nbsp;ע����������Ϊ���������³�Ա</td>
                  </tr>
                <tr>
                  <td><table width="916" border="1" cellspacing="0" cellpadding="5">
                    <tr>
                      <td height="26" background="images/topbg.gif" class="s_headline1">������������ϸ����-��������������Ϣ��</td>
                      </tr>
                    <tr>
                      <td><table width="916" border="1" cellpadding="0" cellspacing="5" bordercolor="#CCCCCC">
                        <tr>
                          <td width="304"><div align="left" class="STYLE1">����д�����õ����֣�* </div></td>
                            <td width="597"><label>
                              <html:text property="name"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">��ѡ������룺*</div></td>
                            <td><label>
                              <html:password property="password"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">���ظ��������룺*</div></td>
                            <td><label>
                              <html:password property="repassword"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">������ʾ���⣺</div></td>
                            <td><label>
                              <html:text property="question"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">������ʾ�𰸣�</div></td>
                            <td><label>
                              <html:text property="answer"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">�Ƽ��ˣ� </div></td>
                            <td><label>
                              <html:text property="groomuser"/>
                              </label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">E-MAIL:*</div></td>
                            <td><label>
                              <html:text property="email"/>
                              <br />
                              <input name="checkbox" type="checkbox" value="checkbox" checked="checked" />
                              <span class="STYLE5">��������</span></label></td>
                          </tr>
                        <tr>
                          <td><div align="left" class="STYLE1">��������֤�룺</div></td>
                            <td><label>
                                        <html:text property="val"/>
          								<img border='0' src='../../RandomPicClient.jsp'/>
                              </label></td>
                          </tr>
                        <tr>
                          <td background="images/topbg.gif">
                          <div align="right">
                          <input type="button" name="btnSend" value="�ͳ�����" onclick="register()">
                          </div></td>
                            <td background="images/topbg.gif" class="bottom">
                            <input type="reset" name="btnReset" value="������д">
                            </td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
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
    
  </html:form>
  </body>
</html:html>