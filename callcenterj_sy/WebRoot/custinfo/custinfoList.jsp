<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gbk"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	UserBean ub = (UserBean) session
			.getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
	//��ϯ�������
	String opseating = "common";
	if (ub != null) {
		opseating = ub.getUserGroup();
	}
%>
<%@ page import="et.bo.sys.login.bean.UserBean"%>
<%@ page import="et.bo.sys.common.SysStaticParameter"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript">

    	function popUp( win_name,loc, w, h, menubar,center ) {
		var NS = (document.layers) ? 1 : 0;
		var editorWin;
		if( w == null ) { w = 500; }
		if( h == null ) { h = 350; }
		if( menubar == null || menubar == false ) {
			menubar = "";
		} else {
			menubar = "menubar,";
		}
		if( center == 0 || center == false ) {
			center = "";
		} else {
			center = true;
		}
		if( NS ) { w += 50; }
		if(center==true){
			var sw=screen.width;
			var sh=screen.height;
			if (w>sw){w=sw;}
			if(h>sh){h=sh;}
			var curleft=(sw -w)/2;
			var curtop=(sh-h-100)/2;
			if (curtop<0)
			{ 
			curtop=(sh-h)/2;
			}
	
			editorWin = window.open(loc,win_name, 'resizable=no,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable=no,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}
	
		function searchTel(tel) {
			parent.parent.document.getElementById('txtTelId').value = tel;
			parent.parent.document.getElementById('btnSearchTel').click();
		}
	


   	</script>


</head>

<body class="listBody">
	<html:form action="custinfo/custinfo.do" method="post">
<%--�ڲ��Ķ�form-bean���������cust_tel_home������������Ϣ����beginTime����������ʱ�䡣--%>
<%--�������û���������ʾ�б���Ŀ��˳��Ϊ�����硢��������ַ�������š�����ʱ�䡢����--%>
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="10%">
					����
				</td>
				<td class="listTitleStyle" width="15%">
<%--					�û�--%>
					����
				</td>
				<td class="listTitleStyle" >
					��ַ
				</td>
<%--				<td class="listTitleStyle" width="15%">--%>
<%--					���õ绰--%>
<%--				</td>--%>
<%--				<td class="listTitleStyle" width="15%">--%>
<%--					�ƶ��绰--%>
<%--				</td>--%>
<%--				<td class="listTitleStyle" width="10%">--%>
<%--					�û���ҵ--%>
<%--				</td>--%>
				<td class="listTitleStyle" width="10%">
					������
				</td>
				<td class="listTitleStyle" width="15%">
					����ʱ��
				</td>
				<td class="listTitleStyle" width="10%">
					����
				</td>
			</tr>
			<logic:iterate id="pagelist" name="list" indexId="i">
				<%
					String style = i.intValue() % 2 == 0 ? "oddStyle"
										: "evenStyle";
				%>
				<tr>
					<td >
						<a href="javascript:searchTel('<bean:write name='pagelist' property='cust_tel_home' filter='true'/>')">
						<bean:write name="pagelist" property="cust_tel_home" filter="true" />
						</a>
					</td>
					<td >
						<bean:write name="pagelist" property="cust_name" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_addr" filter="true" />
					</td>
<%--					<td >--%>
<%--						<bean:write name="pagelist" property="cust_tel_home" filter="true" />--%>
<%--					</td>--%>
<%--					<td >--%>
<%--						<bean:write name="pagelist" property="cust_tel_mob" filter="true" />--%>
<%--					</td>--%>
<%--					<td >--%>
<%--						<bean:write name="pagelist" property="cust_voc" filter="true" />--%>
<%--					</td>--%>
					<td >
						<bean:write name="pagelist" property="cust_rid" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="beginTime" filter="true" />
					</td>
					<td >
<%--						<leafRight:bodyImg alt="��ϸ" nickName="custinfo_detail"  src="../style/${styleLocation}/images/detail.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('1<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=detail&id=<bean:write name='pagelist' property='cust_id'/>',650,290)" />--%>
<%--						</leafRight:bodyImg>--%>
<%--						<leafRight:bodyImg alt="�޸�" nickName="custinfo_update"  src="../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('2<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=update&id=<bean:write name='pagelist' property='cust_id'/>',750,280)"--%>
<%--						</leafRight:bodyImg>--%>
						
						<img alt="��ϸ" src="../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('1<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=detail&id=<bean:write name='pagelist' property='cust_id'/>',650,320)" />
						<img alt="�޸�" src="../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('2<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=update&id=<bean:write name='pagelist' property='cust_id'/>',750,320)" />

						<%
							//UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
										String userGroup = ub.getUserGroup();
										//System.out.println("operator"+userGroup);
										String str = "none";
										if (!userGroup.equals("operator")) {
											str = "inline";
						%>
<%--						<leafRight:bodyImg alt="ɾ��" nickName="custinfo_delete"  src="../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('3<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=delete&id=<bean:write name='pagelist' property='cust_id'/>',650,280)"--%>
<%--						</leafRight:bodyImg>--%>
						<img alt="ɾ��" src="../style/<%=styleLocation%>/images/del.gif"
							onclick="popUp('3<bean:write name='pagelist' property='cust_id'/>','custinfo.do?method=toCustinfoLoad&type=delete&id=<bean:write name='pagelist' property='cust_id'/>',650,320)"
							style="" />

						<%
							}
						%>

					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="5" class="pageTable">
					<page:page name="userpageTurning" style="second" />
				</td>
				<td align="center" class="pageTable">
					<input name="btnAdd" type="button" value="���" class="buttonStyle"
						onclick="popUp('windows','custinfo.do?method=toCustinfoLoad&type=insert',750,320)" />
<%--					<leafRight:btn  name="btnAdd" nickName="custinfo_add" styleClass="buttonStyle" value="���" onclick="popUp('windows','custinfo.do?method=toCustinfoLoad&type=insert',750,280)" scopeName="userRoleLeafRightInsession" />--%>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>