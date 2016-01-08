/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.roleClassTree;

import java.util.Map;

/**
 * 角色所具有的权限是与叶子节点权限相关的操作
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface RoleClassTreeService {
    /**
     * 将用户所属角色的权限载入map中 key nickName value SysRoleLeaf
     * @param userId
     * @return map key id value SysLeafRight
     */
	Map getRoleLeafRightByUserId(String userId);
}
