/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.codename;

import com.zyf.exception.BaseSystemException;

/**
 * �����ҵ�ӳ���ҵ��ʵ������, ����һ��ϵͳ��ҵ��ʹ��<code>CodeName</code>���Ƹ��������
 * һ��ϵͳ��ʵ�������ʱû���ҵ�Ҫ���õ�ʵ��<code>Class</code>, ʹ�����ַ�����Ӧ��һ��Ҫ
 * ��������쳣, ���������¿���������û�в����������ϵͳ�������
 * @author zhangli
 * @version $Id: MappedEntityTypeNotFound.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 * @since 2007-6-9
 */
public class MappedEntityTypeNotFound extends BaseSystemException {
    
    public MappedEntityTypeNotFound(String msg, ClassNotFoundException ex) {
        super(msg, ex);
    }
}
