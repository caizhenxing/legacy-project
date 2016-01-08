package com.zyf.persistent.filter;

import com.zyf.persistent.filter.impl.DefaultDtoMetadata;

/**
 * �����õ���������, �����޸�,�ϲ��ܹ��İ�ȫ��������Ӧ�õ���������ǰִ���߳�. ����ӿ��Ǽܹ�
 * ���ڿ������ݷ��ʵ�, ������ͨ����Ӧ��
 * @author scott
 * @since 2006-5-3
 * @version $Id: ConfigurableConditionService.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 * @see Conditions
 * @see ConditionService
 */
public interface ConfigurableConditionService extends ConditionService {
    /**
     * �ѵ�ǰӦ�õ������ϲ���ϵͳ�����������
     * @param conditions    �ض�Ӧ�õ�����
     * @param paginater     ��ҳ��Ϣ
     * @throws NullPointerException ����κ�һ��������<code>null</code>
     */
    void applyAppendCondition(Condition[] conditions, Paginater paginater);
    
    /**
     * ȡ���ϲ���ϵͳ�����еĵ�ǰӦ�õ�����
     */
    void cancelAppendCondition();
    
    /**
     * ��װ��ǰִ���߳��е�����Ϊ<code>Conditions</code>, ����������<code>null</code>,
     * ִ����װ�����Ӿ�ʱʹ��ȱʡ�Ļ���, �޷�ʹ���ض���Ԫ��Ϣ
     * @param  dto Ҫ������<code>DTO</code>����
     * @return ��װ����������, �����ǰû���κ���������
     *         {@link Conditions#EMPTY_CONDITIONS <code>EMPTY_CONDITIONS</code>}
     * @see DefaultDtoMetadata#INDEPENDENT_DTO_METADATA
     */
    Conditions populateCondition(Class dto);
    
    /**
     * �ڲ����е�<code>sql</code>�����������Ͳ����м����ҳ����, ���ִ��������������
     * ��Ϊ<code>UI</code>�ṩ֧��
     * @param sp        Ҫ�����ҳ��������<code>sql</code>�����������(����:<code>hql</code>)�Ͳ���
     * @return   �����ҳ���<code>sql</code>���Ͳ���
     */
    StatementAndParameters populateSqlWithPaginater(StatementAndParameters sp);
}
