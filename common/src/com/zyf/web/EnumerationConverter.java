/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.web;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.Converter;

import com.zyf.exception.UnexpectedException;
import com.zyf.framework.codename.EnumerationCodeName;

/**
 * ����{@link EnumerationCodeName}���͵�����
 * @author zhangli
 * @version $Id: EnumerationConverter.java,v 1.1 2007/11/05 03:16:05 yushn Exp $
 * @since 2007-5-1
 */
public class EnumerationConverter implements Converter {
    public Object convert(Class type, Object value) {
        try {
            Method method = type.getMethod(EnumerationCodeName.METHOD_NAME, EnumerationCodeName.METHOD_PARAMETERS);
            EnumerationCodeName cn = (EnumerationCodeName) method.invoke(null, new String[] {(String) value});
            return cn;
        } catch (Exception e) {
            throw new UnexpectedException("ִ��" + type.getName() + "��ת��ʱ����,���������ʵ��", e);
        }
    }
}
