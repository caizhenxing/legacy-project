/**
 * author 王文权
*/
//清空table里的所有tr 将传入的字符串解析加入table里
function parseAddTrs2Table(id,doWithStrs,oneStyleName,twoStyleName)
{
	var _t=document.getElementById(id);
	if(_t)
	{

		//清空表单
		clearRowsFromTbl(_t);
		var trs_db = doWithStrs;
		//行以@分隔
		var trs = trs_db.split("@");

		for(var i=0; i<trs.length; i++)
		{
			_t.insertRow(i+1);
			//cell以|分隔
			var tds_db = trs[i].split("|");
			var l = tds_db.length;
			for(var j=0; j<l; j++)
			{
				_t.rows[i+1].insertCell(j); //*
				var _tn = document.createTextNode(tds_db[j]);
				_t.rows[i+1].cells[j].appendChild(_tn); //*
				if(oneStyleName!=0)
				{
					if((i+1)%2!=0)
					_t.rows[i+1].cells[j].className=oneStyleName; //*
				}
				if(twoStyleName!=0)	
				{
					_t.rows[i+1].cells[j].className=twoStyleName; //*
				}
			}
		}
		if(_t.rows.length>0)
		{
			_t.deleteRow(0);
		}
	}
	else
	{
		alert('所传id:'+id+' 对应的表格不存在!');
	}
}

function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}
function clearRowsFromTbl(oTbl)
{
	if(oTbl)
	{
		var length = oTbl.rows.length;
		while((length-1)>0)
		{
			oTbl.deleteRow(length-1);
			length = oTbl.rows.length;
		}
	}
}
function parseTbl(tblId,sName,basePath)
{
	var rCells = '';
	var oTbl = document.getElementById(tblId);
	if(oTbl)
	{
		var _rows = oTbl.rows;
		var rLength = _rows.length;
		for(var i=0; i<rLength; i++)
		{
			var cells = _rows[i].cells;
			var cLength = cells.length;
			for(var j=0; j<cLength; j++)
			{
				var cellV = cells[j].innerText;
				//不支持innerText
				if(!cells[j].innerText)
				{
					//是否支持dom
					var cns = cells[j].childNodes;
					if(cns&&cns.length>0)
					{
						cellV = cns[0].nodeValue;
					}
					else
					{
						//innerHTML
						cellV = cells[j].innerHTML;
					}
				}
				cellV = cTrim(cellV,0);
				if(j>0)
				{
					rCells = rCells + '@' + cellV;
				}
				else if(j==0)
				{
					rCells = rCells + cellV;
				}
				
				if((j == (cLength-1))&&i!=(rLength-1))
				{
					rCells = rCells+'|';
				}		
			}
		}
	}
	/*
	if(!window.frames['frame-if'])
	{
		createIFrame('if',basePath+'test/commit.jsp');
	}
	*/
	window.frames['frame-if'].document.getElementById('hiddenV').value=rCells;
	window.frames['frame-if'].document.getElementById('sheetName').value=sName;
	
	window.frames['frame-if'].document.getElementById('hiddenBtn').click();
	//hiddenBtn
}

// Code by Yahao
// Copyright by YAHAO Studio & 清水万维工作室
// Date: 2000-6-14
// Description: sInputString 为输入字符串，iType为类型，分别为
// 0 - 去除前后空格; 1 - 去前导空格; 2 - 去尾部空格
function cTrim(sInputString,iType)
{
	var sTmpStr = ' ';
	var i = -1;
	
	if(iType == 0 || iType == 1)
	{
		while(sTmpStr == ' ')
		{
			++i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(i);
	}
	
	if(iType == 0 || iType == 2)
	{
		sTmpStr = ' ';
		i = sInputString.length;
		while(sTmpStr == ' ')
		{
			--i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(0,i+1);
	}
	return sInputString
}
/**
* name=frame-id
* frame url = url
*/
function createIFrame(id,url) {
var newPanle = document.createElement("div");
		newPanle.setAttribute("id","panel-"+id);
		var newFrame = document.createElement("iframe");
		newFrame.setAttribute("name","frame-"+id);
		newFrame.setAttribute("id","frame-"+id);
		newFrame.setAttribute("src",url);
		newFrame.width = "100%";
		newFrame.scrolling = "no";
		newFrame.frameBorder = 0;
		newPanle.appendChild(newFrame);
		document.body.appendChild(newPanle);
		newPanle.style.setAttribute("display","none");
}

 function convert(sValue, sDataType) {
        switch(sDataType) {
            case "int":
                return parseInt(sValue);
            case "float":
                return parseFloat(sValue);
            case "date":
                return new Date(Date.parse(sValue));
            default:
                return sValue.toString();
        
        }
}

function generateCompareTRs(iCol, sDataType) {

    return  function compareTRs(oTR1, oTR2) {
                var vValue1, vValue2;

                if (oTR1.cells[iCol].getAttribute("value")) {
                    vValue1 = convert(oTR1.cells[iCol].getAttribute("value"),
                                  sDataType);
                    vValue2 = convert(oTR2.cells[iCol].getAttribute("value"),
                                  sDataType);
                } else {
                    vValue1 = convert(oTR1.cells[iCol].firstChild.nodeValue,
                                  sDataType);
                    vValue2 = convert(oTR2.cells[iCol].firstChild.nodeValue,
                                  sDataType);
                }

                if (vValue1 < vValue2) {
                    return -1;
                } else if (vValue1 > vValue2) {
                    return 1;
                } else {
                    return 0;
                }
            };
}
   
function sortTable(sTableID, iCol, sDataType) {
    var oTable = document.getElementById(sTableID);
    var oTBody = oTable.tBodies[0];
    var colDataRows = oTBody.rows;
    var aTRs = new Array;

    for (var i=0; i < colDataRows.length; i++) {
        aTRs[i] = colDataRows[i];
    }

    if (oTable.sortCol == iCol) {
        aTRs.reverse();
    } else {
        aTRs.sort(generateCompareTRs(iCol, sDataType));
    }

    var oFragment = document.createDocumentFragment();
    for (var i=0; i < aTRs.length; i++) {
        oFragment.appendChild(aTRs[i]);
    }

    oTBody.appendChild(oFragment);
    oTable.sortCol = iCol;
}