package et.bo.sys.group.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.SysGroup;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class GroupSearch extends MyQueryImpl {  
    /**
     * 根据组情况查询
     * 
     * @return
     * 
     * @throws
     */

    public MyQuery searchGroupOperInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(SysGroup.class);

        if(dto.get("name").toString()!=null&&dto.get("name").toString()!="" && !dto.get("name").toString().equals(""))
        {        
        	dc.add(Expression.eq("name",dto.get("name").toString()));
        }
        dc.add(Expression.eq("delMark","N"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery seachGroupExist(IBaseDTO dto){
    	
    	MyQuery mq=new MyQueryImpl();
    	DetachedCriteria dc=DetachedCriteria.forClass(SysGroup.class);
    	dc.add(Expression.eq("name",dto.get("name")));
    	dc.add(Expression.ne("delMark","N"));
    	mq.setDetachedCriteria(dc);
    	return mq;
    }   
}
