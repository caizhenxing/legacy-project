package com.zyf.persistent.filter.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.zyf.core.ContextInfo;
import com.zyf.persistent.BaseDto;
import com.zyf.persistent.DaoHelper;
import com.zyf.persistent.filter.ReworkingParameter;
import com.zyf.persistent.hibernate3.entity.UserWrapper;

/**
 * 用于为<code>BaseDto</code>的子类填写缺省的属性, 比如新创建的<code>dto's id</code>,
 * <code>createdTime</code>等. 这个实现作用于<code>SqlMapExecutor's insert</code>方法
 * @author Scott Captain
 * @since 2006-5-22
 * @version $Id: ReworkingParameterForInsert.java,v 1.1 2007/11/05 03:16:07 yushn Exp $
 * @see SqlMapExecutor#insert(String, Object)
 */
public class ReworkingParameterForInsert implements ReworkingParameter {
    
    public void rework(Object[] params) {
        if (params != null && params.length == 2) {
            Object obj = params[1];
            if (obj != null && obj instanceof BaseDto) {
                BaseDto dto = (BaseDto) obj;
                
                if (StringUtils.isBlank(dto.getId())) { // 新增
                    dto.setId(DaoHelper.nextPrimaryKey());
                    UserWrapper createdUser = new UserWrapper();
                    createdUser.setUsername(ContextInfo.getCurrentUser().getUsername());
                    dto.setCreatedUser(createdUser);
                    dto.setCreatedTime(new Date());
                } else { // 修改
                	UserWrapper lastModifyUser = new UserWrapper();
                	lastModifyUser.setUsername(ContextInfo.getCurrentUser().getUsername());
                	dto.setLastModifiedUser(lastModifyUser);
                	dto.setLastModifiedTime(new Date());
                }
            }
        }
    }

    public void restore() {
        //do nothing
    }
}
