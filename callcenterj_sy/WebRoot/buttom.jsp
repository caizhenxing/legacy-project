<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>null</title>
<script language="javascript">
var xmlHttp;
function createXMLHttpRequest()
{
 if(window.ActiveXObject)
 {
  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
 }
 else if(window.XMLHttpRequest)
 {
  xmlHttp=new XMLHttpRequest();
 }
}
function createQueryString()
{
 var firstName=document.getElementById("firstname").value;
 var middleName=document.getElementById("middleName").value;
 var birthday=document.getElementById("birthday").value;
 var queryString="firstName=" + firstName + "&middleName=" + middleName + "&birthday=" + birthday;
 return queryString;
}
function doRequestUsingGET()
{
 createXMLHttpRequest();
 var queryString="GetAndPost?";
 queryString=queryString+createQueryString() + "&timeStamp=" + new Date().getTime();
 xmlHttp.onreadystatechange=handleStateChange;
 xmlHttp.open("GET",queryString,true);
 xmlHttp.send(null);
}
function doRequestUsingPost()
{
 createXMLHttpRequest();
 var url="GetAndPost?timeStamp=" + new Date().getTime();
 var queryString=createQueryString();
 xmlHttp.open("POST",url,true);
 xmlHttp.onreadystatechange=handleStateChange;
 xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
 xmlHttp.send(queryString);
}
function handleStateChange()
{
 if(xmlHttp.readyState==4)
 {
  if(xmlHttp.status==200)
  {
  	alert('123ok');
   parseResults();
  }
 }
}
function parseResults()
{
 var responseDiv=document.getElementById("serverResponse");
 if(responseDiv.hasChildNodes())
 {
  responseDiv.removeChild(responseDiv.childNodes[0]);
 }
 var responseText=document.createTextNode(xmlHttp.responseText);
 responseDiv.appendChild(responseText);
}
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="#">
  <p><br />
    <br />
    <input name="firstName" type="text" id="firstName" />
</p>
  <p>
    <label>
    <input type="text" name="middleName" id="middleName"  />
    </label>
</p>
  <p>
    <input name="birthday" type="text" id="birthday" />
  </p>
  <p>&nbsp;</p>
  <p>
    <input type="button" name="Submit" value="GET"  onclick="doRequestUsingGET();"/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <input type="button" name="Submit2" value="POST"  onclick="doRequestUsingPost();"/>
  </p>

  <div id="serverResponse"></div>
</form>

</body>
</html>


