@echo off
set CSVDOC=start /wait D:\IOWebDOC\bin\iocsvdoc
rem !! PDFの圧縮
set ZOPT=-z f

rem !!レイアウトファイル(共通)
set IOD1=-iod Page1.iod
set IOD3=-iod Page3.iod

rem !!データファイル(データがＣＳＶでない)
set DATA=-dat test(サンプル用データ).dat


rem !!.pdfを作成
%CSVDOC% -t d -out p %IOD1% %DATA% -o Page1.pdf %ZOPT%
echo test1 status %ERRORLEVEL%
%CSVDOC% -t d -out p %IOD3% %DATA% -o Page3.pdf %ZOPT%
echo test3 status %ERRORLEVEL%

rem !!----------------------------------------------------
rem !!PDFを結合
"D:\PDF Makeup\bin\ypdfcomb.exe" -o 基盤（A）.pdf -i Page1.pdf Page3.pdf



