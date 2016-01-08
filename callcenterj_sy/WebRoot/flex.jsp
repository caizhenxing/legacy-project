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
<%--			{display:'��������Ϣ',name:'sendId',width:40,sortable:true,align:'center'},--%>
<%--			{display:'����������',name:'sendName',width:120,sortable:true,align:'left'},--%>
<%--			{display:'������id',name:'receiveId',width:40,sortable:true,align:'center'},--%>
<%--			{display:'��Ϣ����',name:'messageContent',width:400,sortable:true,align:'center'},--%>
<%--			{display:'����ʱ��',name:'sendTime',width:80,sortable:true,align:'left'}--%>
<%--		],--%>
<%--		searchitems:[--%>
<%--			{display:'����������',name:'sendName'},--%>
<%--			{display:'������id',name:'receiveId',isdefault:true}--%>
<%--		],--%>
<%--		sortname:'receiveId',--%>
<%--		sortorder:'asc',--%>
<%--		usepager:true,--%>
<%--		title:'����Ϣ����ϵͳ',--%>
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
				{display:'��������Ϣ',name:'sendId',width:40,sortable:true,align:'center'},
				{display:'����������',name:'sendName',width:120,sortable:true,align:'left'},
				{display:'������id',name:'receiveId',width:40,sortable:true,align:'center'},
				{display:'��Ϣ����',name:'messageContent',width:400,sortable:true,align:'center'}
				],
			buttons : [
				{name: 'Add', bclass: 'add', onpress : test},
				{name: 'Delete', bclass: 'delete', onpress : test},
				{separator: true}
				],
			searchitems : [
			{display:'����������',name:'sendName'},
			{display:'������id',name:'receiveId',isdefault:true}
				],
			sortname:'receiveId',
			sortorder:'asc',
			usepager: true,
			title: '����Ϣ����ϵͳ',
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
