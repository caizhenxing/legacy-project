//********************************************************************************
//�֐��� �FfncWindowOpen()
//�@�\   �F�E�B���h�E�I�[�v���i�J�����E�B���h�E�n���h���ٕێ��j
//�쐬   �F
//����   �F
//����   �F�Ȃ�
//�߂�l �F�Ȃ�
//���l   �F
//********************************************************************************

//�E�B���h�E�n���h��  
var windowObj = 0;
var windowObj2 = 0;

//*************************************
//* �T�u�E�B���h�E���J���B
//* �J�����E�B���h�E�n���h���̓����o�ϐ��Ƃ��ĕێ�����B
//* �c�[���o�[���͑S�Ĕ�\���B
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
//* �E�B���h�E���J���Ă����ꍇ�͕���B
//*************************************
function fncKeepedWindowClose(){
  //���C����ʂ̏����p�Ƀ^�[�Q�b�g��߂��B
  document.shinsei_form.target="_self";
  if(windowObj.closed == false){
    windowObj.close();
  }
}



//*************************************
//* �E�B���h�E���J���Ă����ꍇ�͌x���_�C�A���O���o����false��Ԃ��B
//*************************************
function fncAlertWindowOpen(msg){
  //���C����ʂ̏����p�Ƀ^�[�Q�b�g��߂��B
  document.shinsei_form.target="_self";
  if(windowObj.closed == false){
  	alert(msg);
  	return false;
  }else{
  	return true;
  }
}

function fncAlertWindowOpen2(msg){
  //���C����ʂ̏����p�Ƀ^�[�Q�b�g��߂��B
  document.shinsei_form.target="_self";
  if(windowObj2.closed == false){
    alert(msg);
    return false;
  }else{
    return true;
  }
}
