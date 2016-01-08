<%@ page language="java" contentType="text/html;charset=GB2312" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>呼叫中心系统</title>

<script type="text/javascript" src = "/struts2/base/scripts/definedWin.js"></script>
</head>

<frameset id="mainIndexFrame" rows="68,*,20" frameborder="no" border="0" framespacing="0">
  <frame id="topFrame" src="top.jsp"  scrolling="no" noresize="true"  title="topFrame" />
  <frameset name="exce" cols="180,*" frameborder="no" border="0" framespacing="0" >
		<frame frameName="tree" src="tree.jsp"  scrolling="yes" noresize="true"  title="tree" />
		<frameset cols="11,*" frameborder="no" border="0" framespacing="0">
		  	<frame id="mid" src="" scrolling="no"  noresize="true" title="mid"/>
			<frame  id="contents" src="" name="contents"/>
		</frameset>
  </frameset>
  <frame id="bottomFrame" src="bottom.jsp"  scrolling="no" noresize="true"  title="bottomFrame" />
</frameset>

<noframes></noframes>

</html>
