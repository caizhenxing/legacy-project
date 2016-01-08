/**
 * 	@(#)FileManagerHelper.java   2006-9-4 ÏÂÎç05:50:29
 *	 ¡£ 
 *	 
 */
package et.bo.oa.file.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.FileManager;
import et.po.FileRight;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author 
 * @version 2006-9-4
 * @see
 */
public class FileManagerHelper {

	public MyQuery createClassTreeQuery()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FileManager.class);
		//dc.add(Restrictions.eq("tagShow","1"));
		dc.add(Restrictions.eq("isAvailable","1"));
		dc.addOrder(Order.asc("id"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery createEditionQuery(String name)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FileManager.class);
		//dc.add(Restrictions.eq("tagShow","1"));
		dc.add(Restrictions.eq("name",name));
		dc.addOrder(Order.desc("fileEdition"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	public MyQuery createRightQuery(String user,String id)
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FileRight.class);
		//dc.add(Restrictions.eq("tagShow","1"));
		dc.add(Restrictions.eq("userId",user));
		dc.addOrder(Order.desc("fileEdition"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
}
