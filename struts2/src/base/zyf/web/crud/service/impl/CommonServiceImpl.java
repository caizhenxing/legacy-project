/**
 * 
 * 制作时间：2007-8-20下午09:07:15
 * 文件名：CommonServiceImpl.java
 * 制作者：zhaoyifei
 * 
 */
package base.zyf.web.crud.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.hibernate.EntityPlus;
import base.zyf.tools.Tools;
import base.zyf.web.condition.ContextInfo;
import base.zyf.web.crud.db.CommonTableViewSet;
import base.zyf.web.crud.service.CommonService;
import base.zyf.web.crud.service.CommonTableViewService;
import base.zyf.web.crud.service.ViewBean;
import base.zyf.web.user.UserBean;



/**
 * @author zhaoyifei
 *
 */
public class CommonServiceImpl implements CommonService {

	private CommonTableViewService ctvs;
	private ClassTreeService bcs;
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
	 * @see com.zyf.common.crud.service.CommonService#add(com.zyf.framework.persistent.Entity)
	 */
	public void add(Object e) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(e);
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonService#delete(com.zyf.framework.persistent.Entity)
	 */
	public void delete(Object e) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(e);
	}

	

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonService#load(java.lang.Class, java.lang.String)
	 */
	public Object load(Class c, String id) {
		return hibernateTemplate.load(c, id);
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.crud.service.CommonService#update(com.zyf.framework.persistent.Entity)
	 */
	public void update(Object e) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(e);
	}

	public ViewBean fetchAll(Class c, String user,String pageId) {
		// TODO Auto-generated method stub
		List ascl=new ArrayList();
		List descl=new ArrayList();
		List al=this.ctvs.getRows(user, pageId, "asc");
		List dl=this.ctvs.getRows(user, pageId, "desc");
		for(int i=0,size=al.size();i<size;i++)
		{
			CommonTableViewSet ctvsa=(CommonTableViewSet)al.get(i);
			ascl.add(ctvsa.getCommonTableView().getRowName());
		}
		for(int i=0,size=dl.size();i<size;i++)
		{
			CommonTableViewSet ctvsa=(CommonTableViewSet)dl.get(i);
			descl.add(ctvsa.getCommonTableView().getRowName());
		}
		return this.fetchAll(c, user,pageId, ascl,  descl);
	}
	

	public ViewBean fetchAll(Class c,String user,String pageId, List orderAsc, List orderDesc) {
		// TODO Auto-generated method stub
		ContextInfo.recoverQuery();
		DetachedCriteria d=DetachedCriteria.forClass(c);
		if(EntityPlus.class.isAssignableFrom(c))
		{
			d.add(Restrictions.eq("delFlg","0"));
		}
		for(int i=0,size=orderAsc.size();i<size;i++)
		{
			d.addOrder(Order.asc((String)orderAsc.get(i)));
		}
		for(int i=0,size=orderDesc.size();i<size;i++)
		{
			d.addOrder(Order.desc((String)orderDesc.get(i)));
		}
		List l=hibernateTemplate.findByCriteria(d);
		
		ViewBean vb=new ViewBean();
		List rs=ctvs.getRows(user, pageId);
		vb.setViewRow(rs);
		//this.change(l,rs);
		vb.setViewList(l);
		return vb;
	}

	/**
	 * @return the ctvs
	 */
	public CommonTableViewService getCtvs() {
		return ctvs;
	}

	/**
	 * @param ctvs the ctvs to set
	 */
	public void setCtvs(CommonTableViewService ctvs) {
		this.ctvs = ctvs;
	}



	

	/**
	 * @return the bcs
	 */
	public ClassTreeService getBcs() {
		return bcs;
	}

	/**
	 * @param bcs the bcs to set
	 */
	public void setBcs(ClassTreeService bcs) {
		this.bcs = bcs;
	}

	private void change(List result,List rows)
	{
		Iterator i=rows.iterator();
		while(i.hasNext())
		{
			CommonTableViewSet ctvs=(CommonTableViewSet)i.next();
			String r=ctvs.getCommonTableView().getRowName();
			StringBuffer getMethod=new StringBuffer();
			getMethod.append("get");
			String first=r.substring(0,1);
			getMethod.append(first.toUpperCase());
			getMethod.append(r.substring(1));
			StringBuffer setMethod=new StringBuffer();
			setMethod.append("set");
			
			setMethod.append(first.toUpperCase());
			setMethod.append(r.substring(1));
			
			Iterator i1=result.iterator();
			while(i1.hasNext())
			{
				Object object=i1.next();
				Object id;
				try {
					id = MethodUtils.invokeExactMethod(object,getMethod.toString(),null);
					String display=bcs.getLabelById((String)id);
					//MethodUtils.invokeExactMethod(object,setMethod.toString(),display);
					Map dict=(Map)MethodUtils.invokeExactMethod(object,"getDict",null);
					dict.put(r,display);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}

	public void deleteLogic(Object e) {
		Tools.setProperty(e,"delFlg","1");
		this.saveOrUpdate(e);
	}


	public void saveOrUpdate(Object e) {
		hibernateTemplate.saveOrUpdate(e);
	}


	public ViewBean fetchAll(Class c, String pageId) {
		
		UserBean ucn=ContextInfo.getContextUser();
		return this.fetchAll(c, ucn.getName(),pageId);
	}



	public int deleteAllLogic(Class c, List oids) {
		// TODO Auto-generated method stub
			int num = oids.size();
		for (int i = 0; i < num; i++) {
			String id = oids.get(i).toString().trim();
			this.deleteLogic(this.load(c, id));
		}
		return num;
	}

	

	public int deleteAll(Class c, List oids) {
		// TODO Auto-generated method stub
		int num = oids.size();
		for (int i = 0; i < num; i++) {
			String id = oids.get(i).toString().trim();
			this.delete(this.load(c, id));
		}
		return num;
	}

	public int deleteAll(Collection c) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(c);
		return c.size();
	}

	public int deleteAllLogic(Collection c) {
		// TODO Auto-generated method stub
		Iterator i=c.iterator();
		while(i.hasNext())
		{
			Object o=i.next();
			this.deleteLogic(o);
		}
		return c.size();
	}

	public ViewBean setVBByList(String pageId, List data) {
		UserBean ucn=ContextInfo.getContextUser();
		String user = 	ucn.getName();
		ViewBean vb=new ViewBean();
		List rs=ctvs.getRows(user, pageId);
		vb.setViewRow(rs);
		vb.setViewList(data);
		return vb;
	}


    public List findByCriteria(DetachedCriteria dc) {
        return hibernateTemplate.findByCriteria(dc);
    }

	public int getSize(DetachedCriteria dc) {
		dc.add(Restrictions.eq("delFlg","0"));
		dc.setProjection(Projections.count("id"));
		Integer i=(Integer)hibernateTemplate.findByCriteria(dc).get(0);
		return i.intValue();
	}

	public void saveOrUpdate(List l) {
		hibernateTemplate.saveOrUpdateAll(l);
			
	}

}
