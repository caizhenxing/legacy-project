package com.zyf.persistent.filter;

import com.zyf.core.GlobalParameters;
import com.zyf.persistent.filter.impl.DefaultDtoMetadata;

/**
 * ���ڲ����־û�����������ķ���, �ṩ��ע��<code>DtoMetadata</code>�ķ���. һ��<code>
 * DtoMetadata</code>�ṩ<code>DTO</code>�ͱ��������Եĳ־û�ʵ��֮��Ķ�Ӧ��ϵ
 * @author scott
 * @since 2006-5-3 2:04
 * @version $Id: ConditionService.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 * @see DtoMetadata
 * @see DefaultDtoMetadata
 */
public interface ConditionService {
    String SERVICE_NAME = GlobalParameters.MODULE_NAME + ".conditionService";
    
    /**
     * Ϊһ��<code>DTO</code>ע��һ��Ԫ���ݴ�����, ����Ѿ�������ͬ���͵Ĵ�����, �����µ�
     * �滻�ɵ�. ���������ṩ��ʲô���͵�<code>Metadata</code>
     * @param metadata    Ҫע���<code>DtoMetadata</code>
     * @return <b>�������<code>dto</code>��û��ע��ͷ���<code>null</code></b>, ����
     *         ����ԭ�е�<code>DtoMetadata</code>
     * @throws NullPointerException ���������<code>null</code>         
     * @throws IllegalArgumentException �������û���ṩҪ�����<code>DTO</code>������, 
     *         (<code>DtoMetadata.getDtoTpye() == null</code>)
     */
    DtoMetadata registry(DtoMetadata metadata);
    
    /**
     * ȡ������Ϊ<code>type</code>��<code>DtoMetadata</code>��ע��
     * @param type Ҫȡ��Ԫ��Ϣע���<code>dto</code>����
     * @return ����ɹ�ȡ������<code>true</code>, ���û��������͵�Դ��Ϣ����<code>false</code>
     * @throws NullPointerException ���������<code>null</code>
     */
    boolean deregistry(Class type);
    
    /**
     * Ϊ����ָ����<code>dto</code>����Ԫ��Ϣ, ������<code>dto</code>û��ע��Ԫ��Ϣ��
     * ������<code>null</code>, ����ȱʡ��<code>DefaultDtoMetadata</code>
     * @param dto Ҫ����Ԫ��Ϣ��<code>dto</code>����
     * @return ���<code>dto</code>��Ԫ��Ϣ
     */
    DtoMetadata getDtoMetadata(Class dto);
}
