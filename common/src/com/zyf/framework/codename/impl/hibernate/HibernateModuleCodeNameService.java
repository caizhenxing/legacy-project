/*
 * 浙江大学快威集团版权所有(2007-2008). Power by COHEG team.
 */
package com.zyf.framework.codename.impl.hibernate;

import java.util.Collection;
import java.util.Collections;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.framework.codename.CodeName;
import com.zyf.framework.codename.impl.AbstractModuleCodeNameService;

/**
 * 代码名称服务的<code>hibernate</code>实现
 * @author zhangli
 * @since 2007-3-21
 * @version $Id: HibernateModuleCodeNameService.java,v 1.1 2007/11/05 03:16:09 yushn Exp $
 */
public class HibernateModuleCodeNameService extends AbstractModuleCodeNameService {
    private HibernateTemplate hibernateTemplate;
    
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    protected Collection listEntity(Class clazz) {
        Collection c = hibernateTemplate.findByCriteria(order(clazz));
        return c.size() > 0 ? c : Collections.EMPTY_LIST;
    }

    protected CodeName loadEntity(Class clazz, String code) {
        return (CodeName) hibernateTemplate.get(clazz, code);
    }

    protected Collection listEntity(Class clazz, String parentCode) {
        DetachedCriteria dc = order(clazz)
            .createCriteria("parent")
            .add(Restrictions.eq("code", parentCode));
        Collection c = hibernateTemplate.findByCriteria(dc);
        return c.size() > 0 ? c : Collections.EMPTY_LIST;
    }
    
    private DetachedCriteria order(Class clazz) {
        return DetachedCriteria.forClass(clazz)
        .addOrder(Order.asc("sequenceNo"));
    }
}
