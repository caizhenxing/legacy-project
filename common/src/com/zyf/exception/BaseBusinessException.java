package com.zyf.exception;


/**
 * ϵͳ������<b>ҵ���쳣</b>�ĳ���, ҵ���쳣����ƿ��������п���Ԥ�ϵ��Ĵ������. ���������쳣��
 * �������̵�һ����. �ܹ��쳣�������Ϊ�����쳣����ǡ���Ĵ������, ����������ڴ���֮��Ĵ�����Ϣ
 * @author scott
 * @since 2006-4-1
 * @version $Id: BaseBusinessException.java,v 1.1 2007/11/05 03:16:08 yushn Exp $
 *
 */
public abstract class BaseBusinessException extends BaseSystemException {
    public BaseBusinessException() {
        super();
    }
    
    public BaseBusinessException(String key, Object[] params) {
        super(key, params);
    }
    
    public BaseBusinessException(Object[] params) {
        super(params);
    }
}
