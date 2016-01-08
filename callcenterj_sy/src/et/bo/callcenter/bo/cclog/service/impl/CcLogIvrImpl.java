package et.bo.callcenter.bo.cclog.service.impl;

import et.bo.callcenter.bo.cclog.bean.CcLogIvrBean;
import et.bo.callcenter.bo.cclog.service.CcLogIvrService;
import et.po.CcIvr;
import et.po.CcMain;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe CcLog日志添加接口实现
 * @author 陈岗
 * @version 2007-7-26
 * @see
 */
public class CcLogIvrImpl implements CcLogIvrService {
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;
	
	public void addCcLogIvr(CcLogIvrBean ccLogIvr) {
		dao.saveEntity(createCcLogIvr(ccLogIvr));
		
	}
	private CcIvr createCcLogIvr(CcLogIvrBean ccLogIvr){
		CcIvr ci = new CcIvr();
		//System.out.println(ccLogIvr.getId()+"............");
		ci.setId(ccLogIvr.getId());
		ci.setCcMain(this.getCcLogMain(ccLogIvr.getCclog_id()));
		ci.setIvrBegintime(ccLogIvr.getIvr_begintime());
		ci.setIvrEndtime(ccLogIvr.getIvr_endtime());
		ci.setProcessType(ccLogIvr.getProcess_type());
		ci.setIsDelete(ccLogIvr.getIs_delete());
		ci.setRemark(ccLogIvr.getRemark());		
		return ci;
	}
	
	private CcMain getCcLogMain(String id) {
		return (CcMain)dao.loadEntity(CcMain.class, id);
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