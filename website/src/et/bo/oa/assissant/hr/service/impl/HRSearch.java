package et.bo.oa.assissant.hr.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.EmployeeInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class HRSearch extends MyQueryImpl{
	 /**
     * 根据角色情况查询
     * 
     * @return
     * 
     * @throws
     */

    public MyQuery searchHrOperInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(EmployeeInfo.class);
        
        //dc.add(Expression.ne("delSign","Y"));
        if(dto.get("name").toString()!=null&&dto.get("name").toString()!="")
        {        
        	dc.add(Expression.like("name","%"+dto.get("name").toString()+"%"));
        }
        
        if(dto.get("department").toString()!=null&&dto.get("department").toString()!="")
        {        
        	dc.add(Expression.eq("department",dto.get("department").toString()));
        }
        if(dto.get("bebirth").toString()!=null&&dto.get("bebirth").toString()!="")
        {        
        	
        	dc.add(Expression.ge("birth",TimeUtil.getTimeByStr(dto.get("bebirth").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("enbirth").toString()!=null&&dto.get("enbirth").toString()!="")
        {        
        	dc.add(Expression.le("birth",TimeUtil.getTimeByStr(dto.get("enbirth").toString(),"yyyy-MM-dd")));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery seachHrExist(IBaseDTO dto){
    	
    	MyQuery mq=new MyQueryImpl();
    	DetachedCriteria dc=DetachedCriteria.forClass(EmployeeInfo.class);
    	dc.add(Expression.eq("name",dto.get("name")));
    	dc.add(Expression.ne("delSign","Y"));
    	mq.setDetachedCriteria(dc);
    	return mq;
    }   
}
