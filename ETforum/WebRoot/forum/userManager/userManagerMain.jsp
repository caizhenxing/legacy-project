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
    
    <title>�û�����</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <script language="javascript">
        //ѡ������
    	function selectAll() {
			for (var i=0;i<document.forms[0].chkId.length;i++) {
				var e=document.forms[0].chkId[i];
				e.checked=!e.checked;
			}
		}
		//ɾ��ѡ��������
		function delSelectIt(){
			if(confirm("ȷ��Ҫ����ɾ��ѡ�е��û���һ��ɾ�������ָܻ���")){
				document.forms[0].action = "../userManager.do?method=toDelUser&oper=delselect";
				document.forms[0].submit();
				return true;
			}else{
				return false;
			}
		}
		//�����û�
		function query(){    
    	      document.forms[0].action = "../userManager.do?method=toUserMain";
    		  document.forms[0].submit();   
    	}
    	//�����û�
		function pageclear(){
    	      document.forms[0].id="";
    	      document.forms[0].email="";
    	}
		//ִ�в���
		function operit(){
			document.forms[0].action = "../opernews.do?method=operArticleInfo";
			document.forms[0].submit();
		}
		//�ƶ�
		function moveall(){
			document.forms[0].action = "../opernews.do?method=moveAll";
			document.forms[0].submit();
		}
		//�̶�
		function puttop(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=puttop&state=put&id="+articleid;
			document.forms[0].submit();
		}
		//���
		function unputtop(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=puttop&state=unput&id="+articleid;
			document.forms[0].submit();
		}
		//��Ϊ�Ƽ�
		function putgroom(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=putpink&state=put&id="+articleid;
			document.forms[0].submit();
		}
		//ȡ���Ƽ�
		function unputgroom(articleid){
			document.forms[0].action = "../opernews.do?method=toArticleLoad&type=putpink&state=unput&id="+articleid;
			document.forms[0].submit();
		}
		function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   var url="<%=request.getContextPath()%>/html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee" onunload="pageclear()">
    <html:form action="/forum/userManager" method="post">
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
<table width="916" border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="#CCCCCC">
          <tr>
            <td colspan="2" background="../../images/forum/di.jpg">&nbsp; �û��б�</td>
            </tr>
          <tr>
            <td width="330" colspan="2">&nbsp;�����û�
          </td>
          <tr><td>
                 &nbsp; �û�����</td>
            <td width="580"><label>
              <div align="left">
                <html:text property="id" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; Eamil��</td>
            <td><label>
              <div align="left">
                <html:text property="email" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ע���������ڣ�yyyy-mm-dd)��</td>
            <td><label>
              <div align="left">
                <html:text property="beginTime" styleClass="input" readonly="true"/>
                <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(beginTime)"/>
                </div>
                
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ע���������ڣ�yyyy-mm-dd)��</td>
            <td><label>
              <div align="left">
                <html:text property="endTime" styleClass="input" readonly="true"/>
                <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endTime)"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ����С�ڣ� </td>
            <td><label>
              <div align="left">
                <html:text property="beginPoint" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ���ִ��ڣ� </td>
            <td><label>
              <div align="left">
                <html:text property="endPoint" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ������С��: </td>
            <td><label>
              <div align="left">
                <html:text property="beginSendPost" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ���������ڣ� </td>
            <td><label>
              <div align="left">
                <html:text property="endSendPost" styleClass="input"/>
                </div>
            </label></td>
          </tr>
          <tr>
            <td>&nbsp; ������û�е�¼��̳�� </td>
            <td><label>
              <div align="left">
                <html:text property="noLoginDate" styleClass="input"/>
                </div>
            </label></td>
          </tr>
<%--          <tr>--%>
<%--            <td>&nbsp; ���һ�ε�¼IP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="newlyIp" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; ע��ʱIP:</td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <html:text property="ip" styleClass="input"/>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>&nbsp; �û��飺 </td>--%>
<%--            <td><label>--%>
<%--              <div align="left">--%>
<%--                <select name="select">--%>
<%--                  <option>̳��</option>--%>
<%--                  <option>����̳��</option>--%>
<%--                  <option>������</option>--%>
<%--                  <option>����</option>--%>
<%--                  <option>��ͨ�û�</option>--%>
<%--                  <option>��֤�û�</option>--%>
<%--                  <option>��ֹ����</option>--%>
<%--                  <option>�ο�</option>--%>
<%--                </select>--%>
<%--                </div>--%>
<%--            </label></td>--%>
<%--          </tr>--%>
          <tr>
            <td height="30" colspan="2"><label>
              <div align="left">
                <input type="submit" name="Submit" value="�����û�" onclick="query()"/>
                
<%--                &nbsp; --%>
                <html:reset value="���"/>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit3" value="��̳����" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit4" value="��̳�ʼ�" />--%>
<%--                &nbsp; --%>
<%--                <input type="submit" name="Submit5" value="�û�����" />--%>
              </div>
              </label></td>
            </tr>
        </table>
        <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
        </table>
<%----%>
<table width="916" border="1" cellpadding="0" align="center" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td><table width="916" border="1" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
                <tr>
                  <td background="../../images/forum/di.jpg"><div align="center">ɾ��</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">�û���</div></td>
<%--                  <td background="../../images/forum/di.jpg"><div align="center">UID</div></td>--%>
                  <td background="../../images/forum/di.jpg"><div align="center">�û���</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">ע������</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">������</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">����ֵ</div></td>
                  <td background="../../images/forum/di.jpg"><div align="center">����ѡ��</div></td>
                </tr>
                <logic:iterate id="c" name="userlist" indexId="i"> 
                <tr>
                  <td><label>
                    <div align="center">
<%--                      <input type="checkbox" name="checkbox" value="checkbox" />--%>
                      <html:multibox property="chkId"><bean:write name="c" property="id" filter="true"/></html:multibox>
                      </div>
                  </label></td>
                  <td><div align="center"><bean:write name="c" property="id" filter="true"/></div></td>
<%--                  <td><div align="center">1</div></td>--%>
                  <td><div align="center"><bean:write name="c" property="name" filter="true"/></div></td>
                  <td><div align="center"><bean:write name="c" property="registerDate" filter="true" format="yyyy-MM-dd HH:mm:ss"/></div></td>
                  <td><div align="center"><bean:write name="c" property="sendPostNum" filter="true"/></div></td>
                  <td><div align="center"><bean:write name="c" property="answerPostNum" filter="true"/></div></td>
                  <td><div align="center">
                          <a href="../userManager.do?method=toLoadUser&userId=<bean:write name='c' property='id'/>">�鿴</a>
                      </div></td>
                </tr>
                </logic:iterate>
                <tr>
                  <td colspan="8">&nbsp; <label>
<%--                    <input type="submit" name="Submit6" value="ȫѡ" />--%>
                    <a href="javascript:selectAll()"><bean:message key="agrofront.news.article.articlelist.selectornot"/></a>
		            <html:checkbox property="chkall" onclick="javascript:selectAll()"/>
                    <input type="button" name="btnRec" value="ɾ����ѡ" onclick="delSelectIt()"/>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit7" value="��ѡ" />--%>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit8" value="����" />--%>
<%--                 &nbsp; --%>
<%--                 <input type="submit" name="Submit9" value="ɾ���û�" />--%>
                  </label>
                  </td>
                  </tr>
<%--                <tr>--%>
<%--                  <td height="35" colspan="8" background="images/topbg.gif">&nbsp;��[1]����ǰ��1ҳ&nbsp; ��1ҳ&nbsp; ����--%>
<%--                    <label>--%>
<%--                    <input name="textfield12" type="text" size="4" />--%>
<%--                    ҳ --%>
<%--                    <input type="submit" name="Submit10" value="ȷ�� " />--%>
<%--                    [20/ҳ]                                    &nbsp;&nbsp;                                  &nbsp;&nbsp;                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ��ʾ--%>
<%--                    <select name="select2">--%>
<%--                      <option>���л�Ա</option>--%>
<%--                    </select>--%>
<%--                    <input type="submit" name="Submit11" value="ȷ��" />--%>
<%--                    </label></td>--%>
<%--                  </tr>--%>

              </table></td>
            </tr>
          </table>

		    <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="638" background="../../images/forum/di.jpg">
              </td>
                <td width="278" background="../../images/forum/di.jpg">
                 <table width="330" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="../../images/forum/di.jpg" colspan="6">
                      <div align="center">               	  
						  <page:page name="userListpageTurning" style="first"/>				   
					  </div>
                    </td>
                  </tr>
                </table>
                </td>
              </tr>
          </table>
        <table width="916" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
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
