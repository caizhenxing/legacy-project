<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<body class="list_body">
<div class="main_title">
	<div>�����</div>
</div>
<div class="main_body">
	<div id="divId_panel"></div>
</div>
<entryPanel:show
	panelList=" �б� #/sys/group/list.do?entityClass=com.cc.sys.db.SysGroup&pageId=sys_group,
				��Ȩ #/sys/module/loadGrant.do?entityClass=com.cc.sys.db.SysGroup"
	/>
</body>
</html>