/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.codename.configuration;

import java.util.List;
import java.util.Map;

import com.zyf.framework.codename.AbstractModuleCodeName;
import com.zyf.framework.service.ServiceBaseWithNotAllowedNullParamters;

/**
 * ϵͳ���÷���, �ṩ�˷ֲ����õĸ���, �������ģ���Լ�ģ���µĹ��ܶ���, ����ϵͳȫ�ֵ�����λ��
 * ���ڵ���, ������ģ������ÿ���ͨ��ģ�������. 
 * @author zhangli
 * @version $Id: ConfigurationService.java,v 1.1 2007/11/05 03:16:00 yushn Exp $
 * @since 2007-6-17
 */
public interface ConfigurationService extends ServiceBaseWithNotAllowedNullParamters {
    
    /**
     * ��������֮��ķָ���, ע�ⲻҪʹ������ַ���Ϊ<code>code</code>, ���ʹ���˿��ܳ���
     * ���ܼ�������������õ����, ����ʹ��"flaw.specialty"��Ϊ����<code>code</code>,��
     * �Ͳ���ʹ��"flaw."�������κ�����
     */
    String SEPARATOR = ".";
    
    /**
     * �����Բ���ָ�����������Ϊ�����õ����÷���, ������÷���������Կ�����������Լ����µ�
     * ����
     * @param prefix ����һ��{@link ConfigurationService}, �ӷ��ص�ʵ�����Բ��������
     * ����ָ���Ĵ�������ü���������
     * @return ���Բ�������ָ��ǰ׺���������õ�{@link ConfigurationService}
     * @throws NullPointerException ���������<code>null</code>��<code>empty</code>
     */
    ConfigurationService configure(String prefix);
    
    /**
     * �г����е�����, Ԫ��������{@link AbstractModuleCodeName}
     * @return ���е�����, ���û�н������<code>Collections.EMPTY_LIST</code>
     */
    List list();
    
    /**
     * ����һ��<code>Map</code>, <code>key</code>�Ǵ���, <code>value</code>��{@link AbstractModuleCodeName}
     * ���д������{@link AbstractModuleCodeName#getCode()}��ֵ
     * @param type Ҫ��������������, �����һ����{@link AbstractModuleCodeName}������
     * @return <code>Map</code>��ʽ�����е�����, ���û���κ����÷���<code>Collections.EMPTY_MAP</code>
     */
    Map map();
    
    /**
     * ����ָ�����������, ��û�ж�Ӧ������ʱ����<code>null</code>
     * @param code ���õĴ���
     * @return �Բ���Ϊ���������, <b>���û��ָ����������÷���<code>null</code></b>
     * @throws NullPointerException ���������<code>null</code>��<code>empty</code>
     */
    AbstractModuleCodeName load(String code);
    
    /**
     * ��ѯ������÷������õ���������, ���õ�����һ����{@link AbstractModuleCodeName}��
     * ����, ��������ʵ��ʹ�������������ʵ�ʵ���������
     * @return ������÷����������������
     */
    Class type();
}
