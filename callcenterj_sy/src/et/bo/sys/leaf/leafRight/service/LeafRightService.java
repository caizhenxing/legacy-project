/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.leaf.leafRight.service;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 叶子节点字典表授权
 *
 * @version 	jan 01 2008 
 * @author 王文权 荆玉琢
 */
public interface LeafRightService {

	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	//void addLeafRight(IBaseDTO dto);
	/**
	 * 得到叶子节点权限列表
	 * @param nodeId
	 * @return List<SysLeafRight>
	 */
	List<DynaBeanDTO> getLeafRightByNodeId(String nodeId);
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	//void updateLeafRight(IBaseDTO dto);
	/**
	 * 删除当前角色所具有的叶子节点权限
	 * @param String roleId 当前角色
	 * @param String treeId 叶子节点
	 * @return
	 * @throws
	 */
	void deleteRoleRights(String roleId,String treeId);
	/**
	 * 授予当前角色所具有的叶子节点权限
	 * @param String roleId 当前角色
	 * @param String[] leafRightId 权限数组
	 * @return
	 * @throws
	 */
	void grantRoleRights(String roleId,List<String> leafRightIds);
	/**
	 * 加载用户所具有的模块权限
	 * @param String userId 当前用户id
	 * @return TreeService 模块及叶子节点
	 * @throws
	 */
	TreeService loadTree(String userId);
	//void removeParamTree(String id);
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
	//void deleteLeafRight(String leafRightId);
	/**
	 * 给角色授权
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerRole(String roleId, TreeService rights);
	/**
	 * 给用户批量授权
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerBatchPerson2Role(String roleId, TreeService rights);
	/**
	 * 当前角色的权限图标 其他正常显示
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerRoleIcon(String roleId, TreeService rights, String tmpIcon);
	//**********************************************************
	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	void addDict(IBaseDTO dto,String treeId);
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	boolean updateDict(IBaseDTO dto);
	
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
	void deleteDict(String id);
	
	/**
	 * 根据ID返回详细信息
	 * @param 
	 * @return
	 * @throws
	 */
	IBaseDTO treeInfo(String id);
	/**
	 * 根据ID返回查询信息
	 * @param 
	 * @return
	 * @throws
	 */
	List treeList(IBaseDTO dto, PageInfo pi,String id);
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	 int getTreeSize();
	 /*
	  * 表主键
	  */
	 public String getKey();
}
