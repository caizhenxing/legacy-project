/**
 * 	@(#)IDongjinLinkImpl.java   2006-12-27 ÏÂÎç01:51:14
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import et.bo.callcenter.plant.dongjin_c161a.IDongjinLink;

 /**
 * @author zhaoyifei
 * @version 2006-12-27
 * @see
 */
public class IDongjinLinkImpl implements IDongjinLink {

	Dongjin dj=null;
	/**
	 * 
	 */
	public IDongjinLinkImpl() {
		// TODO Auto-generated constructor stub
		dj=Dongjin.DongjinFactory(Dongjin.dllname);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#clearLink(int, int)
	 */
	public int clearLink(int wOne, int wAnother) {
		// TODO Auto-generated method stub
		return dj.ClearLink(wOne, wAnother);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#clearOneFromAnother(int, int)
	 */
	public int clearOneFromAnother(int wOne, int wAnother) {
		// TODO Auto-generated method stub
		return dj.ClearOneFromAnother(wOne, wAnother);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#clearThree(int, int, int)
	 */
	public int clearThree(int wOne, int wTwo, int wThree) {
		// TODO Auto-generated method stub
		return dj.ClearThree(wOne, wTwo, wThree);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#linkOneToAnother(int, int)
	 */
	public int linkOneToAnother(int wOne, int wAnother) {
		// TODO Auto-generated method stub
		return dj.LinkOneToAnother(wOne, wAnother);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#linkThree(int, int, int)
	 */
	public int linkThree(int wOne, int wTwo, int wThree) {
		// TODO Auto-generated method stub
		return dj.LinkThree(wOne, wTwo, wThree);
	}

	/* (non-Javadoc)
	 * @see et.bo.callcenter.plant.dongjin_c161a.IDongjinLink#setLink(int, int)
	 */
	public int setLink(int wOne, int wAnother) {
		// TODO Auto-generated method stub
		return dj.SetLink(wOne, wAnother);
	}

}
