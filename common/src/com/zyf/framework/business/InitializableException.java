/*
 * 浙江大学快威集团电力事业部版权所有(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.business;

import com.zyf.exception.BaseSystemException;
import com.zyf.exception.ExceptionUtils;

/**
 * 用于表示在初始化工作中发生的错误, 把从{@link Initializable#execute()}方法抛出的任何异常
 * 都包装成这个异常
 * @author zhangli
 * @version $Id: InitializableException.java,v 1.1 2007/11/05 03:16:06 yushn Exp $
 * @since 2007-6-4
 */
public class InitializableException extends BaseSystemException {
    
    public InitializableException(Initializable init, Throwable ex) {
        StringBuffer msg = new StringBuffer(init.getClass().getName())
            .append(':').append(ExceptionUtils.getCauseMessage(ex));
        setErrorInformation(msg.toString());
        super.initCause(ex);
    }
}
