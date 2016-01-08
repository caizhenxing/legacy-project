package com.zyf.web;

import com.zyf.exception.BaseBusinessException;

/**
 * ����쳣���ڱ���һ��<tt>ҵ��ʵ��</tt>����������Ч��, ��������֤������ǰ��<code>form</code>
 * �е�����ʧ��ʱ���쳣
 * @@InvalidPropertyValueException: ����:����{0}��ֵ��{1}, {2}
 * @author scott
 * @since 2006-3-30
 * @version $Id: InvalidPropertyValueException.java,v 1.1 2007/11/05 03:16:04 yushn Exp $
 *
 */
public class InvalidPropertyValueException extends BaseBusinessException {
    
    /**
     * ������֤ʧ���쳣
     * @param propertyName ������������������, ����:<tt>����</tt>
     * @param value        ��������ֵ, ����:<tt>������������������������������������������������</tt>
     * @param cause        ��������ԭ��, ����:<tt>�й��˲�Ӧ������ô��������,������?</tt>
     */
    public InvalidPropertyValueException(String propertyName, String value, String cause) {
        super(new Object[] {propertyName, value, cause});
    }
}
