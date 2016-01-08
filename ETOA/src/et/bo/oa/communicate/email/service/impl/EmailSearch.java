package et.bo.oa.communicate.email.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.InadjunctInfo;
import et.po.InemailInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class EmailSearch extends MyQueryImpl{

	/**
	 * @describe 收件箱
	 * @param  类型  
	 * @return 类型  
	 * 
	 */

    public MyQuery searchGetEmailList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "n".toUpperCase()));
        dc.add(Expression.eq("emailType", "1"));
        dc.add(Expression.eq("inorout", "1"));
        dc.add(Expression.eq("takeUser",dto.get("sendUser").toString()));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe 发件箱
	 * @param  类型  
	 * @return 类型  
	 * 
	 */

    public MyQuery searchSendEmailList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "n".toUpperCase()));
        dc.add(Expression.eq("emailType", "2"));
        dc.add(Expression.eq("inorout", "1"));
        //
        dc.add(Expression.eq("sendUser",dto.get("sendUser").toString()));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe 草稿箱
	 * @param  类型  
	 * @return 类型  
	 * 
	 */

    public MyQuery searchDraftEmailList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "n".toUpperCase()));
        dc.add(Expression.eq("emailType", "3"));
        dc.add(Expression.eq("inorout", "1"));
        dc.add(Expression.eq("sendUser",dto.get("sendUser").toString()));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe 垃圾箱
	 * @param  类型  
	 * @return 类型  
	 * 
	 */

    public MyQuery searchDelEmailList(IBaseDTO dto, PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InemailInfo.class);
        dc.add(Expression.eq("delSign", "y".toUpperCase()));
        //dc.add(Expression.eq("emailType", "4"));
        dc.add(Expression.eq("inorout", "1"));
        dc.add(Expression.eq("sendUser",dto.get("sendUser").toString()));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe 查询上传列表信息
	 * @param  类型  InemailInfo inemailInfo
	 * @return 类型  
	 * 
	 */

    public MyQuery searchUploadListInfo(InemailInfo inemailInfo) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(InadjunctInfo.class);
        dc.add(Expression.eq("inemailInfo", inemailInfo));
        mq.setDetachedCriteria(dc);
        return mq;
    }
    
}
