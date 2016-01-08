/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.sys.user.service.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import et.po.SysDepartment;
import et.po.SysGroup;
import et.po.SysRole;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class UserHelp {

	public MyQuery ListUserMQ(IBaseDTO dto)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		if(null !=dto.get("userName") && !"".equals(dto.get("userName").toString()))
			dc.add(Expression.like("userName","%"+(String)dto.get("userName")+"%"));
		if(null !=dto.get("sysGroup") && !"".equals(dto.get("sysGroup").toString()))
		{
			SysGroup sg =new SysGroup();
			sg.setId(dto.get("sysGroup").toString());
			dc.add(Expression.eq("sysGroup",sg));
		}
		if(null !=dto.get("sysRole") && !"".equals(dto.get("sysRole").toString()))
		{
			SysRole sr =new SysRole();
			sr.setId(dto.get("sysRole").toString());
			dc.add(Expression.eq("sysRole",sr));
		}
		if(null !=dto.get("departmentId") && !"".equals(dto.get("departmentId").toString()))
		{	
		
			SysDepartment sd =new SysDepartment();
			sd.setId(dto.get("departmentId").toString());
			dc.add(Expression.eq("sysDepartment",sd));
		}
		dc.add(Expression.ne("deleteMark","-1"));
		dc.add(Expression.ne("deleteMark","2"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery listUserMQ(IBaseDTO dto, PageInfo pi)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		if(null !=dto.get("userName") && !"".equals(dto.get("userName").toString()))
			dc.add(Expression.like("userName","%"+(String)dto.get("userName")+"%"));		
		if(null !=dto.get("sysGroup") && !"".equals(dto.get("sysGroup").toString()))
		{
			SysGroup sg =new SysGroup();
			sg.setId(dto.get("sysGroup").toString());
			dc.add(Expression.eq("sysGroup",sg));
		}
		if(null !=dto.get("sysRole") && !"".equals(dto.get("sysRole").toString()))
		{
			SysRole sr =new SysRole();
			sr.setId(dto.get("sysRole").toString());
			dc.add(Expression.eq("sysRole",sr));
		}
			
		if(null !=dto.get("departmentId") && !"".equals(dto.get("departmentId").toString()))
		{
//			
			SysDepartment sd =new SysDepartment();
			sd.setId(dto.get("departmentId").toString());
			dc.add(Expression.eq("sysDepartment",sd));
		}			
		dc.add(Expression.ne("deleteMark","-1"));
		dc.add(Expression.ne("deleteMark","2"));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
		return mq;		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

}
