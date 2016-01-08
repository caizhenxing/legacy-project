<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<!-- import flexgrid -->
<%--<link rel="stylesheet" type="text/css" href="./css/flexgrid/style.css"/>--%>
<link rel="stylesheet" type="text/css" href="./css/flexgrid/flexigrid/flexigrid.css"/>
<script type="text/javascript" src="./js/jquery/jquery.js"></script>
<script type="text/javascript" src="./js/jquery/plug/flexigrid.js"></script>

<style>

	body
		{
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		}
		
	.flexigrid div.fbutton .add
		{
			background: url(css/flexgrid/images/add.png) no-repeat center left;
		}	

	.flexigrid div.fbutton .delete
		{
			background: url(css/flexgrid/images/close.png) no-repeat center left;
		}	

		
</style>

<head>
	<title>flexgrid</title>
</head>
<body>

<!-- 
	private String messageId;

	private String sendId;

	private String sendName;

	private String receiveId;

	private String messageContent;

	private Date sendTime;
 -->

<%--<table id="flex1" style="display:none"></table>--%>
<%--<script type="text/javascript">--%>
<%--	$('#flex1').flexgrid({--%>
<%--		url:'',--%>
<%--		dataType:'json',--%>
<%--		colModel:[--%>
<%--			{display:'发送人信息',name:'sendId',width:40,sortable:true,align:'center'},--%>
<%--			{display:'发送人姓名',name:'sendName',width:120,sortable:true,align:'left'},--%>
<%--			{display:'接收人id',name:'receiveId',width:40,sortable:true,align:'center'},--%>
<%--			{display:'信息内容',name:'messageContent',width:400,sortable:true,align:'center'},--%>
<%--			{display:'发送时间',name:'sendTime',width:80,sortable:true,align:'left'}--%>
<%--		],--%>
<%--		searchitems:[--%>
<%--			{display:'发送人姓名',name:'sendName'},--%>
<%--			{display:'接收人id',name:'receiveId',isdefault:true}--%>
<%--		],--%>
<%--		sortname:'receiveId',--%>
<%--		sortorder:'asc',--%>
<%--		usepager:true,--%>
<%--		title:'短消息管理系统',--%>
<%--		userRp:true,--%>
<%--		rp:15,--%>
<%--		showTableToggleBtn:true,--%>
<%--		width:800,--%>
<%--		onSubmit:addFormData,--%>
<%--		height:200--%>
<%--	}--%>
<%--	);--%>
	

<table id="flex1" style="display: none"></table>
		<script type="text/javascript">
			$("#flex1").flexigrid
			(
			{
			url: 'screen/screen.do?method=flexData',
			dataType: 'json',
			colModel : [
				{display:'发送人信息',name:'sendId',width:40,sortable:true,align:'center'},
				{display:'发送人姓名',name:'sendName',width:120,sortable:true,align:'left'},
				{display:'接收人id',name:'receiveId',width:40,sortable:true,align:'center'},
				{display:'信息内容',name:'messageContent',width:400,sortable:true,align:'center'}
				],
			buttons : [
				{name: 'Add', bclass: 'add', onpress : test},
				{name: 'Delete', bclass: 'delete', onpress : test},
				{separator: true}
				],
			searchitems : [
			{display:'发送人姓名',name:'sendName'},
			{display:'接收人id',name:'receiveId',isdefault:true}
				],
			sortname:'receiveId',
			sortorder:'asc',
			usepager: true,
			title: '短消息管理系统',
			useRp: true,
			rp: 15,
			showTableToggleBtn: true,
			width: 700,
			height: 200
			}
			);
			
			function test(com,grid)
			{
				if (com=='Delete')
					{
						confirm('Delete ' + $('.trSelected',grid).length + ' items?')
					}
				else if (com=='Add')
					{
						alert('Add New Item');
					}			
			}

</script>
	
</body>
</html>
