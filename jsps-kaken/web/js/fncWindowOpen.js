//********************************************************************************
//�֐��� �FfncWindowOpen()
//�@�\   �F�E�B���h�E�I�[�v���i���O�C�����j
//�쐬   �F
//����   �F
//����   �F�Ȃ�
//�߂�l �F�Ȃ�
//���l   �F
//********************************************************************************
function fncWindowOpen(theURL,winName) { 
  var brw_n=navigator.appName.charAt(0);
  var brw_v=window.navigator.appVersion.charAt(0);
  var iIE = true;
  if((brw_n == "N")&&(brw_v == "4"))iIE = false;

  if(iIE==true){
    if( window.screen.height >= 768 ) {
      main = window.open(theURL,winName,'menubar=yes,toolbar=no,location=no,status=yes,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
    } else {
      height = window.screen.height - 100;
      main = window.open(theURL,winName,'menubar=yes,toolbar=no,location=no,status=yes,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
    }
  }else{
    if( window.screen.height >= 768 ) {
      main = window.open(theURL,winName,'menubar=yes,toolbar=no,location=no,status=yes,scrollbars=yes,resizable=yes,width=880,height=600,top=0');
    } else {
      height = window.screen.height - 100;
      main = window.open(theURL,winName,'menubar=yes,toolbar=no,location=no,status=yes,scrollbars=yes,resizable=yes,width=880,height=' + height + ',top=0');
    }
  }

}
