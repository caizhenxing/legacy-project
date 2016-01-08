/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.checkwork.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.CheckworkAbsence;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class OutStateSreachHelp {
	/**
	 * 
	 * @describe 请假,外出,出差 状态查询
	 * @param
	 * @return MyQuery
	 * 
	 */
	public MyQuery getOutStateSreach() {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkAbsence.class);
		String date=TimeUtil.getTheTimeStr(TimeUtil.getNowTime());
//		dc.add(Restrictions.between("beginTime", TimeUtil.getTimeByStr(date, "yyyy-MM-dd HH:mm:ss"), TimeUtil
//							.getTimeByStr(date,
//									"yyyy-MM-dd HH:mm:ss")));
		dc.add(Restrictions.gt("endTime", TimeUtil.getTimeByStr(date, "yyyy-MM-dd HH:mm:ss")));
		dc.add(Restrictions.lt("beginTime", TimeUtil.getTimeByStr(date, "yyyy-MM-dd HH:mm:ss")));
		dc.addOrder(Order.desc("signDate"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(0);
		myQuery.setFetch(5);
		return myQuery;
	}
	public MyQuery getAllOutStateSreach() {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkAbsence.class);
		String date=TimeUtil.getTheTimeStr(TimeUtil.getNowTime());
		dc.add(Restrictions.gt("endTime", TimeUtil.getTimeByStr(date, "yyyy-MM-dd HH:mm:ss")));
		dc.add(Restrictions.lt("beginTime", TimeUtil.getTimeByStr(date, "yyyy-MM-dd HH:mm:ss")));
		dc.addOrder(Order.desc("signDate"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}
}
