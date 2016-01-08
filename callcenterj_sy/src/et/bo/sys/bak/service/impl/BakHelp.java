package et.bo.sys.bak.service.impl;
//
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.DbBackup;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
//
public class BakHelp extends MyQueryImpl {
//	/**
//	 * @describe 取得端口对照表所有记录
//	 */
	public MyQuery bakQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(DbBackup.class);
		String dbType =(String)dto.get("dbtype");
		String beginDate = (String)dto.get("begindate");
		String endDate = (String)dto.get("enddate");
		String beginTime = (String)dto.get("begintime");
		String endTime = (String)dto.get("endtime");
		String remark = (String)dto.get("remark");
		
		if(!dbType.equals("")){
			dc.add(Restrictions.eq("baktype", dbType));
		}
		
		if(!remark.equals("")){
			dc.add(Restrictions.like("remark", remark));
		}
		if((!endDate.equals("")) && (!beginDate.equals("")))
		{
			dc.add(Restrictions.ge("bakdate",TimeUtil.getTimeByStr(dto.get("begindate").toString(),"yyyy-MM-dd")));
			
			dc.add(Restrictions.le("bakdate",TimeUtil.getTimeByStr(dto.get("enddate").toString(),"yyyy-MM-dd")));
		}
		
		if((!beginTime.equals("")) && (!endTime.equals("")))
		{
			dc.add(Restrictions.ge("baktime",beginTime));
			
			dc.add(Restrictions.le("baktime",endTime));
		}
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;       
	}   
  
}
