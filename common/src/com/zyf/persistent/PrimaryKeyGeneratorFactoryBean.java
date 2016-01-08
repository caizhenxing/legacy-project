package com.zyf.persistent;

import org.springframework.beans.factory.FactoryBean;

import com.zyf.utils.UUIDGenerator;

/**
 * �������ɳ־û���ʹ�õ�����, ���ɵ�������<code>String</code>����, ������<b>32</b>λ, ʵ��
 * �����ɲ���ʹ��<code>UUIDGenerator</code>
 * @author scott
 * @since 2006-3-28
 * @version $Id: PrimaryKeyGeneratorFactoryBean.java,v 1.1 2007/11/05 03:16:27 yushn Exp $
 *
 */
public class PrimaryKeyGeneratorFactoryBean implements FactoryBean {

    public Object getObject() throws Exception {
        return (String) new UUIDGenerator().generate();
    }

    public Class getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
