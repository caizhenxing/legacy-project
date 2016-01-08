<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=gb2312"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<%@ include file="../../style.jsp"%>

<html:html lang="true">
<head>
	<html:base />

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet"
		type="text/css" />

	<script language="javascript">
    	
    	function popUp( win_name,loc, w, h, menubar,center ) {
    	
    	//2008-03-20 增加 loc 减少更改
    	loc = "phoneinfo.do?method=phoneLoad&type=" + loc + "&";
    	
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
	
			editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
		}
		else{
			editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
		}
	
		editorWin.focus(); //causing intermittent errors
	}


   	</script>

</head>

<body class="listBody">
	<html:form action="/custinfo/phoneinfo.do" method="post">
		<table width="100%" border="0" align="center" cellpadding="1"
			cellspacing="1" class="listTable">
			<tr>
				<td class="listTitleStyle" width="60">
					用户姓名
				</td>
				<td class="listTitleStyle" width="22%">
					用户电话
				</td>
				<td class="listTitleStyle" width="30%">
					用户地址
				</td>
				<td class="listTitleStyle" width="20%">
					用户类型
				</td>
				<td class="listTitleStyle" width="10%">
					受理工号
				</td>
				<td class="listTitleStyle" width="80">
					操作
				</td>
			</tr>
			<logic:iterate id="pagelist" name="list" indexId="i">
				<%
							String style = i.intValue() % 2 == 0 ? "oddStyle"
							: "evenStyle";
				%>
				<tr>
					<td >
						<bean:write name="pagelist" property="name" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="phone" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_addr" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_type" filter="true" />
					</td>
					<td >
						<bean:write name="pagelist" property="cust_rid" filter="true" />
					</td>
					<td >
<%--						<leafRight:bodyImg alt="详细" nickName="tel_phoneinfo_detail"  src="../../style/${styleLocation}/images/detail.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('1<bean:write name='pagelist' property='dict_cust_type'/>','detail&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)"--%>
<%--						</leafRight:bodyImg>--%>
<%--						<leafRight:bodyImg alt="修改" nickName="tel_phoneinfo_update"  src="../../style/${styleLocation}/images/update.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('2<bean:write name='pagelist' property='dict_cust_type'/>','update&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)"--%>
<%--						</leafRight:bodyImg>--%>
<%--						<leafRight:bodyImg alt="删除" nickName="tel_phoneinfo_delete"  src="../../style/${styleLocation}/images/del.gif"  border="0"  styleId="addBtn" name="addBtn" width="16px;" style="height:16px;" scopeName="userRoleLeafRightInsession">--%>
<%--						onclick="popUp('3<bean:write name='pagelist' property='dict_cust_type'/>','delete&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)"--%>
<%--						</leafRight:bodyImg>--%>
						<img alt="详细" src="../../style/<%=styleLocation%>/images/detail.gif"
							onclick="popUp('1<bean:write name='pagelist' property='dict_cust_type'/>','detail&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)" />
						<img alt="修改" src="../../style/<%=styleLocation%>/images/update.gif"
							onclick="popUp('2<bean:write name='pagelist' property='dict_cust_type'/>','update&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)" />
						<img alt="删除" src="../../style/<%=styleLocation%>/images/del.gif"
							onclick="popUp('3<bean:write name='pagelist' property='dict_cust_type'/>','delete&id=<bean:write name='pagelist' property='id'/>&custType=<bean:write name='pagelist' property='dict_cust_type'/>',760,545)" />
						
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td colspan="5" class="pageStyle">
					<page:page name="phonepageTurning" style="second" />
				</td>
				<td  class="pageTable">
					<input name="btnCountAdd" type="button" class="buttonStyle" value="批量" onclick="popUp('windowscount','countinsert',760,500)" />
					<input name="btnCountAdd" type="button" class="buttonStyle" value="添加" onclick="popUp('windows','insert',860,470)" />
<%--					<leafRight:btn  nickName="tel_phoneinfo_batch" styleClass="buttonStyle" value="批量" onclick="popUp('windowscount','countinsert',760,500)" scopeName="userRoleLeafRightInsession" />--%>
<%--					<leafRight:btn  nickName="tel_phoneinfo_add" styleClass="buttonStyle" value="添加" onclick="popUp('windows','insert',860,470)" scopeName="userRoleLeafRightInsession" />--%>
					
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
