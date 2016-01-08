/*
 * 浙江大学快威集团电力事业部版权所有(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.business;

import com.zyf.exception.BaseSystemException;

/**
 * 正在执行{@link Initializable}的初始化过程中对这个服务的方法执行了调用时抛出这个异常
 * @author zhangli
 * @version $Id: InitializableInProcessException.java,v 1.1 2007/11/05 03:16:06 yushn Exp $
 * @since 2007-6-4
 */
public class InitializableInProcessException extends BaseSystemException {
    
    public InitializableInProcessException(Initializable init) {
        super(new String[] {init.getClass().getName()});
    }
}
