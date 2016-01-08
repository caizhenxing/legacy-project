<%@ page language="java" contentType="text/html;charset=GB2312" %>
<html>

<jsp:directive.include file="/base/default.jspf" />
<script type="text/javascript" src = "/<c:out value='${sysParameter[\'publicResourceServer\']}'/>/base/scripts/xmlhttp.js"></script> 
<script type="text/javascript" src = "/<c:out value='${sysParameter[\'publicResourceServer\']}'/>/base/scripts/codeTree.js"></script>
<body class="list_body">
<script language="javascript">
		var msgInfo_ = new msgInfo();
		if (CurrentPage == null) {
    		var CurrentPage = {};
		}     
		CurrentPage.save = function() {
			var grant = "";
			var as = document.getElementsByName("temp_all_checkbox");
			for(var i=0;i<as.length;i++)		
			{			
			 	temp = as[i];						
				if(temp.checked)
				{
					grant += temp.value +";";
				}
			}
			$('grantString').value = grant;
			FormUtils.post(document.forms[0], '<c:url value="/sys/module/saveGrant.do"/>');
		}
		
		
		</script>
<form name="instrument_entry" method="post" action="">
<input type="hidden" name="oid" value="<c:out value='${oid}'/>" />
<input type="hidden" name="grantS" value="" id="grantString"/>
		<table width="100%" border=0 cellpadding="0" cellspacing="0" height="100%">
		   <div class="main_title"><div>系统模块授权</div></div>
				<tr>
					<td class="attribute">
						组名
					</td>
					<td>
						<input bisname="组名" maxlength="50" name=""
							value="<c:out value='${entityObject.name}'/>" readonly="true" class="readonly"/>
						&nbsp;
						<span class="font_request">*</span>
					</td>
				</tr>
				<tr valign="top">
		        <td width="20%" id="tdId_1" colspan="2">
					<div id="span_menu"  class="tree">
					<treegrant:tree tree="tree"/>
					</div>
		        </td>
		    </tr>
		    <div class="list_bottom">
			<input type="button" value="" name="" id="opera_save" onClick="CurrentPage.save();" />
			</div>
		</table>
		</form>

</body>
</html>