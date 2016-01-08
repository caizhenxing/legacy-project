/**
 * @(#)RoleService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.role.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**<code>RoleService</code> is interface 
 * which contains a series of action about group
 * 
 * @author  jingyuzhuo
 * 
 * @version 08/01/30
 * @since   1.0
 *
 */
public interface RoleService {
	/**
	 * 
	 * 添加角色信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean addRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * 修改角色信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean updateRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * 删除角色信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean deleteRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * 得到显示条数
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public int getRoleSize();
    /**
	 * 
	 * 根据条件查询角色信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public List findRoleInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * 
	 * 通过ID显示所有信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public IBaseDTO getRoleInfo(String id);
}
