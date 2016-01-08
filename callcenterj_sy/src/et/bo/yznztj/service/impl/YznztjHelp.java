/*
 * @(#)CallbackHelp.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.yznztj.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperYznztj;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>客户管理——查询</p>
 * 
 * @version 2008-04-01
 * @author nie
 */
public class YznztjHelp extends MyQueryImpl{
	
	public MyQuery yznztjQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperYznztj.class);		
		
		
		String str = (String)dto.get("sort");
		if(!str.equals("")){
			dc.add(Restrictions.like("sort","%"+str+"%"));
		}
		
		str = (String)dto.get("productName");
		if(!str.equals("")){
			dc.add(Restrictions.like("productName","%"+str+"%"));
		}
		str = (String)dto.get("trait");
		if(!str.equals("")){
			dc.add(Restrictions.like("trait","%"+str+"%"));
		}
		str = (String)dto.get("scope");
		if(!str.equals("")){
			dc.add(Restrictions.like("scope","%"+str+"%"));
		}
		dc.addOrder(Order.desc("addTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
	
	public MyQuery yznztjQuery2(){
		MyQuery mq = new MyQueryImpl();
		
		DetachedCriteria dc = DetachedCriteria.forClass(OperYznztj.class);		
		dc.addOrder(Order.desc("addTime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		return mq;
	}

}
