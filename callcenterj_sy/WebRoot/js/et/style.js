/**
  * zhangfeng add modify css style
  */

//隔行换色
$(document).ready(function(){
		
		//得到表格的总列数
		var rowLength = $('table.listTable tr').size();
		
		$('table.listTable tr').mouseover(function(){
			$(this).addClass('over');}).mouseout(function(){
				$(this).removeClass('over');
			});
		//隔行换色
		$('table.listTable tr').each(function(index){
			var rowIndex = index%2;
			if(index==0){//如果是标题头，加上标题样式
				$(this).addClass('listTitleStylebiaoti');
			}else if(index==rowLength-1){//如果等于最后一行
				
			}else{//隔行换色
				if(rowIndex==1){
					$(this).addClass('evenStyledanhang');
				}else{
					$(this).addClass('oddStylesecond');
				}
			}
		});
	});