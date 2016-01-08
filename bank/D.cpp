#include <windows.h>
#include <commdlg.h>
#include <commctrl.h>
#include <windowsx.h>

#include "D.h"
#include "..\\tc08a32.h"
#include "resource.h"
#include "..\\NewSig.h"

WORD TotalLine;
extern HWND hGWnd;
long DriverOpenFlag;
LINESTRUCT *Lines;
char VoicePath[100];
//#pragma argsused
BOOL WINAPI yzInitSystem()
{
	int i;
	GetVoicePath();
	DriverOpenFlag = LoadDRV ( );
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
		strcpy(Lines[i].CallerID,"");
		strcpy(Lines[i].Dtmf,"");
		Lines[i].State = CH_FREE;
	}

	Sig_Init(0);

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
	lvc.pszText = "CallerID" ;
	lvc.cx = ColumnWidth[3];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),3,&lvc);

	lvc.iSubItem = 4;
	lvc.pszText = "DTMF" ;
	lvc.cx = ColumnWidth[4];
	ListView_InsertColumn(GetDlgItem(hGWnd,IDC_STATELIST),4,&lvc);

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
	for(i=0;i<TotalLine;i++)
	{
		if(Lines[i].nType==CHTYPE_TRUNK)
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "外线")
		else if(Lines[i].nType==CHTYPE_USER)
		{
		
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "内线")
		}
		else  if(Lines[i].nType==CHTYPE_EMPTY)
			ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), i, 1, "空");
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
	char tmpstr[20] ;

	switch( Lines[trkno].State ) 
	{
	case CH_FREE:		
		strcpy(state,"空闲"); 
		break ;
	case CH_RECEIVEID:
		strcpy(state,"接收主叫号码");
		break;
	case CH_WAITSECONDRING:
		strcpy(state,"等待第二次振铃");
		break;
				
	case CH_WELCOME:
		strcpy(state,"这里是东进电话银行演示系统");
		break ;
				
	case CH_ACCOUNT:
	case CH_ACCOUNT1:
		strcpy(state,"请输入8位帐号") ;
		break ;
	case CH_PASSWORD:
	case CH_PASSWORD1:
		strcpy(state,"请输入6位密码");
		break;
	case CH_SELECT:
	case CH_SELECT1:
		strcpy(state,"选择功能");
		break;
	case CH_RECORDFILE:
		strcpy(state,"录制留言");
		break;
	case CH_PLAYRESULT:
		strcpy(state,"播放查询结果");
		break;
	case CH_PLAYRECORD:
		strcpy(state,"播放留言");
		break;
	case CH_OFFHOOK:
		strcpy(state,"摘机");
		break;
	case CH_WAITUSERONHOOK:
		strcpy(state,"等待内线挂机");
		break;
	}
	
	ListView_GetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 2, tmpstr, 19 ) ;
	if ( strcmp(state,tmpstr )!=0) 
		ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 2, state );

	strcpy(state, Lines[trkno].CallerID) ;

	ListView_GetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 3, tmpstr, 19 ) ;
	if ( strcmp(state,tmpstr )!=0) 
		ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 3, state );

	strcpy(state,Lines[trkno].Dtmf);

	ListView_GetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 4, tmpstr, 19 ) ;
	if ( strcmp(state,tmpstr )!=0) 
		ListView_SetItemText(GetDlgItem(hGWnd,IDC_STATELIST), trkno, 4, state );

}
void yzResetChannel(int channelID)
{
	if(Lines[channelID].nType==CHTYPE_TRUNK)
	{
		StartSigCheck(channelID);
		HangUp(channelID);
		Sig_ResetCheck(channelID);
		//StopSigCheck(channelID);
	}
	Lines[channelID].Dtmf[0]=0;
	Lines[channelID].CallerID[0]=0;
	Lines[channelID].State = CH_FREE;
}
char yzConvertDtmf(int ch)
{
	char c;

	switch(ch)
    {
		case 10:
			c = '0';
			break;
		case 11:
			c = '*';
			break;
		case 12:
			c = '#';
			break;
        case 13:
        case 14:
        case 15:
            c=ch-13+'a';
            break;
        case 0:
            c='d';
            break;
		default:
			c = ch + '0';//转换成ASCII码
	}
	return c;
}

void WINAPI yzDoWork()
{
	char FileName[200];
    short int code;
	int len;
	PUSH_PLAY();
	FeedSigFunc();
    for(int i=0;i<TotalLine;i++)
    {
        yzDrawState(i);
        switch(Lines[i].State)
        {
        case CH_FREE:
            if(RingDetect(i))
            {
                if(Lines[i].nType==CHTYPE_USER)
					Lines[i].State=CH_OFFHOOK;
				else 
				{
					Lines[i].State=CH_RECEIVEID;
					ResetCallerIDBuffer(i);
					Lines[i].nTimeElapse=0;
				}
            }
            break;
        case CH_RECEIVEID:
        {
            bool bOffHook=false;
            if(Lines[i].nTimeElapse>2000 && RingDetect(i)) bOffHook=true;
            if(Lines[i].nTimeElapse>7000) bOffHook=true;
            int a=GetCallerIDStr (i,Lines[i].CallerID);
            if(a==3)
            {
                bOffHook=true;
            }
            else if(a==4)
            {
				strcpy(Lines[i].CallerID,"校验错");
                bOffHook=true;
            }
            if(bOffHook)
            {
                OffHook(i);
                StartSigCheck(i);
                Lines[i].State=CH_OFFHOOK;
            }
            Lines[i].nTimeElapse+=50;
        }
            break;
        case CH_WAITSECONDRING:
            if(RingDetect(i))
            {
                OffHook(i);
                StartSigCheck(i);
                Lines[i].State=CH_OFFHOOK;
            }
            break;
        case CH_OFFHOOK:
			strcpy(FileName,VoicePath);
			strcat(FileName,"bank.001");
			InitDtmfBuf(i);
			StartPlayFile(i,FileName,0L);
			Lines[i].State = CH_WELCOME;
			break;
		case CH_WELCOME:
			code=GetDtmfCode(i);
			if(code!=-1)
			{
				StopPlayFile(i);
				Lines[i].Dtmf[0]=yzConvertDtmf(code);
				Lines[i].Dtmf[1]=0;
				Lines[i].State=CH_ACCOUNT1;
				break;
			}
			if(CheckPlayEnd(i))
			{
				StopPlayFile(i);
				strcpy(FileName,VoicePath);
				strcat(FileName,"bank.002");
				StartPlayFile(i,FileName,0L);
				Lines[i].State=CH_ACCOUNT;
			}
			break;
		case CH_ACCOUNT:
			code=GetDtmfCode(i);
			if(code!=-1)
			{
				StopPlayFile(i);
				Lines[i].Dtmf[0]=yzConvertDtmf(code);
				Lines[i].Dtmf[1]=0;
				Lines[i].State=CH_ACCOUNT1;
				break;
			}
			if(CheckPlayEnd(i))
			{
				StopPlayFile(i);					
				Lines[i].State=CH_ACCOUNT1;
			}
			break;
		case CH_ACCOUNT1:
			len=strlen(Lines[i].Dtmf);
			while((code=GetDtmfCode(i))!=-1)
			{
				Lines[i].Dtmf[len++]=yzConvertDtmf(code);
			}
			Lines[i].Dtmf[len]=0;
			if(len>=8)
			{
//				code=GetDtmfCode(i);
				Lines[i].Dtmf[0]=0;
				strcpy(FileName,VoicePath);
				strcat ( FileName,"bank.003" );
				StartPlayFile(i,FileName,0L);
				Lines[i].State = CH_PASSWORD;
			}
			break;
		case CH_PASSWORD:
			code=GetDtmfCode(i);
			if(code!=-1)
			{
				StopPlayFile(i);
				Lines[i].Dtmf[0]=yzConvertDtmf(code);
				Lines[i].Dtmf[1]=0;
				Lines[i].State=CH_PASSWORD1;
				break;
			}
			if(CheckPlayEnd(i))
			{
				StopPlayFile(i);					
				Lines[i].State=CH_PASSWORD1;
			}
			break;
		case CH_PASSWORD1:
			len=strlen(Lines[i].Dtmf);
			while((code=GetDtmfCode(i))!=-1)
			{
				Lines[i].Dtmf[len++]=yzConvertDtmf(code);
			}
			Lines[i].Dtmf[len]=0;
			if(len>=6)
			{
				Lines[i].Dtmf[0]=0;
				strcpy(FileName,VoicePath);
				strcat ( FileName,"bank.004");
				StartPlayFile(i,FileName,0L);
				Lines[i].State = CH_SELECT;
			}
			break;
		case CH_SELECT:
			code=GetDtmfCode(i);
			if(code!=-1)
			{
				Lines[i].Dtmf[0]=yzConvertDtmf(code);
				Lines[i].Dtmf[1]=0;
				switch(Lines[i].Dtmf[0])
				{
				case '1':
					StopPlayFile(i);
					RsetIndexPlayFile(i);
					strcpy(FileName,VoicePath);
					strcat ( FileName, "bank.005");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d5");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d12");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d8");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d11");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d9");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d10");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d6");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d15");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d8");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"bank.008");
					AddIndexPlayFile(i,FileName);
					StartIndexPlayFile(i);
					Lines[i].State = CH_PLAYRESULT;
					break;
				case '2':
					StopPlayFile(i);
					RsetIndexPlayFile(i);
					strcpy(FileName,VoicePath);
					strcat(FileName,"bank.006");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d0");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d15");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d4");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d8");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"bank.008");
					AddIndexPlayFile(i,FileName);
					StartIndexPlayFile(i);
					Lines[i].State = CH_PLAYRESULT;
					break;
				case '3':
					StopPlayFile(i);
					RsetIndexPlayFile(i);
					strcpy(FileName,VoicePath);
					strcat(FileName,"bank.007");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d3");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d13");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d7");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"d12");
					AddIndexPlayFile(i,FileName);

					strcpy(FileName,VoicePath);
					strcat(FileName,"bank.008");
					AddIndexPlayFile(i,FileName);
					StartIndexPlayFile(i);
					Lines[i].State = CH_PLAYRESULT;
					break;
				default:
					break;
				}
			}
			break;
		case CH_PLAYRESULT:
			if(CheckIndexPlayFile(i))
			{
				StopIndexPlayFile(i);
				if(Lines[i].nType==CHTYPE_TRUNK) yzResetChannel(i);
				else 
				{
					StartPlaySignal(i,SIG_BUSY1);
					Lines[i].State=CH_WAITUSERONHOOK;
				}
			}
			break;
		case CH_WAITUSERONHOOK:
			if(!RingDetect(i))
			{
				StartPlaySignal(i,SIG_STOP);
				yzResetChannel(i);
			}
			break;
		}//end switch
		if(Lines[i].nType==CHTYPE_TRUNK && Lines[i].State!=CH_FREE)
		{
			//WORD wSigCheckResult=ReadCheckResult(i,PLAY_CHECK);
			//if(wSigCheckResult==R_BUSY)
			if (Sig_CheckBusy(i))
			{
				switch(Lines[i].State)
				{
                   
				case CH_WELCOME:
				case CH_ACCOUNT:
				case CH_PASSWORD:
				case CH_SELECT:
					StopPlayFile(i);
					break;
				case CH_PLAYRESULT:
					StopIndexPlayFile(i);
					break;
				}
				yzResetChannel(i);
			}
		}
		else if(Lines[i].nType==CHTYPE_USER && Lines[i].State!=CH_FREE)
		{
			if(!RingDetect(i))
			{
				switch(Lines[i].State)
				{
                   
				case CH_WELCOME:
				case CH_ACCOUNT:
				case CH_PASSWORD:
				case CH_SELECT:
					StopPlayFile(i);
					break;
				case CH_PLAYRESULT:
					StopIndexPlayFile(i);
					break;
				}
				yzResetChannel(i);
			}
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