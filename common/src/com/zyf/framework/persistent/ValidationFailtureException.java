/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.persistent;

import com.zyf.exception.BaseBusinessException;

/**
 * {@link Validatable}验证业务实体状态错误时抛出这个异常
 * @author zhangli
 * @version $Id: ValidationFailtureException.java,v 1.1 2007/11/05 03:16:18 yushn Exp $
 * @since 2007-4-7
 */
public class ValidationFailtureException extends BaseBusinessException {
    
    public ValidationFailtureException(String key, Object[] params) {
        super(key, params);
    }
    
    public ValidationFailtureException(Object[] params) {
        super(params);
    }
}
