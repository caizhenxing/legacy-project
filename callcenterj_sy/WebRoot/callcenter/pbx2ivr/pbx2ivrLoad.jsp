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
    	//添加
    	function checkForm(addstaffer){
            if (!checkNotNull(addstaffer.pbxType,"交换机类型")) return false;
            if (!checkNotNull(addstaffer.pbxPort,"交换机端口")) return false;
            if (!checkNotNull(addstaffer.ivrPort,"IVR端口")) return false;
            
              return true;
            }
    	function add(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    	      document.forms[0].action = "../pbx2ivr.do?method=toSave&type=insert";
    		  document.forms[0].submit();
    	    }
    	}
    	//修改
    	function update(){
    	    var f =document.forms[0];
    	    if(checkForm(f)){
    		  document.forms[0].action = "../pbx2ivr.do?method=toSave&type=update";
    		  document.forms[0].submit();
    	    }
    	}
    	//删除
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
		
		//返回页面
		function toback(){
			//opener.parent.bottomm.location.reload();
			opener.parent.bottomm.document.location=opener.parent.bottomm.document.location;
		}
    </script>
  </head>
  
  <body onunload="toback()" class="loadBody">
    <logic:equal name="operSign" value="et.pcc.portCompare.samePortOrIp">
	<script>
		alert("座席分机号或IP已经存在！"); window.close();
	</script>
	</logic:equal>
	
	<logic:equal name="operSign" value="sys.common.operSuccess">
	<script>
		alert("操作成功！"); toback(); window.close();
	</script>
	</logic:equal>
  
    <html:form action="/callcenter/pbx2ivr" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="navigateTable">
		  <tr>
		    <html:hidden property="id"/>
		    <td class="navigateStyle">
		    <logic:equal name="opertype" value="insert">
		     添加
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     修改
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     删除
		    </logic:equal>
		    </td>
		  </tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="contentTable">
         
		  <tr>  
		    <td class="labelStyle">交换机类型</td>
		    <td><html:text property="pbxType" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">交换机端口</td>
		    <td><html:text property="pbxPort" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">IVR端口</td>
		    <td><html:text property="ivrPort" styleClass="writeTextStyle"/></td>
		  </tr>
		  <tr>  
		    <td class="labelStyle">备注</td>
		    <td><html:text property="remark" styleClass="writeTextStyle"/></td>
		  </tr>		  
		  <tr>	
		    <td colspan="4" align="center" width="100%" class="buttonAreaStyle">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button"   value="添加" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button"   value="确定" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="delete">
		     <input name="btnAdd" type="button"   value="删除" onclick="del()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="addgov" type="button"   value="关闭" onClick="javascript: window.close();"/>
		    </td>
		  </tr>
	</table>
    </html:form>
  </body>
</html:html>
