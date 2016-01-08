//????
function popUp( win_name,loc, w, h, menubar,center ) {


	var NS = (document.layers) ? 1 : 0;
	var editorWin;
	if( w == null ) { w = 500; }
	if( h == null ) { h = 350; }
	if( menubar == null || menubar == false ) {
		menubar = "";
	} else {
		menubar = "menubar,";
	}
	if( center == 0 || center == false ) {
		center = "";
	} else {
		center = true;
	}
	
	if( NS ) { w += 50; }
	if(center==true){
		var sw=screen.width;
		var sh=screen.height;
		if (w>sw){w=sw;}
		if(h>sh){h=sh;}
		var curleft=(sw -w)/2;
		var curtop=(sh-h-100)/2;
		if (curtop<0)
		{ 
		curtop=(sh-h)/2;
		}

		editorWin = window.open(loc,win_name, 'resizable,scrollbars,width=' + w + ',height=' + h+',left=' +curleft+',top=' +curtop );
	}
	else{
		editorWin = window.open(loc,win_name, menubar + 'resizable,scrollbars,width=' + w + ',height=' + h );
	}

	editorWin.focus(); //causing intermittent errors
}