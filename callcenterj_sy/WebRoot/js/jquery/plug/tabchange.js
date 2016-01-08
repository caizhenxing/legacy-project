var tab = null;
$( function() {
	newTab();
});
function newTab() {
	tab = new TabView( {
		containerId :'tab_menu',
		pageid :'page',
		cid :'tab_po',
		position :"top"
	});
	tab.add( {
		id :'tab1_id_index1',
		title :"来电用户信息",
		url :"../custinfo/custinfo.do?method=toCustinfoMain",
		isClosed :false
	});
	tab.add( {
		id :'tab2_id_index2',
		title :"服务记录查询",
		url :"../incomming/incommingInfo.do?method=toIncommingInfoMain",
		isClosed :false
	});
	tab.add( {
		id :'tab3_id_index3',
		title :"来电信息查询",
		url :"../callcenter/cclog/telQuery.do?method=toMain",
		isClosed :false
	});
	//指定标签的默认可激活状态
	tab.activate('tab1_id_index1');
	
}
//根据电话号码查询
function searchTel(){
	//得到传入的电话号
	var txtTel = document.getElementById("txtTelId").value;
	//详细查看页
	var id='tab5_id_index'+txtTel;
	var url='../custinfo/custinfo.do?method=toCustinfoMain&tel='+txtTel;
	var title='查看'+txtTel;
	
	tab.add({
		id :id,
		title:title,
		url:url,
		isClosed:true	
	}
	);
	tab.activate(id);
}
