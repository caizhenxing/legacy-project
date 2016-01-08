/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.group.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.SysGroup;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class GroupHelp {

	public MyQuery listGroupMQ(IBaseDTO dto)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SysGroup.class);
		if(null !=dto.get("name") && !"".equals(dto.get("name").toString()))
			dc.add(Expression.like("name","%"+(String)dto.get("name")+"%"));	
		dc.add(Expression.ne("delMark","-1"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		return mq;
		
	}
	
	public MyQuery listGroupMQ(IBaseDTO dto, PageInfo pi)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SysGroup.class);
		if(null !=dto.get("name") && !"".equals(dto.get("name").toString()))
			dc.add(Expression.like("name","%"+(String)dto.get("name")+"%"));		
		dc.add(Expression.ne("delMark","-1"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
		return mq;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

}
