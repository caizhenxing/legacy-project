/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.exception.persistent;

import com.zyf.framework.exception.SystemException;

/**
 * 所有的数据库相关的异常实现这个接口
 * @author zhangli
 * @version $Id: DatabaseException.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 * @since 2007-4-23
 */
public interface DatabaseException extends SystemException {
    
    /**
     * 检索产生异常的数据库<code>sql</code>语句
     * @return 产生异常的数据库<code>sql</code>语句, 并不是所有的异常都有这个值
     */
    String getSql();
    
    /**
     * 检索产生异常的数据库代码
     * @return 异常相关的数据库代码, 目前我们使用的数据库产品总是有这个值
     */
    String getSqlCode();
}
