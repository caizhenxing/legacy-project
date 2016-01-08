package com.zyf.container;

import com.zyf.exception.BaseSystemException;

/**
 * <p>配置异常, 说明<code>spring framework xml bean factory</code>没有定义必须的<code>
 * bean</code>配置或者没有按照要求定义配置, 通常不要捕获这个异常</p>
 * 
 * @author scott
 * @since 2006-3-16
 * @version $Id: ConfigurationException.java,v 1.1 2007/11/05 03:16:01 yushn Exp $
 */
public class ConfigurationException extends BaseSystemException {
    public ConfigurationException(String msg) {
        super(new Object[] {msg});
    }
}
