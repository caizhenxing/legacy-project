<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<body class="list_body">
<div class="main_title">
	<div>��־��ѯ</div>
</div>
<div class="main_body">
	<div id="divId_panel"></div>
</div>
<entryPanel:show
	panelList=" �б� #/sys/log/list.do?entityClass=com.cc.sys.db.SysLog&pageId=sys_log"
	/>
</body>
</html>