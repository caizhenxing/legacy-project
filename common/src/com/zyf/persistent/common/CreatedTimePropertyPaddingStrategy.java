package com.zyf.persistent.common;

import java.beans.PropertyDescriptor;
import java.util.Date;

/**
 * �����ڴ����µļ�¼ʱ��д<code>createdTime</code>������, ע�����ʱ����<code>JVM</code>
 * ��ʱ��, ����и��ϸ��Ҫ��, ����ʹ�����ݿ�ʱ��, ����<code>fetchCurrentTime</code>����
 * @author scott
 * @since 2006-4-13
 * @version $Id: CreatedTimePropertyPaddingStrategy.java,v 1.1 2007/11/05 03:16:22 yushn Exp $
 *
 */
public class CreatedTimePropertyPaddingStrategy extends AbstractPropertyPaddingStrategy {
    private static final String PROPERTY_NAME = "createdTime";
    
    protected boolean isShouldSet(Object bean, PropertyDescriptor desc, Object result) {
        return result == null;
    }

    protected Object settingWith() {
        return fetchCurrentTime();
    }

    protected boolean isDesirableProperty(String propertyName) {
        return PROPERTY_NAME.equals(propertyName);
    }
    
    protected Date fetchCurrentTime() {
        return new Date();
    }

}
