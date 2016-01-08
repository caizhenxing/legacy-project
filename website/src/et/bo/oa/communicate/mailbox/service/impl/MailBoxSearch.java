/**
 * 	@(#)MailBoxSearch.java   Aug 30, 2006 8:06:38 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.mailbox.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.EmailBox;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public class MailBoxSearch extends MyQueryImpl {
	
	/**
	 * @describe 邮箱信息
	 * @param  类型  
	 * @return 类型  
	 * 
	 */

    public MyQuery searchMailBoxList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(EmailBox.class);
        if (dto.get("name")!=null&&!dto.get("name").toString().equals("")) {
            dc.add(Expression.eq("name",dto.get("name").toString()));
        }
        if (dto.get("emailaddress")!=null&&!dto.get("emailaddress").toString().equals("")) {
            dc.add(Expression.eq("emailAddress",dto.get("emailaddress").toString()));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }

}
