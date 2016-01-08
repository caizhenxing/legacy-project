/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.numbering.service;

import com.zyf.common.CommonConstants;
import com.zyf.common.numbering.NumberingConfigure;
import com.zyf.core.ServiceBase;

/**
 * ���ɱ�ŵķ���
 * @since 2007-1-12
 * @author scott
 * @version $Id: NumberingService.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @see NumberingConfigure
 */
public interface NumberingService extends ServiceBase {
    
    String SERVICE_NAME = CommonConstants.MODULE_NAME + ".numberingService";
    
    /**
     * ���ݲ���ָ���ķ��������ɱ��
     * @param businessName  �Ѿ����õı�����ɹ����ҵ������
     * @return ���ɵı��, �����ؿ�ֵ
     * @throws IllegalArgumentException �����Ÿ�ʽ����
     * @throws NullPointerException ��������ǿ�ֵ��û���ҵ�ָ�����Ƶ�����
     */
    String number(String businessName);
    
    /**
     * ���ݲ���ָ���ķ������������ָ�ʽ�ı��
     * @param businessName  �Ѿ����õı�����ɹ����ҵ������
     * @return ���ɵı��, �����ؿ�ֵ
     * @throws NullPointerException ��������ǿ�ֵ��û���ҵ�ָ�����Ƶ�����
     */
    Double numberInDigit(String businessName);
}
