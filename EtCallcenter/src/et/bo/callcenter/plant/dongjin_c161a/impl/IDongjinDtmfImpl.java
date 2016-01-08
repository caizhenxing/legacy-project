/**
 * 	@(#)IDongjinDtmfImpl.java   2006-12-27 ÉÏÎç11:00:14
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinDtmfImpl implements IDongjinDtmf {

	Dongjin dj=null;
	
	/**
	 * 
	 */
	public IDongjinDtmfImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#checkPolarity(int)
	 */
	public boolean checkPolarity(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckPolarity(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#checkSendEnd(int)
	 */
	public boolean checkSendEnd(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckSendEnd(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#checkSilence(int, int)
	 */
	public int checkSilence(int wChnlNo, int wCheckNum) {
		// TODO Auto-generated method stub
		return dj.CheckSilence(wChnlNo, wCheckNum);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#dtmfHit(int)
	 */
	public boolean dtmfHit(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.DtmfHit(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#getDtmfCode(int)
	 */
	public short getDtmfCode(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.GetDtmfCode(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#initDtmfBuf(int)
	 */
	public void initDtmfBuf(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.InitDtmfBuf(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#newCheckSendEnd(int)
	 */
	public boolean newCheckSendEnd(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.NewCheckSendEnd(wChnlNo)==1;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#newSendDtmfBuf(int, java.lang.String)
	 */
	public void newSendDtmfBuf(int wChnlNo, String dialNum) {
		// TODO Auto-generated method stub
		dj.NewSendDtmfBuf(wChnlNo, dialNum);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#readBusyCount()
	 */
	public int readBusyCount() {
		// TODO Auto-generated method stub
		return dj.ReadBusyCount();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#readCheckResult(int, int)
	 */
	public int readCheckResult(int wChnlNo, int wMode) {
		// TODO Auto-generated method stub
		return dj.ReadCheckResult(wChnlNo, wMode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#sendDtmfBuf(int, java.lang.String)
	 */
	public void sendDtmfBuf(int wChnlNo, String dialNum) {
		// TODO Auto-generated method stub
		dj.SendDtmfBuf(wChnlNo, dialNum);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#setSendPara(int, int)
	 */
	public int setSendPara(int toneLen, int silenceLen) {
		// TODO Auto-generated method stub
		return dj.SetSendPara(toneLen, silenceLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#startSigCheck(int)
	 */
	public void startSigCheck(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StartSigCheck(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinDtmf#stopSigCheck(int)
	 */
	public void stopSigCheck(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StopSigCheck(wChnlNo);
	}

}
