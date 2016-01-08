package et.bo.sys.role.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.SysRole;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class RoleSearch extends MyQueryImpl {  
    /**
     * 根据角色情况查询
     * 
     * @return
     * 
     * @throws
     */

    public MyQuery searchRoleOperInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(SysRole.class);

        if(dto.get("name").toString()!=null&&dto.get("name").toString()!="")
        {        
        	dc.add(Expression.eq("name",dto.get("name").toString()));
        }
        dc.add(Expression.ne("deleteMark","Y"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery seachRoleExist(IBaseDTO dto){
    	
    	MyQuery mq=new MyQueryImpl();
    	DetachedCriteria dc=DetachedCriteria.forClass(SysRole.class);
    	dc.add(Expression.eq("name",dto.get("name")));
    	dc.add(Expression.ne("deleteMark","Y"));
    	mq.setDetachedCriteria(dc);
    	return mq;
    }   
}
