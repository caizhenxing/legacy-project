@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDF�̈��k
set ZOPT=-z f

rem !!���C�A�E�g�t�@�C��(����)
set IOD1=-iod Page1.iod
set IOD3=-iod Page3.iod

rem !!�f�[�^�t�@�C��(�f�[�^���b�r�u�łȂ�)
set DATA=-dat test(�T���v���p�f�[�^).dat


rem !!.pdf���쐬
%CSVDOC% -t d -out p %IOD1% %DATA% -o Page1.pdf %ZOPT%
echo test1 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD3% %DATA% -o Page3.pdf %ZOPT%
echo test3 status %ERRORLEVEL%

rem !!----------------------------------------------------
rem !!PDF������
"D:\PDF Makeup\bin\ypdfcomb.exe" -o ��ՁiA�j.pdf -i Page1.pdf Page3.pdf



