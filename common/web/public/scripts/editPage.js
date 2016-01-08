

/*
 * 建立一个编辑页面对象，用于区分多个子表操作
 *
 */
EditPage = function(name){
	this.name = name;
	//当前个数，默认为1，如果修改时候等于已经初始化个数。这里只有累加，因为要唯一标识个数
	this.rowNumber = 1;
	//唯一ID的对象，用于在后台判断是新增（为空）或者修改（不为空）
	this.uuidObj   = "";
	//是否保留最后 N 行，在倒数第 N 行前的位置插入新行(N = holdRow)
	this.holdRow   = 0;

	this.regtxt    = /\[0\]/gi;
	this.regtxt1   = /row=\"0\"/gi;

	//this.delRow ;
	//this.renRow ;
	//this.addRow ;
	//this.clearAll  ;
	
	//将不符合批量替换规则的字符先进行转换,注意 ||| 是保留字符
	this.beginReplaceString = function(str){
		var reg = /\)\[0\]/gi;
		var str1 = str.replace(reg,"|||");
		return str1;
	}
	//将替换后的不符合规则字符替换回来
	this.afterReplaceString = function(str){
		var reg = /\|\|\|/gi;
		var str1 = str.replace(reg,")[0]");
		return str1;		
	}

	//删除行
	this.delRow = function (table,sel){
		var inum = sel.row;
		var objStr = this.uuidObj.replace(this.regtxt,"["+inum+"]");
		var str = document.getElementsByName(objStr)[0].value;
		var obj = document.getElementById(this.name+"_trId_["+inum+"]");
		this.afterDeleteRow(table,sel)
		if(str ==""){
			if(inum == 0){//第一行是假删除
				this.disableButton(obj);
				this.switchButton(inum,"none","none","")
			}else{
				//前端删除		
				table.deleteRow(obj.rowIndex);
				Global.setHeight();
			}
		}else{
			this.disableButton(obj);
			this.switchButton(inum,"none","none","")
		}
	}
	//恢复行，不再删除
	this.renRow = function(table,sel){
		var inum = sel.row;
		var obj = document.getElementById(this.name+"_trId_["+inum+"]");
		this.ableButton(obj);
		//if(inum == 0)
			this.switchButton(inum,"","","none")
		//else
		//	this.switchButton(inum,"none","","none")
	}

	//新增行
	this.addRow = function(table){
		var len = table.rows.length;
		len = this.holdRow ? len - this.holdRow : len;
		var row = table.insertRow(len);
		var tabStr = document.getElementById(this.name+"_trId_[0]").childNodes[0].innerHTML;
		tabStr = this.beginReplaceString(tabStr)
		tabStr = tabStr.replace(this.regtxt,"["+this.rowNumber+"]");
		tabStr = tabStr.replace(this.regtxt1,"row='"+this.rowNumber+"'");
		tabStr = this.afterReplaceString(tabStr)
		var td = document.createElement("TD");
		row.appendChild(td);
		row.row = this.rowNumber;
		row.id  = this.name+"_trId_["+this.rowNumber+"]";
		td.innerHTML = tabStr;
		this.switchButton(this.rowNumber,"","","none");
		this.clearValue(row);
		this.rowNumber++;
		this.afterAddRow(table)
		Global.hightLightForm();
		Global.setHeight();
	}

	//列表的新增行
	this.addListingRow = function(table){
		var len = table.rows.length;
		len = this.holdRow ? len - this.holdRow : len;
		var row = table.insertRow(len);
		row.row = this.rowNumber;
		row.id  = this.name+"_trId_["+this.rowNumber+"]";
		//row.style.cssText=document.getElementById(this.name+"_trId_[0]").style.cssText;
		row.uuid="";
		var tdStr = "";
		var td;
		var tds = document.getElementById(this.name+"_trId_[0]").childNodes;
		//var tds = table.rows[1].cells;
		for(var i=0;i<tds.length;i++){
			tdStr = tds[i].innerHTML;
			tdStr = this.beginReplaceString(tdStr)
			tdStr = tdStr.replace(this.regtxt,"["+this.rowNumber+"]");
			tdStr = tdStr.replace(this.regtxt1,"row='"+this.rowNumber+"'");
			tdStr = this.afterReplaceString(tdStr)
			td = document.createElement("TD");
			td.innerHTML = tdStr;
			td.className = tds[i].className;
			row.appendChild(td);
		}
		this.switchButton(this.rowNumber,"","","none");
		this.clearValue(row);
		this.rowNumber++;
		this.afterAddRow(table)
		Global.hightLightForm();
		Global.setHeight();
	}

	//设置操作按钮
	this.switchButton = function(inum,arr1,arr2,arr3){
		if(document.getElementById(this.name+"_["+inum+"].add"))
			document.getElementById(this.name+"_["+inum+"].add").style.display = arr1;
		if(document.getElementById(this.name+"_["+inum+"].del"))
			document.getElementById(this.name+"_["+inum+"].del").style.display = arr2;
		if(document.getElementById(this.name+"_["+inum+"].old"))
			document.getElementById(this.name+"_["+inum+"].old").style.display = arr3;
	}

	//将所有obj内的未定义holdValue域的对象值清空或者指定为以定义的defValue
	this.clearValue = function(obj){
		var defValue;
		var objAll = obj.getElementsByTagName("*");
		for( i=0;i<objAll.length;i++) {
			if((objAll[i].tagName == "INPUT" || objAll[i].tagName == "TEXTAREA") && !objAll[i].holdValue){
				defValue = objAll[i].defValue ? objAll[i].defValue : "";
				objAll[i].value = defValue;
				objAll[i].checked = false;
			}else if(objAll[i].fillable && !objAll[i].holdValue){
				objAll[i].innerText = "";
			}
		}	
	}

	//对所有obj内未定义holdObj的对象设置为disabeld;
	this.disableButton = function(obj){
		obj.disabled = true;
		var temp = obj.getElementsByTagName("*");
		for( i=0;i<temp.length;i++) {
			if(temp[i].tagName == "INPUT"){
				if(!temp[i].holdObj){
					temp[i].disabled = true;
				}
			}
		}
		obj.oldClass = obj.className;
		obj.className = "disabled";
	}
	this.disableAll = function(obj){
		var temp = obj.getElementsByTagName("*");
		for( i=0;i<temp.length;i++) {
			if(temp[i].tagName == "INPUT"){
				temp[i].disabled = true;
			}
		}
	}
	//将所有对象设置为able;
	this.ableButton = function(obj){
		obj.disabled = false;
		var temp = obj.getElementsByTagName("*");
		for( i=0;i<temp.length;i++) {
			temp[i].disabled = false;
		}
		obj.className = obj.oldClass;
	}

	//在页面上清除掉要删除的元素
	this.clearAll = function (table){
		var old = document.getElementsByName(this.name+"_oldBtn");
		var obj;
		for(var i=0;i<old.length;i++){
			if(old[i].style.display ==""){
				obj = document.getElementById(this.name+"_trId_["+old[i].row+"]");
				obj.childNodes[0].innerHTML = "";//清除
				obj.style.display = "none";
			}
		}
		Global.setHeight();
	}

	this.mouseOver = function(tdObj){
		tdObj.fvalue = tdObj.innerText;
		var str = "";
		if(!tdObj.fname)
			return;
		str += "<input type='text' value='"+tdObj.fvalue+"' name='"+tdObj.fname+"'/>";
		var inum = tdObj.parentNode.row;
		var uuid = tdObj.parentNode.uuid;
		var objStr = this.uuidObj.replace(this.regtxt,"["+inum+"]");
		if(document.getElementsByName(objStr)[0].disabled)
			document.getElementsByName(objStr)[0].disabled = false;
		tdObj.innerHTML = str;
		//document.getElementsByName(tdObj.fname)[0].onmouseOut = this.mouseOut;
	}
	this.mouseOut = function(tdObj){
		tdObj = this;
		if(!tdObj.fname)
			return;
		var nvalue = document.getElementsByName(tdObj.fname)[0].value;
		tdObj.fvalue = nvalue;
		var str = "<input type='hidden' value='"+tdObj.fvalue+"' name='"+tdObj.fname+"'/>";
		tdObj.innerHTML = tdObj.fvalue + str;
	}
	/*
	this.overTd = function(e) {
		var src = e.srcElement;
		while(src!=null){
			  if(src.nodeName!="INPUT"){   
				  this.mouseOver(src);
				  return;
			  }
			  src=src.parentNode;
	   }
	}
	this.outTd = function(e){
		var src = e.srcElement;
		while(src!=null){
			  if(src.nodeName=="TD"){   
				  this.mouseOut(src);
				  return;
			  }
			  src=src.parentNode;
	   }
	}*/

	/*存放初始化值
	 * @param item 对应的行对象名
	 * @param arr  对应的输入框的值，与表中input一一对应，注意先后顺序
	 * 扩展元素一个属性 fillable，如果该元素（span、div）有这个属性，则进行填充;
	   如果该输入域没有用途，就为空值对应填充
	 */
	this.initEditDate = function(itemObj,arrValue){
		var j = 0;
		var arr = itemObj.getElementsByTagName("*");
		for(var i=0;i<arr.length;i++){
			if(arr[i].tagName == 'INPUT'){
				if(arr[i].type == 'text' || arr[i].type == 'hidden' || arr[i].type == 'textarea' || arr[i].type == 'radio'){
					arr[i].value = arrValue[j];
					arr[i].oldValue=arrValue[j];
					j++;
				}
				if(arr[i].type == 'checkbox'){
					//checkbox 要加 holdValue = true 
					if(arrValue[j] == 'true'){
						arr[i].checked = true;						
					}else
						arr[i].checked = false;
					j++;
				}
			}else if(arr[i].fillable){
				arr[i].innerText = arrValue[j];
				j++;
			}
		}
	}

	//在增加行后提供重载的方法
	this.afterAddRow = function(table){
		//TODO
	}
	//在删除行后提供重载的方法,参数请参考 this.delRow
	this.afterDeleteRow = function(table,sel){
		//TODO
	}
	//
	//
	//为了逻辑删除增加的方法
	//
	//
	this.delRowaa = function (table,sel){
			var inum = sel.row;
			var del = document.getElementById(this.name+"_["+inum+"].delFlg").value;
			var str = document.getElementById(this.name+"_["+inum+"].id").value;
			var obj = document.getElementById(this.name+"_trId_["+inum+"]");
			this.afterDeleteRow(table,sel)
			if(str ==""){
				if(inum == 0){//μúò?DDê??ùé?3y
					this.disableButton(obj);
					this.switchButton(inum,"none","none","")
				}else{
					//?°??é?3y	
					table.deleteRow(obj.rowIndex);
					Global.setHeight();
				}
			}else{
			
				//this.disableButton(obj);
				obj.className = "disabled";
				document.getElementById(this.name+"_["+inum+"].delFlg").value="1";
				this.switchButton(inum,"none","none","")
			}
		}
		this.renRowCur = function (table,sel){
			var inum = sel.row;
			document.getElementById(this.name+"_["+inum+"].delFlg").value="0";
			this.renRow(table,sel);
		}
}