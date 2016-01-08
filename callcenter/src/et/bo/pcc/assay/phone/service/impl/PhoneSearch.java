/**
 * 	@(#)PhoneSearch.java   Oct 12, 2006 11:01:54 AM
 *	 。 
 *	 
 */
package et.bo.pcc.assay.phone.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import et.po.CcLog;
import et.po.PoliceCallin;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 12, 2006
 * @see
 */
public class PhoneSearch extends MyQueryImpl {

	/**
	 * @describe 电话时间统计
	 * @param 类型
	 *            InemailInfo inemailInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchPhone(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
		String isvalidin = (String) dto.get("isvalidin");
		if (isvalidin != null && !isvalidin.trim().equals("")) {
			dc.add(Expression.eq("isValidIn", isvalidin));
			// if (isvalidin.equals("SYS_TREE_0000000182")) {
			// dc.add(Expression.eq("isValidIn", "0"));
			// }else if(isvalidin.equals("SYS_TREE_0000000183")){
			// dc.add(Expression.eq("isValidIn", "1"));
			// }else if(isvalidin.equals("SYS_TREE_0000000184")){
			// dc.add(Expression.eq("isValidIn", "2"));
			// }else{
			// dc.add(Expression.eq("isValidIn", dto.get("isvalidin")));
			// }
		}
		mq.setDetachedCriteria(dc);
		// String hql = "";
		// mq.setHql(hql);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * @describe 电话时间统计
	 * @param 类型
	 *            InemailInfo inemailInfo
	 * @return 类型
	 * 
	 */
	public MyQuery searchPhoneForCclog(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcLog.class);
		if (!((dto.get("begintime") == null) || "".equals(dto.get("begintime")
				.toString()))
				&& !((dto.get("endtime") == null) || "".equals(dto.get(
						"endtime").toString()))) {
			dc
					.add(Restrictions.between("beginTime", TimeUtil
							.getTimeByStr(dto.get("begintime").toString(),
									"yyyy-MM-dd"), TimeUtil.getTimeByStr(dto
							.get("endtime").toString(), "yyyy-MM-dd")));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 将字符串转换成一个日期
	 * 
	 * @param startDate
	 * @return
	 */
	private Date getSwitchDate(String startDate) {
		Date dt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			dt = sdf.parse(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	/**
	 * 查询电话根据时间
	 * 
	 * @param
	 * @version Oct 13, 2006
	 * @return
	 */
	public MyQuery searchPhoneByTime(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc = DetachedCriteria.forClass(CcLog.class);
		// String operatetime = (String)dto.get("operatetime");
		// if (operatetime.equals("3")) {
		// dc.add(Expression.between("betweenTime", new Integer("0"), new
		// Integer("180")));
		// }
		// if (operatetime.equals("5")) {
		// dc.add(Expression.between("betweenTime", new Integer("180"), new
		// Integer("300")));
		// }
		// if (operatetime.equals("10")) {
		// dc.add(Expression.between("betweenTime", new Integer("300"), new
		// Integer("600")));
		// }
		// if (operatetime.equals("30")) {
		// dc.add(Expression.between("betweenTime", new Integer("600"), new
		// Integer("1800")));
		// }
		// if (operatetime.equals("30m")) {
		// dc.add(Expression.gt("betweenTime", new Integer(1800)));
		// }
		// if (!((dto.get("begintime") == null) || "".equals(dto.get(
		// "begintime").toString()))
		// && !((dto.get("endtime") == null) || "".equals(dto.get(
		// "endtime").toString()))) {
		// dc.add(Restrictions.between("beginTime", TimeUtil.getTimeByStr(
		// dto.get("begintime").toString(), "yyyy-MM-dd"),
		// TimeUtil.getTimeByStr(dto.get("endtime").toString(),
		// "yyyy-MM-dd")));
		// }
		// mq.setDetachedCriteria(dc);
		StringBuilder hql = new StringBuilder();
		hql.append("select c from CcLog c,PoliceCallin p where c.id=p.id");
		String operatetime = (String) dto.get("operatetime");
		if (operatetime.equals("3")) {
			hql.append(" and c.betweenTime >=0 and c.betweenTime<=180");
		}
		if (operatetime.equals("5")) {
			hql.append(" and c.betweenTime >=180 and c.betweenTime<=300");
		}
		if (operatetime.equals("10")) {
			hql.append(" and c.betweenTime >=300 and c.betweenTime<=600");
		}
		if (operatetime.equals("30")) {
			hql.append(" and c.betweenTime >=600 and c.betweenTime<=1800");
		}
		if (operatetime.equals("30m")) {
			hql.append(" and c.betweenTime >=1800");
		}
		if (!((dto.get("begintime") == null) || "".equals(dto.get("begintime")
				.toString()))
				&& !((dto.get("endtime") == null) || "".equals(dto.get(
						"endtime").toString()))) {
			hql
			.append(" and c.beginTime>=to_date('"+dto.get("begintime").toString()+"','yyyy-mm-dd') and c.beginTime<=to_date('"+dto.get("endtime").toString()+"','yyyy-mm-dd')");
		}
		String isvalidin = (String) dto.get("isvalidin");
		if (isvalidin != null && !isvalidin.trim().equals("")) {
			if (isvalidin.equals("SYS_TREE_0000000182")) {
				hql.append(" and p.isValidIn = '0'");
			} else if (isvalidin.equals("SYS_TREE_0000000183")) {
				hql.append(" and p.isValidIn = '1'");
			} else if (isvalidin.equals("SYS_TREE_0000000184")) {
				hql.append(" and p.isValidIn = '2'");
			}
		}
		mq.setHql(hql.toString());
//		 mq.setParameter("a",
//				 TimeUtil.getTimeByStr(dto.get("begintime").toString()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
//				 mq.setParameter("b",
//				 TimeUtil.getTimeByStr(dto.get("endtime").toString()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * 查询电话根据时间
	 * 
	 * @param
	 * @version Oct 13, 2006
	 * @return
	 */
	public MyQuery searchPhoneByTimeSize(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		// DetachedCriteria dc = DetachedCriteria.forClass(CcLog.class);
		// String operatetime = (String)dto.get("operatetime");
		// if (operatetime.equals("3")) {
		// dc.add(Expression.between("betweenTime", new Integer("0"), new
		// Integer("180")));
		// }
		// if (operatetime.equals("5")) {
		// dc.add(Expression.between("betweenTime", new Integer("180"), new
		// Integer("300")));
		// }
		// if (operatetime.equals("10")) {
		// dc.add(Expression.between("betweenTime", new Integer("300"), new
		// Integer("600")));
		// }
		// if (operatetime.equals("30")) {
		// dc.add(Expression.between("betweenTime", new Integer("600"), new
		// Integer("1800")));
		// }
		// if (operatetime.equals("30m")) {
		// dc.add(Expression.gt("betweenTime", new Integer(1800)));
		// }
		// if (!((dto.get("begintime") == null) || "".equals(dto.get(
		// "begintime").toString()))
		// && !((dto.get("endtime") == null) || "".equals(dto.get(
		// "endtime").toString()))) {
		// dc.add(Restrictions.between("beginTime", TimeUtil.getTimeByStr(
		// dto.get("begintime").toString(), "yyyy-MM-dd"),
		// TimeUtil.getTimeByStr(dto.get("endtime").toString(),
		// "yyyy-MM-dd")));
		// }
		// mq.setDetachedCriteria(dc);
		StringBuilder hql = new StringBuilder();
		hql.append("select c from CcLog c,PoliceCallin p where c.id=p.id");
		String operatetime = (String) dto.get("operatetime");
		if (operatetime.equals("3")) {
			hql.append(" and c.betweenTime >=0 and c.betweenTime<=180");
		}
		if (operatetime.equals("5")) {
			hql.append(" and c.betweenTime >=180 and c.betweenTime<=300");
		}
		if (operatetime.equals("10")) {
			hql.append(" and c.betweenTime >=300 and c.betweenTime<=600");
		}
		if (operatetime.equals("30")) {
			hql.append(" and c.betweenTime >=600 and c.betweenTime<=1800");
		}
		if (operatetime.equals("30m")) {
			hql.append(" and c.betweenTime >=1800");
		}
		if (!((dto.get("begintime") == null) || "".equals(dto.get("begintime")
				.toString()))
				&& !((dto.get("endtime") == null) || "".equals(dto.get(
						"endtime").toString()))) {
			hql
					.append(" and c.beginTime>="+TimeUtil.getTimeByStr(dto.get("begintime").toString()+" 00:00:00","yyyy-MM-dd HH:mm:ss")+" and c.beginTime<=" +
							"" +TimeUtil.getTimeByStr(dto.get("endtime").toString()+" 00:00:00","yyyy-MM-dd HH:mm:ss")
							+"");
		}
		String isvalidin = (String) dto.get("isvalidin");
		if (isvalidin != null && !isvalidin.trim().equals("")) {
			if (isvalidin.equals("SYS_TREE_0000000182")) {
				hql.append(" and p.isValidIn = '0'");
			} else if (isvalidin.equals("SYS_TREE_0000000183")) {
				hql.append(" and p.isValidIn = '1'");
			} else if (isvalidin.equals("SYS_TREE_0000000184")) {
				hql.append(" and p.isValidIn = '2'");
			}
		}
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

}
