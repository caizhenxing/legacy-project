<%@ page language="java" contentType="text/html;charset=GB2312"%>
<jsp:directive.include file="/base/default.jspf" />
<html>
	<body>
		<table class="td" border="0" cellspacing="0" cellpadding="0">
			<tr class="text">
			</tr>
			<moduletree:tree action="/sys/module/expandNode.do?tree=$-{name}"
				style="text" styleSelected="text-selected"
				styleUnselected="text-unselected" images="images/tree" 
				target="contents"/>
		</table>
	</body>
</html>