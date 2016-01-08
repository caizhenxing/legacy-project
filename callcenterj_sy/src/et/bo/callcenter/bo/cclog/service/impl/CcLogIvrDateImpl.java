package et.bo.callcenter.bo.cclog.service.impl;

import java.util.ArrayList;

import et.bo.callcenter.bo.cclog.bean.CcLogIvrDateBean;
import et.bo.callcenter.bo.cclog.service.CcLogIvrDateService;
import et.po.CcIvr;
import et.po.CcIvrDate;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe CcLogTalk日志添加接口实现
 * @author 陈岗
 * @version 2007-7-29
 * @see
 */
public class CcLogIvrDateImpl implements CcLogIvrDateService {
	
	private BaseDAO dao = null;
	
//	private KeyService ks = null;
	
	private ClassTreeService cts = null;

	public void addCcLogIvrDate(ArrayList<CcLogIvrDateBean> list) {
		if(list != null) {
		for(int i=0; i<list.size(); i++) {
			dao.saveEntity(createCclogIvrDate(list.get(i)));
		}	
		}
	}

	private CcIvrDate createCclogIvrDate(CcLogIvrDateBean cclogIvrDate){//将自己的bean转成po的类
		CcIvrDate ccIvrDate=new CcIvrDate();
		ccIvrDate.setId(cclogIvrDate.getId());
		ccIvrDate.setCcIvr(this.getCcLogIvr(cclogIvrDate.getIvr_id()));
		ccIvrDate.setModuleId(cclogIvrDate.getModule_id());
		ccIvrDate.setModuleBegintime(cclogIvrDate.getModule_begintime());
		ccIvrDate.setModuleEndtime(cclogIvrDate.getModule_endtime());
		ccIvrDate.setIsDelete(cclogIvrDate.getIs_delete());
		ccIvrDate.setRemark(cclogIvrDate.getRemark());
		return ccIvrDate;
	}
	
	private CcIvr getCcLogIvr(String id) {
		return (CcIvr)dao.loadEntity(CcIvr.class, id);
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
}
