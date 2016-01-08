<%@ page language="java" import="java.util.List,excellence.framework.base.dto.impl.DynaBeanDTO" pageEncoding="gbk" contentType="text/html; charset=gbk"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
  <head>
    <html:base />
    
    <title>ȫ���û��б�</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
	

	
  </head>
  
  <body class="listBody">
    <html:form action="custinfo/custinfo.do"  method="post">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="listTable">
		  <tr>
		    <td class="listTitleStyle" width="15%">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
		    <td class="listTitleStyle" width="15%">լ&nbsp;&nbsp;&nbsp;&nbsp;��</td>
		    <td class="listTitleStyle" width="15%">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
		    <td class="listTitleStyle" width="15%">�칫�绰</td>
		    <td class="listTitleStyle" width="10%">�ͻ���ҵ</td>
		    <td class="listTitleStyle" width="15%">�ͻ�����</td>
		    <td class="listTitleStyle" width="10%">ѡ&nbsp;&nbsp;&nbsp;&nbsp;��</td>
		  </tr>
		  
		  <%
		  List list = (List)request.getAttribute("list");
		  for(int i = 0; i < list.size(); i++){
			DynaBeanDTO dto = (DynaBeanDTO)list.get(i);

			String cust_name = (String)dto.get("cust_name");
			String cust_tel_home = (String)dto.get("cust_tel_home");
			String cust_tel_mob = (String)dto.get("cust_tel_mob");
			String cust_tel_work = (String)dto.get("cust_tel_work");
			String cust_voc = (String)dto.get("cust_voc");
			String cust_type = (String)dto.get("cust_type");
			
			String v = "";
			if(cust_tel_mob!=null && !cust_tel_mob.equals("")){
				v = cust_tel_mob;
			}else if(cust_tel_home!=null && !cust_tel_home.equals("")){
				v = cust_tel_home;
			}else if(cust_tel_work!=null && !cust_tel_work.equals("")){
				v = cust_tel_work;
			}else{
				v = "null";
			}
			
			String style = i % 2 == 0 ? "oddStyle": "evenStyle";
		  %>
		  <tr>
		  	<td ><%= cust_name %></td>
		    <td ><%= cust_tel_home %></td>
		    <td ><%= cust_tel_mob %></td>
		    <td ><%= cust_tel_work %></td>
		    <td ><%= cust_voc %></td>
            <td ><%= cust_type %></td>
            <td >
		    <input name="ids" type="checkbox" id="ids" value="<%= v %>">
            </td>
		  </tr>
		  <%
		  }
		  %>
		  <tr class="buttonAreaStyle">
		    <td colspan="7">
		    <input name="all" type="button"   value="ȫѡ" onclick="allList()"/>
		    <input name="clear" type="button"   value="ȡ��ȫѡ" onclick="clearList()"/>
		    <input name="Submit2" type="button"   value="ѡ�񲢷���" onclick="selectList()"/>
		    </td>
		  </tr>
		</table>
    </html:form>
  </body>
<script>
	function allList(){
		var checkboxs = document.forms[0].ids;
		if (checkboxs != null){		
			for (i = 0; i < checkboxs.length; i++){
				checkboxs[i].checked = true;
			}
		}
	}
	function clearList(){
		var checkboxs = document.forms[0].ids;
		if (checkboxs != null){		
			for (i = 0; i < checkboxs.length; i++){
				checkboxs[i].checked = false;
			}
		}
	}
	function selectList(){
		var checkboxs = document.forms[0].ids;
		var temp = "";
		if (checkboxs != null){		
			for (i = 0; i < checkboxs.length; i++){
				if(checkboxs[i].checked == true){
					temp += checkboxs[i].value+",";
				}
			}
		}
		var r = /null,/g				//����������ʽ
		temp = temp.replace(r,"");		//�滻����nullΪ�մ�
		temp = temp.substring(0,temp.length-1);//ȥ�����һ������
		opener.parent.document.forms[0].ttt.value = temp;
		//���ø�ҳ����
		
		opener.jsTelBook2Applet(temp);
		window.close();
	}
</script>
</html:html>
