/**
 * 	@(#)NewsAreaHelp.java   2007-1-23 下午02:51:30
 *	 。 
 *	 
 */
package et.bo.news.newsArea.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.NewsArea;
import et.po.NewsStyle;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @describe
 * @author 叶浦亮
 * @version 2007-1-23
 * @see
 */
public class NewsAreaHelp extends MyQueryImpl {
	/**
	 * @describe 区域查询
	 */
	public MyQuery AreaQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(NewsArea.class);
		String newsAreaName = (String)dto.get("newsAreaName");
		if(!newsAreaName.equals("")){
			dc.add(Restrictions.like("newsAreaName", "%"+newsAreaName+"%"));
		}
		String styleId = (String)dto.get("styleId");
		if(!styleId.equals("")){
			dc.add(Restrictions.eq("styleId", styleId));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
        return mq;
	}
	/**
	 * @describe 区域下拉列表查询
	 */
	public MyQuery AreaQuerySelect(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(NewsArea.class);
		mq.setDetachedCriteria(dc);
        return mq;
	}
}
