<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<body class="list_body">
<div class="main_title">
	<div>�û�����</div>
</div>
<div class="main_body">
	<div id="divId_panel"></div>
</div>
<entryPanel:show
	panelList=" �б� #/sys/user/list.do?entityClass=com.cc.sys.db.SysUser&pageId=sys_user,
				��ϸ #/sys/user/load.do?entityClass=com.cc.sys.db.SysUser"
	/>
</body>
</html>