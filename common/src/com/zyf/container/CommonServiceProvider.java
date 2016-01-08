package com.zyf.container;

import com.zyf.persistent.filter.ConditionService;

/**
 * 检索系统中常用的<code>service</code>的<code>utility</code>类
 * @author scott
 * @since 2006-4-27
 * @version $Id: CommonServiceProvider.java,v 1.1 2007/11/05 03:16:01 yushn Exp $
 *
 */
public abstract class CommonServiceProvider {
    public static ConditionService getConditionService() {
        return (ConditionService) ServiceProvider.getService(ConditionService.SERVICE_NAME);
    }
}
