//////////////////////////////////////////////////
//Dial.cpp
#include <windows.h>
#include <commdlg.h>
#include <commctrl.h>
#include <windowsx.h>

#include "resource.h"
#include "dial.h"
#include "d.h"
#include "..\\tc08a32.h"

BOOL bHaveLoadDriver;
HANDLE hInst;
HINSTANCE hLib;
HWND hGWnd;
extern WORD TotalLine;
int PASCAL WinMain(	HINSTANCE hInstance,HINSTANCE hPrevInstance,LPSTR lpszCmdParam, int nCmdShow)
{
	static char szAppName[] = "Dial";
	HWND        hwnd;
	MSG         msg;
	WNDCLASS    wndclass;
	char strSys[200];


//	Unused(lpszCmdParam);

	if (!hPrevInstance)
	{
		wndclass.style         = CS_HREDRAW | CS_VREDRAW;
		wndclass.lpfnWndProc   = (WNDPROC)WndProc;
		wndclass.cbClsExtra    = 0 ;
		wndclass.cbWndExtra    = DLGWINDOWEXTRA;
		wndclass.hInstance     = hInstance;
		wndclass.hIcon         = LoadIcon (hInstance, "IDI_DIAL");
		wndclass.hCursor       = LoadCursor (NULL, IDI_APPLICATION);
		wndclass.hbrBackground = (HBRUSH)GetStockObject(LTGRAY_BRUSH);
		wndclass.lpszMenuName  = NULL;
		wndclass.lpszClassName = szAppName;

		ATOM a=RegisterClass (&wndclass);
	}

	GetSystemDirectory(strSys,200);
	strcat(strSys,"\\comctl32.dll");
	hLib = LoadLibrary(strSys);

	hInst = hInstance;
	hwnd = CreateDialog(hInstance, "DIAL", 0, NULL);
	hGWnd=hwnd;
	
	ShowWindow (hwnd, nCmdShow);
	UpdateWindow(hwnd);
	
	bHaveLoadDriver=yzInitSystem();
	if(!bHaveLoadDriver) PostQuitMessage(0);

	//FARPROC lpProc;
	//lpProc = MakeProcInstance((FARPROC)TimerProc, hInstance);
	SetTimer(hwnd,ID_TIME,100,TimerProc); 

	while (GetMessage(&msg, NULL, 0, 0)) 
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	return msg.wParam ;
}
VOID CALLBACK TimerProc(HWND hwnd,UINT uMsg,UINT idEvent,DWORD dwTime)
{ 
	if(idEvent!=ID_TIME) return;
	yzDoWork();
}

LRESULT CALLBACK  WndProc (HWND hwnd, UINT message, UINT wParam, LONG lParam)
{

	switch (message)
	{
	 	case WM_DESTROY :
			KillTimer(hwnd,ID_TIME);
			if(bHaveLoadDriver) yzExitSystem();
			PostQuitMessage (0) ;
			return 0 ;
			break;
		case WM_COMMAND:
			if(LOWORD(wParam)==ID_OK)
			{
				KillTimer(hwnd,ID_TIME);
				if(bHaveLoadDriver) yzExitSystem();
				PostQuitMessage(0);
			}
			else if(LOWORD(wParam)==IDC_DIAL)
				yzDialOut();
			return 0;
			break;
		default:
			return DefWindowProc (hwnd, message, wParam, lParam) ;
			break;
	}
}



