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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* 是一个树节点 包含了对树节点的属性及其操作 增加节点、删除节点、得到父节点等
*
* @version 	jan 24 2008 
* @author 王文权
*/
public interface TreeControlNodeService  extends Comparable , Serializable, Cloneable{

	/**
	 * 
	 * 设置BaseTreeNodeService 是里边有树基本属性及扩展属性的定义注入它树节点就有了id,parntId等树的基本信息同时也有了扩展信息
	 * @param BaseTreeNodeService treeNodeService 是树基本属性的和扩展属性的定义包含id,parentId,label等
	 * @version 2008-1-4
	 * @return void
	 * @throws
	 */
	void setBaseTreeNodeService(BaseTreeNodeService treeNodeService);
	/**
	 * 
	 * 得到BaseTreeNodeService 是里边有树基本属性及扩展属性的定义注入它树节点就有了id,parntId等树的基本信息同时也有了扩展信息
	 * @param 
	 * @version 2008-1-4
	 * @return BaseTreeNodeService treeNodeService 是树基本属性的和扩展属性的定义包含id,parentId,label等
	 * @throws
	 */
	BaseTreeNodeService getBaseTreeNodeService();

	// ------------------------------------------------------------- Properties
	/**
	 * 
	 * 得到树节点类型
	 * @param 
	 * @version 2008-1-4
	 * @return String type 树类型
	 * @throws
	 */
	String getType();
	/**
	 * 
	 * 得到树节点备注信息
	 * @param 
	 * @version 2008-1-4
	 * @return String remark 得到树节点备注信息
	 * @throws
	 */
	String getReamrk();
	/**
	 * 
	 * 得到树的父节点
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService 树节点
	 * @throws
	 */
	TreeControlNodeService getParent();
	
	/**
	 * 
	 * 设置树的父节点
	 * @param  TreeControlNodeService parent 树节点
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setParent(TreeControlNodeService parent);

	/**
	 * 
	 * 得到树TreeService 是一棵树
	 * @param 
	 * @version 2008-1-4
	 * @return TreeService 是一棵树
	 * @throws
	 */
	TreeService getTree();
	/**
	 * 
	 * 设置树 TreeService 是一棵树
	 * @param TreeService tree 是一棵树
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTree(TreeService tree);
	// --------------------------------------------------------- Public Methods

	/**
	 * 
	 * 增加子节点
	 * @param TreeControlNodeService child 新的树节点
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id不唯一抛出
	 */
	public void addChild(TreeControlNodeService child) throws IllegalArgumentException;

	/**
	 * 
	 * 在指定位置增加子节点
	 * @param int offset 在哪个位置增加节点
	 * @param TreeControlNodeService child 要增加的树节点
	 * @version 2008-1-4
	 * @return 
	 * @throws IllegalArgumentException id不唯一抛出
	 */
	public void addChild(int offset, TreeControlNodeService child)throws IllegalArgumentException;

	/**
	 * 
	 * 得到孩子节点集合
	 * @param 
	 * @version 2008-1-4
	 * @return TreeControlNodeService[] 树节点数组
	 * @throws
	 */
	public TreeControlNodeService[] findChildren();

	/**
	 * 
	 * 删除本节点
	 * @param 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void remove();

	/**
	 * 
	 * 根据位置删除节点
	 * @param int offset 删除第几个孩子节点
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void removeChild(int offset);

	// -------------------------------------------------------- Package Methods

	/**
	 * 
	 * 根据传入孩子节点删除 前提待删除节点child的孩子节点都删除了 使用时要注意
	 * @param TreeControlNodeService child 带删除的树孩子节点
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void removeChild(TreeControlNodeService child);

	/**
	 * 
	 * 得到孩子节点
	 * @param 
	 * @version 2008-1-4
	 * @return List 孩子节点集合
	 * @throws
	 */
	List getChildren();


	/**
	 * 
	 * 设置孩子节点
	 * @param ArrayList list 孩子节点集合
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setChildren(ArrayList list);

	/**
	 * 
	 * 设置树节点的名称
	 * @param label 树节点的名称
	 * @version 2008-1-4
	 * @throws
	 */
	void setLabel(String string);
	/**
	 * 
	 * 得到树节点的名称
	 * @param 
	 * @version 2008-1-4
	 * @return String label 树节点的名称
	 * @throws
	 */
	String getLabel();
	/**
	 * 
	 * 得到树的唯一标识
	 * @param 
	 * @version 2008-1-4
	 * @return id 树的唯一标识
	 * @throws
	 */
	public String getId();
	/**
	 * 
	 * 设置树的唯一标识
	 * @param String id 树的唯一标识
	 * @version 2008-1-4
	 * @throws
	 */
	void setId(String id);

	/**
	 * 
	 * 得到树的父节点Id
	 * @param 
	 * @version 2008-1-4
	 * @return String parentId 树的节点的父节点Id
	 * @throws
	 */
	String getParentId();
	/**
	 * 
	 * 设置树的父节点Id
	 * @param　String parentId 设置树的节点的父节点Id
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setParentId(String parentId);
	/**
	 * 
	 * 比较两个树节点的大小
	 * @param Object o 是树节点
	 * @version 2008-1-4
	 * @return int -1,0,1 分别对应 小于,等于,大于
	 * @throws
	 */
	int compareTo(Object o);
    /**
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
     * @throws CloneNotSupportedException;
     */
	Object clone() throws CloneNotSupportedException;
}
