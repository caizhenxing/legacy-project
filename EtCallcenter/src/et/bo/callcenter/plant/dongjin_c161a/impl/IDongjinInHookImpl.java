/**
 * 	@(#)IDongjinInHookImpl.java   2006-12-27 ÏÂÎç01:09:13
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinInHookImpl implements IDongjinInHook {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinInHookImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#elapseTime(int, int)
	 */
	public int elapseTime(int wChnlNo, int clockType) {
		// TODO Auto-generated method stub
		return dj.ElapseTime(wChnlNo, clockType);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#feedPower(int)
	 */
	public void feedPower(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.FeedPower(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#feedRealRing(int)
	 */
	public void feedRealRing(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.FeedRealRing(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#feedRing(int)
	 */
	public void feedRing(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.FeedRing(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#feedSigFunc()
	 */
	public void feedSigFunc() {
		// TODO Auto-generated method stub
		dj.FeedSigFunc();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#hangUpDetect(int)
	 */
	public int hangUpDetect(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.HangUpDetect(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#offHookDetect(int)
	 */
	public boolean offHookDetect(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.OffHookDetect(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#readGenerateSigBuf(int)
	 */
	public boolean readGenerateSigBuf(String lpFileName) {
		// TODO Auto-generated method stub
		return dj.ReadGenerateSigBuf(lpFileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#startHangUpDetect(int)
	 */
	public void startHangUpDetect(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StartHangUpDetect(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#startPlaySignal(int, int)
	 */
	public void startPlaySignal(int wChnlNo, int sigType) {
		// TODO Auto-generated method stub
		dj.StartPlaySignal(wChnlNo, sigType);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInHook#startTimer(int, int)
	 */
	public void startTimer(int wChnlNo, int clockType) {
		// TODO Auto-generated method stub
		dj.StartTimer(wChnlNo, clockType);
	}

}
