<jsp:directive.include file="/decorators/default.jspf"/>
<html>
<body class="list_body">
	<script language="javascript">
	
		var CurrentPage = {};
		
		if (CurrentPage == null) {
			var CurrentPage = {};
		}
		
		
		
		
		CurrentPage.initValideInput = function () {
	
			if (FormUtils.isWriteable($('fieldInfoBean.id'))) {
				$('fieldInfoBean.id').dataType = 'Require';
				$('fieldInfoBean.id').msg = '变更项目不能为空';
			}
			if (FormUtils.isWriteable($('changeBean.itemDesc'))) {
				$('changeBean.itemDesc').dataType = 'Require';
				$('changeBean.itemDesc').msg = '描述不能为空';	
			}
		}
		
		CurrentPage.initValideInput();
	
		CurrentPage.create = function() {
				
			$('oid').value = '';
			FormUtils.post(document.forms[0], '<c:url value="/hr/changeItemAction.do?step=edit"/>');
		}
	
		CurrentPage.validation = function () {
			return Validator.Validate(document.forms[0], 4);
		}
		
		function AddItem(leftControl, rightControl)
        {
            
            Control1 = null;
            Control2 = null;
                
            Control1=leftControl;
            Control2=rightControl;
            var opt;

            var j=Control1.length;
            if(j==0) return;
            for(j;j>0;j--)
            {
                if(Control1.options[j-1].selected==true)
                {      
                    opt = new Option();
                    opt.text =Control1.options[j-1].text;
                    opt.value=Control1.options[j-1].value;
                    Control2.options.add(opt);
                    Control1.options[j-1] = null;
                }
            }
        }
        
        function AddItemAll(leftControl, rightControl)
        {
            
            Control1 = null;
            Control2 = null;
                
            Control1=leftControl;
            Control2=rightControl;
            var opt;

            var j=Control1.length;
            
            if(j==0) return;
            for(j;j>0;j--)
            {
                if(Control1.options[j-1].selected=true)
                {      
                    opt = new Option();
                    opt.text =Control1.options[j-1].text;
                    opt.value=Control1.options[j-1].value;
                    Control2.options.add(opt);
                    Control1.options[j-1] = null;
                }
            }
        }
        
        function DelItem(leftControl, rightControl)
        {
            Control1 = null;
            Control2 = null;
            
            Control1=leftControl;
            Control2=rightControl;
            
            var opt;
            var j=Control2.length;
            if(j==0) return;
            for(j;j>0;j--)
            {
                if(Control2.options[j-1].selected==true)
                {
                    opt = new Option();
                    opt.text = Control2.options[j-1].text;
                    opt.value= Control2.options[j-1].value;
                    changeAsc(opt);
                     Control1.options.add(opt);
                     Control2.options[j-1]=null;
                }
            }
        }
        
        function DelItemAll(leftControl, rightControl)
        {
            Control1 = null;
            Control2 = null;
            
            Control1=leftControl;
            Control2=rightControl;
            
            var opt;
            var j=Control2.length;
            if(j==0) return;
            for(j;j>0;j--)
            {
                if(Control2.options[j-1].selected=true)
                {
                    opt = new Option();
                    opt.text = Control2.options[j-1].text;
                    opt.value= Control2.options[j-1].value;
                    changeAsc(opt);
                     Control1.options.add(opt);
                     Control2.options[j-1]=null;
                }
            }
        }
        
        function addwritetext(obj)
        {
            var s="";
            for( i=0;i<obj.length;i++)
                s=s+obj.options[i].text + ",";
            s=s.substring(0,s.length-1);
            return(s);
        }

        function addwritevalue(obj)
        {
            var s="";
            for(i=0;i<obj.length;i++)
                s=s+obj.options[i].value + ",";
            s=s.substring(0,s.length-1);
            return(s);
        }         
        
        function OnSubmit(sel)
        {
            document.getElementById("hdnValue").value = addwritevalue(sel);
            if(!confirm('真的要提交吗？'))
            {
            	return(false);
            }
        }
  
function moveItemForSelectTag(objSelect, flgUpDown) {
 	if (objSelect == null || flgUpDown == null) return;
	if (flgUpDown != "U" && flgUpDown != "D") return;
	if (objSelect.tagName != "SELECT") return;

	if (objSelect.options == null) return;
	if (objSelect.options.length < 2) return;
	if (objSelect.selectedIndex < 0) return;
	if (objSelect.selectedIndex == 0 && flgUpDown == "U") return;
	if (objSelect.selectedIndex == objSelect.options.length-1 && flgUpDown == "D") return;

	var curIndex = objSelect.selectedIndex;
	var curValue = objSelect.options[curIndex].value;
	var curText = objSelect.options[curIndex].text;

	if (flgUpDown == "U")  {
		objSelect.options[curIndex].value = objSelect.options[curIndex-1].value;
		objSelect.options[curIndex].text = objSelect.options[curIndex-1].text;
		objSelect.options[curIndex-1].value = curValue;
		objSelect.options[curIndex-1].text = curText;
		objSelect.selectedIndex = curIndex-1;
	} else {
		objSelect.options[curIndex].value = objSelect.options[curIndex+1].value;
		objSelect.options[curIndex].text = objSelect.options[curIndex+1].text;
		objSelect.options[curIndex+1].value = curValue;
		objSelect.options[curIndex+1].text = curText;
		objSelect.selectedIndex = curIndex+1;
	}
	}
function asca(objSelect, flgUpDown) {
 	if (objSelect == null || flgUpDown == null) return;
	if (flgUpDown != "A" && flgUpDown != "D") return;
	if (objSelect.tagName != "SELECT") return;

	if (objSelect.options == null) return;
	var curIndex = objSelect.selectedIndex;
	var curValue = objSelect.options[curIndex].value;
	var curText = objSelect.options[curIndex].text;
	var vindex=curValue.lastIndexOf('-')==-1?curValue.length:curValue.lastIndexOf('-');
	var tindex=curText.lastIndexOf('-')==-1?curText.length:curText.lastIndexOf('-');
	if (flgUpDown == "A")  {
		objSelect.options[curIndex].value = curValue.substring(0,vindex)+'-asc';
		objSelect.options[curIndex].text = curText.substring(0,tindex)+'-升序';
	} else {
		objSelect.options[curIndex].value = curValue.substring(0,vindex)+'-desc';
		objSelect.options[curIndex].text = curText.substring(0,tindex)+'-降序';
	}
}
function changeAsc(obj)
{
	var curValue = obj.value;
	var curText = obj.text;
	var vindex=curValue.lastIndexOf('-')==-1?curValue.length:curValue.lastIndexOf('-');
	var tindex=curText.lastIndexOf('-')==-1?curText.length:curText.lastIndexOf('-');
	obj.value = curValue.substring(0,vindex);
	obj.text = curText.substring(0,tindex);
}
	</script>

	<form id="f" method="post">
	<input type="hidden" name="pageId" value='<c:out value="${theForm.pageId}"/>'/>
		<div class="update_subhead">
 			<span class="switch_open" onclick="StyleControl.switchDiv(this,$('submenu1'))" title="">定制显示</span>
		</div>
    	<table class="EasyTable" cellpadding="0" width="100%" border='0'id="submenu1">
        	<tr align="center">
            	<td width="18%"></td>
                <td>未设置页面项<br>
                   	<select id="selLeft" ondblclick="AddItem(selLeft, selRight)" style="width: 150px" 
                    		multiple="multiple" size="15" name="selLeft" runat="server" type="select-multiple" >
						<table:settable all="${theForm.all}" select="${theForm.select}" isSel="all"/>
               		</select>
                </td>
                <td width="100">
               		<table id="Table3" align="center" style="width: 200px">
                       	<tr>
                           	<td>
                           		<input id="btnAdd" title="将左边列表选中内容添加到右边列表" onclick="AddItem(selLeft, selRight)" 
                           			type="button" value=">>" name="btnAdd" runat="server" class="opera_display"
                           			/>
                           	</td>
                        </tr>
                        <tr>
                           	<td>
                           		<input id="btnAdd" title="将左边列表选中内容全部添加到右边列表" onclick="AddItemAll(selLeft, selRight)" 
                           			type="button" value="全部>>" name="btnAdd" runat="server" class="opera_display"
                           			/>
                           	</td>
                        </tr>
                        <tr>
                           	<td>
                           		<input id="btnAdd" title="将右边列表选中内容全部删除" onclick="DelItemAll(selLeft, selRight)" 
                           			type="button" value="<<全部" name="btnDel" runat="server" class="opera_display"
                           			/>
                           	</td>
                        </tr>
                        <tr>
                        	<td>
                        		<input id="btnDel" title="将右边列表选中内容删除" onclick="DelItem(selLeft, selRight)" 
                        		   type="button"  value="<<" name="btnDel" runat="server" class="opera_display"
                        		   />
                            </td>
                        </tr>
                   	</table> 
                </td>
                <td>已设置页面项<br>
              		<select id="selRight" ondblclick="DelItem(selLeft, selRight)" style="width: 150px" 
               			multiple="multiple" size="15" name="saveList" runat="server" type="select-multiple">
               			 <table:settable all="${theForm.all}" select="${theForm.select}" isSel="select"/>
                    </select>
                </td>
                <td>
                <table>
                <tr><input type="button" name="" id="" value="上移" class="opera_display"  onClick="moveItemForSelectTag(selRight,'U');"/></tr>
                <tr><input type="button" name="" id="" value="下移" class="opera_display" onClick="moveItemForSelectTag(selRight,'D');"/></tr>
                <c:if test="${theForm.asc}">
                 <tr><input type="button" name="" id="" value="asc"  onClick="asca(selRight,'A');"/></tr>
                 <tr><input type="button" name="" id=""  value="desc" onClick="asca(selRight,'D');"/></tr>
                </c:if>
                </table>
                </td>
                <td width="18%"></td>
            </tr>
            <tr>
            	<td colspan="5" height="2"><input id="hdnValue" type="hidden" name="hdnValue" runat="server">
            	</td>
            </tr>
         
        </table>
        <input type='hidden' name="asc" value="<c:out value='${theForm.asc}'/>"/>
    </form>
</body>
<script type="text/javascript">

if (CurrentPage == null) {
	    var CurrentPage = {};
	}  
CurrentPage.save = function() {

	var sel=$('selRight');

	var size=sel.options.length;

	for(i=0;i<size;i++)
	{
		sel.options[i].selected=true
	}
	
	FormUtils.post(document.forms[0], '<c:url value="/curd/curdAction.do?step=saveTable"/>');
	
	top.definedWin.closeListing();
//	alert(window.parent.document.frames('mainFrame').document.frames[0]);
//	opener.document.getElementById('opera_query').onclick();
	}

CurrentPage.onLoadSelect = function(){
		
		top.definedWin.selectListing = function(inum) {
			CurrentPage.save();
		}
	
	}
CurrentPage.onLoadSelect();

</script>
</html>