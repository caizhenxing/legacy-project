/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
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
 * ���÷����ҵ��ʵ��, û�г־û�����, ʵ����{@link Initializable}�ӿ�, ��ϵͳ����ʱ��<code>metadata</code>
 * �г�ʼ������
 * @author zhangli
 * @version $Id: AbstractConfigurationService.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 * @since 2007-6-19
 */
public class AbstractConfigurationService implements ConfigurationService, Initializable, InitializingBean {
    
    /** ������÷�������������������, һ����{@link AbstractModuleCodeName}������ */
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
