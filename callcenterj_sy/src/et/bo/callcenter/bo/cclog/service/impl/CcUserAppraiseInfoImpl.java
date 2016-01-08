package et.bo.callcenter.bo.cclog.service.impl;

import et.bo.callcenter.bo.cclog.service.CcUserAppraiseInfoService;
import et.po.CcUserAppraiseInfo;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;

public class CcUserAppraiseInfoImpl implements CcUserAppraiseInfoService {
	private BaseDAO dao=null;
	private KeyService ks = null;
	public void addCcUserAppraiseInfo(CcUserAppraiseInfo info) {
		// TODO Auto-generated method stub
		String key = ks.getNext("CC_USERAPPRAISEINFO");
		info.setId(1);
		dao.saveEntity(info);
	}
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	

}
