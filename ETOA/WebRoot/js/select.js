function select(obj)
    {

		var page = "sys/dep.do?method=select&value="+obj.value
		var winFeatures = "dialogWidth:600px; dialogHeight:600px;center:1; status:0";
	

		window.showModalDialog(page,obj,winFeatures);

	}
	function appendaa(obj)
    {
		var page = "plan/append.jsp"
		var winFeatures = "dialogHeight:500px; dialogLeft:200px;";
		
		
		window.showModalDialog(page,obj,winFeatures);
	}
	function openwin(param)
		{
		   var aResult = showCalDialog(param);
		   if (aResult != null)
		   {
		     param.value  = aResult;
		   }
		}
		
		function showCalDialog(param)
		{
		   alert("showCalDialog............");
		   var url="html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:800px;dialogHeight:800px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}