/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.business;

import com.zyf.exception.BaseSystemException;

/**
 * ����ִ��{@link Initializable}�ĳ�ʼ�������ж��������ķ���ִ���˵���ʱ�׳�����쳣
 * @author zhangli
 * @version $Id: InitializableInProcessException.java,v 1.1 2007/11/05 03:16:06 yushn Exp $
 * @since 2007-6-4
 */
public class InitializableInProcessException extends BaseSystemException {
    
    public InitializableInProcessException(Initializable init) {
        super(new String[] {init.getClass().getName()});
    }
}
