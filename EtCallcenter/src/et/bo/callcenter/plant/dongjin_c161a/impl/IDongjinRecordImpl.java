/**
 * 	@(#)IDongjinRecordImpl.java   2006-12-27 ÏÂÎç02:01:54
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinRecordImpl implements IDongjinRecord {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinRecordImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#checkRecordEnd(int)
	 */
	public boolean checkRecordEnd(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckRecordEnd(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#dRecOffHookDetect(int)
	 */
	public boolean dRecOffHookDetect(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.DRec_OffHookDetect(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#dadjustVocVol(int, char, char)
	 */
	public int dadjustVocVol(int wChnl, int cMode, int cVolAdjust) {
		// TODO Auto-generated method stub
		return dj.D_AdjustVocVol(wChnl, (char)cMode, (char)cVolAdjust);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#startRecordFile(int, java.lang.String, int)
	 */
	public boolean startRecordFile(int wChnlNo, String fileName, int dwRecordLen) {
		// TODO Auto-generated method stub
		return dj.StartRecordFile(wChnlNo, fileName, dwRecordLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#startRecordFileNew(int, java.lang.String, int, int)
	 */
	public boolean startRecordFileNew(int wChnlNo, String fileName,
			int dwRecordLen, int dwRecordStartPos) {
		// TODO Auto-generated method stub
		return dj.StartRecordFileNew(wChnlNo, fileName, dwRecordLen, dwRecordStartPos);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#stopRecordFile(int)
	 */
	public void stopRecordFile(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StopRecordFile(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#vRGetRecordData(int)
	 */
	public String vRGetRecordData(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.VR_GetRecordData(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#vRSetEcrMode(int, byte, int, int)
	 */
	public void vRSetEcrMode(int wChnl, byte cbEnableFlag, int wParam1,
			int wParam2) {
		// TODO Auto-generated method stub
		dj.VR_SetEcrMode(wChnl, cbEnableFlag, wParam1, wParam2);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#vRSetRefreshSize(int)
	 */
	public int vRSetRefreshSize(int wSize) {
		// TODO Auto-generated method stub
		return dj.VR_SetRefreshSize(wSize);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#vRStartRecord(int)
	 */
	public void vRStartRecord(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.VR_StartRecord(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecord#vRStopRecord(int)
	 */
	public void vRStopRecord(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.VR_StopRecord(wChnlNo);
	}

}
