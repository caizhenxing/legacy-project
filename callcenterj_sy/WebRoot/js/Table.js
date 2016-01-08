i/**
 * author ÍõÎÄÈ¨
*/
//Çå¿ÕtableÀïµÄËùÓÐtr ½«´«ÈëµÄ×Ö·û´®½âÎö¼ÓÈëtableÀï
function parseAddTrs2Table(id,doWithStrs)
{
	var _t=document.getElementById(id);
	if(_t)
	{
		//Çå¿Õ±íµ¥
		clearRowsFromTbl(_t);
		var trs_db = doWithStrs;
		//ÐÐÒÔ@·Ö¸ô
		var trs = trs_db.split("@");
		if(trs.indexOf("@")!=-1)
		{
			for(var i=0; i<trs.length; i++)
			{
				_t.insertRow(i+1);
				//cellÒÔ|·Ö¸ô
				var tds_db = trs[i].split("|");
				var l = tds_db.length;
				for(var j=0; j<l; j++)
				{
					_t.rows(i+1).insertCell(j);
					var _tn = document.createTextNode(tds_db[j]);
					_t.rows(i+1).cells(j).appendChild(_tn);
				}
			}
		}
	}
	else
	{
		alert('Ëù´«id:'+id+' ¶ÔÓ¦µÄ±í¸ñ²»´æÔÚ!');
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
//	if(oTbl)
//	{
//		var goOn = true;
//		var length = oTbl.rows.length;
//		while((length-1)>1)
//		{
//			oTbl.removeNode(length-1);
//			length = oTbl.rows.length;
//			alert(length);
//		}
//	}
}

function parseTblSrc(tblId,sName,basePath)
{
	var rCells = '';
	var arr = new Array();
	var oTbl = document.getElementById(tblId);
	if(oTbl)
	{
		var _rows = oTbl.rows;
		var rLength = _rows.length;
		//ѭ��ÿһ��
		for(var i=0; i<rLength; i++)
		{
			var cells = _rows[i].cells;
			var cLength = cells.length;
			//ѭ��������
			for(var j=0; j<cLength; j++)
			{
				var cellV = cells[j].innerText;
				//²»Ö§³ÖinnerText
				if(!cells[j].innerText)
				{
					//ÊÇ·ñÖ§³Ödom
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
					//rCells = rCells + '@' + cellV;
					arr[cLength*i+j]='@'+cellV;
				}
				else if(j==0)
				{
					//rCells = rCells + cellV;
					arr[cLength*i+j]=cellV;
				}
				
				if((j == (cLength-1))&&i!=(rLength-1))
				{
					//rCells = rCells+'|';
					arr[cLength*i+j]=arr[cLength*i+j]+'|';
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

	//window.frames['frame-if'].document.getElementById('hiddenV').value=rCells;
	window.frames['frame-if'].document.getElementById('hiddenV').value=arr.join("");
	window.frames['frame-if'].document.getElementById('sheetName').value=sName;
	
	window.frames['frame-if'].document.getElementById('hiddenBtn').click();
	arr = null;
	//hiddenBtn
}
function createBodyIFrame()
{
	createIFrame('if',basePath+'statExcel/commit.jsp');
}
//addLoadEvent(createBodyIFrame);
// Code by Yahao
// Copyright by YAHAO Studio & ÇåË®ÍòÎ¬¹¤×÷ÊÒ
// Date: 2000-6-14
// Description: sInputString ÎªÊäÈë×Ö·û´®£¬iTypeÎªÀàÐÍ£¬·Ö±ðÎª
// 0 - È¥³ýÇ°ºó¿Õ¸ñ; 1 - È¥Ç°µ¼¿Õ¸ñ; 2 - È¥Î²²¿¿Õ¸ñ
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

function getSumCol(beginRowIndex,sumColIndex,tblId)
{
	var oTbl = document.getElementById(tblId);
	if(oTbl)
	{
		var sumNum = 0;
		var rows = oTbl.rows;
		if(rows.length>1&&rows.length>beginRowIndex)
		{	
			for(var i=beginRowIndex; i<(rows.length-1); i++)
			{
				var row = rows[i];
				var cols = row.cells;
				if(cols.length>sumColIndex)
				{
					var curV = cols[sumColIndex].innerText;
					if(typeof(curV ) == "undefined")
					{
						curV = 0;
					}
					if(isNaN(curV))
					{
						curV = 0;
					}
					sumNum += curV*1;
				}
			}
			return sumNum
		}

	}
	return 0;
}
/**
* sumCols 表格汇�1�7�的刄1�7 0,1,2,3,4,5
* tblId   表格id
*/
function sumTblRowCol(tblId)
{
	var oTbl = document.getElementById(tblId);
	var arrs = new Array(100);
	//init
	for(var i=0; i<100; i++)
	{
		arrs[i]=0;
	}
	if(oTbl)
	{
		var sumRowNum = 0; //行汇怄1�7
		var allSumNum = 0; //扄1�7有�1�7�1�7
		var rows = oTbl.rows;
		if(rows.length>1)
		{	
			//朄1�7后一行做汇�1�7�1�7
			for(var i=1; i<(rows.length-1); i++)
			{
				var row = rows[i];
				var cols = row.cells;
				var sumRowNum = 0;
				//朄1�7后一列做汇�1�7�1�7
				for(var j=0; j<(cols.length-1);j++)
				{
					arrs[j] = parseInt(arrs[j])+parseInt(cols[j].innerText);
					sumRowNum+=parseInt(cols[j]);
				}
				cols[cols.length-1].innerText=sumRowNum;
				allSumNum+=sumRowNum;
			}
			var lastCols = rows[rows.length-1].cells;
			for(var j=0; j<(lastCols.length-1);j++)
			{
				lastCols[j]=arrs[j];
			}
			lastCols[lastCols.length-1]=allSumNum;
		}

	}
}
//对table行列做统讄1�7
function sumTblRowCol(tblId)
{
	var oTbl = document.getElementById(tblId);
	var arrs = new Array(100);
	//init
	for(var i=0; i<100; i++)
	{
		arrs[i]=0;
	}
	if(oTbl)
	{
		var sumRowNum = 0; //行汇怄1�7
		var allSumNum = 0; //扄1�7有�1�7�1�7
		var rows = oTbl.rows;
		if(rows.length>1)
		{	
			//朄1�7后一行做汇�1�7�1�7
			for(var i=1; i<(rows.length-1); i++)
			{
				var row = rows[i];
				var cols = row.cells;
				var sumRowNum = 0;
				//朄1�7后一列做汇�1�7�1�7
				for(var j=0; j<(cols.length-1);j++)
				{
					arrs[j] = arrs[j]+cols[j].innerText*1;
					sumRowNum=sumRowNum*1+cols[j].innerText*1;
				}
				cols[cols.length-1].innerText=sumRowNum;
				allSumNum+=sumRowNum;
			}
			var lastCols = rows[rows.length-1].cells;
			for(var j=0; j<(lastCols.length-1);j++)
			{
				lastCols[j].innerText=arrs[j];
			}
			lastCols[lastCols.length-1].innerText=allSumNum;
		}

	}
}


function ConvertToExcel(tableID, rowStart ,colStart){
  var xlApp,myWorkbook,myWordsheet,myHTMLTableCell,myExcelCell,myExcelCell2;
  var myCellColSpan,myCellRowSpan;
  try{
    xlApp=new ActiveXObject("Excel.Application");
  }
  catch(e){
    alert("无法启动Excel!");
    return false;
  }
  xlApp.Visible=true;
  myWorkbook=xlApp.Workbooks.Add();
  xlApp.DisplayAlerts=false;
  myWorkbook.Worksheets(3).Delete();
  myWorkbook.Worksheets(2).Delete();
  xlApp.DisplayAlerts=true;
  myWorksheet=myWorkbook.ActiveSheet;
  var obj = document.all.tags("table");
  var RealColCount;  //实际列数（通过HTML表格的第二行得到）
  RealColCount=0;
  for(x=0;x<obj.length;x++)
  {
    if(obj[x].id==tableID)
    {
      for (i=rowStart; i<obj[x].rows.length; i++) 
      {
        for (j=colStart; j<obj[x].rows(i).cells.length; j++){
          myExcelCell=myWorksheet.Cells(i+1-rowStart,j+1-colStart);
          myHTMLTableCell=obj[x].rows(i).cells(j)
          if(j==0)
          {
          	myExcelCell.NumberFormatLocal   =   "@";
          }
          myExcelCell.Value = myHTMLTableCell.innerText;
          myCellColSpan = myHTMLTableCell.colSpan;
          myCellRowSpan = myHTMLTableCell.rowSpan;
          //不是第一行，且有合并单元格的情况
          if((i>rowStart)&&(myCellColSpan*myCellRowSpan>1)){
            myExcelCell2=myWorksheet.Cells(i+1-rowStart+(myCellRowSpan-1),j+1-colStart+(myCellColSpan-1));
            myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
          }
          if(i==rowStart+1)
            RealColCount=RealColCount+myCellColSpan;
        }
      }
      //如果有标题栏（第一个有效行的单元格数量为1），则处理标题栏的合并情况
      if((obj[x].rows(rowStart).cells.length==1)&&(RealColCount>0)){
        myExcelCell=myWorksheet.Cells(1,1);
        myExcelCell2=myWorksheet.Cells(1,RealColCount);
        myWorksheet.Range(myExcelCell,myExcelCell2).Merge();
      }
    }
  }
  myWorksheet.Columns("A:IV").AutoFit;
}

function parseTbl(tblId,sName,basePath)
{
	ConvertToExcel(tblId, 0 ,0);
}alert(111);