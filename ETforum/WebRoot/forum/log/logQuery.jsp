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
    	//���
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.topicTitle,"ģ����")) return false;
            if (!checkNotNull(addstaffer.parentId,"��һ��ģ��")) return false;
              return true;
            }
    	function query(){    
    	      document.forms[0].action = "../forumLog.do?method=toLogList";
    	      document.forms[0].target = "bottomm";
    		  document.forms[0].submit();   
    	}
    	//�޸�
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../moduleManager.do?method=toModuleOper&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//ɾ��
    	function del(){
    		document.forms[0].action = "../moduleManager.do?method=toModuleOper&type=delete";
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
		//����ҳ��
		function toback(){
			opener.parent.topp.document.all;
		}
    </script>
  </head>
  
  <body bgcolor="#eeeeee">
    <html:form action="/forum/forumLog" method="post">
    <%-- jps include ͷ --%>
<%--  <jsp:include flush="true" page="../common/top.jsp"></jsp:include>--%>
<%-- �� --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../images/forum/di.jpg">
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
<table width="916" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
            <tr>
              <td colspan="4" background="images/di.jpg"><div align="left">&nbsp; ��̳��־����</div></td>
              </tr>
            <tr>
                  <td width="12%">&nbsp; �û�����				  </td>
				  <td width="38">
                    <label>
                    <html:text property="userId" styleClass="input"/>
                    </label></td>
                  <td width="12%">&nbsp; IP��
				  </td>
				  <td width="38%">   
                    <label>
                    <html:text property="ip" styleClass="input"/>
                    </label></td>  
            </tr>
            <tr>
                  <td >&nbsp; ��ʼʱ�䣺				  </td>
				  <td >
                    <label>
                    <html:text property="beginTime" styleClass="input"/>
                    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(beginTime)"/>
                    </label></td>
                  <td>&nbsp; ����ʱ�䣺
				  </td>
				  <td>   
                    <label>
                    <html:text property="endTime" styleClass="input"/>
                    <img src="<%=request.getContextPath()%>/html/time.png" width="20" height="20" onclick="openwin(endTime)"/>
                    </label></td>  
            </tr>
            
            <tr>
                  <td>&nbsp;&nbsp; ѡ������ģ�飺</td>
				  <td>
                    <label>
                    <html:select property="moduleName" styleClass="input">		
                       <html:option value="">������ȫ��ģ��</html:option>
                       <html:optionsCollection name="moduleNameList" label="label" value="value"/>
                       
<%--                       <html:option value="day">��һ���ڵ�����</html:option>--%>
<%--                       <html:option value="week">��һ�������ڵ�����</html:option>--%>
<%--                       <html:option value="month">��һ�����ڵ�����</html:option>--%>
<%--                       <html:option value="twoMonth">���������ڵ�����</html:option>--%>
<%--                       <html:option value="threeMonth">���������ڵ�����</html:option>--%>
                       
                    </html:select>
                    </label></td>
                    <td valign="top">&nbsp; ִ�в�����</td>
                 <td>   
                &nbsp;
                <label>
                    <html:select property="act">		
                       <html:option value="">ȫ��</html:option>
                       <html:option value="�鿴">�鿴</html:option>
                       <html:option value="�޸�">�޸�</html:option>
<%--                       <html:optionsCollection name="moduleNameList" label="label" value="value"/>--%>
                       
                    </html:select>
                </label></td>
            </tr>
            <tr>
              <td colspan="4" background="images/di.jpg" align="center"> 
                <input name="btnAdd" type="button" class="bottom" value="��ѯ" onClick="query()"/>&nbsp;&nbsp;
                <html:reset value="��д"/>
              </td>
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
<%--<jsp:include flush="true" page="../common/bottom.jsp"></jsp:include> --%>
    </html:form>
  </body>
</html:html>
