/*
 * @(#)CustinfoHelp.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.custinfo.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCustinfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * 客户管理——查询
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */
public class CustinfoHelp extends MyQueryImpl {
	
	private String addoneDate(String endTime) {
		Date date = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(TimeUtil.getTimeByStr(endTime, "yyyy-MM-dd"));
		ca.add(ca.DATE, 1);
		date = ca.getTime();
		endTime = TimeUtil.getTheTimeStr(date, "yyyy-MM-dd HH:mm:ss");
		return endTime;
	}
	
	private String addHMS(String stime) {
		String hmsTime = "";
		hmsTime = stime + " 00:00:00";
		return hmsTime;
	}

	public MyQuery custinfoQuery(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// StringBuilder hql = new StringBuilder();
		// hql.append("from OperCustinfo where 1=1");

		// 首先确定“未删除”条件
		// dc.add(Restrictions.eq("isDelete", "0"));

		// 姓名
		String str = (String) dto.get("cust_name");
		if (!str.equals("")) {
			dc.add(Restrictions.like("custName", "%" + str + "%"));
			// hql.append(" and custName like %" + str + "%");
		}
		// 电话，or宅电，or手机，or办公电话
		str = (String) dto.get("cust_tel_home");
		if (!str.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + str
					+ "%"), Restrictions.or(Restrictions.like("custTelMob", "%"
					+ str + "%"), Restrictions.like("custTelWork", "%" + str
					+ "%"))));
			// hql.append(" or custTelHome like %" + str
			// + "% or custTelMob like %" + str
			// + "% or custTelWork like %" + str + "%");
		}
		// 客户行业
		str = (String) dto.get("cust_voc");
		if (!str.equals("")) {
			dc.add(Restrictions.like("dictCustVoc", "%" + str + "%"));
			// hql.append(" and dictCustVoc like %" + str + "%");
		}
		dc.add(Restrictions.or(Restrictions.isNull("isDelete"), Restrictions.eq("isDelete", "0")));
		// 客户类型
		str = (String) dto.get("cust_type");
		if (!str.equals("")) {
			dc.add(Restrictions.eq("dictCustType", str));
			// hql.append(" and dictCustType = '" + str +"'");
		}
		// 客户类型
		str = (String) dto.get("cust_rid");
		if (!str.equals("")) {
			dc.add(Restrictions.eq("custRid", str));
			// hql.append(" and dictCustType = '" + str +"'");
		}

		dc.add(Restrictions.or(Restrictions.eq("dictCustType",
				"SYS_TREE_0000002109"), Restrictions.or(Restrictions.eq(
				"dictCustType", "SYS_TREE_0000002108"), Restrictions.eq(
				"dictCustType", "SYS_TREE_0000002104"))));

		// 时间
		String btime = (String) dto.get("beginTime");
		String etime = (String) dto.get("endTime");

		// if(!"".equals(btime) && !"".equals(etime)) {
		// dc.add(Restrictions.between("addtime", TimeUtil.getTimeByStr(btime),
		// TimeUtil.getTimeByStr(etime)));
		// }
		// 开始时间不等于空
		if (!"".equals(btime)) {
			dc.add(Restrictions.ge("addtime", TimeUtil.getTimeByStr(addHMS(btime))));
		}
		// 结束时间不等于空
		if (!"".equals(etime)) {
			dc.add(Restrictions.le("addtime", TimeUtil.getTimeByStr(addoneDate(etime))));
		}

		// 地址
		String cust_addr = (String) dto.get("cust_addr");
		if (!cust_addr.equals("")) {
			dc.add(Restrictions.like("custAddr", "%" + cust_addr + "%"));
		}
		dc.add(Restrictions.isNull("modifyTime"));
		dc.addOrder(Order.desc("addtime"));

		mq.setDetachedCriteria(dc);

		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}

	public MyQuery phoneinfoQuery(IBaseDTO dto, PageInfo pi) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// 首先确定“未删除”条件
		dc.add(Restrictions.eq("isDelete", "0"));

		// 姓名
		String str = (String) dto.get("cust_name");
		if (!str.equals("")) {
			dc.add(Restrictions.like("custName", "%" + str + "%"));
		}
		dc.add(Restrictions.or(Restrictions.isNull("isDelete"), Restrictions.eq("isDelete", "0")));
		// 电话，or宅电，or手机，or办公电话
		str = (String) dto.get("cust_tel_home");
		if (!str.equals("")) {
			dc.add(Restrictions.or(Restrictions.like("custTelHome", "%" + str
					+ "%"), Restrictions.or(Restrictions.like("custTelMob", "%"
					+ str + "%"), Restrictions.like("custTelWork", "%" + str
					+ "%"))));
		}
		// 客户行业
		str = (String) dto.get("cust_voc");
		if (!str.equals("")) {
			dc.add(Restrictions.like("dictCustVoc", "%" + str + "%"));
		}
		// 客户类型
		str = (String) dto.get("cust_type");
		if (!str.equals("1") && !str.equals("")) {
			dc.add(Restrictions.eq("dictCustType", str));
		}
		// dc.add(Restrictions.or(Restrictions.eq("dictCustType", ""),
		// Restrictions.eq("dictCustType", "")));

		dc.addOrder(Order.desc("custId"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());

		return mq;
	}

	public MyQuery openwinQuery(String tel) {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		if (!tel.equals("")) {
			dc.add(Restrictions.or(Restrictions.eq("custTelHome", tel),
					Restrictions.or(Restrictions.eq("custTelMob", tel),
							Restrictions.eq("custTelWork", tel))));
		}

		mq.setDetachedCriteria(dc);

		return mq;
	}

	public MyQuery allQuery() {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// 首先确定“未删除”条件
		dc.add(Restrictions.eq("isDelete", "0"));

		mq.setDetachedCriteria(dc);

		return mq;
	}

	/**
	 * 查询所有的专家
	 * 
	 * @param dto
	 * @param pi
	 * @return
	 */
	public MyQuery custinfoExpertAllQuery() {

		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCustinfo.class);

		// dict_cust_type = SYS_TREE_0000000684 是专家
		dc.add(Restrictions.eq("dictCustType", "SYS_TREE_0000002103"));

		mq.setDetachedCriteria(dc);

		return mq;
	}
}
