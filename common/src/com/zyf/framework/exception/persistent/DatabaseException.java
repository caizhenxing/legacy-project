/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.exception.persistent;

import com.zyf.framework.exception.SystemException;

/**
 * ���е����ݿ���ص��쳣ʵ������ӿ�
 * @author zhangli
 * @version $Id: DatabaseException.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 * @since 2007-4-23
 */
public interface DatabaseException extends SystemException {
    
    /**
     * ���������쳣�����ݿ�<code>sql</code>���
     * @return �����쳣�����ݿ�<code>sql</code>���, ���������е��쳣�������ֵ
     */
    String getSql();
    
    /**
     * ���������쳣�����ݿ����
     * @return �쳣��ص����ݿ����, Ŀǰ����ʹ�õ����ݿ��Ʒ���������ֵ
     */
    String getSqlCode();
}
