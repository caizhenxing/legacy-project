<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<body class="list_body">
<div class="main_title">
	<div>用户管理</div>
</div>
<div class="main_body">
	<div id="divId_panel"></div>
</div>
<entryPanel:show
	panelList=" 列表 #/sys/user/list.do?entityClass=com.cc.sys.db.SysUser&pageId=sys_user,
				详细 #/sys/user/load.do?entityClass=com.cc.sys.db.SysUser"
	/>
</body>
</html>