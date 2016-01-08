/**
 * className ClassTreeLoadService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.classtree;

import java.util.List;

import excellence.common.tree.base.service.BaseTreeNodeService;

/**
 * 隔离数据库为ClassTreeService提供数据
 * 数据可来自db xml等
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface ClassTreeLoadService {
	/**
	 * 将db xml等的数据载入TreeNodeService里
	 * @return List<BaseTreeNodeService>
	 */
	List<BaseTreeNodeService> loadTreeNodeService();
	/**
	 * 将db xml等的数据载入TreeNodeService里
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService>
	 */
	List<BaseTreeNodeService> loadLeafRights(String action, String target);
	
	/**
	 * 将用户信息封装到BaseTreeNodeService里在生成部门树时其作为叶子节点附加到部门树上
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService> 
	 */
	List<BaseTreeNodeService> getAllUsersForDeptTree(String action, String target);
}
