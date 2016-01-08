
function addOption2Select(id)
{
	var slt = document.getElementById(id);
	var res_db = _xmlHttpRequestObj.responseText;
	//alert(res_db);
	//清空slt
	var length = slt.length;
	if(length>0)
	{
		for(var i=1; i<length; i++)
		{
			slt.options[1] = null;
		}
	}
	var sArr = res_db.split(":");
	for(var i=0; i<sArr.length; i++)
	{
		//增加一个option
		//alert(slt+":"+sArr[i]+":"+id);
		slt.options[slt.length] = new Option(sArr[i], sArr[i]);
	}
}
