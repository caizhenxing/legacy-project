/**
 * @(#)OperatorStatisticHelp.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.pcc.operatorStatistic.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperatorWorkInfo;
import et.po.SysGroup;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * @describe 座席工作情况查询帮助
 * @author 叶浦亮
 * @version 1.0, 2006-10-11//
 * @see
 */
public class OperatorStatisticHelp extends MyQueryImpl {
    /**
	 * @describe 
	 */
	public MyQuery operatorWorkInfoQuery(IBaseDTO dto, String userId, String operatorState){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperatorWorkInfo.class);
		if (!((dto.get("beginTime") == null) || "".equals(dto.get(
		"beginTime").toString()))
		&& !((dto.get("endTime") == null) || "".equals(dto.get(
				"endTime").toString()))) {

	    dc.add(Restrictions.between("operateDate", TimeUtil.getTimeByStr(
	    		dto.get("beginTime").toString(), "yyyy-MM-dd"),
			TimeUtil.getTimeByStr(dto.get("endTime").toString(),
					"yyyy-MM-dd")));
}
		dc.add(Restrictions.eq("operator", userId));
		dc.add(Restrictions.eq("operatorState",operatorState));
		mq.setDetachedCriteria(dc);
        return mq;
	}  
	/**
	 * @describe 统计人数查询
	 */
	public MyQuery workInfoPersonQuery(IBaseDTO dto,SysGroup sg, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		if(!dto.get("operator").toString().equals("")){
			dc.add(Restrictions.eq("userId",dto.get("operator").toString()));
		}
		dc.add(Restrictions.eq("sysGroup", sg));
		mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
	}
	/**
	 * @describe 得到人员列表
	 */
	public MyQuery getWorkInfoPersonList(SysGroup sg){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("sysGroup", sg));
		mq.setDetachedCriteria(dc);
        return mq;
	}
	/**
	 * @describe 工作情况分项统计
	 */
	public MyQuery getWorkInfoByDate(Date date, String userId, String operatorState1, String operatorState2){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperatorWorkInfo.class);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(ca.DATE, 1);
		Date endDate = ca.getTime();
//		String Sdate =TimeUtil.getTheTimeStr(date, "yyyy-MM-dd");
//		String Edate =TimeUtil.getTheTimeStr(endDate, "yyyy-MM-dd");
//		System.out.println(Sdate+"时间+"+TimeUtil.getTimeByStr(Sdate, "yyyy-MM-dd"));
//		endDate = TimeUtil.getTimeByStr(Edate, "yyyy-MM-dd");
//		System.out.println(date.toString()+endDate.toString());
		dc.add(Restrictions.eq("operator", userId));
		dc.add(Restrictions.between("operateDate", TimeUtil.getTimeByStr(date.toLocaleString(), "yyyy-MM-dd"), TimeUtil.getTimeByStr(endDate.toLocaleString(), "yyyy-MM-dd")));
//		System.out.println("+++++++++"+TimeUtil.getTimeByStr(date, "yyyy-MM-dd"));
//		dc.add(Restrictions.eq("operateDate", TimeUtil.getTimeByStr(date, "yyyy-MM-dd")));
//		dc.add(Restrictions.eq("operateDate", TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(date), "yyyy-MM-dd")));
//		dc.add(Restrictions.eq("operator", userId));
		dc.add(Restrictions.or(Restrictions.eq("operatorState", operatorState1), Restrictions.eq("operatorState", operatorState2)));
//		Object[] objs = {operatorState1,operatorState2};
//		System.out.println(operatorState1+operatorState2);
//		dc.add(Restrictions.in("operatorState",objs));
		dc.addOrder(Order.asc("operateDate"));
		mq.setDetachedCriteria(dc);
        return mq;
	}
	
	
}
