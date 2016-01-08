/**
 * className BaseTreeService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.service;

import java.util.List;

/**
* 隔离数据库，用此集合直接生成树
*
* @version 	jan 24 2008 
* @author 王文权
*/
public interface TreeInfoService{
	/**
	 * 
	 * 得到基本树节点列表 里边是TreeNodeService元素
	 * @param
	 * @version 2008-1-4
	 * @return List 里边是TreeNodeService元素 定义了树的基本信息和扩展信息
	 * @throws
	 */
	List getTreeNodeList();
	/**
	 * 
	 * 得到基本树节点列表 里边是TreeNodeService元素
	 * @param List nodeList 里边是TreeNodeService元素 定义了树的基本信息和扩展信息
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTreeNodeList(List nodeList);
	/**
	 * 
	 * 得到树的跟元素
	 * @param
	 * @version 2008-1-4
	 * @return TreeControlNodeService root 树节点 是根元素
	 * @throws
	 */
	TreeControlNodeService getRoot();
	/**
	 * 
	 * 设置树的跟元素
	 * @param TreeControlNodeService tcns 树节点 定义为根元素
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setRoot(TreeControlNodeService tcns);
}