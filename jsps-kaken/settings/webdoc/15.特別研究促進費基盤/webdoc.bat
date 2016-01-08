@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDF�̈��k
set ZOPT=-z f

rem !!���C�A�E�g�t�@�C��(����)
set IOD1=-iod page1.iod
set IOD3=-iod page3.iod
set IOD4=-iod page4.iod

rem !!�f�[�^�t�@�C��(�f�[�^���b�r�u�łȂ�)
set DATA=-dat test.dat

rem !!.pdf���쐬
%CSVDOC% -t d -out p %IOD1% %DATA% -o page1.pdf %ZOPT%
echo test1 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD3% %DATA% -o page3.pdf %ZOPT%
echo test3 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD4% %DATA% -o page4.pdf %ZOPT%
echo test4 status %ERRORLEVEL%

rem !!----------------------------------------------------
rem !!PDF������
"D:\PDF Makeup\bin\ypdfcomb.exe" -o ���i����.pdf -i page1.pdf page3.pdf page4.pdf
