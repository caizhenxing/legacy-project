/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.commoninfo.leaveWord.service.impl;

import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.AddresslistInfo;
import et.po.AddresslistsortInfo;
import et.po.LeavewordInfo;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * @describe  留言查询
 * @author
 * @version 1.0, //
 * @see
 * @see
 */
public class LeaveWordSearch extends MyQueryImpl {
    /**
	 * @describe  留言列表
	 * @param
	 * @return
	 * 
	 */
    public MyQuery searchLeaveWordInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(LeavewordInfo.class);
        if(dto.get("name")!=null&&dto.get("name").toString()!="")
        	dc.add(Expression.like("name", "%"+dto.get("name").toString()+"%"));
        if(dto.get("title")!=null&&dto.get("title").toString()!="")
        	dc.add(Expression.like("title", "%"+dto.get("title").toString()+"%"));
        if(dto.get("leaveDateBegin").toString()!=null&&dto.get("leaveDateBegin").toString()!="")
        {        
        	dc.add(Expression.ge("leaveDate",TimeUtil.getTimeByStr(dto.get("leaveDateBegin").toString(),"yyyy-MM-dd")));
        }
        if(dto.get("leaveDateEnd").toString()!=null&&dto.get("leaveDateEnd").toString()!="")
        {        
        	dc.add(Expression.le("leaveDate",TimeUtil.getTimeByStr(dto.get("leaveDateEnd").toString(),"yyyy-MM-dd")));
        }
        dc.addOrder(Order.desc("leaveDate"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }    
    /**
	 * @describe  留言列表See
	 * @param
	 * @return
	 * 
	 */
    public MyQuery searchSeeLeaveWordInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(LeavewordInfo.class);
        dc.addOrder(Order.desc("leaveDate"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        System.out.println("wo zhou le");
        return mq;
    }    
 
}
