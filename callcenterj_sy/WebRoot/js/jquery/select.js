if($.browser.msie)
$(document).ready(function(){
	//��ÿһ��select��������
	$("select").each(function(){
		select_name = $(this).attr("name");
		//ȡ��select�ĸ߶�
		width = $(this).width();
		//ȡ��input�Ŀ��
		input_width = width-17;
		//ȡ�þ�������
		offset = $(this).offset();
		//�����Ĵ���
		select_html = '<div style="width:'+width+'px;position:absolute;left:'+offset.left+'px;top:'+offset.top+'px"><input type="text" style="width:'+input_width+'px;" autocomlete="off" readonly="true" id="input_'+select_name+'" class="select_input"/><img id="img_'+select_name+'" name="'+select_name+'" class="select_img" src="../../images/s.gif" width="17" height="21"></div>';
		//���ӵ�ҳ����
		$("body").append(select_html);
		$(this).css({visibility:"hidden"});
		//$(this).css("margin","200px");
		//ȡ��option������
		option_num = $("option",$(this)).length;
		//option�ĸ߶�
		option_height = option_num * 21 > 200 ? 200 : option_num*21;
		//option�Ŀ��
		option_width = width;
		//option������
		option_left = offset.left;
		option_top = offset.top+21;
		//�����˵�����������
		option_html = "<div class='select_option_div' id=option_"+select_name+" style='height:"+option_height+"px;width:"+option_width+"px;position:absolute;top:"+option_top+"px;left:"+option_left+"px;overflow:auto'>";
		$(this).find("option").each(function(){
			option_html += "<div class='select_option'>"+$(this).text()+"</div>";
		});
		option_html += "</div>";
		//���ӵ�ҳ����
		$("body").append(option_html);
		//����ѡ��
		$("#option_"+select_name).hide();
		//�趨img id �� input id
		img_id = "#img_"+select_name;
		input_id = "#input_"+select_name;
		//�趨ͼƬ����¼�
		$(img_id).click(function(){
			//ͨ��nameȡ��Ŀ��id
			dest_id = "#option_"+$(this).attr("name");
			//ȡ��ѡ���״̬�Ǵ򿪻��ǹر�
			state = $(dest_id).css("display");
			//�ر����е�ѡ��
			$(".select_option_div").hide();
			//���Ĺأ��صĿ�
			if(state == "none"){
				$(dest_id).show();
			}
			else{
				$(dest_id).hide();
			}
		});
		//input�ĵ���¼�
		$(input_id).click(function(){
			//ȡ��Ŀ��id
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
		//����Ĭ��ѡ�е���
		obj = document.getElementById(select_name);
		input_id = "#input_"+select_name;
		$(input_id).val(obj.options[obj.selectedIndex].text);
	});
	//�����ѡ���Ĳ���
	$(".select_option").click(function(){
		//ȡ��select id
		parent_id = $(this).parent().attr("id");
		parent_id = parent_id.substr(7);
		input_id = "#input_"+parent_id;
		//��input����ʾ��ѡ��
		$(input_id).val($(this).text());
		//����selectѡ��
		obj = document.getElementById(parent_id);
		obj.options[obj.selectedIndex].text=$(this).text();
		option_id = "#option_"+parent_id;
		//ѡ��֮������ѡ��
		$(option_id).hide();
	});
	//��optionѡ��Ӧ����������Ƴ�Ч��
	$(".select_option").hover(function(){$(this).addClass("select_highlight")},function(){$(this).removeClass("select_highlight")});
});
//���ҳ�溯��
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
