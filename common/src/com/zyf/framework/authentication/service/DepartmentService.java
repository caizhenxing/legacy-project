/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.authentication.service;

import java.util.List;

import com.zyf.framework.authentication.entity.Department;
import com.zyf.framework.codename.DepartmentCodeName;
import com.zyf.framework.service.ServiceBaseWithNotAllowedNullParamters;

/**
 * ����������Ϣ�ķ���, ��Ϊ������Ϣ�����������԰������������¹����ó���, ������û�����¹���
 * ʱ��Ȼ���������Ϣ�ļ���, ��������ʵ����{@link AuthenticationService}��ͬ, Ҳ���ڼ���
 * ����ķ���, ��ʹ�������¹���ϵͳ
 * TODO:���ڽ���{@link #listChildren(String)}��{@link #listDepartmentCodeNames(String)}
 * ֧��{@link Department#isHasChildren()}����, ���룡
 * @author zhangli
 * @version $Id: DepartmentService.java,v 1.1 2007/11/05 03:16:19 yushn Exp $
 * @since 2007-4-30
 */
public interface DepartmentService extends ServiceBaseWithNotAllowedNullParamters {
    
    String SERVICE_NAME = "system.departmentService";
    
    /**
     * ������֯�����еĸ��ڵ�, ����ڵ���һ����ҵ, ����һ���糧
     * @return ��֯�����еĸ��ڵ�, ������<code>null</code>
     */
    Department top();
    
    /**
     * ���ݲ���<code>id</code>ֵ����������Ϣ
     * @param departmentId Ҫ�����Ĳ��ŵ�<code>id</code>
     * @return ָ��<code>id</code>�Ĳ�����Ϣ, ������<code>null</code>
     * @throws NullPointerException ���������<code>null</code>
     * @throws IllegalArgumentException ���ָ��<code>id</code>����û�ҵ� 
     */
    Department loadDepartment(String departmentId);
    
    /**
     * ����һ�����ż���������µ��Ӳ���, ���صĽ��ͬ��˳���ǲ��ŵ�����˳��
     * @param code Ҫ�����Ĳ��ű���
     * @return ���ŵ��б�, ������һ��Ԫ��, Ԫ��������{@link Department}
     * @throws IllegalArgumentException ����������������
     */
    List listDepartments(String code);
    
    /**
     * ����ָ��<code>id</code>�������Ӳ���
     * @param parentId �ϼ�����
     * @return �ϼ����ŵ��Ӳ���, Ԫ����{@link DepartmentCodeName}
     */
    List listChildren(String parentId);

    /**
     * ����һ�����ż�������ŵ��Ӳ��ŵ�{@link DepartmentCodeName}, ���صĽ���ĵ�һ��
     * Ԫ���ǲ���<code>code</code>, ��������������������˳��, �������������ҵ��ʵ��
     * �ж�����{@link DepartmentCodeName}����, Ҫ����һ�����ż������Ӳ��ŵ����, ����
     * ���ص�Ԫ������, ���������һ��������, ��Ҫ�������������Ĳ�����Ϣ
     * 
     * @param code ���������Ϊ������������������е��Ӳ���
     * @return ��������Լ��Ӳ��ŵ�{@link DepartmentCodeName}, ���ٰ���һ��Ԫ��
     * @throws IllegalArgumentException ����������������
     */
    List listDepartmentCodeNames(String code);
}
