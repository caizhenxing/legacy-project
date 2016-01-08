/*
 * 浙江大学快威集团版权所有(2007-2008). Power by COHEG team.
 */
package com.zyf.framework.codename;

import com.zyf.exception.BaseBusinessException;

/**
 * 当创建新的<code>CodeName</code>时验证有效性
 * @author zhangli
 * @since 2007-3-22
 * @version $Id: InvalidCodeNameException.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 */
public class InvalidCodeNameException extends BaseBusinessException {
    
    /** 创建或修改没有填写<code>code</code>或<code>name</code> */
    private static String KEY_NULL = CodeName.class.getName() + ".null";
    
    /** 创建或修改<code>CodeName</code>时<code>code</code>或<code>name</code>长度越界 */
    private static String KEY_EXCEED = CodeName.class.getName() + ".exceed";
    
    /**
     * 用于<code>code</code>或<code>name</code>是空值时的错误
     */
    public InvalidCodeNameException() {
        super(KEY_NULL, new Object[] {});
    }
    
    public InvalidCodeNameException(String propertyName, int limitation) {
        super(KEY_EXCEED, new Integer[] {new Integer(limitation)});
    }
}
