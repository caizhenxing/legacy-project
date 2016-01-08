/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.roleClassTree.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import et.bo.sys.roleClassTree.RoleClassTreeService;
import et.po.SysRightRole;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
/**
 * 角色所具有的权限是与叶子节点权限相关的操作
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class RoleClassTreeServcieImpl implements RoleClassTreeService {
	private BaseDAO dao=null;

	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	/**
     * 将用户所属角色的权限载入map中
     * @param userId
     * @return map key id value SysLeafRight
     */
	public Map<String,et.po.SysLeafRight> getRoleLeafRightByUserId(String userId) {
		// TODO Auto-generated method stub
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, userId);
		Iterator it = su.getSysRole().getSysRightRoles().iterator();
		Map<String,et.po.SysLeafRight> leafRightMap = new HashMap<String,et.po.SysLeafRight>();
		while(it.hasNext())
		{
			SysRightRole srr = (SysRightRole)it.next();
			leafRightMap.put(srr.getSysLeafRight().getNickName(), srr.getSysLeafRight());
		}
		return leafRightMap;
	}

}
