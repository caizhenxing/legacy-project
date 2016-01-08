/**
 * 	@(#)AgroKonwledgeService.java   2007-4-5 下午08:52:26
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.callcenter.bo.cclog.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import et.bo.callcenter.bo.cclog.service.CcMainService;
import et.bo.callcenter.serversocket.panel.impl.AgentBean;
import et.bo.callcenter.serversocket.panel.impl.CtiBean;
import et.bo.common.mylog.MyLog;
import et.po.CcMain;
import et.po.CcTalk;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;

/**
 * CclogMain表的增删改
 * 
 * @version 2008-05-06
 * @author 王文权
 */
public class CcMainImpl implements CcMainService {
	private BaseDAO dao = null;

	private KeyService ks = null;

	/**
	 * 将CtiBean转换成CcMain插入数据库中
	 * 
	 * @param ctiBean
	 */
	public void addCcLogMain(CtiBean ctiBean) {
		// TODO Auto-generated method stub
		CcMain main = new CcMain();
		main.setId(ctiBean.getMainKey());
		main.setBeginPost(ctiBean.getBeginPort());

		// main.setIsDelete()
		main.setPostType(ctiBean.getPostType());
		main.setProcessEndtime(ctiBean.getProcessEndTime());
		main.setProcessKeeptime(ctiBean.getProcessKeepTime());
		main.setProcessType(ctiBean.getProcessType());
		// main.setRecordBuildtime(this.str2Date(ctiBean.getr))
		// main.setRemark(ctiBean.getRingBeginTime())
		main.setRingBegintime(ctiBean.getRingBeginTime());
		// main.setTelNum(ctiBean.get)
		// 存main表
		dao.saveEntity(main);
		List agentList = ctiBean.getAgeBean();
		for (int i = 0; i < agentList.size(); i++) {
			CcTalk talk = this.agentBean2CcTalkNoMain((AgentBean) agentList
					.get(i));
			talk.setCcMain(main);
			dao.saveEntity(talk);
		}
	}

	/**
	 * 将ctiBean转换成CcMain修改数据库
	 * 
	 * @param ctiBean
	 */
	public void updateCcLogMain(CtiBean ctiBean) {
		// TODO Auto-generated method stub
		CcMain main = (CcMain) dao.loadEntity(CcMain.class, ctiBean
				.getMainKey());
		Iterator talkIt = main.getCcTalks().iterator();
		MyLog.info("ccmainimpl 开始端口是.....   " + ctiBean.getBeginPort());
		if (!this.isNullOrEmpty(ctiBean.getBeginPort()))
			main.setBeginPost(ctiBean.getBeginPort());

		if (!this.isNullOrEmpty(ctiBean.getPostType()))
			main.setPostType(ctiBean.getPostType());

		if (ctiBean.getProcessEndTime() != null)
			main.setProcessEndtime(ctiBean.getProcessEndTime());

		if (!this.isNullOrEmpty(ctiBean.getProcessKeepTime()))
			main.setProcessKeeptime(ctiBean.getProcessKeepTime());

		if (!this.isNullOrEmpty(ctiBean.getProcessType()))
			main.setProcessType(ctiBean.getProcessType());

		if (ctiBean.getRingBeginTime() != null)
			main.setRingBegintime(ctiBean.getRingBeginTime());
		dao.updateEntity(main);
		this.printCcMain(main);
		// 处理字表talk
		List<AgentBean> beanList = ctiBean.getAgeBean();
		// 把数据库里的数据放map里方便查询
		Map<String, CcTalk> talkMap = new HashMap<String, CcTalk>();
		MyLog.info("talkMap size is .....   " + beanList.size());
		while (talkIt.hasNext()) {
			CcTalk talk = (CcTalk) talkIt.next();
			talkMap.put(talk.getId(), talk);
		}
		for (int i = 0; i < beanList.size(); i++) {
			AgentBean bean = beanList.get(i);
			CcTalk ct = talkMap.get(bean.getTalkId());
			// 数据库里的和ctiBean里的对象比较 有则更新 没有则 增加
			if (talkMap.get(bean.getTalkId()) != null) {
				this.dealWithAgentBean2CcTalkNoMainNull(bean, ct);
				//System.out.println("update:");
				this.printCcTalk(ct);
				dao.saveEntity(ct);
			} else {
				ct = this.agentBean2CcTalkNoMain(bean);
				ct.setCcMain(main);
				//System.out.println("insert:");
				this.printCcTalk(ct);
				dao.saveEntity(ct);
			}
		}
	}

	/**
	 * agentBean 转成CcTalk 不包括CcMain
	 * 
	 * @param agentBean
	 * @return
	 */
	private CcTalk agentBean2CcTalkNoMain(AgentBean agentBean) {
		// System.out.println("###agentBean.responstType"+agentBean.getRespondentType());
		CcTalk ct = new CcTalk();
		ct.setId(agentBean.getTalkId());

		ct.setDisplaceBegintime(agentBean.getDisplaceBegintime());
		ct.setDisplaceKeeptime(agentBean.getDisplcaeKeeptime());

		ct.setPhoneNum(agentBean.getPhoneNum());
		ct.setPostType(agentBean.getPostType());
		ct.setProcessType(agentBean.getProcessType());
		ct.setRecordPath(agentBean.getRecordPath());
		ct.setRespondent(agentBean.getRespondent());

		ct.setRespondentType(agentBean.getRespondentType());
		ct.setRingBegintime(agentBean.getRingBegintime());
		ct.setTouchBegintime(agentBean.getTouchBegintime());
		ct.setTouchEndtime(agentBean.getTouchEndtime());
		ct.setTouchKeeptime(agentBean.getTouchKeeptime());
		ct.setTouchPost(agentBean.getTouchPost());
		ct.setWaitingKeeptime(agentBean.getWaitTime());

		return ct;
	}

	/**
	 * 给做update用的 agentBean 转成CcTalk 不包括CcMain 如果agnetBean字段为null或空串也不包括
	 * 
	 * @param agentBean
	 * @return
	 */
	private void dealWithAgentBean2CcTalkNoMainNull(AgentBean agentBean,
			CcTalk ct) {
		if (agentBean.getDisplaceBegintime() != null)
			ct.setDisplaceBegintime(agentBean.getDisplaceBegintime());

		if (!this.isNullOrEmpty(agentBean.getDisplcaeKeeptime()))
			ct.setDisplaceKeeptime(agentBean.getDisplcaeKeeptime());

		if (!this.isNullOrEmpty(agentBean.getPhoneNum()))
			ct.setPhoneNum(agentBean.getPhoneNum());

		if (!this.isNullOrEmpty(agentBean.getPostType()))
			ct.setPostType(agentBean.getPostType());

		if (!this.isNullOrEmpty(agentBean.getProcessType()))
			ct.setProcessType(agentBean.getProcessType());

		if (!this.isNullOrEmpty(agentBean.getRecordPath()))
			ct.setRecordPath(agentBean.getRecordPath());

		if (!this.isNullOrEmpty(agentBean.getRespondent()))
			ct.setRespondent(agentBean.getRespondent());

		if (!this.isNullOrEmpty(ct.getRespondentType()))
			ct.setRespondentType(ct.getRespondentType());

		if (agentBean.getRingBegintime() != null)
			ct.setRingBegintime(agentBean.getRingBegintime());

		if (agentBean.getTouchBegintime() != null)
			ct.setTouchBegintime(agentBean.getTouchBegintime());

		if (agentBean.getTouchEndtime() != null)
			ct.setTouchEndtime(agentBean.getTouchEndtime());

		if (!this.isNullOrEmpty(agentBean.getTouchKeeptime()))
			ct.setTouchKeeptime(agentBean.getTouchKeeptime());

		if (!this.isNullOrEmpty(agentBean.getTouchPost()))
			ct.setTouchPost(agentBean.getTouchPost());

		if (!this.isNullOrEmpty(agentBean.getWaitTime()))
			ct.setWaitingKeeptime(agentBean.getWaitTime());

	}

	/**
	 * 看看字符串是不是空
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		}
		if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串装date
	 * 
	 * @param dateStr
	 * @return
	 */
	private Date str2Date(String dateStr) {
		if (dateStr != null && !"".equals(dateStr.trim())) {
			return TimeUtil.getTimeByStr(dateStr);
		}
		return null;
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

	private void printCcMain(CcMain main) {
		StringBuffer sb = new StringBuffer();
		sb.append("id:" + main.getId() + ",");
		sb.append("beginPost:" + main.getBeginPost() + ",");
		sb.append("postType:" + main.getPostType() + ",");
		sb.append("telNum:" + main.getTelNum() + ",");
		sb.append("ringBegintime:" + main.getRingBegintime() + ",");
		sb.append("processEndTime:" + main.getProcessEndtime() + ",");
		sb.append("recordBuildtime:" + main.getRecordBuildtime() + ",");
		sb.append("processKeeptime:" + main.getProcessKeeptime() + ",");
		sb.append("processType:" + main.getProcessType() + ",");
		sb.append("isDelete:" + main.getIsDelete() + ",");
		sb.append("remark:" + main.getRemark());
		System.out.println(sb.toString());
		System.out.println("\r\n");
	}

	private void printCcTalk(CcTalk talk) {
		StringBuffer sb = new StringBuffer();
		sb.append("Id:" + talk.getId() + ",");

		sb.append("CcMain():" + talk.getCcMain().getId() + ",");

		sb.append("RingBegintime:" + talk.getRingBegintime() + ",");

		sb.append("TouchBegintime:" + talk.getTouchBegintime() + ",");

		sb.append("WaitingKeeptime:" + talk.getWaitingKeeptime() + ",");

		sb.append("TouchEndtime:" + talk.getTouchEndtime() + ",");

		sb.append("TouchKeeptime:" + talk.getTouchKeeptime() + ",");

		sb.append("TouchPost:" + talk.getTouchPost() + ",");

		sb.append("RecordPath:" + talk.getRecordPath() + ",");

		sb.append("PostType:" + talk.getPostType() + ",");

		sb.append("Respondent:" + talk.getRespondent() + ",");

		sb.append("RespondentType:" + talk.getRespondentType() + ",");

		sb.append("PhoneNum:" + talk.getPhoneNum() + ",");

		sb.append("DisplaceBegintime:" + talk.getDisplaceBegintime() + ",");

		sb.append("DisplaceKeeptime:" + talk.getDisplaceKeeptime() + ",");

		sb.append("ProcessType:" + talk.getProcessType() + ",");

		sb.append("IsDelete:" + talk.getIsDelete() + ",");

		sb.append("Address:" + talk.getAddress() + ",");

		sb.append("Remark:" + talk.getRemark() + ",");
		System.out.println(sb.toString());
		System.out.println("\r\n");
	}
}
