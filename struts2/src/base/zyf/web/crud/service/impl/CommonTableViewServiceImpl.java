/**
 * 
 * 制作时间：2007-8-22上午11:15:50
 * 文件名：CommonTableViewServiceImpl.java
 * 制作者：zhaoyifei
 * 
 */
package base.zyf.web.crud.service.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import base.zyf.web.crud.db.CommonTableView;
import base.zyf.web.crud.db.CommonTableViewSet;
import base.zyf.web.crud.service.CommonTableViewService;


/**
 * @author zhaoyifei
 *
 */
public class CommonTableViewServiceImpl implements CommonTableViewService {

	private boolean setIsNull=false;
	
	private HibernateTemplate hibernateTemplate;
	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonTableViewService#getRows(java.lang.String, java.lang.Class)
	 */
	public List getRows(String userId, String pageId) {

		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.createAlias("commonTableView","commonTableView");
		dc.add(Restrictions.eq("commonTableView.tableName", pageId));
		dc.add(Restrictions.isNull("orderAsc"));
		dc.addOrder(Order.asc("orderNum"));
		List l= hibernateTemplate.findByCriteria(dc);
		if(l.size()==0)
		{
			this.setIsNull=true;
			DetachedCriteria dcc = DetachedCriteria.forClass(CommonTableView.class);
			dcc.add(Restrictions.eq("tableName", pageId));
			dcc.add(Restrictions.isNotNull("orderby"));
			dcc.addOrder(Order.asc("orderby"));
			l= hibernateTemplate.findByCriteria(dcc);
		}
		return l;
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonTableViewService#getRows(java.lang.Class)
	 */
	public List getRows(String pageId) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableView.class);
		dc.add(Restrictions.eq("tableName", pageId));
		
		List l= hibernateTemplate.findByCriteria(dc);
		
		return l;
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonTableViewService#getRowsDict(java.lang.Class, java.lang.String)
	 */
	public String getRowsDict(String pageId, String rowName) {
		// TODO Auto-generated method stub
		String result=null;
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableView.class);
		
		dc.add(Restrictions.eq("tableName", pageId));
		dc.add(Restrictions.eq("rowName", rowName));
		dc.addOrder(Order.asc("orderby"));
		List l= hibernateTemplate.findByCriteria(dc);
		if(l.size()!=0)
		{
			CommonTableView ctv=(CommonTableView)l.get(0);
			result=ctv.getDictName();
		}
		return result;
	}

	public void saveRows(String[] ids, String userId, String pageId) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.createAlias("commonTableView","commonTableView");
		dc.add(Restrictions.eq("commonTableView.tableName", pageId));
		dc.add(Restrictions.isNull("orderAsc"));
		List l= hibernateTemplate.findByCriteria(dc);
		hibernateTemplate.deleteAll(l);
		if(ids==null)
			return;
		for(int i=0;i<ids.length;i++)
		{
			CommonTableViewSet ctvs=new CommonTableViewSet();
			
			ctvs.setUserId(userId);
			ctvs.setOrderNum(new Integer(i));
			CommonTableView commonTableView=(CommonTableView)hibernateTemplate.load(CommonTableView.class,ids[i]);
			ctvs.setCommonTableView(commonTableView);
			hibernateTemplate.save(ctvs);
		}
	}

	public String getSearchTags(String pageId, String user) {
		// TODO Auto-generated method stub
		
		List l= this.getRows(user, pageId);
		StringBuffer re=new StringBuffer();
		Iterator i=l.iterator();
		while(i.hasNext())
		{
			CommonTableViewSet c=(CommonTableViewSet)i.next();
			re.append("\"");
			re.append(c.getCommonTableView().getRowName());
			re.append("\"");
			if(i.hasNext())
				re.append(",");
		}
		return re.toString();
	}

	public List getRows(String userId, String pageId, String asc) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.createAlias("commonTableView","commonTableView");
		dc.add(Restrictions.eq("commonTableView.tableName", pageId));
		dc.add(Restrictions.eq("orderAsc", asc));
		dc.addOrder(Order.asc("orderNum"));
		List l= hibernateTemplate.findByCriteria(dc);
		
		return l;
	}
	public List getRows(String userId, String pageId, boolean asc) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.createAlias("commonTableView","commonTableView");
		dc.add(Restrictions.eq("commonTableView.tableName", pageId));
		if(asc)
		dc.add(Restrictions.isNotNull("orderAsc"));
		else
			dc.add(Restrictions.isNull("orderAsc"));
		dc.addOrder(Order.asc("orderNum"));
		List l= hibernateTemplate.findByCriteria(dc);
		
		return l;
	}

	public void saveRows(String[] ids, String userId, String pageId, String asc) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
		dc.add(Restrictions.eq("userId", userId));
		
		dc.createAlias("commonTableView","commonTableView");
		dc.add(Restrictions.eq("commonTableView.tableName", pageId));
		dc.add(Restrictions.isNotNull(asc));
		List l= hibernateTemplate.findByCriteria(dc);
		hibernateTemplate.deleteAll(l);
		if(ids==null)
			return;
		for(int i=0;i<ids.length;i++)
		{
			CommonTableViewSet ctvs=new CommonTableViewSet();
			
			ctvs.setUserId(userId);
			ctvs.setOrderNum(new Integer(i));
			CommonTableView commonTableView=(CommonTableView)hibernateTemplate.load(CommonTableView.class,ids[i]);
			ctvs.setCommonTableView(commonTableView);
			ctvs.setOrderAsc(asc);
			hibernateTemplate.save(ctvs);
		}
	}

public void saveRows(String[] ids, String userId, String pageId,boolean asc) {
	// TODO Auto-generated method stub
	DetachedCriteria dc = DetachedCriteria.forClass(CommonTableViewSet.class);
	dc.add(Restrictions.eq("userId", userId));
	
	dc.createAlias("commonTableView","commonTableView");
	dc.add(Restrictions.eq("commonTableView.tableName", pageId));
	dc.add(Restrictions.isNotNull("orderAsc"));
	List l= hibernateTemplate.findByCriteria(dc);
	hibernateTemplate.deleteAll(l);
	if(ids==null)
		return;
	for(int i=0;i<ids.length;i++)
	{
		String id=ids[i];
		String asca="asc";
		if(id.split("-").length==2)
		asca=id.split("-")[1];
		
		
		CommonTableViewSet ctvs=new CommonTableViewSet();
		
		ctvs.setUserId(userId);
		ctvs.setOrderNum(new Integer(i));
		CommonTableView commonTableView=(CommonTableView)hibernateTemplate.load(CommonTableView.class,id.split("-")[0]);
		ctvs.setCommonTableView(commonTableView);
		ctvs.setOrderAsc(asca);
		hibernateTemplate.save(ctvs);
	}
}

public boolean isSetIsNull() {
	return setIsNull;
}
}
