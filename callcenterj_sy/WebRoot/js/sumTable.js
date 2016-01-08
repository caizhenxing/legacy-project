function sumTable(table){
	var table = document.getElementById(table);
	var rows = table.rows.length;
	var cells = table.rows(rows-1).cells.length;
	
	tr = table.insertRow();
	tr.className = "evenStyle";
	for(var i = 0; i < cells; i++){
		td = tr.insertCell();
		if(i == 0){
			td.innerText = "SUM";
		}else{
			var sum = 0;
			for(var j = 1; j < rows; j++){
				sum += parseInt(table.rows(j).cells(i).innerText);
			}
			td.innerText = sum;
		}
	}
}
