/**
 * 	@(#)VoiceChangeDongjin.java   2007-1-26 ÏÂÎç01:21:55
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation.dongjin;

import et.bo.callcenter.operation.VoiceFileChange;
import et.bo.callcenter.plant.dongjin_c161a.IDongjinRecordChange;

 /**
 * @author zhaoyifei
 * @version 2007-1-26
 * @see
 */
public class VoiceChangeDongjin implements VoiceFileChange {

	private IDongjinRecordChange idrc=null;
	/**
	 * @return the idrc
	 */
	public IDongjinRecordChange getIdrc() {
		return idrc;
	}

	/**
	 * @param idrc the idrc to set
	 */
	public void setIdrc(IDongjinRecordChange idrc) {
		this.idrc = idrc;
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.VoiceFileChange#ad6ToPcm(java.lang.String, java.lang.String)
	 */
	public boolean ad6ToPcm(String source, String target) {
		// TODO Auto-generated method stub
		return 1==idrc.ad6ktoPcm(source,target);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.VoiceFileChange#adToPcm(java.lang.String, java.lang.String)
	 */
	public boolean adToPcm(String source, String target) {
		// TODO Auto-generated method stub
		return 1==idrc.adtoPcm(source,target);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.VoiceFileChange#pcmToAd(java.lang.String, java.lang.String)
	 */
	public boolean pcmToAd(String source, String target) {
		// TODO Auto-generated method stub
		return 1==idrc.pcmtoAd(source,target);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.VoiceFileChange#pcmToWave(java.lang.String, java.lang.String)
	 */
	public boolean pcmToWave(String source, String target) {
		// TODO Auto-generated method stub
		return 1==idrc.pcmtoWave(source,target);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.operation.VoiceFileChange#waveToPcm(java.lang.String, java.lang.String)
	 */
	public boolean waveToPcm(String source, String target) {
		// TODO Auto-generated method stub
		return 1==idrc.wavetoPcm(source,target);
	}

}
