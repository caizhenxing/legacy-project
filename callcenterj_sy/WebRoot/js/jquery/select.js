if($.browser.msie)
$(document).ready(function(){
	//对每一个select进行美化
	$("select").each(function(){
		select_name = $(this).attr("name");
		//取得select的高度
		width = $(this).width();
		//取得input的宽度
		input_width = width-17;
		//取得绝对坐标
		offset = $(this).offset();
		//美化的代码
		select_html = '<div style="width:'+width+'px;position:absolute;left:'+offset.left+'px;top:'+offset.top+'px"><input type="text" style="width:'+input_width+'px;" autocomlete="off" readonly="true" id="input_'+select_name+'" class="select_input"/><img id="img_'+select_name+'" name="'+select_name+'" class="select_img" src="../../images/s.gif" width="17" height="21"></div>';
		//附加到页面上
		$("body").append(select_html);
		$(this).css({visibility:"hidden"});
		//$(this).css("margin","200px");
		//取得option的数量
		option_num = $("option",$(this)).length;
		//option的高度
		option_height = option_num * 21 > 200 ? 200 : option_num*21;
		//option的宽度
		option_width = width;
		//option的坐标
		option_left = offset.left;
		option_top = offset.top+21;
		//下拉菜单的美化代码
		option_html = "<div class='select_option_div' id=option_"+select_name+" style='height:"+option_height+"px;width:"+option_width+"px;position:absolute;top:"+option_top+"px;left:"+option_left+"px;overflow:auto'>";
		$(this).find("option").each(function(){
			option_html += "<div class='select_option'>"+$(this).text()+"</div>";
		});
		option_html += "</div>";
		//附加到页面上
		$("body").append(option_html);
		//隐藏选项
		$("#option_"+select_name).hide();
		//设定img id 和 input id
		img_id = "#img_"+select_name;
		input_id = "#input_"+select_name;
		//设定图片点击事件
		$(img_id).click(function(){
			//通过name取得目标id
			dest_id = "#option_"+$(this).attr("name");
			//取得选项的状态是打开还是关闭
			state = $(dest_id).css("display");
			//关闭所有的选项
			$(".select_option_div").hide();
			//开的关，关的开
			if(state == "none"){
				$(dest_id).show();
			}
			else{
				$(dest_id).hide();
			}
		});
		//input的点击事件
		$(input_id).click(function(){
			//取得目标id
			dest_id = $(this).attr("id").substr(6);
			dest_id = "#option_"+dest_id;
			state = $(dest_id).css("display");
			$(".select_option_div").hide();
			if(state == "none"){
				$(dest_id).show();
				//alert("hello");
			}
			else{
				$(dest_id).hide();
			}
		});
		//设置默认选中的项
		obj = document.getElementById(select_name);
		input_id = "#input_"+select_name;
		$(input_id).val(obj.options[obj.selectedIndex].text);
	});
	//当点击选项后的操作
	$(".select_option").click(function(){
		//取得select id
		parent_id = $(this).parent().attr("id");
		parent_id = parent_id.substr(7);
		input_id = "#input_"+parent_id;
		//在input处显示所选项
		$(input_id).val($(this).text());
		//操作select选项
		obj = document.getElementById(parent_id);
		obj.options[obj.selectedIndex].text=$(this).text();
		option_id = "#option_"+parent_id;
		//选中之后隐藏选项
		$(option_id).hide();
	});
	//对option选项应用鼠标移入移出效果
	$(".select_option").hover(function(){$(this).addClass("select_highlight")},function(){$(this).removeClass("select_highlight")});
});
//点击页面函数
$(document).click(function(evt){
	var evt = evt || window.event; // event object
	var target = evt.target || window.event.srcElement; // event target
	var t_className = target.className; // event target id
	if(t_className == "select_img" || t_className == "select_input" || t_className == "select_option"){
		return false;
	}
	else{
	$(".select_option_div").hide();
	};
});
