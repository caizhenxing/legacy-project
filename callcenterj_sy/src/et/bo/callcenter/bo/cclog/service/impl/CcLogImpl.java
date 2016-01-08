package et.bo.callcenter.bo.cclog.service.impl;

import et.bo.callcenter.bo.cclog.bean.CcLogBean;
import et.bo.callcenter.bo.cclog.service.CcLogService;
import et.po.CcMain;
import excellence.common.classtree.ClassTreeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe CcLog日志添加接口实现
 * @author 陈岗
 * @version 2007-7-26
 * @see
 */
public class CcLogImpl implements CcLogService {
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;
	

	public void addCcLog(CcLogBean ccLog) {
		// TODO Auto-generated method stub
		dao.saveEntity(createCcLog(ccLog));
	}
	
	private CcMain createCcLog(CcLogBean ccLog){
		CcMain cm = new CcMain();
		
		cm.setId(ccLog.getId());
		cm.setIsDelete("0");
		cm.setBeginPost(ccLog.getBegin_post());
		cm.setPostType(ccLog.getPost_type());
		cm.setProcessEndtime(ccLog.getProcess_endtime());
		cm.setProcessKeeptime(ccLog.getProcess_keeptime());
		cm.setProcessType(ccLog.getProcess_type());
		cm.setRecordBuildtime(TimeUtil.getNowTime());
//		cm.setRecordPath(ccLog.getRecord_path());
		cm.setRemark(ccLog.getRemark());
		cm.setRingBegintime(ccLog.getRing_begintime());
		
		String t = ccLog.getTel_num();
		String telNum = t;
		
		cm.setTelNum(telNum);
		cm.setRemark(ccLog.getRemark());
		
		return cm;
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