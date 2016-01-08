package et.bo.callcenter.bo.cclog.service.impl;

import et.bo.callcenter.bo.cclog.bean.CcLogMailBoxBean;
import et.bo.callcenter.bo.cclog.service.CcLogMailService;
import et.po.CcMain;
import et.po.CcVoicemail;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe 语音信箱日志添加接口实现
 * @author 陈岗
 * @date 2008-3-31
 * @see
 */
public class CcLogMailImpl implements CcLogMailService {
	
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;

	public void addCcLogMail(CcLogMailBoxBean ccLogMail) {
		dao.saveEntity(createCcLogMail(ccLogMail));
	}
	
	
	private CcVoicemail createCcLogMail(CcLogMailBoxBean ccLogMailBox){
		CcVoicemail cvm = new CcVoicemail();
		
		cvm.setId(ccLogMailBox.getId());
		cvm.setCcMain(getCcLogMain(ccLogMailBox.getCclogid()));
		cvm.setBegintime(ccLogMailBox.getBegintime());
		cvm.setEndtime(ccLogMailBox.getEndtime());
		cvm.setWay(ccLogMailBox.getWay());
		cvm.setLongtime(ccLogMailBox.getLongtime());
		cvm.setIfdispose(ccLogMailBox.getIfdispose());
		cvm.setPhoneNum(ccLogMailBox.getPhoneNum());
		
//		cvm.setId(ccLog.getId());
//		cm.setIsDelete("0");
//		cm.setBeginPost(ccLog.getBegin_post());
//		cm.setPostType(ccLog.getPost_type());
//		cm.setProcessEndtime(ccLog.getProcess_endtime());
//		cm.setProcessKeeptime(ccLog.getProcess_keeptime());
//		cm.setProcessType(ccLog.getProcess_type());
//		cm.setRecordBuildtime(TimeUtil.getNowTime());
//		cm.setRecordPath(ccLog.getRecord_path());
//		cm.setRemark(ccLog.getRemark());
//		cm.setRingBegintime(ccLog.getRing_begintime());
//		cm.setTelNum(ccLog.getTel_num());
//		cm.setRemark(ccLog.getRemark());
		
		return cvm;
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