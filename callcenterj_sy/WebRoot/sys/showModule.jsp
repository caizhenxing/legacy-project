
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=GBK" pageEncoding="GBK"%>



	<script type="text/javascript">
		var handle;
		function start()
		{
 			var obj = document.getElementById("tip");
 			if (parseInt(obj.style.height)==0)
 			{ obj.style.display="block";
  			handle = setInterval("changeH('up')",2);
 		}else
 		{
    		 handle = setInterval("changeH('down')",2) 
 			} 
		}
		function changeH(str)
		{
 			var obj = document.getElementById("tip");
 			if(str=="up")
 			{
  			if (parseInt(obj.style.height)>200)
   			clearInterval(handle);
  			else
   			obj.style.height=(parseInt(obj.style.height)+8).toString()+"px";
 		}
 		if(str=="down")
 		{
  		if (parseInt(obj.style.height)<8)
  		{ clearInterval(handle);
   			obj.style.display="none";
 		 }
  		else  
  		 obj.style.height=(parseInt(obj.style.height)-8).toString()+"px"; 
 		}
		}
		function showwin()
		{
			 document.getElementsByTagName("html")[0].style.overflow = "hidden";
 			start();
 			document.getElementById("shadow").style.display="block";
			 document.getElementById("detail").style.display="block"; 
		}
		function recover()
		{
 			document.getElementsByTagName("html")[0].style.overflow = "auto";
 			document.getElementById("shadow").style.display="none";
 			document.getElementById("detail").style.display="none";  
		}
	</script>


<body onload="document.getElementById('tip').style.height='0px'">

	<div id="shadow">
	</div>

	<div id="detail">
		<h1>
			<a href="javascript:void(0)" onclick="recover()">×</a>系统提示
		</h1>
		哈哈哈哈你～～～
	</div>

	<div id="tip">
		<h1>
			<a href="javascript:void(0)" onclick="start()">×</a>系统提示
		</h1>
		<p>
			<a href="javascript:void(0)" onclick="showwin()">点击这里查看详细</a>
		</p>
	</div>

	<p>
		<a href="javascript:void(0)" onclick="start()">点击这里测试</a>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
	<p>
	</p>
</body>
