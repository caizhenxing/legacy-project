<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>FusionCharts v3 Documentation</title>
<link rel="stylesheet" href="./style/fusion/Style.css" type="text/css" />

<script language="JavaScript" src="./js/jquery/jquery-1.3.1.min.js"></script>
<script language="JavaScript" src="./js/jquery/plug/jquery.blockUI.js"></script>

<script language="JavaScript" src="./js/fusion/FusionCharts.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		//$.blockUI({ message: '<div>歡迎來到 jsGears.com !</div>'}); 
	
	});
	function   getLeft(div)   
	  {   
	          var   left   =   0;   
	          while(div!=   null)   
	          {   
	                  left   +=   div.offsetLeft;   
	                  div=   div.offsetParent;     
	          }     
	          return   left;     
	  }  
	  	function   getTop(div)   
	  {   
	          var   top   =   0;   
	          while(div!=   null)   
	          {   
	                  top   +=   div.offsetTop;   
	                  div=   div.offsetParent;     
	          }             
	          return   top;     
	  } 
	  function setPosition()
	  {
	  	var div=document.getElementById('chartdiv');
	  	var coverTopDiv = document.getElementById('coverTopDiv');
	  	coverTopDiv.style.top=getTop(div);
	  	coverTopDiv.style.left=getLeft(div);
	  } 
	  function changePosition()
	  {
	  	setPosition();
	  }
</script>
</head>

<body onload="setPosition()" onresize="changePosition()"><br><br><br><br><br><br><br><br><br><br><br>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
  <tr> 
    <td valign="top" class="text" align="center"> 
	    <div id="chartdiv" align="center" style="background-color:blue;width:800px;"></div>
	    <div id="coverTopDiv"  style="border-top-width:0px;border-bottom-width:0px; border-right-width:1px;border-left-width:1px; border-right-color:#A3A4A1;border-left-color:#A3A4A1; border-style:solid;z-index:999;padding:0px;position:absolute;margin-top:-2px;width: 400px;height:20;top:0;left:0;background-color:red;background:#cccccc   url(../../../images/chart02.jpg);"></div>
	      <script type="text/javascript">
			   var chart = new FusionCharts("./css/charts/Column2D.swf", "ChartId", "800", "450", "0", "1");
			   chart.setDataURL("./dataxml/Column2D.xml");		   
			   chart.render("chartdiv");
			</script> 
	</td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
</table>
</body>
</html>