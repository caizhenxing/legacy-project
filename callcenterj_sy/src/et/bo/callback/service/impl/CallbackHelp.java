/*
 * @(#)CallbackHelp.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.callback.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.OperCallback;
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
public class CallbackHelp extends MyQueryImpl{
	
	public MyQuery custinfoQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperCallback.class);
		
		//首先确定“未删除”条件
		dc.add(Restrictions.eq("isDelete","0"));
		//回访内容
		String str = (String)dto.get("callback_content");
		if(!str.equals("")){
			dc.add(Restrictions.like("callbackContent","%"+str+"%"));
		}
		//回访备注
		str = (String)dto.get("remark");
		if(!str.equals("")){
			dc.add(Restrictions.like("remark","%"+str+"%"));
		}
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}
	

}
