/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service;

import java.util.List;

import com.zyf.common.report.config.ReportConfig;

/**
 * ��������<code>DAO</code>, ��Ϊͬһ��{@link ReportManagementService}���ڶ��ʵ��,
 * ������<code>excel</code>��<code>velocity</code>��, ���ԾͰ����ݷ��ʶ��󵥶�������
 * , �Ա㲻ͬ��ʵ������������ݷ����߼�
 * @author zhangli
 * @version $Id: ReportConfigDao.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @since 2007-5-8
 */
public interface ReportConfigDao {
    
    /** ɾ��һ���������� */
    void deleteEntity(ReportConfig reportConfig);
    
    /** �������еĻ��߱���һ���½��ı������� */
    void saveEntity(ReportConfig reportConfig);
    
    /** 
     * ���ݲ��������ѱ���ı�������, ��Ϊ����<code>code</code>Ϊ��������Ҳ���Ǹ���
     * <code>code</code>����
     */
    ReportConfig loadEntityById(String code);
    
    /**
     * �г�����ָ��<code>code</code>����������, ���������Ҫ����Էֲ���������õ����
     * , <code>code</code>��ηֲ���{@link ReportManagementService}������
     * @param code Ҫ���ҵı�������<code>code</code>ģʽ
     * @return ���������Ľ��
     */
    List listEntities(String code);
}
