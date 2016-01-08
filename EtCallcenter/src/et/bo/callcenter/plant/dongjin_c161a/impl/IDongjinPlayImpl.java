/**
 * 	@(#)IDongjinPlayImpl.java   2006-12-27 ÏÂÎç01:54:33
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinPlayImpl implements IDongjinPlay {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinPlayImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#addIndexPlayFile(int, java.lang.String)
	 */
	public boolean addIndexPlayFile(int wChnlNo, String fileName) {
		// TODO Auto-generated method stub
		return dj.AddIndexPlayFile(wChnlNo, fileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#checkIndexPlayFile(int)
	 */
	public boolean checkIndexPlayFile(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckIndexPlayFile(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#checkPlayEnd(int)
	 */
	public boolean checkPlayEnd(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.CheckPlayEnd(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#resetIndex()
	 */
	public void resetIndex() {
		// TODO Auto-generated method stub
		dj.ResetIndex();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#rsetIndexPlayFile(int)
	 */
	public void rsetIndexPlayFile(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.RsetIndexPlayFile(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#setIndex(int, int)
	 */
	public boolean setIndex(String vocBuf, int dwVocLen) {
		// TODO Auto-generated method stub
		return dj.SetIndex(vocBuf, dwVocLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#startIndexPlayFile(int)
	 */
	public boolean startIndexPlayFile(int wChnlNo) {
		// TODO Auto-generated method stub
		return dj.StartIndexPlayFile(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#startPlay(int, int, int, int)
	 */
	public void startPlay(int wChnlNo, String playBuf, int dwStartPos,
			int dwPlayLen) {
		// TODO Auto-generated method stub
		dj.StartPlay(wChnlNo, playBuf, dwStartPos, dwPlayLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#startPlayFile(int, java.lang.String, int)
	 */
	public boolean startPlayFile(int wChnlNo, String fileName, int startPos) {
		// TODO Auto-generated method stub
		return dj.StartPlayFile(wChnlNo, fileName,startPos);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#startPlayIndex(int, int[], int)
	 */
	public void startPlayIndex(int wChnlNo, int[] pIndexTable, int wIndexLen) {
		// TODO Auto-generated method stub
		dj.StartPlayIndex(wChnlNo, pIndexTable, wIndexLen);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#stopIndexPlayFile(int)
	 */
	public void stopIndexPlayFile(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StopIndexPlayFile(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#stopPlay(int)
	 */
	public void stopPlay(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StopPlay(wChnlNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinPlay#stopPlayFile(int)
	 */
	public void stopPlayFile(int wChnlNo) {
		// TODO Auto-generated method stub
		dj.StopPlayFile(wChnlNo);
	}

}
