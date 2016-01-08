package com.zyf.persistent.ibatis;

import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.zyf.persistent.DaoHelper;

/**
 * ���ڷ������<code>IBATIS</code>��<code>utility</code>
 * @author scott
 * @since 2006-4-20
 * @version $Id: IbatisUtils.java,v 1.1 2007/11/05 03:16:07 yushn Exp $
 *
 */
public abstract class IbatisUtils {
    /**
     * ָ��<code>IBATIS sql mapping id</code>���ض����<code>sql</code>���. 
     * @param id    ��������<code>mapping id</code>
     * @param param ���<code>sql</code>���ʹ�õĲ���
     * @return   ���ؼ����������
     */
    public static String getSql(String id, Object param) {
        ExtendedSqlMapClient sqlMapClient = (ExtendedSqlMapClient) DaoHelper.getSqlMapClientTemplate().getSqlMapClient();
        Sql sql = sqlMapClient.getMappedStatement(id).getSql();
        return sql.getSql(null, param);
    }
}
