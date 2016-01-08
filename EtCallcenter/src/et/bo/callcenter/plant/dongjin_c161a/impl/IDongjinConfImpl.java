/**
 * 	@(#)IDongjinConfImpl.java   2006-12-27 ÉÏÎç10:38:11
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinConf;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinConfImpl implements IDongjinConf {

	Dongjin dj=null;
	public IDongjinConfImpl()
	{
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}
	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#addChnl(int, int, int, int)
	 */
	public int addChnl(int confNo, int channelNo, int chnlAtte, int noiseSupp) {
		// TODO Auto-generated method stub
		return dj.AddChnl(confNo, channelNo, chnlAtte, noiseSupp);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#addListenChnl(int, int)
	 */
	public int addListenChnl(int confNo, int channelNo) {
		// TODO Auto-generated method stub
		return dj.AddListenChnl(confNo, channelNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#dConfAddRecListenChnl(int, int)
	 */
	public int dConfAddRecListenChnl(int confNo, int channelNo) {
		// TODO Auto-generated method stub
		return dj.DConf_AddRecListenChnl(confNo, channelNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#dConfDisableConfCard()
	 */
	public void dConfDisableConfCard() {
		// TODO Auto-generated method stub
		dj.DConf_DisableConfCard();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#dConfEnableConfCard()
	 */
	public int dConfEnableConfCard() {
		// TODO Auto-generated method stub
		return dj.DConf_EnableConfCard();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#dConfGetResNumber()
	 */
	public int dConfGetResNumber() {
		// TODO Auto-generated method stub
		return dj.DConf_GetResNumber();
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#dConfSubRecListenChnl(int, int)
	 */
	public int dConfSubRecListenChnl(int confNo, int channelNo) {
		// TODO Auto-generated method stub
		return dj.DConf_SubRecListenChnl(confNo, channelNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#subChnl(int, int)
	 */
	public int subChnl(int confNo, int channekNo) {
		// TODO Auto-generated method stub
		return dj.SubChnl(confNo, channekNo);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinConf#subListenChnk(int, int)
	 */
	public int subListenChnk(int confNo, int channelNo) {
		// TODO Auto-generated method stub
		return dj.SubListenChnk(confNo, channelNo);
	}

}
