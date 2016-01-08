package com.zyf.persistent.filter;

import java.util.List;

/**
 * ����һ��<code>DTO</code>�ڳ־û����Ԫ��Ϣ, ͨ�������ݿ�ı����Ƽ������������ƵĶ�Ӧ��
 * ϵ, ÿһ��<code>DTO</code>����һ�����ʵ����֮��Ӧ
 * @author scott
 * @since 2006-4-19
 * @version $Id: DtoMetadata.java,v 1.1 2007/11/05 03:16:03 yushn Exp $
 *
 */
public interface DtoMetadata {
    
    /**
     * �����ṩ��Ϣ��<code>entry</code>������
     * @return
     */
    Class getDtoType();
    
    /**
     * ���������Ʒ�����Ӧ����������
     * @param columnName ���ݿ���������
     * @return ����ж�Ӧ����������, ���Է���<code>null</code>��ʾû��������֮��Ӧ
     */
    String getPropertyName(String columnName);
    
    /**
     * �����������Ʒ�����Ӧ��������
     * @param propertyName <code>domain object</code>����������
     * @return ������Զ�Ӧ��������, ���Է���<code>null</code>��ʾû������֮��Ӧ
     */
    String getColumnName(String propertyName);
    
    /**
     * �������<code>domain object</code>��Ӧ�����ݿ�ı�/��ͼ����
     * @return <code>domain object</code>��Ӧ�����ݿ�ı�/��ͼ����
     */
    String getTableName();
    
    /**
     * ����������<code>domain object</code>������/Ψһ������������
     * @return ������<code>domain object</code>������/Ψһ������������
     */
    String[] getPrimaryKey();

    /**
     * ���ش����ݿ��е�һ����װ<code>domain object</code>ʱҪ��װ����������, ֻ��������б�
     * �е����Բ���װ, <b>���������<code>EMPTY_LIST</code>����װ���е�����</b>
     * @return Ҫ��װ��<code>domain object</code>������
     */
    List getProperties();
}
