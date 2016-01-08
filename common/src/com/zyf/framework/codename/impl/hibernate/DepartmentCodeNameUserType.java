/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.codename.impl.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.zyf.framework.codename.DepartmentCodeName;
import com.zyf.security.SecurityContextInfo;

/**
 * ҵ��ʵ��ʹ��{@link DepartmentCodeName}��Ϊ����, ���������ʱ������Ե�ת������<code>hibernate</code>
 * ʵ��, ����ʱ�����沿��<code>id</code>, ����ʱ����һ��������<code>Department</code>ʵ��
 * @author zhangli
 * @version $Id: DepartmentCodeNameUserType.java,v 1.3 2008/01/25 06:55:13 lanxg Exp $
 * @since 2007-4-1
 */
public class DepartmentCodeNameUserType implements UserType {
    
    private Log logger = LogFactory.getLog(DepartmentCodeNameUserType.class);
    
    private int[] TYPES = new int[] {Hibernate.STRING.sqlType()};

    public Object assemble(Serializable serial, Object obj) throws HibernateException {
        return serial;
    }

    public Object deepCopy(Object obj) throws HibernateException {
        if (obj == null) {
            return obj;
        }
        return (DepartmentCodeName) ((DepartmentCodeName) obj).clone();
    }

    public Serializable disassemble(Object obj) throws HibernateException {
        return (Serializable) obj;
    }

    public boolean equals(Object o1, Object o2) throws HibernateException {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        /* DepartmentCodeName implements proper equals */
        return o1.equals(o2);
    }

    public int hashCode(Object obj) throws HibernateException {
        if (obj == null) {
            /* ��� hash ������ȷ, ���Ƕ���������Ѿ��㹻�� */
            return DepartmentCodeName.class.hashCode();
        }
        return obj.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] columns, Object owner) throws HibernateException, SQLException {
        String id = resultSet.getString(columns[0]);
        DepartmentCodeName cn = new DepartmentCodeName();
        if (StringUtils.isBlank(id)) {
            return cn;
        }
        /*
        DepartmentService service = (DepartmentService) ServiceProvider
            .getService(DepartmentService.SERVICE_NAME);
        try {
            Department d = service.loadDepartment(id);
            cn.copyFrom(d);
        } catch (IllegalStateException e) {
            if (logger.isInfoEnabled()) {
                logger.info("ҵ��ʵ��" 
                    + owner.getClass().getName() 
                    + "������id=" + id 
                    + "�Ĳ���,�����ݿ���û���ҵ�");
            }
            cn.setCode(id);
            cn.setName(id);
        }
        */
        ////////////////////////////
        if(SecurityContextInfo.getCurrentUser() != null){
	        cn.setCode(SecurityContextInfo.getCurrentUser().getDeptId());
	        cn.setName(SecurityContextInfo.getCurrentUser().getDeptName());
        }
        ////////////////////////////
        
        return cn;
    }

    public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException {
        DepartmentCodeName d = (DepartmentCodeName) value;
        if (d == null || d.getCode() == null) {
            ps.setNull(index, TYPES[0]);
        } else {
            ps.setString(index, d.getId());
        }
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public Class returnedClass() {
        return DepartmentCodeName.class;
    }

    public int[] sqlTypes() {
        return TYPES;
    }
}
