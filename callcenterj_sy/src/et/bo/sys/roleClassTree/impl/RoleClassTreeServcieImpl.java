/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * ��ɫ�����е�Ȩ������Ҷ�ӽڵ�Ȩ����صĲ���
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
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
     * ���û�������ɫ��Ȩ������map��
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
