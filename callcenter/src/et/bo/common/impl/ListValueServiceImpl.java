package et.bo.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.common.ListValueService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ListValueServiceImpl implements ListValueService {

	private BaseDAO dao=null;
	private KeyService ks = null;
	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	public List getLabelValue(String tablename, String key, String value, String fk)
	{
		List list =new ArrayList();
		MyQuery mq =new MyQueryImpl();
		String hql ="select "+key+","+value+" from "+tablename;				
		mq.setHql(hql);
		Object [] temp =this.dao.findEntity(mq);
		if(!"-1".equals(fk))
		{
			LabelValueBean lv =new LabelValueBean(fk,null);
			list.add(lv);
		}
		if(null !=temp && temp.length >0)
		{
			for(int i=0;i<temp.length;i++)
			{				
				Object[] kv =(Object [])temp[i];
				String k =kv[0]!=null?kv[0].toString():"";
				String v =kv[1]!=null?kv[1].toString():"";
				LabelValueBean lv =new LabelValueBean(k,v);
				list.add(lv);
//				System.out.println(kv[0]+"\t"+kv[1]);
			}
		}	
//		System.out.println(temp.length);
		return list;
	}
	
	public List getLabelValue(String tablename, String key, String value, String flag, String fvalue)
	{
		List list =new ArrayList();
		MyQuery mq =new MyQueryImpl();
		String hql ="select "+key+","+value+" from "+tablename +" where "+flag +" = "+"'"+fvalue+"'";
		mq.setHql(hql);
		Object [] temp =this.dao.findEntity(mq);		
		if(null !=temp && temp.length >0)
		{
			for(int i=0;i<temp.length;i++)
			{				
				Object[] kv =(Object [])temp[i];
				String k =kv[0]!=null?kv[0].toString():"";
				String v =kv[1]!=null?kv[1].toString():"";
				LabelValueBean lv =new LabelValueBean(k,v);
				list.add(lv);
//				System.out.println(kv[0]+"\t"+kv[1]);
			}
		}	
//		System.out.println(temp.length);
		return list;
	}
	public boolean checkDup(Class c, String f, String value)
	{
		//TODO 需要写出方法的具体实现
		DetachedCriteria dc=DetachedCriteria.forClass(c);
		dc.add(Expression.eq(f,value));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		Object [] o =dao.findEntity(mq);
		if(null !=o && o.length>0)
		{
			return true;
		}
		return false;
	}
}
