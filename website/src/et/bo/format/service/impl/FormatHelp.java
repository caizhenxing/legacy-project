/**
 * 	@(#)FormatHelp.java   2007-1-22 ����10:18:49
 *	 �� 
 *	 
 */
package et.bo.format.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import et.po.NewsStyle;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @describe
 * @author Ҷ����
 * @version 2007-1-22
 * @see
 */
public class FormatHelp extends MyQueryImpl {
	/**
	 * @describe ��ʽ��ѯ
	 */
	public MyQuery formatQuery(IBaseDTO dto, PageInfo pi){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(NewsStyle.class);
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
        return mq;
	}   
	/**
	 * @describe ��ʽ��ѯ
	 */
	public MyQuery styleQuery(){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(NewsStyle.class);
		mq.setDetachedCriteria(dc);
        return mq;
	}   

}
