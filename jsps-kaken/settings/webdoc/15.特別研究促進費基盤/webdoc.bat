@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDFの圧縮
set ZOPT=-z f

rem !!レイアウトファイル(共通)
set IOD1=-iod page1.iod
set IOD3=-iod page3.iod
set IOD4=-iod page4.iod

rem !!データファイル(データがＣＳＶでない)
set DATA=-dat test.dat

rem !!.pdfを作成
%CSVDOC% -t d -out p %IOD1% %DATA% -o page1.pdf %ZOPT%
echo test1 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD3% %DATA% -o page3.pdf %ZOPT%
echo test3 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD4% %DATA% -o page4.pdf %ZOPT%
echo test4 status %ERRORLEVEL%

rem !!----------------------------------------------------
rem !!PDFを結合
"D:\PDF Makeup\bin\ypdfcomb.exe" -o 促進費基盤.pdf -i page1.pdf page3.pdf page4.pdf
