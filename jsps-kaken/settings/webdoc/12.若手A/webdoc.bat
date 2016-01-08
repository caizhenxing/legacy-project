@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDFの圧縮
set ZOPT=-z f

rem !!レイアウトファイル(共通)
set IOD1=-iod page1.iod

rem !!データファイル(データがＣＳＶでない)
rem set DATA=-dat Page1_MAX.dat

set DATA1=-dat 若手A新規Page1_MAX.dat
set DATA2=-dat 若手A継続Page1_MAX.dat

rem !!.pdfを作成
%CSVDOC% -t d -out p %IOD1% %DATA1% -o 若手A新規page1.pdf %ZOPT%
%CSVDOC% -t d -out p %IOD1% %DATA2% -o 若手A継続page1.pdf %ZOPT%
echo test3 status %ERRORLEVEL%








