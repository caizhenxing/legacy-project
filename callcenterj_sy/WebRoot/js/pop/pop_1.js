
function CLASS_MSN_MESSAGE_1(id,width,height,caption,title,message,target,action){  
    this.id     = id;  
    this.title  = title;  
    this.caption= caption;  
    this.message= message;  
    this.target = target;  
    this.action = action;  
    this.width    = width?width:200;  
    this.height = height?height:120;  
    this.timeout= 500;  
    this.speed    = 20; 
    this.step    = 1; 
    this.right    = screen.width -1;  
    this.bottom = screen.height; 
    this.left    = this.right - this.width; 
    this.top    = this.bottom - this.height; 
    this.timer    = 0; 
    this.pause    = false;
    this.close    = false;
    this.autoHide    = true;
}  
  

CLASS_MSN_MESSAGE_1.prototype.hide = function(){  
    if(this.onunload()){  

        var offset  = this.height>this.bottom-this.top?this.height:this.bottom-this.top; 
        var me  = this;  

        if(this.timer>0){   
            window.clearInterval(me.timer);  
        }  

        var fun = function(){  
            if(me.pause==false||me.close){
                var x  = me.left; 
                var y  = 0; 
                var width = me.width; 
                var height = 0; 
                if(me.offset>0){ 
                    height = me.offset; 
                } 
     
                y  = me.bottom - height; 
     
                if(y>=me.bottom){ 
                    window.clearInterval(me.timer);  
                    me.Pop.hide();  
                } else { 
                    me.offset = me.offset - me.step;  
                } 
                me.Pop.show(x,y,width,height);    
            }             
        }  

        this.timer = window.setInterval(fun,this.speed)      
    }  
}  
  

CLASS_MSN_MESSAGE_1.prototype.onunload = function() {  
    return true;  
}  

CLASS_MSN_MESSAGE_1.prototype.show = function(){  

    var oPopup = window.createPopup(); 
    
    this.Pop = oPopup;  
  
    var w = this.width;  
    var h = this.height;  
  
    var str = "<DIV id='divM' style='BORDER-RIGHT: #455690 1px solid; BORDER-TOP: #a6b4cf 1px solid; Z-INDEX: 99999; LEFT: 0px; BORDER-LEFT: #a6b4cf 1px solid; WIDTH: " + w + "px; BORDER-BOTTOM: #455690 1px solid; POSITION: absolute; TOP: 0px; HEIGHT: " + h + "px; BACKGROUND-COLOR: #c9d3f3'>"  
        str += "<TABLE style='BORDER-TOP: #ffffff 1px solid; BORDER-LEFT: #ffffff 1px solid' cellSpacing=0 cellPadding=0 width='100%' bgColor=#cfdef4 border=0>"  
        str += "<TR>"  
        str += "<TD style='FONT-SIZE: 12px;COLOR: #0f2c8c' width=30 height=24></TD>"  
        str += "<TD style='PADDING-LEFT: 4px; FONT-WEIGHT: normal; FONT-SIZE: 12px; COLOR: #1f336b; PADDING-TOP: 4px' vAlign=center width='100%'>" + this.caption + "</TD>"  
        str += "<TD style='PADDING-RIGHT: 2px; PADDING-TOP: 2px' vAlign=center align=right width=19>"  
        str += "<SPAN title=close style='FONT-WEIGHT: bold; FONT-SIZE: 12px; CURSOR: hand; COLOR: red; MARGIN-RIGHT: 4px' id='btSysClose' >*</SPAN></TD>"  
        str += "</TR>"  
        str += "<TR>"  
        str += "<TD style='PADDING-RIGHT: 1px;PADDING-BOTTOM: 1px' colSpan=3 height=" + (h-28) + ">"  
        str += "<DIV id='divL' style='BORDER-RIGHT: #b9c9ef 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #728eb8 1px solid; PADDING-LEFT: 8px; FONT-SIZE: 12px; PADDING-BOTTOM: 8px; BORDER-LEFT: #728eb8 1px solid; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; BORDER-BOTTOM: #b9c9ef 1px solid; HEIGHT: 100%'><span id='spanL'>" + this.title + "</span><BR><BR>"  
        str += "</DIV>"  
        str += "</TD>"  
        str += "</TR>"  
        str += "</TABLE>"  
        str += "</DIV>"  
  
    oPopup.document.body.innerHTML = str;     
  
    this.offset  = 0; 
    var me  = this;  

    oPopup.document.body.onmouseover = function(){me.pause=true;}
    oPopup.document.body.onmouseout = function(){me.pause=false;}
  
//================================================
	var v_height=105;
	var v_divL = oPopup.document.getElementById("divL");
	
	//---------------短消息----------------------
	if(this.action.length>1){
		v_height+=11;
		//alert("this.action = "+this.action);
		var v_url = this.action;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			me.hide();
			popUp('Messagewindows',v_url,750,280);
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "您有一封新的普通短消息！";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 

	var myStr = this.message.split(',');
	//alert(myStr);
					
	//待审
	var a_dianxing = new Array();//普通
	var a_xiaoguo = new Array();//效果
	var a_huizhen = new Array();//会诊
	var a_jiaodian = new Array();//焦点
	var a_qiye = new Array();//企业
	var a_gongqiu = new Array();//供求
	var a_putongyiliao = new Array();//普通医疗
	var a_yuyueyiliao = new Array();//预约医疗						
	var a_jiage = new Array();//价格
	var a_wenjuansheji = new Array();//问卷设计
	var a_jiaodianzhuizong = new Array();//焦点追踪一审
	//驳回
	var a_dianxing_bh = new Array();
	var a_xiaoguo_bh = new Array();
	var a_huizhen_bh = new Array();
	var a_jiaodian_bh = new Array();
	var a_qiye_bh = new Array();	
	var a_gongqiu_bh = new Array();
	var a_putongyiliao_bh = new Array();	
	var a_yuyueyiliao_bh = new Array();
	var a_jiage_bh = new Array();
	var a_wenjuansheji_bh = new Array();
	var a_jiaodianzhuizong_bh = new Array();//焦点追踪一审驳回
	//已审
	var a_dianxing_ys = new Array();
	var a_xiaoguo_ys = new Array();
	var a_huizhen_ys = new Array();
	var a_jiaodian_ys = new Array();
	var a_qiye_ys = new Array();	
	var a_gongqiu_ys = new Array();
	var a_putongyiliao_ys = new Array();	
	var a_yuyueyiliao_ys = new Array();	
	var a_jiage_ys = new Array();
	var a_wenjuansheji_ys = new Array();

	//发布		
	var a_dianxing_fb = new Array();
	var a_xiaoguo_fb = new Array();
	var a_huizhen_fb = new Array();
	var a_jiaodian_fb = new Array();
	var a_gongqiu_fb = new Array();
	var a_jiage_fb = new Array();
	var a_wenjuansheji_fb = new Array();
	
	//一审
	var a_shichangfenxi_fc = new Array();
	//一审驳回
	var a_shichangfenxi_fcb = new Array();
	//二审
	var a_shichangfenxi_sc = new Array();
	//二审驳回
	var a_shichangfenxi_scb = new Array();
	
	var a_jiaodianzhuizong_es = new Array();//焦点追踪二审
	var a_jiaodianzhuizong_esbh = new Array();//焦点追踪二审驳回
	
	//alert("myStr.length"+myStr.length);
	for(var i=0;i<myStr.length;i+=2){
	//alert("myStr["+i+"]"+myStr[i]);	
		if(myStr[i]=="dianxing"){
			//alert("dianxing");
			//alert("myStr["+i+1+"]"+myStr[i+1]);						
			if(myStr[i+1]=="待审"){
			//alert("待审");
				a_dianxing.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
			//alert("驳回");
				a_dianxing_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
			//alert("已审");
				a_dianxing_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
			//alert("发布");
				a_dianxing_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="jiaodian"){
			if(myStr[i+1]=="待审"){
				a_jiaodian.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_jiaodian_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_jiaodian_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_jiaodian_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="xiaoguo"){			
			if(myStr[i+1]=="待审"){
				a_xiaoguo.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_xiaoguo_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_xiaoguo_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_xiaoguo_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="huizhen"){
			if(myStr[i+1]=="待审"){
				a_huizhen.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_huizhen_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_huizhen_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_huizhen_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="qiye"){
			if(myStr[i+1]=="待审"){
				a_qiye.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_qiye_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_qiye_ys.push(new Array(myStr[i],myStr[i+1]));
			}									
		}else if(myStr[i]=="gongqiu"){
			if(myStr[i+1]=="待审"){
				a_gongqiu.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_gongqiu_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_gongqiu_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_gongqiu_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="putongyiliao"){
			if(myStr[i+1]=="待审"){
				a_putongyiliao.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_putongyiliao_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_putongyiliao_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_putongyiliao_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="yuyueyiliao"){
			if(myStr[i+1]=="待审"){
				a_yuyueyiliao.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_yuyueyiliao_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_yuyueyiliao_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_yuyueyiliao_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="jiage"){
			if(myStr[i+1]=="待审"){
				a_jiage.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_jiage_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_jiage_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_jiage_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="wenjuansheji"){
			if(myStr[i+1]=="待审"){
				a_wenjuansheji.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="驳回"){
				a_wenjuansheji_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="已审"){
				a_wenjuansheji_ys.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="发布"){
				a_wenjuansheji_fb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="shichangfenxi"){
			if(myStr[i+1]=="一审"){
				a_shichangfenxi_fc.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="一审驳回"){
				a_shichangfenxi_fcb.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="二审"){
				a_shichangfenxi_sc.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="二审驳回"){
				a_shichangfenxi_scb.push(new Array(myStr[i],myStr[i+1]));
			}										
		}else if(myStr[i]=="jiaodianzhuizong"){
			if(myStr[i+1]=="一审"){
				a_jiaodianzhuizong.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="一审驳回"){
				a_jiaodianzhuizong_bh.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="二审"){
				a_jiaodianzhuizong_es.push(new Array(myStr[i],myStr[i+1]));
			}else if(myStr[i+1]=="二审驳回"){
				a_jiaodianzhuizong_esbh.push(new Array(myStr[i],myStr[i+1]));
			}										
		}				
								
	}
				
	//-----------普通--------------------			
	//alert("1"+v_divL);
	//alert(a_dianxing.length);
	if(a_dianxing.length>0){
		v_height+=11;
		//alert("a_dianxing.length>0");
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_dianxing.length+"条典型案例库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_dianxing_bh.length>0){
		v_height+=11;
		//alert("a_dianxing_bh.length>0");
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_dianxing_bh.length+"条典型案例库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	if(a_dianxing_ys.length>0){
		v_height+=11;
		//alert("a_dianxing_ys.length>0");
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_dianxing_ys.length+"条典型案例库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}

//	if(a_dianxing_fb.length>0){
//		//alert("a_dianxing_fb.length>0");
//		var addDiv = oPopup.document.createElement('DIV');
//		
//		addDiv.style.wordBreak ='break-all';
//		addDiv.align='left';
//		
//		var addA = oPopup.document.createElement('A');
//		addA.href='javascript:void(0)';
//		addA.hidefocus='false';
//		addA.onclick = function(){
//			popUp('windows','../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoMain&state=issuance',800,550);
//			me.hide();
//	    }
//		
//		var addFont = oPopup.document.createElement('FONT');
//		addFont.color='#ff0000';
//		addFont.innerText = "你有"+a_dianxing_fb.length+"条典型案例库 发布 消息";
//		
//		addA.appendChild(addFont);
//		addDiv.appendChild(addA);
//		v_divL.appendChild(addDiv);
//	}
	
	//-----------焦点-------------------
	if(a_jiaodian.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodian.length+"条焦点案例库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_jiaodian_bh.length>0){	
		v_height+=11;	
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodian_bh.length+"条焦点案例库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_jiaodian_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodian_ys.length+"条焦点案例库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
//	if(a_jiaodian_fb.length>0){
//		var message="你有"+a_jiaodian_fb.length+"条焦点案例库 发布 消息";
//		var v_action = "popUp(\'windows\',\'../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoMain&state=issuance\',800,550);";
//		
//	}
	
	//------------会诊------------------
	if(a_huizhen.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../caseinfo/hzinfo.do?method=tohzinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_huizhen.length+"条会诊案例库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_huizhen_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/hzinfo.do?method=tohzinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_huizhen_bh.length+"条会诊案例库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_huizhen_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/hzinfo.do?method=tohzinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_huizhen_ys.length+"条会诊案例库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
//	if(a_huizhen_fb.length>0){
//		var message="你有"+a_huizhen_fb.length+"条会诊案例库 发布 消息";
//		var v_action = "popUp(\'windows\',\'../caseinfo/hzinfo.do?method=tohzinfoMain&state=issuance\',800,550);";
//		
//	}
				
	//------------效果------------------
	if(a_xiaoguo.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_xiaoguo.length+"条效果案例库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_xiaoguo_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_xiaoguo_bh.length+"条效果案例库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_xiaoguo_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_xiaoguo_ys.length+"条效果案例库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 

//	if(a_xiaoguo_fb.length>0){				
//		var message="你有"+a_xiaoguo_fb.length+"条效果案例库 发布 消息";
//		var v_action = "popUp(\'windows\',\'../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoMain&state=issuance\',800,550);";
//
//	} 

	//--------------供求------------------
	if(a_gongqiu.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../sad.do?method=toSadMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_gongqiu.length+"条农产品供求库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_gongqiu_bh.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../sad.do?method=toSadMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_gongqiu_bh.length+"条农产品供求库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_gongqiu_ys.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../sad.do?method=toSadMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_gongqiu_ys.length+"条农产品供求库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	//---------------价格-----------------
	if(a_jiage.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../operpriceinfo.do?method=toOperPriceinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiage.length+"条农产品价格库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_jiage_bh.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../operpriceinfo.do?method=toOperPriceinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiage_bh.length+"条农产品价格库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_jiage_ys.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../operpriceinfo.do?method=toOperPriceinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiage_ys.length+"条农产品价格库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}  
	//---------------问卷设计-----------------
	if(a_wenjuansheji.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../inquiry.do?method=toMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_wenjuansheji.length+"条调查问卷设计库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_wenjuansheji_bh.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../inquiry.do?method=toMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_wenjuansheji_bh.length+"条调查问卷设计库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_wenjuansheji_ys.length>0){v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../inquiry.do?method=toMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_wenjuansheji_ys.length+"条调查问卷设计库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	//---------企业---------------------
	if(a_qiye.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../operCorpinfo.do?method=toOperCorpinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_qiye.length+"条企业信息库 待审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_qiye_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../operCorpinfo.do?method=toOperCorpinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_qiye_bh.length+"条企业信息库 驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_qiye_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../operCorpinfo.do?method=toOperCorpinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_qiye_ys.length+"条企业信息库 已审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}

//---------普通医疗---------------------
	if(a_putongyiliao.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../medical/medicinfo.do?method=toMedicinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_putongyiliao.length+"条普通医疗服务 待审 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_putongyiliao_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../medical/medicinfo.do?method=toMedicinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_putongyiliao_bh.length+"条普通医疗服务 驳回 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_putongyiliao_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../medical/medicinfo.do?method=toMedicinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_putongyiliao_ys.length+"条普通医疗服务 已审 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	//---------预约医疗---------------------
	if(a_yuyueyiliao.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../medical/bookMedicinfo.do?method=toBookMedicinfoMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_yuyueyiliao.length+"条预约医疗服务 待审 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_yuyueyiliao_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../medical/bookMedicinfo.do?method=toBookMedicinfoMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_yuyueyiliao_bh.length+"条预约医疗服务 驳回 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_yuyueyiliao_ys.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../medical/bookMedicinfo.do?method=toBookMedicinfoMain&state=pass',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_yuyueyiliao_ys.length+"条预约医疗服务 已审 消息";//信息库
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	//---------市场分析---------------------	
	if(a_shichangfenxi_fc.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../markanainfo.do?method=toMarkanainfoMain&state=firstCensor',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_shichangfenxi_fc.length+"条市场分析库 一审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}	
	
	if(a_shichangfenxi_fcb.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../markanainfo.do?method=toMarkanainfoMain&state=firstCensorBack',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_shichangfenxi_fcb.length+"条市场分析库 一审驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_shichangfenxi_sc.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../markanainfo.do?method=toMarkanainfoMain&state=secondCensor',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_shichangfenxi_sc.length+"条市场分析库 二审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	if(a_shichangfenxi_scb.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../markanainfo.do?method=toMarkanainfoMain&state=secondCensorBack',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_shichangfenxi_scb.length+"条市场分析库 二审驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	//--------------焦点追踪---------------
	if(a_jiaodianzhuizong.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){			
			popUp('windows','../focusPursue.do?method=toFocusPursueMain&state=wait',800,550);
			me.hide();			
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodianzhuizong.length+"条焦点追踪库 一审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
 
	if(a_jiaodianzhuizong_bh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../focusPursue.do?method=toFocusPursueMain&state=back',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodianzhuizong_bh.length+"条焦点追踪库 一审驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	} 
	
	if(a_jiaodianzhuizong_es.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../focusPursue.do?method=toFocusPursueMain&state=waitagain',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodianzhuizong_es.length+"条焦点追踪库 二审 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}
	
	if(a_jiaodianzhuizong_esbh.length>0){
		v_height+=11;
		var addDiv = oPopup.document.createElement('DIV');
		
		addDiv.style.wordBreak ='break-all';
		addDiv.align='left';
		
		var addA = oPopup.document.createElement('A');
		addA.href='javascript:void(0)';
		addA.hidefocus='false';
		addA.onclick = function(){
			popUp('windows','../focusPursue.do?method=toFocusPursueMain&state=backagain',800,550);
			me.hide();
	    }
		
		var addFont = oPopup.document.createElement('FONT');
		addFont.color='#ff0000';
		addFont.innerText = "你有"+a_jiaodianzhuizong_esbh.length+"条焦点追踪库 二审驳回 消息";
		
		addA.appendChild(addFont);
		addDiv.appendChild(addA);
		v_divL.appendChild(addDiv);
	}	
	

//    ===========================================  

	var v_divM = oPopup.document.getElementById("divM").style.height = v_height+"px";
	me.height=v_height;
	oPopup.document.getElementById("spanL").innerHTML = "您有"+(v_height-105)/11+"封消息";//innerText
    var fun = function(){
        var x  = me.left; 
        var y  = 0; 
        var width    = me.width; 
        var height    = me.height; 

            if(me.offset>me.height){ 
                height = me.height; 
            } else { 
                height = me.offset; 
            } 

        y  = me.bottom - me.offset; 
        if(y<=me.top){ 
            me.timeout--; 
            if(me.timeout==0){ 
                window.clearInterval(me.timer);  
                if(me.autoHide){
                    me.hide(); 
                }
            } 
        } else { 
            me.offset = me.offset + me.step; 
        } 
        me.Pop.show(x,y,width,v_height);    

    }  
  
    this.timer = window.setInterval(fun,this.speed); 

  
    
    var btClose = oPopup.document.getElementById("btSysClose");  
  
    btClose.onclick = function(){  
        me.close = true;
        me.hide();  
    }  
  	 
}  

CLASS_MSN_MESSAGE_1.prototype.speed = function(s){ 
    var t = 20; 
    try { 
        t = praseInt(s); 
    } catch(e){} 
    this.speed = t; 
} 

CLASS_MSN_MESSAGE_1.prototype.step = function(s){ 
    var t = 1; 
    try { 
        t = praseInt(s); 
    } catch(e){} 
    this.step = t; 
} 
  
CLASS_MSN_MESSAGE_1.prototype.rect = function(left,right,top,bottom){ 
    try { 
        this.left        = left    !=null?left:this.right-this.width; 
        this.right        = right    !=null?right:this.left +this.width; 
        this.bottom        = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height; 
        this.top        = top    !=null?top:this.bottom - this.height; 
    } catch(e){} 
} 