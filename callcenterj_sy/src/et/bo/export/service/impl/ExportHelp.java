/*
 * @(#)CallbackHelp.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.export.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperCallback;
import et.po.OperExportInfo;
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
public class ExportHelp extends MyQueryImpl{
	
	public MyQuery exportQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperExportInfo.class);		
		
		//专家姓名
		String str = (String)dto.get("name");
		if(!str.equals("")){
			dc.add(Restrictions.like("name","%"+str+"%"));
		}
		//专家简介
		str = (String)dto.get("remark");
		if(!str.equals("")){
			dc.add(Restrictions.like("remark","%"+str+"%"));
		}
		dc.addOrder(Order.desc("addtime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
	
	
	public MyQuery exportQuery2(){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperExportInfo.class);		
		dc.addOrder(Order.asc("addtime"));
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		return mq;
	}
	

}
