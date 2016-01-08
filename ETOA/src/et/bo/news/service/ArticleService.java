/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface ArticleService {
    // 向里面录入新闻信息
    public boolean addArticleInfo(IBaseDTO dto);

    // 修改新闻信息
    public boolean updateArticleInfo(IBaseDTO dto);

    // 删除新闻信息
    public boolean deleteArticleInfo(IBaseDTO dto);

    // 得到条数
    public int getArticleSize();

    // 根据条件查询新闻信息
    public List findArticleInfo(IBaseDTO dto, PageInfo pi);

    // 查询出条件信息以load
    public IBaseDTO getArticleInfo(String id);

    //设置推荐
    public boolean putPink(String state,String articleid);
    
    //设置置顶
    public boolean putTop(String state,String articleid);
    
    //执行操作
    public boolean putArticleOper(String type,String[] str);
    
    //移动新闻
    public boolean moveAll(String classid,String[] str);
    
    //  得到条数
    public int getRecycleSize();

    // 根据条件查询新闻信息
    public List findRecycleInfo(IBaseDTO dto, PageInfo pi);
    
    //还原新闻
    public boolean revertNews(String articleid,String type);
    
    //还原选定的新闻
    public boolean revertNews(String[] str);
    
    //删除新闻，清空回收站
    public boolean delNews(String articleid,String type);
    
    //删除选定的新闻
    public boolean delNews(String[] str);
    //首页新闻
    public List getIndexList();
    
    //首页more
    public int getIndexSize();
    public List findIndexInfo(IBaseDTO dto, PageInfo pi);
    //新闻详细信息显示
    public List getNewsList(String id);
}
