/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.commoninfo.affiche.service.impl;


import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.AficheInfo;
import et.po.NewsArticle;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AfficheSearch extends MyQueryImpl {
    //查询公告信息
    public MyQuery searchAfficheInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AficheInfo.class);
        dc.addOrder(Order.desc("id"));
        dc.add(Expression.eq("delSign","n".toUpperCase()));
        String aficheTitle=(String)dto.get("aficheTitle");
        if (aficheTitle!=null&&!aficheTitle.trim().equals("")) {
            dc.add(Expression.like("aficheTitle","%"+aficheTitle+"%"));
        }
        String aficheType = (String)dto.get("aficheType");
        if (aficheType!=null&&!aficheType.trim().equals("")) {
            dc.add(Expression.eq("aficheType",aficheType));
        }
        Date nowdate = TimeUtil.getNowTime();
        dc.add(Expression.le("beginTime",nowdate));
        dc.add(Expression.ge("endTime",nowdate));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    //首页显示公告信息
    public MyQuery searchHeadAffice(){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AficheInfo.class);
        dc.addOrder(Order.desc("id"));
        Date nowdate = TimeUtil.getNowTime();
        dc.add(Expression.le("beginTime",nowdate));
        dc.add(Expression.ge("endTime",nowdate));
        mq.setDetachedCriteria(dc);
        mq.setFirst(0);
        mq.setFetch(9);
        return mq;
    }
    
    /**
     * <p>详细页列表</p>
     *
     * @param info:
     * 
     * @return:
     * 
     * @throws
     */

    public MyQuery searchIndexInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AficheInfo.class);
        dc.add(Expression.eq("delSign","n".toUpperCase()));
        dc.addOrder(Order.desc("id"));
        Date nowdate = TimeUtil.getNowTime();
        dc.add(Expression.le("beginTime",nowdate));
        dc.add(Expression.ge("endTime",nowdate));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
}
