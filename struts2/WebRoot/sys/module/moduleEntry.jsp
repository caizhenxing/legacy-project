<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<script type="text/javascript" src = "/<c:out value='${sysParameter[\'publicResourceServer\']}'/>/base/scripts/xmlhttp.js"></script> 
<script type="text/javascript" src = "/<c:out value='${sysParameter[\'publicResourceServer\']}'/>/base/scripts/codeTree.js"></script>
<body class="list_body">
<form name="instrument_entry" method="post" action="">
		<table width="100%" border=0 cellpadding="0" cellspacing="0" height="100%">
		   <div class="main_title"><div>系统模块结构维护</div></div>
		    <tr valign="top">
		        <td width="20%" id="tdId_1">
					<div id="span_menu"  class="tree">
					<treeview:tree tree="entityObject"/>
					</div>
		        </td>
		        <td width="5" style="cursor:e-resize" onmousedown="setCapture()" onmouseup="releaseCapture();" onmousemove="StyleControl.dragWidth(tdId_1,30)" bgcolor="#eeeeee">&nbsp;</td>
		        <td> 
		            <div class="main_body">
		                <div id="divId_panel"></div>
		            </div>
		            <entryPanel:show
						panelList="详细 #/sys/module/load.do?entityClass=com.cc.sys.db.SysModule&oid=tree_root"
					/>
		        </td>
		    </tr>
		</table>
			<script type="text/javascript">
		 function viewDetail(cid) {
    var pat = new RegExp('([\?\&])oid=[^\&]*');
    for (i = 0; i < panel.dataArr.length; i++) {
	    var url = panel.dataArr[i][2];
	    if (pat.test(url)) {
		    panel.dataArr[i][2]=url.replace(pat, RegExp.$1 + "oid=" + cid);
	    }
    }
    panel.click(0);
  }
		</script>
		</form>

</body>
</html>