//********************************************************************************
//関数名 ：fncWindowOpen()
//機能   ：ウィンドウオープン（開いたウィンドウハンドルほ保持）
//作成   ：
//履歴   ：
//引数   ：なし
//戻り値 ：なし
//備考   ：
//********************************************************************************

//ウィンドウハンドル  
var windowObj = 0;
var windowObj2 = 0;

//*************************************
//* サブウィンドウを開く。
//* 開いたウィンドウハンドルはメンバ変数として保持する。
//* ツールバー等は全て非表示。
//*************************************
function fncWindowOpenAndKeep(theURL,winName) { 
  var brw_n=navigator.appName.charAt(0);
  var brw_v=window.navigator.appVersion.charAt(0);
  var iIE = true;
  if((brw_n == "N")&&(brw_v == "4"))iIE = false;

  if(iIE==true){
    if( window.screen.height >= 768 ) {
      //windowObj = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      windowObj = window.open("", winName, 'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj.focus();
    } else {
      height = window.screen.height - 100;
      //windowObj = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      windowObj = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
	  document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj.focus();
    }
  }else{
    if( window.screen.height >= 768 ) {
      //windowObj = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      windowObj = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj.focus();
    } else {
      height = window.screen.height - 100;
      //windowObj = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      windowObj = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj.focus();      
    }
  }

}

function fncWindowOpenAndKeep2(theURL,winName) { 
  var brw_n=navigator.appName.charAt(0);
  var brw_v=window.navigator.appVersion.charAt(0);
  var iIE = true;
  if((brw_n == "N")&&(brw_v == "4"))iIE = false;

  if(iIE==true){
    if( window.screen.height >= 768 ) {
      //windowObj2 = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      windowObj2 = window.open("", winName, 'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj2.focus();
    } else {
      height = window.screen.height - 100;
      //windowObj2 = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      windowObj2 = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj2.focus();
    }
  }else{
    if( window.screen.height >= 768 ) {
      //windowObj2 = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      windowObj2 = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj2.focus();
    } else {
      height = window.screen.height - 100;
      //windowObj2 = window.open(theURL,winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      windowObj2 = window.open("",winName,'menubar=no,toolbar=no,location=no,status=no,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
      document.shinsei_form.target=winName;
      document.shinsei_form.action=theURL;
      document.shinsei_form.submit();
      windowObj2.focus();      
    }
  }
}

//*************************************
//* ウィンドウが開いていた場合は閉じる。
//*************************************
function fncKeepedWindowClose(){
  //メイン画面の処理用にターゲットを戻す。
  document.shinsei_form.target="_self";
  if(windowObj.closed == false){
    windowObj.close();
  }
}



//*************************************
//* ウィンドウが開いていた場合は警告ダイアログを出してfalseを返す。
//*************************************
function fncAlertWindowOpen(msg){
  //メイン画面の処理用にターゲットを戻す。
  document.shinsei_form.target="_self";
  if(windowObj.closed == false){
  	alert(msg);
  	return false;
  }else{
  	return true;
  }
}

function fncAlertWindowOpen2(msg){
  //メイン画面の処理用にターゲットを戻す。
  document.shinsei_form.target="_self";
  if(windowObj2.closed == false){
    alert(msg);
    return false;
  }else{
    return true;
  }
}
