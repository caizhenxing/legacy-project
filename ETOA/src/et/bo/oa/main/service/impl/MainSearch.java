/**
 * 	@(#)MainSearch.java   Sep 13, 2006 6:49:08 PM
 *	 。 
 *	 
 */
package et.bo.oa.main.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.InemailInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Sep 13, 2006
 * @see
 */
public class MainSearch extends MyQueryImpl {

	/**
	 * 查询首页内部邮件列表
	 * @param
	 * @version Sep 13, 2006
	 * @return
	 */
    public MyQuery searchEmailInfo(String username) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "n".toUpperCase()));
        dc.add(Expression.eq("takeUser",username));
        dc.addOrder(Order.desc("id"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(0);
        mq.setFetch(4);
        return mq;
    }
    
    /**
     * 查询首页列表信息
     * @param
     * @version Sep 15, 2006
     * @return
     */
    public MyQuery searchIndexEmailList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "n".toUpperCase()));
        dc.add(Expression.eq("takeUser",dto.get("sendUser").toString()));
        dc.addOrder(Order.desc("id"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
	
}
