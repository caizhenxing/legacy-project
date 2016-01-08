/**
 * 	@(#)IDongjinFskImpl.java   2006-12-27 ÉÏÎç11:14:43
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinFskImpl implements IDongjinFsk {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinFskImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskCheckSendFSKEnd(int, int)
	 */
	public int dJFskCheckSendFSKEnd(int vocID, int mode) {
		// TODO Auto-generated method stub
		return dj.DJFsk_CheckSendFSKEnd(vocID, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskGetFSK(int, byte[], int)
	 */
	public int dJFskGetFSK(int trunkID, byte[] pInfo, int mode) {
		// TODO Auto-generated method stub
		return dj.DJFsk_GetFSK(trunkID, pInfo, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskInitForFsk(int)
	 */
	public int dJFskInitForFsk(int mode) {
		// TODO Auto-generated method stub
		return dj.DJFsk_InitForFsk(mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskRelease()
	 */
	public void dJFskRelease() {
		// TODO Auto-generated method stub
		dj.DJFsk_Release();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskResetFskBuffer(int, int)
	 */
	public void dJFskResetFskBuffer(int trunkID, int mode) {
		// TODO Auto-generated method stub
		dj.DJFsk_ResetFskBuffer(trunkID, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskSendFSK(int, byte[], int, int)
	 */
	public int dJFskSendFSK(int trunkID, byte[] pInfo, int wSize, int mode) {
		// TODO Auto-generated method stub
		return dj.DJFsk_SendFSK(trunkID, pInfo, wSize, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskSendFSKA(int, byte[], int, int, int)
	 */
	public int dJFskSendFSKA(int trunkID, byte[] pInfo, int wSize, int mode,
			int markNum) {
		// TODO Auto-generated method stub
		return dj.DJFsk_SendFSKA(trunkID, pInfo, wSize, mode, markNum);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskSendFSKBit(int, byte[], int, int)
	 */
	public int dJFskSendFSKBit(int trunkID, byte[] pInfo, int wSize, int mode) {
		// TODO Auto-generated method stub
		return dj.DJFsk_SendFSKBit(trunkID, pInfo, wSize, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#dJFskStopSend(int, int)
	 */
	public void dJFskStopSend(int trunkID, int mode) {
		// TODO Auto-generated method stub
		dj.DJFsk_StopSend(trunkID, mode);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#getCallerIDRawStr(int)
	 */
	public String getCallerIDRawStr(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.GetCallerIDRawStr(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#getCallerIDStr(int, int)
	 */
	public String getCallerIDStr(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.GetCallerIDStr(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinFsk#resetCallerIDBuffer(int)
	 */
	public void resetCallerIDBuffer(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.ResetCallerIDBuffer(wChnlNo);
	}

}
