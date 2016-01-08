function select(obj)
    {
		var page = "sys/dep.do?method=select&value="+obj.value
		var winFeatures = "dialogHeight:500px; dialogLeft:500px;";
		
		
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
		   var url="html/calendar.html";
		   var aRetVal = showModalDialog(url,"status=no","dialogWidth:215px;dialogHeight:238px;status:no;");
		   if (aRetVal != null)
		   {
		      return aRetVal;
		   }
		   return null;
		}