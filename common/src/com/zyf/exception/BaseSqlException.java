package com.zyf.exception;

/**
 * 系统中在数据库操作过程中发生的所有异常的基类, 大多数情况下这个类及子类是由架构创建的
 * 
 * @author scott
 * @since 2006-4-1
 * @version $Id: BaseSqlException.java,v 1.1 2007/11/05 03:16:08 yushn Exp $
 *
 */
public abstract class BaseSqlException extends BaseSystemException {
    public BaseSqlException(Throwable e) {
    }
}
