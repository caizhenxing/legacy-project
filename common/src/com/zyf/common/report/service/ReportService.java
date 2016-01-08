/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.common.report.service;

import java.util.Map;

import com.zyf.common.CommonConstants;
import com.zyf.common.report.config.ReportConfig;
import com.zyf.framework.service.ServiceBaseWithNotAllowedNullParamters;

/**
 * ����������, ���ݸ��������ݺ�ģ����ɱ�����, �Ѵ�����ı���д���������, ʵ����Ҫ����
 * �������Ĵ�С, ��������ڴ����. �κ�һ��ʵ���ڴ����̶������ı�������˶�ݱ���ʱ, (����
 * �����{@link ReportConfig#isFixedRow()}����<code>true</code>)�����������Ϊ�����ļ�
 * ����ѹ��Ϊһ��<code>zip</code>���
 * @author zhangli
 * @version $Id: ReportService.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 * @since 2007-5-6
 */
public interface ReportService extends ServiceBaseWithNotAllowedNullParamters {
    
    String SERVICE_NAME = CommonConstants.MODULE_NAME + ".reportService";
    
    /**
     * �������ݺ����ô�������, �ѽ��д���������, ���������ѱ������ú����ɵı��������д
     * ������<code>reportSource</code>��
     * @param data      ����, ��ν���������ʵ��, ͨ��<code>key</code>��ģ���е�ռλ��
     * @param reportSource ���պ�д�����ݵĴ�����, �������ȡ<code>code</code>�������
     * @throws IllegalArgumentException �������û�����ñ��������
     */
    void process(Map data, ReportSource reportSource);
}
