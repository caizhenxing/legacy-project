package com.zyf.persistent.filter;

/**
 * ���ڼܹ���ȫ,ͨ�ò�ѯĿ���޸�һ��<code>sql</code>�����Ļص��ӿ�, ʵ����Ϊ<code>SqlMapProxy</code>
 * ����������
 * 
 * @author scott
 * @since 2006-5-6
 * @version $Id: ReworkingParameter.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 *
 */
public interface ReworkingParameter {
    /**
     * �ṩ�˿��޸ĵĲ���, ���������ȡ�������ʵ�����õķ��� 
     * @param params
     */
    void rework(Object[] params);
    
    /**
     * �ָ��޸��˵Ĳ���
     */
    void restore();
}
