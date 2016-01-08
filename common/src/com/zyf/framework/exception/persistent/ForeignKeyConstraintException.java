package com.zyf.framework.exception.persistent;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * �־û����쳣, ͨ�����ڷ������ݿ�ʱ, �����ӱ��¼�Ҳ�������ļ�����������¼ʱ��Ϊ�ӱ����Լ��
 * ���޷�ɾ��/�޸�����
 * 
 * @author scott
 * @since 2006-2-9
 * @version $Id: ForeignKeyConstraintException.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 *
 */
public class ForeignKeyConstraintException extends DataIntegrityViolationException implements DatabaseException {
    
    private String sql;
    
    private String code = "2292";

    public ForeignKeyConstraintException(String msg, String sql, Throwable ex) {
        super(msg, ex);
        this.sql = sql;
    }
    
    public String getSql() {
        return sql;
    }

    public String getSqlCode() {
        return code;
    }
}
