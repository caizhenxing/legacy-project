/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.service.impl;

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

public class ArticleSearch extends MyQueryImpl {
    
    /**
     * <p>根据新闻信息查询</p>
     *
     * @param info:根据新闻信息查询
     * 
     * @return:MyQuery mq
     * 
     * @throws
     */

    public MyQuery searchArticleInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
        dc.add(Expression.eq("deleted","n".toUpperCase()));
        dc.addOrder(Order.desc("articleid"));
        String title=(String)dto.get("title");
        if (title!=null&&!title.trim().equals("")) {
            dc.add(Expression.like("title","%"+title+"%"));
        }
        String classid = (String)dto.get("classid");
        if (classid!=null&&!classid.trim().equals("")) {
            dc.add(Expression.eq("classid",classid));
        }
        String author = (String)dto.get("editor");
        if (author!=null&&!author.trim().equals("")) {
            dc.add(Expression.eq("author",author));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
     * <p>查询回收站信息</p>
     *
     * @param info:查询回收站信息
     * 
     * @return:MyQuery mq
     * 
     * @throws
     */

    public MyQuery searchRecycleInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
        dc.add(Expression.eq("deleted","y".toUpperCase()));
        dc.addOrder(Order.desc("articleid"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
     * <p>首页新闻列表显示</p>
     *
     * @param info:首页新闻列表显示
     * 
     * @return:
     * 
     * @throws
     */

    public MyQuery searchHeadNews(){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
        dc.addOrder(Order.desc("articleid"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(0);
        mq.setFetch(4);
        return mq;
    }
    
    /**
     * <p>首页新闻more</p>
     *
     * @param info:
     * 
     * @return:
     * 
     * @throws
     */

    public MyQuery searchIndexInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(NewsArticle.class);
        dc.add(Expression.eq("deleted","n".toUpperCase()));
        dc.addOrder(Order.desc("articleid"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
}
