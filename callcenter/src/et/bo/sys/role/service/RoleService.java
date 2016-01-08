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
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 *
 */
public interface RoleService {

//	向里面录入角色信息
    public boolean addRoleInfo(IBaseDTO dto);
    
    //修改角色信息
    public boolean updateRoleInfo(IBaseDTO dto);
    
    //删除角色信息
    public boolean deleteRoleInfo(IBaseDTO dto);
    
    //得到条数
    public int getRoleSize();
    
    //根据条件查询角色
    public List findRoleInfo(IBaseDTO dto,PageInfo pi);
    
    //查询出条件信息以load
    public IBaseDTO getRoleInfo(String id);
}
