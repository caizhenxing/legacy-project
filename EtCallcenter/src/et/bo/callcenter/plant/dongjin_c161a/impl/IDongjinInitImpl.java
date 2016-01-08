/**
 * 	@(#)IDongjinInitImpl.java   2006-12-27 ÏÂÎç01:14:13
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinInit;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinInitImpl implements IDongjinInit {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinInitImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#checkChType(int)
	 */
	public int checkChType(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckChType(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#checkChTypeNew(int)
	 */
	public int checkChTypeNew(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckChTypeNew(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#checkValidCh()
	 */
	public int checkValidCh() {
		// TODO Auto-generated method stub
		return dj.CheckValidCh();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#dSetWorkMode(int, char, char)
	 */
	public void dSetWorkMode(int wChnlNo, char cbWorkMode, char cbModeVal) {
		// TODO Auto-generated method stub
		dj.D_SetWorkMode(wChnlNo, cbWorkMode, cbModeVal);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#disableCard()
	 */
	public void disableCard() {
		// TODO Auto-generated method stub
		dj.DisableCard();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#enableCard(int, int)
	 */
	public int enableCard(int wUsedCh, int wFileBufLen) {
		// TODO Auto-generated method stub
		return dj.EnableCard(wUsedCh, wFileBufLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#freeDRV()
	 */
	public void freeDRV() {
		// TODO Auto-generated method stub
		dj.FreeDRV();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#getSysInfo()
	 */
	public TcIniType getSysInfo() {
		// TODO Auto-generated method stub
		String[] re=dj.GetSysInfo();
		
		return new TcIniType(re);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#getSysInfoMore()
	 */
	public TcIniTypeMore getSysInfoMore() {
		// TODO Auto-generated method stub
		String[] re=dj.GetSysInfoMore();
		return new TcIniTypeMore(re);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#isSupportCallerID()
	 */
	public boolean isSupportCallerID() {
		// TODO Auto-generated method stub
		return dj.IsSupportCallerID();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#loadDRV()
	 */
	public int loadDRV() {
		// TODO Auto-generated method stub
		return dj.LoadDRV();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#newReadPass(int)
	 */
	public int newReadPass(int wCardNo) {
		// TODO Auto-generated method stub
		return dj.NewReadPass(wCardNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#pushPlay()
	 */
	public void pushPlay() {
		// TODO Auto-generated method stub
		dj.PUSH_PLAY();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#setBusyPara(int)
	 */
	public void setBusyPara(int BusyLen) {
		// TODO Auto-generated method stub
		dj.SetBusyPara(BusyLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#setDialPara(int, int, int, int)
	 */
	public void setDialPara(int RingBack1, int RingBack0, int BusyLen,
			int RingTimes) {
		// TODO Auto-generated method stub
		dj.SetDialPara(RingBack1, RingBack0, BusyLen, RingTimes);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinInit#setPackRate(int)
	 */
	public void setPackRate(int wPackRate) {
		// TODO Auto-generated method stub
		dj.SetPackRate(wPackRate);
	}

}
