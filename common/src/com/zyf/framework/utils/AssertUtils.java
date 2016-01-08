/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.utils;

import org.apache.commons.lang.StringUtils;

/**
 * һ����Եķ���, ����ִ�з����Բ�������֤
 * @author zhangli
 * @version $Id: AssertUtils.java,v 1.1 2007/11/05 03:16:07 yushn Exp $
 * @since 2007-4-22
 */
public abstract class AssertUtils {
    
    public static final String NULL_VALUE_MESSAGE = "�����ǿ�ֵ"; 
    
    public static void notNull(Object value) {
        notNull(value, null);
    }
    
    /**
     * �����жϷ�������ʱ�����Ƿ�Ϊ<code>null</code>, ����<code>String</code>���͵İ���<cpde>empty</code>,
     * ��������ǿ��׳�<code>NullPointerExpcetion</code>
     * @param value     Ҫ�жϵĲ���
     * @param message   ��������<code>null</code>,<code>empty</code>ʱ���쳣��Ϣ
     */
    public static void notNull(Object value, String message) {
        if (value == null || (value.getClass() == String.class && StringUtils.isBlank(value.toString()))) {
            if (StringUtils.isBlank(message)) {
                message = NULL_VALUE_MESSAGE;
            }
            throw new NullPointerException(message);
        }
    }
}
