/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.exception.persistent;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

/**
 * @author zhangli
 * @version $Id: InsertedValueTooLongException.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 * @since 2007-5-28
 */
public class InsertedValueTooLongException extends DataAccessException implements DatabaseException {
    public static final String CODE = "1461";
    private String sql;
    
    public InsertedValueTooLongException(String msg, String sql, SQLException ex) {
        super(msg, ex);
        this.sql = sql;
    }
    
    public String getSql() {
        return sql;
    }

    public String getSqlCode() {
        return CODE;
    }
}
