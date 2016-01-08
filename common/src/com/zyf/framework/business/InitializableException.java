/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.business;

import com.zyf.exception.BaseSystemException;
import com.zyf.exception.ExceptionUtils;

/**
 * ���ڱ�ʾ�ڳ�ʼ�������з����Ĵ���, �Ѵ�{@link Initializable#execute()}�����׳����κ��쳣
 * ����װ������쳣
 * @author zhangli
 * @version $Id: InitializableException.java,v 1.1 2007/11/05 03:16:06 yushn Exp $
 * @since 2007-6-4
 */
public class InitializableException extends BaseSystemException {
    
    public InitializableException(Initializable init, Throwable ex) {
        StringBuffer msg = new StringBuffer(init.getClass().getName())
            .append(':').append(ExceptionUtils.getCauseMessage(ex));
        setErrorInformation(msg.toString());
        super.initCause(ex);
    }
}
