#include <windows.h>
#include <windowsx.h>
#include <winbase.h>
#include <commdlg.h>
#include <commctrl.h>
#include <string.h>
#include <stdio.h>

#include "..\\tc08a32.h"
#include "dial.h"
#include "d.h"
#include "resource.h"

extern HWND hGWnd;
WORD TotalLine;
LINESTRUCT *Lines;
char VoicePath[100];
BOOL WINAPI yzInitSystem()
{
	int i;
	long DriverOpenFlag = LoadDRV ( );
	if ( DriverOpenFlag ) {
		MessageBox ( hGWnd, "Load driver FAIL", "Message", MB_OK );
		return FALSE;
	}

	TotalLine = CheckValidCh();
	if ( EnableCard(TotalLine,1024*8) != (long)0) {
		FreeDRV();
		MessageBox ( hGWnd, "Enable Card FAIL", "Message", MB_OK );
		return FALSE;
	}
	Lines=new LINESTRUCT[TotalLine];
	SetBusyPara(350);
	for(i=0;i<TotalLine;i++)
	{
        Lines[i].nType=CheckChType(i);
		Lines[i].State = CH_FREE;
	}

	static int ColumnWidth[60] = {50, 50, 150, 150, 150, 100, 50, 150};
	LV_COLUMN lvc;
	char dig[10];
	lvc.mask =  LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
	lvc.iSubItem = 0;
	lvc.pszText = "通道号" ;
	lvc.cx = ColumnWidth[0];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),0,&lvc);

	lvc.iSubItem = 1;
	lvc.pszText = " 通道类型" ;
	lvc.cx = ColumnWidth[1];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),1,&lvc);
	
	lvc.iSubItem = 2;
	lvc.pszText = "流程状态" ;
	lvc.cx = ColumnWidth[2];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),2,&lvc);

	lvc.iSubItem = 3;
	lvc.pszText = "号码" ;
	lvc.cx = ColumnWidth[3];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),3,&lvc);

	LV_ITEM lvi;
	for(i = 0; i < TotalLine; i++)	
	{
		lvi.mask = LVIF_TEXT;
		lvi.iItem = i;
		lvi.iSubItem = 0;
		itoa( i, dig, 10 ) ; 
		lvi.pszText = dig;
		ListView_InsertItem(GetDlgItem(hGWnd,IDC_STATELIST),&lvi);
	}
	GetVoicePath();
	char FileName[128];
	for(i=0;i<TotalLine;i++)
	{
		if(Lines[i].nType==CHTYPE_TRUNK)
		{
			itoa(i,dig,10);
			SendMessage(GetDlgItem(hGWnd,IDC_CHANNEL),CB_ADDSTRING,0,(LPARAM)dig);
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "外线")
		}
		else if(Lines[i].nType==CHTYPE_USER)
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "内线")
		else  if(Lines[i].nType==CHTYPE_EMPTY)
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "空");
		RsetIndexPlayFile(i);
		strcpy(FileName,VoicePath);
		strcat(FileName,"dial.001");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"dial.002");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d1");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d2");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d8");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d15");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d9");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"d7");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"dial.003");
		AddIndexPlayFile(i,FileName);

		strcpy(FileName,VoicePath);
		strcat(FileName,"dial.004");
		AddIndexPlayFile(i,FileName);

	}
	return TRUE;
}
void WINAPI yzExitSystem()
{
	DisableCard();
	FreeDRV();
	delete Lines;
}
void WINAPI yzDrawState( int trkno )
{	
	char state[100]; ;
	char tmpstr[32] ;

	switch( Lines[trkno].State ) 
	{
	case CH_FREE:		
		strcpy(state,"空闲"); 
		break ;
	case CH_DIAL:
		strcpy(state,"拨号");
		break;
	case CH_CHECKSIG:
		strcpy(state,"检查信号音");
		break;
	case CH_BUSY:
		strcpy(state,"占线");
		break;
	case CH_NOBODY:
		strcpy(state,"无人接电话");
		break;
	case CH_NOSIGNAL:
		strcpy(state,"没有信号音");
		break;
	case CH_OFFHOOK:
		strcpy(state,"摘机");
		break;
	case CH_PLAY:
		strcpy(state,"放音");
		break ;
	case CH_ONHOOK:
		strcpy(state,"挂机");
		break;
	}
	
	ListView_GetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 2, tmpstr, 31 ) ;
	if ( strcmp(state,tmpstr )!=0) 
		ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 2, state );

	strcpy(state, Lines[trkno].TelNum) ;

	ListView_GetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 3, tmpstr, 31 ) ;
	if ( strcmp(state,tmpstr )!=0) 
		ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 3, state );
}
void yzResetChannel(int channelID)
{
	if(Lines[channelID].nType==CHTYPE_TRUNK)
	{
		HangUp(channelID);
		StopSigCheck(channelID);
	}
	Lines[channelID].TelNum[0]=0;
	Lines[channelID].State = CH_FREE;
}
void WINAPI yzDoWork()
{
	int tt;
	PUSH_PLAY();
	FeedSigFunc();
	for(int Line=0;Line<TotalLine;Line++)
	{
		yzDrawState(Line);
		switch(Lines[Line].State)
		{
		case CH_FREE:
			break;
		case CH_DIAL:
			if(CheckSendEnd(Line) == 1)
			{
				StartSigCheck(Line);
				Lines[Line].State=CH_CHECKSIG;
			}
			break;
		case CH_CHECKSIG:
			tt=ReadCheckResult(Line,SEND_CHECK);
			if(tt == S_BUSY)
			{
				Lines[Line].State = CH_BUSY;
			}
			else if(tt == S_CONNECT)
			{
				Lines[Line].State = CH_OFFHOOK;
			}
			else if(tt == S_NOSIGNAL)
			{
				Lines[Line].State= CH_NOSIGNAL;
			}
			else if(tt == S_NOBODY)
			{
				Lines[Line].State= CH_NOBODY;
			}
			break;
		case CH_BUSY:
		case CH_NOSIGNAL:
		case CH_NOBODY:
			yzResetChannel(Line);
			break;
		case CH_OFFHOOK:
			StartIndexPlayFile(Line);
			Lines[Line].State=CH_PLAY;
			break;
		case CH_PLAY:
			if(CheckIndexPlayFile(Line) == 1)
			{
				StopIndexPlayFile(Line);
				Lines[Line].State = CH_ONHOOK;
			}
			break;
		case CH_ONHOOK:
			yzResetChannel(Line);
			break;
		}
	}//end for
}
void GetVoicePath()
{
	char FileName[100];
	GetWindowsDirectory(FileName,100);
	strcat(FileName,"\\tc08a-v.ini");
	GetPrivateProfileString("SYSTEM","InstallDir",NULL,VoicePath,100,FileName);
	strcat(VoicePath,"voc\\");
}
void yzDialOut()
{
	int CurSel=SendMessage(GetDlgItem(hGWnd,IDC_CHANNEL),CB_GETCURSEL,0,0);
	if(CurSel==CB_ERR) return;
	char a[32];
	memset(a,0,32);
	SendMessage(GetDlgItem(hGWnd,IDC_CHANNEL),CB_GETLBTEXT,CurSel,(LPARAM)a);
	if(strcmp(a,"")==0) return;
	int ChNo=atoi(a);
	GetWindowText(GetDlgItem(hGWnd,IDC_DIALNUM),a,32);
	OffHook(ChNo);
	SendDtmfBuf(ChNo,a);
	strcpy(Lines[ChNo].TelNum,a);
	Lines[ChNo].State=CH_DIAL;
}