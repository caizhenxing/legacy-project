/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.service.impl;


import java.util.ArrayList;
import java.util.List;

import et.bo.news.service.ArticleService;
import et.po.NewsArticle;
import et.po.SysTree;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class ArticleServiceImpl implements ArticleService {

    int num = 0;

    int recnum = 0;
    
    int indexnum = 0;

    private BaseDAO dao = null;

    private KeyService ks = null;

    public BaseDAO getDao() {
        return dao;
    }

    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }

    public KeyService getKs() {
        return ks;
    }

    public void setKs(KeyService ks) {
        this.ks = ks;
    }

    // 添加
    private NewsArticle createNewsArticleInfo(IBaseDTO dto) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setArticleid(ks.getNext("news_article"));
        newsArticle.setClassid(dto.get("classid").toString());
        newsArticle.setTitle(dto.get("title") == null ? "" : dto.get("title")
                .toString());
        newsArticle.setAuthor(dto.get("author").toString());
        newsArticle.setCopyfrom(dto.get("copyfrom").toString());
        newsArticle.setEditor(dto.get("editor").toString());
        newsArticle.setKeyvalue(dto.get("key").toString());
        newsArticle.setHits(dto.get("hits").toString());
        newsArticle.setUpdatetime(TimeUtil.getNowTime());
        newsArticle.setHot("0");
        newsArticle.setOntop("0");
        newsArticle.setElite("0");
        newsArticle.setPassed(dto.get("passed").toString());
        newsArticle.setContent(dto.get("content").toString());
        newsArticle.setIncludepic("0");
        newsArticle.setDefaultpicurl("");
        newsArticle.setUploadfiles("");
        newsArticle.setDeleted("n".toUpperCase());
        return newsArticle;
    }

    public boolean addArticleInfo(IBaseDTO dto) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        dao.saveEntity(createNewsArticleInfo(dto));
        flag = true;
        return flag;
    }

    // 修改
    private NewsArticle updateNewsArticleInfo(IBaseDTO dto) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setArticleid(dto.get("articleid").toString());
        newsArticle.setClassid(dto.get("classid").toString());
        newsArticle.setTitle(dto.get("title") == null ? "" : dto.get("title")
                .toString());
        newsArticle.setAuthor(dto.get("author").toString());
        newsArticle.setCopyfrom(dto.get("copyfrom").toString());
        newsArticle.setEditor(dto.get("editor").toString());
        newsArticle.setKeyvalue(dto.get("key").toString());
        newsArticle.setHits(dto.get("hits").toString());
        newsArticle.setUpdatetime(TimeUtil.getNowTime());
        newsArticle.setHot("0");
        newsArticle.setOntop("0");
        newsArticle.setElite("0");
        newsArticle.setPassed(dto.get("passed").toString());
        newsArticle.setContent(dto.get("content").toString());
        newsArticle.setIncludepic("0");
        newsArticle.setDefaultpicurl("");
        newsArticle.setUploadfiles("");
        newsArticle.setDeleted("n".toUpperCase());
        return newsArticle;
    }

    public boolean updateArticleInfo(IBaseDTO dto) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        dao.updateEntity(updateNewsArticleInfo(dto));
        flag = true;
        return flag;
    }

    public boolean deleteArticleInfo(IBaseDTO dto) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                NewsArticle.class, dto.get("articleid").toString());
        newsArticle.setDeleted("y".toUpperCase());
        dao.updateEntity(newsArticle);
        flag = true;
        return flag;
    }

    public int getArticleSize() {
        // TODO 需要写出方法的具体实现
        return num;
    }

    public List findArticleInfo(IBaseDTO dto, PageInfo pi) {
        // TODO 需要写出方法的具体实现
        List l = new ArrayList();
        ArticleSearch articleSearch = new ArticleSearch();
        Object[] result = (Object[]) dao.findEntity(articleSearch
                .searchArticleInfo(dto, pi));
        int s = dao.findEntitySize(articleSearch.searchArticleInfo(dto, pi));
        num = s;
        for (int i = 0, size = result.length; i < size; i++) {
            NewsArticle newsArticle = (NewsArticle) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("articleid", newsArticle.getArticleid());
            String title = newsArticle.getTitle();
            if (title.length()>15) {
                title = title.substring(0,14)+"...";
            }
            dbd.set("title", title);
            dbd.set("author", newsArticle.getAuthor());
            dbd.set("updatetime", TimeUtil.getTheTimeStr(newsArticle
                    .getUpdatetime()));
            dbd.set("elite", newsArticle.getElite().equals("0") ? "☆" : "★(荐)");
            dbd.set("ontop", newsArticle.getOntop().equals("0") ? "0" : "1");
            StringBuffer sb = new StringBuffer(100);
            if (newsArticle.getElite().equals("0")) {
                sb.append("☆");
            }
            if (newsArticle.getElite().equals("1")) {
                sb.append("★(荐)");
            }
            if (newsArticle.getOntop().equals("1")) {
                sb.append("(顶)");
            }
            dbd.set("state", sb.toString());
            l.add(dbd);
        }
        return l;
    }

    public IBaseDTO getArticleInfo(String id) {
        // TODO 需要写出方法的具体实现
        IBaseDTO dto = new DynaBeanDTO();
        NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                NewsArticle.class, id);
        dto.set("articleid", newsArticle.getArticleid());
        dto.set("classid", newsArticle.getClassid());
        dto.set("title", newsArticle.getTitle());
        dto.set("author", newsArticle.getAuthor());
        dto.set("copyfrom", newsArticle.getCopyfrom());
        dto.set("editor", newsArticle.getEditor());
        dto.set("key", newsArticle.getKeyvalue());
        dto.set("hits", newsArticle.getHits());
        dto.set("updatetime", TimeUtil.getTheTimeStr(newsArticle
                .getUpdatetime()));
        dto.set("hot", newsArticle.getHot());
        dto.set("ontop", newsArticle.getOntop());
        dto.set("elite", newsArticle.getElite());
        dto.set("passed", newsArticle.getPassed());
        dto.set("content", newsArticle.getContent());
        dto.set("includepic", newsArticle.getIncludepic());
        dto.set("defaultpicurl", newsArticle.getDefaultpicurl());
        dto.set("uploadfiles", newsArticle.getUploadfiles());
        return dto;
    }

    public boolean putPink(String state, String articleid) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                NewsArticle.class, articleid);
        // 设置推荐
        if (state.equals("put")) {
            newsArticle.setElite("1");
        } else if (state.equals("unput")) {
            newsArticle.setElite("0");
        }
        dao.updateEntity(newsArticle);
        flag = true;
        return flag;
    }

    public boolean putTop(String state, String articleid) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                NewsArticle.class, articleid);
        // 设置推荐
        if (state.equals("put")) {
            newsArticle.setOntop("1");
        } else if (state.equals("unput")) {
            newsArticle.setOntop("0");
        }
        dao.updateEntity(newsArticle);
        flag = true;
        return flag;
    }

    // 0:彻底删除 1:放入回收站 2:全部设置为推荐 3:全部取消推荐 4:全部固顶 5:全部解固
    public boolean putArticleOper(String type, String[] str) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        if (type.equals("0")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                dao.removeEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        if (type.equals("1")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                newsArticle.setDeleted("y".toUpperCase());
                dao.updateEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        if (type.equals("2")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                newsArticle.setElite("1");
                dao.updateEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        if (type.equals("3")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                newsArticle.setElite("0");
                dao.updateEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        if (type.equals("4")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                newsArticle.setOntop("1");
                dao.updateEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        if (type.equals("5")) {
            for (int i = 0; i < str.length; i++) {
                String articleid = str[i];
                NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                        NewsArticle.class, articleid);
                newsArticle.setOntop("0");
                dao.updateEntity(newsArticle);
                flag = true;
            }
            return flag;
        }
        return flag;
    }

    public boolean moveAll(String classid, String[] str) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            String articleid = str[i];
            NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                    NewsArticle.class, articleid);
            newsArticle.setClassid(classid);
            dao.updateEntity(newsArticle);
        }
        flag = true;
        return flag;
    }

    public int getRecycleSize() {
        // TODO 需要写出方法的具体实现
        return recnum;
    }

    public List findRecycleInfo(IBaseDTO dto, PageInfo pi) {
        // TODO 需要写出方法的具体实现
        List l = new ArrayList();
        ArticleSearch articleSearch = new ArticleSearch();
        Object[] result = (Object[]) dao.findEntity(articleSearch
                .searchRecycleInfo(dto, pi));
        int s = dao.findEntitySize(articleSearch.searchRecycleInfo(dto, pi));
        recnum = s;
        for (int i = 0, size = result.length; i < size; i++) {
            NewsArticle newsArticle = (NewsArticle) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("articleid", newsArticle.getArticleid());
            dbd.set("title", newsArticle.getTitle());
            dbd.set("author", newsArticle.getAuthor());
            dbd.set("updatetime", TimeUtil.getTheTimeStr(newsArticle
                    .getUpdatetime()));
            dbd.set("elite", newsArticle.getElite().equals("0") ? "☆" : "★(荐)");
            dbd.set("ontop", newsArticle.getOntop().equals("0") ? "0" : "1");
            StringBuffer sb = new StringBuffer(100);
            if (newsArticle.getElite().equals("0")) {
                sb.append("☆");
            }
            if (newsArticle.getElite().equals("1")) {
                sb.append("★(荐)");
            }
            if (newsArticle.getOntop().equals("1")) {
                sb.append("(顶)");
            }
            dbd.set("state", sb.toString());
            l.add(dbd);
        }
        return l;
    }

    public boolean revertNews(String articleid, String type) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        //还原所有
        if (type.equals("all")) {
            String sql = "update news_article set deleted = 'N' where deleted = 'Y'";
            dao.execute(sql);
            flag = true;
            return flag;
        } else {
            NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                    NewsArticle.class, articleid);
            newsArticle.setDeleted("N");
            dao.updateEntity(newsArticle);
            flag = true;
            return flag;
        }
    }

    public boolean delNews(String articleid, String type) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        if (type.equals("all")) {
            String sql = "delete from news_article where deleted = 'Y'";
            dao.execute(sql);
            flag = true;
            return flag;
        } else {
            NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                    NewsArticle.class, articleid);
            dao.removeEntity(newsArticle);
            flag = true;
            return flag;
        }
    }

    public boolean delNews(String[] str) {
        // TODO 需要写出方法的具体实现
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            String articleid = str[i];
            NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                    NewsArticle.class, articleid);
            dao.removeEntity(newsArticle);
            flag = true;
        }
        return flag;

    }

    public boolean revertNews(String[] str) {
        //TODO 需要写出方法的具体实现
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            String articleid = str[i];
            NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                    NewsArticle.class, articleid);
            newsArticle.setDeleted("n".toUpperCase());
            dao.updateEntity(newsArticle);
            flag = true;
        }
        return flag;

    }

    public List getIndexList() {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        ArticleSearch articleSearch = new ArticleSearch();
        Object[] result=(Object[])dao.findEntity(articleSearch.searchHeadNews());
        for(int i = 0,size=result.length;i<size;i++){
            NewsArticle newsArticle  = (NewsArticle)result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id",newsArticle.getArticleid());
           
            String title = newsArticle.getTitle();
            if (title.length()>=15) {
                title = title.substring(0,15)+"...";
            }
            dbd.set("title",title);
            dbd.set("author", newsArticle.getAuthor());
            dbd.set("newsTime",TimeUtil.getTheTimeStr(newsArticle.getUpdatetime(),"yyyy-MM-dd"));
            l.add(dbd);
        }
        return l;
    }

    public int getIndexSize() {
        //TODO 需要写出方法的具体实现
        return indexnum;
    }

    public List findIndexInfo(IBaseDTO dto, PageInfo pi) {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        ArticleSearch articleSearch = new ArticleSearch();
        Object[] result = (Object[]) dao.findEntity(articleSearch
                .searchIndexInfo(dto, pi));
        int s = dao.findEntitySize(articleSearch.searchIndexInfo(dto, pi));
        indexnum = s;
        for (int i = 0, size = result.length; i < size; i++) {
            NewsArticle newsArticle = (NewsArticle) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id", newsArticle.getArticleid());
            dbd.set("title", newsArticle.getTitle());
            dbd.set("author", newsArticle.getAuthor());
            dbd.set("newsTime",TimeUtil.getTheTimeStr(newsArticle.getUpdatetime(),"yyyy-MM-dd"));
            l.add(dbd);
        }
        return l;
    }

    public List getNewsList(String id) {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        IBaseDTO dto = new DynaBeanDTO();
        NewsArticle newsArticle = (NewsArticle) dao.loadEntity(
                NewsArticle.class, id);
        dto.set("articleid", newsArticle.getArticleid());
        String classid = newsArticle.getClassid();
        if (!classid.equals("0")) {
            SysTree sys = (SysTree)dao.loadEntity(SysTree.class,classid);
            dto.set("classid", sys.getLabel());
		}else{
			
		}
        dto.set("title", classid);
        dto.set("author", newsArticle.getAuthor());
        dto.set("copyfrom", newsArticle.getCopyfrom());
        dto.set("editor", newsArticle.getEditor());
        dto.set("key", newsArticle.getKeyvalue());
        dto.set("hits", newsArticle.getHits());
        dto.set("updatetime", TimeUtil.getTheTimeStr(newsArticle
                .getUpdatetime()));
        dto.set("hot", newsArticle.getHot());
        dto.set("ontop", newsArticle.getOntop());
        dto.set("elite", newsArticle.getElite());
        dto.set("passed", newsArticle.getPassed());
        dto.set("content", newsArticle.getContent());
        dto.set("includepic", newsArticle.getIncludepic());
        dto.set("defaultpicurl", newsArticle.getDefaultpicurl());
        dto.set("uploadfiles", newsArticle.getUploadfiles());
        l.add(dto);
        return l;
    }

}
