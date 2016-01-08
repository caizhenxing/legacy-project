/**
 * 	@(#)IDongjinRecordChangeImpl.java   2006-12-27 ÏÂÎç02:07:22
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinRecordChangeImpl implements IDongjinRecordChange {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinRecordChangeImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange#ad6ktoPcm(java.lang.String, java.lang.String)
	 */
	public int ad6ktoPcm(String adpcmFileName, String pcmFileName) {
		// TODO Auto-generated method stub
		return dj.Ad6ktoPcm(adpcmFileName, pcmFileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange#adtoPcm(java.lang.String, java.lang.String)
	 */
	public int adtoPcm(String adpcmFileName, String pcmFileName) {
		// TODO Auto-generated method stub
		return dj.AdtoPcm(adpcmFileName, pcmFileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange#pcmtoAd(java.lang.String, java.lang.String)
	 */
	public int pcmtoAd(String pcmFileName, String adpcmFileName) {
		// TODO Auto-generated method stub
		return dj.PcmtoAd(pcmFileName, adpcmFileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange#pcmtoWave(java.lang.String, java.lang.String)
	 */
	public int pcmtoWave(String pcmFileName, String waveFileName) {
		// TODO Auto-generated method stub
		return dj.PcmtoWave(pcmFileName, waveFileName);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange#wavetoPcm(java.lang.String, java.lang.String)
	 */
	public int wavetoPcm(String waveFileName, String pcmFileName) {
		// TODO Auto-generated method stub
		return dj.WavetoPcm(waveFileName, pcmFileName);
	}

}
