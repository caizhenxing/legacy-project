package et.bo.callcenter.bo.cclog.service.impl;

import java.util.ArrayList;

import et.bo.callcenter.bo.cclog.bean.CcLogTalkBean;
import et.bo.callcenter.bo.cclog.service.CcLogTalkService;
import et.po.CcMain;
import et.po.CcTalk;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @describe CcLogTalk日志添加接口实现
 * @author 陈岗
 * @version 2007-7-29
 * @see
 */
public class CcLogTalkImpl implements CcLogTalkService {

	private BaseDAO dao = null;

	// private KeyService ks = null;

	private ClassTreeService cts = null;

	public void addCcLogTalk(ArrayList<CcLogTalkBean> list) {
		// TODO Auto-generated method stub
		//System.out.println( list.size()+"%%%%%%%%%%%%%%%%%%%%%");
		
		for (int i = 0; i < list.size(); i++) {
			if (getCcLogTalk(list.get(i)) != null) {
				CcLogTalkBean ccLogTalk = list.get(i);
				CcTalk ccTalk = getCcLogTalk(list.get(i));
		
				ccTalk.setCcMain(this.getCcLogMain(ccLogTalk.getCCLOG_ID()));
				ccTalk.setRingBegintime(ccLogTalk.getRING_BEGINTIME());
				ccTalk.setTouchBegintime(ccLogTalk.getTOUCH_BEGINTIME());
				ccTalk.setWaitingKeeptime(ccLogTalk.getWAITING_KEEPTIME());
				ccTalk.setTouchEndtime(ccLogTalk.getTOUCH_ENDTIME());
				ccTalk.setTouchKeeptime(ccLogTalk.getTOUCH_KEEPTIME());
				ccTalk.setTouchPost(ccLogTalk.getTOUCH_POST());
				ccTalk.setPostType(ccLogTalk.getPOST_TYPE());
				ccTalk.setRespondentType(ccLogTalk.getRESPONDENT_TYPE());
				ccTalk.setPhoneNum(ccLogTalk.getPHONE_NUM());
				ccTalk.setDisplaceBegintime(ccLogTalk.getDISPLACE_BEGINTIME());
				ccTalk.setRecordPath(ccLogTalk.getRECORD_PATH());
				ccTalk.setProcessType(ccLogTalk.getPROCESS_TYPE());
				ccTalk.setAddress(ccLogTalk.getADDRESS());
				ccTalk.setIsDelete("0");

				dao.updateEntity(ccTalk);
			} else
				dao.saveEntity(createCcLogTalk(list.get(i)));
		}

	}

	private CcTalk createCcLogTalk(CcLogTalkBean ccLogTalk) {
		CcTalk ccTalk = new CcTalk();
		// CclogMain ccLog = new CclogMain();

		System.out.println("jingyuzhuo...........................");
		
		ccTalk.setId(ccLogTalk.getID());
		ccTalk.setCcMain(this.getCcLogMain(ccLogTalk.getCCLOG_ID()));
		ccTalk.setRingBegintime(ccLogTalk.getRING_BEGINTIME());
		ccTalk.setTouchBegintime(ccLogTalk.getTOUCH_BEGINTIME());
		ccTalk.setWaitingKeeptime(ccLogTalk.getWAITING_KEEPTIME());
		ccTalk.setTouchEndtime(ccLogTalk.getTOUCH_ENDTIME());
		ccTalk.setTouchKeeptime(ccLogTalk.getTOUCH_KEEPTIME());
		ccTalk.setTouchPost(ccLogTalk.getTOUCH_POST());
		ccTalk.setPostType(ccLogTalk.getPOST_TYPE());
		ccTalk.setRespondent(ccLogTalk.getRESPONDENT());
		ccTalk.setRespondentType(ccLogTalk.getRESPONDENT_TYPE());
		ccTalk.setPhoneNum(ccLogTalk.getPHONE_NUM());
		ccTalk.setDisplaceBegintime(ccLogTalk.getDISPLACE_BEGINTIME());
		ccTalk.setRecordPath(ccLogTalk.getRECORD_PATH());
		ccTalk.setProcessType(ccLogTalk.getPROCESS_TYPE());
		ccTalk.setIsDelete("0");
		ccTalk.setAddress(ccLogTalk.getADDRESS());
		ccTalk.setRemark(ccLogTalk.getREMARK());

		return ccTalk;
	}

	private CcMain getCcLogMain(String id) {
		return (CcMain) dao.loadEntity(CcMain.class, id);
	}

	private CcTalk getCcLogTalk(CcLogTalkBean ccLogTalk) {
		System.out.println("id---------->"+ccLogTalk.getCCLOG_ID());
		if (dao.loadEntity(CcTalk.class, ccLogTalk.getID()) != null)
			return (CcTalk) dao.loadEntity(CcTalk.class, ccLogTalk
					.getID());
		return null;
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

	public String loadValue(String id) {
		// TODO Auto-generated method stub
		String result = "";
		CcTalk ct = (CcTalk) dao.loadEntity(CcTalk.class, id);
		if (ct != null) {
			result = "result";
		}
		return result;
	}

	public CcMain loadMainValue(String id) {
		// TODO Auto-generated method stub
		CcMain cm = (CcMain) dao.loadEntity(CcMain.class, id);
		return cm;
	}

}
