/*
 * �㽭��ѧ�������Ű�Ȩ����(2007-2008). Power by COHEG team.
 */
package com.zyf.framework.codename;

import com.zyf.exception.BaseBusinessException;

/**
 * �������µ�<code>CodeName</code>ʱ��֤��Ч��
 * @author zhangli
 * @since 2007-3-22
 * @version $Id: InvalidCodeNameException.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 */
public class InvalidCodeNameException extends BaseBusinessException {
    
    /** �������޸�û����д<code>code</code>��<code>name</code> */
    private static String KEY_NULL = CodeName.class.getName() + ".null";
    
    /** �������޸�<code>CodeName</code>ʱ<code>code</code>��<code>name</code>����Խ�� */
    private static String KEY_EXCEED = CodeName.class.getName() + ".exceed";
    
    /**
     * ����<code>code</code>��<code>name</code>�ǿ�ֵʱ�Ĵ���
     */
    public InvalidCodeNameException() {
        super(KEY_NULL, new Object[] {});
    }
    
    public InvalidCodeNameException(String propertyName, int limitation) {
        super(KEY_EXCEED, new Integer[] {new Integer(limitation)});
    }
}
