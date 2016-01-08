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
	 * ��ӽ�ɫ��Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean addRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * �޸Ľ�ɫ��Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean updateRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * ɾ����ɫ��Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean deleteRoleInfo(IBaseDTO dto);
    /**
	 * 
	 * �õ���ʾ����
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public int getRoleSize();
    /**
	 * 
	 * ����������ѯ��ɫ��Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public List findRoleInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * 
	 * ͨ��ID��ʾ������Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public IBaseDTO getRoleInfo(String id);
}
