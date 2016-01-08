@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDF�̈��k
set ZOPT=-z f

rem !!���C�A�E�g�t�@�C��(����)
set IOD1=-iod page1.iod
set IOD2=-iod page2.iod
set IOD3=-iod page3.iod
set IOD4=-iod page4.iod

rem !!�f�[�^�t�@�C��(�f�[�^���b�r�u�łȂ�)
set DATA1=-dat Page1_MAX.dat
set DATA2=-dat Page2_MAX.dat
set DATA3=-dat Page3_MAX.dat
set DATA4=-dat Page4_MAX.dat

rem !!.pdf���쐬
%CSVDOC% -t d -out p %IOD1% %DATA1% -o page1.pdf %ZOPT%
echo page1 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD2% %DATA2% -o page2.pdf %ZOPT%
echo page2 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD3% %DATA3% -o page3.pdf %ZOPT%
echo page3 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD4% %DATA4% -o page4.pdf %ZOPT%
echo page4 status %ERRORLEVEL%

@pause
