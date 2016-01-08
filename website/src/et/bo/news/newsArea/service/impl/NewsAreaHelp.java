/**
 * 	@(#)NewsAreaHelp.java   2007-1-23 ����02:51:30
 *	 �� 
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
 * @author Ҷ����
 * @version 2007-1-23
 * @see
 */
public class NewsAreaHelp extends MyQueryImpl {
	/**
	 * @describe �����ѯ
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
	 * @describe ���������б��ѯ
	 */
	public MyQuery AreaQuerySelect(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(NewsArea.class);
		mq.setDetachedCriteria(dc);
        return mq;
	}
}
