/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.authentication.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zyf.framework.authentication.entity.Department;
import com.zyf.framework.authentication.service.DepartmentService;

/**
 * ���ŷ����<code>BeanShell</code>����ʵ��
 * @author zhangli
 * @version $Id: BeanShellDepartmentService.java,v 1.1 2007/11/05 03:16:13 yushn Exp $
 * @since 2007-5-1
 */
public class BeanShellDepartmentService extends BeanShellIntegrationService implements DepartmentService {
    
    private static final String KEY_JDBC_TEMPLATE = "jdbcTemplate";
    private static final String KEY_TOP_DEPARTMENT = "topDepartment";
    
    private JdbcTemplate jdbcTemplate;
    
    private String topDepartment = "0000";
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void setTopDepartment(String top) {
        this.topDepartment = top;
    }
    
    private DepartmentService getSerivce() {
        return (DepartmentService) service;
    }
    
    public List listDepartments(String code) {
        List result = getSerivce().listDepartments(code);
        if (result.size() == 0) {
            throw new IllegalArgumentException("û��ָ������Ĳ���[" + code + "]");
        }
        return result;
    }

    public List listDepartmentCodeNames(String code) {
        List result = getSerivce().listDepartmentCodeNames(code);
        if (result.size() == 0) {
            throw new IllegalArgumentException("û��ָ������Ĳ���[" + code + "]");
        }
        return result;
    }

    public Department loadDepartment(String departmentId) {
        return getSerivce().loadDepartment(departmentId);
    }

    public Department top() {
        return getSerivce().top();
    }

    protected Class getServiceClass() {
        return DepartmentService.class;
    }

    protected void addBeanShellRequiredVariables(Map variables) {
        variables.put(KEY_JDBC_TEMPLATE, jdbcTemplate);
        variables.put(KEY_TOP_DEPARTMENT, topDepartment);
    }

    public List listChildren(String parentId) {
        return getSerivce().listChildren(parentId);
    }
}
