/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.common.impl;


import ocelot.common.key.SequenceService;
import ocelot.framework.base.dao.BaseDAO;
import et.po.SysKey;

public class SequenceServiceImpl implements SequenceService {
    
    private BaseDAO dao=null;

    public long getNext(String keyName, int poolSize) {
        //TODO 需要写出方法的具体实现
        SysKey sysKey=null;
        Object o = dao.loadEntity(SysKey.class,keyName);
        if(o==null)  sysKey= new SysKey(keyName);
        else sysKey = (SysKey)o;
        if(null==sysKey.getTbIdMax())sysKey.setTbIdMax("0");
        long l =Long.parseLong(sysKey.getTbIdMax())+poolSize;
        sysKey.setTbIdMax(String.valueOf(l));
        dao.saveEntity(sysKey);
        return l;
    }

    public BaseDAO getDao() {
        return dao;
    }

    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }

}
