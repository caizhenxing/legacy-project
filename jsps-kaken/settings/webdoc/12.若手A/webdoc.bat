@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDF�̈��k
set ZOPT=-z f

rem !!���C�A�E�g�t�@�C��(����)
set IOD1=-iod page1.iod

rem !!�f�[�^�t�@�C��(�f�[�^���b�r�u�łȂ�)
rem set DATA=-dat Page1_MAX.dat

set DATA1=-dat ���A�V�KPage1_MAX.dat
set DATA2=-dat ���A�p��Page1_MAX.dat

rem !!.pdf���쐬
%CSVDOC% -t d -out p %IOD1% %DATA1% -o ���A�V�Kpage1.pdf %ZOPT%
%CSVDOC% -t d -out p %IOD1% %DATA2% -o ���A�p��page1.pdf %ZOPT%
echo test3 status %ERRORLEVEL%








