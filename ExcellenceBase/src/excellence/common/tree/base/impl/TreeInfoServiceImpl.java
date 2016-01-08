/**
 * className BaseTreeService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.impl;

import java.util.List;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;

/**
* 隔离数据库，用此集合直接生成树
*
* @version 	jan 24 2008 
* @author 王文权
*/
public class TreeInfoServiceImpl implements TreeInfoService{
	/**
	 * 基本树节点列表 里边是TreeNodeService元素
	 */
	private List treeNodeList;
	/**
	 * 设置树的跟元素
	 */
	private TreeControlNodeService root;
	/**
	 * 
	 * 得到基本树节点列表 里边是TreeNodeService元素
	 * @param
	 * @version 2008-1-4
	 * @return List 里边是TreeNodeService元素 定义了树的基本信息和扩展信息
	 * @throws
	 */
	public List getTreeNodeList()
	{
		return this.treeNodeList;
	}
	/**
	 * 
	 * 得到基本树节点列表 里边是TreeNodeService元素
	 * @param List nodeList 里边是TreeNodeService元素 定义了树的基本信息和扩展信息
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setTreeNodeList(List treeNodeList)
	{
		this.treeNodeList = treeNodeList;
	}
	/**
	 * 
	 * 得到树的跟元素
	 * @param
	 * @version 2008-1-4
	 * @return TreeControlNodeService root 树节点 是根元素
	 * @throws
	 */
	public TreeControlNodeService getRoot()
	{
		return this.root;
	}
	/**
	 * 
	 * 设置树的跟元素
	 * @param TreeControlNodeService tcns 树节点 定义为根元素
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setRoot(TreeControlNodeService root)
	{
		this.root = root;
	}
}
