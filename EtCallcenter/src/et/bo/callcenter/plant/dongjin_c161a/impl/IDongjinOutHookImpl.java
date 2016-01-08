/**
 * 	@(#)IDongjinOutHookImpl.java   2006-12-27 ÏÂÎç01:53:07
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinOutHookImpl implements IDongjinOutHook {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinOutHookImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook#hangUp(int)
	 */
	public void hangUp(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.HangUp(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook#offHook(int)
	 */
	public void offHook(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.OffHook(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinOutHook#ringDetect(int)
	 */
	public boolean ringDetect(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.RingDetect(wChnlNo);
	}

}
