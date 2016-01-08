/**
 * 	@(#)IDongjinVoiceImpl.java   2007-1-4 ÏÂÎç04:36:55
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice;

 /**
 * @author zhaoyifei
 * @version 2007-1-4
 * @see
 */
public class IDongjinVoiceImpl implements IDongjinVoice {

	Dongjin dj=null;
	public IDongjinVoiceImpl()
	{
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigCheckBusy(int)
	 */
	public int sigCheckBusy(int wChNo) {
		// TODO Auto-generated method stub
		return dj.sigCheckBusy(wChNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigCheckDial(int)
	 */
	public int sigCheckDial(int wChNo) {
		// TODO Auto-generated method stub
		return dj.sigCheckDial(wChNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigGetCadenceCount(int, int)
	 */
	public int sigGetCadenceCount(int wChNo, int nCadenceType) {
		// TODO Auto-generated method stub
		return dj.sigGetCadenceCount(wChNo, nCadenceType);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigInit(int)
	 */
	public int sigInit(int wPara) {
		// TODO Auto-generated method stub
		return dj.sigInit(wPara);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigResetCheck(int)
	 */
	public void sigResetCheck(int wChNo) {
		// TODO Auto-generated method stub
		dj.sigResetCheck(wChNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinVoice#sigStartDial(int, char[], char[], int)
	 */
	public int sigStartDial(int wChNo, String dialNum, String preDialNum,
			int wMode) {
		// TODO Auto-generated method stub
		return dj.sigStartDial(wChNo, dialNum, preDialNum, wMode);
	}

}
