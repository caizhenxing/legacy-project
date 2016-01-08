<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title></title>
    <link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

<script language="javascript" src="../../js/common.js"></script>
<SCRIPT language="javascript" src="../../js/form.js" type=text/javascript>
</SCRIPT>
    <script language="javascript">
    	//���
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.pbxType,"����������")) return false;
            if (!checkNotNull(addstaffer.pbxPort,"�������˿�")) return false;
            if (!checkNotNull(addstaffer.ivrPort,"IVR�˿�")) return false;
            
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../pbx2ivr.do?method=toSave&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//�޸�
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../pbx2ivr.do?method=toSave&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//ɾ��
    	function del(){
    		document.forms[0].action = "../pbx2ivr.do?method=toSave&type=delete";
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
		
		//����ҳ��
		function toback(){
			//opener.parent.bottomm.location.reload();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody">
    <logic:equal name="operSign" value="et.pcc.portCompare.samePortOrIp">
	<script>
		alert("��ϯ�ֻ��Ż�IP�Ѿ����ڣ�"); window.close();
	</script>
	</logic:equal>
	
	<logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("�����ɹ���"); toback(); window.close();
	</script>
	</logic:equal>
  
    <html:form action="/callcenter/pbx2ivr" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
		    <logic:equal name="opertype" value="insert">
		     ���
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     �޸�
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     ɾ��
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
         
		  <tr>  
		    <td class="labelStyle">����������</td>
		    <td><html:text property="pbxType" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">�������˿�</td>
		    <td><html:text property="pbxPort" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">IVR�˿�</td>
		    <td><html:text property="ivrPort" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">��ע</td>
		    <td><html:text property="remark" styleClass="writeTextStyle"/></td>
		  </tr>		  
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="buttonAreaStyle">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button"   value="���" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button"   value="ȷ��" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button"   value="ɾ��" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button"   value="�ر�" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
