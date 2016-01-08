package et.bo.oa.assissant.document.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.FileInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


public class DocumentSearch extends MyQueryImpl{
	/**
     * 根据角色情况查询
     * 
     * @return
     * 
     * @throws
     */

    public MyQuery searchDocOperInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(FileInfo.class);
        
        
        dc.add(Expression.eq("folderSign","Y"));
        if(dto.get("folderCode").toString()!=null&&dto.get("folderCode").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderCode",dto.get("folderCode").toString()));
        }
        if(dto.get("folderName").toString()!=null&&dto.get("folderName").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderName",dto.get("folderName").toString()));
        }
        if(dto.get("folderType").toString()!=null&&dto.get("folderType").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderType",dto.get("folderType").toString()));
        }
        if(dto.get("createTime").toString()!=null&&dto.get("createTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("createTime",TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("updateTime").toString()!=null&&dto.get("updateTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("updateTime",TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("folderVersion").toString()!=null&&dto.get("folderVersion").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderVersion",dto.get("folderVersion").toString()));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery searchDocOperInfo2(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(FileInfo.class);
        
        
        dc.add(Expression.eq("folderSign","W"));
        if(dto.get("folderCode").toString()!=null&&dto.get("folderCode").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderCode",dto.get("folderCode").toString()));
        }
        if(dto.get("folderName").toString()!=null&&dto.get("folderName").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderName",dto.get("folderName").toString()));
        }
        if(dto.get("folderType").toString()!=null&&dto.get("folderType").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderType",dto.get("folderType").toString()));
        }
        if(dto.get("createTime").toString()!=null&&dto.get("createTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("createTime",TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("updateTime").toString()!=null&&dto.get("updateTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("updateTime",TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("folderVersion").toString()!=null&&dto.get("folderVersion").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderVersion",dto.get("folderVersion").toString()));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery searchDocOperInfo4(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(FileInfo.class);
        
        
//        dc.add(Expression.eq("folderSign","W"));
        if(dto.get("folderCode").toString()!=null&&dto.get("folderCode").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderCode",dto.get("folderCode").toString()));
        }
        if(dto.get("folderName").toString()!=null&&dto.get("folderName").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderName",dto.get("folderName").toString()));
        }
        if(dto.get("folderType").toString()!=null&&dto.get("folderType").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderType",dto.get("folderType").toString()));
        }
        if(dto.get("createTime").toString()!=null&&dto.get("createTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("createTime",TimeUtil.getTimeByStr(dto.get("createTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("updateTime").toString()!=null&&dto.get("updateTime").toString()!="")
        {        
        	
        	dc.add(Expression.eq("updateTime",TimeUtil.getTimeByStr(dto.get("updateTime").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("folderVersion").toString()!=null&&dto.get("folderVersion").toString()!="")
        {        
        	
        	dc.add(Expression.eq("folderVersion",dto.get("folderVersion").toString()));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    public MyQuery seachDocExist(IBaseDTO dto){
    	
    	MyQuery mq=new MyQueryImpl();
    	DetachedCriteria dc=DetachedCriteria.forClass(FileInfo.class);
    	dc.add(Expression.eq("folderCode",dto.get("folderCode").toString()));
//    	dc.add(Expression.ne("delSign","Y"));
    	mq.setDetachedCriteria(dc);
    	return mq;
    }   
}
