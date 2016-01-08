/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.web;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;

import com.zyf.exception.UnexpectedException;
import com.zyf.framework.codename.CodeName;

/**
 * ��ҳ��Ĵ���ת��<code>CodeName</code>
 * @author zhangli
 * @version $Id: CodeNameConverter.java,v 1.1 2007/11/05 03:16:05 yushn Exp $
 * @since 2007-4-20
 */
public class CodeNameConverter implements Converter {

    public Object convert(Class type, Object value) {
        CodeName cn = null;
        try {
            cn = (CodeName) type.newInstance();
            PropertyUtils.setProperty(cn, "code", value);
        } catch (Exception e) {
            throw new UnexpectedException("���ܴ���[" + type.getName() + "]��ʵ��", e);
        }
        return cn;
    }
}
