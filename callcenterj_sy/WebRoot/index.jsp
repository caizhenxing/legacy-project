<%@ page language="java" contentType="text/html;charset=GB2312" %>
<script>
function newWindows(pageurl){
    var winwidth = screen.availWidth-10;
    var winheight= screen.availHeight-30;
	window.open(pageurl,'_blank','top=0,left=0,width=1430,height=810,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,location=no,status=yes');
	this.window.opener = null;
	//this.window.close();//不可以在这里关闭，因为在这里关闭的话IE第一次打开时有弹出提示就关了，什么都没有了
}
newWindows("index2.jsp");
</script>
