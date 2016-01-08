<%@ page language="java"  contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title><bean:message key="et.oa.hr.hrManagerLoad.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="../../images/css/styleA.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript src="../../js/form.js" type=text/javascript>
    </SCRIPT>
    
    <script type="text/javascript">
    	function checkForm(addstaffer){
    		if (!checkNotNull(addstaffer.tagAllInfo,"问题总类")) return false;
<%--    		if (!checkNotNull(addstaffer.tagBigInfo,"问题大类")) return false;--%>
<%--    		if (!checkNotNull(addstaffer.tagSmallInfo,"问题小类")) return false;--%>
        	if (!checkNotNull(addstaffer.question,"问题")) return false;
<%--        	if (!checkNotNull(addstaffer.content,"问题内容")) return false;--%>
           return true;
        }
    	<%
    		String pid = (String)request.getSession().getAttribute("pid");
    		//String pid = (String)request.getParameter("id");
    	%>
    	//添加
    	function add(){
    		var f =document.forms[0];
    	    if(checkForm(f)){
    			document.forms[0].action = "../cclog.do?method=operQuestion&type=insert&pid=<%=pid%>";
    			document.forms[0].submit();
    		}
    	}
    	//修改
       function update(){
       		var f =document.forms[0];
    	    if(checkForm(f)){
    			document.forms[0].action = "../cclog.do?method=operQuestion&type=update&pid=<%=pid%>";
    			document.forms[0].submit();
    		}
    	}
    	
    	//返回页面
		function toback(){
			opener.parent.document.location.reload();
		}
		
		function changetwo(){

			var info_group=document.forms[0].tagAllInfo.value;
			
			document.forms[0].action="../cclog.do?method=toGroupSort"
<%--		    document.forms[0].target="topp";--%>
			document.forms[0].submit();
		}
		function changethree(){

			var info_sort=document.forms[0].tagBigInfo.value;
			
			document.forms[0].action="../cclog.do?method=toGroupDivision"
<%--		    document.forms[0].target="topp";--%>
			document.forms[0].submit();
		}
		
    </script>
  </head>
  
      <logic:notEmpty name="idus_state">
	<script>window.close();alert("操作成功！");window.close();</script>
	</logic:notEmpty>
  
  <body onunload="toback()" bgcolor="#eeeeee">
  
    <html:form action="/callcenter/cclog" method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablebgcolor">
		  <tr>
		    <td colspan="2" align="center" class="tdbgpicload">
		    <bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.title"/>
		    </td>
		  </tr>

		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.questiontype"/></td>
		    <td class="tdbgcolorloadleft">
		    		<html:select property="tagAllInfo">
						<html:option value="">请选择</html:option>
						<html:options collection="grouplist" property="value" labelProperty="label"/>
					</html:select>
<%--					行业大类<html:select property="tagBigInfo" onchange="changethree()">--%>
<%--						<html:option value="">请选择</html:option>--%>
<%--						<html:options collection="sortlist" property="value" labelProperty="label"/>--%>
<%--					</html:select>--%>
<%--					行业小类<html:select property="tagSmallInfo" >--%>
<%--						<html:option value="">请选择</html:option>--%>
<%--						<html:options collection="divisionlist" property="value" labelProperty="label"/>--%>
<%--					</html:select>--%>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright"><bean:message bundle="pcc" key="et.pcc.assay.question.seequestion.question"/></td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="question" cols="40" rows="8"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">答&nbsp;&nbsp;&nbsp;&nbsp;案</td>
		    <td class="tdbgcolorloadleft">
		    <html:textarea property="questionKey" cols="40" rows="8"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="tdbgcolorqueryright">是否回答成功</td>
		    <td class="tdbgcolorloadleft">
		    
		    <html:select property="ifAnswerSucceed">
					<html:option value="1">回答成功</html:option>
					<html:option value="0">未回答成功</html:option>
		    </html:select>
		    </td>
		  </tr>	
		  <tr>
		    <td class="tdbgcolorqueryright">是否需要回访</td>
		    <td class="tdbgcolorloadleft">
		    
		    <html:select property="ifNeedRevert">
					<html:option value="yes">需要回访</html:option>
					<html:option value="no">不需要回访</html:option>
		    </html:select>
		    </td>
		  </tr> 
		  <tr>
		    <td colspan="2" class="tdbgcolorloadbuttom">
		    <logic:equal name="opertype" value="insert">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.add'/>" onclick="add()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <logic:equal name="opertype" value="update">
		     <input name="btnAdd" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.update'/>" onclick="update()"/>&nbsp;&nbsp;
		    </logic:equal>
		    <input name="btnReset" type="button" class="bottom" value="<bean:message bundle='pcc' key='sys.close'/>" onclick="javascript:window.close();"/>
		    </td>
		  </tr>
		</table>
		<html:hidden property="id"/>
    </html:form>
  </body>
</html:html>
