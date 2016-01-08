var intervalLength = 3000;
       var handleWin = '-1';
       var handleShowWin = '-1';
       var handlerHiddenWin = '-1';
       var curInfoId = '';
       var bPath = '';
       var userId = '';
       function getObj(id)
	{
		return document.getElementById(id);
	}
       function resetInterval(timeLength,path,uId)
       {
        bPath = path;
   		userId = uId;
       	if(handleWin!='-1')
       	{
       		window.clearInterval(handleWin);
       		handleWin = -1;
       	}
       	intervalLength = timeLength;
       	handleWin = intervalRun();
       }
   	function getMsg()
   	{
   	    var url = bPath+'./servlet/msgLoopServlet?receiveMan='+userId;
   		postXmlHttp(url,"showMsg()",'doNo()');
   	}
   	function showMsg()
   	{
   		var res_db = _xmlHttpRequestObj.responseText;
   		if(res_db != 'noMsg')
   		{
   			var infos = res_db.split('[#^%#]');
   			if(curInfoId != infos[0])
   			{
   				curInfoId = infos[0];
   				intervalShowWin();
   				getObj('curInfoContent').innerHTML=infos[1];
   				getObj('infoNum').innerHTML=infos[2];
   			}
   		}
   	}
   	function intervalRun()
   	{
  
   		handleWin = window.setInterval("getMsg()",intervalLength);
   		return handleWin;
   	}
   	function intervalShowWin()
   	{
   		handleShowWin = window.setInterval("showMsgWin()",200);
   	}
   	function addBottom(addHeight)
   	{
   		var curBottom = parseInt(getObj('showMsg').style.height);
   		
   		if(isNaN(curBottom))
   		{
   			curBottom = 0;
   		}
   		if(curBottom < 200)
   		{
   		    getObj('closeWin').disabled = true; 
   			getObj('showMsg').style.height  =  curBottom + addHeight;
   		}
   		else
   		{
   			getObj('showMsg').style.height = 200;
   			getObj('closeWin').disabled = false; 
   			if(handleShowWin!='-1')
   			{
       			window.clearInterval(handleShowWin);
       			handleShowWin = -1;
       		}
   		}
   	}
   	function subBottom(subHeight)
   	{
   		var curBottom = parseInt(getObj('showMsg').style.height);
   		if(isNaN(curBottom))
   		{
   			curBottom = 0;
   		}
   		if(curBottom > 0)
   		{
   			getObj('showMsg').style.height  =  curBottom - subHeight;
   			if(curBottom - subHeight <50)
   			{
   				getObj('infoTbl').style.display="none";
   			}
   		}
   		else
   		{
   			
   		    getObj('showMsg').style.height = 0;
   		    
   			if(handlerHiddenWin!='-1')
   			{
       			window.clearInterval(handlerHiddenWin);
       			handlerHiddenWin = -1;
       		}
   		}
   	}
   	function showMsgWin()
   	{
   		getObj('infoTbl').style.display="inline";
           addBottom(20);
   	}
   	function hiddenMsgWin()
   	{
   		subBottom(20);
   	}
   	function closeWin()
   	{
   	   getObj('closeWin').disabled = true; 
   	   
   	   if(handleShowWin != -1)
   	   {
  	   			window.clearInterval(handleShowWin);
      			handleShowWin = -1;
   	   }
   	   handlerHiddenWin = window.setInterval("hiddenMsgWin()",200);
   	}