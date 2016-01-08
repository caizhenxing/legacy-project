/*
 * 浙江大学快威集团电力事业部版权所有(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.codename.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.zyf.framework.business.Initializable;
import com.zyf.framework.business.InitializableException;
import com.zyf.framework.business.InitializableInProcessException;
import com.zyf.framework.codename.AbstractModuleCodeName;

/**
 * 配置服务的业务实现, 没有持久化部分, 实现了{@link Initializable}接口, 在系统启动时从<code>metadata</code>
 * 中初始化配置
 * @author zhangli
 * @version $Id: AbstractConfigurationService.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 * @since 2007-6-19
 */
public class AbstractConfigurationService implements ConfigurationService, Initializable, InitializingBean {
    
    /** 这个配置服务所操作的配置类型, 一定是{@link AbstractModuleCodeName}的子类 */
    private Class type;

    public ConfigurationService configure(String prefix) {
        // TODO Auto-generated method stub
        return null;
    }

    public List list() {
        // TODO Auto-generated method stub
        return null;
    }

    public AbstractModuleCodeName load(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    public Map map() {
        // TODO Auto-generated method stub
        return null;
    }

    public Class type() {
        // TODO Auto-generated method stub
        return null;
    }

    public void initialize() throws InitializableException, InitializableInProcessException {
        // TODO Auto-generated method stub
        
    }

    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return false;
    }

    public void afterPropertiesSet() throws Exception {
        if (type == null) {
            throw new IllegalArgumentException();
        }
    }
}
