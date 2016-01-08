/**
 * 沈阳卓越科技有限公司
 */
package et.bo.common.impl;

import java.util.Date;

import et.bo.common.ThreeCallService;
import et.po.ThirdLog;
import excellence.framework.base.dao.BaseDAO;

/**
 * @author zhangfeng
 * 三方通话信息的录入
 */
public class ThreeCallServiceImpl implements ThreeCallService {
	
    private BaseDAO dao=null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see et.bo.common.ThreeCallService#insertThreeCall()
	 */
	public void insertThreeCall(String agcId) {
		ThirdLog tl = new ThirdLog();
		tl.setAgcwid(agcId);
		tl.setCallBetintime(new Date());
		dao.saveEntity(tl);
	}

}
