package et.bo.sys.department.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;

import et.po.SysDepartment;
import et.po.SysUser;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;





public class ClassTreeHelp {

	public MyQuery createClassTreeQuery()
	{
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysDepartment.class);
		//dc.add(Restrictions.eq("tagShow","1"));
		
		dc.addOrder(Order.asc("id"));
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	
	
	public MyQuery userQuery(IBaseDTO dto){
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		String name =(String)dto.get("selectName");
		
		if(name!=null&&!name.equals("")){
			dc.add(Restrictions.like("userName", "%"+name+"%"));
		}
		
		mq.setDetachedCriteria(dc);
		
		return mq;       
	}   
	

}
