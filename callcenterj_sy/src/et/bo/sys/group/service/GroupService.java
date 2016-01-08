/**
 * @(#)GroupService.java	 06/04/17
 *
 * Copyright jingyuzhuo. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.group.service;


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
public interface GroupService {
	
	//	 ��ʾ�Ѿ�����
	String IS_FREEZE = "0";
	// ��ʾû�ж���(����)
	String IS_NOT_FREEZE = "1";
	// ��ʾ�Ѿ�ɾ��
	String DEL_MARK = "Y";
	// ��ʾδɾ��
	String NOT_DEL_MARK = "N";
	/**
	 * 
	 * �������Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean addGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * �޸�����Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean updateGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * ɾ������Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean deleteGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * �õ���ʾ����
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public int getGroupSize();
    /**
	 * 
	 * ����������ѯ����Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public List findGroupInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * 
	 * ͨ��ID��ʾ������Ϣ
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public IBaseDTO getGroupInfo(String id);
}
