/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service;

import java.util.List;

import com.zyf.common.report.config.ReportConfig;

/**
 * �������ù������, ά����������
 * @author zhangli
 * @version $Id: ReportManagementService.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @since 2007-5-6
 */
public interface ReportManagementService extends ReportService {
    
    /**
     * ����һ���µı�������
     * @param code �������ñ���
     * @param name ��������
     * @return �µı�������
     */
    ReportConfig createReportConfig(String code, String name);
    
    /**
     * ����һ���½�������б������õ��޸�
     * @param reportConfig ��������
     */
    void saveReportConfig(ReportConfig reportConfig);
    
    /**
     * ����һ����������
     * @param code �������ñ���
     * @return ��������
     * @throws IllegalStateException ���û�������ı�������
     */
    ReportConfig loadReportConfig(String code);
    
    /**
     * ɾ�����б�������
     * @param reportConfig ��������
     */
    void deleteReportConfig(ReportConfig reportConfig);
    
    /**
     * �г�����ָ���������ñ�������б�������, �����������������ñ���, ��������ϵͳ����,
     * ���Ǹ���ģ���<code>MODULE_NAME</code>, �����ǹ�������, �������Ǳ�������, ֮��
     * ��","�ָ�
     * @return ���������ı�������, Ԫ��������{@link ReportConfig}, ���û�����ݷ���
     * <code>Collections.EMPTY_LIST</code>
     */
    List listReportConfigs(String code);

}
