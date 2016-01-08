/**
 * 
 */
package et.bo.callcenter.threecall.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.callcenter.portCompare.service.PortCompareService;
import et.bo.callcenter.threecall.service.ThreeCallService;
import et.po.EasyStateInfo;
import et.po.OperCustinfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zf
 * 
 */
public class ThreeCallImpl implements ThreeCallService {

	private BaseDAO dao = null;
	
	private PortCompareService pcs = null;

	public PortCompareService getPcs() {
		return pcs;
	}

	public void setPcs(PortCompareService pcs) {
		this.pcs = pcs;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.threecall.service.ThreeCallService#getThreeList()
	 */
	public String getThreeList() {
		// TODO Auto-generated method stub
		String result = "";
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EasyStateInfo.class);
		dc.add(Restrictions.or(Restrictions.eq("threeState", "1"), Restrictions
				.eq("threeState", "2")));
		mq.setDetachedCriteria(dc);
		Object[] o = dao.findEntity(mq);
		StringBuilder sb = new StringBuilder();
		sb.append("three:");
		for (int i = 0; i < o.length; i++) {
			EasyStateInfo esi = (EasyStateInfo) o[i];
			String outingNum = "";// 外线号
			String agentNum = "";// 专家号
			String threePort = "";// 第三方端口
			outingNum = esi.getIncommingCall();
			agentNum = esi.getThreeCall();
			threePort = esi.getThreePort();
			sb.append(threePort);
			sb.append(",");
			sb.append(outingNum);
			sb.append("-");
			sb.append(agentNum);
			sb.append(":");
		}
		result = sb.toString();
		return result;
	}

	public String getAllInfo(String agentUser, String threePort) {
		// TODO Auto-generated method stub
		String result = "";
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(EasyStateInfo.class);
		dc.add(Restrictions.eq("threePort", threePort));
		mq.setDetachedCriteria(dc);
		Object[] o = dao.findEntity(mq);
		StringBuilder sb = new StringBuilder();
		sb.append("all:");
		for (int i = 0; i < o.length; i++) {
			EasyStateInfo esi = (EasyStateInfo) o[i];
			String outingNum = esi.getIncommingCall();// 外线号
			String expertNum = esi.getThreeCall();// 专家号

			sb.append(getExpertInfo(expertNum));
			sb.append(",");
			sb.append(getOutingInfo(outingNum));
			sb.append(",");
			sb.append(agentUser);
		}
		result = sb.toString();
		return result;
	}

	/**
	 * 得到外线号对应的信息
	 * 
	 * @param outingNum
	 * @return
	 */
	private String getOutingInfo(String outingNum) {
		String str = "";
		MyQuery mq = new MyQueryImpl();
		// 客户信息表
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + outingNum
				+ "%"), Restrictions.or(Restrictions.like("custTelMob", "%"
				+ outingNum + "%"), Restrictions.like("custTelWork", "%"
				+ outingNum + "%"))));
		dc.add(Restrictions.eq("dictCustType", "SYS_TREE_0000002109"));
		mq.setDetachedCriteria(dc);
		Object[] o = dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			OperCustinfo oci = (OperCustinfo) o[i];
			str = oci.getCustAddr();
			str = str + "-" + oci.getCustName();
		}
		return str;
	}

	/**
	 * 得到专家信息.
	 * 
	 * @param expertNum the expert num
	 * 
	 * @return the expert info
	 */
	private String getExpertInfo(String expertNum) {
		String str = "";
		MyQuery mq = new MyQueryImpl();
		// 客户信息表
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);
		dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + expertNum
				+ "%"), Restrictions.or(Restrictions.like("custTelMob", "%"
				+ expertNum + "%"), Restrictions.like("custTelWork", "%"
				+ expertNum + "%"))));
		dc.add(Restrictions.eq("dictCustType", "SYS_TREE_0000002103"));
		mq.setDetachedCriteria(dc);
		Object[] o = dao.findEntity(mq);
		for (int i = 0; i < o.length; i++) {
			OperCustinfo oci = (OperCustinfo) o[i];
			str = oci.getExpertType();
			str = str + "-" + oci.getCustName();
		}
		return str;
	}

	public String getInnerPort(String ip) {
		// TODO Auto-generated method stub
		String innerPort = pcs.getPortByIp(ip);
		return innerPort;
	}
}
