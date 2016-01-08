#ifndef _D_H
#define _D_H
enum CHANNEL_STATE {
	CH_FREE,
	CH_DIAL,
	CH_CHECKSIG,
	CH_PLAY,
	CH_ONHOOK,
	CH_OFFHOOK,
	CH_BUSY,
	CH_NOBODY,
	CH_NOSIGNAL,
};
struct LINESTRUCT
{
	int nType;
	int State;
	char TelNum[32];
};

void WINAPI yzDoWork();
BOOL  WINAPI yzInitSystem();
void  WINAPI yzExitSystem();
void WINAPI yzDrawState( int trkno );
char yzConvertDtmf(int ch);
void GetVoicePath();
void yzDialOut();
#endif  //_D_H
