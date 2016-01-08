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
	
	//	 表示已经冻结
	String IS_FREEZE = "0";
	// 表示没有冻结(正常)
	String IS_NOT_FREEZE = "1";
	// 表示已经删除
	String DEL_MARK = "Y";
	// 表示未删除
	String NOT_DEL_MARK = "N";
	/**
	 * 
	 * 添加组信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean addGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * 修改组信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean updateGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * 删除组信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public boolean deleteGroupInfo(IBaseDTO dto);
    /**
	 * 
	 * 得到显示条数
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public int getGroupSize();
    /**
	 * 
	 * 根据条件查询组信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public List findGroupInfo(IBaseDTO dto,PageInfo pi);
    /**
	 * 
	 * 通过ID显示所有信息
	 * @param
	 * @version 2008-1-30
	 * @return 
	 * @throws
	 */
    public IBaseDTO getGroupInfo(String id);
}
