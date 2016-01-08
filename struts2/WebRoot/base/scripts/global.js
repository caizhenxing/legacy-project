/**
 * 全局参数设置
 * @author liuz
 * @modifier wangcc@bis.com.cn 2007-12-06 增加业务按钮的可见性控制 
 * @version $Id: global.js,v 1.9 2008/01/17 06:01:24 lanxg Exp $
 */

if(Global == null){
	var Global = {}
}

// 取通过URL传过来的参数 (格式如 ?Param1=Value1&Param2=Value2)
Global.URLParams = new Object() ;
Global.initParam = function (){
	var uParams = document.location.href.split('?');
	var aParams = document.location.search.substr(1).split('&') ;
	for (i=0 ; i < aParams.length ; i++) {
		var aParam = aParams[i].split('=') ;
		if(aParam[1])
			Global.URLParams[aParam[0]] = aParam[1].replace("#","") ;
	}
}
Global.initParam(); 

//Global.lastCss = '';
Global.hightLightForm = function(){
    // add input box highlighting 
    Global.addFocusHandlers(document.getElementsByTagName("input"));
    Global.addFocusHandlers(document.getElementsByTagName("textarea"));
}


Global.addFocusHandlers = function(elements) {
    for (i=0; i < elements.length; i++) {
        if (elements[i].type != "button" && elements[i].type != "submit" &&
				elements[i].type != "reset" ) {		
			if(elements[i].checked){
				elements[i].style.borderColor='orange';elements[i].select();
			}
			if(elements[i].readOnly)
				elements[i].className += " readonly";
            elements[i].onfocus=function() {this.style.borderColor='orange';this.select();Global.onfocusInput()};
            //elements[i].onclick=function() {this.select()};
            //elements[i].onblur=function() {this.style.borderColor="";};
			elements[i].onblur=function() {
				if( this.type == "checkbox" && this.checked){
					//对checkbox加重显示，如果radio需要写一个事件
				}else if( this.type == "radio" ){
					Global.clickRadio(this);
				}else {
					this.style.borderColor="";
				}		
				Global.onBlurInput();
			};
        }else if (elements[i].type == "button"){
			/* 点击按钮的效果*/
			//elements[i].oldClass = elements[i].className;
			//elements[i].onmouseover=function() {this.className= this.oldClass+" click";};
			//elements[i].onmouseout=function()  {this.className= this.oldClass;};
			
        }
    }
}
//在页面上激活事件
Global.onfocusInput = function(){};
Global.onBlurInput  = function(){};

Global.clickRadio = function(robj){
	var rname = document.getElementsByName(robj.name);
	for(var i=0;i<rname.length;i++){
		if(!rname[i].checked){
			rname[i].style.borderColor="";
		}
	}
}
Global.showLightbox = function(url){
	
}

//判断是否是自定义窗口
Global.isDefinedWindow = function(){
	if(!self.name){
		return false;
	}else if(self.name && self.name.indexOf("definedWin")==0){
		return true;
	}else if(parent != self && parent.Global){
		return parent.Global.isDefinedWindow();
	}
	return false;
}

/**自动调节页面高度*/
Global.setHeight = function(hei){
	if(self == parent || self == top)
		return;
	var el = parent.document.getElementsByTagName("iframe");
	var flag = false;
	for(var i = 0; i < el.length; i++){
		if(el[i].name){	
			var url = parent.frames[el[i].name].location;
			if(typeof url.href == 'string' && self.location.href == url) {
				//el[i].style.height = document.body.scrollHeight + (hei ? hei : 10);//must +"px";
				el[i].height = document.body.scrollHeight + (hei ? hei : 10);
				//el[i].parentNode.style.height = el[i].height;
				flag = true;				
				break;
			}
		}
	}
	if(flag && parent.Global && !window.dialogArguments) {
		parent.Global.setHeight();
	}
	//alert(location.href+"| \r\n "+document.body.scrollHeight);
}

/**
 * 组装下拉框
 */
Global.populateCombos = function() {
	var combos = document.getElementsByTagName('select');	
	var ELEMENT_SEPARATOR = '|||';
	var TEXT_VALUE_SEPARATOR = '###';

	for (var i = 0; i < combos.length; i++) {
		var combo = combos[i];
		var comboSupportList = combo.getAttribute('source');
		if (comboSupportList != null) {
			var elements = comboSupportList.split(ELEMENT_SEPARATOR);
			if (elements[0] == 'true') {
				combo.multiple = true;
				combo.Size = 10;
			}
			
			for (var j = 1; j < elements.length; j++) {
				var valueTextSelected = elements[j].split(TEXT_VALUE_SEPARATOR);
				var value = valueTextSelected[0];
				var text = valueTextSelected[1];
				var selected = valueTextSelected[2];
				var option = new Option(text, value);
				combo.options[combo.options.length] = option;
				if (selected == 'true') {
					option.selected = true;
				}
			}
		}
	}
}

/*
 * 显示顶部的操作按钮
 */
Global.topOpera = top.TopOpera;
Global.displayOperaButton = function(){
	if(Global.topOpera){
		Global.topOpera.disabledBtn();
		if(typeof CurrentPage == 'object'){
			if(CurrentPage.create){
				Global.topOpera.create = CurrentPage.create;
				Global.topOpera.setAbledBtn(0);
			}
			if(CurrentPage.submit){
				Global.topOpera.save  = CurrentPage.submit;
				Global.topOpera.setAbledBtn(1);
			}
			if(CurrentPage.doTransition){
				Global.topOpera.submit  = CurrentPage.doTransition;
				Global.topOpera.setAbledBtn(2);
			}else if(CurrentPage.initializeWorkflow){
				Global.topOpera.submit  = CurrentPage.initializeWorkflow;
				Global.topOpera.setAbledBtn(2);
			}
			/*根据是否在桌面决定业务按钮是否可见（在桌面时不可见）wangcc@bis.com.cn 2007-12-06 Start*/
		    if (CurrentPage.notVisibleBtn){
		    	Global.topOpera.setUnVisibleBtn(0);
		    	Global.topOpera.setUnVisibleBtn(1);
		    	Global.topOpera.setUnVisibleBtn(2);
		    }else{
		    	Global.topOpera.setVisibleBtn(0);
		    	Global.topOpera.setVisibleBtn(1);
		    	Global.topOpera.setVisibleBtn(2);		    		
		    }
		    /*根据是否在桌面决定业务按钮是否可见（在桌面时不可见）wangcc@bis.com.cn 2007-12-06 End*/
		}
		if(typeof PublicPaginater == 'object'){
			if(PublicPaginater.forwardPre){
				Global.topOpera.prePage  = PublicPaginater.forwardPre;
				Global.topOpera.setAbledBtn(3);
			}
			if(PublicPaginater.forwardNext){
				Global.topOpera.nextPage = PublicPaginater.forwardNext;
				Global.topOpera.setAbledBtn(4);
			}			
		}
		if(typeof TableSort == 'object'){
			if(TableSort.isPreLine){
				TableSort.isPreLine();
				Global.topOpera.preLine = TableSort.preLine;
			}
			if(TableSort.isNextLine){
				TableSort.isNextLine();
				Global.topOpera.nextLine = TableSort.nextLine;
			}
		}
	}
}

//动态显示当前位置
Global.displayTreePath = function(){
    var tree = top.topTree;
    if (tree && window.name == 'mainFrame') {
        var url = document.URLUnencoded + '';
        if (url.indexOf("&panelUrl") != -1) {
        	url = url.substring(0,url.indexOf("&panelUrl"));
        }
	    var len = tree.DeptList.length;
	    for (var i=0 ; i<len ; i++) {
	        var nodeUrl = tree.DeptList[i].nodeUrl;
//	        nodeUrl = nodeUrl.replace('&amp;','&');
	        if (url.indexOf(nodeUrl) != -1) {
                var offset = url.indexOf(nodeUrl);
                if (nodeUrl.indexOf(url.substring(offset)) != -1) {
	                var iselect = i;
					var lvlName = "";
					for (;;){
						var node = tree.DeptList[iselect];
						if (typeof node != 'object'){
							break;
						}
						if (lvlName == ""){
							lvlName = node.nodeName;
						}else{
							lvlName = node.nodeName + "\\" + lvlName;
						}
						iselect = node.fathId;
					}
					tree.clickNode(i);
					tree.setExtend(i);
					top.document.getElementById("location").innerHTML = lvlName;
					return;
				}
	        }
	    }
    }
}

//提供给页面加入自定义onload内容
Global.beforeOnload = function(){}
Global.afterOnload = function(){}

/*  如果页面使用onload，都要使用如下形式才能保证前一个onload不会覆盖。
 *  var customaryOnload = window.onload;
 *  window.onload  = initPage;
 *  方法中再使用 eval(customaryOnload());
 *
 */
window.onload = function() {
	Global.beforeOnload();
	//close loading
	if(top.definedWin && top.definedWin.isLoading){
		top.definedWin.closeLoading();
	}

	//高亮度显示输入框
    Global.hightLightForm();
    Global.setHeight();
	//Global.populateCombos();
	var divObj = document.getElementsByName('divId_scrollLing');
	if(divObj && divObj.length){
		for(var i=0;i<divObj.length;i++){
			//TableSort.resizeListing(divObj[i]);
			//将列表对象化,加入拖拉宽度效果			
			//ListUtil.cell[i] = new ListUtil.fixCell('ListUtil.cell['+i+']',divObj[i]);
			//tableSort.js 
			if(divObj[i].childNodes[0].rows.length > 1){
				var trObj = divObj[i].childNodes[0].rows[1]
				TableSort.lastClick = trObj;
				trObj.lastClassName = trObj.className;
				//TableSort.setClickTrObj(trObj,false);
			}
		}		
	}

	//display opera button
	if(!Global.isDefinedWindow()){
		Global.displayOperaButton();
	}

    //动态显示当前位置
    Global.displayTreePath();

	//对操作按钮设置为disabled，见frame_explorer.htm
	//if(parent &&  parent.explorerFrame)
	//	parent.explorerFrame.setButtonAllDisabled();
	Global.afterOnload();
}
