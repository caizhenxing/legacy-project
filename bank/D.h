#ifndef _BANK_H
#define _BANK_H
#include "..\\NewSig.h"
enum CHANNEL_STATE {
	CH_FREE,
	CH_RECEIVEID,
	CH_WAITSECONDRING,
	CH_WELCOME,
	CH_ACCOUNT,
	CH_ACCOUNT1,
	CH_PASSWORD,
	CH_PASSWORD1,
	CH_SELECT,
	CH_SELECT1,
	CH_PLAYRESULT,
	CH_RECORDFILE,
	CH_PLAYRECORD,
	CH_OFFHOOK,
	CH_WAITUSERONHOOK,
};
struct LINESTRUCT
{
	int nType;
	int State;
	char CallerID[32];
	char Dtmf[32];
	int nTimeElapse;
};

void WINAPI yzDoWork();
BOOL  WINAPI yzInitSystem();
void  WINAPI yzExitSystem();
void WINAPI yzDrawState( int trkno );
char yzConvertDtmf(int ch);
void GetVoicePath();


#endif