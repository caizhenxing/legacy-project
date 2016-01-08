/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.commoninfo.affiche.service.impl;


import java.util.ArrayList;
import java.util.List;

import et.bo.news.service.impl.ArticleSearch;
import et.bo.oa.commoninfo.affiche.service.AficheService;
import et.po.AficheInfo;
import et.po.NewsArticle;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class AficheServiceImpl implements AficheService {
    
    int num = 0;
    
    int indexnum = 0;
    
    private BaseDAO dao=null;
    
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
    
    private AficheInfo createAficheInfo(IBaseDTO dto){
        AficheInfo aficheInfo = new AficheInfo();
        aficheInfo.setId(ks.getNext("afiche_info"));
        aficheInfo.setAficheTitle(dto.get("aficheTitle").toString());
        aficheInfo.setAficheUser(dto.get("aficheUser").toString());
        aficheInfo.setAficheType(dto.get("aficheType").toString());
        aficheInfo.setAficheInfo(dto.get("aficheInfo").toString());
        aficheInfo.setAficheSendto(dto.get("aficheSendto").toString());
        aficheInfo.setCreateTime(TimeUtil.getNowTime());
        aficheInfo.setBeginTime(TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd"));
        aficheInfo.setEndTime(TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd"));
        aficheInfo.setDelSign("n".toUpperCase());
        return aficheInfo;
    }

    public boolean addAficheInfo(IBaseDTO dto) {
        //TODO 需要写出方法的具体实现
        boolean flag = false;
        dao.saveEntity(createAficheInfo(dto));
        flag = true;
        return flag;
    }

    public boolean delAficheInfo(String[] str) {
        //TODO 需要写出方法的具体实现
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            String articleid = str[i];
            AficheInfo aficheInfo = (AficheInfo) dao.loadEntity(
                    AficheInfo.class, articleid);
            //aficheInfo.setDelSign("y".toUpperCase());
            //dao.updateEntity(aficheInfo);
            dao.removeEntity(aficheInfo);
            flag = true;
        }
        return flag;
    }

    public int getAficheInfoSize() {
        //TODO 需要写出方法的具体实现
        return num;
    }

    public List findAficheInfo(IBaseDTO dto, PageInfo pi) {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        AfficheSearch afficheSearch = new AfficheSearch();
        Object[] result=(Object[])dao.findEntity(afficheSearch.searchAfficheInfo(dto,pi));
        int s = dao.findEntitySize(afficheSearch.searchAfficheInfo(dto,pi));
        num = s;
        for(int i = 0,size=result.length;i<size;i++){
            AficheInfo aficheInfo  = (AficheInfo)result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id",aficheInfo.getId());
            dbd.set("aficheTitle",aficheInfo.getAficheTitle());
            l.add(dbd);
        }
        return l;
    }

    public IBaseDTO getAficheInfo(String id) {
        //TODO 需要写出方法的具体实现
        IBaseDTO dto = new DynaBeanDTO();
        AficheInfo aficheInfo = (AficheInfo)dao.loadEntity(AficheInfo.class,dto.get("userId").toString());
        dto.set("id",id);
        dto.set("aficheTitle",aficheInfo.getAficheTitle());
        dto.set("aficheUser",aficheInfo.getAficheUser());
        dto.set("aficheType",aficheInfo.getAficheType());
        dto.set("aficheInfo",aficheInfo.getAficheInfo());
        dto.set("aficheSendto",aficheInfo.getAficheSendto());
        dto.set("createTime",TimeUtil.getTheTimeStr(aficheInfo.getCreateTime(),"yyyy-MM-dd"));
        dto.set("beginTime",TimeUtil.getTheTimeStr(aficheInfo.getBeginTime(),"yyyy-MM-dd"));
        dto.set("endTime",TimeUtil.getTheTimeStr(aficheInfo.getEndTime(),"yyyy-MM-dd"));
        return dto;
    }

    public List getAficheList() {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        AfficheSearch afficheSearch = new AfficheSearch();
        Object[] result=(Object[])dao.findEntity(afficheSearch.searchHeadAffice());
        for(int i = 0,size=result.length;i<size;i++){
            AficheInfo aficheInfo  = (AficheInfo)result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id",aficheInfo.getId());
            dbd.set("title",aficheInfo.getAficheTitle());
            l.add(dbd);
        }
        return l;
    }

    public List getIndexAficheList(IBaseDTO dto,PageInfo pi) {
            //TODO 需要写出方法的具体实现
            List l = new ArrayList();
            AfficheSearch afficheSearch = new AfficheSearch();
            Object[] result = (Object[]) dao.findEntity(afficheSearch
                    .searchIndexInfo(dto, pi));
            int s = dao.findEntitySize(afficheSearch.searchIndexInfo(dto, pi));
            indexnum = s;
            for (int i = 0, size = result.length; i < size; i++) {
                AficheInfo aficheInfo  = (AficheInfo)result[i];
                DynaBeanDTO dbd = new DynaBeanDTO();
                dbd.set("id", aficheInfo.getId());
                dbd.set("title", aficheInfo.getAficheTitle());
                dbd.set("author",aficheInfo.getAficheUser());
                dbd.set("newsTime",TimeUtil.getTheTimeStr(aficheInfo.getCreateTime(),"yyyy-MM-dd"));
                l.add(dbd);
            }
            return l;
    }

    public int getIndexAficheSize() {
        //TODO 需要写出方法的具体实现
        return indexnum;
    }

    public List getAficheList(String id) {
        //TODO 需要写出方法的具体实现
        List l = new ArrayList();
        IBaseDTO dto = new DynaBeanDTO();
        AficheInfo aficheInfo = (AficheInfo)dao.loadEntity(AficheInfo.class,id);
        dto.set("id",id);
        String title = aficheInfo.getAficheTitle();
        if (title.length()>=15) {
            title = title.substring(0,15)+"...";
        }
        dto.set("aficheTitle",title);
        dto.set("aficheUser",aficheInfo.getAficheUser());
        dto.set("aficheType",aficheInfo.getAficheType());
        dto.set("aficheInfo",aficheInfo.getAficheInfo());
        dto.set("aficheSendto",aficheInfo.getAficheSendto());
        dto.set("createTime",TimeUtil.getTheTimeStr(aficheInfo.getCreateTime(),"yyyy-MM-dd"));
        dto.set("beginTime",TimeUtil.getTheTimeStr(aficheInfo.getBeginTime(),"yyyy-MM-dd"));
        dto.set("endTime",TimeUtil.getTheTimeStr(aficheInfo.getEndTime(),"yyyy-MM-dd"));
        l.add(dto);
        return l;
    }

}
