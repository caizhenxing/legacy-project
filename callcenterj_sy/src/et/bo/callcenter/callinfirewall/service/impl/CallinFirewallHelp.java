package et.bo.callcenter.callinfirewall.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.CcFirewall;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class CallinFirewallHelp extends MyQueryImpl {
	/**
	 * @describe 规则列表查询
	 */
	public MyQuery callinFirewallQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();

		DetachedCriteria dc = DetachedCriteria.forClass(CcFirewall.class);
		// hql.append("from CcFirewall cf where cf.id = cf.id");
		String beginTime = (String)dto.get("beginTime");
		String endTime = (String)dto.get("endTime");
		if(beginTime!=null&&"".equals(beginTime))
		{
			beginTime = null;
		}
		if(endTime!=null&&"".equals(endTime))
		{
			endTime = null;
		}
		if(beginTime!=null&&endTime!=null)
		{
			dc.add(Restrictions.between("beginTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd"), TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
		}
//		else if(beginTime!=null)
//		{
//			dc.add(Restrictions.like("beginTime", TimeUtil.getTimeByStr(beginTime, "yyyy-MM-dd")));
//		}
//		else if(endTime!=null)
//		{
//			dc.add(Restrictions.like("beginTime", TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd")));
//		}
		if (!dto.get("callinNum").toString().equals("")) {
			// hql.append(" and cf.phoneNum like '%"+
			// dto.get("callinNum").toString() +"%'");
			dc.add(Restrictions.like("phoneNum", "%"
					+ dto.get("callinNum").toString() + "%"));
		}
		// else
		// if(!dto.get("callinNumBegin").toString().equals("")&&!dto.get("callinNumEnd").toString().equals("")){
		// dc.add(Restrictions.between("", arg1, arg2))
		// }

		if (!dto.get("isPass").toString().equals("")) {
			// hql.append(" and cf.isPass = '"+ dto.get("isPass").toString()
			// +"'");
			dc.add(Restrictions.eq("isPass", dto.get("isPass").toString()));
		}
		// if(!dto.get("isAvailable").toString().equals("")){
		// dc.add(Restrictions.eq("isAvailable",
		// dto.get("isAvailable").toString()));
		// }
		dc.addOrder(Order.desc("beginTime"));
		// mq.setHql(hql.toString());
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe 号码是否在黑名单内
	 */
	public MyQuery IfInBlacklist(String phoneNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcFirewall.class);
		dc.add(Restrictions.eq("phoneNum", phoneNum));
		// dc.add(Restrictions.ge("callinNumBegin", phoneNum));
		// dc.add(Restrictions.le("callinNumEnd", phoneNum));
		dc.add(Restrictions.eq("isPass", "N"));
		dc.add(Restrictions.eq("isAvailable", "Y"));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;
	}

	/**
	 * @describe 是否有相同号码
	 */
	public MyQuery ifHaveSameNum(String phoneNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcFirewall.class);
		dc.add(Restrictions.eq("phoneNum", phoneNum));
		mq.setDetachedCriteria(dc);
		mq.setFetch(1);
		return mq;
	}
}
